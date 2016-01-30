package com.dsc.dci.jweb.funcs.ekb.indkanban;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import com.dci.jweb.DCIConstants.DCIWebConstants;
import com.dci.jweb.DCIEnums.Database.ConnectionType;
import com.dci.jweb.DataBaseLib.Database.DatabaseObjects;
import com.dci.jweb.PublicLib.APControl;
import com.dci.jweb.PublicLib.DBControl;
import com.dci.jweb.PublicLib.DCIString;
import com.dci.jweb.PublicLib.Register.RegisterObject;
import com.dsc.dci.jweb.funcs.ekb.kanban.KanBan;
import com.dsc.dci.jweb.funcs.main.login.Login;
import com.dsc.dci.jweb.pub.Singleton;
import com.dsc.dci.sqlcode.funcs.ekb.sqlIndKanBan;

public class IndKanBan {
	private int loginStatus;

	public IndKanBan() {
		this.loginStatus = 99;
	}

	public HashMap<String, String> decodeParams(String code) {
		HashMap<String, String> params = null;
		String de64 = null;
		String decode = null;
		String[] values = null;

		params = new HashMap<String, String>();

		if (!DCIString.isNullOrEmpty(code)) {
			de64 = DCIString.Base64Decode(code);
			decode = DCIString.strDecode(de64);
			values = decode.split(";");

			params.put("conn_id", values[0]);
			params.put("func_id", values[1]);
			params.put("user_id", values[2]);
			params.put("pwd", DCIString.Base64Decode(values[3]));
			params.put("dcikey", values[4]);
		}

		return params;
	}

	public boolean transCheck(HttpServletRequest request, HashMap<String, String> params) {
		boolean ok = false;
		Login login = new Login();
		// if
		// (request.getSession().getAttribute(DCIWebConstants.getUserInfoTag())
		// == null) {
		this.loginStatus = login.checkPwd(params.get("user_id"), params.get("pwd"),
				new APControl().getRequestIP(request));
		// } else {
		// this.loginStatus = 8;
		// }
		if (this.loginStatus == 4) {
			HashMap<String, String> tmp = login.getUserInfo();
			if (tmp.size() == 0) {
				this.loginStatus = 5;
			} else {
				Singleton s = Singleton.getInstance();
				if (s.getUsedLicense() >= RegisterObject.getInstance().getMaxUser()) {
					// if (s.getUsedLicense() >= s.getUserLimit()) {
					login.logout(request, params.get("user_id"));
					this.loginStatus = 9;
				} else {
					tmp.put("lang", Singleton.getInstance().getDeflang());
					request.getSession().setAttribute(DCIWebConstants.getUserInfoTag(), tmp);
					request.getSession().setAttribute(DCIWebConstants.getUserInfoTag() + params.get("user_id"), tmp);
				}
			}
		}

		if (this.loginStatus == 4) {
			ok = true;
		} else {
			ok = false;
		}

		return ok;
	}

	public String encodePageParams(HashMap<String, String> params) {
		String code = null;
		if (params != null && params.containsKey("conn_id") && params.containsKey("func_id")) {
			code = DCIString.strEncode(params.get("conn_id") + ";" + params.get("func_id"));
			code = DCIString.Base64Encode(code);
		} else {
			code = "";
		}
		return code;
	}

	public HashMap<String, String> decodePageParams(String code) {
		HashMap<String, String> params = null;
		String de64 = null;
		String decode = null;
		String[] values = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		params = new HashMap<String, String>();

		if (!DCIString.isNullOrEmpty(code)) {
			de64 = DCIString.Base64Decode(code);
			decode = DCIString.strDecode(de64);
			values = decode.split(";");

			params.put("conn_id", values[0]);
			params.put("func_id", values[1]);
			if (values.length >= 3) {
				params.put("relation_filter", values[2]);
			} else {
				params.put("relation_filter", "");
			}

			try {
				conn = DatabaseObjects.getInstance().getConnection(ConnectionType.SYS);
				ps = conn.prepareStatement(new sqlIndKanBan().getFuncName());
				ps.setString(1, values[1]);
				rs = ps.executeQuery();

				if (rs.next()) {
					params.put("func_name",
							Singleton.getInstance().getLanguage(Singleton.getInstance().getDeflang(), rs.getString(1)));
				} else {
					params.put("func_name", values[1]);
				}
			} catch (Exception ex) {
				params = null;
				ex.printStackTrace();
			} finally {
				new DBControl().closeConnection(rs, ps, conn);
			}
		}

		return params;
	}

	public int getLoginStatus() {
		return this.loginStatus;
	}

	public String getCUSUrl() {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String url = null;

		try {
			conn = DatabaseObjects.getInstance().getConnection(ConnectionType.SYS);
			ps = conn.prepareStatement(new sqlIndKanBan().getCUSUrl());
			rs = ps.executeQuery();

			if (rs.next()) {
				if (!DCIString.isNullOrEmpty(rs.getString("func_url"))) {
					url = rs.getString("func_url");
				}
			}

			if (DCIString.isNullOrEmpty(url)) {
				url = "./../../FuncViews/Funcs/IndKanBan.jsp";
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			new DBControl().closeConnection(rs, ps, conn);
		}

		return url;
	}
}
