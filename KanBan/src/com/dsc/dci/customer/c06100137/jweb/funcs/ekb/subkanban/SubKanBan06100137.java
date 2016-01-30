package com.dsc.dci.customer.c06100137.jweb.funcs.ekb.subkanban;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpSession;
import com.dci.jweb.DCIEnums.Database.ConnectionType;
import com.dci.jweb.DataBaseLib.Database.DatabaseObjects;
import com.dci.jweb.PublicLib.APControl;
import com.dci.jweb.PublicLib.DBControl;
import com.dci.jweb.PublicLib.DCIString;
import com.dsc.dci.customer.c06100137.sqlcode.funcs.ekb.sqlSubKanBan;
import com.dsc.dci.jweb.apenums.ColumnType;
import com.dsc.dci.jweb.funcs.ekb.kanban.KanBan;
import com.dsc.dci.jweb.pub.Singleton;
import com.dsc.dci.sqlcode.funcs.ekb.sqlKanBan;

public class SubKanBan06100137 {
	private String currLang;
	private sqlKanBan kbcmd;
	private DatabaseObjects dbobj;
	private DBControl dbctrl;
	private String uid;

	public SubKanBan06100137(HttpSession session, String uid) {
		this.dbobj = DatabaseObjects.getInstance();
		this.kbcmd = new sqlKanBan();
		this.dbctrl = new DBControl();
		APControl apc = new APControl();
		if (session == null) {
			this.currLang = null;
		} else {
			this.currLang = apc.getUserInfoFromSession(session, uid, "lang");
		}
		this.uid = uid;
	}

	public HashMap<String, Object> getInitData(HttpSession session, String sql_id) {
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
		boolean is_cross = false;
		KanBan kb = null;

		try {
			kb = new KanBan(session, this.uid);
			conn = this.dbobj.getConnection(ConnectionType.SYS);
			ps1 = conn.prepareStatement(this.kbcmd.getSqlColumns());
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
					tmp.put("xtype", "dcilightcolumnCus");
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

			ps1 = conn.prepareStatement(this.kbcmd.getIsCrossDB());
			ps1.setString(1, sql_id);
			rs1 = ps1.executeQuery();
			is_cross = rs1.next();
			this.dbctrl.closeConnection(rs1, ps1, null);

			ps = conn.prepareStatement(new sqlSubKanBan().getSqlCommand());
			ps.setString(1, sql_id);
			ps.setString(2, this.uid);
			ps.setString(3, sql_id);
			ps.setString(4, sql_id);
			ps.setString(5, this.uid);
			ps.setString(6, sql_id);
			rs = ps.executeQuery();

			if (rs.next()) {
				legendValue = kb.buildLegendLangs(sql_id, rs.getString("legend"), this.currLang);
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

			ps = conn.prepareStatement(this.kbcmd.getAllLights());
			rs = ps.executeQuery();

			while (rs.next()) {
				tmp4 = new HashMap<String, Object>();
				tmp4.put("label",
						Singleton.getInstance().getLanguage(this.currLang, rs.getString("icon_name").replace(" ", "_")));
				tmp4.put("value", rs.getString("icon_map_key"));
				lights.add(tmp4);
			}

			this.dbctrl.closeConnection(rs, ps, null);

			ps = conn.prepareStatement(this.kbcmd.getAdvCondition());
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

			ps = conn.prepareStatement(this.kbcmd.getSqlRelation());
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

}
