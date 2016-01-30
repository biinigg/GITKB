package com.dsc.dci.jweb.funcs.lkb.lkb002;

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
import com.dci.jweb.PublicLib.DCIString;
import com.dsc.dci.sqlcode.funcs.lkb.sqlLKB002;

public class LKB002 {
	private DBControl dbctrl;
	private sqlLKB002 cmd;
	private DataDatabaseObject dataobj;
	// private final String connid = "EKBQuery";
	private String connid;

	public LKB002(String compid) {
		this.dbctrl = new DBControl();
		this.cmd = new sqlLKB002();
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
		LKB002Bean bean = null;
		int cnt = 0;

		try {
			conn = this.dataobj.getConnection(this.connid);
			ps = conn.prepareStatement(cmd.querySql(params.get("F001"), params.get("F002"), params.get("F003s"),
					params.get("F003e"), params.get("F005"), Boolean.valueOf(params.get("F006")), params.get("F008"),
					params.get("F009")));

			rs = ps.executeQuery();
			while (rs.next()) {
				if (datas == null) {
					datas = new ArrayList<Object>();
				}
				cnt++;
				bean = new LKB002Bean();
				bean.setSeq(String.valueOf(cnt));
				bean.setWS(rs.getString("WS"));
				bean.setEQ_ID(rs.getString("EQ_ID"));
				bean.setEQ_Name(rs.getString("EQ_Name"));
				bean.setEQ_TYPE(rs.getString("EQ_TYPE"));
				bean.setStart_Time(rs.getString("Start_Time"));
				bean.setEQ_STATUS_NAME(rs.getString("EQ_STATUS_NAME"));
				bean.setEQ_REASON_NAME(rs.getString("EQ_REASON_NAME"));
				bean.setRemark(rs.getString("Remark"));
				bean.setCode_Color(rs.getString("Code_Color"));

				datas.add(bean);
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
		int total = 0;
		DecimalFormat df = new DecimalFormat("#.##");

		try {
			conn = this.dataobj.getConnection(this.connid);
			ps = conn.prepareStatement(cmd.chartSql(params.get("F001"), params.get("F002"), params.get("F003s"),
					params.get("F003e"), params.get("F005"), Boolean.valueOf(params.get("F006")), params.get("F008"),
					params.get("F009")));

			rs = ps.executeQuery();
			while (rs.next()) {
				if (datas == null) {
					datas = new ArrayList<Object>();
				}
				if (rs.getInt("EQ_REASON_COUNT") != 0) {
					tmp = new HashMap<String, Object>();
					tmp.put("name", rs.getString("EQ_REASON_NAME"));
					tmp.put("value", rs.getInt("EQ_REASON_COUNT"));
					datas.add(tmp);
					total += rs.getInt("EQ_REASON_COUNT");
				}
			}
			if (datas != null) {
				for (int i = 0; i < datas.size(); i++) {
					tmp = (HashMap<String, Object>) datas.get(i);
					tmp.put("percent",
							df.format((((Double.parseDouble(tmp.get("value").toString())
									/ Double.parseDouble(String.valueOf(total)) * 10000) + 0.5) / 100)));
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			this.dbctrl.closeConnection(rs, ps, conn);
		}
		return datas;
	}

	public ArrayList<Object> getChart2Data(HashMap<String, String> params) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<Object> datas = null;
		HashMap<String, Object> tmp = null;
		double total = 0;
		DecimalFormat df = new DecimalFormat("#.##");

		try {
			conn = this.dataobj.getConnection(this.connid);
			ps = conn.prepareStatement(cmd.chart2Sql(params.get("F001"), params.get("F002"), params.get("F003s"),
					params.get("F003e"), Boolean.valueOf(params.get("F006")), params.get("F008"), params.get("F009")));

			rs = ps.executeQuery();
			while (rs.next()) {
				if (datas == null) {
					datas = new ArrayList<Object>();
				}
				if (rs.getDouble("EQ_STATUS_HOURS") != 0) {
					tmp = new HashMap<String, Object>();
					tmp.put("name", rs.getString("EQ_STATUS_NAME"));
					tmp.put("value", rs.getDouble("EQ_STATUS_HOURS"));
					datas.add(tmp);
					total += rs.getDouble("EQ_STATUS_HOURS");
				}
			}
			if (datas != null) {
				for (int i = 0; i < datas.size(); i++) {
					tmp = (HashMap<String, Object>) datas.get(i);
					tmp.put("percent",
							df.format((((Double.parseDouble(tmp.get("value").toString())
									/ Double.parseDouble(String.valueOf(total)) * 10000) + 0.5) / 100)));
				}
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

	public ArrayList<Object> getF005Data(String F001) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<Object> datas = null;
		HashMap<String, Object> tmp = null;

		try {
			conn = this.dataobj.getConnection(this.connid);
			ps = conn.prepareStatement(cmd.F005Sql(F001));

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
				tmp.put("value", rs.getString("EQ_Reason"));
				tmp.put("label", rs.getString("Code_Name"));
				datas.add(tmp);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			this.dbctrl.closeConnection(rs, ps, conn);
		}
		return datas;
	}

	public ArrayList<Object> getF009Data(String F001, String F002) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<Object> datas = null;
		HashMap<String, Object> tmp = null;

		try {
			conn = this.dataobj.getConnection(this.connid);
			ps = conn.prepareStatement(cmd.F009Sql(F001, F002));

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
				tmp.put("value", rs.getString("EQ_Status"));
				tmp.put("label", rs.getString("Code_Name"));
				datas.add(tmp);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			this.dbctrl.closeConnection(rs, ps, conn);
		}
		return datas;
	}

	public ArrayList<Object> getF008Data(String F001, String F002) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<Object> datas = null;
		HashMap<String, Object> tmp = null;

		try {
			conn = this.dataobj.getConnection(this.connid);
			ps = conn.prepareStatement(cmd.F008Sql(F001, F002));

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

	public ArrayList<HashMap<String, Object>> getExportData(HashMap<String, String> params) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<HashMap<String, Object>> datas = null;
		HashMap<String, Object> bean = null;
		int cnt = 0;

		try {
			conn = this.dataobj.getConnection(this.connid);
			ps = conn.prepareStatement(cmd.querySql(params.get("F001"), params.get("F002"), params.get("F003s"),
					params.get("F003e"), params.get("F005"), Boolean.valueOf(params.get("F006")), params.get("F008"),
					params.get("F009")));

			rs = ps.executeQuery();
			while (rs.next()) {
				if (datas == null) {
					datas = new ArrayList<HashMap<String, Object>>();
				}
				cnt++;
				bean = new HashMap<String, Object>();
				bean.put("seq",String.valueOf(cnt));
				bean.put("ws",rs.getString("WS"));
				bean.put("eq_ID",rs.getString("EQ_ID"));
				bean.put("eq_Name",rs.getString("EQ_Name"));
				bean.put("eq_TYPE",rs.getString("EQ_TYPE"));
				bean.put("start_Time",rs.getString("Start_Time"));
				bean.put("eq_STATUS_NAME",rs.getString("EQ_STATUS_NAME"));
				bean.put("eq_REASON_NAME",rs.getString("EQ_REASON_NAME"));
				bean.put("remark",rs.getString("Remark"));
				bean.put("code_Color",rs.getString("Code_Color"));

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
							if (exceldata.get(i).get("code_Color") == null) {
								html += "<td>";
							} else {
								if (cols.get(j).get("dataIndex").equals("eq_STATUS_NAME")) {
									html += "<td style='background-color : " + exceldata.get(i).get("code_Color")
											+ " ;'>";
								} else {
									html += "<td>";
								}
							}

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
}
