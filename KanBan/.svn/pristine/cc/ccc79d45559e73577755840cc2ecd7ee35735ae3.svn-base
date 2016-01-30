package com.dsc.dci.jweb.pub;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.web.multipart.MultipartFile;

import com.dci.jweb.DCIBeans.Database.DBInfoBean;
import com.dci.jweb.DCIBeans.website.FileUploadBean;
import com.dci.jweb.DCIConstants.DCIWebConstants;
import com.dci.jweb.DCIEnums.Database.ConnectionStatus;
import com.dci.jweb.DCIEnums.Database.ConnectionType;
import com.dci.jweb.DataBaseLib.Database.DataDatabaseObject;
import com.dci.jweb.DataBaseLib.Database.DatabaseObjects;
import com.dci.jweb.PublicLib.ConfigControl;
import com.dci.jweb.PublicLib.DBControl;
import com.dci.jweb.PublicLib.DCIString;
import com.dci.jweb.PublicLib.Register.RegisterObject;
import com.dsc.dci.sqlcode.main.sqlInit;
import com.dsc.dci.sqlcode.main.sqlPubMethods;
import com.dci.jweb.DCIEnums.Database.DBType;

public class APPubMethods {
	private sqlPubMethods cmd;
	private DBControl dbctrl;

	public APPubMethods() {
		this.cmd = new sqlPubMethods();
		this.dbctrl = new DBControl();
	}

	public ConnectionStatus setConnectionPool() {
		Singleton s = Singleton.getInstance();
		ConnectionStatus status = ConnectionStatus.Fail;
		DatabaseObjects dbobj = DatabaseObjects.getInstance();

		if (dbobj.isDataSourceExist(ConnectionType.SYS)) {
			dbobj.clearDataSource(ConnectionType.SYS);
		}
		ConfigControl cc = new ConfigControl(s.isTestArea(), s.getTestAreaConfigPath());
		DBInfoBean info = cc.DBConfigLoader(APConstants.getConfigfilename());
		if (info == null) {
			status = ConnectionStatus.Fail;
		} else {
			s.setDeflang(info.getLangType());
			s.setOfficialUrl(info.getOfficialUrl());
			RegisterObject.getInstance().setGuardInfo(info.getGuardIP(), info.getGuardPort());
			dbobj.createDataSource(info, ConnectionType.SYS);
			if (dbobj.isDataSourceExist(ConnectionType.SYS)) {
				status = setDataConnectionPool(dbobj);
				if (status == ConnectionStatus.Success) {
					s.setSysDBType(info.getDBType());
				}
			} else {
				status = ConnectionStatus.Fail;
			}
		}
		s.setDatabaseStatus(status == ConnectionStatus.Success);
		// System.out.println(status.toString());
		return status;
	}

	private ConnectionStatus setDataConnectionPool(DatabaseObjects dbobj) {
		ConnectionStatus status = ConnectionStatus.Fail;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		DBControl dbctrl = new DBControl();
		// PublicMethods pm = new PublicMethods();
		DBInfoBean bean = null;
		DataDatabaseObject datadbobj = DataDatabaseObject.getInstance();

		try {
			conn = dbobj.getConnection(ConnectionType.SYS);
			ps = conn.prepareStatement(sqlInit.getAllConnInfo());
			rs = ps.executeQuery();
			if (datadbobj.isDataSourceExist()) {
				datadbobj.clearDataSource();
			}
			while (rs.next()) {
				bean = new DBInfoBean();
				dbobj.copySysDBConfigSetting(bean);
				bean.setConnID(rs.getString("conn_id"));
				bean.setDBAddr(rs.getString("db_addr"));
				bean.setDBPort(rs.getString("db_port"));
				bean.setDBType(DBType.valueOf(rs.getString("db_type")));
				bean.setDBName(rs.getString("db_name"));
				bean.setUserName(rs.getString("db_user"));
				bean.setPassword(DCIString.Base64Decode(rs.getString("db_pwd")));
				datadbobj.createDataDataSource(bean, rs.getString("conn_id"));
			}
			status = ConnectionStatus.Success;
		} catch (Exception ex) {
			status = ConnectionStatus.Fail;
			ex.printStackTrace();
		} finally {
			dbctrl.closeConnection(rs, ps, conn);
		}

		return status;
	}

	public void reloadProcess(boolean reloadConn) {
		System.out.println("Start DCI web process");
		System.out.println("Init singleton objects");
		Singleton s = Singleton.getInstance();
		// APPubMethods method = new APPubMethods();
		checkLicense();

		System.out.println("Build all datasource");
		if (reloadConn) {
			setConnectionPool();
		}
		if (s.getDatabaseStatus()) {
			// System.out.println("Check Current Version");
			// new EKBVersionCheck();
			System.out.println("Load System Config");
			loadSystemConfig();
			System.out.println("Load Multi Language");
			loadMultiLanguage();
			if (!s.getLicenseStatus()) {
				System.out.println("license check fail");
			}
		} else {
			System.out.println("set connection pool fail");
		}

	}

	public void checkLicense() {
		Singleton s = Singleton.getInstance();
		DatabaseObjects dbo = DatabaseObjects.getInstance();

		ArrayList<HashMap<String, String>> regs = null;
		HashMap<String, String> tmp = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			if (s.isTestArea()) {
				new UserSyncSend().setROInfos();
				s.setLicenseStatus(true);
			} else {
				conn = dbo.getConnection(ConnectionType.SYS);
				ps = conn.prepareStatement(this.cmd.getRegistres());

				rs = ps.executeQuery();
				regs = new ArrayList<HashMap<String, String>>();
				while (rs.next()) {
					tmp = new HashMap<String, String>();
					tmp.put("serial_number", rs.getString("serial_number"));
					tmp.put("execute_code", rs.getString("execute_code"));
					tmp.put("register_info", rs.getString("register_info"));
					regs.add(tmp);
				}
				if (regs == null || regs.size() == 0) {
					s.setNolicense(true);
				} else {
					s.setNolicense(false);
				}
				RegisterObject rb = RegisterObject.getInstance();
				rb.setActiveSerial(regs);
				s.setLicenseStatus(rb.getLicenseStatus());
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			s.setLicenseStatus(false);
		} finally {
			this.dbctrl.closeConnection(rs, ps, conn);
		}
	}

	public void loadMultiLanguage() {
		Singleton s = Singleton.getInstance();
		DatabaseObjects dbo = DatabaseObjects.getInstance();

		HashMap<String, HashMap<String, String>> multiLang = null;
		HashMap<String, HashMap<String, String>> orimultiLang = null;
		HashMap<String, Integer> orirecords = null;
		HashMap<String, String> tmp = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String lastLang = null;

		try {
			conn = dbo.getConnection(ConnectionType.SYS);
			ps = conn.prepareStatement(this.cmd.getMultiLanguage());

			rs = ps.executeQuery();

			while (rs.next()) {
				if (lastLang == null || !lastLang.equals(rs.getString("lang"))) {
					if (multiLang == null) {
						multiLang = new HashMap<String, HashMap<String, String>>();
					}
					if (tmp != null) {
						multiLang.put(lastLang, tmp);
					}
					tmp = new HashMap<String, String>();
				}
				tmp.put(rs.getString("lang_key"), rs.getString("lang_value"));
				lastLang = rs.getString("lang");
			}
			if (tmp != null) {
				multiLang.put(lastLang, tmp);
			}
			orimultiLang = s.getMultiLanguage();
			if (orirecords == null) {
				orirecords = new HashMap<String, Integer>();
			}
			for (String key : orimultiLang.keySet()) {
				if (orimultiLang.get(key) == null) {
					orirecords.put(key, 0);
				} else {
					orirecords.put(key, orimultiLang.get(key).size());
				}
			}
			s.clearMultiLanguage();
			s.setMultiLanguage(multiLang);

			if (multiLang == null || multiLang.size() == 0) {
				System.out.print("Database has no language data");
			} else {
				int cnt = 0;
				String msg = "";
				boolean hidemsg = true;
				for (String key : multiLang.keySet()) {
					if (multiLang.get(key) == null) {
						cnt = 0;
					} else {
						cnt = multiLang.get(key).size();
					}

					msg += key + ":" + multiLang.get(key).size() + "     ";

					if (orirecords.get(key) == null) {
						hidemsg = hidemsg && cnt == 0;
					} else {
						hidemsg = hidemsg && cnt == orirecords.get(key);
					}

					cnt = 0;
				}
				if (!hidemsg) {
					System.out.println("Language data changed reload " + msg);
				}

			}

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			this.dbctrl.closeConnection(rs, ps, conn);
		}
	}

	public void addLanguages(String currpath) {
		DatabaseObjects dbobj = DatabaseObjects.getInstance();
		Connection conn = null;
		PreparedStatement ps = null;
		String[] datas = null;
		BufferedReader bufferedReader = null;
		InputStreamReader isr = null;
		FileInputStream fis = null;
		DBControl dbctrl = new DBControl();
		int cnt = 0;

		try {
			conn = dbobj.getConnection(ConnectionType.SYS);
			ps = conn.prepareStatement(this.cmd.truncateSTDLanguage(), ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			ps.executeUpdate();
			dbctrl.closeConnection(null, ps, null);
			ps = conn.prepareStatement(this.cmd.addSTDLanguage(), ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			File langfile = new File(currpath + File.separator + "System_Files" + File.separator + "LanguageDatas.dci");

			if (langfile.exists()) {
				fis = new FileInputStream(langfile);
				isr = new InputStreamReader(fis, "UTF8");

				bufferedReader = new BufferedReader(isr);

				String line;
				while ((line = bufferedReader.readLine()) != null) {
					cnt++;
					datas = line.split(";");
					if (datas.length == 3) {
						ps.setString(1, datas[0]);
						ps.setString(2, datas[1]);
						ps.setString(3, datas[2]);
						ps.addBatch();
						// System.out.println(datas[1]);
					}

					if (cnt % 200 == 0) {
						ps.executeBatch();
						cnt = 0;
					}
				}

				if (cnt != 0) {
					ps.executeBatch();
				}
			}
			dbctrl.closeConnection(null, ps, null);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			dbctrl.closeConnection(null, ps, conn);
			try {
				if (bufferedReader != null) {
					bufferedReader.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if (fis != null) {
					fis.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if (isr != null) {
					isr.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void loadSystemConfig() {
		Singleton s = Singleton.getInstance();
		DatabaseObjects dbo = DatabaseObjects.getInstance();

		HashMap<String, String> configs = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = dbo.getConnection(ConnectionType.SYS);
			ps = conn.prepareStatement(this.cmd.getSystemConfig());

			rs = ps.executeQuery();
			configs = new HashMap<String, String>();

			while (rs.next()) {
				configs.put(rs.getString("config_id"), rs.getString("config_value"));
			}

			s.clearSystemConfig();
			s.setSystemConfig(configs);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			this.dbctrl.closeConnection(rs, ps, conn);
		}
	}

	public HashMap<String, ArrayList<HashMap<String, String>>> getResult(boolean success) {
		HashMap<String, ArrayList<HashMap<String, String>>> result = null;

		result = new HashMap<String, ArrayList<HashMap<String, String>>>();
		HashMap<String, String> tmp = new HashMap<String, String>();
		ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
		tmp.put("result", String.valueOf(success));
		data.add(tmp);

		result.put("success", data);

		return result;
	}

	public HashMap<String, Object> getGridFormat(String user_id, String grid_id) {
		DatabaseObjects dbo = DatabaseObjects.getInstance();

		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		HashMap<String, Object> datas = null;
		ArrayList<HashMap<String, Object>> cols = null;
		HashMap<String, Object> tmp = null;

		try {
			conn = dbo.getConnection(ConnectionType.SYS);
			ps = conn.prepareStatement(this.cmd.getGridFormat());
			ps.setString(1, user_id);
			ps.setString(2, user_id);
			ps.setString(3, grid_id);
			rs = ps.executeQuery();

			cols = new ArrayList<HashMap<String, Object>>();

			while (rs.next()) {
				tmp = new HashMap<String, Object>();
				tmp.put("col_id", rs.getString("col_id"));
				tmp.put("col_index", rs.getString("col_index"));
				tmp.put("col_width", rs.getString("col_width"));
				tmp.put("col_visible", rs.getString("col_visible"));

				cols.add(tmp);
			}

			datas = new HashMap<String, Object>();
			datas.put("colFormats", cols);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			this.dbctrl.closeConnection(rs, ps, conn);
		}

		return datas;
	}

	public HashMap<String, Object> editGridFormat(String user_id, String grid_id, String coldatas) {
		DatabaseObjects dbo = DatabaseObjects.getInstance();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<HashMap<String, String>> datas = null;
		HashMap<String, Object> result = null;
		boolean boolResult = false;
		String resultMsg = "";
		try {

			conn = dbo.getConnection(ConnectionType.SYS);

			ps = conn.prepareStatement(this.cmd.deleteGridFormat(), ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			ps.setString(1, user_id);
			ps.setString(2, grid_id);
			ps.executeUpdate();

			this.dbctrl.closeConnection(null, ps, null);

			if (!DCIString.isNullOrEmpty(coldatas)) {
				datas = new ObjectMapper().readValue(coldatas, new TypeReference<ArrayList<HashMap<String, String>>>() {
				});

				if (datas != null && datas.size() > 0) {
					ps = conn.prepareStatement(this.cmd.addGridFormat(), ResultSet.TYPE_SCROLL_INSENSITIVE,
							ResultSet.CONCUR_READ_ONLY);
					for (int i = 0; i < datas.size(); i++) {
						ps.setString(1, user_id);
						ps.setString(2, grid_id);
						ps.setString(3, datas.get(i).get("col_id").toString());
						ps.setString(4, datas.get(i).get("col_index").toString());
						ps.setString(5, datas.get(i).get("col_width").toString());
						ps.setString(6, datas.get(i).get("col_visible").toString());
						ps.addBatch();

						if (i != 0 && (i % 100) == 0) {
							ps.executeBatch();
							ps.clearBatch();
						}
					}

					ps.executeBatch();
				}
			}
			boolResult = true;
		} catch (Exception ex) {
			ex.printStackTrace();
			boolResult = false;
			resultMsg = ex.getMessage();
		} finally {
			this.dbctrl.closeConnection(rs, ps, conn);
			result = new HashMap<String, Object>();
			result.put("success", boolResult);
			result.put("errorMessage", resultMsg);
		}
		return result;
	}

	public String getIconPath(String iconid) {
		DatabaseObjects dbo = DatabaseObjects.getInstance();

		String iconPath = "";
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = dbo.getConnection(ConnectionType.SYS);
			ps = conn.prepareStatement(this.cmd.getIconPath());
			ps.setString(1, iconid);
			rs = ps.executeQuery();

			if (rs.next()) {
				iconPath = rs.getString("icon_path");
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			iconPath = "";
		} finally {
			this.dbctrl.closeConnection(rs, ps, conn);
		}

		return iconPath;
	}

	public boolean saveUploadFile(String filename, FileUploadBean item) {
		boolean ok = false;
		String path = null;
		File dir = null;
		OutputStream fs = null;
		InputStream stream = null;

		try {
			path = new ConfigControl(false, null).getConfigXMLPath() + File.separator + APConstants.getUploadicondir();

			dir = new File(path);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			MultipartFile file = item.getFile();
			String fileName = null;
			if (file.getSize() > 0) {
				stream = file.getInputStream();
				fileName = path + File.separator + filename;
				fs = new FileOutputStream(fileName);

				int readBytes = 0;
				byte[] buffer = new byte[1024 * 1024];
				while ((readBytes = stream.read(buffer, 0, 10000)) != -1) {
					fs.write(buffer, 0, readBytes);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (fs != null) {
				try {
					fs.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (stream != null) {
				try {
					stream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return ok;
	}

	public boolean deleteUploadFile(String filename) {
		boolean ok = false;

		try {
			File file = new File(filename);

			if (file.exists()) {
				for (int i = 0; i < 3; i++) {
					file.delete();
					if (file.exists()) {
						Thread.currentThread().sleep(1000);
					} else {
						ok = true;
						break;
					}
				}
			} else {
				ok = true;
			}
		} catch (Exception ex) {
			ok = false;
		}

		return ok;
	}

	public String getUploadFormParameter(String id, HttpServletRequest request) {
		String value = "";

		try {
			HttpServletRequest tmp = request;
			DiskFileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			List<FileItem> items = upload.parseRequest(tmp);

			if (items != null && items.size() > 0) {

				for (FileItem item : items) {
					if (item.isFormField()) {
						// System.out.println(item.getFieldName() + "---" +
						// item.getString());
						if (item.getFieldName().equals(id)) {
							value = item.getString();
							break;
						}
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return value;
	}

	public String readVersion(String file) {
		BufferedReader bufferedReader = null;
		InputStreamReader isr = null;
		FileInputStream fis = null;
		String ver = null;

		try {

			File langfile = new File(file);

			if (langfile.exists()) {
				fis = new FileInputStream(langfile);
				isr = new InputStreamReader(fis, "UTF8");

				bufferedReader = new BufferedReader(isr);

				String line;

				line = bufferedReader.readLine();

				if (DCIString.isNullOrEmpty(line)) {
					ver = DCIWebConstants.getEkbinitversion();
					System.out.println("ver file is empty");
				} else {
					ver = DCIString.Base64Decode(DCIString.strDecode(line));
				}
			} else {

				System.out.println("ver file not found---" + file);
				ver = DCIWebConstants.getEkbinitversion();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			ver = DCIWebConstants.getEkbinitversion();
		} finally {
			try {
				if (bufferedReader != null) {
					bufferedReader.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if (fis != null) {
					fis.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if (isr != null) {
					isr.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (!DCIString.isNullOrEmpty(ver)) {
			// String[] vercodes = null;
			// vercodes = ver.split("\\.");
			//
			// for (int i = 0; i < vercodes.length; i++) {
			// if (i == 0) {
			// ver = DCIString.strFix(vercodes[i], 3, true, "0");
			// } else {
			// ver += DCIString.strFix(vercodes[i], 3, true, "0");
			// }
			// }

			ver = DCIString.transToLongVerFormat(ver);
		}

		return ver;
	}

	public void saveDBVersion(String version) {
		Connection conn = null;
		PreparedStatement ps = null;
		DatabaseObjects dbobj = DatabaseObjects.getInstance();
		String sql = null;

		try {
			if (Singleton.getInstance().getSystemConfig().containsKey("SYSVersion")) {
				sql = this.cmd.updDBVersion();
			} else {
				sql = this.cmd.addDBVersion();
			}

			conn = dbobj.getConnection(ConnectionType.SYS);

			ps = conn.prepareStatement(sql);
			ps.setString(1, version);
			ps.executeUpdate();
			loadSystemConfig();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			this.dbctrl.closeConnection(null, ps, conn);
		}
	}
}
