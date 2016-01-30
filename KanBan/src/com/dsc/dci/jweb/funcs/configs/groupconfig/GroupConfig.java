package com.dsc.dci.jweb.funcs.configs.groupconfig;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;

import org.codehaus.jackson.map.ObjectMapper;

import com.dci.jweb.DCIEnums.Database.ConnectionType;
import com.dci.jweb.DataBaseLib.Database.DatabaseObjects;
import com.dci.jweb.PublicLib.DBControl;
import com.dci.jweb.PublicLib.DCIString;
import com.dsc.dci.jweb.pub.APConstants;
import com.dsc.dci.jweb.pub.Singleton;
import com.dsc.dci.sqlcode.configs.sqlGroupConfig;

public class GroupConfig {

	private sqlGroupConfig cmd;
	private DatabaseObjects dbobj;
	private DBControl dbctrl;

	public GroupConfig() {
		this.dbobj = DatabaseObjects.getInstance();
		this.cmd = new sqlGroupConfig();
		this.dbctrl = new DBControl();
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
						tmp.put("group_id", rs.getString("group_id"));
						tmp.put("group_name", rs.getString("group_name"));
						tmp.put("group_desc", rs.getString("group_desc"));
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
		String[] roles = null;
		String newid = null;

		try {
			datas = DCIString.jsonTranToObjMap(formDatas);//new ObjectMapper().readValue(formDatas, HashMap.class);
			conn = this.dbobj.getConnection(ConnectionType.SYS);

			if (mode.equals("Add")) {
				sql = this.cmd.addData();
			} else {
				sql = this.cmd.updateData();
			}
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			ps.setString(1, datas.get("group_name").toString());
			ps.setString(2, datas.get("visible").toString());
			ps.setString(3, datas.get("group_desc").toString());
			if (mode.equals("Add")) {
				newid = getMaxID(conn);
			} else {
				newid = datas.get("group_id").toString();
			}
			ps.setString(4, newid);
			ps.executeUpdate();

			if (!DCIString.isNullOrEmpty(datas.get("bodyvalue").toString())) {
				roles = datas.get("bodyvalue").toString().split(";");
			}
			groupRoleEditor(conn, newid, roles);
			resultdata = allData("1", "1", " and group_id = '" + newid + "'");

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
			datas = DCIString.jsonTranToObjMap(formdatas);//new ObjectMapper().readValue(formdatas, HashMap.class);
			conn = this.dbobj.getConnection(ConnectionType.SYS);

			groupRoleEditor(conn, datas.get("group_id").toString(), null);

			ps = conn.prepareStatement(this.cmd.deleteData(), ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			ps.setString(1, datas.get("group_id").toString());
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
					id = "G001";
				} else {
					id = id.substring(0, 1)
							+ DCIString.strFix(String.valueOf(Integer.parseInt(id.substring(1)) + 1), 3, true, "0");
				}
			} else {
				id = "G001";
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

	public HashMap<String, Object> getGroupRoles(String group_id) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		HashMap<String, Object> datas = null;
		ArrayList<HashMap<String, Object>> selected = null;
		ArrayList<HashMap<String, Object>> unselected = null;
		HashMap<String, Object> tmp = null;

		try {
			conn = this.dbobj.getConnection(ConnectionType.SYS);
			ps = conn.prepareStatement(this.cmd.groupRoles(), ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			ps.setString(1, group_id);
			ps.setString(2, group_id);
			rs = ps.executeQuery();

			selected = new ArrayList<HashMap<String, Object>>();
			unselected = new ArrayList<HashMap<String, Object>>();

			while (rs.next()) {
				tmp = new HashMap<String, Object>();
				tmp.put("role_id", rs.getString("role_id"));
				tmp.put("role_name", rs.getString("role_name"));
				tmp.put("role_desc", rs.getString("role_desc"));

				if (rs.getString("tp").equals("1")) {
					unselected.add(tmp);
				} else {
					selected.add(tmp);
				}
			}

			datas = new HashMap<String, Object>();
			datas.put("selected", selected);
			datas.put("unselected", unselected);

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			this.dbctrl.closeConnection(rs, ps, conn);
		}

		return datas;
	}

	private void groupRoleEditor(Connection conn, String group_id, String[] roles) throws Exception {
		PreparedStatement ps = null;
		boolean reconn = false;
		
		try {
			if (conn == null || conn.isClosed()) {
				conn = this.dbobj.getConnection(ConnectionType.SYS);
				reconn = true;
			}
			ps = conn.prepareStatement(this.cmd.deleteGroupRoleMapping(), ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			ps.setString(1, group_id);
			ps.executeUpdate();

			this.dbctrl.closeConnection(null, ps, null);
			if (roles != null && roles.length > 0) {
				ps = conn.prepareStatement(this.cmd.addGroupRoleMapping(), ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
				for (int i = 0; i < roles.length; i++) {
					ps.setString(1, group_id);
					ps.setString(2, roles[i]);
					ps.addBatch();

					if (i != 0 && (i % 100) == 0) {
						ps.executeBatch();
						ps.clearBatch();
					}
				}

				ps.executeBatch();
			}
		} finally {
			if (reconn) {
				this.dbctrl.closeConnection(null, ps, conn);
			} else {
				this.dbctrl.closeConnection(null, ps, null);
			}
		}
	}

	public HashMap<String, Object> copyGroup(String group_id, String group_name, String lang) {
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
				ps.setString(2, group_name);
				ps.setString(3, group_name);
				ps.setString(4, group_id);
				ps.executeUpdate();

				this.dbctrl.closeConnection(null, ps, null);
				ps = conn.prepareStatement(this.cmd.copyStep2());
				ps.setString(1, newid);
				ps.setString(2, group_id);
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
