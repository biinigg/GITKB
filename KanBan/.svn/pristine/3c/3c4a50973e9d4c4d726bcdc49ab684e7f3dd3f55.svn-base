package com.dsc.dci.jweb.funcs.wpp.wpp004;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import com.dci.jweb.DataBaseLib.Database.DataDatabaseObject;
import com.dci.jweb.PublicLib.DBControl;
import com.dci.jweb.PublicLib.DCIDate;
import com.dci.jweb.PublicLib.DCIExcel;
import com.dci.jweb.PublicLib.DCIString;
import com.dsc.dci.jweb.pub.Singleton;
import com.dsc.dci.sqlcode.funcs.wpp.sqlWPP004;

public class WPP004 {

	private DBControl dbctrl;
	private sqlWPP004 cmd;
	private DataDatabaseObject dataobj;
	// private final String connid = "WPPQuery";
	private String connid;

	public WPP004(String compid) {
		this.dbctrl = new DBControl();
		this.cmd = new sqlWPP004();
		this.dataobj = DataDatabaseObject.getInstance();
		if (DCIString.isNullOrEmpty(compid)) {
			this.connid = "WPPQuery";
		} else {
			this.connid = compid;
		}
	}

	public HashMap<String, ArrayList<Object>> getComboBoxData() {
		HashMap<String, ArrayList<Object>> alldatas = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<Object> datas = null;
		HashMap<String, Object> tmp = null;

		try {
			conn = this.dataobj.getConnection(this.connid);
			ps = conn.prepareStatement(cmd.F001Sql());
			rs = ps.executeQuery();
			datas = new ArrayList<Object>();
			while (rs.next()) {
				tmp = new HashMap<String, Object>();
				tmp.put("value", rs.getString("value"));
				tmp.put("label", rs.getString("label"));
				datas.add(tmp);
			}

			alldatas = new HashMap<String, ArrayList<Object>>();
			alldatas.put("f001value", datas);
			this.dbctrl.closeConnection(rs, ps, null);

			ps = conn.prepareStatement(cmd.F003_1Sql());
			rs = ps.executeQuery();
			datas = new ArrayList<Object>();
			while (rs.next()) {
				tmp = new HashMap<String, Object>();
				tmp.put("value", rs.getString("value"));
				tmp.put("label", rs.getString("label"));
				datas.add(tmp);
			}

			alldatas.put("f003_1value", datas);
			this.dbctrl.closeConnection(rs, ps, null);

			ps = conn.prepareStatement(cmd.F003_2Sql());
			rs = ps.executeQuery();
			datas = new ArrayList<Object>();
			while (rs.next()) {
				tmp = new HashMap<String, Object>();
				tmp.put("value", rs.getString("value"));
				tmp.put("label", rs.getString("label"));
				datas.add(tmp);
			}

			alldatas.put("f003_2value", datas);
			this.dbctrl.closeConnection(rs, ps, null);

			ps = conn.prepareStatement(cmd.F006Sql());
			rs = ps.executeQuery();
			datas = new ArrayList<Object>();
			while (rs.next()) {
				tmp = new HashMap<String, Object>();
				tmp.put("value", rs.getString("value"));
				tmp.put("label", rs.getString("label"));
				datas.add(tmp);
			}

			alldatas.put("f006value", datas);
			this.dbctrl.closeConnection(rs, ps, null);

			ps = conn.prepareStatement(cmd.F009Sql());
			rs = ps.executeQuery();
			datas = new ArrayList<Object>();
			while (rs.next()) {
				tmp = new HashMap<String, Object>();
				tmp.put("value", rs.getString("value"));
				tmp.put("label", rs.getString("label"));
				datas.add(tmp);
			}

			alldatas.put("f009value", datas);
			this.dbctrl.closeConnection(rs, ps, null);

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			this.dbctrl.closeConnection(rs, ps, conn);
		}

		return alldatas;
	}

	public HashMap<String, ArrayList<Object>> query(HashMap<String, String> params) {
		HashMap<String, ArrayList<Object>> alldatas = null;
		Connection conn = null;
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		ArrayList<Object> datas = null;
		HashMap<String, Object> tmp = null;
		ArrayList<Object> datas2 = null;
		HashMap<String, Object> tmp2 = null;
		// String p1 = null;
		// String p2 = null;
		String changedate = "";
		int cnt = 0;

		try {
			conn = this.dataobj.getConnection(this.connid);
			ps1 = conn.prepareStatement(cmd.HeadSql(params.get("F001"), params.get("F002"), params.get("F003")));
			rs1 = ps1.executeQuery();
			if (rs1.next()) {
				alldatas = new HashMap<String, ArrayList<Object>>();
				datas = new ArrayList<Object>();
				tmp = new HashMap<String, Object>();
				tmp.put("TA006", DCIDate.parseShowDate(rs1.getString("TA006"), "/"));
				tmp.put("TA007", DCIDate.parseShowDate(rs1.getString("TA007"), "/"));
				tmp.put("TA008", rs1.getString("TA008"));
				tmp.put("TA009", rs1.getString("TA009"));
				tmp.put("TA012",
						DCIDate.parseShowDate(rs1.getString("TA012"), "/") + " "
								+ DCIDate.parseShowTime(rs1.getString("TA013")));
				tmp.put("currdate", DCIDate.parseString(rs1.getTimestamp("currdate"), "yyyy/MM/dd HH:mm"));
				datas.add(tmp);
				alldatas = new HashMap<String, ArrayList<Object>>();
				alldatas.put("headdatas", datas);
				// p1 = rs1.getString("TA006");
				// p2 = rs1.getString("TA007");

				ps2 = conn.prepareStatement(cmd.BodySql(params.get("F001"), params.get("F002"), params.get("F003"),
						params.get("F004"), params.get("F005"), params.get("F006"), params.get("F007"),
						params.get("F008"), params.get("F009"), params.get("F010")));
				rs2 = ps2.executeQuery();

				datas2 = new ArrayList<Object>();
				while (rs2.next()) {
					if (!changedate.equals(rs2.getString("TG007"))) {
						if (cnt != 0) {
							tmp2 = new HashMap<String, Object>();
							tmp2.put("dates", changedate);
							tmp2.put(changedate, datas);
							datas2.add(tmp2);
						}
						datas = new ArrayList<Object>();
					}
					tmp = new HashMap<String, Object>();
					tmp.put("TG002", rs2.getString("TG002"));
					tmp.put("TG003", rs2.getString("TG003"));
					tmp.put("TG007", DCIDate.parseShowDate(rs2.getString("TG007"), "/"));
					tmp.put("TG008", rs2.getString("TG008"));
					tmp.put("TG009", rs2.getString("TG009"));
					tmp.put("TG011", rs2.getString("TG011"));
					tmp.put("TG012", rs2.getString("TG012"));
					tmp.put("TG013", rs2.getString("TG013"));
					tmp.put("TG017", rs2.getString("TG017"));
					tmp.put("TG021", rs2.getString("TG021"));
					tmp.put("TG022", rs2.getString("TG022"));
					tmp.put("TA006", rs2.getString("TA006"));
					tmp.put("TA007", rs2.getString("TA007"));
					tmp.put("TA015", rs2.getString("TA015"));
					tmp.put("TB010", DCIDate.parseShowDate(rs2.getString("TB010"), "/"));
					tmp.put("TB021", rs2.getString("TB021"));
					tmp.put("MB002", rs2.getString("MB002"));
					tmp.put("MB003", rs2.getString("MB003"));
					tmp.put("MB004", rs2.getString("MB004"));
					tmp.put("MV002", rs2.getString("MV002"));
					tmp.put("MOID", rs2.getString("MOID"));
					tmp.put("ORDERID", rs2.getString("ORDERID"));
					datas.add(tmp);
					changedate = rs2.getString("TG007");
					cnt++;
				}
				if (cnt != 0) {
					tmp2 = new HashMap<String, Object>();
					tmp2.put("dates", changedate);
					tmp2.put(changedate, datas);
					datas2.add(tmp2);
				}
				alldatas.put("griddatas", datas2);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			this.dbctrl.closeConnection(rs1, ps1, null);
			this.dbctrl.closeConnection(rs2, ps2, conn);
		}

		return alldatas;
	}

	public byte[] getExportData(HashMap<String, String> params, String colstr, String lang) {
		byte[] alldatas = null;
		Connection conn = null;
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		ArrayList<HashMap<String, String>> cols = null;
		HSSFWorkbook workbook = null;
		HSSFSheet sheet = null;
		HSSFRow row = null;
		HSSFCell cell = null;
		CellStyle styleHead = null;
		DCIExcel excelFuncs = new DCIExcel();
		int cnt = 1;
		HashMap<String, String> checkCol = null;
		String[] transCol = new String[] { "TG007", "TB010" };
		String changedate = "";
		String value = null;
		Singleton s = Singleton.getInstance();

		try {
			if (!DCIString.isNullOrEmpty(colstr)) {
				cols = new ObjectMapper().readValue(colstr, new TypeReference<ArrayList<HashMap<String, String>>>() {
				});

				checkCol = new HashMap<String, String>();
				for (int i = 0; i < transCol.length; i++) {
					checkCol.put(transCol[i], "");
				}

				workbook = new HSSFWorkbook();

				conn = this.dataobj.getConnection(this.connid);
				ps1 = conn.prepareStatement(cmd.HeadSql(params.get("F001"), params.get("F002"), params.get("F003")));
				rs1 = ps1.executeQuery();
				if (rs1.next()) {
					ps2 = conn.prepareStatement(cmd.BodySql(params.get("F001"), params.get("F002"), params.get("F003"),
							params.get("F004"), params.get("F005"), params.get("F006"), params.get("F007"),
							params.get("F008"), params.get("F009"), params.get("F010")));
					rs2 = ps2.executeQuery();

					while (rs2.next()) {
						if (!changedate.equals(rs2.getString("TG007"))) {
							sheet = workbook.createSheet(rs2.getString("TG007"));
							row = sheet.createRow(0);
							styleHead = excelFuncs.buildDefHeaderStyle(workbook);
							excelFuncs.setHeaderCells(sheet, row, cell, styleHead, cols);
							cnt = 1;
						}

						row = sheet.createRow(cnt);
						for (int j = 0; j < cols.size(); j++) {
							if (!Boolean.parseBoolean(cols.get(j).get("hidden"))) {
								cell = row.createCell(j);
								if (DCIString.isNullOrEmpty(rs2.getString(cols.get(j).get("dataIndex")))) {
									cell.setCellValue("");
								} else {
									value = rs2.getString(cols.get(j).get("dataIndex"));
									if (cols.get(j).get("dataIndex").equals("TG013")) {
										excelFuncs.setCellLightStyle(workbook, cell, value);
									} else if (cols.get(j).get("dataIndex").equals("TG022")) {
										if (value.equals("1")) {
											cell.setCellValue(s.getLanguage(lang, "direct_materials"));
										} else if (value.equals("2")) {
											cell.setCellValue(s.getLanguage(lang, "indirect_materials"));
										} else if (value.equals("3")) {
											cell.setCellValue(s.getLanguage(lang, "client_supplied_materials"));
										} else if (value.equals("5")) {
											cell.setCellValue(s.getLanguage(lang, "custom_supplied_materials"));
										} else {
											cell.setCellValue(value);
										}
									} else if (checkCol.containsKey(cols.get(j).get("dataIndex"))) {
										cell.setCellValue(DCIDate.parseShowDate(value, "/"));
									} else {
										cell.setCellValue(value);
									}
								}
							}
						}
						changedate = rs2.getString("TG007");
						cnt++;
					}
				}
				if (cnt == 1) {
					sheet = workbook.createSheet(this.getClass().getSimpleName());
					row = sheet.createRow(0);
					styleHead = excelFuncs.buildDefHeaderStyle(workbook);
					excelFuncs.setHeaderCells(sheet, row, cell, styleHead, cols);
				}
				alldatas = excelFuncs.transToDownloadObj(workbook);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			this.dbctrl.closeConnection(rs1, ps1, null);
			this.dbctrl.closeConnection(rs2, ps2, conn);
		}

		return alldatas;
	}
}
