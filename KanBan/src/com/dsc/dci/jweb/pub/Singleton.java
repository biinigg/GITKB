package com.dsc.dci.jweb.pub;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.management.Query;

import org.jboss.mx.util.MBeanServerLocator;

import com.dci.jweb.DCIConstants.DCIWebConstants;
import com.dci.jweb.DCIEnums.Database.DBType;
import com.dci.jweb.DCIEnums.Server.LangType;
import com.dci.jweb.PublicLib.DCIString;
import com.dci.jweb.PublicLib.Register.RegisterObject;

public class Singleton {
	private static Singleton instance = null;
	private boolean licStatus;
	private boolean dbStatus;
	private boolean nolicense;
	private boolean isTestArea;
	private LangType deflang;
	private HashMap<String, String> onlineUsers = null;
	private HashMap<String, String> licUsers = null;
	private HashMap<String, String> sysConfig = null;
	private HashMap<String, HashMap<String, String>> multiLanguage = null;
	private DBType sysDBType = null;
	private String contextPath = null;
	private String currVer = null;
	private String officialUrl = null;
	private String testAreaConfigPath = null;

	// private String o

	private Singleton() {
		this.licStatus = false;
		this.dbStatus = false;
		this.nolicense = false;
		// this.isTestArea = false;
		this.onlineUsers = new HashMap<String, String>();
		this.licUsers = new HashMap<String, String>();
		this.sysConfig = new HashMap<String, String>();
		this.multiLanguage = new HashMap<String, HashMap<String, String>>();
		this.contextPath = "";
		checkArea();
	}

	public static Singleton getInstance() {
		if (instance == null) {
			synchronized (Singleton.class) {
				if (instance == null) {
					instance = new Singleton();
				}
			}
		}
		return instance;
	}

	public boolean getLicenseStatus() {
		return this.licStatus;
	}

	public void setLicenseStatus(boolean status) {
		this.licStatus = status;
	}

	public boolean getDatabaseStatus() {
		return this.dbStatus;
	}

	public void setDatabaseStatus(boolean status) {
		this.dbStatus = status;
	}

	public boolean getWebStartStatus() {
		return this.licStatus && this.dbStatus;
	}

	public void addOnlineUser(String uid, String ip) {
		if (!DCIString.isNullOrEmpty(uid) && !DCIString.isNullOrEmpty(ip)) {
			if (this.isTestArea) {
				new UserSyncSend().addUser(uid, ip, false);
			} else {
				this.onlineUsers
						.put(uid + APConstants.getUserkeytag() + ip, String.valueOf(System.currentTimeMillis()));
			}
			// System.out.println(uid + "---" + this.onlineUsers.get(uid +
			// APConstants.getUserkeytag() + ip));
		}
	}

	public void removeOnlineUser(String uid, String ip) {
		String key = uid + APConstants.getUserkeytag() + ip;
		if (this.isTestArea) {
			new UserSyncSend().removeUser(uid, ip, false);
		} else {
			if (this.onlineUsers.containsKey(key)) {
				removeLicenseUser(uid, ip);
				this.onlineUsers.remove(key);
			}
		}
	}

	public void addLicenseUser(String uid, String ip) {
		if (!DCIString.isNullOrEmpty(uid) && !DCIString.isNullOrEmpty(ip)) {
			if (this.isTestArea) {
				new UserSyncSend().addUser(uid, ip, true);
			} else {
				this.licUsers.put(uid + APConstants.getUserkeytag() + ip, String.valueOf(System.currentTimeMillis()));
			}
		}
	}

	public void removeLicenseUser(String uid, String ip) {
		String key = uid + APConstants.getUserkeytag() + ip;
		if (this.isTestArea) {
			new UserSyncSend().removeUser(uid, ip, true);
		} else {
			if (this.licUsers.containsKey(key)) {
				this.licUsers.remove(key);
			}
		}
	}

	public int getUsedLicense() {
		int result = 0;
		if (this.isTestArea) {
			result = new UserSyncSend().getUserNum(true);
		} else {
			result = this.licUsers.size();
		}
		return result;
	}

	public int getOnlineUsers() {
		int result = 0;
		if (this.isTestArea) {
			result = new UserSyncSend().getUserNum(false);
		} else {
			result = this.onlineUsers.size();
		}
		return result;
	}

	public HashMap<String, String> getLicUsers() {
		HashMap<String, String> result = null;
		if (this.isTestArea) {
			result = new UserSyncSend().getUserList(true);
		} else {
			result = this.licUsers;
		}
		return result;
	}

	public HashMap<String, String> getOnlineUserList() {
		HashMap<String, String> result = null;
		if (this.isTestArea) {
			result = new UserSyncSend().getUserList(false);
		} else {
			result = this.onlineUsers;
		}
		return result;
	}

	public boolean userExist(String uid, String ip) {
		boolean exist = false;
		// System.out.println("input key : " + uid + APConstants.getUserkeytag()
		// + ip);
		if (this.isTestArea) {
			exist = new UserSyncSend().getUserExist(false, uid, ip);
		} else {
			exist = this.onlineUsers.containsKey(uid + APConstants.getUserkeytag() + ip);
		}
		// for (String k : this.onlineUsers.keySet()) {
		// System.out.println(k);
		// }
		return exist;
	}

	public boolean licUserExist(String uid, String ip) {
		boolean exist = false;
		// System.out.println("input key : " + uid + APConstants.getUserkeytag()
		// + ip);
		if (this.isTestArea) {
			exist = new UserSyncSend().getUserExist(true, uid, ip);
		} else {
			exist = this.licUsers.containsKey(uid + APConstants.getUserkeytag() + ip);
		}

		// for (String k : this.licUsers.keySet()) {
		// System.out.println(k);
		// }

		return exist;
	}

	/*
	 * public int getUserLimit() { int num = 0; if (this.isTestArea) { num = new
	 * UserSyncSend().getMaxUser(); } else { num =
	 * RegisterObject.getInstance().getMaxUser(); }
	 * 
	 * return num; }
	 */

	public String getLanguage(String lang, String key) {
		String value = null;
		HashMap<String, String> tmp = null;
		if (this.multiLanguage != null && this.multiLanguage.containsKey(lang)) {
			tmp = this.multiLanguage.get(lang);
			if (tmp.containsKey(key)) {
				value = tmp.get(key);
			} else {
				value = key;
			}
		} else {
			value = key;
		}
		return value;
	}

	public String getLanguageForCol(String lang, String key) {
		String value = null;
		HashMap<String, String> tmp = null;

		value = getLanguage(lang, key);

		if (value.equals(key)) {
			if (this.multiLanguage != null && this.multiLanguage.containsKey(lang)) {
				tmp = this.multiLanguage.get(lang);

				for (String tkey : tmp.keySet()) {
					if (tkey.toLowerCase().indexOf(key.toLowerCase()) != -1) {
						if (!value.equals(tmp.get(tkey))) {
							value = tmp.get(tkey);
							break;
						}
					}
				}
			} else {
				value = key;
			}
		}

		return value;
	}

	public HashMap<String, String> getLanguageList(String lang, String key) {
		HashMap<String, String> result = null;
		HashMap<String, String> tmp = null;

		result = new LinkedHashMap<String, String>();
		result.put(key, key);
		if (this.multiLanguage != null && this.multiLanguage.containsKey(lang)) {
			tmp = this.multiLanguage.get(lang);

			for (String tkey : tmp.keySet()) {
				if (tkey.toLowerCase().indexOf(key.toLowerCase()) != -1) {
					if (!result.containsValue(tmp.get(tkey))) {
						result.put(tkey, tmp.get(tkey));
					}
				}
			}
		}

		return result;
	}

	public void addMultiLanguage(String lang, String key, String value) {
		HashMap<String, String> tmp = null;

		if (this.multiLanguage == null) {
			this.multiLanguage = new HashMap<String, HashMap<String, String>>();
		}

		if (this.multiLanguage.containsKey(lang)) {
			tmp = this.multiLanguage.get(lang);
		} else {
			tmp = new HashMap<String, String>();
		}

		if (tmp == null) {
			tmp = new HashMap<String, String>();
		}
		tmp.put(key, value);
		this.multiLanguage.put(lang, tmp);
	}

	public void removeMultiLanguage(String lang, String key) {
		HashMap<String, String> tmp = null;

		if (this.multiLanguage == null) {
			this.multiLanguage = new HashMap<String, HashMap<String, String>>();
		}

		if (this.multiLanguage.containsKey(lang)) {
			tmp = this.multiLanguage.get(lang);
		} else {
			tmp = new HashMap<String, String>();
		}

		if (tmp == null) {
			tmp = new HashMap<String, String>();
		}

		if (tmp.containsKey(key)) {
			tmp.remove(key);
		}
		this.multiLanguage.put(lang, tmp);
	}

	public void clearMultiLanguage() {
		if (this.multiLanguage != null) {
			this.multiLanguage.clear();
		}
	}

	public void setMultiLanguage(HashMap<String, HashMap<String, String>> multilang) {
		this.multiLanguage = multilang;
	}

	public HashMap<String, HashMap<String, String>> getMultiLanguage() {
		if (this.multiLanguage == null) {
			this.multiLanguage = new HashMap<String, HashMap<String, String>>();
		}
		return this.multiLanguage;
	}

	public void clearSystemConfig() {
		this.sysConfig.clear();
	}

	public HashMap<String, String> getSystemConfig() {
		return this.sysConfig;
	}

	public void setSystemConfig(HashMap<String, String> condifs) {
		this.sysConfig = condifs;
	}

	public String getSystemConfig(String key) {
		if (this.sysConfig == null || !this.sysConfig.containsKey(key)) {
			return "";
		} else {
			return this.sysConfig.get(key);
		}
	}

	public String getDeflang() {
		if (this.deflang == null) {
			this.deflang = LangType.CHT;
		}
		return deflang.toString();
	}

	public void setDeflang(LangType deflang) {
		this.deflang = deflang;
	}

	public void checkUser() {
		if (this.onlineUsers != null) {
			long time = 0;
			long timeoutgap = 0;
			if (DCIString.isNullOrEmpty(this.sysConfig.get("UserTimeOut"))) {
				timeoutgap = 1800000;
			} else {
				timeoutgap = Long.valueOf(this.sysConfig.get("UserTimeOut"));
			}
			ArrayList<String> expUser = new ArrayList<String>(0);
			for (String key : this.onlineUsers.keySet()) {
				time = Long.parseLong(this.onlineUsers.get(key));
				if (System.currentTimeMillis() - time > timeoutgap) {
					expUser.add(key);
				}
			}

			for (int i = 0; i < expUser.size(); i++) {
				this.licUsers.remove(expUser.get(i));
				this.onlineUsers.remove(expUser.get(i));
			}
			// System.out.println("user checked : remove " + expUser.size() +
			// " users");
		}
	}

	public boolean isNolicense() {
		return nolicense;
	}

	public void setNolicense(boolean nolicense) {
		this.nolicense = nolicense;
	}

	public boolean isTestArea() {
		return this.isTestArea;
	}

	public void setTestArea(boolean isTestArea) {
		this.isTestArea = isTestArea;
	}

	public DBType getSysDBType() {
		return sysDBType;
	}

	public void setSysDBType(DBType sysDBType) {
		this.sysDBType = sysDBType;
	}

	public String getContextPath() {
		return contextPath;
	}

	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
		this.testAreaConfigPath = contextPath + File.separator + "System_Files" + File.separator + "TestAreaConfigs";
	}

	public String getCurrVer() {
		return currVer;
	}

	public void setCurrVer(String currVer) {
		this.currVer = currVer;
	}

	public String getOfficialUrl() {
		return officialUrl;
	}

	public void setOfficialUrl(String officialUrl) {
		this.officialUrl = officialUrl;
	}

	public String getTestAreaConfigPath() {
		return testAreaConfigPath;
	}

	private void checkArea() {
		MBeanServer mbs = null;
		Set<ObjectName> objs = null;
		ObjectName obj = null;
		String port = null;
		try {
			try {
				mbs = MBeanServerLocator.locateJBoss();

				objs = mbs.queryNames(new ObjectName("*:type=Connector,*"),
						Query.match(Query.attr("protocol"), Query.value("HTTP/1.1")));
			} catch (Exception e) {
				objs = null;
			}
			if (objs == null || objs.size() == 0) {
				mbs = ManagementFactory.getPlatformMBeanServer();

				objs = mbs.queryNames(new ObjectName("*:type=Connector,*"),
						Query.match(Query.attr("protocol"), Query.value("HTTP/1.1")));
			}

			for (Iterator<ObjectName> i = objs.iterator(); i.hasNext();) {
				obj = i.next();
				port = obj.getKeyProperty("port");
				if (!DCIString.isNullOrEmpty(port)) {
					setTestArea(!port.equals(DCIWebConstants.getServerdefport()));
					break;
				}
			}
			if (DCIString.isNullOrEmpty(port)) {
				setTestArea(true);
			}
		} catch (Exception ex) {
			setTestArea(true);
			ex.printStackTrace();
		}
	}

	public String getExpExcelName(Class<?> c) {
		String result = null;

		if (this.sysConfig == null || !this.sysConfig.containsKey("ExcelExtension")) {
			result = c.getSimpleName() + ".xls";
		} else {
			result = c.getSimpleName() + "." + this.sysConfig.get("ExcelExtension");
		}
		return result;
	}
}
