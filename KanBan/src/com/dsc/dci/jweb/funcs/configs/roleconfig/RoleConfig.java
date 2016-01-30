package com.dsc.dci.jweb.funcs.configs.roleconfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import com.dci.jweb.DCIEnums.Database.ConnectionType;
import com.dci.jweb.DataBaseLib.Database.DatabaseObjects;
import com.dci.jweb.PublicLib.DBControl;
import com.dci.jweb.PublicLib.DCIString;
import com.dsc.dci.jweb.pub.APConstants;
import com.dsc.dci.jweb.pub.Singleton;
import com.dsc.dci.sqlcode.configs.sqlRoleConfig;

public class RoleConfig {
	private sqlRoleConfig cmd;
	private DatabaseObjects dbobj;
	private DBControl dbctrl;

	public RoleConfig() {
		this.dbobj = DatabaseObjects.getInstance();
		this.cmd = new sqlRoleConfig();
		this.dbctrl = new DBControl();
	}

	public HashMap<String, Object> getIntiData(String lang) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		HashMap<String, Object> datas = null;
		ArrayList<HashMap<String, Object>> conns = null;
		// ArrayList<HashMap<String, Object>> roles = null;
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
			// roles = new ArrayList<HashMap<String, Object>>();
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
			// datas.put("roles", roles);
			datas.put("funcs", funcs);

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			this.dbctrl.closeConnection(rs, ps, conn);
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
						tmp.put("role_id", rs.getString("role_id"));
						tmp.put("role_name", rs.getString("role_name"));
						tmp.put("role_desc", rs.getString("role_desc"));
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
					tmp = new HashMap<String, Object>();
					tmp.put("value", rsmd.getColumnName(i).toLowerCase());
					tmp.put("label", Singleton.getInstance().getLanguage(lang, rsmd.getColumnName(i).toLowerCase()));
					cols.add(tmp);
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

			ps.setString(1, datas.get("role_name").toString());
			ps.setString(2, datas.get("role_desc").toString());
			ps.setString(3, datas.get("visible").toString());
			if (mode.equals("Add")) {
				newid = getMaxID(conn);
			} else {
				newid = datas.get("role_id").toString();
			}
			ps.setString(4, newid);
			ps.executeUpdate();

			resultdata = allData("1", "1", " and role_id = '" + newid + "'");
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
			result.put("resultData", resultdata);
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
			deleteBodyWithHead(conn, datas.get("role_id").toString());
			ps = conn.prepareStatement(this.cmd.deleteData(), ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			ps.setString(1, datas.get("role_id").toString());
			ps.executeUpdate();
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

	private void deleteBodyWithHead(Connection conn, String role_id) throws Exception {
		boolean reconn = false;
		PreparedStatement ps = null;

		try {
			if (conn == null || conn.isClosed()) {
				conn = this.dbobj.getConnection(ConnectionType.SYS);
				reconn = true;
			}

			ps = conn.prepareStatement(this.cmd.bodyDelWithHead(), ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			ps.setString(1, role_id);
			ps.executeUpdate();
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		} finally {
			if (reconn) {
				this.dbctrl.closeConnection(null, ps, conn);
			} else {
				this.dbctrl.closeConnection(null, ps, null);
			}
		}
	}

	public HashMap<String, Object> getBodyData(String role_id) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		HashMap<String, Object> alldatas = null;
		ArrayList<HashMap<String, Object>> datas = null;
		HashMap<String, Object> tmp = null;

		try {
			conn = this.dbobj.getConnection(ConnectionType.SYS);
			ps = conn.prepareStatement(this.cmd.bodyData(), ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			ps.setString(1, role_id);
			rs = ps.executeQuery();

			datas = new ArrayList<HashMap<String, Object>>();

			while (rs.next()) {
				tmp = new HashMap<String, Object>();
				tmp.put("conn_id", rs.getString("conn_id"));
				tmp.put("role_id", rs.getString("role_id"));
				tmp.put("func_id", rs.getString("func_id"));
				tmp.put("func_package", rs.getString("package"));
				tmp.put("can_edit", rs.getString("can_edit").equals("1"));
				datas.add(tmp);
			}

			alldatas = new HashMap<String, Object>();
			alldatas.put("items", datas);

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			this.dbctrl.closeConnection(rs, ps, conn);
		}

		return alldatas;
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
				if (datas.get(i).get("moditp").toString().equals("add")) {
					ps = conn.prepareStatement(this.cmd.bodyAdd(), ResultSet.TYPE_SCROLL_INSENSITIVE,
							ResultSet.CONCUR_READ_ONLY);
				} else {
					ps = conn.prepareStatement(this.cmd.bodyUpd(), ResultSet.TYPE_SCROLL_INSENSITIVE,
							ResultSet.CONCUR_READ_ONLY);
				}

				if (Boolean.valueOf(datas.get(i).get("can_edit"))) {
					ps.setString(1, "1");
				} else {
					ps.setString(1, "0");
				}
				ps.setString(2, datas.get(i).get("conn_id").toString());
				ps.setString(3, datas.get(i).get("role_id").toString());
				ps.setString(4, datas.get(i).get("func_id").toString());
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
			datas = DCIString.jsonTranToObjMap(deldatas);// new
															// ObjectMapper().readValue(deldatas,
															// HashMap.class);
			conn = this.dbobj.getConnection(ConnectionType.SYS);
			ps = conn.prepareStatement(this.cmd.bodyDel(), ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			ps.setString(1, datas.get("conn_id").toString());
			ps.setString(2, datas.get("role_id").toString());
			ps.setString(3, datas.get("func_id").toString());
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
					id = "R001";
				} else {
					id = id.substring(0, 1)
							+ DCIString.strFix(String.valueOf(Integer.parseInt(id.substring(1)) + 1), 3, true, "0");
				}
			} else {
				id = "R001";
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

	public HashMap<String, Object> copyRole(String role_id, String role_name, String lang) {
		Connection conn = null;
		PreparedStatement ps = null;
		HashMap<String, Object> datas = null;
		String newid = null;

		try {
			conn = this.dbobj.getConnection(ConnectionType.SYS);
			newid = getMaxID(conn);

			if (DCIString.isNullOrEmpty(newid)) {
				datas = new HashMap<String, Object>();
				datas.put("success", false);
				datas.put("msg", Singleton.getInstance().getLanguage(lang, "get_newid_fail"));
			} else {
				ps = conn.prepareStatement(this.cmd.copyStep1());
				ps.setString(1, newid);
				ps.setString(2, role_name);
				ps.setString(3, role_name);
				ps.setString(4, role_id);
				ps.executeUpdate();

				this.dbctrl.closeConnection(null, ps, null);
				ps = conn.prepareStatement(this.cmd.copyStep2());
				ps.setString(1, newid);
				ps.setString(2, role_id);
				ps.executeUpdate();

				this.dbctrl.closeConnection(null, ps, null);
				datas = new HashMap<String, Object>();
				datas.put("success", true);
				datas.put("msg", newid);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			datas = new HashMap<String, Object>();
			datas.put("success", false);
			datas.put("msg", ex.getMessage());
		} finally {
			this.dbctrl.closeConnection(null, ps, conn);
		}
		return datas;
	}
}
