package com.dsc.dci.jweb.funcs.wpp.wpp005;

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
import com.dsc.dci.sqlcode.funcs.wpp.sqlWPP005;

public class WPP005 {

	private DBControl dbctrl;
	private sqlWPP005 cmd;
	private DataDatabaseObject dataobj;
	// private final String connid = "WPPQuery";
	private String connid;

	public WPP005(String compid) {
		this.dbctrl = new DBControl();
		this.cmd = new sqlWPP005();
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
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<Object> datas = null;
		HashMap<String, Object> tmp = null;
		int cnt = 0;

		try {
			conn = this.dataobj.getConnection(this.connid);
			ps = conn.prepareStatement(cmd.BodySql(params.get("F001"), params.get("F002"), params.get("F003"),
					params.get("F004"), params.get("F005"), params.get("F006")));
			rs = ps.executeQuery();

			alldatas = new HashMap<String, ArrayList<Object>>();
			while (rs.next()) {
				if (cnt == 0) {
					datas = new ArrayList<Object>();
					tmp = new HashMap<String, Object>();
					tmp.put("TK017",
							DCIDate.parseShowDate(rs.getString("TK017"), "/") + " "
									+ DCIDate.parseShowTime(rs.getString("TK018")));
					tmp.put("currdate", DCIDate.parseString(rs.getTimestamp("currdate"), "yyyy/MM/dd HH:mm"));
					datas.add(tmp);
					alldatas.put("headdatas", datas);
					datas = new ArrayList<Object>();
				}
				tmp = new HashMap<String, Object>();
				tmp.put("TK001", rs.getString("TK001"));
				tmp.put("TK003", DCIDate.parseShowDate(rs.getString("TK003"), "/"));
				tmp.put("TK006", rs.getString("TK006"));
				tmp.put("TK007", rs.getString("TK007"));
				tmp.put("TK008", rs.getString("TK008"));
				tmp.put("TK009", rs.getString("TK009"));
				tmp.put("TK010", rs.getString("TK010"));
				tmp.put("TK012", rs.getString("TK012"));
				tmp.put("TK014", rs.getString("TK014"));
				tmp.put("TK015", rs.getString("TK015"));
				tmp.put("TK016", rs.getString("TK016"));
				tmp.put("TK019", rs.getString("TK019"));
				tmp.put("TK020", rs.getString("TK020"));
				// tmp.put("TK022", rs.getString("TK022"));
				tmp.put("TK023", rs.getString("TK023"));
				tmp.put("TK025", rs.getString("TK025"));
				tmp.put("TK027", rs.getString("TK027"));
				tmp.put("TK029", DCIDate.parseShowDate(rs.getString("TK029"), "/"));
				tmp.put("TE003", rs.getString("TE003"));
				tmp.put("MB002", rs.getString("MB002"));
				tmp.put("MB003", rs.getString("MB003"));
				// tmp.put("TB010", DCIDate.parseShowDate(rs.getString("TB010"),
				// "/"));
				tmp.put("NOTE", rs.getString("NOTE"));
				tmp.put("TJ001", rs.getString("TJ001"));
				tmp.put("TJ002", rs.getString("TJ002"));

				datas.add(tmp);
				cnt++;
			}
			alldatas.put("griddatas", datas);

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			this.dbctrl.closeConnection(rs, ps, conn);
		}

		return alldatas;
	}

	public HashMap<String, ArrayList<Object>> buildSubGrid(String subtype, HashMap<String, String> params, String lang) {
		HashMap<String, ArrayList<Object>> alldatas = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<Object> datas = null;
		HashMap<String, Object> tmp = null;

		try {
			conn = this.dataobj.getConnection(this.connid);
			ps = conn.prepareStatement(cmd.subNotesql(params.get("TJ001"), params.get("TJ002")));
			rs = ps.executeQuery();
			datas = new ArrayList<Object>();
			while (rs.next()) {
				tmp = new HashMap<String, Object>();
				if (rs.getString("TJ003").equals("business")) {
					tmp.put("TJ003", Singleton.getInstance().getLanguage(lang, "business"));
				} else {
					tmp.put("TJ003", rs.getString("TJ003"));
				}
				// tmp.put("TJ003", rs.getString("TJ003"));
				tmp.put("TJ005", rs.getString("TJ005"));
				tmp.put("TJ006", rs.getString("TJ006"));

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
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<HashMap<String, String>> cols = null;
		HSSFWorkbook workbook = null;
		HSSFSheet sheet = null;
		HSSFRow row = null;
		HSSFCell cell = null;
		CellStyle styleHead = null;
		DCIExcel excelFuncs = new DCIExcel();
		int cnt = 1;
		HashMap<String, String> checkCol = null;
		String[] transCol = new String[] { "TK003", "TK029" };
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
				ps = conn.prepareStatement(cmd.BodySql(params.get("F001"), params.get("F002"), params.get("F003"),
						params.get("F004"), params.get("F005"), params.get("F006")));
				rs = ps.executeQuery();

				while (rs.next()) {
					row = sheet.createRow(cnt);
					for (int j = 0; j < cols.size(); j++) {
						if (!Boolean.parseBoolean(cols.get(j).get("hidden"))) {
							cell = row.createCell(j);
							if (DCIString.isNullOrEmpty(rs.getString(cols.get(j).get("dataIndex")))) {
								cell.setCellValue("");
							} else {
								value = rs.getString(cols.get(j).get("dataIndex"));
								if (cols.get(j).get("dataIndex").equals("TK019")) {
									excelFuncs.setCellLightStyle(workbook, cell, value);
								} else if (cols.get(j).get("dataIndex").equals("TK014")) {
									if (value.equals("1")) {
										cell.setCellValue(s.getLanguage(lang, "purchasing"));
									} else if (value.equals("2")) {
										cell.setCellValue(s.getLanguage(lang, "sub_contract"));
									} else {
										cell.setCellValue(value);
									}
								} else if (cols.get(j).get("dataIndex").equals("TK015")) {
									if (value.equals("1")) {
										cell.setCellValue(s.getLanguage(lang, "scheduling"));
									} else {
										cell.setCellValue("");
									}
								} else if (cols.get(j).get("dataIndex").equals("TE003")) {
									if (value.equals("1")) {
										cell.setCellValue(s.getLanguage(lang, "waiting"));
									} else if (value.equals("2")) {
										cell.setCellValue(s.getLanguage(lang, "preparing"));
									} else if (value.equals("3")) {
										cell.setCellValue(s.getLanguage(lang, "issuing"));
									} else if (value.equals("4")) {
										cell.setCellValue(s.getLanguage(lang, "completed"));
									} else if (value.equals("5")) {
										cell.setCellValue(s.getLanguage(lang, "material_shortage"));
									} else {
										cell.setCellValue(value);
									}
								} else if (cols.get(j).get("dataIndex").equals("TK020")) {
									if (value.equals("1")) {
										cell.setCellValue(s.getLanguage(lang, "non_produced"));
									} else if (value.equals("2")) {
										cell.setCellValue(s.getLanguage(lang, "material_released"));
									} else if (value.equals("3")) {
										cell.setCellValue(s.getLanguage(lang, "producing"));
									} else if (value.equals("Y")) {
										cell.setCellValue(s.getLanguage(lang, "finished"));
									} else if (value.equals("y")) {
										cell.setCellValue(s.getLanguage(lang, "assigned_completed"));
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

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			this.dbctrl.closeConnection(rs, ps, conn);
		}

		return alldatas;
	}
}
