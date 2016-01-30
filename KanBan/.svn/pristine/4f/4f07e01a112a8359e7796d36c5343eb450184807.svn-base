package com.dsc.dci.jweb.funcs.configs.regserial;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

import com.dci.jweb.DCIEnums.Database.ConnectionType;
import com.dci.jweb.DataBaseLib.Database.DatabaseObjects;
import com.dci.jweb.PublicLib.DBControl;
import com.dci.jweb.PublicLib.DCIDate;
import com.dci.jweb.PublicLib.DCIString;
import com.dci.jweb.PublicLib.PublicMethods;
import com.dci.jweb.PublicLib.Register.RegisterCenter;
import com.dci.jweb.PublicLib.Register.RegisterObject;
import com.dsc.dci.jweb.pub.APPubMethods;
import com.dsc.dci.sqlcode.configs.sqlRegSerial;

public class RegSerial {
	private sqlRegSerial cmd;
	private DatabaseObjects dbobj;
	private DBControl dbctrl;

	public RegSerial() {
		this.dbobj = DatabaseObjects.getInstance();
		this.cmd = new sqlRegSerial();
		this.dbctrl = new DBControl();
	}

	public HashMap<String, Object> getInitData() {
		HashMap<String, Object> datas = null;
		ArrayList<HashMap<String, String>> allregs = null;
		ArrayList<HashMap<String, String>> serials = null;
		HashMap<String, String> tmp = null;
		RegisterObject ro = RegisterObject.getInstance();

		try {
			allregs = ro.getAllSerials();
			serials = new ArrayList<HashMap<String, String>>();
			for (int i = 0; i < allregs.size(); i++) {
				tmp = new HashMap<String, String>();
				if (allregs.get(i).get("isReg").equals("1") || allregs.get(i).get("isReg").equals("2")) {
					if (allregs.get(i).get("area").equals("0123456789")) {
						if (allregs.get(i).get("isExpired").equals("false")) {
							tmp.put("label", allregs.get(i).get("serial"));
							tmp.put("value", allregs.get(i).get("serial"));
						}
					} else {
						tmp.put("label", allregs.get(i).get("serial"));
						tmp.put("value", allregs.get(i).get("serial"));
					}
				}
				if (tmp.size() > 0) {
					serials.add(tmp);
				}
			}

			datas = new HashMap<String, Object>();
			datas.put("regs", allregs);
			datas.put("serials", serials);
			datas.put("mkey", new PublicMethods().getHardwareKey(ro.getGuardIP(), ro.getGuardPort()));

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return datas;
	}

	public HashMap<String, Object> saveData(String serial_number) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		HashMap<String, Object> result = null;
		boolean boolResult = false;
		String resultMsg = "";
		boolean regstatus = false;
		ArrayList<HashMap<String, String>> allregs = null;
		HashMap<String, String> tmp = null;

		try {
			conn = this.dbobj.getConnection(ConnectionType.SYS);

			ps = conn.prepareStatement(this.cmd.addRegisters(), ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			ps.setString(1, serial_number);
			ps.setString(2, DCIString.strEncode(DCIDate.getToday("yyyyMMdd")));

			ps.executeUpdate();
			new APPubMethods().checkLicense();

			RegisterObject ro = RegisterObject.getInstance();
			allregs = ro.getAllSerials();

			for (int i = 0; i < allregs.size(); i++) {
				tmp = allregs.get(i);

				if (tmp.get("serial").equals(serial_number)) {
					regstatus = !tmp.get("status").equals("B");
					break;
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
			result.put("regstatus", regstatus);
			result.put("errorMessage", resultMsg);
		}
		return result;
	}

	public HashMap<String, Object> saveExecuteData(String serial_number, String ecode) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<HashMap<String, String>> allregs = null;
		HashMap<String, Object> result = null;
		HashMap<String, String> tmp = null;
		RegisterCenter rc = null;
		boolean boolResult = false;
		String resultMsg = "";
		String mkey = "";
		RegisterObject ro = RegisterObject.getInstance();
		boolean firstreg = false;
		boolean regstatus = false;

		try {
			rc = new RegisterCenter();
			mkey = new PublicMethods().getHardwareKey(ro.getGuardIP(), ro.getGuardPort());
			rc.setExecuteCode(serial_number, mkey);

			if (rc.verifyExecuteCode(ecode)) {
				conn = this.dbobj.getConnection(ConnectionType.SYS);

				ps = conn.prepareStatement(this.cmd.updExecute(), ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);

				ps.setString(1, ecode);
				ps.setString(2, "");
				ps.setString(3, serial_number);

				ps.executeUpdate();

				new APPubMethods().checkLicense();
			} else {
				allregs = ro.getAllSerials();

				for (int i = 0; i < allregs.size(); i++) {
					tmp = allregs.get(i);

					if (tmp.get("serial").equals(serial_number)) {
						firstreg = tmp.get("isReg").equals("1");
						break;
					}
				}

				if (firstreg) {
					conn = this.dbobj.getConnection(ConnectionType.SYS);

					ps = conn.prepareStatement(this.cmd.updExecute(), ResultSet.TYPE_SCROLL_INSENSITIVE,
							ResultSet.CONCUR_READ_ONLY);
					ps.setString(1, ecode);
					ps.setString(2, DCIString.strEncode(DCIDate.getToday("yyyyMMdd")));
					ps.setString(3, serial_number);

					ps.executeUpdate();

					new APPubMethods().checkLicense();
				}
			}
			allregs = ro.getAllSerials();
			for (int i = 0; i < allregs.size(); i++) {
				tmp = allregs.get(i);

				if (tmp.get("serial").equals(serial_number)) {
					resultMsg = transSerailStatus(tmp.get("status"));
					regstatus = tmp.get("status").equals("A");
					break;
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
			result.put("regstatus", regstatus);
			result.put("errorMessage", resultMsg);
		}
		return result;
	}

	private String transSerailStatus(String value) {
		String result = null;

		if (value.equals("A")) {
			result = "授權成功";
		} else if (value.equals("B")) {
			result = "過期";
		} else if (value.equals("C")) {
			result = "尚未授權";
		} else if (value.equals("D")) {
			result = "授權失敗";
		} else {
			result = "授權失敗";
		}

		return result;
	}
}
