package com.dsc.dci.jweb.funcs.configs.sqleditor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import com.dci.jweb.DCIConstants.DCIWebConstants;
import com.dci.jweb.DCIEnums.Database.ConnectionType;
import com.dci.jweb.DCIEnums.Database.CrossDBType;
import com.dci.jweb.DCIEnums.Database.DBType;
import com.dci.jweb.DataBaseLib.Database.DataDatabaseObject;
import com.dci.jweb.DataBaseLib.Database.DatabaseFuncs;
import com.dci.jweb.DataBaseLib.Database.DatabaseObjects;
import com.dci.jweb.PublicLib.APControl;
import com.dci.jweb.PublicLib.DBControl;
import com.dci.jweb.PublicLib.DCIString;
import com.dsc.dci.jweb.apenums.ColumnType;
import com.dsc.dci.jweb.pub.APConstants;
import com.dsc.dci.jweb.pub.APPubMethods;
import com.dsc.dci.jweb.pub.Singleton;
import com.dsc.dci.sqlcode.configs.sqlSQLEditor;

public class SQLEditor {
	private sqlSQLEditor cmd;
	private DatabaseObjects dbobj;
	private DBControl dbctrl;

	public SQLEditor() {
		this.dbobj = DatabaseObjects.getInstance();
		this.cmd = new sqlSQLEditor();
		this.dbctrl = new DBControl();
	}

	public HashMap<String, Object> getAllData(String strpage, String strpagesize, String filter, String lang) {
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

			ps = conn.prepareStatement(this.cmd.getAllData(filter), ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			ps.setString(1, lang);
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
						tmp.put("sql_id", rs.getString("sql_id"));
						tmp.put("sql_name", Singleton.getInstance().getLanguage(lang, rs.getString("sql_name")));
						tmp.put("sql_context", rs.getString("sql_context"));
						tmp.put("group_by", rs.getString("group_by"));
						tmp.put("order_by", rs.getString("order_by"));
						tmp.put("legend", buildLegendLangs(rs.getString("sql_id"), rs.getString("legend"), lang));
						tmp.put("legend_lang", buildLegendLangs(rs.getString("sql_id"), rs.getString("legend"), null));
						tmp.put("auto_refresh",
								rs.getString("auto_refresh") == null ? false : rs.getString("auto_refresh").equals("1"));
						tmp.put("refresh_gap", DCIString.secondsToMinutes(rs.getString("refresh_gap")));
						tmp.put("use_marquee", rs.getString("use_marquee") == null ? false : rs
								.getString("use_marquee").equals("1"));
						tmp.put("marquee_location", rs.getString("marquee_location"));
						tmp.put("marquee_type", rs.getString("marquee_type"));
						tmp.put("marquee_refresh_gap", rs.getString("marquee_refresh_gap"));
						tmp.put("use_popup", rs.getString("use_popup") == null ? false : rs.getString("use_popup")
								.equals("1"));
						tmp.put("popup_dir", rs.getString("popup_dir"));
						tmp.put("popup_refresh_gap", rs.getString("popup_refresh_gap"));
						// tmp.put("cross_conn_id",
						// rs.getString("cross_conn_id"));
						// tmp.put("cross_sql_context",
						// rs.getString("cross_sql_context"));
						// tmp.put("cross_group_by",
						// rs.getString("cross_group_by"));
						// tmp.put("cross_order_by",
						// rs.getString("cross_order_by"));
						// tmp.put("cross_type", rs.getString("cross_type"));
						// tmp.put("join_key_set1",
						// rs.getString("join_key_set1"));
						// tmp.put("join_key_set2",
						// rs.getString("join_key_set2"));
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

	private String buildLegendLangs(String sql_id, String legend, String lang) {
		String result = null;

		if (DCIString.isNullOrEmpty(legend)) {
			result = "";
		} else {
			String[] icons = null;
			Singleton s = Singleton.getInstance();
			String iconid = null;
			String iconKey = null;
			String iconDesc = null;
			try {
				icons = legend.split(";");
				for (int i = 0; i < icons.length; i++) {
					if (icons[i].indexOf("=") == -1) {
						iconid = icons[i];
					} else {
						iconid = icons[i].split("=")[0];
					}

					iconKey = sql_id + "_legend_" + iconid;
					if (DCIString.isNullOrEmpty(lang)) {
						if (DCIString.isNullOrEmpty(result)) {
							result = iconid + "=" + s.getLanguage("CHS", iconKey) + "^" + s.getLanguage("CHT", iconKey);
						} else {
							result += ";" + iconid + "=" + s.getLanguage("CHS", iconKey) + "^"
									+ s.getLanguage("CHT", iconKey);
						}
					} else {
						iconDesc = s.getLanguage(lang, iconKey);
						if (!iconDesc.equals(iconKey)) {
							if (DCIString.isNullOrEmpty(result)) {
								result = iconid + "=" + s.getLanguage(lang, iconDesc);
							} else {
								result += ";" + iconid + "=" + s.getLanguage(lang, iconDesc);
							}
						}
					}
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}

		}
		return result;
	}

	public HashMap<String, Object> getOpenWinData(String filter, String action) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		HashMap<String, Object> datas = null;
		ArrayList<HashMap<String, Object>> items = null;
		HashMap<String, Object> tmp = null;

		try {
			conn = this.dbobj.getConnection(ConnectionType.SYS);
			ps = conn.prepareStatement(this.cmd.openWinData(filter), ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			rs = ps.executeQuery();
			items = new ArrayList<HashMap<String, Object>>();

			while (rs.next()) {
				tmp = new HashMap<String, Object>();
				if (action.equals("connlist")) {
					tmp.put("value", rs.getString("conn_id"));
					tmp.put("label", rs.getString("conn_name"));
				} else {
					tmp.put("conn_id", rs.getString("conn_id"));
					tmp.put("conn_name", rs.getString("conn_name"));
				}
				items.add(tmp);
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
				ps.setString(1, lang);
			} else {
				ps = conn.prepareStatement(this.cmd.openWinData(" and 1=2 "), ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
			}
			rs = ps.executeQuery();
			if (rs != null) {
				ResultSetMetaData rsmd = rs.getMetaData();
				cols = new ArrayList<HashMap<String, Object>>();
				for (int i = 1; i <= rsmd.getColumnCount(); i++) {

					if (!rsmd.getColumnName(i).toLowerCase().startsWith("cross_")
							&& !rsmd.getColumnName(i).toLowerCase().startsWith("join_")
							&& !rsmd.getColumnName(i).toLowerCase().equals("order_by")
							&& !rsmd.getColumnName(i).toLowerCase().equals("group_by")
							&& !rsmd.getColumnName(i).toLowerCase().equals("lang_value")) {
						tmp = new HashMap<String, Object>();
						tmp.put("value", rsmd.getColumnName(i).toLowerCase());
						if (rsmd.getColumnName(i).toLowerCase().equals("sql_context")) {
							tmp.put("label", Singleton.getInstance().getLanguage(lang, "SQL"));
						} else {
							tmp.put("label",
									Singleton.getInstance().getLanguage(lang, rsmd.getColumnName(i).toLowerCase()));
						}
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

	public HashMap<String, Object> sqlCheck(String sql_text, String group_by, String order_by, String conn_id,
			String lang) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		HashMap<String, Object> datas = null;
		String sql_cmd = null;
		String[] connids = null;
		boolean colRepeat = false;
		boolean orisqlok = false;

		try {
			if (DCIString.isNullOrEmpty(conn_id)) {
				throw new Exception("connection id is null or empty");
			}

			connids = conn_id.split(";");
			if (connids != null && connids.length > 0) {
				conn_id = connids[0];
			}
			conn = DataDatabaseObject.getInstance().getConnection(conn_id);

			sql_cmd = sql_text;

			if (!DCIString.isNullOrEmpty(group_by)) {
				sql_cmd += "\r\n group by " + group_by;
			}

			if (!DCIString.isNullOrEmpty(order_by)) {
				sql_cmd += "\r\n order by " + order_by;
			}
			ps = conn.prepareStatement(sql_cmd, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();
			orisqlok = true;
			sql_cmd = null;
			this.dbctrl.closeConnection(rs, ps, null);

			sql_cmd = sql_text + "\r\n and 1=2 ";

			if (!DCIString.isNullOrEmpty(group_by)) {
				sql_cmd += "\r\n group by " + group_by;
			}

			if (!DCIString.isNullOrEmpty(order_by)) {
				sql_cmd += " order by " + order_by;
			}

			ps = conn.prepareStatement(sql_cmd, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();

			ResultSetMetaData rsmd = rs.getMetaData();
			HashMap<String, Integer> tmp = new HashMap<String, Integer>();

			for (int i = 1; i <= rsmd.getColumnCount(); i++) {
				if (tmp.containsKey(rsmd.getColumnName(i))) {
					colRepeat = true;
					break;
				} else {
					tmp.put(rsmd.getColumnName(i), i);
				}
			}

			datas = new HashMap<String, Object>();
			if (colRepeat) {
				datas.put("success", false);
				datas.put("msg", Singleton.getInstance().getLanguage(lang, "col_repeat"));
			} else {
				datas.put("success", true);
				datas.put("msg", "");
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			if (datas == null) {
				datas = new HashMap<String, Object>();
			}
			datas.put("success", false);
			if (orisqlok) {
				datas.put("msg", Singleton.getInstance().getLanguage(lang, "need_end_with_where"));
			} else {
				datas.put("msg", ex.getMessage());
			}
		} finally {
			this.dbctrl.closeConnection(rs, ps, conn);
		}

		return datas;
	}

	public HashMap<String, Object> getSqlConns(String sql_id) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		HashMap<String, Object> datas = null;
		ArrayList<HashMap<String, Object>> items = null;
		HashMap<String, Object> tmp = null;
		String conns = "";

		try {
			conn = this.dbobj.getConnection(ConnectionType.SYS);
			ps = conn.prepareStatement(this.cmd.getSqlConns(), ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			ps.setString(1, sql_id);
			rs = ps.executeQuery();
			items = new ArrayList<HashMap<String, Object>>();

			while (rs.next()) {
				if (DCIString.isNullOrEmpty(conns)) {
					conns = rs.getString("conn_id");
				} else {
					conns += ";" + rs.getString("conn_id");
				}
			}
			tmp = new HashMap<String, Object>();
			tmp.put("conns", conns);
			items.add(tmp);

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

	public HashMap<String, Object> getInitData(String lang) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		HashMap<String, Object> datas = null;
		ArrayList<HashMap<String, Object>> formats = null;
		HashMap<String, Object> tmp = null;
		String[] datelist = new String[] { "yyyy-MM-dd", "yyyy/MM/dd", "MM-dd", "MM/dd", "yyyy-MM-dd HH:mm:ss",
				"yyyy/MM/dd HH:mm:ss", "MM-dd-yyyy", "MM/dd/yyyy", "dd-MM-yyyy", "dd/MM/yyyy", "yyyyMMdd" };

		try {
			formats = new ArrayList<HashMap<String, Object>>();
			for (int i = 0; i < datelist.length; i++) {
				tmp = new HashMap<String, Object>();
				tmp.put("value", datelist[i].toString());
				tmp.put("label", dateFormatLangTrans(lang, datelist[i]));

				formats.add(tmp);
			}

			datas = new HashMap<String, Object>();
			datas.put("coltps", ColumnType.getComboData(lang));
			datas.put("formats", formats);

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			this.dbctrl.closeConnection(rs, ps, conn);
		}

		return datas;
	}

	private String dateFormatLangTrans(String lang, String format) {
		String[] lkeys = new String[] { "yyyy", "MM", "dd", "HH", "mm", "ss" };

		for (int i = 0; i < lkeys.length; i++) {
			format = format.replace(lkeys[i], Singleton.getInstance().getLanguage(lang, lkeys[i]));
		}
		return format;
	}

	public HashMap<String, Object> saveData(String formDatas, String mode, String lang) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		HashMap<String, Object> datas = null;
		HashMap<String, Object> result = null;
		HashMap<String, String> crossInfo = null;
		ArrayList<HashMap<String, String>> crossInfo2 = null;
		HashMap<String, Object> resultdata = null;
		String sql = null;
		boolean boolResult = false;
		String resultMsg = "";
		String newid = null;
		String[] conns = null;

		try {
			datas = DCIString.jsonTranToObjMap(formDatas);// new
															// ObjectMapper().readValue(formDatas,
															// HashMap.class);
			conn = this.dbobj.getConnection(ConnectionType.SYS);

			conns = datas.get("conndatas").toString().split(";");

			if (mode.equals("Add")) {
				sql = this.cmd.addData();
				newid = getMaxID(conn);
			} else {
				sql = this.cmd.updateData();
				newid = datas.get("sql_id").toString();
			}

			sqlConnEditor(conn, newid, conns);
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			ps.setString(1, newid);
			ps.setString(2, datas.get("sql_context").toString());
			ps.setString(3, datas.get("group_by").toString());
			ps.setString(4, datas.get("order_by").toString());
			ps.setString(5, saveLegendLangs(conn, newid, datas.get("legend_lang").toString()));
			// ps.setString(6,
			// Boolean.parseBoolean(datas.get("use_marquee").toString()) ? "1" :
			// "0");
			ps.setString(6, datas.get("use_marquee").toString());
			ps.setString(7, datas.get("marquee_location").toString());
			ps.setString(8, datas.get("marquee_type").toString());
			ps.setString(9, datas.get("marquee_refresh_gap").toString());
			// ps.setString(10,
			// Boolean.parseBoolean(datas.get("auto_refresh").toString()) ? "1"
			// : "0");
			ps.setString(10, datas.get("auto_refresh").toString());
			ps.setString(11, DCIString.minutesToSeconds(datas.get("refresh_gap").toString()));
			// ps.setString(12,
			// Boolean.parseBoolean(datas.get("use_popup").toString()) ? "1" :
			// "0");
			ps.setString(12, datas.get("use_popup").toString());
			ps.setString(13, datas.get("popup_dir").toString());
			ps.setString(14, datas.get("popup_refresh_gap").toString());
			ps.setString(15, newid);
			ps.executeUpdate();

			cusMenuEditor(conn, newid, true);
			adminRoleEditor(conn, newid, conns);
			String[][] langs = new String[2][];
			langs[0] = new String[] { "CHT", newid, datas.get("sql_name").toString() };
			langs[1] = new String[] { "CHS", newid, datas.get("sql_name").toString() };
			saveLangs(conn, langs);
			if (datas.get("sql_cross") != null) {
				if (!DCIString.isNullOrEmpty(datas.get("sql_cross").toString())) {
					// crossInfo =
					// DCIString.jsonTranToStrMap(datas.get("sql_cross").toString());
					// new
					// ObjectMapper().readValue(datas.get("sql_cross").toString(),
					// HashMap.class);
					crossInfo2 = (ArrayList<HashMap<String, String>>) DCIString.jsonTranToObjMap(datas.get("sql_cross")
							.toString(), new ArrayList<HashMap<String, String>>().getClass());
				}
			}

			sqlCrossEditor(conn, newid, crossInfo2);
			resultdata = getAllData("1", "1", " and sql_id = '" + newid + "'", lang);
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

	private String saveLegendLangs(Connection conn, String sql_id, String legend) {
		String result = null;
		boolean reconn = false;
		PreparedStatement ps = null;
		String ltp = null;
		String[] icons = null;
		String[] items = null;
		String[] langs = null;
		Singleton s = Singleton.getInstance();
		try {
			if (conn == null || conn.isClosed()) {
				conn = this.dbobj.getConnection(ConnectionType.SYS);
				reconn = true;
			}

			ps = conn.prepareStatement(this.cmd.deleteAllLegendCusLang());
			ps.setString(1, sql_id + "_legend_%");
			ps.executeUpdate();
			this.dbctrl.closeConnection(null, ps, null);
			if (DCIString.isNullOrEmpty(legend)) {
				result = "";
			} else {
				ps = conn.prepareStatement(this.cmd.addCusLang());
				icons = legend.split(";");
				for (int i = 0; i < icons.length; i++) {
					items = icons[i].split("=");
					if (items.length == 2) {
						langs = items[1].split("\\^");
						if (langs.length == 2) {
							for (int j = 0; j < langs.length; j++) {
								if (!DCIString.isNullOrEmpty(langs[j])) {
									if (j == 0) {
										ltp = "CHS";
									} else if (j == 1) {
										ltp = "CHT";
									}
									ps.setString(1, ltp);
									ps.setString(2, sql_id + "_legend_" + items[0]);
									ps.setString(3, langs[j]);
									ps.executeUpdate();
									s.addMultiLanguage(ltp, sql_id + "_legend_" + items[0], langs[j]);
								}
							}
						} else {
							if (langs[0].startsWith("^")) {
								ltp = "CHT";
							} else {
								ltp = "CHS";
							}
							ps.setString(1, ltp);
							ps.setString(2, sql_id + "_legend_" + items[0]);
							ps.setString(3, langs[0].replace("^", ""));
							ps.executeUpdate();
							s.addMultiLanguage(ltp, sql_id + "_legend_" + items[0], langs[0].replace("^", ""));
						}

						if (DCIString.isNullOrEmpty(result)) {
							result = items[0];
						} else {
							result += ";" + items[0];
						}
					} else {
						continue;
					}
				}
			}

		} catch (Exception ex) {
			result = "";
			ex.printStackTrace();
		} finally {
			if (reconn) {
				this.dbctrl.closeConnection(null, ps, conn);
			} else {
				this.dbctrl.closeConnection(null, ps, null);
			}
		}

		return result;
	}

	public HashMap<String, Object> getBodyData(String sql_id, String lang) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		HashMap<String, Object> alldatas = null;
		ArrayList<HashMap<String, Object>> datas = null;
		HashMap<String, Object> tmp = null;
		HashMap<String, ArrayList<HashMap<String, String>>> relations = null;

		try {
			conn = this.dbobj.getConnection(ConnectionType.SYS);

			relations = getColRelation(conn, sql_id, lang);

			ps = conn.prepareStatement(this.cmd.getSqlColumnWithHead(), ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			ps.setString(1, sql_id);
			rs = ps.executeQuery();

			datas = new ArrayList<HashMap<String, Object>>();

			while (rs.next()) {
				tmp = new HashMap<String, Object>();
				tmp.put("sql_id", rs.getString("sql_id"));
				tmp.put("col_id", rs.getString("col_id"));
				tmp.put("col_lang_key", rs.getString("col_lang_key"));
				tmp.put("col_type", rs.getString("col_type"));
				tmp.put("col_ori_name", rs.getString("col_ori_name"));
				tmp.put("col_width", DCIString.isNullOrEmpty(rs.getString("col_width")) ? 0 : rs.getInt("col_width"));
				tmp.put("col_seq", DCIString.isNullOrEmpty(rs.getString("col_seq")) ? 0 : rs.getInt("col_seq"));
				tmp.put("visible", rs.getString("visible") == null ? false : rs.getString("visible").equals("1"));
				tmp.put("is_popup", rs.getString("is_popup") == null ? false : rs.getString("is_popup").equals("1"));
				tmp.put("popup_title", rs.getString("popup_title") == null ? false : rs.getString("popup_title")
						.equals("1"));
				tmp.put("config_value", rs.getString("config_value"));
				tmp.put("cht", rs.getString("cht"));
				tmp.put("chs", rs.getString("chs"));
				tmp.put("relation_image", "./../../images/button_icon/BtnConfig.png");
				if (relations == null || relations.size() == 0) {
					tmp.put("relation_data", "");
				} else {
					tmp.put("relation_data", relations.get(rs.getString("col_id")));
				}
				tmp.put("calc_rule", rs.getString("calc_rule"));
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

	private HashMap<String, ArrayList<HashMap<String, String>>> getColRelation(Connection conn, String sql_id,
			String lang) throws Exception {
		boolean reconn = false;
		PreparedStatement ps = null;
		ResultSet rs = null;
		HashMap<String, ArrayList<HashMap<String, String>>> datas = null;
		HashMap<String, String> tmp = null;
		String lastCol = null;
		ArrayList<HashMap<String, String>> colset = null;

		try {
			if (conn == null || conn.isClosed()) {
				conn = this.dbobj.getConnection(ConnectionType.SYS);
			}
			ps = conn.prepareStatement(this.cmd.getSqlRelation(), ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			ps.setString(1, sql_id);
			ps.setString(2, lang);
			rs = ps.executeQuery();

			datas = new HashMap<String, ArrayList<HashMap<String, String>>>();
			colset = new ArrayList<HashMap<String, String>>();
			while (rs.next()) {
				if (lastCol != null && !lastCol.equals(rs.getString("col_id"))) {
					datas.put(lastCol, colset);
					colset = new ArrayList<HashMap<String, String>>();
				}
				tmp = new HashMap<String, String>();
				tmp.put("col_id", rs.getString("col_id"));
				tmp.put("target_sql_id", rs.getString("target_sql_id"));
				tmp.put("target_col_id", rs.getString("target_col_id"));
				tmp.put("target_sql_name", Singleton.getInstance().getLanguage(lang, rs.getString("target_sql_name")));
				tmp.put("target_col_name", rs.getString("target_col_name"));
				tmp.put("seq", rs.getString("seq"));
				colset.add(tmp);
				lastCol = rs.getString("col_id");
			}

			if (colset != null && colset.size() > 0) {
				datas.put(lastCol, colset);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			datas = new HashMap<String, ArrayList<HashMap<String, String>>>();
		} finally {
			if (reconn) {
				this.dbctrl.closeConnection(rs, ps, conn);
			} else {
				this.dbctrl.closeConnection(rs, ps, null);
			}
		}

		return datas;
	}

	public HashMap<String, Object> deleteData(String formdatas) {
		Connection conn = null;
		PreparedStatement ps = null;
		HashMap<String, Object> datas = null;
		HashMap<String, Object> result = null;
		boolean boolResult = false;
		String resultMsg = "";
		try {
			datas = DCIString.jsonTranToObjMap(formdatas); // new
															// ObjectMapper().readValue(formdatas,
															// HashMap.class);

			conn = this.dbobj.getConnection(ConnectionType.SYS);

			ps = conn.prepareStatement(this.cmd.deleteData(), ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			ps.setString(1, datas.get("sql_id").toString());
			ps.executeUpdate();
			this.dbctrl.closeConnection(null, ps, null);
			saveLegendLangs(conn, datas.get("sql_id").toString(), null);
			cusMenuEditor(conn, datas.get("sql_id").toString(), false);
			roleEditor(conn, datas.get("sql_id").toString(), null);
			ps = conn.prepareStatement(this.cmd.deleteCusLangWithHead());
			ps.setString(1, datas.get("sql_id").toString());
			ps.setString(2, datas.get("sql_id").toString());
			ps.executeUpdate();
			this.dbctrl.closeConnection(null, ps, null);
			ps = conn.prepareStatement(this.cmd.deleteSqlRelation());
			ps.setString(1, datas.get("sql_id").toString());
			ps.executeUpdate();
			sqlConnEditor(conn, datas.get("sql_id").toString(), null);
			sqlCrossEditor(conn, datas.get("sql_id").toString(), new ArrayList<HashMap<String, String>>());
			deleteBodyWithHead(conn, datas.get("sql_id").toString());
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

	public HashMap<String, Object> saveBody(String deldatas, String allAdd) {
		Connection conn = null;
		PreparedStatement ps = null;
		ArrayList<HashMap<String, Object>> datas = null;
		ArrayList<HashMap<String, String>> relations = null;
		HashMap<String, Object> result = null;
		boolean boolResult = false;
		String resultMsg = "";
		String[][] langs = null;

		try {
			datas = new ObjectMapper().readValue(deldatas, new TypeReference<ArrayList<HashMap<String, Object>>>() {
			});

			conn = this.dbobj.getConnection(ConnectionType.SYS);
			if (Boolean.parseBoolean(allAdd) && datas.size() > 0) {
				deleteBodyWithHead(conn, datas.get(0).get("sql_id").toString());
			}

			for (int i = 0; i < datas.size(); i++) {
				if (datas.get(i).get("moditp").toString().equals("add")) {
					ps = conn.prepareStatement(this.cmd.addSqlColumn(), ResultSet.TYPE_SCROLL_INSENSITIVE,
							ResultSet.CONCUR_READ_ONLY);
				} else {
					ps = conn.prepareStatement(this.cmd.updSqlColumn(), ResultSet.TYPE_SCROLL_INSENSITIVE,
							ResultSet.CONCUR_READ_ONLY);
				}
				ps.setString(1, datas.get(i).get("col_lang_key").toString());
				ps.setString(2, datas.get(i).get("col_type").toString());
				ps.setString(3, datas.get(i).get("col_ori_name").toString());
				ps.setString(4, datas.get(i).get("col_width").toString());
				ps.setString(5, datas.get(i).get("col_seq").toString());
				if (datas.get(i).get("config_value") == null) {
					ps.setString(6, "");
				} else {
					ps.setString(6, datas.get(i).get("config_value").toString());
				}

				if (datas.get(i).get("visible") != null && (Boolean) datas.get(i).get("visible")) {
					ps.setString(7, "1");
				} else {
					ps.setString(7, "0");
				}
				if (datas.get(i).get("is_popup") != null && (Boolean) datas.get(i).get("is_popup")) {
					ps.setString(8, "1");
				} else {
					ps.setString(8, "0");
				}
				if (datas.get(i).get("popup_title") != null && (Boolean) datas.get(i).get("popup_title")) {
					ps.setString(9, "1");
				} else {
					ps.setString(9, "0");
				}
				if (datas.get(i).get("calc_rule") == null) {
					ps.setString(10, null);
				} else {
					ps.setString(10, datas.get(i).get("calc_rule").toString());
				}
				ps.setString(11, datas.get(i).get("sql_id").toString());
				ps.setString(12, datas.get(i).get("col_id").toString());
				ps.executeUpdate();

				langs = new String[2][];
				langs[0] = new String[] { "CHT", datas.get(i).get("col_lang_key").toString(),
						datas.get(i).get("cht") == null ? "" : datas.get(i).get("cht").toString() };
				langs[1] = new String[] { "CHS", datas.get(i).get("col_lang_key").toString(),
						datas.get(i).get("chs") == null ? "" : datas.get(i).get("chs").toString() };
				saveLangs(conn, langs);
//				if (datas.get(i).get("write_data") != null) {
//					if (!DCIString.isNullOrEmpty(datas.get(i).get("write_data").toString())) {
//						relations = new ObjectMapper().readValue(datas.get(i).get("write_data").toString(),
//								new TypeReference<ArrayList<HashMap<String, String>>>() {
//								});
//					}
//				}
				


				if (datas.get(i).containsKey("write_data")) {
					if (datas.get(i).get("write_data") != null) {
						if (!DCIString.isNullOrEmpty(datas.get(i).get("write_data").toString())) {
							relations = new ObjectMapper().readValue(datas.get(i).get("write_data").toString(),
									new TypeReference<ArrayList<HashMap<String, String>>>() {
									});
						}
					}
					sqlRelationEditor(conn, datas.get(i).get("sql_id").toString(), datas.get(i).get("col_id")
							.toString(), relations);
				}
				
				

//				sqlRelationEditor(conn, datas.get(0).get("sql_id").toString(), datas.get(0).get("col_id").toString(),
//						relations);
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
			datas = DCIString.jsonTranToObjMap(deldatas); // new
															// ObjectMapper().readValue(deldatas,
															// HashMap.class);
			conn = this.dbobj.getConnection(ConnectionType.SYS);
			ps = conn.prepareStatement(this.cmd.deleteSqlColumn(), ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			ps.setString(1, datas.get("sql_id").toString());
			ps.setString(2, datas.get("col_id").toString());
			ps.executeUpdate();

			this.dbctrl.closeConnection(null, ps, null);
			ps = conn.prepareStatement(this.cmd.deleteSqlRelation_col(), ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			ps.setString(1, datas.get("sql_id").toString());
			ps.setString(2, datas.get("col_id").toString());
			ps.executeUpdate();

			this.dbctrl.closeConnection(null, ps, null);
			ps = conn.prepareStatement(this.cmd.deleteCusLang(), ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			ps.setString(1, datas.get("col_lang_key").toString());
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

	public void deleteBodyWithHead(Connection conn, String sql_id) throws Exception {
		boolean reconn = false;
		PreparedStatement ps = null;

		try {
			if (conn == null || conn.isClosed()) {
				conn = this.dbobj.getConnection(ConnectionType.SYS);
			}
			ps = conn.prepareStatement(this.cmd.deleteSqlColumnWithHead(), ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			ps.setString(1, sql_id);
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

	public HashMap<String, Object> getAllLights(String lang) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		HashMap<String, Object> alldatas = null;
		ArrayList<HashMap<String, Object>> datas = null;
		HashMap<String, Object> tmp = null;

		try {
			conn = this.dbobj.getConnection(ConnectionType.SYS);
			ps = conn.prepareStatement(this.cmd.getAllLights());
			rs = ps.executeQuery();

			datas = new ArrayList<HashMap<String, Object>>();

			while (rs.next()) {
				tmp = new HashMap<String, Object>();
				tmp.put("light_id", DCIString.Base64Encode(rs.getString("icon_id")));
				tmp.put("icon_id", rs.getString("icon_id"));
				tmp.put("icon_name", Singleton.getInstance().getLanguage(lang, rs.getString("icon_name")));
				tmp.put("icon_map_key", rs.getString("icon_map_key"));
				tmp.put("legend_value", "");
				tmp.put("legend_value_chs", "");
				tmp.put("sort_id", String.valueOf(rs.getRow() + 10000));
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

	public HashMap<String, Object> getAllSqls(String lang) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		HashMap<String, Object> alldatas = null;
		ArrayList<HashMap<String, Object>> datas = null;
		HashMap<String, Object> tmp = null;
		HashMap<String, ArrayList<HashMap<String, String>>> cols = null;

		try {
			conn = this.dbobj.getConnection(ConnectionType.SYS);

			cols = getSqlColumns(conn, lang);

			ps = conn.prepareStatement(this.cmd.getAllData(""), ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			ps.setString(1, lang);
			rs = ps.executeQuery();

			datas = new ArrayList<HashMap<String, Object>>();

			while (rs.next()) {
				tmp = new HashMap<String, Object>();
				tmp.put("label", Singleton.getInstance().getLanguage(lang, rs.getString("sql_name")));
				tmp.put("value", rs.getString("sql_id"));
				tmp.put("cols", cols.get(rs.getString("sql_id")));
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

	private HashMap<String, ArrayList<HashMap<String, String>>> getSqlColumns(Connection conn, String lang)
			throws Exception {
		boolean reconn = false;
		PreparedStatement ps = null;
		ResultSet rs = null;
		HashMap<String, ArrayList<HashMap<String, String>>> datas = null;
		HashMap<String, String> tmp = null;
		String lastSql = null;
		ArrayList<HashMap<String, String>> colset = null;

		try {
			if (conn == null || conn.isClosed()) {
				conn = this.dbobj.getConnection(ConnectionType.SYS);
			}
			ps = conn.prepareStatement(this.cmd.getSqlColumns(), ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			ps.setString(1, lang);
			rs = ps.executeQuery();

			datas = new HashMap<String, ArrayList<HashMap<String, String>>>();
			colset = new ArrayList<HashMap<String, String>>();
			while (rs.next()) {
				if (lastSql != null && !lastSql.equals(rs.getString("sql_id"))) {
					datas.put(lastSql, colset);
					colset = new ArrayList<HashMap<String, String>>();
				}
				tmp = new HashMap<String, String>();
				tmp.put("value", rs.getString("col_id"));
				tmp.put("label", rs.getString("col_name"));
				colset.add(tmp);
				lastSql = rs.getString("sql_id");
			}

			if (colset != null && colset.size() > 0) {
				datas.put(lastSql, colset);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			datas = new HashMap<String, ArrayList<HashMap<String, String>>>();
		} finally {
			if (reconn) {
				this.dbctrl.closeConnection(rs, ps, conn);
			} else {
				this.dbctrl.closeConnection(rs, ps, null);
			}
		}

		return datas;
	}

	public HashMap<String, Object> getSqlColumns(String sql_id, String lang) {
		Connection conn = null;
		Connection conn2 = null;
		Connection conn3 = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		PreparedStatement ps2 = null;
		ResultSet rs2 = null;
		PreparedStatement ps3 = null;
		ResultSet rs3 = null;
		PreparedStatement ps4 = null;
		ResultSet rs4 = null;
		HashMap<String, Object> datas = null;
		ArrayList<HashMap<String, Object>> cols = null;
		HashMap<String, Object> tmp = null;
		HashMap<String, Object> existtmp = null;
		boolean boolResult = false;
		String resultMsg = "";
		String sql_cmd = null;
		String cross_sql_cmd = null;
		// Singleton s = Singleton.getInstance();
		HashMap<String, HashMap<String, Object>> existCol = null;
		boolean hasExistCols = false;

		try {
			conn = this.dbobj.getConnection(ConnectionType.SYS);
			ps = conn.prepareStatement(cmd.getInitSqlColumns(), ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			ps.setString(1, sql_id);
			ps.setString(2, sql_id);
			rs = ps.executeQuery();
			if (rs != null && rs.next()) {
				sql_cmd = rs.getString("sql_context") + "\r\n and 1=2 ";
				if (!DCIString.isNullOrEmpty(rs.getString("group_by"))) {
					sql_cmd += "\r\n group by " + rs.getString("group_by");
				}
				conn2 = DataDatabaseObject.getInstance().getConnection(rs.getString("conn_id"));
				ps2 = conn2.prepareStatement(sql_cmd, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				rs2 = ps2.executeQuery();
				if (rs2 != null) {
					existCol = getExistColumn(conn, sql_id, lang);
					if (existCol != null && existCol.size() > 0) {
						hasExistCols = true;
					}

					ResultSetMetaData rsmd = rs2.getMetaData();
					if (rs.getString("is_cross").equals("0")
							|| (rs.getString("is_cross").equals("1") && rs.getString("cross_type").equals(
									CrossDBType.union_all.toString()))) {
						cols = new ArrayList<HashMap<String, Object>>();
						for (int i = 1; i <= rsmd.getColumnCount(); i++) {
							if (hasExistCols) {
								if (existCol.containsKey(rsmd.getColumnName(i))) {
									existtmp = existCol.get(rsmd.getColumnName(i));
									tmp = new HashMap<String, Object>();
									tmp.put("sql_id", sql_id);
									tmp.put("col_id", rsmd.getColumnName(i));
									tmp.put("col_lang_key", sql_id + "_" + rsmd.getColumnName(i));
									tmp.put("col_type", existtmp.get("col_type"));
									tmp.put("col_ori_name", existtmp.get("col_ori_name"));
									tmp.put("col_width", existtmp.get("col_width"));
									tmp.put("col_seq", i * 10);
									tmp.put("visible", existtmp.get("visible"));
									tmp.put("is_popup", existtmp.get("is_popup"));
									tmp.put("popup_title", existtmp.get("popup_title"));
									tmp.put("config_value", existtmp.get("config_value"));
									tmp.put("chs", existtmp.get("chs"));
									tmp.put("cht", existtmp.get("cht"));
									tmp.put("moditag", 1);
									tmp.put("moditp", "add");
									tmp.put("relation_image", existtmp.get("relation_image"));
									tmp.put("relation_data", existtmp.get("relation_data"));
									tmp.put("calc_rule", existtmp.get("calc_rule"));
								} else {
									tmp = setColData(sql_id, rsmd.getColumnName(i), i);
								}
							} else {
								tmp = setColData(sql_id, rsmd.getColumnName(i), i);

							}
							cols.add(tmp);
						}
					} else {
						ArrayList<ResultSetMetaData> arss = null;

						ps4 = conn.prepareStatement(this.cmd.getSqlCross());
						ps4.setString(1, sql_id);
						rs4 = ps4.executeQuery();
						arss = new ArrayList<ResultSetMetaData>();
						arss.add(rsmd);
						while (rs4.next()) {
							cross_sql_cmd = rs4.getString("sql_context") + "\r\n and 1=2 ";
							if (!DCIString.isNullOrEmpty(rs4.getString("group_by"))) {
								cross_sql_cmd += "\r\n group by " + rs4.getString("group_by");
							}
							try {
								conn3 = DataDatabaseObject.getInstance().getConnection(rs4.getString("conn_id"));
								ps3 = conn3.prepareStatement(cross_sql_cmd, ResultSet.TYPE_SCROLL_INSENSITIVE,
										ResultSet.CONCUR_READ_ONLY);
								// rs3 = ps3.executeQuery();
								arss.add(ps3.executeQuery().getMetaData());

							} catch (Exception e) {

							} finally {
								// this.dbctrl.closeConnection(null, null,
								// conn3);
							}
						}
						if (arss != null) {
							ResultSetMetaData[] rss = null;

							rss = new ResultSetMetaData[arss.size()];

							rss = arss.toArray(rss);

							HashMap<String, HashMap<String, String>> joincols = new DatabaseFuncs().colnameRepeat(rss);

							if (joincols != null) {
								int cnt = 1;
								cols = new ArrayList<HashMap<String, Object>>();
								for (String key : joincols.keySet()) {
									if (hasExistCols) {
										if (existCol.containsKey(joincols.get(key).get("name"))) {
											existtmp = existCol.get(joincols.get(key).get("name"));
											tmp = new HashMap<String, Object>();
											tmp.put("sql_id", sql_id);
											tmp.put("col_id", joincols.get(key).get("name"));
											tmp.put("col_lang_key", sql_id + "_" + joincols.get(key).get("name"));
											tmp.put("col_type", existtmp.get("col_type"));
											tmp.put("col_ori_name", existtmp.get("col_ori_name"));
											tmp.put("col_width", existtmp.get("col_width"));
											tmp.put("col_seq", cnt * 10);
											tmp.put("visible", existtmp.get("visible"));
											tmp.put("is_popup", existtmp.get("is_popup"));
											tmp.put("popup_title", existtmp.get("popup_title"));
											tmp.put("config_value", existtmp.get("config_value"));
											tmp.put("chs", existtmp.get("chs"));
											tmp.put("cht", existtmp.get("cht"));
											tmp.put("moditag", 1);
											tmp.put("moditp", "add");
											tmp.put("relation_image", existtmp.get("relation_image"));
											tmp.put("relation_data", existtmp.get("relation_data"));
											tmp.put("calc_rule", existtmp.get("calc_rule"));
										} else {
											tmp = setColData(sql_id, joincols.get(key).get("name"), cnt);
										}
									} else {
										tmp = setColData(sql_id, joincols.get(key).get("name"), cnt);
									}
									cols.add(tmp);
									cnt++;
								}
							}
						}
					}

					/*
					 * else { cross_sql_cmd = rs.getString("cross_sql_context")
					 * + "\r\n and 1=2 "; if
					 * (!DCIString.isNullOrEmpty(rs.getString
					 * ("cross_group_by"))) { cross_sql_cmd += "\r\n group by "
					 * + rs.getString("cross_group_by"); } conn3 =
					 * DataDatabaseObject
					 * .getInstance().getConnection(rs.getString
					 * ("cross_conn_id")); ps3 =
					 * conn3.prepareStatement(cross_sql_cmd,
					 * ResultSet.TYPE_SCROLL_INSENSITIVE,
					 * ResultSet.CONCUR_READ_ONLY); rs3 = ps3.executeQuery(); if
					 * (rs3 != null) { ResultSetMetaData rsmd2 =
					 * rs3.getMetaData();
					 * 
					 * HashMap<String, String> joincols = new
					 * DatabaseFuncs().colnameRepeat(rsmd, rsmd2);
					 * 
					 * if (joincols != null) { int cnt = 1; cols = new
					 * ArrayList<HashMap<String, Object>>(); for (String key :
					 * joincols.keySet()) { if (hasExistCols) { if
					 * (existCol.containsKey(joincols.get(key))) { existtmp =
					 * existCol.get(joincols.get(key)); tmp = new
					 * HashMap<String, Object>(); tmp.put("sql_id", sql_id);
					 * tmp.put("col_id", joincols.get(key));
					 * tmp.put("col_lang_key", sql_id + "_" +
					 * joincols.get(key)); tmp.put("col_type",
					 * existtmp.get("col_type")); tmp.put("col_ori_name",
					 * existtmp.get("col_ori_name")); tmp.put("col_width",
					 * existtmp.get("col_width")); tmp.put("col_seq", cnt * 10);
					 * tmp.put("visible", existtmp.get("visible"));
					 * tmp.put("is_popup", existtmp.get("is_popup"));
					 * tmp.put("popup_title", existtmp.get("popup_title"));
					 * tmp.put("config_value", existtmp.get("config_value"));
					 * tmp.put("chs", existtmp.get("chs")); tmp.put("cht",
					 * existtmp.get("cht")); tmp.put("moditag", 1);
					 * tmp.put("moditp", "add"); tmp.put("relation_image",
					 * existtmp.get("relation_image")); tmp.put("relation_data",
					 * existtmp.get("relation_data")); tmp.put("calc_rule",
					 * existtmp.get("calc_rule")); } else { tmp =
					 * setColData(sql_id, joincols.get(key), cnt); } } else {
					 * tmp = setColData(sql_id, joincols.get(key), cnt); }
					 * cols.add(tmp); cnt++; } } } }
					 */
				}
			}

			datas = new HashMap<String, Object>();
			datas.put("items", cols);
			boolResult = true;

		} catch (Exception ex) {
			ex.printStackTrace();
			boolResult = false;
			resultMsg = ex.getMessage();
		} finally {
			this.dbctrl.closeConnection(rs4, ps4, null);
			this.dbctrl.closeConnection(rs3, ps3, conn3);
			this.dbctrl.closeConnection(rs2, ps2, conn2);
			this.dbctrl.closeConnection(rs, ps, conn);
			if (datas == null) {
				datas = new HashMap<String, Object>();
			}
			datas.put("success", boolResult);
			datas.put("errorMessage", resultMsg);
		}

		return datas;
	}

	private HashMap<String, Object> setColData(String sql_id, String col_id, int seq) {
		HashMap<String, Object> tmp = null;
		tmp = new HashMap<String, Object>();
		tmp.put("sql_id", sql_id);
		tmp.put("col_id", col_id);
		tmp.put("col_lang_key", sql_id + "_" + col_id);
		tmp.put("col_type", "CHAR");
		tmp.put("col_ori_name", col_id);
		tmp.put("col_width", 100);
		tmp.put("col_seq", seq * 10);
		tmp.put("visible", true);
		tmp.put("is_popup", false);
		tmp.put("popup_title", false);
		tmp.put("config_value", "");
		tmp.put("chs", Singleton.getInstance().getLanguageForCol("CHS", col_id));
		tmp.put("cht", Singleton.getInstance().getLanguageForCol("CHT", col_id));
		tmp.put("moditag", 1);
		tmp.put("moditp", "add");
		tmp.put("relation_image", "./../../images/button_icon/BtnConfig.png");
		tmp.put("relation_data", "");
		tmp.put("calc_rule", "");

		return tmp;
	}

	private HashMap<String, HashMap<String, Object>> getExistColumn(Connection conn, String sql_id, String lang) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		// String id = null;
		boolean reconn = false;
		HashMap<String, HashMap<String, Object>> datas = null;
		HashMap<String, Object> tmp = null;
		HashMap<String, ArrayList<HashMap<String, String>>> relations = null;

		try {
			if (conn == null || conn.isClosed()) {
				conn = this.dbobj.getConnection(ConnectionType.SYS);
				reconn = true;
			}

			relations = getColRelation(conn, sql_id, lang);

			ps = conn.prepareStatement(this.cmd.getSqlColumnWithHead(), ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			ps.setString(1, sql_id);
			rs = ps.executeQuery();
			datas = new HashMap<String, HashMap<String, Object>>();

			while (rs.next()) {
				tmp = new HashMap<String, Object>();
				tmp.put("sql_id", rs.getString("sql_id"));
				tmp.put("col_id", rs.getString("col_id"));
				tmp.put("col_lang_key", rs.getString("col_lang_key"));
				tmp.put("col_type", rs.getString("col_type"));
				tmp.put("col_ori_name", rs.getString("col_ori_name"));
				tmp.put("col_width", rs.getString("col_width"));
				tmp.put("col_seq", rs.getString("col_seq"));
				tmp.put("visible", rs.getString("visible") == null ? false : rs.getString("visible").equals("1"));
				tmp.put("is_popup", rs.getString("is_popup") == null ? false : rs.getString("is_popup").equals("1"));
				tmp.put("popup_title", rs.getString("popup_title") == null ? false : rs.getString("popup_title")
						.equals("1"));
				tmp.put("config_value", rs.getString("config_value"));
				tmp.put("cht", rs.getString("cht"));
				tmp.put("chs", rs.getString("chs"));
				tmp.put("relation_image", "./../../images/button_icon/BtnConfig.png");
				if (relations == null || relations.size() == 0) {
					tmp.put("relation_data", "");
				} else {
					tmp.put("relation_data", relations.get(rs.getString("col_id")));
				}
				tmp.put("calc_rule", rs.getString("calc_rule"));
				datas.put(rs.getString("col_id"), tmp);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			// id = "";
		} finally {
			if (reconn) {
				this.dbctrl.closeConnection(rs, ps, conn);
			} else {
				this.dbctrl.closeConnection(rs, ps, null);
			}
		}
		return datas;
	}

	public HashMap<String, Object> getSqlCrossDB(String sql_id) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		HashMap<String, Object> datas = null;
		HashMap<String, Object> tmp = null;
		boolean boolResult = false;
		String resultMsg = "";

		try {
			conn = this.dbobj.getConnection(ConnectionType.SYS);
			ps = conn
					.prepareStatement(cmd.getSqlCross(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ps.setString(1, sql_id);
			rs = ps.executeQuery();

			if (rs.next()) {
				tmp = new HashMap<String, Object>();
				tmp.put("cross_type", rs.getString("cross_type"));
				tmp.put("sql_context", rs.getString("sql_context"));
				tmp.put("group_by", rs.getString("group_by"));
				tmp.put("order_by", rs.getString("order_by"));
				tmp.put("join_key_set1", rs.getString("join_key_set1"));
				tmp.put("join_key_set2", rs.getString("join_key_set2"));
			}

			datas = new HashMap<String, Object>();
			datas.put("sqls", tmp);
			boolResult = true;

		} catch (Exception ex) {
			if (tmp == null) {
				tmp = new HashMap<String, Object>();
			}
			ex.printStackTrace();
			boolResult = false;
			resultMsg = ex.getMessage();
		} finally {
			this.dbctrl.closeConnection(rs, ps, conn);
			datas.put("success", boolResult);
			datas.put("errorMessage", resultMsg);
		}

		return datas;
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
					id = "K0001";
				} else {
					id = id.substring(0, 1)
							+ DCIString.strFix(String.valueOf(Integer.parseInt(id.substring(1)) + 1), 4, true, "0");
				}
			} else {
				id = "K0001";
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

	private void sqlRelationEditor(Connection conn, String sql_id, String col_id,
			ArrayList<HashMap<String, String>> relations) throws Exception {
		PreparedStatement ps = null;
		boolean reconn = false;
		HashMap<String, String> tmp = null;

		try {
			if (conn == null || conn.isClosed()) {
				conn = this.dbobj.getConnection(ConnectionType.SYS);
				reconn = true;
			}
			ps = conn.prepareStatement(this.cmd.deleteSqlRelation_col(), ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			ps.setString(1, sql_id);
			ps.setString(2, col_id);
			ps.executeUpdate();

			this.dbctrl.closeConnection(null, ps, null);
			if (relations != null && relations.size() > 0) {
				ps = conn.prepareStatement(this.cmd.addSqlRelation(), ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
				for (int i = 0; i < relations.size(); i++) {
					tmp = relations.get(i);

					ps.setString(1, sql_id);
					ps.setString(2, tmp.get("col_id"));
					ps.setString(3, "R" + DCIString.strFix(String.valueOf(i + 1), 3, true, "0"));
					ps.setString(4, tmp.get("target_sql_id"));
					ps.setString(5, tmp.get("target_col_id"));
					ps.setString(6, tmp.get("seq"));
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

	private void sqlConnEditor(Connection conn, String sql_id, String[] conns) throws Exception {
		PreparedStatement ps = null;
		boolean reconn = false;

		try {
			if (conn == null || conn.isClosed()) {
				conn = this.dbobj.getConnection(ConnectionType.SYS);
				reconn = true;
			}
			ps = conn.prepareStatement(this.cmd.deleteSqlConnMapping(), ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			ps.setString(1, sql_id);
			ps.executeUpdate();

			this.dbctrl.closeConnection(null, ps, null);
			if (conns != null && conns.length > 0) {
				ps = conn.prepareStatement(this.cmd.addSqlConnMapping(), ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
				for (int i = 0; i < conns.length; i++) {
					ps.setString(1, sql_id);
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

	private void cusMenuEditor(Connection conn, String sql_id, boolean add) throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;
		String parent = null;
		boolean reconn = false;
		String cusUrl = null;

		try {
			if (conn == null || conn.isClosed()) {
				conn = this.dbobj.getConnection(ConnectionType.SYS);
				reconn = true;
			}

			if (add) {
				ps = conn.prepareStatement(this.cmd.getCusMenuParentID(), ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
				ps.setString(1, sql_id);

				rs = ps.executeQuery();

				if (rs.next()) {
					if (!DCIString.isNullOrEmpty(rs.getString("parent_id"))) {
						parent = rs.getString("parent_id");
					}
				}
				this.dbctrl.closeConnection(rs, ps, null);
			}

			ps = conn.prepareStatement(this.cmd.getCusUrl());
			ps.setString(1, sql_id);
			ps.setString(2, sql_id);

			rs = ps.executeQuery();

			if (rs.next()) {
				cusUrl = rs.getString("func_url");
			} else {
				cusUrl = "./../../FuncViews/Funcs/EKB/KanBan.jsp";
			}

			this.dbctrl.closeConnection(rs, ps, null);

			ps = conn.prepareStatement(this.cmd.deleteCusMenu(), ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			ps.setString(1, sql_id);
			ps.executeUpdate();

			this.dbctrl.closeConnection(null, ps, null);
			if (add) {
				ps = conn.prepareStatement(this.cmd.addCusMenu(), ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
				ps.setString(1, sql_id);
				ps.setString(2, sql_id);
				ps.setString(3, parent);
				ps.setString(4, cusUrl);
				ps.setString(5, "1");
				ps.setString(6, "1");
				ps.setString(7, "1");
				ps.setString(8, "EKB");

				ps.executeUpdate();
			}
		} finally {
			if (reconn) {
				this.dbctrl.closeConnection(rs, ps, conn);
			} else {
				this.dbctrl.closeConnection(rs, ps, null);
			}
		}
	}

	private void roleEditor(Connection conn, String sql_id, String[] conns) throws Exception {
		PreparedStatement ps = null;
		boolean reconn = false;

		try {
			if (conn == null || conn.isClosed()) {
				conn = this.dbobj.getConnection(ConnectionType.SYS);
				reconn = true;
			}

			ps = conn.prepareStatement(this.cmd.deleteFuncRoleRule());
			ps.setString(1, sql_id);
			ps.executeUpdate();

			this.dbctrl.closeConnection(null, ps, null);

			ps = conn.prepareStatement(this.cmd.deleteFuncUserRoleRule());
			ps.setString(1, sql_id);
			ps.executeUpdate();

			this.dbctrl.closeConnection(null, ps, null);
		} finally {
			if (reconn) {
				this.dbctrl.closeConnection(null, ps, conn);
			} else {
				this.dbctrl.closeConnection(null, ps, null);
			}
		}
	}

	private void adminRoleEditor(Connection conn, String sql_id, String[] conns) throws Exception {
		PreparedStatement ps = null;
		boolean reconn = false;

		try {
			if (conn == null || conn.isClosed()) {
				conn = this.dbobj.getConnection(ConnectionType.SYS);
				reconn = true;
			}

			ps = conn.prepareStatement(this.cmd.deleteRoleRule());
			ps.setString(1, sql_id);
			ps.executeUpdate();

			this.dbctrl.closeConnection(null, ps, null);

			ps = conn.prepareStatement(this.cmd.deleteUserRoleRule());
			ps.setString(1, sql_id);
			ps.executeUpdate();

			this.dbctrl.closeConnection(null, ps, null);
			if (conns != null && conns.length > 0) {
				ps = conn.prepareStatement(this.cmd.addRoleRule(), ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);

				for (int i = 0; i < conns.length; i++) {
					ps.setString(1, conns[i]);
					ps.setString(2, sql_id);
					ps.executeUpdate();
				}

			}
		} finally {
			if (reconn) {
				this.dbctrl.closeConnection(null, ps, conn);
			} else {
				this.dbctrl.closeConnection(null, ps, null);
			}
		}
	}

	private void saveLangs(Connection conn, String[][] langs) throws Exception {
		PreparedStatement ps = null;
		boolean reconn = false;

		try {
			if (langs != null && langs.length > 0) {
				if (conn == null || conn.isClosed()) {
					conn = this.dbobj.getConnection(ConnectionType.SYS);
					reconn = true;
				}
				ps = conn.prepareStatement(this.cmd.deleteCusLang(), ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
				ps.setString(1, langs[0][1]);
				ps.executeUpdate();

				this.dbctrl.closeConnection(null, ps, null);
				ps = conn.prepareStatement(this.cmd.addCusLang(), ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
				for (int i = 0; i < langs.length; i++) {
					if (!DCIString.isNullOrEmpty(langs[i][2])) {
						ps.setString(1, langs[i][0]);
						ps.setString(2, langs[i][1]);
						ps.setString(3, langs[i][2]);
						ps.addBatch();
					}
				}

				ps.executeBatch();

				for (int i = 0; i < langs.length; i++) {
					if (DCIString.isNullOrEmpty(langs[i][2])) {
						Singleton.getInstance().removeMultiLanguage(langs[i][0], langs[i][1]);
					} else {
						Singleton.getInstance().addMultiLanguage(langs[i][0], langs[i][1], langs[i][2]);
					}
				}

			}
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

	private void sqlCrossEditor(Connection conn, String sql_id, HashMap<String, String> crossSql) throws Exception {
		PreparedStatement ps = null;
		boolean reconn = false;

		try {
			if (conn == null || conn.isClosed()) {
				conn = this.dbobj.getConnection(ConnectionType.SYS);
				reconn = true;
			}
			ps = conn.prepareStatement(this.cmd.deleteSqlCross(), ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			ps.setString(1, sql_id);
			ps.executeUpdate();

			this.dbctrl.closeConnection(null, ps, null);
			if (crossSql != null && crossSql.size() > 0) {
				ps = conn.prepareStatement(this.cmd.addSqlCross(), ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
				ps.setString(1, sql_id);
				ps.setString(2, crossSql.get("conn_id"));
				ps.setString(3, crossSql.get("cross_type"));
				ps.setString(4, crossSql.get("sql_context"));
				ps.setString(5, crossSql.get("group_by"));
				ps.setString(6, crossSql.get("order_by"));
				ps.setString(7, crossSql.get("join_key_set1"));
				ps.setString(8, crossSql.get("join_key_set2"));
				ps.executeUpdate();
			}
		} finally {
			if (reconn) {
				this.dbctrl.closeConnection(null, ps, conn);
			} else {
				this.dbctrl.closeConnection(null, ps, null);
			}
		}
	}

	private void sqlCrossEditor(Connection conn, String sql_id, ArrayList<HashMap<String, String>> crossSql)
			throws Exception {
		PreparedStatement ps = null;
		PreparedStatement ps2 = null;
		HashMap<String, String> tmp = null;
		String[] keys = null;
		String[] condis = null;
		String[] tmpcondi = null;
		String cid = null;
		boolean reconn = false;

		try {
			if (conn == null || conn.isClosed()) {
				conn = this.dbobj.getConnection(ConnectionType.SYS);
				reconn = true;
			}
			ps = conn.prepareStatement(this.cmd.deleteSqlCross2());
			ps.setString(1, sql_id);
			ps.executeUpdate();

			this.dbctrl.closeConnection(null, ps, null);
			ps = conn.prepareStatement(this.cmd.deleteSqlJoinKeys());
			ps.setString(1, sql_id);
			ps.executeUpdate();

			this.dbctrl.closeConnection(null, ps, null);
			if (crossSql != null && crossSql.size() > 0) {
				ps = conn.prepareStatement(this.cmd.addSqlCross2());
				ps2 = conn.prepareStatement(this.cmd.addSqlJoinKeys());
				for (int i = 0; i < crossSql.size(); i++) {
					tmp = crossSql.get(i);
					cid = "C" + DCIString.strFix(String.valueOf(i + 1), 2, true, "0");
					ps.setString(1, sql_id);
					ps.setString(2, cid);
					ps.setString(3, tmp.get("conn_id"));
					ps.setString(4, tmp.get("cross_type"));
					ps.setString(5, tmp.get("sql_context"));
					ps.setString(6, tmp.get("group_by"));
					ps.setString(7, tmp.get("order_by"));
					ps.setString(8, tmp.get("cross_seq").toString());
					ps.executeUpdate();
					if (!DCIString.isNullOrEmpty(tmp.get("join_keys"))) {
						keys = tmp.get("join_keys").split(";");

						if (keys != null) {
							for (int j = 0; j < keys.length; j++) {
								condis = keys[j].split("=");
								if (condis != null && condis.length == 2) {
									tmpcondi = condis[0].split("\\.");

									ps2.setString(1, sql_id);
									ps2.setString(2, cid);
									ps2.setString(3, "JK" + DCIString.strFix(String.valueOf(j + 1), 2, true, "0"));
									ps2.setString(4, String.valueOf(tmpcondi[0].getBytes()[0] - 65));
									ps2.setString(5, tmpcondi[1]);

									tmpcondi = condis[1].split("\\.");
									ps2.setString(6, String.valueOf(tmpcondi[0].getBytes()[0] - 65));
									ps2.setString(7, tmpcondi[1]);
									ps2.executeUpdate();
								}
							}
						}
					}
					keys = null;
				}
			}
		} finally {
			if (reconn) {
				this.dbctrl.closeConnection(null, ps, conn);
				this.dbctrl.closeConnection(null, ps2, null);
			} else {
				this.dbctrl.closeConnection(null, ps, null);
				this.dbctrl.closeConnection(null, ps2, null);
			}
		}
	}

	public HashMap<String, Object> copySql(String sql_id, String sql_name, String lang) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		HashMap<String, Object> datas = null;
		String newid = null;
		String connstr = null;
		String sqlCode = null;

		try {
			conn = this.dbobj.getConnection(ConnectionType.SYS);
			newid = getMaxID(conn);

			if (DCIString.isNullOrEmpty(newid)) {
				datas = new HashMap<String, Object>();
				datas.put("success", false);
				datas.put("msg", Singleton.getInstance().getLanguage(lang, "get_newid_fail"));
			} else {
				ps = conn.prepareStatement(this.cmd.copyStep1(), ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
				ps.setString(1, newid);
				ps.setString(2, newid);
				ps.setString(3, sql_id);
				ps.executeUpdate();

				this.dbctrl.closeConnection(null, ps, null);
				if (Singleton.getInstance().getSysDBType() == DBType.Oracle) {
					sqlCode = this.cmd.copyStep2_oracle();
				} else {
					sqlCode = this.cmd.copyStep2();
				}
				ps = conn.prepareStatement(sqlCode, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ps.setString(1, newid);
				ps.setString(2, newid);
				ps.setString(3, sql_id);
				ps.executeUpdate();
				sqlCode = null;
				this.dbctrl.closeConnection(null, ps, null);
				ps = conn.prepareStatement(this.cmd.copyStep3(), ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
				ps.setString(1, newid);
				ps.setString(2, sql_id);
				ps.executeUpdate();

				this.dbctrl.closeConnection(null, ps, null);
				ps = conn.prepareStatement(this.cmd.copyStep4(), ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
				ps.setString(1, newid);
				ps.setString(2, sql_id);
				ps.executeUpdate();

				this.dbctrl.closeConnection(null, ps, null);
				ps = conn.prepareStatement(this.cmd.copyStep5(), ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
				ps.setString(1, newid);
				ps.setString(2, sql_id);
				ps.executeUpdate();

				this.dbctrl.closeConnection(null, ps, null);
				ps = conn.prepareStatement(this.cmd.copyStep8(), ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
				ps.setString(1, newid);
				ps.setString(2, sql_id);
				ps.executeUpdate();

				this.dbctrl.closeConnection(null, ps, null);
				if (Singleton.getInstance().getSysDBType() == DBType.Oracle) {
					sqlCode = this.cmd.copyStep6_oracle();
				} else {
					sqlCode = this.cmd.copyStep6();
				}
				ps = conn.prepareStatement(sqlCode, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ps.setString(1, newid);
				ps.setString(2, sql_id);
				ps.executeUpdate();

				this.dbctrl.closeConnection(null, ps, null);
				ps = conn.prepareStatement(this.cmd.copyStep7(), ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
				ps.setString(1, sql_id + "_legend_");
				ps.setString(2, newid + "_legend_");
				ps.setString(3, sql_id + "_legend_%");
				ps.executeUpdate();

				this.dbctrl.closeConnection(null, ps, null);

				ps = conn.prepareStatement(this.cmd.getSqlName(), ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
				ps.setString(1, newid);
				rs = ps.executeQuery();
				if (rs.next()) {
					cusMenuEditor(conn, newid, true);
				}
				String[][] langs = new String[2][];
				langs[0] = new String[] { "CHT", newid, sql_name };
				langs[1] = new String[] { "CHS", newid, sql_name };
				saveLangs(conn, langs);

				this.dbctrl.closeConnection(rs, ps, null);

				ps = conn.prepareStatement(this.cmd.getSqlConns(), ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
				ps.setString(1, newid);
				rs = ps.executeQuery();
				while (rs.next()) {
					if (DCIString.isNullOrEmpty(connstr)) {
						connstr = rs.getString("conn_id");
					} else {
						connstr += ";" + rs.getString("conn_id");
					}
				}
				if (!DCIString.isNullOrEmpty(connstr)) {
					adminRoleEditor(conn, newid, connstr.split(";"));
				}
				this.dbctrl.closeConnection(rs, ps, null);
				new APPubMethods().loadMultiLanguage();
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
			this.dbctrl.closeConnection(rs, ps, conn);
		}

		return datas;
	}

	public HashMap<String, Object> createLink(String sql_id, String conn_id, String uid, HttpServletRequest request) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		HashMap<String, Object> datas = null;
		String linkvalue = null;
		String pwd = "";

		try {
			conn = this.dbobj.getConnection(ConnectionType.SYS);

			if (DCIString.isNullOrEmpty(sql_id) || DCIString.isNullOrEmpty(conn_id)) {
				datas = new HashMap<String, Object>();
				datas.put("success", false);
				datas.put(
						"msg",
						Singleton.getInstance().getLanguage(
								new APControl().getUserInfoFromSession(request.getSession(), uid, "lang"),
								"create_link_fail"));
			} else {
				ps = conn.prepareStatement(this.cmd.getPwd());
				ps.setString(1, uid);
				rs = ps.executeQuery();

				if (rs.next()) {
					if (rs.getObject("user_pwd") == null) {
						pwd = "";
					} else {
						pwd = rs.getString("user_pwd");
					}
				}

				linkvalue = DCIString.Base64Encode(DCIString.strEncode(conn_id + ";" + sql_id + ";" + uid + ";" + pwd
						+ ";" + DCIWebConstants.getPostTagValue()));

				datas = new HashMap<String, Object>();
				datas.put("success", true);
				datas.put(
						"linkvalue",
						request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
								+ request.getContextPath() + "/EKB/OpenLink/IndKanBan.dsc?indkey=" + linkvalue);
				datas.put("msg", "");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			datas = new HashMap<String, Object>();
			datas.put("success", false);
			datas.put("msg", ex.getMessage());
		} finally {
			this.dbctrl.closeConnection(rs, ps, conn);
		}

		return datas;
	}

	public HashMap<String, Object> getAllUsers() {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		HashMap<String, Object> alldatas = null;
		ArrayList<HashMap<String, Object>> datas = null;
		HashMap<String, Object> tmp = null;

		try {
			conn = this.dbobj.getConnection(ConnectionType.SYS);
			ps = conn.prepareStatement(this.cmd.getAllUsers());
			rs = ps.executeQuery();

			datas = new ArrayList<HashMap<String, Object>>();

			while (rs.next()) {
				tmp = new HashMap<String, Object>();
				tmp.put("label", rs.getString("user_name"));
				tmp.put("value", rs.getString("user_id"));
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

	public HashMap<String, Object> getLangList(String langtp, String key) {
		HashMap<String, Object> list = null;
		HashMap<String, String> langlist = null;
		HashMap<String, String> tmp = null;
		ArrayList<HashMap<String, String>> datas = null;

		datas = new ArrayList<HashMap<String, String>>();
		if (!DCIString.isNullOrEmpty(langtp)) {
			langlist = Singleton.getInstance().getLanguageList(langtp, key);

			for (String lkey : langlist.keySet()) {
				tmp = new HashMap<String, String>();
				tmp.put("lang_value", langlist.get(lkey));
				datas.add(tmp);
			}
		}

		list = new HashMap<String, Object>();
		list.put("items", datas);

		return list;
	}

	public HashMap<String, Object> getCrossMain(String sql_id) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		HashMap<String, Object> alldatas = null;
		ArrayList<HashMap<String, Object>> datas = null;
		HashMap<String, Object> tmp = null;
		HashMap<String, Object> lasttmp = null;
		String lastKey = null;
		String keyString = "";

		try {
			conn = this.dbobj.getConnection(ConnectionType.SYS);
			ps = conn.prepareStatement(this.cmd.getCrossMain());
			ps.setString(1, sql_id);
			ps.setString(2, sql_id);
			rs = ps.executeQuery();

			datas = new ArrayList<HashMap<String, Object>>();
			tmp = new HashMap<String, Object>();

			while (rs.next()) {
				// if (!DCIString.isNullOrEmpty(lastKey) &&
				// !lastKey.equals(rs.getString("cross_id"))) {
				// }
				if (rs.getString("cross_type").equals(CrossDBType.join.toString())
						|| rs.getString("cross_type").equals(CrossDBType.left_join.toString())) {

					if (DCIString.isNullOrEmpty(rs.getString("key_belong1"))
							|| DCIString.isNullOrEmpty(rs.getString("key_belong1"))) {
						tmp.put("join_keys", "");
						datas.add(tmp);
						tmp = new HashMap<String, Object>();
					} else {
						if (!DCIString.isNullOrEmpty(lastKey) && !lastKey.equals(rs.getString("cross_id"))) {
							tmp.put("join_keys", keyString);
							datas.add(tmp);
							tmp = new HashMap<String, Object>();
							keyString = "";
						}
						if (DCIString.isNullOrEmpty(keyString)) {
							keyString = new String(new byte[] { Byte.parseByte(String.valueOf(65 + rs
									.getInt("key_belong1"))) });
						} else {
							keyString += ";"
									+ new String(new byte[] { Byte.parseByte(String.valueOf(65 + rs
											.getInt("key_belong1"))) });
						}
						keyString += "." + rs.getString("key_col1") + "=";
						keyString += new String(new byte[] { Byte.parseByte(String.valueOf(65 + rs
								.getInt("key_belong2"))) });
						keyString += "." + rs.getString("key_col2");

					}
				}
				tmp.put("cross_id", rs.getString("cross_id"));
				tmp.put("conn_id", rs.getString("conn_id"));
				tmp.put("cross_type", rs.getString("cross_type"));
				tmp.put("sql_context", rs.getString("sql_context"));
				tmp.put("group_by", rs.getString("group_by"));
				tmp.put("order_by", rs.getString("order_by"));
				if (DCIString.isNullOrEmpty(rs.getString("cross_seq"))) {
					tmp.put("cross_seq", "1");
					tmp.put("cross_seq_show", "B");
				} else {
					tmp.put("cross_seq", rs.getString("cross_seq"));
					tmp.put("cross_seq_show",
							new String(new byte[] { Byte.parseByte(String.valueOf(65 + rs.getInt("cross_seq"))) }));
				}
				if (!rs.getString("cross_type").equals(CrossDBType.join.toString())
						&& !rs.getString("cross_type").equals(CrossDBType.left_join.toString())) {
					tmp.put("join_keys", "");
					datas.add(tmp);
					tmp = new HashMap<String, Object>();
				}
				lastKey = rs.getString("cross_id");
			}

			if (tmp != null && tmp.size() > 0) {
				tmp.put("join_keys", keyString);
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

	public HashMap<String, Object> getKeyCols(String sqls) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<HashMap<String, String>> datas = null;
		HashMap<String, Object> alldatas = null;
		ArrayList<HashMap<String, Object>> seqs = null;
		HashMap<String, Object> seq = null;
		ArrayList<HashMap<String, String>> cols = null;
		HashMap<String, String> col = null;
		HashMap<String, String> tmp = null;
		DataDatabaseObject dataObj = null;
		ResultSetMetaData rsmd = null;
		String[] conns = null;
		String sqlcmd = null;

		try {
			if (!DCIString.isNullOrEmpty(sqls)) {
				alldatas = new HashMap<String, Object>();
				datas = (ArrayList<HashMap<String, String>>) DCIString.jsonTranToObjMap(sqls,
						new ArrayList<HashMap<String, String>>().getClass());

				if (datas != null) {
					seqs = new ArrayList<HashMap<String, Object>>();
					dataObj = DataDatabaseObject.getInstance();
					for (int i = 0; i < datas.size(); i++) {
						seq = new HashMap<String, Object>();
						tmp = datas.get(i);
						try {
							if (!DCIString.isNullOrEmpty(tmp.get("connid"))) {
								cols = new ArrayList<HashMap<String, String>>();
								conns = tmp.get("connid").split(";");
								conn = dataObj.getConnection(conns[0]);

								if (conn != null && !conn.isClosed()) {
									sqlcmd = tmp.get("sql") + "\r\n and 1=2 ";
									if (!DCIString.isNullOrEmpty(tmp.get("groupBy"))) {
										sqlcmd += "\r\n group by " + tmp.get("groupBy");
									}
									ps = conn.prepareStatement(sqlcmd);
									rs = ps.executeQuery();
									rsmd = rs.getMetaData();
									for (int j = 1; j <= rsmd.getColumnCount(); j++) {
										col = new HashMap<String, String>();
										col.put("value", rsmd.getColumnName(j));
										col.put("label", rsmd.getColumnName(j));
										cols.add(col);
									}
								}
							}
						} catch (Exception e) {
							e.printStackTrace();
						} finally {
							this.dbctrl.closeConnection(rs, ps, conn);
						}
						seq.put("seq",
								new String(new byte[] { Byte.parseByte(String.valueOf(65 + Integer.parseInt(tmp
										.get("seq")))) }));
						seq.put("cols", cols);
						seqs.add(seq);
					}
				}
				alldatas.put("datas", seqs);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			this.dbctrl.closeConnection(rs, ps, conn);
		}
		if (alldatas == null) {
			alldatas = new HashMap<String, Object>();
		}
		return alldatas;
	}
}
