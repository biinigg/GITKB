package com.dsc.dci.jweb.funcs.wpp.wpp001;

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
import org.apache.poi.ss.usermodel.Font;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import com.dci.jweb.DataBaseLib.Database.DataDatabaseObject;
import com.dci.jweb.PublicLib.DBControl;
import com.dci.jweb.PublicLib.DCIDate;
import com.dci.jweb.PublicLib.DCIExcel;
import com.dci.jweb.PublicLib.DCIString;
import com.dsc.dci.jweb.pub.Singleton;
import com.dsc.dci.sqlcode.funcs.wpp.sqlPublic;
import com.dsc.dci.sqlcode.funcs.wpp.sqlWPP001;

public class WPP001 {

	private DBControl dbctrl;
	private sqlWPP001 cmd;
	private DataDatabaseObject dataobj;
	// private final String connid = "WPPQuery";
	private String connid;

	public WPP001(String compid) {
		this.dbctrl = new DBControl();
		this.cmd = new sqlWPP001();
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

			ps = conn.prepareStatement(cmd.F003Sql());
			rs = ps.executeQuery();
			datas = new ArrayList<Object>();
			while (rs.next()) {
				tmp = new HashMap<String, Object>();
				tmp.put("value", rs.getString("value"));
				tmp.put("label", rs.getString("label"));
				datas.add(tmp);
			}

			alldatas.put("f003value", datas);
			this.dbctrl.closeConnection(rs, ps, null);

			ps = conn.prepareStatement(cmd.F004Sql());
			rs = ps.executeQuery();
			datas = new ArrayList<Object>();
			while (rs.next()) {
				tmp = new HashMap<String, Object>();
				tmp.put("value", rs.getString("value"));
				tmp.put("label", rs.getString("label"));
				datas.add(tmp);
			}

			alldatas.put("f004value", datas);
			this.dbctrl.closeConnection(rs, ps, null);

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
		String tmpparams = null;
		String[] tmpparams2 = null;

		try {
			conn = this.dataobj.getConnection(this.connid);
			if (subtype.equals("TB009")) {
				ps = conn.prepareStatement(cmd.subTB009sql(params.get("TB001"), params.get("TB002"),
						params.get("TB003"), params.get("TB004"), params.get("TB005")));
			} else if (subtype.equals("NOTE")) {
				tmpparams = params.get("ORDERID");
				if (DCIString.isNullOrEmpty(tmpparams)) {
					tmpparams2 = new String[] { "", "", "" };
				} else {
					if (tmpparams.split("-").length == 3) {
						tmpparams2 = tmpparams.split("-");
					} else {
						tmpparams2 = new String[] { "", "", "" };
					}
				}
				//System.out.println(tmpparams + tmpparams2.length);
				ps = conn.prepareStatement(cmd.subNotesql(params.get("TB006"), params.get("TB007"), tmpparams2[0],
						tmpparams2[1], tmpparams2[2]));
			} else if (subtype.equals("TB017")) {
				ps = conn.prepareStatement(cmd.subTB017sql(params.get("TB001"), params.get("TB002"),
						params.get("TB003"), params.get("TB004"), params.get("TB005")));
			}
			rs = ps.executeQuery();
			datas = new ArrayList<Object>();
			while (rs.next()) {
				tmp = new HashMap<String, Object>();
				if (subtype.equals("TB009")) {
					tmp.put("TC007", DCIDate.parseShowDate(rs.getString("TC007"), "/"));
					tmp.put("TC008", rs.getString("TC008"));
					tmp.put("TC009", rs.getString("TC009"));
					tmp.put("TC010", rs.getString("TC010"));
					tmp.put("TC011", rs.getString("TC011"));
					tmp.put("TC012", rs.getString("TC012"));
				} else if (subtype.equals("NOTE")) {
					if (rs.getString("TJ003").equals("business")) {
						tmp.put("TJ003", Singleton.getInstance().getLanguage(lang, "business"));
					} else {
						tmp.put("TJ003", rs.getString("TJ003"));
					}
					tmp.put("TJ005", rs.getString("TJ005"));
					tmp.put("TJ006", rs.getString("TJ006"));
				} else if (subtype.equals("TB017")) {
					tmp.put("TG002", rs.getString("TG002"));
					tmp.put("TG003", rs.getString("TG003"));
					tmp.put("TG007", DCIDate.parseShowDate(rs.getString("TG007"), "/"));
					tmp.put("TG008", rs.getString("TG008"));
					tmp.put("TG012", rs.getString("TG012"));
					tmp.put("TG013", rs.getString("TG013"));
					tmp.put("TG021", rs.getString("TG021"));
					tmp.put("MB002", rs.getString("MB002"));
					tmp.put("MB003", rs.getString("MB003"));
					tmp.put("MB004", rs.getString("MB004"));
					tmp.put("MB025", rs.getString("MB025"));
					tmp.put("MAN01", rs.getString("MAN01"));
					tmp.put("MAN02", rs.getString("MAN02"));
					tmp.put("TB011", rs.getString("TB011"));
				}
				datas.add(tmp);
			}

			alldatas = new HashMap<String, ArrayList<Object>>();
			alldatas.put("griddatas", datas);
			this.dbctrl.closeConnection(rs, ps, null);

			if (subtype.equals("TB009")) {
				ps = conn.prepareStatement(cmd.subTB009Headsql());
				rs = ps.executeQuery();
				datas = new ArrayList<Object>();
				while (rs.next()) {
					tmp = new HashMap<String, Object>();
					tmp.put("MA008", rs.getString("MA008"));
					tmp.put("MA009", rs.getString("MA009"));
					tmp.put("MA010", rs.getString("MA010"));
					tmp.put("MA011", rs.getString("MA011"));
					datas.add(tmp);
				}

				alldatas.put("head", datas);
				this.dbctrl.closeConnection(rs, ps, null);
			}

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
		HashMap<String, String> wppma = null;
		String p1 = null;
		String p2 = null;
		String p3 = null;
		String p4 = null;

		try {
			conn = this.dataobj.getConnection(this.connid);
			ps1 = conn.prepareStatement(cmd.HeadSql(params.get("F001"), params.get("F002"), params.get("F003"),
					params.get("F004")));
			rs1 = ps1.executeQuery();
			if (rs1.next()) {
				alldatas = new HashMap<String, ArrayList<Object>>();
				datas = new ArrayList<Object>();
				tmp = new HashMap<String, Object>();
				tmp.put("TA001", rs1.getString("TA001"));
				tmp.put("TA002", rs1.getString("TA002"));
				tmp.put("TA003", rs1.getString("TA003"));
				tmp.put("TA004", rs1.getString("TA004"));
				tmp.put("TA006", DCIDate.parseShowDate(rs1.getString("TA006"), "/"));
				tmp.put("TA007", DCIDate.parseShowDate(rs1.getString("TA007"), "/"));
				tmp.put("TA008", rs1.getString("TA008"));
				tmp.put("TA009", rs1.getString("TA009"));
				tmp.put("TA010",
						DCIDate.parseShowDate(rs1.getString("TA010"), "/") + " "
								+ DCIDate.parseShowTime(rs1.getString("TA011")));
				// tmp.put("TA011", rs1.getString("TA011"));
				tmp.put("TA012",
						DCIDate.parseShowDate(rs1.getString("TA012"), "/") + " "
								+ DCIDate.parseShowTime(rs1.getString("TA013")));
				tmp.put("currdate", DCIDate.parseString(rs1.getTimestamp("currdate"), "yyyy/MM/dd HH:mm"));
				datas.add(tmp);

				alldatas.put("headdatas", datas);
				p1 = rs1.getString("TA001");
				p2 = rs1.getString("TA002");
				p3 = rs1.getString("TA003");
				p4 = rs1.getString("TA004");

				wppma = getWPPMA(conn);
				if (wppma != null) {
					ps2 = conn.prepareStatement(cmd.BodySql(p1, p2, p3, p4, wppma.get("MA012"), wppma.get("MA007")));
					rs2 = ps2.executeQuery();

					datas = new ArrayList<Object>();
					while (rs2.next()) {
						if (params.get("F005").toString().equals("1")) {
							if (!rs2.getString("TB015").equals("R")) {
								continue;
							}
						} else if (params.get("F005").toString().equals("2")) {
							if (!rs2.getString("TB016").equals("R")) {
								continue;
							}
						} else if (params.get("F005").toString().equals("3")) {
							if (!rs2.getString("TB017").equals("R")) {
								continue;
							}
						}
						tmp = new HashMap<String, Object>();
						tmp.put("TB001", rs2.getString("TB001"));
						tmp.put("TB002", rs2.getString("TB002"));
						tmp.put("TB003", rs2.getString("TB003"));
						tmp.put("TB004", rs2.getString("TB004"));
						tmp.put("TB005", rs2.getString("TB005"));
						tmp.put("TB006", rs2.getString("TB006"));
						tmp.put("TB007", rs2.getString("TB007"));
						tmp.put("TB008", rs2.getString("TB008"));
						tmp.put("TB009", rs2.getString("TB009"));
						tmp.put("TB010", DCIDate.parseShowDate(rs2.getString("TB010"), "/"));
						tmp.put("TB011", DCIDate.parseShowDate(rs2.getString("TB011"), "/"));
						tmp.put("TB012", rs2.getString("TB012"));
						tmp.put("TB013", rs2.getString("TB013"));
						tmp.put("TB014", DCIDate.parseShowDate(rs2.getString("TB014"), "/"));
						tmp.put("TB015", rs2.getString("TB015"));
						tmp.put("TB016", rs2.getString("TB016"));
						tmp.put("TB017", rs2.getString("TB017"));
						tmp.put("TB021", rs2.getString("TB021"));
						tmp.put("TB022", rs2.getString("TB022"));
						tmp.put("TB023", DCIDate.parseShowDate(rs2.getString("TB023"), "/"));
						tmp.put("TA006", rs2.getString("TA006"));
						tmp.put("TA007", rs2.getString("TA007"));
						tmp.put("TA011", rs2.getString("TA011"));
						tmp.put("TA012", DCIDate.parseShowDate(rs2.getString("TA012"), "/"));
						tmp.put("TA014", DCIDate.parseShowDate(rs2.getString("TA014"), "/"));
						tmp.put("TA015", rs2.getString("TA015"));
						tmp.put("TA017", rs2.getString("TA017"));
						tmp.put("TA033", rs2.getString("TA033"));
						tmp.put("MB002", rs2.getString("MB002"));
						tmp.put("MB003", rs2.getString("MB003"));
						// tmp.put("MA002", rs2.getString("MA002"));
						// tmp.put("TD013",
						// DCIDate.parseShowDate(rs2.getString("TD013"),"/"));
						tmp.put("TE003", rs2.getString("TE003"));
						tmp.put("MOID", rs2.getString("MOID"));
						tmp.put("PORDERID", rs2.getString("PORDERID"));
						tmp.put("ORDERID", rs2.getString("ORDERID"));
						tmp.put("NOTE", rs2.getString("NOTE"));
						datas.add(tmp);
					}
					alldatas.put("griddatas", datas);
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			this.dbctrl.closeConnection(rs1, ps1, null);
			this.dbctrl.closeConnection(rs2, ps2, conn);
		}

		return alldatas;
	}

	private HashMap<String, String> getWPPMA(Connection conn) {
		HashMap<String, String> params = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean reconn = false;

		try {
			if (conn == null || conn.isClosed()) {
				conn = this.dataobj.getConnection(this.connid);
				reconn = true;
			}
			ps = conn.prepareStatement(new sqlPublic().WPPMAsql());
			rs = ps.executeQuery();

			if (rs.next()) {
				params = new HashMap<String, String>();
				params.put("MA001", rs.getString("MA001"));
				params.put("MA002", rs.getString("MA002"));
				params.put("MA003", rs.getString("MA003"));
				params.put("MA004", rs.getString("MA004"));
				params.put("MA005", rs.getString("MA005"));
				params.put("MA006", rs.getString("MA006"));
				params.put("MA007", rs.getString("MA007"));
				params.put("MA008", rs.getString("MA008"));
				params.put("MA009", rs.getString("MA009"));
				params.put("MA010", rs.getString("MA010"));
				params.put("MA011", rs.getString("MA011"));
				params.put("MA012", rs.getString("MA012"));
			}

		} catch (Exception ex) {
			params = null;
			ex.printStackTrace();
		} finally {
			if (reconn) {
				this.dbctrl.closeConnection(rs, ps, conn);
			} else {
				this.dbctrl.closeConnection(rs, ps, null);
			}
		}

		return params;
	}

	public byte[] getExportData(HashMap<String, String> params, String colstr, String lang) {
		byte[] alldatas = null;
		Connection conn = null;
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		ArrayList<HashMap<String, String>> cols = null;
		HashMap<String, String> wppma = null;
		String p1 = null;
		String p2 = null;
		String p3 = null;
		String p4 = null;
		HSSFWorkbook workbook = null;
		HSSFSheet sheet = null;
		HSSFRow row = null;
		HSSFCell cell = null;
		CellStyle styleHead = null;
		DCIExcel excelFuncs = new DCIExcel();
		int cnt = 1;
		HashMap<String, String> dateCol = null;
		HashMap<String, String> lightCol = null;
		String[] dateCols = new String[] { "TB010", "TB011", "TB014", "TB023", "TA012", "TA014" };
		String[] lightCols = new String[] { "TB015", "TB016", "TB017" };
		String value = null;
		Singleton s = Singleton.getInstance();

		try {
			if (!DCIString.isNullOrEmpty(colstr)) {
				cols = new ObjectMapper().readValue(colstr, new TypeReference<ArrayList<HashMap<String, String>>>() {
				});

				dateCol = new HashMap<String, String>();
				for (int i = 0; i < dateCols.length; i++) {
					dateCol.put(dateCols[i], "");
				}

				lightCol = new HashMap<String, String>();
				for (int i = 0; i < lightCols.length; i++) {
					lightCol.put(lightCols[i], "");
				}

				workbook = new HSSFWorkbook();
				sheet = workbook.createSheet(this.getClass().getSimpleName());
				row = sheet.createRow(0);
				styleHead = excelFuncs.buildDefHeaderStyle(workbook);
				excelFuncs.setHeaderCells(sheet, row, cell, styleHead, cols);

				conn = this.dataobj.getConnection(this.connid);
				ps1 = conn.prepareStatement(cmd.HeadSql(params.get("F001"), params.get("F002"), params.get("F003"),
						params.get("F004")));
				rs1 = ps1.executeQuery();
				if (rs1.next()) {
					p1 = rs1.getString("TA001");
					p2 = rs1.getString("TA002");
					p3 = rs1.getString("TA003");
					p4 = rs1.getString("TA004");

					wppma = getWPPMA(conn);
					if (wppma != null) {
						ps2 = conn
								.prepareStatement(cmd.BodySql(p1, p2, p3, p4, wppma.get("MA012"), wppma.get("MA007")));
						rs2 = ps2.executeQuery();

						while (rs2.next()) {
							if (params.get("F005").toString().equals("1")) {
								if (!rs2.getString("TB015").equals("R")) {
									continue;
								}
							} else if (params.get("F005").toString().equals("2")) {
								if (!rs2.getString("TB016").equals("R")) {
									continue;
								}
							} else if (params.get("F005").toString().equals("3")) {
								if (!rs2.getString("TB017").equals("R")) {
									continue;
								}
							}

							row = sheet.createRow(cnt);
							for (int j = 0; j < cols.size(); j++) {
								if (!Boolean.parseBoolean(cols.get(j).get("hidden"))) {
									cell = row.createCell(j);
									if (DCIString.isNullOrEmpty(rs2.getString(cols.get(j).get("dataIndex")))) {
										cell.setCellValue("");
									} else {
										value = rs2.getString(cols.get(j).get("dataIndex"));
										if (dateCol.containsKey(cols.get(j).get("dataIndex"))) {
											cell.setCellValue(DCIDate.parseShowDate(value, "/"));
										} else if (lightCol.containsKey(cols.get(j).get("dataIndex"))) {
											excelFuncs.setCellLightStyle(workbook, cell, value);
										} else if (cols.get(j).get("dataIndex").equals("TA011")) {
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
										} else {
											cell.setCellValue(value);
										}
									}
								}
							}
							cnt++;
						}
					}
				}

				alldatas = excelFuncs.transToDownloadObj(workbook);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			excelFuncs.closeWorkBook(workbook);
			this.dbctrl.closeConnection(rs1, ps1, null);
			this.dbctrl.closeConnection(rs2, ps2, conn);
		}

		return alldatas;
	}
}
