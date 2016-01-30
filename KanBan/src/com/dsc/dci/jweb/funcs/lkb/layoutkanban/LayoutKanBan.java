package com.dsc.dci.jweb.funcs.lkb.layoutkanban;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;

import com.dci.jweb.DCIEnums.Database.ConnectionType;
import com.dci.jweb.DCIEnums.Database.DBType;
import com.dci.jweb.DataBaseLib.Database.DataDatabaseObject;
import com.dci.jweb.DataBaseLib.Database.DatabaseObjects;
import com.dci.jweb.PublicLib.DBControl;
import com.dsc.dci.sqlcode.funcs.lkb.sqlLayoutKanBan;
import com.dci.jweb.DCIEnums.Server.BlockPattern;

public class LayoutKanBan {
	private DBControl dbctrl;
	private sqlLayoutKanBan cmd;
	private DatabaseObjects dbobj;
	private DataDatabaseObject dataobj;
	private String orisql;
	private HashMap<String, String> dtlorisql;
	private final String hintWidth = "150";

	public LayoutKanBan() {
		this.dbctrl = new DBControl();
		this.cmd = new sqlLayoutKanBan();
		this.dbobj = DatabaseObjects.getInstance();
		this.dataobj = DataDatabaseObject.getInstance();
	}

	public ArrayList<Object> getLKBInfo(String lkbid, String connid) {
		ArrayList<Object> infos = null;
		HashMap<String, Object> info = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = this.dataobj.getConnection(connid);// this.dbobj.getConnection(ConnectionType.SYS);
			ps = conn.prepareStatement(cmd.getLKBInfo());
			ps.setString(1, lkbid);
			rs = ps.executeQuery();

			info = new HashMap<String, Object>();
			info.put("result", false);

			if (rs.next()) {
				info.put("lkbcolor", rs.getString("Bg_Color"));
				info.put("imgurl", rs.getString("Image_Location"));
			}

			infos = new ArrayList<Object>();
			if (info != null) {
				info.put("result", true);
				infos.add(info);
			}
		} catch (Exception ex) {
			infos = new ArrayList<Object>();
			info = new HashMap<String, Object>();
			info.put("result", false);
			infos.add(info);
			ex.printStackTrace();
		} finally {
			this.dbctrl.closeConnection(rs, ps, conn);
		}
		return infos;
	}

	public ArrayList<Object> buileData(String lkbid, String connid) {
		HashMap<String, HashMap<String, HashMap<String, String>>> datas = getLKBDatas(lkbid, connid);
		HashMap<String, HashMap<String, HashMap<String, String>>> dtldatas = getBlockDtl(lkbid, connid);
		return buildJson(datas, dtldatas, lkbid, connid);
	}

	public ArrayList<Object> buileReflashData(String lkbid, String connid) {
		HashMap<String, HashMap<String, HashMap<String, String>>> datas = getLKBDatas(lkbid, connid);
		HashMap<String, HashMap<String, HashMap<String, String>>> dtldatas = getBlockDtl(lkbid, connid);
		return buildUpdateJson(datas, dtldatas, lkbid, connid);
	}

	private HashMap<String, HashMap<String, HashMap<String, String>>> getLKBDatas(String lkbid, String connid) {
		HashMap<String, HashMap<String, HashMap<String, String>>> list = new HashMap<String, HashMap<String, HashMap<String, String>>>();
		Connection conn = null;
		Connection conn2 = null;
		PreparedStatement ps = null;
		PreparedStatement ps2 = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		String sqlcode = null;

		try {
			conn = this.dataobj.getConnection(connid);// this.dbobj.getConnection(ConnectionType.SYS);
			ps = conn.prepareStatement(cmd.getSQLAndConn());
			ps.setString(1, lkbid);
			rs = ps.executeQuery();

			while (rs.next()) {
				try {
					conn2 = this.dataobj.getConnection(rs.getString("Conn_ID").trim());
					sqlcode = rs.getString("SQL_CODE");
					if (rs.getObject("Group_By") != null) {
						sqlcode += "\r\n group by " + rs.getString("Group_By");
					}
					if (rs.getObject("Order_By") != null) {
						sqlcode += "\r\n order by " + rs.getString("Order_By");
					}
					ps2 = conn2.prepareStatement(sqlcode);
					rs2 = ps2.executeQuery();
					list.put(rs.getString("Group_ID"), buileSQLData(rs2, rs.getString("Group_ID")));
				} catch (Exception ex2) {
					ex2.printStackTrace();
				} finally {
					this.dbctrl.closeConnection(rs2, ps2, conn2);
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			this.dbctrl.closeConnection(rs, ps, conn);
		}
		return list;
	}

	private HashMap<String, HashMap<String, HashMap<String, String>>> getBlockDtl(String lkbid, String connid) {
		HashMap<String, HashMap<String, HashMap<String, String>>> list = new HashMap<String, HashMap<String, HashMap<String, String>>>();
		HashMap<String, HashMap<String, String>> rows = null;
		HashMap<String, String> row = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String lastKey = "";

		try {
			conn = this.dataobj.getConnection(connid);// this.dbobj.getConnection(ConnectionType.SYS);
			// System.out.println(cmd.getBlockDetail(this.orisql));
			ps = conn.prepareStatement(cmd.getBlockDetail(this.orisql));
			ps.setString(1, lkbid);
			ps.setString(2, lkbid);
			ps.setString(3, lkbid);
			rs = ps.executeQuery();

			while (rs.next()) {

				// System.out.println(rs.getString("Group_id") + "$$" +
				// rs.getString("Block_Pattern") + "$$"
				// + rs.getString("Block_ID") != lastKey);
				if (!(rs.getString("Group_id") + "$$" + rs.getString("Block_Pattern") + "$$" + rs.getString("Block_ID"))
						.equals(lastKey)) {
					if (!lastKey.equals("")) {
						list.put(lastKey, rows);
					}
					rows = new HashMap<String, HashMap<String, String>>();
				}

				row = new HashMap<String, String>();
				row.put("DB_Column", rs.getString("DB_Column"));
				row.put("Font_Size", rs.getString("Font_Size"));
				row.put("Font_Color", rs.getString("Font_Color"));
				row.put("Display_Title", rs.getString("Display_Title"));
				row.put("IsHint", rs.getString("IsHint"));

				rows.put(rs.getString("Layout_ID"), row);

				lastKey = rs.getString("Group_id") + "$$" + rs.getString("Block_Pattern") + "$$"
						+ rs.getString("Block_ID");
			}

			if (rows != null && rows.size() > 0) {
				list.put(lastKey, rows);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			this.dbctrl.closeConnection(rs, ps, conn);
		}
		return list;
	}

	// /set resultset datas into hashMap
	private HashMap<String, HashMap<String, String>> buileSQLData(ResultSet rs, String group) {
		HashMap<String, HashMap<String, String>> rows = null;
		Object value = null;
		if (rs == null) {
			rows = null;
		} else {
			HashMap<String, String> row = null;
			ResultSetMetaData rsmd = null;
			int mappingcolidx = 0;
			// boolean havenumvalue = false;
			try {
				rows = new HashMap<String, HashMap<String, String>>();

				while (rs.next()) {
					rsmd = rs.getMetaData();
					row = new HashMap<String, String>();
					// havenumvalue = false;
					for (int i = 1; i <= rsmd.getColumnCount(); i++) {
						if (rsmd.getColumnName(i).equalsIgnoreCase("DB_Block_ID")) {
							mappingcolidx = i;
						} else {
							if (rsmd.getColumnType(i) == Types.TIMESTAMP) {
								row.put(rsmd.getColumnName(i), rs.getTimestamp(rsmd.getColumnName(i)).toString());
							} else if (rsmd.getColumnType(i) == Types.DATE) {
								row.put(rsmd.getColumnName(i), rs.getDate(rsmd.getColumnName(i)).toString());
							} else {
								value = rs.getObject(rsmd.getColumnName(i));
								if (value == null) {
									row.put(rsmd.getColumnName(i), "");
								} else {
									row.put(rsmd.getColumnName(i), value.toString());
									// if (isnumber(value.toString())) {
									// buileDtlRangeSQL(DBType.SqlServer,
									// value.toString(), value.toString());
									// havenumvalue = true;
									// }
								}
							}
						}
					}
					rows.put(rs.getString(mappingcolidx), row);

					buileRangeSQL(DBType.SqlServer, rs.getString("DB_Block_ID"), rs.getString("range_value"), group);
					// buileDtlRangeSQL(DBType.SqlServer,
					// rs.getString("DB_Block_ID"),
					// rs.getString("range_value"), group);
					// if (!havenumvalue) {
					// buileDtlRangeSQL(DBType.SqlServer,
					// rs.getString("DB_Block_ID"),
					// rs.getString("range_value"));
					// }

				}
			} catch (Exception ex) {
				rows = null;
				ex.printStackTrace();
			}

			// System.out.println(this.orisql);
		}
		return rows;
	}

	private ArrayList<Object> buildJson(HashMap<String, HashMap<String, HashMap<String, String>>> dbdatas,
			HashMap<String, HashMap<String, HashMap<String, String>>> dtldatas, String lkbid, String connid) {
		// ArrayList<PatternMainBean> datas = null;
		// PatternMainBean bean = null;
		ArrayList<Object> items = null;
		if (dbdatas == null) {
			// bean = null;
		} else {
			Connection conn = null;
			PreparedStatement ps = null;
			ResultSet rs = null;

			try {
				conn = this.dataobj.getConnection(connid);// this.dbobj.getConnection(ConnectionType.SYS);
				ps = conn.prepareStatement(cmd.getMasterData(this.orisql));

				ps.setString(1, lkbid);
				ps.setString(2, lkbid);

				rs = ps.executeQuery();
				items = new ArrayList<Object>(0);
				while (rs.next()) {

					if (BlockPattern.valueOf(rs.getInt("Block_Pattern")) == BlockPattern.P03) {
						items.add(setPattern03Data(
								dbdatas.get(rs.getString("Group_id")).get(rs.getString("DB_Block_ID")),
								rs.getString("LKB_ID"),
								rs.getString("Block_ID"),
								rs.getInt("Block_Location_X"),
								rs.getInt("Block_Location_Y"),
								rs.getInt("Block_Height"),
								rs.getInt("Block_Width"),
								rs.getString("block_color"),
								dtldatas.get(rs.getString("Group_id") + "$$" + rs.getString("Block_Pattern") + "$$"
										+ rs.getString("Block_ID"))));
					} else if (BlockPattern.valueOf(rs.getInt("Block_Pattern")) == BlockPattern.P04) {
						items.add(setPattern04Data(
								dbdatas.get(rs.getString("Group_id")).get(rs.getString("DB_Block_ID")),
								rs.getString("LKB_ID"),
								rs.getString("Block_ID"),
								rs.getInt("Block_Location_X"),
								rs.getInt("Block_Location_Y"),
								rs.getInt("Block_Height"),
								rs.getInt("Block_Width"),
								dtldatas.get(rs.getString("Group_id") + "$$" + rs.getString("Block_Pattern") + "$$"
										+ rs.getString("Block_ID"))));
					} else {

						items.add(setPattern01Data(
								dbdatas.get(rs.getString("Group_id")).get(rs.getString("DB_Block_ID")),
								rs.getString("LKB_ID"),
								rs.getString("Block_ID"),
								rs.getInt("Block_Location_X"),
								rs.getInt("Block_Location_Y"),
								rs.getInt("Block_Height"),
								rs.getInt("Block_Width"),
								rs.getString("block_color"),
								dtldatas.get(rs.getString("Group_id") + "$$" + rs.getString("Block_Pattern") + "$$"
										+ rs.getString("Block_ID"))));
					}
				}
				// bean.setItems(items);
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				this.dbctrl.closeConnection(rs, ps, conn);
			}

		}

		return items;
	}

	private Pattern03Bean setPattern03Data(HashMap<String, String> datas, String lkbid, String blkid, int x, int y,
			int h, int w, String style, HashMap<String, HashMap<String, String>> html) {
		Pattern03Bean bean = new Pattern03Bean();
		String value = datas.get("range_value");
		bean.setXtype("panel");
		bean.setId(lkbid + blkid);
		bean.setWidth(w);
		bean.setHeight(h);
		bean.setX(x);
		bean.setY(y);
		bean.setBodyStyle("background:#FFFFFF;text-align:center;");
		// bean.setItems(setPattern03SubData(h, value, style));
		// bean.setHtml(value == null || value.equals("") ? "0" : value);
		bean.setHtml(buildP3Html(h, value, style));

		if (html == null) {
			bean.setHtml(bean.getHtml().replace("@@###", ""));
		} else {
			HashMap<String, String> row = null;
			String tip = null;
			value = null;
			for (String key : html.keySet()) {
				row = html.get(key);

				value = datas.get(row.get("DB_Column"));
				if (value == null) {
					value = "";
				}
				if (tip == null) {
					tip = "";
				}
				if (row.get("IsHint").toString().equals("1")) {
					tip += row.get("Display_Title") + value + "</br>";
				}
				value = null;
			}

			value = bean.getHtml();
			bean.setHtml(value.replace("@@###", tip));
		}

		return bean;
	}

	// private Pattern03SubBean setPattern03SubData(int h, String value, String
	// color) {
	// Pattern03SubBean bean = new Pattern03SubBean();
	// // bean.setId("testidforp03");
	// bean.setXtype("panel");
	// bean.setHeight(countSubHeight(h, value));
	// bean.setBodyStyle("background:" + color +
	// ";text-align:center;z-index:1;");
	// bean.setHtml(value == null || value.equals("") ? "0" : value);
	//
	// return bean;
	// }

	private ArrayList<Object> buildUpdateJson(HashMap<String, HashMap<String, HashMap<String, String>>> dbdatas,
			HashMap<String, HashMap<String, HashMap<String, String>>> dtldatas, String lkbid, String connid) {
		ArrayList<Object> items = null;
		if (dbdatas == null) {
			// bean = null;
		} else {
			// bean = setMainData(lkbid, bgColor);
			Connection conn = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			// ArrayList<Object> items = null;

			try {
				conn = this.dataobj.getConnection(connid);// this.dbobj.getConnection(ConnectionType.SYS);
				ps = conn.prepareStatement(cmd.getMasterData(this.orisql));

				ps.setString(1, lkbid);
				ps.setString(2, lkbid);

				rs = ps.executeQuery();
				items = new ArrayList<Object>(0);
				while (rs.next()) {

					if (BlockPattern.valueOf(rs.getInt("Block_Pattern")) == BlockPattern.P03) {
						items.add(setPattern03UpdateData(
								dbdatas.get(rs.getString("Group_id")).get(rs.getString("DB_Block_ID")),
								rs.getString("LKB_ID"),
								rs.getString("Block_ID"),
								rs.getInt("Block_Height"),
								rs.getString("block_color"),
								dtldatas.get(rs.getString("Group_id") + "$$" + rs.getString("Block_Pattern") + "$$"
										+ rs.getString("Block_ID"))));
					} else if (BlockPattern.valueOf(rs.getInt("Block_Pattern")) == BlockPattern.P04) {
						items.add(setPattern04Data(
								dbdatas.get(rs.getString("Group_id")).get(rs.getString("DB_Block_ID")),
								rs.getString("LKB_ID"),
								rs.getString("Block_ID"),
								rs.getInt("Block_Location_X"),
								rs.getInt("Block_Location_Y"),
								rs.getInt("Block_Height"),
								rs.getInt("Block_Width"),
								dtldatas.get(rs.getString("Group_id") + "$$" + rs.getString("Block_Pattern") + "$$"
										+ rs.getString("Block_ID"))));
					} else {
						items.add(setPattern01UpdateData(
								dbdatas.get(rs.getString("Group_id")).get(rs.getString("DB_Block_ID")),
								rs.getString("LKB_ID"),
								rs.getString("Block_ID"),
								rs.getString("block_color"),
								dtldatas.get(rs.getString("Group_id") + "$$" + rs.getString("Block_Pattern") + "$$"
										+ rs.getString("Block_ID"))));
					}
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				this.dbctrl.closeConnection(rs, ps, conn);
			}

		}

		return items;
	}

	private Pattern03Bean setPattern03UpdateData(HashMap<String, String> datas, String lkbid, String blkid, int h,
			String style, HashMap<String, HashMap<String, String>> html) {
		Pattern03Bean bean = new Pattern03Bean();
		bean.setPattern(BlockPattern.P03.value());
		bean.setId(lkbid + blkid);
		String value = datas.get("range_value");
		bean.setBodyStyle("background:" + style + ";text-align:center;");
		bean.setHtml(buildP3Html(h, value, style));

		if (html == null) {
			bean.setHtml(bean.getHtml().replace("@@###", ""));
		} else {
			HashMap<String, String> row = null;
			String tip = null;
			value = null;
			for (String key : html.keySet()) {
				row = html.get(key);

				value = datas.get(row.get("DB_Column"));
				if (value == null) {
					value = "";
				}
				if (tip == null) {
					tip = "";
				}
				if (row.get("IsHint").toString().equals("1")) {
					tip += row.get("Display_Title") + value + "</br>";
				}
				value = null;
			}

			value = bean.getHtml();
			bean.setHtml(value.replace("@@###", tip));
		}
		return bean;
	}

	private String buildP3Html(int h, String value, String style) {
		int intvalue = 0;
		if (value == null || value.equals("") || !isnumber(value)) {
			intvalue = 0;
		} else {
			intvalue = Integer.valueOf(value);
		}
		int colorh = countSubHeight(h, value);
		String htmlvalue = "<table width='100%' height='100%' cellpadding='0' cellspacing='0' border='0' data-qwidth='"
				+ this.hintWidth + "' data-qtip='@@###'>";

		if (intvalue <= 0) {
			htmlvalue += "<tr><td valign='top' align='center' height='" + h + "' style='background : transparent;'>"
					+ value + "</td></tr>";
		} else if (intvalue >= 100) {
			htmlvalue += "<tr><td valign='top' align='center' height='" + h + "' style='background : " + style + ";'>"
					+ value + "</td></tr>";
		} else {
			htmlvalue += "<tr><td valign='top' align='center' height='" + (h - colorh)
					+ "'>$$$@@@</td></tr><tr><td valign='top' align='center' height='" + colorh
					+ "' style='background : " + style + ";'>@@@###</td></tr>";
			if ((h - colorh) < 20) {
				htmlvalue = htmlvalue.replace("$$$@@@", "");
				htmlvalue = htmlvalue.replace("@@@###", value);
			} else {
				htmlvalue = htmlvalue.replace("$$$@@@", value);
				htmlvalue = htmlvalue.replace("@@@###", "");
			}
		}
		htmlvalue += "</table>";
		return htmlvalue;
	}

	private Pattern01Bean setPattern01UpdateData(HashMap<String, String> datas, String lkbid, String blkid,
			String style, HashMap<String, HashMap<String, String>> html) {
		Pattern01Bean bean = new Pattern01Bean();
		bean.setPattern(BlockPattern.P01.value());
		bean.setId(lkbid + blkid);
		bean.setBodyStyle("background:" + style + ";text-align:center;");
		String htmlvalue = "<table height='100%' width='100%' data-qwidth='" + this.hintWidth + "' data-qtip='@@###'>";
		String value = null;
		String tip = null;
		int cnt = 0;

		HashMap<String, String> row = null;
		int rowheight = 100;
		if (html != null) {
			if (html.size() != 0) {
				rowheight = 100 / html.size();
			}
		}

		for (String key : html.keySet()) {
			row = html.get(key);
			// System.out.println(key);
			value = datas.get(row.get("DB_Column"));
			if (value == null) {
				value = "";
			}
			if (tip == null) {
				tip = "";
			}
			// htmlvalue += "<div>" + row.get("Display_Title") + value +
			// "</div>";
			if (row.get("IsHint").toString().equals("0")) {
				htmlvalue += "<tr><td height='" + String.valueOf(rowheight) + "%' valign='top' style='font-size :"
						+ row.get("Font_Size") + "px;color:" + row.get("Font_Color") + ";'>" + row.get("Display_Title")
						+ value + "</td></tr>";
				cnt++;
			} else {
				tip += row.get("Display_Title") + value + "</br>";
			}
			value = null;
		}
		if (cnt == 0) {
			htmlvalue += "<tr><td></td></tr>";
		}
		htmlvalue = htmlvalue.replace("@@###", tip);
		// System.out.println(bean.getHtml());
		bean.setHtml(htmlvalue + "</table>");
		return bean;
	}

	private Pattern01Bean setPattern01Data(HashMap<String, String> datas, String lkbid, String blkid, int x, int y,
			int h, int w, String style, HashMap<String, HashMap<String, String>> html) {
		Pattern01Bean bean = new Pattern01Bean();
		bean.setXtype("panel");
		bean.setId(lkbid + blkid);
		bean.setWidth(w);
		bean.setHeight(h);
		bean.setX(x);
		bean.setY(y);
		bean.setBodyStyle("background:" + style + ";text-align:center;");
		String htmlvalue = "<table height='100%' width='100%' data-qwidth='" + this.hintWidth + "' data-qtip='@@###'>";
		String value = null;
		String tip = null;
		int cnt = 0;

		HashMap<String, String> row = null;
		int rowheight = 100;
		if (html != null) {
			if (html.size() != 0) {
				rowheight = 100 / html.size();
			}
		}
		for (String key : html.keySet()) {
			row = html.get(key);
			// System.out.println(key);
			value = datas.get(row.get("DB_Column"));
			if (value == null) {
				value = "";
			}
			if (tip == null) {
				tip = "";
			}
			// htmlvalue += "<div>" + row.get("Display_Title") + value +
			// "</div>";
			// System.out.println(row.get("IsHint").toString());
			if (row.get("IsHint").toString().equals("0")) {
				htmlvalue += "<tr><td height='" + String.valueOf(rowheight) + "%' valign='top' style='font-size :"
						+ row.get("Font_Size") + "px;color:" + row.get("Font_Color") + ";'>" + row.get("Display_Title")
						+ value + "</td></tr>";
				cnt++;
			} else {
				tip += row.get("Display_Title") + value + "</br>";
			}
			value = null;
		}
		if (cnt == 0) {
			htmlvalue += "<tr><td></td></tr>";
		}
		htmlvalue = htmlvalue.replace("@@###", tip);
		bean.setHtml(htmlvalue + "</table>");

		return bean;
	}

	private Pattern04Bean setPattern04Data(HashMap<String, String> datas, String lkbid, String blkid, int x, int y,
			int h, int w, HashMap<String, HashMap<String, String>> html) {
		Pattern04Bean bean = new Pattern04Bean();
		bean.setPattern(BlockPattern.P04.value());
		bean.setXtype("panel");
		bean.setId(lkbid + blkid);
		bean.setWidth(w);
		bean.setHeight(h);
		bean.setX(x);
		bean.setY(y);
		bean.setBodyStyle("background: transparent no-repeat url(/KanBan/images/LKB/sys_icon/man.png);");
		String htmlvalue = "<table height='100%' width='100%' data-qwidth='" + this.hintWidth + "' data-qtip='@@###'>";
		String value = null;
		String tip = null;
		int cnt = 0;

		HashMap<String, String> row = null;
		int rowheight = 100;
		if (html != null) {
			if (html.size() != 0) {
				rowheight = 100 / html.size();
			}
		}
		for (String key : html.keySet()) {
			row = html.get(key);
			// System.out.println(key);
			value = datas.get(row.get("DB_Column"));
			if (value == null) {
				value = "";
			}
			if (tip == null) {
				tip = "";
			}

			tip += row.get("Display_Title") + value + "</br>";
			value = null;
		}
		if (cnt == 0) {
			htmlvalue += "<tr><td></td></tr>";
		}
		// System.out.println(tip);
		htmlvalue = htmlvalue.replace("@@###", tip);
		bean.setHtml(htmlvalue + "</table>");

		return bean;
	}

	private Pattern01Bean setPattern05UpdateData(HashMap<String, String> datas, String lkbid, String blkid,
			String style, HashMap<String, HashMap<String, String>> html) {
		Pattern01Bean bean = new Pattern01Bean();
		bean.setPattern(BlockPattern.P01.value());
		bean.setId(lkbid + blkid);
		bean.setBodyStyle("background:" + style + ";text-align:left;");
		String htmlvalue = "<table height='100%' width='100%' border='1' data-qwidth='" + this.hintWidth
				+ "' data-qtip='@@###'>";
		String value = null;
		String tip = null;
		int cnt = 0;

		HashMap<String, String> row = null;
		int rowheight = 100;
		if (html != null) {
			if (html.size() != 0) {
				rowheight = 100 / html.size();
			}
		}

		for (String key : html.keySet()) {
			row = html.get(key);
			// System.out.println(key);
			value = datas.get(row.get("DB_Column"));
			if (value == null) {
				value = "";
			}
			if (tip == null) {
				tip = "";
			}
			// htmlvalue += "<div>" + row.get("Display_Title") + value +
			// "</div>";
			htmlvalue += "<tr><td height='" + String.valueOf(rowheight) + "%' valign='top' style='font-size :"
					+ row.get("Font_Size") + "px;color:" + row.get("Font_Color") + ";'>" + row.get("Display_Title")
					+ value + "</td></tr>";
			cnt++;
			value = null;
		}
		if (cnt == 0) {
			htmlvalue += "<tr><td></td></tr>";
		}
		htmlvalue = htmlvalue.replace("@@###", tip);
		// System.out.println(bean.getHtml());
		bean.setHtml(htmlvalue + "</table>");
		return bean;
	}

	private Pattern01Bean setPattern05Data(HashMap<String, String> datas, String lkbid, String blkid, int x, int y,
			int h, int w, String style, HashMap<String, HashMap<String, String>> html) {
		Pattern01Bean bean = new Pattern01Bean();
		bean.setXtype("panel");
		bean.setId(lkbid + blkid);
		bean.setWidth(w);
		bean.setHeight(h);
		bean.setX(x);
		bean.setY(y);
		bean.setBodyStyle("background:" + style + ";text-align:center;");
		String htmlvalue = "<table height='100%' width='100%' data-qwidth='" + this.hintWidth + "' data-qtip='@@###'>";
		String value = null;
		String tip = null;
		int cnt = 0;

		HashMap<String, String> row = null;
		int rowheight = 100;
		if (html != null) {
			if (html.size() != 0) {
				rowheight = 100 / html.size();
			}
		}
		for (String key : html.keySet()) {
			row = html.get(key);
			// System.out.println(key);
			value = datas.get(row.get("DB_Column"));
			if (value == null) {
				value = "";
			}
			if (tip == null) {
				tip = "";
			}
			// htmlvalue += "<div>" + row.get("Display_Title") + value +
			// "</div>";
			// System.out.println(row.get("IsHint").toString());
			if (row.get("IsHint").toString().equals("0")) {
				htmlvalue += "<tr><td height='" + String.valueOf(rowheight) + "%' valign='top' style='font-size :"
						+ row.get("Font_Size") + "px;color:" + row.get("Font_Color") + ";'>" + row.get("Display_Title")
						+ value + "</td></tr>";
				cnt++;
			} else {
				tip += row.get("Display_Title") + value + "</br>";
			}
			value = null;
		}
		if (cnt == 0) {
			htmlvalue += "<tr><td></td></tr>";
		}
		htmlvalue = htmlvalue.replace("@@###", tip);
		bean.setHtml(htmlvalue + "</table>");

		return bean;
	}

	/*
	 * private PatternMainBean setMainData(String lkbid, String bgColor) {
	 * PatternMainBean bean = new PatternMainBean();
	 * 
	 * bean.setXtype("panel"); bean.setId(lkbid + "panel");
	 * bean.setRegion("center"); bean.setLayout("absolute");
	 * bean.setBodyStyle("background: " + bgColor);
	 * 
	 * return bean; }
	 */

	private void buileRangeSQL(DBType dbtp, String eq, String value, String group) {
		if (dbtp == DBType.SqlServer) {
			if (this.orisql == null) {
				this.orisql = "select '" + eq + "' eq , " + value + " value_range , '" + group + "' groupid";
			} else {
				this.orisql = orisql + " union all select '" + eq + "' eq , " + value + " value_range, '" + group
						+ "' groupid";
			}
		} else if (dbtp == DBType.Oracle) {
			if (this.orisql == null) {
				this.orisql = "select '" + eq + "' eq , " + value + " value_range,'" + group + "' groupid from dule ";
			} else {
				this.orisql = orisql + " union all select '" + eq + "' eq , " + value + " value_range, '" + group
						+ "' groupid  from dule ";
			}
		}
	}

	private void buileDtlRangeSQL(DBType dbtp, String eq, String value, String group) {
		String sql = null;
		if (this.dtlorisql == null) {
			this.dtlorisql = new HashMap<String, String>();
		}
		if (this.dtlorisql.containsKey(group)) {
			sql = this.dtlorisql.get(group);
		}

		if (dbtp == DBType.SqlServer) {
			if (sql == null || sql == "") {
				sql = "select '" + eq + "' eq , " + value + " value_range ";
			} else {
				sql = sql + " union all select '" + eq + "' eq , " + value + " value_range ";
			}
		} else if (dbtp == DBType.Oracle) {
			if (sql == null || sql == "") {
				sql = "select '" + eq + "' eq , " + value + " value_range from dule ";
			} else {
				sql = sql + " union all select '" + eq + "' eq , " + value + " value_range from dule ";
			}
		}
		if (this.dtlorisql.containsKey(group)) {
			this.dtlorisql.remove(group);
		}
		this.dtlorisql.put(group, sql);
	}

	private int countSubHeight(int h, String value) {
		if (value == null || value.equals("")) {
			value = "0";
		}
		double v = Double.valueOf(value) / 100;
		if (v >= 1) {
			v = 1;
		}
		double newh = Double.valueOf(h) * v;

		return (int) (newh / 1);
	}

	private boolean isnumber(String value) {
		boolean isnumber = false;
		try {
			Integer.parseInt(value);
			isnumber = true;
		} catch (Exception ex) {
			try {
				Double.parseDouble(value);
				isnumber = true;
			} catch (Exception ex1) {
				try {
					Long.parseLong(value);
					isnumber = true;
				} catch (Exception ex2) {
					isnumber = false;
				}
			}
		}

		return isnumber;
	}

}
