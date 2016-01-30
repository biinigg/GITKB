package com.dsc.dci.jweb.funcs.main.login;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import com.dci.jweb.DCIEnums.Database.ConnectionType;
import com.dci.jweb.DataBaseLib.Database.DatabaseObjects;
import com.dci.jweb.PublicLib.APControl;
import com.dci.jweb.PublicLib.DBControl;
import com.dci.jweb.PublicLib.DCIDate;
import com.dci.jweb.PublicLib.DCIString;
import com.dci.jweb.PublicLib.Register.RegisterObject;
import com.dsc.dci.jweb.pub.APConstants;
import com.dsc.dci.jweb.pub.Singleton;
import com.dsc.dci.sqlcode.main.sqlLogin;

public class Login {
	private sqlLogin cmd;
	private DatabaseObjects dbobj;
	private DBControl dbctrl;
	private HashMap<String, String> userInfo;

	public Login() {
		this.dbobj = DatabaseObjects.getInstance();
		this.cmd = new sqlLogin();
		this.dbctrl = new DBControl();
	}

	public HashMap<String, String> getUserInfo() {
		if (this.userInfo == null) {
			this.userInfo = new HashMap<String, String>();
		}
		return this.userInfo;
	}

	public int checkPwd(String uid, String pwd, String ip) {
		int status = 0;
		String dbpwd = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			if (Singleton.getInstance().userExist(uid, ip)) {
				status = 7;// 使用者已登入
			} else {
				conn = this.dbobj.getConnection(ConnectionType.SYS);
				ps = conn.prepareStatement(this.cmd.getPwd());
				ps.setString(1, uid);

				rs = ps.executeQuery();

				if (rs.next()) {
					if (rs.getInt("visible") == 0) {
						status = 2;// 帳號已停用
					} else if (rs.getInt("group_visible") == 0) {
						status = 6;// 使用者群組已停用
					} else {
						if (DCIString.isNullOrEmpty(rs.getString("user_pwd"))) {
							dbpwd = "";
						} else {
							dbpwd = DCIString.Base64Decode(rs.getString("user_pwd"));
						}
						if (pwd.equals(dbpwd)) {
							this.userInfo = new HashMap<String, String>();
							this.userInfo.put("user_id", uid);
							this.userInfo.put("user_name", rs.getString("user_name"));
							this.userInfo.put("group_name", rs.getString("group_name"));
							this.userInfo.put("login_time",
									DCIDate.parseString(Calendar.getInstance().getTime(), "yyyy/MM/dd hh:mm:ss"));
							Singleton.getInstance().addOnlineUser(uid, ip);
							status = 4;
						} else {
							status = 3;// 密碼錯誤
						}
					}
				} else {
					status = 1;// 帳號不存在
				}
			}

		} catch (Exception ex) {
			status = 99;// exception
			ex.printStackTrace();
		} finally {
			this.dbctrl.closeConnection(rs, ps, conn);
		}

		return status;
	}

	public void logout(HttpServletRequest request, String uid) {
		APControl apc = new APControl();
		apc.clearSession(request.getSession(), uid);
		String ip = apc.getRequestIP(request);
		Singleton.getInstance().removeLicenseUser(uid, ip);
		Singleton.getInstance().removeOnlineUser(uid, ip);
		// request.getSession().invalidate();
	}

	// public void logout(HttpServletRequest request) {
	// APControl apc = new APControl();
	// String uid = apc.getUserInfoFromSession(request.getSession(), "user_id");
	// apc.clearSession(request.getSession());
	// String ip = apc.getRequestIP(request);
	// Singleton.getInstance().removeLicenseUser(uid, ip);
	// Singleton.getInstance().removeOnlineUser(uid, ip);
	// request.getSession().invalidate();
	// }

	public HashMap<String, Object> getUserList() {
		HashMap<String, Object> datas = null;
		ArrayList<HashMap<String, Object>> users = null;
		HashMap<String, String> currusers = null;
		HashMap<String, Object> tmp = null;
		String[] info = null;
		int cnt = 1;

		Singleton s = Singleton.getInstance();
		currusers = s.getOnlineUserList();
		users = new ArrayList<HashMap<String, Object>>();
		for (String key : currusers.keySet()) {
			info = key.split(APConstants.getUserkeytag());
			if (info != null && info.length == 2) {
				tmp = new HashMap<String, Object>();
				tmp.put("seq", String.valueOf(cnt));
				tmp.put("user_id", info[0]);
				if (info[1].indexOf(":") == -1) {
					tmp.put("ip_addr_d", info[1]);
				} else {
					tmp.put("ip_addr_d", "127.0.0.1");
				}
				tmp.put("ip_addr", info[1]);
				tmp.put("use_lic", s.getLicUsers().containsKey(key));
				users.add(tmp);
				cnt++;
			}
		}

		tmp = new HashMap<String, Object>();
		tmp.put("onlineUser", String.valueOf(currusers.size()));
		tmp.put("usedLic", String.valueOf(s.getUsedLicense()));
		tmp.put("licLimit", String.valueOf(RegisterObject.getInstance().getMaxUser()));
		// tmp.put("licLimit", String.valueOf(s.getUserLimit()));
		datas = new HashMap<String, Object>();
		datas.put("griduser", users);
		datas.put("head", tmp);

		// System.out.println(s.getOnlineUsers() + "---" + s.getUsedLicense());
		return datas;
	}

	public HashMap<String, Object> removeUser(String userid, String ip, String pwd, String lang) {
		HashMap<String, Object> result = null;
		boolean boolResult = false;
		String resultMsg = "";
		Singleton s = Singleton.getInstance();

		try {
			if (DCIString.isNullOrEmpty(userid)) {
				resultMsg = s.getLanguage(lang, "dcie08");
				boolResult = false;
			} else {
				if (s.getSystemConfig("RemoveUserPwd").equals(pwd)) {
					s.removeLicenseUser(userid, ip);
					s.removeOnlineUser(userid, ip);
					boolResult = true;
				} else {
					resultMsg = s.getLanguage(lang, "logine03");
					boolResult = false;
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			boolResult = false;
			resultMsg = ex.getMessage();
		} finally {
			result = new HashMap<String, Object>();
			result.put("success", boolResult);
			result.put("errorMessage", resultMsg);
		}
		return result;
	}
}
