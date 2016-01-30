package com.dsc.dci.jweb.funcs.wpp.wpp007;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
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
import com.dsc.dci.sqlcode.funcs.wpp.sqlWPP007;

public class WPP007 {

	private DBControl dbctrl;
	private sqlWPP007 cmd;
	private DataDatabaseObject dataobj;
	// private final String connid = "WPPQuery";
	private String connid;
	private DecimalFormat df;

	public WPP007(String compid) {
		this.dbctrl = new DBControl();
		this.cmd = new sqlWPP007();
		this.dataobj = DataDatabaseObject.getInstance();
		this.df = new DecimalFormat("##0.00");
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

			ps = conn.prepareStatement(cmd.F002Sql());
			rs = ps.executeQuery();
			datas = new ArrayList<Object>();
			while (rs.next()) {
				tmp = new HashMap<String, Object>();
				tmp.put("value", rs.getString("value"));
				tmp.put("label", rs.getString("label"));
				datas.add(tmp);
			}

			alldatas.put("f002value", datas);
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
			ps = conn.prepareStatement(this.cmd.BodySql(params.get("F001"), params.get("F002")));
			rs = ps.executeQuery();

			alldatas = new HashMap<String, ArrayList<Object>>();
			while (rs.next()) {
				if (cnt == 0) {
					datas = new ArrayList<Object>();
					tmp = new HashMap<String, Object>();
					tmp.put("TN031",
							DCIDate.parseShowDate(rs.getString("TN031"), "/") + "-"
									+ DCIDate.parseShowTime(rs.getString("TN032")));
					datas.add(tmp);
					alldatas.put("headdatas", datas);
					cnt++;
					datas = new ArrayList<Object>();
				}
				tmp = new HashMap<String, Object>();
				if (rs.getString("TN022").equals("N")) {
					if (rs.getDouble("TN018") == 0) {
						tmp.put("TN022", "N");
					} else {
						tmp.put("TN022", "D");
					}
				} else {
					tmp.put("TN022", rs.getString("TN022"));
				}

				tmp.put("TN023", rs.getString("TN023"));
				tmp.put("TN024", rs.getString("TN024"));
				tmp.put("value", this.df.format((Math.floor((rs.getDouble("value") * 10000) + 0.5)) / 100) + "%");
				tmp.put("TN006", rs.getString("TN006"));
				tmp.put("TN007", DCIDate.parseShowDate(rs.getString("TN007"), "/"));
				tmp.put("ORDER_ID", rs.getString("ORDER_ID"));
				tmp.put("TN011", rs.getString("TN011"));
				tmp.put("TN012", rs.getString("TN012"));
				tmp.put("TN013", rs.getString("TN013"));
				tmp.put("TN014", rs.getString("TN014"));
				tmp.put("TN015", rs.getString("TN015"));
				tmp.put("TN017", rs.getString("TN017"));
				tmp.put("TN021", rs.getString("TN021"));
				tmp.put("TN019", rs.getString("TN019"));
				tmp.put("TN018", rs.getString("TN018"));
				tmp.put("TN020", DCIDate.parseShowDate(rs.getString("TN020"), "/"));
				tmp.put("MC007", rs.getString("MC007"));
				tmp.put("TN016", rs.getString("TN016"));
				tmp.put("TN025", rs.getString("TN025"));
				tmp.put("NOTE", rs.getString("NOTE"));
				tmp.put("TN008", rs.getString("TN008"));
				tmp.put("TN009", rs.getString("TN009"));
				tmp.put("TN010", rs.getString("TN010"));
				datas.add(tmp);
			}
			alldatas.put("griddatas", datas);

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			this.dbctrl.closeConnection(rs, ps, conn);
		}

		return alldatas;
	}

	public HashMap<String, ArrayList<Object>> buildSubGrid(String subtype, HashMap<String, String> params) {
		HashMap<String, ArrayList<Object>> alldatas = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<Object> datas = null;
		HashMap<String, Object> tmp = null;

		try {
			conn = this.dataobj.getConnection(this.connid);
			if (subtype.equals("NOTE")) {
				ps = conn
						.prepareStatement(cmd.subNotesql(params.get("TN008"), params.get("TN009"), params.get("TN010")));
			} else {
				ps = conn.prepareStatement(cmd.subMOsql(params.get("TN008"), params.get("TN009"), params.get("TN010")));
			}

			rs = ps.executeQuery();
			datas = new ArrayList<Object>();
			while (rs.next()) {
				tmp = new HashMap<String, Object>();

				if (subtype.equals("NOTE")) {
					tmp.put("TP005", rs.getString("TP005"));
					tmp.put("TP006", rs.getString("TP006"));
				} else {
					tmp.put("TO009", rs.getString("TO009"));
					tmp.put("TO025", rs.getString("TO025"));
					tmp.put("TO026", rs.getString("TO026"));
					tmp.put("TO027", rs.getString("TO027"));
					tmp.put("TO021", rs.getString("TO021"));
					tmp.put("MO_ID", rs.getString("MO_ID"));
					tmp.put("TO014", rs.getString("TO014"));
					tmp.put("TO023", DCIDate.parseShowDate(rs.getString("TO023"), "/"));
					tmp.put("TO016", DCIDate.parseShowDate(rs.getString("TO016"), "/"));
					tmp.put("TO010", rs.getString("TO010"));
					tmp.put("TO011", rs.getString("TO011"));
					tmp.put("TO017", rs.getString("TO017"));
					tmp.put("TO013", rs.getString("TO013"));
					tmp.put("TO018", rs.getString("TO018"));
					tmp.put("TO022", DCIDate.parseShowDate(rs.getString("TO022"), "/"));
					tmp.put("TO015", DCIDate.parseShowDate(rs.getString("TO015"), "/"));
					tmp.put("TO024", rs.getString("TO024"));
					tmp.put("TO012", rs.getString("TO012"));
				}
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
		String[] transCol = new String[] { "TN007", "TN020" };
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
				ps = conn.prepareStatement(this.cmd.BodySql(params.get("F001"), params.get("F002")));
				rs = ps.executeQuery();

				while (rs.next()) {
					row = sheet.createRow(cnt);
					for (int j = 0; j < cols.size(); j++) {
						if (!Boolean.parseBoolean(cols.get(j).get("hidden"))) {
							cell = row.createCell(j);
							if (DCIString.isNullOrEmpty(rs.getString(cols.get(j).get("dataIndex")))) {
								if (cols.get(j).get("dataIndex").equals("value")) {
									cell.setCellValue("0.00%");
								} else {
									cell.setCellValue("");
								}
							} else {
								if (cols.get(j).get("dataIndex").equals("TN022")) {
									value = rs.getString(cols.get(j).get("dataIndex"));
									if (value.equals("N")) {
										if (rs.getDouble("TN018") != 0) {
											value = "D";
										}
									}

									if (value.equals("Y")) {
										cell.setCellValue(s.getLanguage(lang, "closed"));
									} else if (value.equals("y")) {
										cell.setCellValue(s.getLanguage(lang, "assigned_closed"));
									} else if (value.equals("N")) {
										cell.setCellValue(s.getLanguage(lang, "non_shipping"));
									} else if (value.equals("D")) {
										cell.setCellValue(s.getLanguage(lang, "shipping"));
									} else {
										cell.setCellValue(value);
									}

								} else if (cols.get(j).get("dataIndex").equals("value")) {
									cell.setCellValue(this.df.format((Math.floor((rs.getDouble("value") * 10000) + 0.5)) / 100)
											+ "%");
								} else if (cols.get(j).get("dataIndex").equals("TN023")) {
									value = rs.getString(cols.get(j).get("dataIndex"));
									if (value.equals("Y")) {
										excelFuncs.setCellLightStyle(workbook, cell, "R");
									} else {
										excelFuncs.setCellLightStyle(workbook, cell,
												rs.getString(cols.get(j).get("dataIndex")));
									}
								} else if (checkCol.containsKey(cols.get(j).get("dataIndex"))) {
									cell.setCellValue(DCIDate.parseShowDate(rs.getString(cols.get(j).get("dataIndex")),
											"/"));
								} else {
									cell.setCellValue(rs.getString(cols.get(j).get("dataIndex")));
								}
							}
						}
					}
					cnt++;
				}
				alldatas = excelFuncs.transToDownloadObj(workbook);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			this.dbctrl.closeConnection(rs, ps, conn);
		}

		return alldatas;
	}
}
