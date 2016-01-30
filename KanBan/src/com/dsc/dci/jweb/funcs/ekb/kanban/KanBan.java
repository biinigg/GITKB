package com.dsc.dci.jweb.funcs.ekb.kanban;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import com.dci.jweb.DCIEnums.Database.ConnectionType;
import com.dci.jweb.DCIEnums.Database.CrossDBType;
import com.dci.jweb.DataBaseLib.Database.DataDatabaseObject;
import com.dci.jweb.DataBaseLib.Database.DatabaseFuncs;
import com.dci.jweb.DataBaseLib.Database.DatabaseObjects;
import com.dci.jweb.PublicLib.APControl;
import com.dci.jweb.PublicLib.ConfigControl;
import com.dci.jweb.PublicLib.DBControl;
import com.dci.jweb.PublicLib.DCIDate;
import com.dci.jweb.PublicLib.DCINumber;
import com.dci.jweb.PublicLib.DCIString;
import com.dsc.dci.jweb.apenums.ColumnType;
import com.dsc.dci.jweb.pub.APConstants;
import com.dsc.dci.jweb.pub.APPubMethods;
import com.dsc.dci.jweb.pub.Singleton;
import com.dsc.dci.sqlcode.funcs.ekb.sqlKanBan;

public class KanBan {
	private sqlKanBan cmd;
	private DatabaseObjects dbobj;
	private DBControl dbctrl;
	private String currLang;
	private String uid;
	private Singleton s;

	public KanBan(HttpSession session, String uid) {
		this.dbobj = DatabaseObjects.getInstance();
		this.cmd = new sqlKanBan();
		this.dbctrl = new DBControl();
		APControl apc = new APControl();
		if (session == null) {
			this.currLang = null;
		} else {
			this.currLang = apc.getUserInfoFromSession(session, uid, "lang");
		}
		this.uid = uid;
		this.s = Singleton.getInstance();
	}

	public String buildLegendLangs(String sql_id, String legend, String lang) {
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
					iconDesc = s.getLanguage(lang, iconKey);
					if (!iconDesc.equals(iconKey)) {
						if (DCIString.isNullOrEmpty(result)) {
							result = iconid + "=" + s.getLanguage(lang, iconDesc);
						} else {
							result += ";" + iconid + "=" + s.getLanguage(lang, iconDesc);
						}
					}
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}

		}
		return result;
	}

	public HashMap<String, Object> getInitData(String sql_id) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		PreparedStatement ps1 = null;
		ResultSet rs1 = null;
		HashMap<String, Object> datas = null;
		ArrayList<HashMap<String, Object>> cols = null;
		ArrayList<HashMap<String, Object>> comboCols = null;
		ArrayList<HashMap<String, Object>> lights = null;
		ArrayList<HashMap<String, Object>> advs = null;
		ArrayList<HashMap<String, Object>> sqlRelation = null;
		ArrayList<HashMap<String, Object>> fields = null;
		HashMap<String, Object> tmp = null;
		HashMap<String, Object> tmp2 = null;
		HashMap<String, Object> tmp3 = null;
		HashMap<String, Object> tmp4 = null;
		HashMap<String, Object> tmp5 = null;
		HashMap<String, Object> tmp6 = null;
		String legendValue = null;
		String popupImg = null;
		String popupTitle = null;
		boolean is_cross = false;

		try {
			conn = this.dbobj.getConnection(ConnectionType.SYS);
			ps1 = conn.prepareStatement(this.cmd.getSqlColumns());
			ps1.setString(1, this.currLang);
			ps1.setString(2, sql_id);
			rs1 = ps1.executeQuery();
			cols = new ArrayList<HashMap<String, Object>>();
			fields = new ArrayList<HashMap<String, Object>>();
			comboCols = new ArrayList<HashMap<String, Object>>();
			lights = new ArrayList<HashMap<String, Object>>();
			advs = new ArrayList<HashMap<String, Object>>();
			sqlRelation = new ArrayList<HashMap<String, Object>>();

			while (rs1.next()) {
				tmp = new HashMap<String, Object>();
				tmp.put("colid", "col" + rs1.getString("col_id"));
				tmp.put("dataIndex", rs1.getString("col_id"));
				tmp.put("width", rs1.getInt("col_width"));
				tmp.put("text", rs1.getString("col_text"));
				tmp.put("hidden", rs1.getInt("visible") == 0);
				tmp.put("locked", rs1.getInt("locked") == 1);
				tmp.put("style", "text-align:center");
				tmp.put("sqlid", sql_id);
				tmp.put("rowHeight", "16");

				tmp6 = new HashMap<String, Object>();
				tmp6.put("name", rs1.getString("col_id"));

				if (rs1.getString("col_type").equals(ColumnType.LIGHT.toString())) {
					tmp.put("xtype", "dcilightcolumn");
					tmp.put("align", "center");
				} else if (rs1.getString("col_type").equals(ColumnType.NUM.toString())) {
					tmp.put("align", "right");
					tmp6.put("sortType", "");
				} else if (rs1.getString("col_type").equals(ColumnType.MONEY.toString())) {
					tmp.put("align", "right");
					tmp6.put("sortType", "");
				} else if (rs1.getString("col_type").equals(ColumnType.PROGRESS.toString())) {
					tmp.put("xtype", "dciprogressbarcolumn");
					tmp.put("align", "right");
				} else if (rs1.getString("col_type").equals(ColumnType.CHECKBOX.toString())) {
					tmp.put("xtype", "dcicheckcolumn");
					tmp.put("showOnly", true);
				} else if (rs1.getString("col_type").equals(ColumnType.IMAGELINK.toString())) {
					tmp.put("xtype", "dciimgcolumn");
					tmp.put("align", "center");
				} else if (rs1.getString("col_type").equals(ColumnType.IMAGEMAPPING.toString())) {
					tmp.put("xtype", "dciimgmapcolumn");
					tmp.put("align", "center");
				}

				cols.add(tmp);
				fields.add(tmp6);

				if (DCIString.isNullOrEmpty(rs1.getString("calc_rule"))) {
					tmp4 = new HashMap<String, Object>();
					tmp4.put("label", Singleton.getInstance().getLanguage(this.currLang, rs1.getString("col_lang_key")));
					tmp4.put("value", rs1.getString("col_ori_name"));
					tmp4.put("col_type", rs1.getString("col_type"));
					tmp4.put("config_value", rs1.getString("config_value"));
					comboCols.add(tmp4);
				}

				if (rs1.getString("is_popup") != null && rs1.getString("is_popup").equals("1")) {
					if (DCIString.isNullOrEmpty(popupImg)) {
						popupImg = rs1.getString("col_id");
					} else {
						popupImg += ";" + rs1.getString("col_id");
					}
				}

				if (rs1.getString("popup_title") != null && rs1.getString("popup_title").equals("1")) {
					if (DCIString.isNullOrEmpty(popupTitle)) {
						popupTitle = rs1.getString("col_id");
					} else {
						popupTitle += ";" + rs1.getString("col_id");
					}
				}
			}

			this.dbctrl.closeConnection(rs1, ps1, null);

			ps1 = conn.prepareStatement(this.cmd.getIsCrossDB());
			ps1.setString(1, sql_id);
			rs1 = ps1.executeQuery();
			is_cross = rs1.next();
			this.dbctrl.closeConnection(rs1, ps1, null);

			ps = conn.prepareStatement(this.cmd.getSqlCommand());
			ps.setString(1, sql_id);
			ps.setString(2, this.uid);
			ps.setString(3, sql_id);
			ps.setString(4, sql_id);
			ps.setString(5, this.uid);
			ps.setString(6, sql_id);
			ps.setString(7, this.uid);
			ps.setString(8, sql_id);
			rs = ps.executeQuery();

			if (rs.next()) {
				legendValue = buildLegendLangs(sql_id, rs.getString("legend"), this.currLang);
				tmp = new HashMap<String, Object>();
				tmp.put("use_marquee", rs.getString("use_marquee"));
				tmp.put("marquee_location", rs.getString("marquee_location"));
				tmp.put("marquee_type", rs.getString("marquee_type"));
				tmp.put("marquee_refresh_gap", rs.getString("marquee_refresh_gap"));
				tmp2 = new HashMap<String, Object>();
				tmp2.put("auto_refresh", rs.getString("auto_refresh"));
				tmp2.put("refresh_gap", rs.getString("refresh_gap"));
				tmp2.put("display_gap", DCIString.secondsToMinutes(rs.getString("refresh_gap")));
				tmp3 = new HashMap<String, Object>();
				tmp3.put("op_is_open", rs.getString("op_is_open"));
				tmp3.put("filter_col", rs.getString("filter_col"));
				tmp3.put("filter_condi", rs.getString("filter_condi"));
				tmp3.put("filter_value", rs.getString("filter_value"));
				tmp3.put("sort_col", rs.getString("sort_col"));
				tmp3.put("sort_type", rs.getString("sort_type"));
				tmp3.put("page_size", rs.getString("page_size"));
				tmp3.put("grid_font_size", rs.getString("grid_font_size"));
				tmp3.put("grid_font_color", rs.getString("grid_font_color"));
				tmp3.put("grid_row_height", rs.getString("grid_row_height"));
				tmp3.put("grid_row_color", rs.getString("grid_row_color"));
				tmp3.put("grid_row_even_color", rs.getString("grid_row_even_color"));
				if (rs.getString("popup_width") == null) {
					tmp3.put("popup_width", 200);
				} else {
					if (DCIString.isInteger(rs.getString("popup_width"))) {
						tmp3.put("popup_width", rs.getInt("popup_width"));
					} else {
						tmp3.put("popup_width", 200);
					}
				}
				tmp5 = new HashMap<String, Object>();
				tmp5.put("use_popup", rs.getString("use_popup") == null ? false : rs.getString("use_popup").equals("1"));
				tmp5.put("popup_dir", rs.getString("popup_dir"));
				tmp5.put("popup_refresh_gap", rs.getString("popup_refresh_gap"));
				tmp5.put("imgcols", popupImg);
				tmp5.put("titlecols", popupTitle);
			}

			this.dbctrl.closeConnection(rs, ps, null);

			ps = conn.prepareStatement(this.cmd.getAllLights());
			rs = ps.executeQuery();

			while (rs.next()) {
				tmp4 = new HashMap<String, Object>();
				tmp4.put("label",
						Singleton.getInstance().getLanguage(this.currLang, rs.getString("icon_name").replace(" ", "_")));
				tmp4.put("value", rs.getString("icon_map_key"));
				lights.add(tmp4);
			}

			this.dbctrl.closeConnection(rs, ps, null);

			ps = conn.prepareStatement(this.cmd.getAdvCondition());
			ps.setString(1, this.uid);
			ps.setString(2, sql_id);
			rs = ps.executeQuery();

			while (rs.next()) {
				tmp4 = new HashMap<String, Object>();
				tmp4.put("condi_relation", rs.getString("condi_relation"));
				tmp4.put("condi_col", rs.getString("filter_col"));
				tmp4.put("condi_type", rs.getString("filter_condi"));
				tmp4.put("condi_value", rs.getString("filter_value"));
				tmp4.put("condi_col_display", rs.getString("filter_col_display"));
				tmp4.put("condi_type_display", rs.getString("filter_condi_display"));
				tmp4.put("condi_value_display", rs.getString("filter_value_display"));
				advs.add(tmp4);
			}

			this.dbctrl.closeConnection(rs, ps, null);

			ps = conn.prepareStatement(this.cmd.getSqlRelation());
			ps.setString(1, sql_id);
			rs = ps.executeQuery();

			while (rs.next()) {
				tmp4 = new HashMap<String, Object>();
				// tmp4.put("sql_id", rs.getString("sql_id"));
				tmp4.put("col_id", rs.getString("col_id"));
				tmp4.put("target_sql_id", rs.getString("target_sql_id"));
				tmp4.put("target_col_id", rs.getString("target_col_id"));
				tmp4.put("target_ori_col_id", rs.getString("col_ori_name"));
				if (DCIString.isNullOrEmpty(rs.getString("target_sql_name_id"))) {
					tmp4.put("target_sql_name", rs.getString("target_sql_id"));
				} else {
					tmp4.put("target_sql_name", rs.getString("target_sql_id") + " "
							+ Singleton.getInstance().getLanguage(this.currLang, rs.getString("target_sql_name_id")));
				}
				sqlRelation.add(tmp4);
			}

			datas = new HashMap<String, Object>();
			datas.put("cols", cols);
			datas.put("fields", fields);
			datas.put("legend", lightLegendBuilder(legendValue));
			datas.put("marquee", tmp);
			datas.put("refresh", tmp2);
			datas.put("display", tmp3);
			datas.put("combo", comboCols);
			datas.put("lights", lights);
			datas.put("popup", tmp5);
			datas.put("advances", advs);
			datas.put("relation", sqlRelation);
			datas.put("is_cross", is_cross);

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			this.dbctrl.closeConnection(rs, ps, conn);
		}

		return datas;
	}

	public HashMap<String, Object> getKanBanData(String strpage, String strpagesize, String filter, String sort,
			String sql_id, String conn_id) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn2 = null;
		PreparedStatement ps2 = null;
		ResultSet rs2 = null;
		PreparedStatement ps3 = null;
		ResultSet rs3 = null;
		Connection conn5 = null;
		PreparedStatement ps5 = null;
		ResultSet rs5 = null;
		HashMap<String, Object> datas = null;
		// HashMap<String, Object> tmpdata = null;
		ArrayList<HashMap<String, Object>> items = null;
		HashMap<String, Object> tmp = null;
		HashMap<String, String[]> colConfig = null;
		HashMap<String, String> iconmap = null;
		HashMap<String, String> calcInfo = null;
		int cnt = 0;
		int page = 0;
		int pagesize = 0;
		int totalRow = 0;
		String sqlcmd = null;
		ResultSetMetaData rsmd = null;
		String colName = null;
		String dataValue = null;
		KanBanMethods kbm = new KanBanMethods(this.cmd, this.dbobj, this.dbctrl, this.currLang);
		boolean isSqlFilter = false;
		int rstp = -1;

		try {
			conn = this.dbobj.getConnection(ConnectionType.SYS);
			ps = conn.prepareStatement(this.cmd.getSqlCommand(), ResultSet.TYPE_FORWARD_ONLY,
					ResultSet.CONCUR_READ_ONLY);
			ps.setString(1, sql_id);
			ps.setString(2, this.uid);
			ps.setString(3, sql_id);
			ps.setString(4, sql_id);
			ps.setString(5, this.uid);
			ps.setString(6, sql_id);
			ps.setString(7, this.uid);
			ps.setString(8, sql_id);
			rs = ps.executeQuery();

			if (rs.next()) {
				ps3 = conn.prepareStatement(this.cmd.getColConfig(), ResultSet.TYPE_FORWARD_ONLY,
						ResultSet.CONCUR_READ_ONLY);
				ps3.setString(1, sql_id);
				ps3.setString(2, sql_id);
				rs3 = ps3.executeQuery();
				if (colConfig == null) {
					colConfig = new HashMap<String, String[]>();
				}
				if (calcInfo == null) {
					calcInfo = new HashMap<String, String>();
				}

				while (rs3.next()) {
					if (rs3.getString("tp").equals("1")) {
						colConfig.put(rs3.getString("col_id"),
								new String[] { rs3.getString("col_type"), rs3.getString("config_value") });
					} else if (rs3.getString("tp").equals("2")) {
						calcInfo.put(rs3.getString("col_id"), rs3.getString("config_value"));
					}
				}

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

				conn2 = DataDatabaseObject.getInstance().getConnection(conn_id);
				sqlcmd = rs.getString("sql_context");

				if (!DCIString.isNullOrEmpty(filter)) {
					if (rs.getString("is_cross").equals("1")) {
						if (kbm.sqlCheck(sqlcmd + " \r\n" + filter, conn2)) {
							sqlcmd += " \r\n" + filter;
							isSqlFilter = true;
						}
					} else {
						sqlcmd += " \r\n" + filter;
						isSqlFilter = true;
					}
				}

				if (!DCIString.isNullOrEmpty(rs.getString("group_by"))) {
					sqlcmd += "\r\n group by " + rs.getString("group_by");
				}

				if (DCIString.isNullOrEmpty(sort)) {
					if (!DCIString.isNullOrEmpty(rs.getString("order_by"))) {
						sqlcmd += "\r\n order by " + rs.getString("order_by");
					}
				} else {
					sqlcmd += "\r\n order by " + sort;
				}

				if (rs.getString("is_cross").equals("1")) {
					rstp = ResultSet.TYPE_FORWARD_ONLY;
				} else {
					rstp = ResultSet.TYPE_SCROLL_INSENSITIVE;
				}

				ps2 = conn2.prepareStatement(sqlcmd, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				rs2 = ps2.executeQuery();

				if (rs.getString("is_cross").equals("1")) {

					// ////cross multi db
					ArrayList<HashMap<String, Object>> crossData = null;
					HashMap<String, Object> crossInfos = null;
					HashMap<String, Object> ctmp = null;
					ResultSet[] rss = null;
					ArrayList<String[]> cols = null;
					CrossDBType ctp = null;
					int ccnt = 0;

					crossInfos = crossInfoBuilder(conn, sql_id, filter, sort);
					if (crossInfos != null && crossInfos.size() > 0) {
						cols = new ArrayList<String[]>();
						rss = new ResultSet[crossInfos.size() + 1];
						rss[0] = rs2;
						ccnt = 0;
						for (String k : crossInfos.keySet()) {
							ctmp = (HashMap<String, Object>) crossInfos.get(k);
							if (ccnt == 0) {
								ctp = CrossDBType.valueOf(ctmp.get("type").toString());
							}
							ccnt++;
							rss[ccnt] = (ResultSet) ctmp.get("rs");
							cols.addAll((ArrayList<String[]>) ctmp.get("keys"));
						}

						if (ctp != null) {
							if (ctp == CrossDBType.join) {
								crossData = new DatabaseFuncs().JoinResult(rss, cols);
							} else if (ctp == CrossDBType.left_join) {
								crossData = new DatabaseFuncs().LeftJoinResult(rss, cols);
							} else {
								crossData = new DatabaseFuncs().UnionAllResult(rss);
							}
						}

					}
					/*
					 * conn5 =
					 * DataDatabaseObject.getInstance().getConnection(rs.
					 * getString("cross_conn_id")); sqlcmd =
					 * rs.getString("cross_sql_context");
					 * 
					 * if (!DCIString.isNullOrEmpty(filter)) { if
					 * (kbm.sqlCheck(sqlcmd + " \r\n" + filter, conn5)) { sqlcmd
					 * += " \r\n" + filter; isSqlFilter = true; } }
					 * 
					 * if
					 * (!DCIString.isNullOrEmpty(rs.getString("cross_group_by"
					 * ))) { sqlcmd += "\r\n group by " +
					 * rs.getString("cross_group_by"); }
					 * 
					 * if (DCIString.isNullOrEmpty(sort)) { if
					 * (!DCIString.isNullOrEmpty
					 * (rs.getString("cross_order_by"))) { sqlcmd +=
					 * "\r\n order by " + rs.getString("cross_order_by"); } }
					 * ps5 = conn5.prepareStatement(sqlcmd,
					 * ResultSet.TYPE_SCROLL_INSENSITIVE,
					 * ResultSet.CONCUR_READ_ONLY); rs5 = ps5.executeQuery();
					 * 
					 * ArrayList<Object> crossData = null; ArrayList<String[]>
					 * joinKeys = null; if
					 * (rs.getString("cross_type").equals(CrossDBType
					 * .join.toString())) { joinKeys = new
					 * ArrayList<String[]>(0); joinKeys.add(new String[] {
					 * rs.getString("join_key_set1"),
					 * rs.getString("join_key_set2") }); crossData = new
					 * DatabaseFuncs().JoinResult(rs2, rs5, joinKeys); } else if
					 * (rs.getString("cross_type").equals(CrossDBType.left_join.
					 * toString())) { crossData = new
					 * DatabaseFuncs().LeftJoinResult(rs2, rs5, joinKeys); }
					 * else { crossData = new
					 * DatabaseFuncs().UnionAllResult(rs2, rs5); }
					 */

					items = new ArrayList<HashMap<String, Object>>();
					if (crossData != null && crossData.size() > 0) {
						ConfigControl cc = new ConfigControl(false, s.getTestAreaConfigPath());
						HashMap<String, Object> firstRow = null;
						HashMap<String, Object> row = null;
						iconmap = kbm.getIconIDMap(conn);
						totalRow = crossData.size();

						if ((page - 1) * pagesize <= totalRow) {
							cnt = 1;
							firstRow = (HashMap<String, Object>) crossData.get(0);

							// datacols = new ArrayList<String>();

							// for (String key : firstRow.keySet()) {
							// datacols.add(key);
							// }

							// for (String key : calcInfo.keySet()) {
							// datacols.add(key);
							// }
							for (int p = (page - 1) * pagesize; p < totalRow; p++) {
								row = (HashMap<String, Object>) crossData.get(p);
								tmp = new HashMap<String, Object>();

								for (String key : firstRow.keySet()) {
									// for (int i = 0; i < datacols.size(); i++)
									// {
									// colName = datacols.get(i);
									colName = key;

									if (calcInfo.containsKey(colName)) {
										dataValue = DCINumber.calcNumber(row, calcInfo.get(colName));
									} else {
										if (row.containsKey(colName) && row.get(colName) != null
												&& !DCIString.isNullOrEmpty(row.get(colName).toString())) {
											dataValue = row.get(colName).toString().trim();
										} else {
											dataValue = "";
										}
									}

									if (colConfig != null && colConfig.containsKey(colName)) {
										if (colConfig.get(colName)[0].equals(ColumnType.MAPPING.toString())) {
											tmp.put(colName, kbm.mappingAnalysis(colConfig.get(colName)[1], dataValue));
										} else if (colConfig.get(colName)[0].equals(ColumnType.NUM.toString())) {
											tmp.put(colName, DCIString.numFormat(dataValue, colConfig.get(colName)[1]));
										} else if (colConfig.get(colName)[0].equals(ColumnType.LIGHT.toString())) {
											if (iconmap != null) {
												if (!DCIString.isNullOrEmpty(colConfig.get(colName)[1])) {
													dataValue = DCIString.transRangValue(dataValue,
															colConfig.get(colName)[1]);
												}
												if (iconmap.containsKey(dataValue)) {
													tmp.put(colName, iconmap.get(dataValue));
												}
											}
										} else if (colConfig.get(colName)[0].equals(ColumnType.PROGRESS.toString())) {
											tmp.put(colName, kbm.percentFormat(dataValue, colConfig.get(colName)[1]));
										} else if (colConfig.get(colName)[0].equals(ColumnType.MONEY.toString())) {
											if (DCIString.isNullOrEmpty(dataValue)) {
												tmp.put(colName, "");
											} else {
												tmp.put(colName,
														"$" + DCIString.numFormat(dataValue, colConfig.get(colName)[1]));
											}
										} else if (colConfig.get(colName)[0].equals(ColumnType.CHECKBOX.toString())) {
											tmp.put(colName, kbm.CheckBoxFormat(dataValue));
										} else if (colConfig.get(colName)[0].equals(ColumnType.DATE.toString())) {
											tmp.put(colName, DCIString.dateFormat(dataValue, colConfig.get(colName)[1]));
										} else if (colConfig.get(colName)[0].equals(ColumnType.IMAGEMAPPING.toString())) {
											if (DCIString.isNullOrEmpty(dataValue)) {
												DCIString.Base64Encode("column data is null");
											} else {
												tmp.put(colName,
														DCIString.Base64Encode(cc.getConfigXMLPath() + File.separator
																+ APConstants.getUploadicondir() + File.separator
																+ dataValue + "." + colConfig.get(colName)[1]));
											}
										} else {
											tmp.put(colName, dataValue);
										}
									} else {
										tmp.put(colName, dataValue);
									}
								}
								items.add(tmp);
								cnt++;
								if (cnt > pagesize) {
									break;
								}
							}
						}
					}

				} else {
					items = new ArrayList<HashMap<String, Object>>();
					ConfigControl cc = new ConfigControl(false, s.getTestAreaConfigPath());
					if (rs2.next()) {
						if (rsmd == null) {
							rsmd = rs2.getMetaData();
						}
						iconmap = kbm.getIconIDMap(conn);
						rs2.last();
						totalRow = rs2.getRow();
						rs2.beforeFirst();

						if ((page - 1) * pagesize <= totalRow) {
							if ((page - 1) * pagesize != 0) {
								rs2.absolute((page - 1) * pagesize);
							}
							cnt = 1;
							HashMap<String, String> oridatacols = new HashMap<String, String>();
							// datacols = new ArrayList<String>();

							for (int i = 1; i <= rsmd.getColumnCount(); i++) {
								// datacols.add(rsmd.getColumnName(i));
								oridatacols.put(rsmd.getColumnName(i), "");
							}

							// for (String key : calcInfo.keySet()) {
							// datacols.add(key);
							// }

							while (rs2.next()) {
								tmp = new HashMap<String, Object>();
								for (int i = 1; i <= rsmd.getColumnCount(); i++) {
									colName = rsmd.getColumnName(i);

									if (calcInfo.containsKey(colName)) {
										dataValue = DCINumber.calcNumber(rs2, calcInfo.get(colName));
									} else {
										if (oridatacols.containsKey(colName)
												&& !DCIString.isNullOrEmpty(rs2.getString(colName))) {
											dataValue = rs2.getString(colName).trim();
										} else {
											dataValue = "";
										}
									}

									if (colConfig != null && colConfig.containsKey(colName)) {
										if (colConfig.get(colName)[0].equals(ColumnType.MAPPING.toString())) {
											tmp.put(colName, kbm.mappingAnalysis(colConfig.get(colName)[1], dataValue));
										} else if (colConfig.get(colName)[0].equals(ColumnType.NUM.toString())) {
											tmp.put(colName, DCIString.numFormat(dataValue, colConfig.get(colName)[1]));
										} else if (colConfig.get(colName)[0].equals(ColumnType.LIGHT.toString())) {
											if (iconmap != null) {
												if (!DCIString.isNullOrEmpty(colConfig.get(colName)[1])) {
													dataValue = DCIString.transRangValue(dataValue,
															colConfig.get(colName)[1]);
												}
												if (iconmap.containsKey(dataValue)) {
													tmp.put(colName, iconmap.get(dataValue));
												}
											}
										} else if (colConfig.get(colName)[0].equals(ColumnType.PROGRESS.toString())) {
											tmp.put(colName, kbm.percentFormat(dataValue, colConfig.get(colName)[1]));
										} else if (colConfig.get(colName)[0].equals(ColumnType.MONEY.toString())) {
											if (DCIString.isNullOrEmpty(dataValue)) {
												tmp.put(colName, "");
											} else {
												tmp.put(colName,
														"$" + DCIString.numFormat(dataValue, colConfig.get(colName)[1]));
											}
										} else if (colConfig.get(colName)[0].equals(ColumnType.CHECKBOX.toString())) {
											tmp.put(colName, kbm.CheckBoxFormat(dataValue));
										} else if (colConfig.get(colName)[0].equals(ColumnType.DATE.toString())) {
											tmp.put(colName, DCIString.dateFormat(dataValue, colConfig.get(colName)[1]));
										} else if (colConfig.get(colName)[0].equals(ColumnType.IMAGEMAPPING.toString())) {
											if (DCIString.isNullOrEmpty(dataValue)) {
												DCIString.Base64Encode("column data is null");
											} else {
												tmp.put(colName,
														DCIString.Base64Encode(cc.getConfigXMLPath() + File.separator
																+ APConstants.getUploadicondir() + File.separator
																+ dataValue + "." + colConfig.get(colName)[1]));
											}
										} else {
											tmp.put(colName, dataValue);
										}
									} else {
										tmp.put(colName, dataValue);
									}
								}
								items.add(tmp);
								cnt++;
								if (cnt > pagesize) {
									break;
								}
							}
						}
					}
				}
				datas = new HashMap<String, Object>();
				datas.put("items", items);
				datas.put("total", totalRow);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			totalRow = 0;
			datas = new HashMap<String, Object>();
		} finally {
			this.dbctrl.closeConnection(rs5, ps5, conn5);
			this.dbctrl.closeConnection(rs2, ps2, conn2);
			this.dbctrl.closeConnection(rs3, ps3, null);
			this.dbctrl.closeConnection(rs, ps, conn);
		}
		return datas;
	}

	public String exportFile(String filter, String sort, String sql_id, String conn_id, String colstr, String ctype) {
		ArrayList<HashMap<String, Object>> exceldata = null;
		ArrayList<HashMap<String, String>> cols = null;
		String html = null;
		try {
			exceldata = getExportData(filter, sort, sql_id, conn_id);
			if (exceldata != null && !DCIString.isNullOrEmpty(colstr)) {
				cols = new ObjectMapper().readValue(colstr, new TypeReference<ArrayList<HashMap<String, String>>>() {
				});

				html = "<html><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"><body><table border='1'><tr style='background-color : lightblue;'>";
				for (int j = 0; j < cols.size(); j++) {
					if (!Boolean.parseBoolean(cols.get(j).get("hidden"))) {
						html += "<td style='width:" + cols.get(j).get("width") + "px'>" + cols.get(j).get("text")
								+ "</td>";
					}
				}
				html += "</tr>";
				for (int i = 0; i < exceldata.size(); i++) {
					html += "<tr>";
					for (int j = 0; j < cols.size(); j++) {
						if (!Boolean.parseBoolean(cols.get(j).get("hidden"))) {
							html += "<td>";

							if (ctype.equals("1")) {
								if (exceldata.get(i).get(cols.get(j).get("dataIndex")) == null) {
									html += "=\"\"</td>";
								} else {
									html += "=\"" + exceldata.get(i).get(cols.get(j).get("dataIndex")) + "\"</td>";
								}
							} else {
								if (exceldata.get(i).get(cols.get(j).get("dataIndex")) == null
										|| DCIString.isNullOrEmpty(exceldata.get(i).get(cols.get(j).get("dataIndex"))
												.toString())) {
									html += "&nbsp;</td>";
								} else {
									html += exceldata.get(i).get(cols.get(j).get("dataIndex")) + "</td>";
								}
							}
						}
					}
					html += "</tr>";
				}
				html += "</table></body></html>";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// System.out.println(html);
		return html;
	}

	private ArrayList<HashMap<String, Object>> getExportData(String filter, String sort, String sql_id, String conn_id) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn2 = null;
		PreparedStatement ps2 = null;
		ResultSet rs2 = null;
		PreparedStatement ps3 = null;
		ResultSet rs3 = null;
		Connection conn5 = null;
		PreparedStatement ps5 = null;
		ResultSet rs5 = null;
		ArrayList<HashMap<String, Object>> items = null;
		HashMap<String, Object> tmp = null;
		HashMap<String, String[]> colConfig = null;
		HashMap<String, String> iconmap = null;
		HashMap<String, String> calcInfo = null;
		String sqlcmd = null;
		ResultSetMetaData rsmd = null;
		String colName = null;
		String dataValue = null;
		KanBanMethods kbm = new KanBanMethods(this.cmd, this.dbobj, this.dbctrl, this.currLang);
		int rstp = -1;

		try {
			conn = this.dbobj.getConnection(ConnectionType.SYS);
			ps = conn.prepareStatement(this.cmd.getSqlCommand(), ResultSet.TYPE_FORWARD_ONLY,
					ResultSet.CONCUR_READ_ONLY);
			ps.setString(1, sql_id);
			ps.setString(2, this.uid);
			ps.setString(3, sql_id);
			ps.setString(4, sql_id);
			ps.setString(5, this.uid);
			ps.setString(6, sql_id);
			ps.setString(7, this.uid);
			ps.setString(8, sql_id);
			rs = ps.executeQuery();

			if (rs.next()) {
				ps3 = conn.prepareStatement(this.cmd.getColConfig(), ResultSet.TYPE_FORWARD_ONLY,
						ResultSet.CONCUR_READ_ONLY);
				ps3.setString(1, sql_id);
				ps3.setString(2, sql_id);
				rs3 = ps3.executeQuery();
				if (colConfig == null) {
					colConfig = new HashMap<String, String[]>();
				}
				if (calcInfo == null) {
					calcInfo = new HashMap<String, String>();
				}

				while (rs3.next()) {
					if (rs3.getString("tp").equals("1")) {
						colConfig.put(rs3.getString("col_id"),
								new String[] { rs3.getString("col_type"), rs3.getString("config_value") });
					} else if (rs3.getString("tp").equals("2")) {
						calcInfo.put(rs3.getString("col_id"), rs3.getString("config_value"));
					}
				}

				sqlcmd = rs.getString("sql_context");

				if (!DCIString.isNullOrEmpty(filter)) {
					if (rs.getString("is_cross").equals("1")) {
						if (kbm.sqlCheck(sqlcmd + " \r\n" + filter, conn2)) {
							sqlcmd += " \r\n" + filter;
							// isSqlFilter = true;
						}
					} else {
						sqlcmd += " \r\n" + filter;
						// isSqlFilter = true;
					}
				}

				if (!DCIString.isNullOrEmpty(rs.getString("group_by"))) {
					sqlcmd += "\r\n group by " + rs.getString("group_by");
				}

				if (DCIString.isNullOrEmpty(sort)) {
					if (!DCIString.isNullOrEmpty(rs.getString("order_by"))) {
						sqlcmd += "\r\n order by " + rs.getString("order_by");
					}
				} else {
					sqlcmd += "\r\n order by " + sort;
				}

				if (rs.getString("is_cross").equals("1")) {
					rstp = ResultSet.TYPE_FORWARD_ONLY;
				} else {
					rstp = ResultSet.TYPE_SCROLL_INSENSITIVE;
				}
				conn2 = DataDatabaseObject.getInstance().getConnection(conn_id);
				ps2 = conn2.prepareStatement(sqlcmd, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				rs2 = ps2.executeQuery();

				if (rs.getString("is_cross").equals("1")) {
					// ////cross multi db

					ArrayList<HashMap<String, Object>> crossData = null;
					HashMap<String, Object> crossInfos = null;
					HashMap<String, Object> ctmp = null;
					ResultSet[] rss = null;
					ArrayList<String[]> cols = null;
					CrossDBType ctp = null;
					int ccnt = 0;

					crossInfos = crossInfoBuilder(conn, sql_id, filter, sort);
					if (crossInfos != null && crossInfos.size() > 0) {
						cols = new ArrayList<String[]>();
						rss = new ResultSet[crossInfos.size() + 1];
						rss[0] = rs2;
						ccnt = 0;
						for (String k : crossInfos.keySet()) {
							ctmp = (HashMap<String, Object>) crossInfos.get(k);
							if (ccnt == 0) {
								ctp = CrossDBType.valueOf(ctmp.get("type").toString());
							}
							ccnt++;
							rss[ccnt] = (ResultSet) ctmp.get("rs");
							cols.addAll((ArrayList<String[]>) ctmp.get("keys"));
						}

						if (ctp != null) {
							if (ctp == CrossDBType.join) {
								crossData = new DatabaseFuncs().JoinResult(rss, cols);
							} else if (ctp == CrossDBType.left_join) {
								crossData = new DatabaseFuncs().LeftJoinResult(rss, cols);
							} else {
								crossData = new DatabaseFuncs().UnionAllResult(rss);
							}
						}

					}

					// conn5 =
					// DataDatabaseObject.getInstance().getConnection(rs.getString("cross_conn_id"));
					// sqlcmd = rs.getString("cross_sql_context");
					//
					// if (!DCIString.isNullOrEmpty(filter)) {
					// if (kbm.sqlCheck(sqlcmd + " \r\n" + filter, conn5)) {
					// sqlcmd += " \r\n" + filter;
					// // isSqlFilter = true;
					// }
					// }
					//
					// if
					// (!DCIString.isNullOrEmpty(rs.getString("cross_group_by")))
					// {
					// sqlcmd += "\r\n group by " +
					// rs.getString("cross_group_by");
					// }
					//
					// if (DCIString.isNullOrEmpty(sort)) {
					// if
					// (!DCIString.isNullOrEmpty(rs.getString("cross_order_by")))
					// {
					// sqlcmd += "\r\n order by " +
					// rs.getString("cross_order_by");
					// }
					// }
					//
					// ps5 = conn5.prepareStatement(sqlcmd,
					// ResultSet.TYPE_SCROLL_INSENSITIVE,
					// ResultSet.CONCUR_READ_ONLY);
					// rs5 = ps5.executeQuery();
					//
					// ArrayList<Object> crossData = null;
					// if
					// (rs.getString("cross_type").equals(CrossDBType.join.toString()))
					// {
					// ArrayList<String[]> joinKeys = null;
					// joinKeys = new ArrayList<String[]>(0);
					// joinKeys.add(new String[] {
					// rs.getString("join_key_set1"),
					// rs.getString("join_key_set2") });
					// crossData = new DatabaseFuncs().JoinResult(rs2, rs5,
					// joinKeys);
					// } else if
					// (rs.getString("cross_type").equals(CrossDBType.left_join.toString()))
					// {
					//
					// } else {
					// crossData = new DatabaseFuncs().UnionAllResult(rs2, rs5);
					// }

					items = new ArrayList<HashMap<String, Object>>();
					if (crossData != null && crossData.size() > 0) {
						// ConfigControl cc = new ConfigControl();
						HashMap<String, Object> firstRow = null;
						HashMap<String, Object> row = null;
						iconmap = kbm.getIconNameMap(conn);

						firstRow = (HashMap<String, Object>) crossData.get(0);

						// datacols = new ArrayList<String>();
						//
						// for (String key : firstRow.keySet()) {
						// datacols.add(key);
						// }
						//
						// for (String key : calcInfo.keySet()) {
						// datacols.add(key);
						// }
						for (int p = 0; p < crossData.size(); p++) {
							row = (HashMap<String, Object>) crossData.get(p);
							tmp = new HashMap<String, Object>();

							for (String key : firstRow.keySet()) {
								// for (int i = 0; i < datacols.size(); i++) {
								// colName = datacols.get(i);
								colName = key;

								if (calcInfo.containsKey(colName)) {
									dataValue = DCINumber.calcNumber(row, calcInfo.get(colName));
								} else {
									if (row.containsKey(colName) && row.get(colName) != null
											&& !DCIString.isNullOrEmpty(row.get(colName).toString())) {
										dataValue = row.get(colName).toString().trim();
									} else {
										dataValue = "";
									}
								}

								if (colConfig != null && colConfig.containsKey(colName)) {
									if (colConfig.get(colName)[0].equals(ColumnType.MAPPING.toString())) {
										tmp.put(colName, kbm.mappingAnalysis(colConfig.get(colName)[1], dataValue));
									} else if (colConfig.get(colName)[0].equals(ColumnType.NUM.toString())) {
										tmp.put(colName, DCIString.numFormat(dataValue, colConfig.get(colName)[1]));
									} else if (colConfig.get(colName)[0].equals(ColumnType.PROGRESS.toString())) {
										tmp.put(colName, kbm.percentFormat(dataValue, colConfig.get(colName)[1]));
									} else if (colConfig.get(colName)[0].equals(ColumnType.LIGHT.toString())) {
										if (iconmap != null) {
											if (!DCIString.isNullOrEmpty(colConfig.get(colName)[1])) {
												dataValue = DCIString.transRangValue(dataValue,
														colConfig.get(colName)[1]);
											}
											if (iconmap.containsKey(dataValue)) {
												tmp.put(colName, iconmap.get(dataValue));
											}
										}
									} else if (colConfig.get(colName)[0].equals(ColumnType.MONEY.toString())) {
										if (DCIString.isNullOrEmpty(dataValue)) {
											tmp.put(colName, "");
										} else {
											tmp.put(colName,
													"$" + DCIString.numFormat(dataValue, colConfig.get(colName)[1]));
										}
									} else if (colConfig.get(colName)[0].equals(ColumnType.CHECKBOX.toString())) {
										tmp.put(colName, kbm.CheckBoxFormat(dataValue));
									} else if (colConfig.get(colName)[0].equals(ColumnType.DATE.toString())) {
										tmp.put(colName, DCIString.dateFormat(dataValue, colConfig.get(colName)[1]));
									} else {
										tmp.put(colName, dataValue);
									}
								} else {
									tmp.put(colName, dataValue);
								}
							}
							items.add(tmp);
						}
					}

				} else {
					items = new ArrayList<HashMap<String, Object>>();
					// ConfigControl cc = new ConfigControl();
					if (rs2.next()) {
						if (rsmd == null) {
							rsmd = rs2.getMetaData();
						}
						iconmap = kbm.getIconNameMap(conn);
						rs2.beforeFirst();

						HashMap<String, String> oridatacols = new HashMap<String, String>();
						// datacols = new ArrayList<String>();
						//
						for (int i = 1; i <= rsmd.getColumnCount(); i++) {
							// datacols.add(rsmd.getColumnName(i));
							oridatacols.put(rsmd.getColumnName(i), null);
						}
						//
						// for (String key : calcInfo.keySet()) {
						// datacols.add(key);
						// }

						while (rs2.next()) {
							tmp = new HashMap<String, Object>();
							for (int i = 1; i <= rsmd.getColumnCount(); i++) {
								colName = rsmd.getColumnName(i);

								if (calcInfo.containsKey(colName)) {
									dataValue = DCINumber.calcNumber(rs2, calcInfo.get(colName));
								} else {
									if (oridatacols.containsKey(colName)
											&& !DCIString.isNullOrEmpty(rs2.getString(colName))) {
										dataValue = rs2.getString(colName).trim();
									} else {
										dataValue = "";
									}
								}

								if (colConfig != null && colConfig.containsKey(colName)) {
									if (colConfig.get(colName)[0].equals(ColumnType.MAPPING.toString())) {
										tmp.put(colName, kbm.mappingAnalysis(colConfig.get(colName)[1], dataValue));
									} else if (colConfig.get(colName)[0].equals(ColumnType.NUM.toString())) {
										tmp.put(colName, DCIString.numFormat(dataValue, colConfig.get(colName)[1]));
									} else if (colConfig.get(colName)[0].equals(ColumnType.PROGRESS.toString())) {
										tmp.put(colName, kbm.percentFormat(dataValue, colConfig.get(colName)[1]));
									} else if (colConfig.get(colName)[0].equals(ColumnType.LIGHT.toString())) {
										if (iconmap != null) {
											if (!DCIString.isNullOrEmpty(colConfig.get(colName)[1])) {
												dataValue = DCIString.transRangValue(dataValue,
														colConfig.get(colName)[1]);
											}
											if (iconmap.containsKey(dataValue)) {
												tmp.put(colName, iconmap.get(dataValue));
											}
										}
									} else if (colConfig.get(colName)[0].equals(ColumnType.MONEY.toString())) {
										if (DCIString.isNullOrEmpty(dataValue)) {
											tmp.put(colName, "");
										} else {
											tmp.put(colName,
													"$" + DCIString.numFormat(dataValue, colConfig.get(colName)[1]));
										}
									} else if (colConfig.get(colName)[0].equals(ColumnType.CHECKBOX.toString())) {
										tmp.put(colName, kbm.CheckBoxFormat(dataValue));
									} else if (colConfig.get(colName)[0].equals(ColumnType.DATE.toString())) {
										tmp.put(colName, DCIString.dateFormat(dataValue, colConfig.get(colName)[1]));
									} else {
										tmp.put(colName, dataValue);
									}
								} else {
									tmp.put(colName, dataValue);
								}
							}
							items.add(tmp);
						}
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			items = new ArrayList<HashMap<String, Object>>();
		} finally {
			this.dbctrl.closeConnection(rs5, ps5, conn5);
			this.dbctrl.closeConnection(rs2, ps2, conn2);
			this.dbctrl.closeConnection(rs3, ps3, null);
			this.dbctrl.closeConnection(rs, ps, conn);
		}
		return items;
	}

	public HashMap<String, Object> getMarqueeData(String sql_id, String conn_id) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		HashMap<String, Object> datas = null;
		ArrayList<HashMap<String, Object>> marquee = null;
		HashMap<String, Object> tmp = null;

		try {
			conn = this.dbobj.getConnection(ConnectionType.SYS);
			Calendar c = Calendar.getInstance();
			String currtime = DCIDate.parseString(c.getTime(), "yyyyMMddHHmmss");
			ps = conn.prepareStatement(this.cmd.getMarqueeData());
			ps.setString(1, sql_id);
			ps.setString(2, conn_id);
			ps.setString(3, currtime);
			ps.setString(4, currtime);
			rs = ps.executeQuery();
			marquee = new ArrayList<HashMap<String, Object>>();

			while (rs.next()) {
				if (rs.getString("marquee_type").equals("1")) {
					tmp = new HashMap<String, Object>();
					tmp.put("message", rs.getString("message"));
					marquee.add(tmp);
				} else {
					getAdvanceMarquee(conn_id, rs.getString("message"), marquee);
				}
			}

			datas = new HashMap<String, Object>();
			datas.put("marquee", marquee);

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			this.dbctrl.closeConnection(rs, ps, conn);
		}

		return datas;
	}

	private void getAdvanceMarquee(String conn_id, String sql, ArrayList<HashMap<String, Object>> marquee) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		HashMap<String, Object> tmp = null;
		ResultSetMetaData rsmd = null;
		String tmpstr = null;

		try {
			conn = DataDatabaseObject.getInstance().getConnection(conn_id);
			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();

			if (marquee == null) {
				marquee = new ArrayList<HashMap<String, Object>>();
			}

			if (rs.next()) {
				if (rsmd == null) {
					rsmd = rs.getMetaData();
				}
				rs.beforeFirst();
				while (rs.next()) {
					for (int i = 1; i <= rsmd.getColumnCount(); i++) {
						if (i == 1) {
							tmpstr = rs.getString(rsmd.getColumnName(i)).trim();
						} else {
							tmpstr += "-" + rs.getString(rsmd.getColumnName(i)).trim();
						}
					}
					tmp = new HashMap<String, Object>();
					tmp.put("message", tmpstr);
					marquee.add(tmp);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			this.dbctrl.closeConnection(rs, ps, conn);
		}
	}

	public HashMap<String, Object> getFuncInfo(String conn_id, String func_id) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		HashMap<String, Object> tmp = null;

		try {
			conn = this.dbobj.getConnection(ConnectionType.SYS);
			ps = conn.prepareStatement(this.cmd.getFuncInfo());
			ps.setString(1, this.uid);
			ps.setString(2, conn_id);
			ps.setString(3, func_id);
			ps.setString(4, this.uid);
			ps.setString(5, conn_id);
			ps.setString(6, func_id);
			rs = ps.executeQuery();

			if (rs.next()) {
				tmp = new HashMap<String, Object>();
				tmp.put("url", rs.getString("func_url"));
				tmp.put("text", Singleton.getInstance().getLanguage(this.currLang, rs.getString("lang_key")));
				tmp.put("func_package", rs.getString("package"));
				tmp.put("can_edit", rs.getString("can_edit"));
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			this.dbctrl.closeConnection(rs, ps, conn);
		}

		return tmp;
	}

	public HashMap<String, Object> saveFromat(String formDatas, String grid_id, String coldatas) {
		Connection conn = null;
		PreparedStatement ps = null;
		HashMap<String, Object> datas = null;
		HashMap<String, Object> result = null;
		boolean boolResult = false;
		String resultMsg = "";

		try {
			conn = this.dbobj.getConnection(ConnectionType.SYS);
			ps = conn.prepareStatement(this.cmd.deleteSqlFormat());

			ps.setString(1, this.uid);
			ps.setString(2, grid_id);
			ps.executeUpdate();
			if (!formDatas.equals("dodelete")) {
				this.dbctrl.closeConnection(null, ps, null);
				datas = DCIString.jsonTranToObjMap(formDatas);
				// new
				// ObjectMapper().readValue(formDatas,
				// HashMap.class);

				ps = conn.prepareStatement(this.cmd.addSqlFromat(), ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);

				ps.setString(1, this.uid);
				ps.setString(2, grid_id);
				ps.setString(3, datas.get("fs").toString());
				ps.setString(4, datas.get("fc").toString());
				ps.setString(5, datas.get("rh").toString());
				ps.setString(6, datas.get("bc").toString());
				ps.setString(7, datas.get("popup_width").toString());
				ps.setString(8, datas.get("bcEven").toString());
				ps.executeUpdate();
			}
			new APPubMethods().editGridFormat(this.uid, grid_id, coldatas);
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

	public HashMap<String, Object> saveData(String formDatas, String sql_id, String advDatas) {
		Connection conn = null;
		PreparedStatement ps = null;
		HashMap<String, Object> datas = null;
		HashMap<String, Object> result = null;
		boolean boolResult = false;
		String resultMsg = "";

		try {
			conn = this.dbobj.getConnection(ConnectionType.SYS);

			ps = conn.prepareStatement(this.cmd.deleteSqlCondition());

			ps.setString(1, this.uid);
			ps.setString(2, sql_id);
			ps.executeUpdate();
			if (formDatas.equals("dodelete")) {
				advDataEditor(null, sql_id, null);
			} else {
				this.dbctrl.closeConnection(null, ps, null);
				datas = DCIString.jsonTranToObjMap(formDatas);
				// new ObjectMapper().readValue(formDatas, HashMap.class);

				ps = conn.prepareStatement(this.cmd.addSqlCondition(), ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);

				ps.setString(1, this.uid);
				ps.setString(2, sql_id);
				ps.setString(3, datas.get("condi_col").toString());
				ps.setString(4, datas.get("condi_type").toString());
				ps.setString(5, datas.get("condi_value").toString());
				ps.setString(6, datas.get("sort_col").toString());
				ps.setString(7, datas.get("sort_type").toString());
				ps.setString(8, datas.get("page_size").toString());
				ps.executeUpdate();

				advDataEditor(conn, sql_id, advDatas);
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

	public HashMap<String, Object> saveInitParams(String sql_id, String paramsValue) {
		Connection conn = null;
		PreparedStatement ps = null;
		HashMap<String, Object> result = null;
		boolean boolResult = false;
		String resultMsg = "";

		try {
			conn = this.dbobj.getConnection(ConnectionType.SYS);
			ps = conn.prepareStatement(this.cmd.deleteSqlInitParams());
			ps.setString(1, sql_id);
			ps.setString(2, this.uid);
			ps.executeUpdate();

			this.dbctrl.closeConnection(null, ps, null);

			ps = conn.prepareStatement(this.cmd.addSqlInitParams());

			ps.setString(1, sql_id);
			ps.setString(2, this.uid);
			ps.setString(3, paramsValue);
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

	private void advDataEditor(Connection conn, String sql_id, String advDatas) throws Exception {
		PreparedStatement ps = null;
		boolean reconn = false;
		ArrayList<HashMap<String, String>> datas = null;
		HashMap<String, String> tmp = null;

		try {
			if (conn == null || conn.isClosed()) {
				conn = this.dbobj.getConnection(ConnectionType.SYS);
				reconn = true;
			}

			ps = conn.prepareStatement(this.cmd.deleteAdvCondition(), ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			ps.setString(1, this.uid);
			ps.setString(2, sql_id);
			ps.executeUpdate();

			this.dbctrl.closeConnection(null, ps, null);

			if (!DCIString.isNullOrEmpty(advDatas)) {
				datas = new ObjectMapper().readValue(advDatas, new TypeReference<ArrayList<HashMap<String, String>>>() {
				});
				ps = conn.prepareStatement(this.cmd.addAdvCondition(), ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
				for (int i = 0; i < datas.size(); i++) {
					tmp = datas.get(i);
					ps.setString(1, this.uid);
					ps.setString(2, sql_id);
					ps.setString(3, "A" + DCIString.strFix(String.valueOf(i + 1), 3, true, "0"));
					ps.setString(4, tmp.get("condi_relation"));
					ps.setString(5, tmp.get("condi_col"));
					ps.setString(6, tmp.get("condi_type"));
					ps.setString(7, tmp.get("condi_value"));
					ps.setString(8, tmp.get("seq"));
					ps.setString(9, tmp.get("condi_col_display"));
					ps.setString(10, tmp.get("condi_type_display"));
					ps.setString(11, tmp.get("condi_value_display"));
					ps.addBatch();

					if (i != 0 && (i % 100) == 0) {
						ps.executeBatch();
						ps.clearBatch();
					}
				}

				ps.executeBatch();
			}
		} catch (Exception ex) {
			throw ex;
		} finally {

			if (reconn) {
				this.dbctrl.closeConnection(null, ps, conn);
			} else {
				this.dbctrl.closeConnection(null, ps, null);
			}
		}

	}

	private String lightLegendBuilder(String legendStr) {
		String legend = null;
		String[] legends = null;
		String[] item = null;
		// Singleton s = Singleton.getInstance();
		try {
			if (!DCIString.isNullOrEmpty(legendStr)) {
				legends = legendStr.split(";");

				if (legends != null && legends.length > 0) {
					for (int i = 0; i < legends.length; i++) {
						if (legends[i].indexOf("=") != -1) {
							item = legends[i].split("=");
							if (DCIString.isNullOrEmpty(legend)) {
								legend = "<table><tr><td><font size='3'>$$$kn$$$</font></td>";
							}

							legend += "<td><img src='./../../ImageLoader.dsc?iconid=" + DCIString.Base64Encode(item[0])
									+ "' width='20' height='20'/></td><td><font size='3'>" + item[1] + "</font></td>";
						}
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			legend = "";
		} finally {
			if (DCIString.isNullOrEmpty(legend)) {
				legend = "";
			} else {
				legend += "</tr></table>";
			}
		}
		return legend;
	}

	private HashMap<String, Object> crossInfoBuilder(Connection conn, String sql_id, String filter, String sort) {
		boolean reconn = false;
		Connection subconn = null;
		PreparedStatement ps = null;
		PreparedStatement subps = null;
		ResultSet rs = null;
		// ResultSet subrs = null;
		HashMap<String, Object> infos = null;
		HashMap<String, Object> subInfo = null;
		// ResultSet[] rss = null;
		// ArrayList<ResultSet> arrRs = null;
		ArrayList<String[]> cols = null;
		String lastCid = "";
		String sqlcmd = null;
		KanBanMethods kbm = new KanBanMethods(this.cmd, this.dbobj, this.dbctrl, this.currLang);

		try {
			if (conn == null || conn.isClosed()) {
				conn = this.dbobj.getConnection(ConnectionType.SYS);
				reconn = true;
			}

			ps = conn.prepareStatement(this.cmd.getCrossDBInfo());
			ps.setString(1, sql_id);
			ps.setString(2, sql_id);
			rs = ps.executeQuery();
			// arrRs = new ArrayList<ResultSet>();
			while (rs.next()) {
				if (!DCIString.isNullOrEmpty(rs.getString("cross_type"))
						&& !DCIString.isNullOrEmpty(rs.getString("conn_id"))
						&& !DCIString.isNullOrEmpty(rs.getString("sql_context"))) {
					if (!rs.getString("cross_id").equals(lastCid)) {
						if (infos == null) {
							infos = new HashMap<String, Object>();
						}
						if (!DCIString.isNullOrEmpty(lastCid)) {
							subInfo.put("keys", cols);
							infos.put(lastCid, subInfo);
						}
						cols = new ArrayList<String[]>();
						subInfo = new HashMap<String, Object>();
						try {
							subconn = DataDatabaseObject.getInstance().getConnection(rs.getString("conn_id"));
							sqlcmd = rs.getString("sql_context");

							if (!DCIString.isNullOrEmpty(filter)) {
								if (kbm.sqlCheck(sqlcmd + " \r\n" + filter, subconn)) {
									sqlcmd += " \r\n" + filter;
								}
							}

							if (!DCIString.isNullOrEmpty(rs.getString("group_by"))) {
								sqlcmd += "\r\n group by " + rs.getString("group_by");
							}

							if (DCIString.isNullOrEmpty(sort)) {
								if (!DCIString.isNullOrEmpty(rs.getString("order_by"))) {
									sqlcmd += "\r\n order by " + rs.getString("order_by");
								}
							}

							subps = subconn.prepareStatement(sqlcmd, ResultSet.TYPE_SCROLL_INSENSITIVE,
									ResultSet.CONCUR_READ_ONLY);

							subInfo.put("rs", subps.executeQuery());
							subInfo.put("type", rs.getString("cross_type"));
						} catch (Exception e) {
							e.printStackTrace();
						} finally {
							// this.dbctrl.closeConnection(null, subps,
							// subconn);
						}
					}
					if (rs.getString("cross_type").equals(CrossDBType.join.toString())
							|| rs.getString("cross_type").equals(CrossDBType.left_join.toString())) {
						if (cols == null) {
							cols = new ArrayList<String[]>();
						}

						cols.add(new String[] {
								new String(new byte[] { Byte.parseByte(String.valueOf(65 + rs.getInt("key_belong1"))) })
										+ "." + rs.getString("key_col1"),
								new String(new byte[] { Byte.parseByte(String.valueOf(65 + rs.getInt("key_belong2"))) })
										+ "." + rs.getString("key_col2") });
					}

					lastCid = rs.getString("cross_id");
				}
			}

			if (subInfo != null && subInfo.size() > 0) {
				subInfo.put("keys", cols);
				infos.put(lastCid, subInfo);
			}

		} catch (Exception ex) {
			infos = new HashMap<String, Object>();
			ex.printStackTrace();
		} finally {
			if (reconn) {
				this.dbctrl.closeConnection(rs, ps, conn);
			} else {
				this.dbctrl.closeConnection(rs, ps, null);
			}
		}

		return infos;
	}
}
