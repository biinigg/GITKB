package com.dsc.dci.jweb.funcs.configs.paramsconfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import com.dci.jweb.DCIEnums.Database.ConnectionType;
import com.dci.jweb.DataBaseLib.Database.DatabaseObjects;
import com.dci.jweb.PublicLib.DBControl;
import com.dci.jweb.PublicLib.DCIString;
import com.dsc.dci.jweb.pub.APConstants;
import com.dsc.dci.jweb.pub.APPubMethods;
import com.dsc.dci.sqlcode.configs.sqlParamsConfig;

public class ParamsConfig {

	private sqlParamsConfig cmd;
	private DatabaseObjects dbobj;
	private DBControl dbctrl;

	public ParamsConfig() {
		this.dbobj = DatabaseObjects.getInstance();
		this.cmd = new sqlParamsConfig();
		this.dbctrl = new DBControl();
	}

	public HashMap<String, Object> getAllDatas() {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		HashMap<String, Object> datas = null;
		ArrayList<HashMap<String, Object>> items = null;
		HashMap<String, Object> tmp = null;

		try {
			conn = this.dbobj.getConnection(ConnectionType.SYS);
			ps = conn.prepareStatement(this.cmd.getAllDatas(), ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			rs = ps.executeQuery();
			items = new ArrayList<HashMap<String, Object>>();

			while (rs.next()) {
				tmp = new HashMap<String, Object>();
				tmp.put("config_id", rs.getString("config_id"));
				if (rs.getString("config_id").equals("UserTimeOut")) {
					tmp.put("config_value",
							DCIString.secondsToMinutes(String.valueOf(rs.getInt("config_value") / 1000)));
				} else {
					tmp.put("config_value", rs.getString("config_value"));
				}
				tmp.put("config_desc", rs.getString("config_desc"));
				items.add(tmp);
			}

			datas = new HashMap<String, Object>();
			datas.put("items", items);

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			this.dbctrl.closeConnection(rs, ps, conn);
		}

		return datas;
	}

	public HashMap<String, Object> saveBody(String deldatas) {
		Connection conn = null;
		PreparedStatement ps = null;
		ArrayList<HashMap<String, String>> datas = null;
		HashMap<String, Object> result = null;
		boolean boolResult = false;
		String resultMsg = "";

		try {
			datas = new ObjectMapper().readValue(deldatas, new TypeReference<ArrayList<HashMap<String, String>>>() {
			});
			conn = this.dbobj.getConnection(ConnectionType.SYS);

			for (int i = 0; i < datas.size(); i++) {
				ps = conn.prepareStatement(this.cmd.updDatas(), ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
				if (datas.get(i).get("config_id").toString().equals("UserTimeOut")) {
					ps.setString(
							1,
							DCIString.minutesToSeconds(String.valueOf(Integer.parseInt(datas.get(i).get("config_value")
									.toString()) * 1000)));
				} else {
					ps.setString(1, datas.get(i).get("config_value").toString());
				}
				ps.setString(2, datas.get(i).get("config_id").toString());
				ps.executeUpdate();
				this.dbctrl.closeConnection(null, ps, null);
			}
			new APPubMethods().loadSystemConfig();
			boolResult = true;
		} catch (Exception ex) {
			ex.printStackTrace();
			boolResult = false;
			resultMsg = ex.getMessage();
		} finally {
			this.dbctrl.closeConnection(null, ps, conn);
			result = new HashMap<String, Object>();
			result.put("success", boolResult);
			result.put("errorMessage", resultMsg);
		}
		return result;
	}

}
