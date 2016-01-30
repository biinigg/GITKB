package com.dsc.dci.jweb.funcs.wpp.wpp002;

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
import com.dsc.dci.sqlcode.funcs.wpp.sqlWPP002;

public class WPP002 {

	private DBControl dbctrl;
	private sqlWPP002 cmd;
	private DataDatabaseObject dataobj;
	// private final String connid = "WPPQuery";
	private String connid;

	public WPP002(String compid) {
		this.dbctrl = new DBControl();
		this.cmd = new sqlWPP002();
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

			ps = conn.prepareStatement(cmd.F005Sql());
			rs = ps.executeQuery();
			datas = new ArrayList<Object>();
			while (rs.next()) {
				tmp = new HashMap<String, Object>();
				tmp.put("value", rs.getString("value"));
				tmp.put("label", rs.getString("label"));
				datas.add(tmp);
			}

			alldatas.put("f005value", datas);
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
		String MB01 = null;

		try {
			conn = this.dataobj.getConnection(this.connid);
			ps1 = conn.prepareStatement(cmd.HeadSql(params.get("F001"), params.get("F002"), params.get("F004")));
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

				ps2 = conn.prepareStatement(cmd.getParameter());
				rs2 = ps2.executeQuery();

				if (rs2.next()) {
					MB01 = rs2.getString("MB001");
				}

				this.dbctrl.closeConnection(rs2, ps2, null);

				ps2 = conn.prepareStatement(cmd.BodySql(params.get("F001"), params.get("F002"), params.get("F003"),
						params.get("F004"), params.get("F005"), params.get("F006"), params.get("F007"), MB01));
				rs2 = ps2.executeQuery();

				datas = new ArrayList<Object>();
				while (rs2.next()) {
					tmp = new HashMap<String, Object>();
					tmp.put("dtp", rs2.getString("dtp"));
					tmp.put("C02", rs2.getString("C02"));
					tmp.put("C03", rs2.getString("C03"));
					tmp.put("MOID", rs2.getString("MOID"));
					tmp.put("C07", DCIDate.parseShowDate(rs2.getString("C07"), "/"));
					tmp.put("C08", rs2.getString("C08"));
					tmp.put("C09", rs2.getString("C09"));
					tmp.put("C10", rs2.getString("C10"));
					tmp.put("C11", rs2.getString("C11"));
					tmp.put("C12", rs2.getString("C12"));
					tmp.put("C13", rs2.getString("C13"));
					tmp.put("C17", rs2.getString("C17"));
					tmp.put("C21", rs2.getString("C21"));
					tmp.put("C22", rs2.getString("C22"));
					tmp.put("TA006", rs2.getString("TA006"));
					tmp.put("TA007", rs2.getString("TA007"));
					tmp.put("TA015", rs2.getString("TA015"));
					tmp.put("MB002", rs2.getString("MB002"));
					tmp.put("MB003", rs2.getString("MB003"));
					tmp.put("MB004", rs2.getString("MB004"));
					tmp.put("MV002", rs2.getString("MV002"));
					tmp.put("TB010", DCIDate.parseShowDate(rs2.getString("TB010"), "/"));
					tmp.put("ORDERID", rs2.getString("ORDERID"));
					tmp.put("TB021", rs2.getString("TB021"));
					datas.add(tmp);
				}
				alldatas.put("griddatas", datas);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			this.dbctrl.closeConnection(rs1, ps1, null);
			this.dbctrl.closeConnection(rs2, ps2, conn);
		}

		return alldatas;
	}

	public HashMap<String, ArrayList<Object>> buildSubGrid(HashMap<String, String> params) {
		HashMap<String, ArrayList<Object>> alldatas = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<Object> datas = null;
		HashMap<String, Object> tmp = null;

		try {
			conn = this.dataobj.getConnection(this.connid);
			ps = conn.prepareStatement(cmd.subGridSql(params.get("TF002"), params.get("TF003")));

			rs = ps.executeQuery();
			datas = new ArrayList<Object>();
			while (rs.next()) {
				tmp = new HashMap<String, Object>();
				tmp.put("TD001", rs.getString("TD001"));
				tmp.put("TD012", DCIDate.parseShowDate(rs.getString("TD012"), "/"));
				tmp.put("TD008", rs.getString("TD008"));
				tmp.put("TD009", rs.getString("TD009"));
				tmp.put("TD015", rs.getString("TD015"));
				tmp.put("TK008", rs.getString("TK008"));
				tmp.put("TD015-TK008", rs.getDouble("TD015") - rs.getDouble("TK008"));
				tmp.put("NOTE", DCIDate.parseShowDate(rs.getString("NOTE"), "/"));
				datas.add(tmp);
			}

			alldatas = new HashMap<String, ArrayList<Object>>();
			alldatas.put("griddatas", datas);
			this.dbctrl.closeConnection(rs, ps, null);

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			this.dbctrl.closeConnection(rs, ps, conn);
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
		String MB01 = null;
		HSSFWorkbook workbook = null;
		HSSFSheet sheet = null;
		HSSFRow row = null;
		HSSFCell cell = null;
		CellStyle styleHead = null;
		DCIExcel excelFuncs = new DCIExcel();
		int cnt = 1;
		HashMap<String, String> checkCol = null;
		String[] transCol = new String[] { "C07", "TB010" };
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
				sheet = workbook.createSheet(this.getClass().getSimpleName());
				row = sheet.createRow(0);
				styleHead = excelFuncs.buildDefHeaderStyle(workbook);
				excelFuncs.setHeaderCells(sheet, row, cell, styleHead, cols);

				conn = this.dataobj.getConnection(this.connid);
				ps1 = conn.prepareStatement(cmd.HeadSql(params.get("F001"), params.get("F002"), params.get("F004")));
				rs1 = ps1.executeQuery();
				if (rs1.next()) {
					ps2 = conn.prepareStatement(cmd.getParameter());
					rs2 = ps2.executeQuery();

					if (rs2.next()) {
						MB01 = rs2.getString("MB001");
					}

					this.dbctrl.closeConnection(rs2, ps2, null);

					ps2 = conn.prepareStatement(cmd.BodySql(params.get("F001"), params.get("F002"), params.get("F003"),
							params.get("F004"), params.get("F005"), params.get("F006"), params.get("F007"), MB01));
					rs2 = ps2.executeQuery();

					while (rs2.next()) {
						row = sheet.createRow(cnt);
						for (int j = 0; j < cols.size(); j++) {
							if (!Boolean.parseBoolean(cols.get(j).get("hidden"))) {
								cell = row.createCell(j);
								if (DCIString.isNullOrEmpty(rs2.getString(cols.get(j).get("dataIndex")))) {
									cell.setCellValue("");
								} else {
									value = rs2.getString(cols.get(j).get("dataIndex"));
									if (cols.get(j).get("dataIndex").equals("C13")) {
										excelFuncs.setCellLightStyle(workbook, cell, value);
									} else if (cols.get(j).get("dataIndex").equals("C22")) {
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
						cnt++;
					}
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
