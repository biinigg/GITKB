package com.dsc.dci.jweb.funcs.configs.pe000config;

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
import com.dsc.dci.jweb.pub.Singleton;
import com.dsc.dci.sqlcode.configs.sqlPE000Config;

public class PE000Config {

	private sqlPE000Config cmd;
	private DatabaseObjects dbobj;
	private DBControl dbctrl;

	public PE000Config() {
		this.dbobj = DatabaseObjects.getInstance();
		this.cmd = new sqlPE000Config();
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
				tmp.put("pid", rs.getString("report_id"));
				tmp.put("report_id", rs.getString("report_id"));	
				tmp.put("report_name", rs.getString("report_name"));
				tmp.put("dept_name", rs.getString("dept_name"));
				tmp.put("owner", rs.getString("owner"));
				tmp.put("sequ_num", rs.getString("sequ_num"));
				tmp.put("process_id", rs.getString("process_id"));
				tmp.put("process_name", rs.getString("process_name"));
				tmp.put("is_work", rs.getString("is_work").equals("Y"));
				tmp.put("schedule_time", rs.getString("schedule_time"));
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
				if(datas.get(i).get("report_id").toString().trim()==null||datas.get(i).get("report_id").toString().trim().equals("")){
					continue;
				}
				if (datas.get(i).get("moditp").toString().equals("add")) {
					ps = conn.prepareStatement(this.cmd.bodyAdd(), ResultSet.TYPE_SCROLL_INSENSITIVE,
							ResultSet.CONCUR_READ_ONLY);
				}else {
					ps = conn.prepareStatement(this.cmd.bodyUpd(), ResultSet.TYPE_SCROLL_INSENSITIVE,
							ResultSet.CONCUR_READ_ONLY);
					ps.setString(10, datas.get(i).get("pid").toString());
				}
				ps.setString(1, datas.get(i).get("sequ_num").toString());
				ps.setString(2, datas.get(i).get("process_id").toString());
				ps.setString(3, datas.get(i).get("process_name").toString());
				ps.setString(4, datas.get(i).get("is_work").toString());
				if (Boolean.valueOf(datas.get(i).get("is_work").toString())) {
					ps.setString(4, "Y");
				} else {
					ps.setString(4, "N");
				}
				ps.setString(5, datas.get(i).get("report_id").toString());
				ps.setString(6, datas.get(i).get("report_name").toString());
				ps.setString(7, datas.get(i).get("dept_name").toString());
				ps.setString(8, datas.get(i).get("owner").toString());
				ps.setString(9, datas.get(i).get("schedule_time").toString());
				ps.executeUpdate();
				this.dbctrl.closeConnection(null, ps, null);
			}
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
	public HashMap<String, Object> deleteBody(String deldatas) {
		Connection conn = null;
		PreparedStatement ps = null;
		HashMap<String, Object> datas = null;
		HashMap<String, Object> result = null;
		boolean boolResult = false;
		String resultMsg = "";
		try {
			datas = new ObjectMapper().readValue(deldatas, HashMap.class);
			conn = this.dbobj.getConnection(ConnectionType.SYS);
			ps = conn.prepareStatement(this.cmd.bodyDel(), ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			ps.setString(1, datas.get("report_id").toString());
			ps.executeUpdate();
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
	
	public HashMap<String, Object> getIntiData(String lang) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		HashMap<String, Object> datas = null;
		ArrayList<HashMap<String, Object>> conns = null;
		ArrayList<HashMap<String, Object>> funcs = null;
		HashMap<String, Object> tmp = null;
		Singleton s = Singleton.getInstance();
		try {
			conn = this.dbobj.getConnection(ConnectionType.SYS);
			ps = conn.prepareStatement(this.cmd.getInitData(), ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			ps.setString(1, lang);
			rs = ps.executeQuery();

			conns = new ArrayList<HashMap<String, Object>>();
			funcs = new ArrayList<HashMap<String, Object>>();

			while (rs.next()) {
				tmp = new HashMap<String, Object>();
				tmp.put("value", rs.getString("value"));
				if (rs.getString("tp").equals("1")) {
					tmp.put("label", s.getLanguage(lang, rs.getString("label")));
					conns.add(tmp);
				} else if (rs.getString("tp").equals("2")) {
					tmp.put("label", rs.getString("label"));
					tmp.put("package", rs.getString("package"));
					funcs.add(tmp);
				}
			}

			datas = new HashMap<String, Object>();
			datas.put("conns", conns);
			datas.put("funcs", funcs);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			this.dbctrl.closeConnection(rs, ps, conn);
		}

		return datas;
	}

}
