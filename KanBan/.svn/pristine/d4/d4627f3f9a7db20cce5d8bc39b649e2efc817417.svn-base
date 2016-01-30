package com.dsc.dci.jweb.funcs.configs.userconfig;

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
import com.dsc.dci.sqlcode.configs.sqlUserConfig;

public class UserConfig {

	private sqlUserConfig cmd;
	private DatabaseObjects dbobj;
	private DBControl dbctrl;

	public UserConfig() {
		this.dbobj = DatabaseObjects.getInstance();
		this.cmd = new sqlUserConfig();
		this.dbctrl = new DBControl();
	}

	public HashMap<String, Object> getAllUserInfo(String strpage, String strpagesize, String filter) {
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
			ps = conn.prepareStatement(this.cmd.getAllUserInfo(filter), ResultSet.TYPE_SCROLL_INSENSITIVE,
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
						tmp.put("user_id", rs.getString("user_id"));
						tmp.put("user_name", rs.getString("user_name"));
						tmp.put("user_pwd", DCIString.Base64Decode(rs.getString("user_pwd")));
						tmp.put("group_id", rs.getString("group_id"));
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
			ps = conn.prepareStatement(this.cmd.getAllUserInfo(" and 1=2 "), ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			rs = ps.executeQuery();
			if (rs != null) {
				ResultSetMetaData rsmd = rs.getMetaData();
				cols = new ArrayList<HashMap<String, Object>>();
				for (int i = 1; i <= rsmd.getColumnCount(); i++) {
					if (!rsmd.getColumnName(i).toLowerCase().equals("email")
							&& !rsmd.getColumnName(i).toLowerCase().equals("skype")
							&& !rsmd.getColumnName(i).toLowerCase().equals("cell_phone")) {
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

	public HashMap<String, Object> getIntiData(String lang) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		HashMap<String, Object> datas = null;
		ArrayList<HashMap<String, Object>> conns = null;
		ArrayList<HashMap<String, Object>> groups = null;
		ArrayList<HashMap<String, Object>> funcs = null;
		HashMap<String, Object> tmp = null;

		try {
			conn = this.dbobj.getConnection(ConnectionType.SYS);
			ps = conn.prepareStatement(this.cmd.getInitData(), ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			ps.setString(1, lang);

			rs = ps.executeQuery();
			conns = new ArrayList<HashMap<String, Object>>();
			groups = new ArrayList<HashMap<String, Object>>();
			funcs = new ArrayList<HashMap<String, Object>>();

			while (rs.next()) {
				tmp = new HashMap<String, Object>();
				if (rs.getString("tp").equals("1")) {
					tmp.put("value", rs.getString("value"));
					tmp.put("label", Singleton.getInstance().getLanguage(lang, rs.getString("label")));
					conns.add(tmp);
				} else if (rs.getString("tp").equals("2")) {
					tmp.put("value", rs.getString("value"));
					if (rs.getInt("visible") == 1) {
						tmp.put("label",
								rs.getString("label") + "(" + Singleton.getInstance().getLanguage(lang, "enable") + ")");
					} else {
						tmp.put("label",
								rs.getString("label") + "(" + Singleton.getInstance().getLanguage(lang, "disable")
										+ ")");
					}
					groups.add(tmp);
				} else if (rs.getString("tp").equals("3")) {
					tmp.put("value", rs.getString("value"));
					tmp.put("label", rs.getString("label"));
					tmp.put("package", rs.getString("package"));
					funcs.add(tmp);
				}
			}

			datas = new HashMap<String, Object>();
			datas.put("conns", conns);
			datas.put("groups", groups);
			datas.put("funcs", funcs);

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

			ps.setString(1, datas.get("user_name").toString());
			ps.setString(2, DCIString.Base64Encode(datas.get("user_pwd").toString()));
			ps.setString(3, datas.get("group_id").toString());
			ps.setString(4, datas.get("visible").toString());
			ps.setString(5, datas.get("user_id").toString());
			ps.executeUpdate();

			resultdata = getAllUserInfo("1", "1", " and user_id = '" + datas.get("user_id") + "'");

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
			ps = conn.prepareStatement(this.cmd.deleteData(), ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			ps.setString(1, datas.get("user_id").toString());
			ps.executeUpdate();
			deleteBodyWithHead(conn, datas.get("user_id").toString());
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

	private void deleteBodyWithHead(Connection conn, String user_id) throws Exception {
		boolean reconn = false;
		PreparedStatement ps = null;

		try {
			if (conn == null || conn.isClosed()) {
				conn = this.dbobj.getConnection(ConnectionType.SYS);
				reconn = true;
			}

			ps = conn.prepareStatement(this.cmd.bodyDelWithHead(), ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			ps.setString(1, user_id);
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
				tmp.put("user_id", rs.getString("user_id"));
				tmp.put("func_id", rs.getString("func_id"));
				tmp.put("can_edit", rs.getString("can_edit").equals("1"));
				tmp.put("func_package", rs.getString("package"));
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
				ps.setString(3, datas.get(i).get("user_id").toString());
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
			ps.setString(2, datas.get("user_id").toString());
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
}
