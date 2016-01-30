package com.dsc.dci.jweb.funcs.lkb.lkb001;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import com.dci.jweb.DataBaseLib.Database.DataDatabaseObject;
import com.dci.jweb.PublicLib.DBControl;
import com.dci.jweb.PublicLib.DCIDate;
import com.dci.jweb.PublicLib.DCIString;
import com.dsc.dci.sqlcode.funcs.lkb.sqlLKB001;

public class LKB001 {
	private DBControl dbctrl;
	private sqlLKB001 cmd;
	private DataDatabaseObject dataobj;
	// private final String connid = "EKBQuery";
	private String connid;

	public LKB001(String compid) {
		this.dbctrl = new DBControl();
		this.cmd = new sqlLKB001();
		this.dataobj = DataDatabaseObject.getInstance();
		if (DCIString.isNullOrEmpty(compid)) {
			this.connid = "EKBQuery";
		} else {
			this.connid = compid;
		}
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

	public HashMap<String, ArrayList<Object>> getQueryData(HashMap<String, String> params) {
		HashMap<String, ArrayList<Object>> alldatas = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<String> statusCode = null;
		ArrayList<Object> datas = null;
		ArrayList<Object> chart2datas = null;
		HashMap<String, Object> tmp = null;
		HashMap<String, Object> tmp2 = null;
		HashMap<String, Object> status = null;
		int eqcnt = 0;
		int daycnt = 0;
		DecimalFormat df = new DecimalFormat("#.##");
		String eqs = null;
		String sql = null;
		Date endtime = null;
		Calendar c = null;

		try {
			conn = this.dataobj.getConnection(this.connid);
			ps = conn.prepareStatement(cmd.eqsSql(params.get("F001"), params.get("F002"), params.get("F008"),
					params.get("updatetime")));

			rs = ps.executeQuery();
			while (rs.next()) {
				if (datas == null) {
					datas = new ArrayList<Object>();
				}
				if (status == null) {
					statusCode = new ArrayList<String>();
					status = new HashMap<String, Object>();
					tmp2 = new HashMap<String, Object>();
				}
				if (status.containsKey(rs.getString("Code_Value").trim())) {
					status.put(rs.getString("Code_Value").trim(),
							Integer.parseInt(status.get(rs.getString("Code_Value").trim()).toString()) + 1);
				} else {
					statusCode.add(rs.getString("Code_Value").trim());
					status.put(rs.getString("Code_Value").trim(), 1);
					tmp2.put(rs.getString("Code_Value").trim(),
							new String[] { rs.getString("Code_Name"), rs.getString("Code_Color") });
				}

				if (eqs == null) {
					eqs = rs.getString("EQKey");
				} else {
					eqs += ";" + rs.getString("EQKey");
				}

				tmp = new HashMap<String, Object>();
				tmp.put("eqid", rs.getString("EQ_ID"));
				tmp.put("eqkey", rs.getString("EQKey"));
				tmp.put("title", rs.getString("EQ_ID") + "/" + rs.getString("EQ_NAME"));
				//tmp.put("pic1", "./../../ImageLoader.dsc?imgpath=" + DCIString.Base64Encode(rs.getString("EQ_Image")));
				//tmp.put("pic2", "./../../ImageLoader.dsc?imgpath=" + DCIString.Base64Encode(rs.getString("Code_Image")));
				tmp.put("pic1", rs.getString("EQ_Image"));
				tmp.put("pic2", rs.getString("Code_Image"));
				tmp.put("timegap", rs.getString("timegap"));
				tmp.put("EQ_Time", DCIDate.parseString(rs.getTimestamp("EQ_Time"), "yyyy/MM/dd HH:mm"));
				// System.out.println(rs.getString("EQ_Time"));
				tmp.put("utility1", "");
				tmp.put("utility3", "");
				// tmp.put("chartData", null);
				datas.add(tmp);
			}

			this.dbctrl.closeConnection(rs, ps, null);

			if (alldatas == null) {
				alldatas = new HashMap<String, ArrayList<Object>>();
			}

			if (datas == null) {
				datas = new ArrayList<Object>();
				alldatas.put("eqlist", datas);
				alldatas.put("status", datas);
				alldatas.put("chart1", datas);
			} else {

				alldatas.put("eqlist", datas);

				eqcnt = datas.size();
				datas = null;
				int[] arr = new int[statusCode.size()];

				for (int i = 0; i < statusCode.size(); i++) {
					arr[i] = Integer.parseInt(statusCode.get(i));
				}

				Arrays.sort(arr);

				for (int i = 0; i < arr.length; i++) {
					if (datas == null) {
						datas = new ArrayList<Object>();
					}
					tmp = new HashMap<String, Object>();
					tmp.put("statusid", String.valueOf(arr[i]));
					tmp.put("status", ((String[]) tmp2.get(String.valueOf(arr[i])))[0]);
					tmp.put("eqcnt", status.get(String.valueOf(arr[i])));
					tmp.put("percent",
							df.format((((Double.parseDouble(status.get(String.valueOf(arr[i])).toString())
									/ Double.parseDouble(String.valueOf(eqcnt)) * 10000) + 0.5) / 100))
									+ "%");
					tmp.put("statuscolor", ((String[]) tmp2.get(String.valueOf(arr[i])))[1]);
					datas.add(tmp);
				}

				alldatas.put("status", datas);
				datas = null;
				status = null;
				tmp2 = null;

				c = Calendar.getInstance();
				c.setTime(DCIDate.parseDate(params.get("updatetime")));
				for (int i = 0; i < Integer.parseInt(params.get("F003")); i++) {
					if (i == 0) {
						endtime = c.getTime();
						if (DCIDate.parseIntSec(params.get("F005"), "HH:mm") > DCIDate.parseIntSec(
								DCIDate.parseString(endtime, "HH:mm:ss"), "HH:mm:ss")) {
							c.add(Calendar.DATE, -1);
						}
						sql = cmd.utilitySql(params.get("F001"), eqs, DCIDate.parseString(c.getTime(), "yyyy/MM/dd")
								+ " " + params.get("F005") + ":00",
								DCIDate.parseString(endtime, "yyyy/MM/dd HH:mm:ss"), String.valueOf(i),
								DCIDate.parseString(c.getTime(), "yyyy/MM/dd"));
						endtime = DCIDate.parseDate(DCIDate.parseString(c.getTime(), "yyyy/MM/dd") + " "
								+ params.get("F005") + ":00");
					} else {
						c.setTime(endtime);
						c.add(Calendar.DATE, -1);
						sql += " union all "
								+ cmd.utilitySql(params.get("F001"), eqs,
										DCIDate.parseString(c.getTime(), "yyyy/MM/dd HH:mm:ss"),
										DCIDate.parseString(endtime, "yyyy/MM/dd HH:mm:ss"), String.valueOf(i),
										DCIDate.parseString(c.getTime(), "yyyy/MM/dd"));
						endtime = DCIDate.parseDate(DCIDate.parseString(c.getTime(), "yyyy/MM/dd HH:mm:ss"));
					}
				}

				sql += " order by 1";
				// System.out.println(sql);
				ps = conn.prepareStatement(sql);

				rs = ps.executeQuery();

				while (rs.next()) {
					if (datas == null) {
						datas = new ArrayList<Object>();
					}
					tmp = new HashMap<String, Object>();
					tmp.put("date", rs.getString("date"));
					tmp.put("value", Double.parseDouble(df.format(((rs.getDouble("UTILITY") * 10000) + 0.5) / 100)));

					datas.add(tmp);
				}
				this.dbctrl.closeConnection(rs, ps, null);
				alldatas.put("chart1", datas);
				datas = null;

				datas = alldatas.get("eqlist");
				for (int j = 0; j < datas.size(); j++) {
					tmp = (HashMap<String, Object>) datas.get(j);
					c = Calendar.getInstance();
					c.setTime(DCIDate.parseDate(params.get("updatetime")));
					for (int i = 0; i < Integer.parseInt(params.get("F003")); i++) {
						if (i == 0) {
							endtime = c.getTime();
							if (DCIDate.parseIntSec(params.get("F005"), "HH:mm") > DCIDate.parseIntSec(
									DCIDate.parseString(endtime, "HH:mm:ss"), "HH:mm:ss")) {
								c.add(Calendar.DATE, -1);
							}
							sql = cmd.utilityByEqSql(params.get("F001"), tmp.get("eqkey").toString(),
									DCIDate.parseString(c.getTime(), "yyyy/MM/dd") + " " + params.get("F005") + ":00",
									DCIDate.parseString(endtime, "yyyy/MM/dd HH:mm:ss"), String.valueOf(i),
									DCIDate.parseString(c.getTime(), "MM/dd"));
							endtime = DCIDate.parseDate(DCIDate.parseString(c.getTime(), "yyyy/MM/dd") + " "
									+ params.get("F005") + ":00");
						} else {
							c.setTime(endtime);
							c.add(Calendar.DATE, -1);
							sql += " union all "
									+ cmd.utilityByEqSql(params.get("F001"), tmp.get("eqkey").toString(),
											DCIDate.parseString(c.getTime(), "yyyy/MM/dd HH:mm:ss"),
											DCIDate.parseString(endtime, "yyyy/MM/dd HH:mm:ss"), String.valueOf(i),
											DCIDate.parseString(c.getTime(), "MM/dd"));
							endtime = DCIDate.parseDate(DCIDate.parseString(c.getTime(), "yyyy/MM/dd HH:mm:ss"));
						}
					}

					sql += " order by 1";
					// System.out.println(sql);
					ps = conn.prepareStatement(sql);

					rs = ps.executeQuery();
					double totalUTILITY1 = 0;
					double totalUTILITY3 = 0;
					while (rs.next()) {
						if (chart2datas == null) {
							chart2datas = new ArrayList<Object>();
						}
						tmp2 = new HashMap<String, Object>();
						tmp2.put("date", rs.getString("date"));
						tmp2.put("value",
								Double.parseDouble(df.format(((rs.getDouble("UTILITY") * 10000) + 0.5) / 100)));

						chart2datas.add(tmp2);

						if (Integer.parseInt(params.get("F003")) - daycnt <= 1) {
							totalUTILITY1 += Double
									.parseDouble(df.format(((rs.getDouble("UTILITY") * 10000) + 0.5) / 100));
						}
						if (Integer.parseInt(params.get("F003")) - daycnt <= 3) {
							totalUTILITY3 += Double
									.parseDouble(df.format(((rs.getDouble("UTILITY") * 10000) + 0.5) / 100));
						}

						daycnt++;
					}
					tmp.put("utility1", df.format(totalUTILITY1) + "%");
					tmp.put("utility3", df.format(((totalUTILITY3 / 3.0 * 100) + 0.5) / 100) + "%");
					tmp.put("chartData", chart2datas);
					chart2datas = null;
					daycnt = 0;
					this.dbctrl.closeConnection(rs, ps, null);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			this.dbctrl.closeConnection(rs, ps, conn);
		}
		return alldatas;
	}

	/*
	 * private int parseSec(String t) { int sec = 0; if (t.indexOf(":") ==
	 * t.lastIndexOf(":")) { t += ":00"; } SimpleDateFormat formatter = new
	 * SimpleDateFormat("HH:mm:ss"); Calendar c = Calendar.getInstance(); try {
	 * 
	 * Date date = formatter.parse(t); c.setTime(date); sec =
	 * c.get(Calendar.HOUR_OF_DAY) * 3600 + c.get(Calendar.MINUTE) * 60 +
	 * c.get(Calendar.SECOND);
	 * 
	 * } catch (Exception e) { e.printStackTrace(); sec = 0; } return sec; }
	 */

	/*
	 * private String parseString(Date t, String format) { String datetime = "";
	 * SimpleDateFormat formatter = new SimpleDateFormat(format);
	 * 
	 * try { datetime = formatter.format(t); } catch (Exception e) {
	 * e.printStackTrace(); datetime = ""; } return datetime; }
	 */

	/*
	 * private String parseString(Timestamp t, String format) { String datetime
	 * = ""; SimpleDateFormat formatter = new SimpleDateFormat(format);
	 * 
	 * try { datetime = formatter.format(t); } catch (Exception e) {
	 * e.printStackTrace(); datetime = ""; } return datetime; }
	 */

	/*
	 * private Date parseDate(String t) { Date datetime = null; SimpleDateFormat
	 * formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	 * 
	 * try { datetime = formatter.parse(t); } catch (Exception e) {
	 * e.printStackTrace(); datetime = null; } return datetime; }
	 */
}
