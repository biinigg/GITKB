package com.dsc.dci.jweb.funcs.ekb.pe001;

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
import com.dsc.dci.sqlcode.funcs.ekb.sqlPE001;

public class PE001 {
	private sqlPE001 cmd;
	private DatabaseObjects dbobj;
	private DBControl dbctrl;
	private String currLang;
	private String uid;

	public PE001(HttpSession session, String uid) {
		this.dbobj = DatabaseObjects.getInstance();
		this.cmd = new sqlPE001();
		this.dbctrl = new DBControl();
		APControl apc = new APControl();
		if (session == null) {
			this.currLang = null;
		} else {
			this.currLang = apc.getUserInfoFromSession(session, uid, "lang");
		}
		this.uid = uid;
	}

	public HashMap<String, Object> getInitData(String conn_id, String uid) {
		Connection conn = null;
		Connection conn1 = null;
		Connection conn2 = null;
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		ResultSet rs = null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		HashMap<String, Object> datas = null;
		HashMap<String, Object> condis = null;
		HashMap<String, Object> tmp = null;
		ArrayList<HashMap<String, Object>> cols = null;
		String colSet = "";
		ArrayList<HashMap<String, Object>> fields = null;
		String columnName = null;
		try {
			// 日清日結管制表grid的columns動態產生設置
			conn = this.dbobj.getConnection(ConnectionType.SYS);
			ps = conn.prepareStatement(this.cmd.getSqlData().replace("%conn_id%", conn_id),
					ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			// ps.setString(1, "CSYS");
			rs = ps.executeQuery();
			ResultSetMetaData reMeta = rs.getMetaData();
			cols = new ArrayList<HashMap<String, Object>>();
			if (rs.next()) {
				for (int i = 1; i <= reMeta.getColumnCount(); i++) {
					tmp = new HashMap<String, Object>();
					columnName = reMeta.getColumnName(i);
					tmp.put("dataIndex", columnName);
					tmp.put("header", columnName);
					if (i == 1) {
						colSet = columnName;
					} else {
						colSet += ";" + columnName;
					}
					cols.add(tmp);
				}
			}
			this.dbctrl.closeConnection(rs, ps, conn);
			// Grid資料篩選條件:1.Process_Exception_History.conn_id 聯集
			// 2.角色權限控制Role_Rule_Info.func_id
			conn1 = this.dbobj.getConnection(ConnectionType.SYS);
			ps1 = conn1.prepareStatement(this.cmd.getRoleGrantKBID(), ResultSet.TYPE_FORWARD_ONLY,
					ResultSet.CONCUR_READ_ONLY);
			ps1.setString(1, conn_id);
			ps1.setString(2, uid);
			rs1 = ps1.executeQuery();
			condis = new HashMap<String, Object>();
			int x = 0;
			while (rs1.next()) {
				condis.put("" + x, rs1.getString("func_id"));
				x++;
			}
			this.dbctrl.closeConnection(rs1, ps1, conn1);

			conn2 = this.dbobj.getConnection(ConnectionType.SYS);
			ps2 = conn2.prepareStatement(this.cmd.getSqlData().replace("%conn_id%", conn_id),
					ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			rs2 = ps2.executeQuery();
			ResultSetMetaData reMeta2 = rs2.getMetaData();
			fields = new ArrayList<HashMap<String, Object>>();
			int flag = 0;
			while (rs2.next()) {
				tmp = new HashMap<String, Object>();
				for (int i = 1; i <= reMeta2.getColumnCount(); i++) {
					columnName = reMeta2.getColumnName(i);
					tmp.put(columnName, rs2.getObject(i));
					if (condis.containsValue(rs2.getObject(i))) {
						flag = 1;
					}
				}
				if (flag == 1) {
					fields.add(tmp);
					flag = 0;
				}
			}
			this.dbctrl.closeConnection(rs2, ps2, conn2);
			datas = new HashMap<String, Object>();
			datas.put("cols", cols); // var gridColumns
			datas.put("fields", fields);// grid data
			datas.put("colSet", colSet);// columns head
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			this.dbctrl.closeConnection(rs, ps, conn);
		}
		return datas;
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
		HashMap<String, Object> tmp = null;
		HashMap<String, Object> tmp2 = null;
		HashMap<String, Object> tmp3 = null;
		HashMap<String, Object> tmp4 = null;
		HashMap<String, Object> tmp5 = null;
		String fields = null;
		String legendValue = null;
		String popupImg = null;
		String popupTitle = null;

		try {
			conn = this.dbobj.getConnection(ConnectionType.SYS);
			ps1 = conn.prepareStatement(this.cmd.getSqlColumns());
			ps1.setString(1, this.currLang);
			ps1.setString(2, sql_id);
			rs1 = ps1.executeQuery();
			cols = new ArrayList<HashMap<String, Object>>();
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

				if (rs1.getString("col_type").equals(ColumnType.LIGHT.toString())) {
					tmp.put("xtype", "dcilightcolumn");
					tmp.put("align", "center");
				} else if (rs1.getString("col_type").equals(ColumnType.NUM.toString())) {
					tmp.put("align", "right");
				} else if (rs1.getString("col_type").equals(ColumnType.MONEY.toString())) {
					tmp.put("align", "right");
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

				if (DCIString.isNullOrEmpty(fields)) {
					fields = rs1.getString("col_id");
				} else {
					fields += ";" + rs1.getString("col_id");
				}

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

			ps = conn.prepareStatement(this.cmd.getSqlCommand());
			ps.setString(1, sql_id);
			ps.setString(2, this.uid);
			ps.setString(3, sql_id);
			ps.setString(4, sql_id);
			ps.setString(5, this.uid);
			ps.setString(6, sql_id);
			rs = ps.executeQuery();

			if (rs.next()) {
				legendValue = rs.getString("legend");
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

			ps = conn.prepareStatement(this.cmd.getAllLights(), ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			rs = ps.executeQuery();

			while (rs.next()) {
				tmp4 = new HashMap<String, Object>();
				tmp4.put("label",
						Singleton.getInstance().getLanguage(this.currLang, rs.getString("icon_name").replace(" ", "_")));
				tmp4.put("value", rs.getString("icon_map_key"));
				lights.add(tmp4);
			}

			this.dbctrl.closeConnection(rs, ps, null);

			ps = conn.prepareStatement(this.cmd.getAdvCondition(), ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
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

			ps = conn.prepareStatement(this.cmd.getSqlRelation(), ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
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
		PE001Methods kbm = new PE001Methods(this.cmd, this.dbobj, this.dbctrl, this.currLang);
		boolean isSqlFilter = false;
		int rstp = -1;
		Singleton s = Singleton.getInstance();

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
					conn5 = DataDatabaseObject.getInstance().getConnection(rs.getString("cross_conn_id"));
					sqlcmd = rs.getString("cross_sql_context");

					if (!DCIString.isNullOrEmpty(filter)) {
						if (kbm.sqlCheck(sqlcmd + " \r\n" + filter, conn5)) {
							sqlcmd += " \r\n" + filter;
							isSqlFilter = true;
						}
					}

					if (!DCIString.isNullOrEmpty(rs.getString("cross_group_by"))) {
						sqlcmd += "\r\n group by " + rs.getString("cross_group_by");
					}

					if (DCIString.isNullOrEmpty(sort)) {
						if (!DCIString.isNullOrEmpty(rs.getString("cross_order_by"))) {
							sqlcmd += "\r\n order by " + rs.getString("cross_order_by");
						}
					}
					ps5 = conn5.prepareStatement(sqlcmd, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
					rs5 = ps5.executeQuery();

					ArrayList<Object> crossData = null;
					if (rs.getString("cross_type").equals(CrossDBType.join.toString())) {
						ArrayList<String[]> joinKeys = null;
						joinKeys = new ArrayList<String[]>(0);
						joinKeys.add(new String[] { rs.getString("join_key_set1"), rs.getString("join_key_set2") });
						crossData = new DatabaseFuncs().JoinResult(rs2, rs5, joinKeys);
					} else {
						crossData = new DatabaseFuncs().UnionAllResult(rs2, rs5);
					}

					items = new ArrayList<HashMap<String, Object>>();
					if (crossData != null && crossData.size() > 0) {
						ConfigControl cc = new ConfigControl(s.isTestArea(), s.getTestAreaConfigPath());
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
					ConfigControl cc = new ConfigControl(s.isTestArea(), s.getTestAreaConfigPath());
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
//			System.out.println("exceldata:" + exceldata);
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
//		System.out.println("***");
//		System.out.println(html);
//		System.out.println("===");
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
		PE001Methods kbm = new PE001Methods(this.cmd, this.dbobj, this.dbctrl, this.currLang);
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
					sqlcmd = rs.getString("cross_sql_context");

					if (!DCIString.isNullOrEmpty(filter)) {
						if (kbm.sqlCheck(sqlcmd + " \r\n" + filter, conn5)) {
							sqlcmd += " \r\n" + filter;
							// isSqlFilter = true;
						}
					}

					if (!DCIString.isNullOrEmpty(rs.getString("cross_group_by"))) {
						sqlcmd += "\r\n group by " + rs.getString("cross_group_by");
					}

					if (DCIString.isNullOrEmpty(sort)) {
						if (!DCIString.isNullOrEmpty(rs.getString("cross_order_by"))) {
							sqlcmd += "\r\n order by " + rs.getString("cross_order_by");
						}
					}

					conn5 = DataDatabaseObject.getInstance().getConnection(rs.getString("cross_conn_id"));
					ps5 = conn5.prepareStatement(sqlcmd, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
					rs5 = ps5.executeQuery();

					ArrayList<Object> crossData = null;
					if (rs.getString("cross_type").equals(CrossDBType.join.toString())) {
						ArrayList<String[]> joinKeys = null;
						joinKeys = new ArrayList<String[]>(0);
						joinKeys.add(new String[] { rs.getString("join_key_set1"), rs.getString("join_key_set2") });
						crossData = new DatabaseFuncs().JoinResult(rs2, rs5, joinKeys);
					} else {
						crossData = new DatabaseFuncs().UnionAllResult(rs2, rs5);
					}

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

	// ****日清日結
	public String getConnid(String func_id, String user_id) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String data = null;
		try {
			conn = this.dbobj.getConnection(ConnectionType.SYS);
			ps = conn.prepareStatement(this.cmd.getConn_id());
			ps.setString(1, func_id);
			ps.setString(2, user_id);
			rs = ps.executeQuery();
			if (rs.next()) {
				data = rs.getString("conn_id");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
//			System.out.println(data);
		} finally {
			this.dbctrl.closeConnection(rs, ps, conn);
		}
		return data;
	}

	// @@@@
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
				datas = new ObjectMapper().readValue(formDatas, HashMap.class);

				ps = conn.prepareStatement(this.cmd.addSqlFromat(), ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);

				ps.setString(1, this.uid);
				ps.setString(2, grid_id);
				ps.setString(3, datas.get("fs").toString());
				ps.setString(4, datas.get("fc").toString());
				ps.setString(5, datas.get("rh").toString());
				ps.setString(6, datas.get("bc").toString());
				ps.setString(7, datas.get("popup_width").toString());
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
			if (!formDatas.equals("dodelete")) {
				this.dbctrl.closeConnection(null, ps, null);
				datas = new ObjectMapper().readValue(formDatas, HashMap.class);

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

	// ****from SQLEditor().getSqlColumns
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
						cross_sql_cmd = rs.getString("cross_sql_context") + " and 1=2 ";
						if (!DCIString.isNullOrEmpty(rs.getString("cross_group_by"))) {
							cross_sql_cmd += rs.getString("cross_group_by");
						}
						conn3 = DataDatabaseObject.getInstance().getConnection(rs.getString("cross_conn_id"));
						ps3 = conn3.prepareStatement(cross_sql_cmd, ResultSet.TYPE_SCROLL_INSENSITIVE,
								ResultSet.CONCUR_READ_ONLY);
						rs3 = ps3.executeQuery();
						if (rs3 != null) {
							ResultSetMetaData rsmd2 = rs3.getMetaData();

							HashMap<String, String> joincols = new DatabaseFuncs().colnameRepeat(rsmd, rsmd2);

							if (joincols != null) {
								int cnt = 1;
								cols = new ArrayList<HashMap<String, Object>>();
								for (String key : joincols.keySet()) {
									if (hasExistCols) {
										if (existCol.containsKey(joincols.get(key))) {
											existtmp = existCol.get(joincols.get(key));
											tmp = new HashMap<String, Object>();
											tmp.put("sql_id", sql_id);
											tmp.put("col_id", joincols.get(key));
											tmp.put("col_lang_key", sql_id + "_" + joincols.get(key));
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
											tmp = setColData(sql_id, joincols.get(key), cnt);
										}
									} else {
										tmp = setColData(sql_id, joincols.get(key), cnt);
									}
									cols.add(tmp);
									cnt++;
								}
							}
						}
					}
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
		tmp.put("chs", Singleton.getInstance().getLanguage("CHS", col_id));
		tmp.put("cht", Singleton.getInstance().getLanguage("CHT", col_id));
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
		String id = null;
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
			id = "";
		} finally {
			if (reconn) {
				this.dbctrl.closeConnection(rs, ps, conn);
			} else {
				this.dbctrl.closeConnection(rs, ps, null);
			}
		}
		return datas;
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
			ps = conn.prepareStatement(this.cmd.getSqlRelationEditor(), ResultSet.TYPE_SCROLL_INSENSITIVE,
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

	public HashMap<String, Object> saveBody(HashMap<String, Object> deldatas) {
		Connection conn = null;
		PreparedStatement ps = null;
		ArrayList<HashMap<String, Object>> datas = null;
		ArrayList<HashMap<String, String>> relations = null;
		HashMap<String, Object> result = null;
		boolean boolResult = false;
		String resultMsg = "";
		String[][] langs = null;

		try {
			datas = (ArrayList<HashMap<String, Object>>) deldatas.get("items");
			conn = this.dbobj.getConnection(ConnectionType.SYS);
			if (datas.size() > 0) {
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
				if (datas.get(i).get("write_data") != null) {
					if (!DCIString.isNullOrEmpty(datas.get(i).get("write_data").toString())) {
						relations = new ObjectMapper().readValue(datas.get(i).get("write_data").toString(),
								new TypeReference<ArrayList<HashMap<String, String>>>() {
								});
					}
				}

				sqlRelationEditor(conn, datas.get(0).get("sql_id").toString(), datas.get(0).get("col_id").toString(),
						relations);
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
					if (!DCIString.isNullOrEmpty(langs[i][2])) {
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

	// @@@@
}