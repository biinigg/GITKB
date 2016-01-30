package com.dsc.dci.jweb.funcs.configs.connconfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;

import org.codehaus.jackson.map.ObjectMapper;

import com.dci.jweb.DCIBeans.Database.DBInfoBean;
import com.dci.jweb.DCIEnums.Database.ConnectionType;
import com.dci.jweb.DCIEnums.Database.DBType;
import com.dci.jweb.DataBaseLib.Database.DataDatabaseObject;
import com.dci.jweb.DataBaseLib.Database.Database;
import com.dci.jweb.DataBaseLib.Database.DatabaseObjects;
import com.dci.jweb.PublicLib.DBControl;
import com.dci.jweb.PublicLib.DCIString;
import com.dsc.dci.jweb.pub.APConstants;
import com.dsc.dci.jweb.pub.Singleton;
import com.dsc.dci.sqlcode.configs.sqlConnConfig;

public class ConnConfig {
	private sqlConnConfig cmd;
	private DatabaseObjects dbobj;
	private DBControl dbctrl;

	public ConnConfig() {
		this.dbobj = DatabaseObjects.getInstance();
		this.cmd = new sqlConnConfig();
		this.dbctrl = new DBControl();
	}

	public HashMap<String, Object> getIntiData() {
		HashMap<String, Object> datas = null;
		ArrayList<HashMap<String, Object>> dbtypes = null;
		HashMap<String, Object> tmp = null;

		try {
			dbtypes = new ArrayList<HashMap<String, Object>>();
			for (String value : DBType.StringValues()) {
				tmp = new HashMap<String, Object>();
				tmp.put("value", value);
				tmp.put("label", DBType.valueToLabel(value));
				// System.out.println(value + "---" +
				// DBType.valueOf(value).toString());
				dbtypes.add(tmp);
			}

			datas = new HashMap<String, Object>();
			datas.put("dbtypes", dbtypes);

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return datas;
	}

	public HashMap<String, Object> allData(String strpage, String strpagesize, String filter) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		HashMap<String, Object> datas = null;
		ArrayList<HashMap<String, Object>> items = null;
		HashMap<String, Object> tmp = null;
		int cnt = 0;
		int page = 0;
		int pagesize = 0;
		int totalRow = 0;
		try {
			conn = this.dbobj.getConnection(ConnectionType.SYS);
			ps = conn.prepareStatement(this.cmd.allData(filter), ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			rs = ps.executeQuery();
			items = new ArrayList<HashMap<String, Object>>();
			if (DCIString.isNullOrEmpty(strpage)) {
				page = 1;
			} else {
				page = Integer.parseInt(strpage);
			}
			if (DCIString.isNullOrEmpty(strpagesize)) {
				pagesize = APConstants.getDefaultpagesize();
			} else {
				pagesize = Integer.parseInt(strpagesize);
			}
			if (rs.next()) {
				rs.last();
				totalRow = rs.getRow();
				rs.beforeFirst();
				if ((page - 1) * pagesize <= totalRow) {
					if ((page - 1) * pagesize != 0) {
						rs.absolute((page - 1) * pagesize);
					}
					cnt = 1;
					while (rs.next()) {
						tmp = new HashMap<String, Object>();
						tmp.put("id", cnt);
						tmp.put("conn_id", rs.getString("conn_id"));
						tmp.put("conn_name", rs.getString("conn_name"));
						tmp.put("conn_desc", rs.getString("conn_desc"));
						tmp.put("db_addr", rs.getString("db_addr"));
						tmp.put("db_name", rs.getString("db_name"));
						tmp.put("db_user", rs.getString("db_user"));
						tmp.put("db_pwd", DCIString.Base64Decode(rs.getString("db_pwd")));
						tmp.put("db_type", rs.getString("db_type"));
						tmp.put("db_port", rs.getString("db_port"));
						tmp.put("visible", rs.getString("visible"));
						items.add(tmp);
						cnt++;
						if (cnt > pagesize) {
							break;
						}
					}
				}
			} else {
				totalRow = 0;
			}

			datas = new HashMap<String, Object>();
			datas.put("items", items);
			datas.put("total", totalRow);

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			this.dbctrl.closeConnection(rs, ps, conn);
		}

		// System.out.println("execute getAllUserInfo");

		return datas;
	}

	public HashMap<String, Object> getQueryColumns(String lang) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		HashMap<String, Object> datas = null;
		ArrayList<HashMap<String, Object>> cols = null;
		HashMap<String, Object> tmp = null;

		try {
			conn = this.dbobj.getConnection(ConnectionType.SYS);
			ps = conn.prepareStatement(this.cmd.allData(" and 1=2 "), ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			rs = ps.executeQuery();
			if (rs != null) {
				ResultSetMetaData rsmd = rs.getMetaData();
				cols = new ArrayList<HashMap<String, Object>>();
				for (int i = 1; i <= rsmd.getColumnCount(); i++) {
					if (!rsmd.getColumnName(i).toLowerCase().equals("db_pwd")) {
						tmp = new HashMap<String, Object>();
						tmp.put("value", rsmd.getColumnName(i).toLowerCase());
						tmp.put("label", Singleton.getInstance().getLanguage(lang, rsmd.getColumnName(i).toLowerCase()));
						cols.add(tmp);
					}
				}
			}

			datas = new HashMap<String, Object>();
			datas.put("cols", cols);

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			this.dbctrl.closeConnection(rs, ps, conn);
		}

		return datas;
	}

	public HashMap<String, Object> connCheck(String formDatas) {
		HashMap<String, Object> datas = null;
		HashMap<String, Object> result = null;

		try {
			datas = DCIString.jsonTranToObjMap(formDatas);// new
															// ObjectMapper().readValue(formDatas,
															// HashMap.class);
			new Database().testConnedtion(DBType.valueOf(datas.get("db_type").toString()), datas.get("db_addr")
					.toString(), datas.get("db_port").toString(), datas.get("db_name").toString(), datas.get("db_user")
					.toString(), datas.get("db_pwd").toString());
			result = new HashMap<String, Object>();
			result.put("success", true);
			result.put("msg", "");

		} catch (Exception ex) {
			ex.printStackTrace();
			if (result == null) {
				result = new HashMap<String, Object>();
			}
			result.put("success", false);
			result.put("msg", ex.getMessage());
		}

		return result;
	}

	public HashMap<String, Object> nameCheck(String id, String name, String lang) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		HashMap<String, Object> result = null;

		try {
			conn = this.dbobj.getConnection(ConnectionType.SYS);
			ps = conn.prepareStatement(this.cmd.nameCheck(), ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			ps.setString(1, id);
			ps.setString(2, name);
			rs = ps.executeQuery();
			result = new HashMap<String, Object>();
			if (rs.next()) {
				result.put("success", false);
				result.put("msg", Singleton.getInstance().getLanguage(lang, "conn_name_exist"));
			} else {
				result.put("success", true);
				result.put("msg", "");
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			if (result == null) {
				result = new HashMap<String, Object>();
			}
			result.put("success", false);
			result.put("msg", ex.getMessage());
		} finally {
			this.dbctrl.closeConnection(rs, ps, conn);
		}

		return result;
	}

	public HashMap<String, Object> saveData(String formDatas, String mode) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		HashMap<String, Object> datas = null;
		HashMap<String, Object> result = null;
		HashMap<String, Object> resultdata = null;
		String sql = null;
		boolean boolResult = false;
		String resultMsg = "";
		String newid = null;

		try {
			datas = DCIString.jsonTranToObjMap(formDatas);// new
															// ObjectMapper().readValue(formDatas,
															// HashMap.class);
			conn = this.dbobj.getConnection(ConnectionType.SYS);
			if (mode.equals("Add")) {
				sql = this.cmd.addData();
			} else {
				sql = this.cmd.updateData();
			}
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			ps.setString(1, datas.get("conn_name").toString());
			ps.setString(2, datas.get("conn_desc").toString());
			ps.setString(3, datas.get("db_addr").toString());
			ps.setString(4, datas.get("db_name").toString());
			ps.setString(5, datas.get("db_user").toString());
			ps.setString(6, DCIString.Base64Encode(datas.get("db_pwd").toString()));
			ps.setString(7, datas.get("db_type").toString());
			ps.setString(8, datas.get("db_port").toString());
			ps.setString(9, datas.get("visible").toString());
			if (mode.equals("Add")) {
				newid = getMaxID(conn);
			} else {
				newid = datas.get("conn_id").toString();
			}
			ps.setString(10, newid);
			ps.executeUpdate();

			if (datas.get("visible").toString().equals("1")) {
				DBInfoBean infos = null;

				infos = this.dbobj.copySysDBConfigSetting();

				if (infos != null) {
					infos.setConnID(newid);
					infos.setDBAddr(datas.get("db_addr").toString());
					infos.setDBPort(datas.get("db_port").toString());
					infos.setDBName(datas.get("db_name").toString());
					infos.setDBType(DBType.valueOf(datas.get("db_type").toString()));
					infos.setUserName(datas.get("db_user").toString());
					infos.setPassword(datas.get("db_pwd").toString());
					modiDataConnPool(newid, infos, false);
				}
			} else {
				modiDataConnPool(newid, null, true);
			}
			resultdata = allData("1", "1", " and conn_id = '" + newid + "'");
			boolResult = true;
		} catch (Exception ex) {
			ex.printStackTrace();
			boolResult = false;
			resultMsg = ex.getMessage();
		} finally {
			this.dbctrl.closeConnection(rs, ps, conn);
			result = new HashMap<String, Object>();
			result.put("success", boolResult);
			result.put("resultData", resultdata);
			result.put("errorMessage", resultMsg);
		}
		return result;
	}

	public HashMap<String, Object> deleteData(String formdatas) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		HashMap<String, Object> datas = null;
		HashMap<String, Object> result = null;
		boolean boolResult = false;
		String resultMsg = "";
		try {
			datas = DCIString.jsonTranToObjMap(formdatas);// new
															// ObjectMapper().readValue(formdatas,
															// HashMap.class);
			conn = this.dbobj.getConnection(ConnectionType.SYS);
			ps = conn.prepareStatement(this.cmd.deleteData(), ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			ps.setString(1, datas.get("conn_id").toString());
			ps.executeUpdate();
			modiDataConnPool(datas.get("conn_id").toString(), null, true);
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

	private String getMaxID(Connection conn) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		String id = null;
		boolean reconn = false;
		try {
			if (conn == null || conn.isClosed()) {
				conn = this.dbobj.getConnection(ConnectionType.SYS);
				reconn = true;
			}
			ps = conn.prepareStatement(this.cmd.maxID(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			rs = ps.executeQuery();
			if (rs.next()) {
				id = rs.getString(1);
				if (DCIString.isNullOrEmpty(id)) {
					id = "C001";
				} else {
					id = id.substring(0, 1)
							+ DCIString.strFix(String.valueOf(Integer.parseInt(id.substring(1)) + 1), 3, true, "0");
				}
			} else {
				id = "C001";
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			id = "";
		} finally {
			if (reconn) {
				this.dbctrl.closeConnection(rs, ps, conn);
			} else {
				this.dbctrl.closeConnection(rs, ps, null);
			}
		}
		return id;
	}

	private void modiDataConnPool(String conn_id, DBInfoBean infos, boolean justRemove) {
		DataDatabaseObject ddobj = DataDatabaseObject.getInstance();
		if (!DCIString.isNullOrEmpty(conn_id)) {
			ddobj.removeDataSource(conn_id);
			if (!justRemove) {
				ddobj.createDataDataSource(infos, conn_id);
			}
		}
	}
}
