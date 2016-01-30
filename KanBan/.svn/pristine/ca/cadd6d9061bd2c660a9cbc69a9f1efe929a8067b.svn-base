package com.dsc.dci.jweb.funcs.wpp.wpp006;

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
import com.dsc.dci.sqlcode.funcs.wpp.sqlWPP006;

public class WPP006 {

	private DBControl dbctrl;
	private sqlWPP006 cmd;
	private DataDatabaseObject dataobj;
	// private final String connid = "WPPQuery";
	private String connid;
	private DecimalFormat df;

	public WPP006(String compid) {
		this.dbctrl = new DBControl();
		this.cmd = new sqlWPP006();
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
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		ArrayList<Object> datas = null;
		HashMap<String, Object> tmp = null;

		try {
			conn = this.dataobj.getConnection(this.connid);
			ps1 = conn.prepareStatement(this.cmd.HeadSql(params.get("F001"), params.get("F002"), params.get("F003")));
			rs1 = ps1.executeQuery();

			if (rs1.next()) {
				alldatas = new HashMap<String, ArrayList<Object>>();
				datas = new ArrayList<Object>();
				tmp = new HashMap<String, Object>();
				tmp.put("TL005", DCIDate.parseShowDate(rs1.getString("TL005"), "/"));
				tmp.put("TL006", DCIDate.parseShowDate(rs1.getString("TL006"), "/"));
				if (rs1.getString("TL009") == null || rs1.getString("TL009") == null) {
					tmp.put("TL009", "");
				} else {
					tmp.put("TL009",
							DCIDate.parseShowDate(rs1.getString("TL009"), "/") + "-"
									+ DCIDate.parseShowTime(rs1.getString("TL010")));
				}
				tmp.put("TL011",
						DCIDate.parseShowDate(rs1.getString("TL011"), "/") + "-"
								+ DCIDate.parseShowTime(rs1.getString("TL012")));

				if (params.get("F003").equals("2")) {
					tmp.put("value", getMOValue(conn, params));
					tmp.put("TL016", rs1.getString("TL016"));
				} else {
					tmp.put("value", "");
					tmp.put("TL016", "");
				}
				datas.add(tmp);
				alldatas.put("headdatas", datas);

				ps2 = conn
						.prepareStatement(this.cmd.BodySql(params.get("F001"), params.get("F002"), params.get("F003")));
				rs2 = ps2.executeQuery();
				datas = new ArrayList<Object>();
				while (rs2.next()) {
					tmp = new HashMap<String, Object>();
					if (rs2.getString("TN022").equals("N")) {
						if (rs2.getDouble("TN018") == 0) {
							tmp.put("TN022", "N");
						} else {
							tmp.put("TN022", "D");
						}
					} else {
						tmp.put("TN022", rs2.getString("TN022"));
					}
					tmp.put("TN023", rs2.getString("TN023"));
					tmp.put("TN024", rs2.getString("TN024"));
					tmp.put("TN027", rs2.getString("TN027"));
					tmp.put("TN006", rs2.getString("TN006"));
					tmp.put("TN007", DCIDate.parseShowDate(rs2.getString("TN007"), "/"));
					tmp.put("ORDER_ID", rs2.getString("ORDER_ID"));
					tmp.put("TN011", rs2.getString("TN011"));
					tmp.put("TN012", rs2.getString("TN012"));
					tmp.put("TN013", rs2.getString("TN013"));
					tmp.put("TN014", rs2.getString("TN014"));
					tmp.put("TN015", rs2.getString("TN015"));
					tmp.put("TN017", rs2.getString("TN017"));
					tmp.put("TN021", rs2.getString("TN021"));
					tmp.put("TN019", rs2.getString("TN019"));
					tmp.put("TN018", rs2.getString("TN018"));
					tmp.put("TN020", DCIDate.parseShowDate(rs2.getString("TN020"), "/"));
					tmp.put("MC007", rs2.getString("MC007"));
					tmp.put("TN016", rs2.getString("TN016"));
					tmp.put("TN025", rs2.getString("TN025"));
					tmp.put("NOTE", rs2.getString("NOTE"));
					tmp.put("TN008", rs2.getString("TN008"));
					tmp.put("TN009", rs2.getString("TN009"));
					tmp.put("TN010", rs2.getString("TN010"));
					datas.add(tmp);
				}
				alldatas.put("griddatas", datas);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			this.dbctrl.closeConnection(rs2, ps2, null);
			this.dbctrl.closeConnection(rs1, ps1, conn);
		}

		return alldatas;
	}

	private String getMOValue(Connection conn, HashMap<String, String> params) {
		String value = null;
		boolean reconn = false;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			if (conn == null) {
				conn = this.dataobj.getConnection(this.connid);
				reconn = true;
			}

			ps = conn.prepareStatement(this.cmd.HeadSql2(params.get("F001"), params.get("F002"), params.get("F003")));
			rs = ps.executeQuery();

			if (rs.next()) {
				value = this.df.format((Math.floor((rs.getDouble("value") * 10000) + 0.5)) / 100) + "%";
			} else {
				value = "";
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (reconn) {
				this.dbctrl.closeConnection(rs, ps, conn);
			} else {
				this.dbctrl.closeConnection(rs, ps, null);
			}
		}
		return value;
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
				ps = conn.prepareStatement(cmd.subMOsql(params.get("TN008"), params.get("TN009"), params.get("TN010"),
						params.get("TN013")));
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
				ps1 = conn
						.prepareStatement(this.cmd.HeadSql(params.get("F001"), params.get("F002"), params.get("F003")));
				rs1 = ps1.executeQuery();

				if (rs1.next()) {
					ps2 = conn.prepareStatement(this.cmd.BodySql(params.get("F001"), params.get("F002"),
							params.get("F003")));
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
									if (cols.get(j).get("dataIndex").equals("TN022")) {
										if (value.equals("N")) {
											if (rs2.getDouble("TN018") != 0) {
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

									} else if (cols.get(j).get("dataIndex").equals("TN023")) {
										if (value.equals("Y")) {
											excelFuncs.setCellLightStyle(workbook, cell, "R");
										} else {
											excelFuncs.setCellLightStyle(workbook, cell, value);
										}
									} else if (cols.get(j).get("dataIndex").equals("TN027")) {
										if (value.equals("0")) {
											cell.setCellValue(s.getLanguage(lang, "unknown"));
										} else if (value.equals("1")) {
											cell.setCellValue(s.getLanguage(lang, "started"));
										} else if (value.equals("2")) {
											cell.setCellValue(s.getLanguage(lang, "non_produced"));
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
			this.dbctrl.closeConnection(rs2, ps2, null);
			this.dbctrl.closeConnection(rs1, ps1, conn);
		}

		return alldatas;
	}
}
