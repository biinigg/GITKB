package com.dsc.dci.jweb.funcs.configs.sysdbconfig;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.dci.jweb.DCIBeans.Database.DBInfoBean;
import com.dci.jweb.DCIEnums.Database.ConnectionType;
import com.dci.jweb.DCIEnums.Server.ConfigSaveStatus;
import com.dci.jweb.DataBaseLib.Database.DatabaseObjects;
import com.dci.jweb.PublicLib.ConfigControl;
import com.dci.jweb.PublicLib.DBControl;
import com.dci.jweb.PublicLib.DCIString;
import com.dci.jweb.PublicLib.PublicMethods;
import com.dsc.dci.jweb.patchs.EKBVersionCheck;
import com.dsc.dci.jweb.pub.APConstants;
import com.dsc.dci.jweb.pub.APPubMethods;
import com.dsc.dci.jweb.pub.Singleton;
import com.dsc.dci.sqlcode.configs.sqlSysDBConfig;

public class SysDBConfig {
	private String errMsg;
	private String currpath;
	private Singleton s;

	public SysDBConfig() {
		this.s = Singleton.getInstance();
	}

	public void setCurrPath(String path) {
		this.currpath = path;
	}

	public String getErrMsg() {
		return this.errMsg;
	}

	public ArrayList<HashMap<String, String>> getAllIPs() {
		ArrayList<HashMap<String, String>> datas = null;
		ArrayList<String> ips = null;
		HashMap<String, String> tmp = null;

		ips = new PublicMethods().getAllIP();
		datas = new ArrayList<HashMap<String, String>>();

		if (ips != null) {
			for (int i = 0; i < ips.size(); i++) {
				tmp = new HashMap<String, String>();
				tmp.put("display", ips.get(i));
				tmp.put("value", ips.get(i));
				datas.add(tmp);
			}
		}

		return datas;
	}

	public DBInfoBean getConfigDate(boolean isvm) {
		DBInfoBean data = null;
		ConfigControl cc = new ConfigControl(s.isTestArea(), s.getTestAreaConfigPath());
		if (new File(cc.getConfigXMLPath(APConstants.getConfigfilename())).exists()) {
			data = cc.DBConfigLoader(APConstants.getConfigfilename());
		}

		if (data == null) {
			data = new DBInfoBean();
			data.setDBAddr("");
			data.setDBPort("1433");
			data.setDBName("EKB");
			data.setGuardIP("");
			data.setGuardPort(6666);
			data.setMaxActive(1000);
			data.setMaxWait(1000);
			data.setMaxIdle(100);
			data.setMinIdle(10);
			data.setOfficialUrl("");
			if (isvm) {
				data.setLocalIP(new PublicMethods().getServerIPAddr());
			}
		} else {
			if (isvm) {
				data.setLocalIP(new PublicMethods().readRegIP());
			}
		}

		return data;
	}

	public ConfigSaveStatus saveData(String confdata) {
		this.errMsg = "";
		ConfigSaveStatus status = ConfigSaveStatus.SaveException;
		if (confdata == null || confdata.length() == 0) {
			status = ConfigSaveStatus.NoData;
		} else {
			HashMap<String, Object> bean = null;
			try {
				bean = DCIString.jsonTranToObjMap(confdata);
				// new ObjectMapper().readValue(confdata, HashMap.class);

				if (checkGuardServer(bean)) {
					if (writeXML(bean)) {
						status = ConfigSaveStatus.valueOf(new APPubMethods().setConnectionPool());
						if (status == ConfigSaveStatus.ConnectionFail) {
							String path = new ConfigControl(s.isTestArea(), s.getTestAreaConfigPath())
									.getConfigXMLPath(APConstants.getConfigfilename());
							File f = new File(path);
							if (f.exists()) {
								f.delete();
							}
						} else {
							PublicMethods pm = new PublicMethods();
							if (pm.getVMData()) {
								pm.writeRegIP(bean.get("LocalIP").toString());
							}
							new APPubMethods().addLanguages(this.currpath);

							System.out.println("Check Current Version");
							new EKBVersionCheck();
							// new WebAPListener(false);
							new APPubMethods().reloadProcess(false);
							addSysConnInfoAndIcon(bean);
						}
					} else {
						status = ConfigSaveStatus.SaveFail;
					}
				} else {
					status = ConfigSaveStatus.GuardConnectFail;
				}
			} catch (Exception ex) {
				status = ConfigSaveStatus.SaveException;
				this.errMsg = ex.getMessage();
				ex.printStackTrace();
			}
		}

		return status;
	}

	private boolean checkGuardServer(HashMap<String, Object> bean) {
		boolean ok = false;
		String ip = null;
		String port = null;
		String hkey = null;
		PublicMethods pm = null;
		pm = new PublicMethods();

		if (pm.getVMData()) {
			if (bean == null) {
				ok = false;
			} else {
				if (bean.get("GuardIP") == null || bean.get("GuardPort") == null) {
					ok = false;
				} else {
					ip = bean.get("GuardIP").toString();
					port = bean.get("GuardPort").toString();
					if (DCIString.isNullOrEmpty(ip) || !DCIString.isInteger(port)) {
						ok = false;
					} else {
						hkey = pm.getHardwareKey(ip, Integer.parseInt(port));
						if (DCIString.isNullOrEmpty(hkey) || hkey.length() < 30) {
							ok = false;
						} else {
							ok = true;
						}
					}
				}
			}
		} else {
			ok = true;
		}
		return ok;
	}

	private void addSysConnInfoAndIcon(HashMap<String, Object> bean) {
		DatabaseObjects dbobj = DatabaseObjects.getInstance();
		sqlSysDBConfig cmd = new sqlSysDBConfig();
		Connection conn = null;
		PreparedStatement ps = null;
		DBControl dbctrl = new DBControl();
		try {
			conn = dbobj.getConnection(ConnectionType.SYS);
			ps = conn.prepareStatement(cmd.deleteSysConn(), ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			ps.executeUpdate();
			dbctrl.closeConnection(null, ps, null);
			ps = conn.prepareStatement(cmd.addSysConn(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			ps.setString(1, bean.get("DBAddr").toString());
			ps.setString(2, bean.get("DBName").toString());
			ps.setString(3, bean.get("UserName").toString());
			ps.setString(4, DCIString.Base64Encode(bean.get("Password").toString()));
			ps.setString(5, bean.get("DBType").toString());
			ps.setString(6, bean.get("DBPort").toString());

			ps.executeUpdate();
			dbctrl.closeConnection(null, ps, null);

			File dir = new File(
					new ConfigControl(false, s.getTestAreaConfigPath()).getConfigXMLPath(APConstants
							.getSysicondir()));

			if (dir.exists()) {
				String[] files = dir.list();

				if (files != null) {
					Arrays.sort(files);

					ps = conn.prepareStatement(cmd.deleteSysIcon(), ResultSet.TYPE_SCROLL_INSENSITIVE,
							ResultSet.CONCUR_READ_ONLY);
					ps.setString(1, "I" + DCIString.strFix(String.valueOf(files.length), 3, true, "0"));
					ps.executeUpdate();
					dbctrl.closeConnection(null, ps, null);

					ps = conn.prepareStatement(cmd.addSysIcon(), ResultSet.TYPE_SCROLL_INSENSITIVE,
							ResultSet.CONCUR_READ_ONLY);

					for (int i = 0; i < files.length; i++) {
						if (files[i].toLowerCase().endsWith("gif")) {
							ps.setString(1, "I" + DCIString.strFix(String.valueOf(i + 1), 3, true, "0"));
							ps.setString(2, files[i].substring(files[i].indexOf("_") + 1, files[i].lastIndexOf(".")));
							ps.setString(3, String.valueOf(i + 1));
							ps.setString(4, dir.getPath() + File.separator + files[i]);
							ps.addBatch();
						}
					}

					if (files.length > 0) {
						ps.executeBatch();
					}
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			dbctrl.closeConnection(null, ps, conn);
		}
	}

	/*
	 * private void addLanguages() { DatabaseObjects dbobj =
	 * DatabaseObjects.getInstance(); sqlSysDBConfig cmd = new sqlSysDBConfig();
	 * Connection conn = null; PreparedStatement ps = null; String[] datas =
	 * null; BufferedReader bufferedReader = null; InputStreamReader isr = null;
	 * FileInputStream fis = null; DBControl dbctrl = new DBControl(); int cnt =
	 * 0;
	 * 
	 * try { conn = dbobj.getConnection(ConnectionType.SYS); ps =
	 * conn.prepareStatement(cmd.truncateSTDLanguage(),
	 * ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
	 * ps.executeUpdate(); dbctrl.closeConnection(null, ps, null); ps =
	 * conn.prepareStatement(cmd.addSTDLanguage(),
	 * ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
	 * 
	 * File langfile = new File(this.currpath + File.separator + "System_Files"
	 * + File.separator + "LanguageDatas.dci");
	 * 
	 * if (langfile.exists()) { fis = new FileInputStream(langfile); isr = new
	 * InputStreamReader(fis, "UTF8");
	 * 
	 * bufferedReader = new BufferedReader(isr);
	 * 
	 * String line; while ((line = bufferedReader.readLine()) != null) { cnt++;
	 * datas = line.split(";"); if (datas.length == 3) { ps.setString(1,
	 * datas[0]); ps.setString(2, datas[1]); ps.setString(3, datas[2]);
	 * ps.addBatch(); }
	 * 
	 * if (cnt % 200 == 0) { ps.executeBatch(); cnt = 0; } }
	 * 
	 * if (cnt != 0) { ps.executeBatch(); } } dbctrl.closeConnection(null, ps,
	 * null); } catch (Exception ex) { ex.printStackTrace(); } finally {
	 * dbctrl.closeConnection(null, ps, conn); try { if (bufferedReader != null)
	 * { bufferedReader.close(); } } catch (IOException e) { // TODO
	 * Auto-generated catch block e.printStackTrace(); } try { if (fis != null)
	 * { fis.close(); } } catch (IOException e) { // TODO Auto-generated catch
	 * block e.printStackTrace(); } try { if (isr != null) { isr.close(); } }
	 * catch (IOException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } } }
	 */

	private boolean writeXML(HashMap<String, Object> bean) {
		boolean ok = false;
		ConfigControl cc = null;
		File configFile = null;
		FileOutputStream fos = null;
		DocumentBuilderFactory docFactory = null;
		DocumentBuilder docBuilder = null;
		Document doc = null;
		Element rootElement = null;
		Element DBConfig = null;
		Element PoolConfig = null;
		Element GuardConfig = null;
		Element AreaConfig = null;
		Element tmp = null;
		TransformerFactory transformerFactory = null;
		Transformer transformer = null;
		DOMSource source = null;
		StreamResult result = null;

		try {
			docFactory = DocumentBuilderFactory.newInstance();
			docBuilder = docFactory.newDocumentBuilder();

			doc = docBuilder.newDocument();
			rootElement = doc.createElement("DCISYS");
			doc.appendChild(rootElement);

			DBConfig = doc.createElement("DBConfig");
			rootElement.appendChild(DBConfig);

			tmp = doc.createElement("DBAddr");
			tmp.appendChild(doc.createTextNode(bean.get("DBAddr").toString()));
			DBConfig.appendChild(tmp);

			tmp = doc.createElement("DBPort");
			tmp.appendChild(doc.createTextNode(bean.get("DBPort").toString()));
			DBConfig.appendChild(tmp);

			tmp = doc.createElement("DBName");
			tmp.appendChild(doc.createTextNode(bean.get("DBName").toString()));
			DBConfig.appendChild(tmp);

			tmp = doc.createElement("UserName");
			tmp.appendChild(doc.createTextNode(bean.get("UserName").toString()));
			DBConfig.appendChild(tmp);

			tmp = doc.createElement("Password");
			tmp.appendChild(doc.createCDATASection(DCIString.Base64Encode(bean.get("Password").toString())));
			DBConfig.appendChild(tmp);

			tmp = doc.createElement("DBType");
			tmp.appendChild(doc.createTextNode(bean.get("DBType").toString()));
			DBConfig.appendChild(tmp);

			tmp = doc.createElement("LangType");
			tmp.appendChild(doc.createTextNode(bean.get("LangType").toString()));
			DBConfig.appendChild(tmp);

			PoolConfig = doc.createElement("PoolConfig");
			rootElement.appendChild(PoolConfig);

			tmp = doc.createElement("maxActive");
			tmp.appendChild(doc.createTextNode(bean.get("maxActive").toString()));
			PoolConfig.appendChild(tmp);

			tmp = doc.createElement("maxWait");
			tmp.appendChild(doc.createTextNode(bean.get("maxWait").toString()));
			PoolConfig.appendChild(tmp);

			tmp = doc.createElement("maxIdle");
			tmp.appendChild(doc.createTextNode(bean.get("maxIdle").toString()));
			PoolConfig.appendChild(tmp);

			tmp = doc.createElement("minIdle");
			tmp.appendChild(doc.createTextNode(bean.get("minIdle").toString()));
			PoolConfig.appendChild(tmp);

			tmp = doc.createElement("RemoveAbandoned");
			tmp.appendChild(doc.createTextNode(String.valueOf(true)));
			PoolConfig.appendChild(tmp);

			tmp = doc.createElement("RemoveAbandonedTimeout");
			tmp.appendChild(doc.createTextNode(String.valueOf(120)));
			PoolConfig.appendChild(tmp);

			GuardConfig = doc.createElement("GuardConfig");
			rootElement.appendChild(GuardConfig);

			tmp = doc.createElement("GuardIP");
			tmp.appendChild(doc.createTextNode(bean.get("GuardIP").toString()));
			GuardConfig.appendChild(tmp);

			tmp = doc.createElement("GuardPort");
			tmp.appendChild(doc.createTextNode(bean.get("GuardPort").toString()));
			GuardConfig.appendChild(tmp);

			AreaConfig = doc.createElement("AreaConfig");
			rootElement.appendChild(AreaConfig);

			tmp = doc.createElement("OfficialUrl");
			tmp.appendChild(doc.createTextNode(bean.get("OfficialUrl").toString()));
			AreaConfig.appendChild(tmp);

			cc = new ConfigControl(s.isTestArea(), s.getTestAreaConfigPath());
			configFile = new File(cc.getConfigXMLPath());

			if (!configFile.exists()) {
				configFile.mkdir();
			}
			configFile = new File(cc.getConfigXMLPath(APConstants.getConfigfilename()));

			transformerFactory = TransformerFactory.newInstance();
			transformer = transformerFactory.newTransformer();
			source = new DOMSource(doc);
			fos = new FileOutputStream(configFile);
			result = new StreamResult(fos);
			transformer.transform(source, result);

			ok = true;

		} catch (Exception ex) {
			ok = false;
			ex.printStackTrace();
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		return ok;
	}
}
