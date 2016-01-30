package com.dsc.dci.jweb.funcs.system.usersyncrecv;

import java.util.HashMap;

import com.dci.jweb.DCIEnums.Server.UserCountType;
import com.dci.jweb.PublicLib.DCIString;
import com.dci.jweb.PublicLib.Register.RegisterObject;
import com.dsc.dci.jweb.pub.APConstants;
import com.dsc.dci.jweb.pub.Singleton;

public class UserSyncRecv {

	public HashMap<String, String> addUser(String counttp, String uid, String ip) {
		UserCountType uctp = null;
		HashMap<String, String> result = null;
		Singleton s = Singleton.getInstance();

		result = new HashMap<String, String>();

		result.put(APConstants.getReskey(), String.valueOf(false));

		if (s.isTestArea()) {
			result.put(APConstants.getErrkey(), "officla_url_err");
		} else {
			uctp = UserCountType.valueOf(counttp);

			if (uctp != null) {
				if (uctp == UserCountType.License) {
					s.addLicenseUser(uid, ip);
					result.put(APConstants.getReskey(), String.valueOf(true));
				} else if (uctp == UserCountType.OnLine) {
					s.addOnlineUser(uid, ip);
					result.put(APConstants.getReskey(), String.valueOf(true));
				}
			}
		}

		return result;
	}

	public HashMap<String, String> removeUser(String counttp, String uid, String ip) {
		UserCountType uctp = null;
		HashMap<String, String> result = null;
		Singleton s = Singleton.getInstance();

		result = new HashMap<String, String>();
		result.put(APConstants.getReskey(), String.valueOf(false));

		if (s.isTestArea()) {
			result.put(APConstants.getErrkey(), "officla_url_err");
		} else {
			uctp = UserCountType.valueOf(counttp);

			if (uctp != null) {
				if (uctp == UserCountType.License) {
					s.removeLicenseUser(uid, ip);
					result.put(APConstants.getReskey(), String.valueOf(true));
				} else if (uctp == UserCountType.OnLine) {
					s.removeOnlineUser(uid, ip);
					result.put(APConstants.getReskey(), String.valueOf(true));
				}
			}
		}

		return result;
	}

	public HashMap<String, String> getUserNum(String counttp) {
		UserCountType uctp = null;
		HashMap<String, String> result = null;
		int users = 0;
		Singleton s = Singleton.getInstance();

		result = new HashMap<String, String>();
		result.put(APConstants.getReskey(), String.valueOf(false));

		if (s.isTestArea()) {
			result.put(APConstants.getErrkey(), "officla_url_err");
		} else {
			uctp = UserCountType.valueOf(counttp);

			if (uctp != null) {
				result = new HashMap<String, String>();
				if (uctp == UserCountType.License) {
					users = s.getUsedLicense();
				} else if (uctp == UserCountType.OnLine) {
					users = s.getOnlineUsers();
				}

				result.put("userNum", String.valueOf(users));
				result.put(APConstants.getReskey(), String.valueOf(true));
			}
		}

		return result;
	}

	public HashMap<String, String> getUserList(String counttp) {
		UserCountType uctp = null;
		HashMap<String, String> result = null;
		HashMap<String, String> tmp = null;
		Singleton s = Singleton.getInstance();

		result = new HashMap<String, String>();
		result.put(APConstants.getReskey(), String.valueOf(false));

		if (s.isTestArea()) {
			result.put(APConstants.getErrkey(), "officla_url_err");
		} else {
			uctp = UserCountType.valueOf(counttp);

			if (uctp != null) {
				if (uctp == UserCountType.License) {
					tmp = s.getLicUsers();
				} else if (uctp == UserCountType.OnLine) {
					tmp = s.getOnlineUserList();
				}
				if (tmp != null) {
					for (String key : tmp.keySet()) {
						result.put(key, tmp.get(key));
					}
				}
				result.put(APConstants.getReskey(), String.valueOf(true));
			}
		}

		return result;
	}

	public HashMap<String, String> usrExist(String counttp, String uid, String ip) {
		UserCountType uctp = null;
		HashMap<String, String> result = null;
		boolean userExist = false;
		Singleton s = Singleton.getInstance();

		result = new HashMap<String, String>();
		result.put(APConstants.getReskey(), String.valueOf(false));

		if (s.isTestArea()) {
			result.put(APConstants.getErrkey(), "officla_url_err");
		} else {
			uctp = UserCountType.valueOf(counttp);

			if (uctp != null) {
				result = new HashMap<String, String>();
				if (uctp == UserCountType.License) {
					userExist = s.licUserExist(uid, ip);
				} else if (uctp == UserCountType.OnLine) {
					userExist = s.userExist(uid, ip);
				}

				result.put("uexist", String.valueOf(userExist));
				result.put(APConstants.getReskey(), String.valueOf(true));
			}
		}

		return result;
	}

	public HashMap<String, String> getLicStatus() {
		HashMap<String, String> result = null;
		Singleton s = Singleton.getInstance();

		result = new HashMap<String, String>();
		result.put(APConstants.getReskey(), String.valueOf(false));

		if (s.isTestArea()) {
			result.put(APConstants.getErrkey(), "officla_url_err");
		} else {
			result = new HashMap<String, String>();
			result.put("licstatus", String.valueOf(s.getLicenseStatus()));
			result.put(APConstants.getReskey(), String.valueOf(true));
		}

		return result;
	}

	public HashMap<String, String> getMaxUserNum() {
		HashMap<String, String> result = null;
		Singleton s = Singleton.getInstance();

		result = new HashMap<String, String>();
		result.put(APConstants.getReskey(), String.valueOf(false));

		if (s.isTestArea()) {
			result.put(APConstants.getErrkey(), "officla_url_err");
		} else {
			result.put("maxUser", String.valueOf(RegisterObject.getInstance().getMaxUser()));
			result.put(APConstants.getReskey(), String.valueOf(true));
		}

		return result;
	}

	public HashMap<String, String> getROInfos() {
		HashMap<String, String> result = null;
		Singleton s = Singleton.getInstance();

		result = new HashMap<String, String>();
		result.put(APConstants.getReskey(), String.valueOf(false));

		if (s.isTestArea()) {
			result.put(APConstants.getErrkey(), "officla_url_err");
		} else {
			result.put("as", DCIString.jsonTranFromArrayList(RegisterObject.getInstance().getAllSerials()));
			result.put("si", DCIString.jsonTranFromArrayList(RegisterObject.getInstance().getSerialInfo()));
			result.put(APConstants.getReskey(), String.valueOf(true));
		}

		return result;
	}

}
