package com.dsc.dci.jweb.funcs.configs.marqueeconfig;

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
import com.dci.jweb.PublicLib.DCIDate;
import com.dci.jweb.PublicLib.DCIString;
import com.dsc.dci.jweb.pub.APConstants;
import com.dsc.dci.jweb.pub.Singleton;
import com.dsc.dci.sqlcode.configs.sqlMarqueeConfig;

public class MarqueeConfig {

	private sqlMarqueeConfig cmd;
	private DatabaseObjects dbobj;
	private DBControl dbctrl;

	public MarqueeConfig() {
		this.dbobj = DatabaseObjects.getInstance();
		this.cmd = new sqlMarqueeConfig();
		this.dbctrl = new DBControl();
	}

	public HashMap<String, Object> getAllData(String strpage, String strpagesize, String filter, String lang) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		HashMap<String, Object> datas = null;
		ArrayList<HashMap<String, Object>> items = null;
		HashMap<String, Object> tmp = null;
		HashMap<String, String> conns = null;
		HashMap<String, String> sqls = null;
		HashMap<String, String> dconns = null;
		HashMap<String, String> dsqls = null;
		HashMap<String, HashMap<String, String>> results = null;
		int cnt = 0;
		int page = 0;
		int pagesize = 0;
		int totalRow = 0;

		try {
			conn = this.dbobj.getConnection(ConnectionType.SYS);
			results = getConnAndSql(conn, lang);
			conns = results.get("conn");
			sqls = results.get("sql");
			dconns = results.get("dconn");
			dsqls = results.get("dsql");
			ps = conn.prepareStatement(this.cmd.getAllData(filter), ResultSet.TYPE_SCROLL_INSENSITIVE,
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
						tmp.put("marquee_id", rs.getString("marquee_id"));
						tmp.put("marquee_type", rs.getString("marquee_type"));
						tmp.put("message", rs.getString("message"));
						tmp.put("start_date", DCIString.dateFormat(rs.getString("start_date"), "yyyy/MM/dd HH:mm:ss"));
						tmp.put("end_date", DCIString.dateFormat(rs.getString("end_date"), "yyyy/MM/dd HH:mm:ss"));
						if (conns == null) {
							tmp.put("conns", "");
						} else {
							tmp.put("conns",
									conns.get(rs.getString("marquee_id")) == null ? "" : conns.get(rs
											.getString("marquee_id")));
						}

						if (sqls == null) {
							tmp.put("sqls", "");
						} else {
							tmp.put("sqls",
									sqls.get(rs.getString("marquee_id")) == null ? "" : sqls.get(rs
											.getString("marquee_id")));
						}
						if (dconns == null) {
							tmp.put("displayconns", "");
						} else {
							tmp.put("displayconns", dconns.get(rs.getString("marquee_id")));
						}

						if (dsqls == null) {
							tmp.put("displaysqls", "");
						} else {
							tmp.put("displaysqls", dsqls.get(rs.getString("marquee_id")));
						}
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

	private HashMap<String, HashMap<String, String>> getConnAndSql(Connection conn, String lang) {
		HashMap<String, HashMap<String, String>> datas = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		HashMap<String, String> result1 = null;
		HashMap<String, String> result2 = null;
		HashMap<String, String> result3 = null;
		HashMap<String, String> result4 = null;
		boolean reconn = false;
		String lastMarquee = null;
		String tmp = null;
		String dtmp = null;

		try {
			if (conn == null || conn.isClosed()) {
				conn = this.dbobj.getConnection(ConnectionType.SYS);
				reconn = true;
			}
			ps = conn.prepareStatement(this.cmd.getConns(), ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();
			datas = new HashMap<String, HashMap<String, String>>();
			while (rs.next()) {
				if (!DCIString.isNullOrEmpty(lastMarquee) && !rs.getString("marquee_id").equals(lastMarquee)) {
					if (result1 == null) {
						result1 = new HashMap<String, String>();
					}
					result1.put(lastMarquee, tmp);
					tmp = null;
					if (result3 == null) {
						result3 = new HashMap<String, String>();
					}
					result3.put(lastMarquee, dtmp);
					dtmp = null;
				}

				if (DCIString.isNullOrEmpty(tmp)) {
					tmp = rs.getString("conn_id");
				} else {
					tmp += ";" + rs.getString("conn_id");
				}

				if (DCIString.isNullOrEmpty(dtmp)) {
					dtmp = rs.getString("conn_name");
				} else {
					dtmp += ";" + rs.getString("conn_name");
				}

				lastMarquee = rs.getString("marquee_id");
			}

			if (!DCIString.isNullOrEmpty(tmp)) {
				if (result1 == null) {
					result1 = new HashMap<String, String>();
				}
				result1.put(lastMarquee, tmp);
				tmp = null;
				if (result3 == null) {
					result3 = new HashMap<String, String>();
				}
				result3.put(lastMarquee, dtmp);
				dtmp = null;
			}

			datas.put("conn", result1);
			datas.put("dconn", result3);
			lastMarquee = null;
			this.dbctrl.closeConnection(rs, ps, null);

			ps = conn.prepareStatement(this.cmd.getSqls(), ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();

			while (rs.next()) {
				if (!DCIString.isNullOrEmpty(lastMarquee) && !rs.getString("marquee_id").equals(lastMarquee)) {
					if (result2 == null) {
						result2 = new HashMap<String, String>();
					}
					result2.put(lastMarquee, tmp);
					tmp = null;
					if (result4 == null) {
						result4 = new HashMap<String, String>();
					}
					result4.put(lastMarquee, dtmp);
					dtmp = null;
				}

				if (DCIString.isNullOrEmpty(tmp)) {
					tmp = rs.getString("sql_id");
				} else {
					tmp += ";" + rs.getString("sql_id");
				}

				if (DCIString.isNullOrEmpty(dtmp)) {
					dtmp = Singleton.getInstance().getLanguage(lang, rs.getString("sql_name"));
				} else {
					dtmp += ";" + Singleton.getInstance().getLanguage(lang, rs.getString("sql_name"));
				}

				lastMarquee = rs.getString("marquee_id");
			}

			if (!DCIString.isNullOrEmpty(tmp)) {
				if (result2 == null) {
					result2 = new HashMap<String, String>();
				}
				result2.put(lastMarquee, tmp);
				tmp = null;
				if (result4 == null) {
					result4 = new HashMap<String, String>();
				}
				result4.put(lastMarquee, dtmp);
				dtmp = null;
			}
			datas.put("sql", result2);
			datas.put("dsql", result4);

		} catch (Exception ex) {
			ex.printStackTrace();
			datas = new HashMap<String, HashMap<String, String>>();
		} finally {
			if (reconn) {
				this.dbctrl.closeConnection(rs, ps, conn);
			} else {
				this.dbctrl.closeConnection(rs, ps, null);
			}
		}
		return datas;
	}

	public HashMap<String, Object> getQueryColumns(String lang, String tp) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		HashMap<String, Object> datas = null;
		ArrayList<HashMap<String, Object>> cols = null;
		HashMap<String, Object> tmp = null;

		try {
			conn = this.dbobj.getConnection(ConnectionType.SYS);
			if (tp.equals("headform")) {
				ps = conn.prepareStatement(this.cmd.getAllData(" and 1=2 "), ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
			} else if (tp.equals("connBtn")) {
				ps = conn.prepareStatement(this.cmd.getConnList(), ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
			} else if (tp.equals("sqlBtn")) {
				ps = conn.prepareStatement(this.cmd.getSqlList(), ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
			}
			rs = ps.executeQuery();
			if (rs != null) {
				ResultSetMetaData rsmd = rs.getMetaData();
				cols = new ArrayList<HashMap<String, Object>>();
				for (int i = 1; i <= rsmd.getColumnCount(); i++) {
					tmp = new HashMap<String, Object>();
					tmp.put("value", rsmd.getColumnName(i).toLowerCase());
					if (rsmd.getColumnName(i).toLowerCase().equals("message")) {
						tmp.put("label", Singleton.getInstance().getLanguage(lang, "marquee_msg"));
					} else {
						tmp.put("label", Singleton.getInstance().getLanguage(lang, rsmd.getColumnName(i).toLowerCase()));
					}
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

	public HashMap<String, Object> getOpenWinData(String tp, String lang) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		HashMap<String, Object> datas = null;
		ArrayList<HashMap<String, Object>> items = null;
		HashMap<String, Object> tmp = null;
		ResultSetMetaData rsmd = null;

		try {
			conn = this.dbobj.getConnection(ConnectionType.SYS);
			if (tp.equals("connBtn")) {
				ps = conn.prepareStatement(this.cmd.getConnList(), ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
			} else if (tp.equals("sqlBtn")) {
				ps = conn.prepareStatement(this.cmd.getSqlList(), ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
			}
			rs = ps.executeQuery();
			items = new ArrayList<HashMap<String, Object>>();

			if (rs != null) {
				rsmd = rs.getMetaData();
				while (rs.next()) {
					tmp = new HashMap<String, Object>();
					for (int i = 1; i <= rsmd.getColumnCount(); i++) {
						if (tp.equals("sqlBtn") && rsmd.getColumnName(i).toLowerCase().equals("sql_name")) {
							tmp.put(rsmd.getColumnName(i).toLowerCase(),
									Singleton.getInstance().getLanguage(lang,
											rs.getString(rsmd.getColumnName(i).toLowerCase())));
						} else {
							tmp.put(rsmd.getColumnName(i).toLowerCase(),
									rs.getString(rsmd.getColumnName(i).toLowerCase()));
						}
					}
					items.add(tmp);
				}
			}

			datas = new HashMap<String, Object>();
			datas.put("items", items);

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			this.dbctrl.closeConnection(rs, ps, conn);
		}

		// System.out.println("execute getAllUserInfo");

		return datas;
	}

	public HashMap<String, Object> getIntiData(String lang) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		HashMap<String, Object> datas = null;
		ArrayList<HashMap<String, Object>> conns = null;
		ArrayList<HashMap<String, Object>> sqls = null;
		HashMap<String, Object> tmp = null;

		try {
			conn = this.dbobj.getConnection(ConnectionType.SYS);
			ps = conn.prepareStatement(this.cmd.getConnList(), ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			rs = ps.executeQuery();
			conns = new ArrayList<HashMap<String, Object>>();

			while (rs.next()) {
				tmp = new HashMap<String, Object>();
				tmp.put("value", rs.getString("conn_id"));
				tmp.put("label", Singleton.getInstance().getLanguage(lang, rs.getString("conn_name")));
				conns.add(tmp);
			}

			this.dbctrl.closeConnection(rs, ps, null);
			ps = conn.prepareStatement(this.cmd.getSqlList(), ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			rs = ps.executeQuery();
			sqls = new ArrayList<HashMap<String, Object>>();

			while (rs.next()) {
				tmp = new HashMap<String, Object>();
				tmp.put("value", rs.getString("sql_id"));
				tmp.put("label", Singleton.getInstance().getLanguage(lang, rs.getString("sql_name")));
				sqls.add(tmp);
			}

			datas = new HashMap<String, Object>();
			datas.put("connList", conns);
			datas.put("sqlList", sqls);

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			this.dbctrl.closeConnection(rs, ps, conn);
		}

		return datas;
	}

	public HashMap<String, Object> saveData(String formDatas, String mode, String lang) {
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
				newid = getMaxID(conn);
			} else {
				sql = this.cmd.updateData();
				newid = datas.get("marquee_id").toString();
			}

			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			ps.setString(1, datas.get("marquee_type").toString());
			ps.setString(2, datas.get("message").toString());
			if (DCIString.isNullOrEmpty(datas.get("start_date").toString())) {
				ps.setString(4, "19110101000000");
			} else {
				ps.setString(3,
						DCIDate.parseString(DCIDate.parseDate(datas.get("start_date").toString()), "yyyyMMddHHmmss"));
			}
			if (DCIString.isNullOrEmpty(datas.get("end_date").toString())) {
				ps.setString(4, "29991231000000");
			} else {
				ps.setString(4,
						DCIDate.parseString(DCIDate.parseDate(datas.get("end_date").toString()), "yyyyMMddHHmmss"));
			}
			ps.setString(5, newid);
			ps.executeUpdate();

			connEditor(conn, newid, datas.get("conns").toString());
			if (datas.get("marquee_type").toString().equals("3") || datas.get("marquee_type").toString().equals("4")) {
				sqlEditor(conn, newid, null);
			} else {
				sqlEditor(conn, newid, datas.get("sqls").toString());
			}

			resultdata = getAllData("1", "1", " and marquee_id = '" + newid + "'", lang);
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

			ps.setString(1, datas.get("marquee_id").toString());
			ps.executeUpdate();

			connEditor(conn, datas.get("marquee_id").toString(), null);
			sqlEditor(conn, datas.get("marquee_id").toString(), null);
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
		String d = null;

		try {
			if (conn == null || conn.isClosed()) {
				conn = this.dbobj.getConnection(ConnectionType.SYS);
				reconn = true;
			}

			d = DCIDate.getToday("yyMMdd");

			ps = conn.prepareStatement(this.cmd.maxID(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps.setString(1, d + "%");
			rs = ps.executeQuery();
			if (rs.next()) {
				id = rs.getString(1);
				if (DCIString.isNullOrEmpty(id)) {
					id = d + "001";
				} else {
					id = id.substring(0, 6)
							+ DCIString.strFix(String.valueOf(Integer.parseInt(id.substring(6)) + 1), 3, true, "0");
				}
			} else {
				id = d + "001";
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

	private void connEditor(Connection conn, String marquee_id, String connstr) throws Exception {
		PreparedStatement ps = null;
		boolean reconn = false;

		try {
			if (conn == null || conn.isClosed()) {
				conn = this.dbobj.getConnection(ConnectionType.SYS);
				reconn = true;
			}
			ps = conn.prepareStatement(this.cmd.deleteConnWithHead(), ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			ps.setString(1, marquee_id);
			ps.executeUpdate();

			this.dbctrl.closeConnection(null, ps, null);
			if (!DCIString.isNullOrEmpty(connstr)) {
				String[] conns = connstr.split(";");
				ps = conn.prepareStatement(this.cmd.addConnMapping(), ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
				for (int i = 0; i < conns.length; i++) {
					ps.setString(1, marquee_id);
					ps.setString(2, conns[i]);
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

	private void sqlEditor(Connection conn, String marquee_id, String sqlstr) throws Exception {
		PreparedStatement ps = null;
		boolean reconn = false;

		try {
			if (conn == null || conn.isClosed()) {
				conn = this.dbobj.getConnection(ConnectionType.SYS);
				reconn = true;
			}
			ps = conn.prepareStatement(this.cmd.deleteSqlWithHead(), ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			ps.setString(1, marquee_id);
			ps.executeUpdate();

			this.dbctrl.closeConnection(null, ps, null);
			if (!DCIString.isNullOrEmpty(sqlstr)) {
				String[] sqls = sqlstr.split(";");
				ps = conn.prepareStatement(this.cmd.addSqlMapping(), ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
				for (int i = 0; i < sqls.length; i++) {
					ps.setString(1, marquee_id);
					ps.setString(2, sqls[i]);
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
}
