package com.dsc.dci.jweb.funcs.lkb.lkb003;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import com.dci.jweb.DataBaseLib.Database.DataDatabaseObject;
import com.dci.jweb.PublicLib.DBControl;
import com.dci.jweb.PublicLib.DCIDate;
import com.dci.jweb.PublicLib.DCIString;
import com.dsc.dci.sqlcode.funcs.lkb.sqlLKB003;

public class LKB003 {
	private DBControl dbctrl;
	private sqlLKB003 cmd;
	private DataDatabaseObject dataobj;
	// private final String connid = "EKBQuery";
	private String connid;

	public LKB003(String compid) {
		this.dbctrl = new DBControl();
		this.cmd = new sqlLKB003();
		this.dataobj = DataDatabaseObject.getInstance();
		if (DCIString.isNullOrEmpty(compid)) {
			this.connid = "EKBQuery";
		} else {
			this.connid = compid;
		}
	}

	public ArrayList<Object> getQueryData(HashMap<String, String> params) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<Object> datas = null;
		LKB003Bean bean = null;
		int cnt = 0;
		DecimalFormat df = new DecimalFormat("#.##");

		try {
			conn = this.dataobj.getConnection(this.connid);
			ps = conn.prepareStatement(cmd.querySql(params.get("F001"), params.get("F002"), params.get("F003s"),
					params.get("F003e"), params.get("F005"), params.get("F006"), Boolean.valueOf(params.get("F008")),
					params.get("F009s"), params.get("F009e"), params.get("F011")));

			rs = ps.executeQuery();
			while (rs.next()) {
				if (datas == null) {
					datas = new ArrayList<Object>();
				}
				cnt++;
				bean = new LKB003Bean();
				bean.setSeq(String.valueOf(cnt));
				bean.setOrder_ID(rs.getString("Order_ID"));
				bean.setWS(rs.getString("WS"));
				bean.setOP(rs.getString("OP"));
				bean.setEQ_ID(rs.getString("EQ_ID"));
				bean.setEQ_Name(rs.getString("EQ_Name"));
				// bean.setCode_Name(rs.getString("Code_Name"));
				bean.setOut_Time(rs.getString("Out_Time"));
				bean.setArrive_Qty(rs.getDouble("Arrive_Qty"));
				bean.setOut_Qty(rs.getDouble("Out_Qty"));
				bean.setUTILITY(df.format((rs.getDouble("UTILITY") * 10000 + 0.5) / 100) + "%");
				bean.setShrinkage_Qty(rs.getDouble("Shrinkage_Qty"));
				bean.setSurplus_Qty(rs.getDouble("Surplus_Qty"));
				bean.setRework_Qty(rs.getDouble("Rework_Qty"));

				datas.add(bean);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			this.dbctrl.closeConnection(rs, ps, conn);
		}
		return datas;
	}

	public ArrayList<Object> getF001Data() {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<Object> datas = null;
		HashMap<String, Object> tmp = null;

		try {
			conn = this.dataobj.getConnection(this.connid);
			ps = conn.prepareStatement(cmd.F001Sql());

			rs = ps.executeQuery();
			while (rs.next()) {
				if (datas == null) {
					datas = new ArrayList<Object>();
				}
				tmp = new HashMap<String, Object>();
				tmp.put("value", rs.getString("Factory_ID"));
				tmp.put("label", rs.getString("Factory_Name"));
				datas.add(tmp);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			this.dbctrl.closeConnection(rs, ps, conn);
		}
		return datas;
	}

	public ArrayList<Object> getF002Data(String F001) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<Object> datas = null;
		HashMap<String, Object> tmp = null;

		try {
			conn = this.dataobj.getConnection(this.connid);
			ps = conn.prepareStatement(cmd.F002Sql(F001));

			rs = ps.executeQuery();
			while (rs.next()) {
				if (datas == null) {
					datas = new ArrayList<Object>();
					tmp = new HashMap<String, Object>();
					tmp.put("value", "");
					tmp.put("label", "");
					datas.add(tmp);
				}
				tmp = new HashMap<String, Object>();
				tmp.put("value", rs.getString("WS_ID"));
				tmp.put("label", rs.getString("WS_Name"));
				datas.add(tmp);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			this.dbctrl.closeConnection(rs, ps, conn);
		}
		return datas;
	}

	/*
	 * public ArrayList<Object> getF004Data() { ArrayList<Object> datas = null;
	 * datas = new ArrayList<Object>(); EKBQ003OpenWinBean bean = new
	 * EKBQ003OpenWinBean(); bean.setEQ_Property("test01");
	 * bean.setCode_Name("testname01"); datas.add(bean); return datas; }
	 */

	public ArrayList<Object> getF005Data(String F001, String F002) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<Object> datas = null;
		HashMap<String, Object> tmp = null;

		try {
			conn = this.dataobj.getConnection(this.connid);
			ps = conn.prepareStatement(cmd.F005Sql(F001, F002));

			rs = ps.executeQuery();
			while (rs.next()) {
				if (datas == null) {
					datas = new ArrayList<Object>();
				}
				tmp = new HashMap<String, Object>();
				tmp.put("EQ_ID", rs.getString("EQ_ID"));
				tmp.put("EQ_Name", rs.getString("EQ_Name"));
				tmp.put("WS_ID", rs.getString("WS_ID"));
				tmp.put("WS_Name", rs.getString("WS_Name"));
				datas.add(tmp);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			this.dbctrl.closeConnection(rs, ps, conn);
		}
		return datas;
	}

	public ArrayList<Object> getF006Data() {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<Object> datas = null;
		HashMap<String, Object> tmp = null;

		try {
			conn = this.dataobj.getConnection(this.connid);
			ps = conn.prepareStatement(cmd.F006Sql());

			rs = ps.executeQuery();
			while (rs.next()) {
				if (datas == null) {
					datas = new ArrayList<Object>();
				}
				tmp = new HashMap<String, Object>();
				tmp.put("Order_ID", rs.getString("Order_ID"));
				datas.add(tmp);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			this.dbctrl.closeConnection(rs, ps, conn);
		}
		return datas;
	}

	public ArrayList<Object> getF011Data(String F001, String F002) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<Object> datas = null;
		HashMap<String, Object> tmp = null;

		try {
			conn = this.dataobj.getConnection(this.connid);
			ps = conn.prepareStatement(cmd.F011Sql(F001, F002));

			rs = ps.executeQuery();
			while (rs.next()) {
				if (datas == null) {
					datas = new ArrayList<Object>();
				}
				tmp = new HashMap<String, Object>();
				tmp.put("OP_ID", rs.getString("OP_ID"));
				tmp.put("OP_Name", rs.getString("OP_Name"));
				tmp.put("OP_Desc", rs.getString("OP_Desc"));
				tmp.put("WS_ID", rs.getString("WS_ID"));
				tmp.put("WS_Name", rs.getString("WS_Name"));
				datas.add(tmp);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			this.dbctrl.closeConnection(rs, ps, conn);
		}
		return datas;
	}

	public ArrayList<Object> getChartData(HashMap<String, String> params) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<Object> datas = null;
		HashMap<String, Object> tmp = null;
		String lastDate = "";
		String f007 = null;
		DecimalFormat df = new DecimalFormat("#.##");

		try {
			conn = this.dataobj.getConnection(this.connid);
			if (params.get("F007") == null || params.get("F007").trim().length() == 0) {
				f007 = "0";
			} else {
				f007 = DCIDate.parseStringSec(params.get("F007"), "HH:mm");
			}
			ps = conn.prepareStatement(cmd.chartSql(params.get("F001"), params.get("F002"), params.get("F003s"),
					params.get("F003e"), params.get("F005"), params.get("F006"), f007, params.get("F011")));

			rs = ps.executeQuery();
			datas = new ArrayList<Object>();

			while (rs.next()) {
				if (!lastDate.equals(rs.getString("BELONG_DATE"))) {
					if (tmp != null) {
						datas.add(tmp);
					}
					tmp = new HashMap<String, Object>();
					tmp.put("date", rs.getString("BELONG_DATE"));
				}
				tmp.put(rs.getString("EQ_ID"),
						Double.parseDouble(df.format((rs.getDouble("YIELD") * 10000 + 0.5) / 100)));
				lastDate = rs.getString("BELONG_DATE");
			}
			datas.add(tmp);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			this.dbctrl.closeConnection(rs, ps, conn);
		}

		/*
		 * tmp = new HashMap<String, Object>(); tmp.put("date", "2013/01/01");
		 * tmp.put("EQ01", 100); tmp.put("EQ02", 200); tmp.put("EQ03", 300);
		 * 
		 * datas.add(tmp);
		 * 
		 * tmp = new HashMap<String, Object>(); tmp.put("date", "2013/02/01");
		 * tmp.put("EQ01", 700); tmp.put("EQ02", 500); tmp.put("EQ03", 600);
		 * 
		 * datas.add(tmp);
		 * 
		 * tmp = new HashMap<String, Object>(); tmp.put("date", "2013/03/01");
		 * tmp.put("EQ01", 550); tmp.put("EQ02", 650); tmp.put("EQ03", 750);
		 * 
		 * datas.add(tmp);
		 */

		return datas;
	}

	public ArrayList<Object> getSeriesData(HashMap<String, String> params) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<Object> datas = null;
		HashMap<String, String> tmp = null;

		try {
			conn = this.dataobj.getConnection(this.connid);
			ps = conn.prepareStatement(cmd.seriesSql(params.get("F001"), params.get("F002"), params.get("F003s"),
					params.get("F003e"), params.get("F005"), params.get("F006"), params.get("F011")));

			rs = ps.executeQuery();
			datas = new ArrayList<Object>();
			tmp = new HashMap<String, String>();
			tmp.put("series", "date");
			datas.add(tmp);
			while (rs.next()) {
				tmp = new HashMap<String, String>();
				tmp.put("series", rs.getString("series"));
				datas.add(tmp);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			this.dbctrl.closeConnection(rs, ps, conn);
		}

		return datas;
	}

	public ArrayList<HashMap<String, Object>> getExportData(HashMap<String, String> params) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<HashMap<String, Object>> datas = null;
		HashMap<String, Object> bean = null;
		int cnt = 0;
		DecimalFormat df = new DecimalFormat("#.##");

		try {
			conn = this.dataobj.getConnection(this.connid);
			ps = conn.prepareStatement(cmd.querySql(params.get("F001"), params.get("F002"), params.get("F003s"),
					params.get("F003e"), params.get("F005"), params.get("F006"), Boolean.valueOf(params.get("F008")),
					params.get("F009s"), params.get("F009e"), params.get("F011")));

			rs = ps.executeQuery();
			while (rs.next()) {
				if (datas == null) {
					datas = new ArrayList<HashMap<String, Object>>();
				}
				cnt++;
				bean = new HashMap<String, Object>();
				bean.put("seq", String.valueOf(cnt));
				bean.put("order_ID", rs.getString("Order_ID"));
				bean.put("ws", rs.getString("WS"));
				bean.put("op", rs.getString("OP"));
				bean.put("eq_ID", rs.getString("EQ_ID"));
				bean.put("eq_Name", rs.getString("EQ_Name"));
				// bean.setCode_Name(rs.getString("Code_Name"));
				bean.put("out_Time", rs.getString("Out_Time"));
				bean.put("arrive_Qty", rs.getDouble("Arrive_Qty"));
				bean.put("out_Qty", rs.getDouble("Out_Qty"));
				bean.put("utility", df.format((rs.getDouble("UTILITY") * 10000 + 0.5) / 100) + "%");
				bean.put("shrinkage_Qty", rs.getDouble("Shrinkage_Qty"));
				bean.put("surplus_Qty", rs.getDouble("Surplus_Qty"));
				bean.put("rework_Qty", rs.getDouble("Rework_Qty"));

				datas.add(bean);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			this.dbctrl.closeConnection(rs, ps, conn);
		}
		return datas;
	}

	public String exportFile(HashMap<String, String> params, String colstr, String ctype) {
		ArrayList<HashMap<String, Object>> exceldata = null;
		ArrayList<HashMap<String, String>> cols = null;
		String html = null;
		try {
			exceldata = getExportData(params);// getExportData(filter, sort,
												// sql_id, conn_id);
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

	/*
	 * private String parseSec(String t) { String sec = "0"; SimpleDateFormat
	 * formatter = new SimpleDateFormat("HH:mm"); Calendar c =
	 * Calendar.getInstance();
	 * 
	 * try {
	 * 
	 * Date date = formatter.parse(t); c.setTime(date); sec =
	 * String.valueOf(c.get(Calendar.HOUR_OF_DAY) * 3600 +
	 * c.get(Calendar.MINUTE) * 60 + c.get(Calendar.SECOND));
	 * 
	 * } catch (Exception e) { e.printStackTrace(); sec = "0"; }
	 * 
	 * return sec; }
	 */
}
