package com.dsc.dci.jweb.funcs.apps.interfaces;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.dci.jweb.DCIEnums.Database.ConnectionType;
import com.dci.jweb.DCIEnums.Database.CrossDBType;
import com.dci.jweb.DataBaseLib.Database.DataDatabaseObject;
import com.dci.jweb.DataBaseLib.Database.DatabaseFuncs;
import com.dci.jweb.DataBaseLib.Database.DatabaseObjects;
import com.dci.jweb.PublicLib.ConfigControl;
import com.dci.jweb.PublicLib.DBControl;
import com.dci.jweb.PublicLib.DCINumber;
import com.dci.jweb.PublicLib.DCIString;
import com.dsc.dci.jweb.apenums.ColumnType;
import com.dsc.dci.jweb.funcs.ekb.kanban.KanBanMethods;
import com.dsc.dci.jweb.pub.APConstants;
import com.dsc.dci.jweb.pub.Singleton;
import com.dsc.dci.sqlcode.funcs.apps.sqlAppFunctions;
import com.dsc.dci.sqlcode.funcs.ekb.sqlKanBan;

public class AppFunctions {
	private sqlAppFunctions cmd;
	private DatabaseObjects dbobj;
	private DBControl dbctrl;
	private Document doc;

	public AppFunctions() {
		this.dbobj = DatabaseObjects.getInstance();
		this.cmd = new sqlAppFunctions();
		this.dbctrl = new DBControl();
	}

	public HashMap<String, String> getXMLDatas(HttpServletRequest request) {
		HashMap<String, String> datas = null;
		SAXReader reader = null;
		// Document document = null;
		Element root = null;
		Element element = null;

		try {
			reader = new SAXReader();
			this.doc = reader.read(request.getReader());
			root = this.doc.getRootElement();
			datas = new HashMap<String, String>();
			for (Iterator<Element> i = root.elementIterator(); i.hasNext();) {
				element = (Element) i.next();
				if (!element.getName().equals("Data")) {
					datas.put(element.getName(), element.getTextTrim());
				}
			}

		} catch (Exception e) {
			datas = new HashMap<String, String>();
			e.printStackTrace();
		}
		return datas;
	}

	public HashMap<String, Object> getXMLObjDatas() {
		ArrayList<HashMap<String, String>> qdatas = null;
		HashMap<String, String> qdata = null;
		HashMap<String, Object> datas = null;
		// SAXReader reader = null;
		// Document document = null;
		Element root = null;
		Element element = null;
		Element eData = null;
		Element eQry = null;

		try {
			// reader = new SAXReader();
			// document = reader.read(request.getReader());
			root = this.doc.getRootElement();
			datas = new HashMap<String, Object>();
			for (Iterator<Element> i = root.elementIterator(); i.hasNext();) {
				element = (Element) i.next();
				if (element.getName().equals("Data")) {
					for (Iterator<Element> j = element.elementIterator(); j.hasNext();) {
						eData = (Element) j.next();
						if (eData.getName().equals("FormQRY")) {
							qdata = new HashMap<String, String>();
							for (Iterator<Element> k = eData.elementIterator(); k.hasNext();) {
								eQry = (Element) k.next();
								qdata.put(eQry.getName(), eQry.getTextTrim());
							}
							if (qdatas == null) {
								qdatas = new ArrayList<HashMap<String, String>>();
							}
							qdatas.add(qdata);
						} else {
							datas.put(eData.getName(), eData.getTextTrim());
						}
					}
					if (qdatas != null) {
						datas.put("FormQRY", qdatas);
					}
				} else {
					datas.put(element.getName(), element.getTextTrim());
				}
			}

		} catch (Exception e) {
			datas = new HashMap<String, Object>();
			e.printStackTrace();
		}
		return datas;
	}

	public String getEmptyError(HashMap<String, String> header, String errCode, String errmsg) {
		Document doc = null;
		Element tmp = null;

		doc = DocumentHelper.createDocument();
		Element root = doc.addElement("STD_IN");
		if (header == null || header.size() == 0) {
			root.addElement("Companyid").addText("");
			root.addElement("LangType").addText("");
			root.addElement("Userid").addText("");
			root.addElement("DoAction").addText("");
		} else {
			if (DCIString.isNullOrEmpty(header.get("Companyid"))) {
				root.addElement("Companyid").addText("");
			} else {
				root.addElement("Companyid").addText(header.get("Companyid"));
			}
			if (DCIString.isNullOrEmpty(header.get("LangType"))) {
				root.addElement("LangType").addText("");
			} else {
				root.addElement("LangType").addText(header.get("LangType"));
			}
			if (DCIString.isNullOrEmpty(header.get("Userid"))) {
				root.addElement("Userid").addText("");
			} else {
				root.addElement("Userid").addText(header.get("Userid"));
			}
			if (DCIString.isNullOrEmpty(header.get("DoAction"))) {
				root.addElement("DoAction").addText("");
			} else {
				root.addElement("DoAction").addText(header.get("DoAction"));
			}
		}
		root.addElement("Data");
		root.addElement("Result").addText(String.valueOf(false));
		tmp = root.addElement("Exception");
		tmp.addElement("Code").addText(errCode);
		tmp.addElement("Mesmsg").addCDATA(errmsg);
		return doc.asXML();
	}

	private Element standRoot(Document doc, HashMap<String, String> header) {
		Element root = doc.addElement("STD_IN");
		if (!header.containsKey("Companyid") || DCIString.isNullOrEmpty(header.get("Companyid"))) {
			root.addElement("Companyid").addText("");
		} else {
			root.addElement("Companyid").addText(header.get("Companyid"));
		}
		if (!header.containsKey("LangType") || DCIString.isNullOrEmpty(header.get("LangType"))) {
			root.addElement("LangType").addText("");
		} else {
			root.addElement("LangType").addText(header.get("LangType"));
		}
		if (!header.containsKey("Userid") || DCIString.isNullOrEmpty(header.get("Userid"))) {
			root.addElement("Userid").addText("");
		} else {
			root.addElement("Userid").addText(header.get("Userid"));
		}
		if (!header.containsKey("DoAction") || DCIString.isNullOrEmpty(header.get("DoAction"))) {
			root.addElement("DoAction").addText("");
		} else {
			root.addElement("DoAction").addText(header.get("DoAction"));
		}
		root.addElement("Result");
		root.addElement("Data");
		root.addElement("Exception");

		return root;
	}

	private Element standRootForObj(Document doc, HashMap<String, Object> header) {
		Element root = doc.addElement("STD_IN");
		if (!header.containsKey("Companyid") || DCIString.isNullOrEmpty(header.get("Companyid").toString())) {
			root.addElement("Companyid").addText("");
		} else {
			root.addElement("Companyid").addText(header.get("Companyid").toString());
		}
		if (!header.containsKey("Companyid") || DCIString.isNullOrEmpty(header.get("LangType").toString())) {
			root.addElement("LangType").addText("");
		} else {
			root.addElement("LangType").addText(header.get("LangType").toString());
		}
		if (!header.containsKey("Companyid") || DCIString.isNullOrEmpty(header.get("Userid").toString())) {
			root.addElement("Userid").addText("");
		} else {
			root.addElement("Userid").addText(header.get("Userid").toString());
		}
		if (!header.containsKey("Companyid") || DCIString.isNullOrEmpty(header.get("DoAction").toString())) {
			root.addElement("DoAction").addText("");
		} else {
			root.addElement("DoAction").addText(header.get("DoAction").toString());
		}
		root.addElement("Result");
		root.addElement("Data");
		root.addElement("Exception");

		return root;
	}

	private Element setException(Element root, String errCode, String msg) {
		Element tmp = null;
		tmp = root.element("Exception");
		tmp.addElement("Code").addText(errCode);
		if (DCIString.isNullOrEmpty(msg)) {
			tmp.addElement("Mesmsg").addText("");
		} else {
			tmp.addElement("Mesmsg").addCDATA(msg);
		}
		root.element("Data").clearContent();
		root.element("Result").addText(String.valueOf(false));
		return root;
	}

	public String getConnList(HashMap<String, String> header) {
		String result = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Document doc = null;
		Element root = null;
		Element tmp = null;
		Singleton s = Singleton.getInstance();
		String lang = null;

		try {
			lang = header.get("LangType");
			if (DCIString.isNullOrEmpty(lang)) {
				lang = s.getDeflang();
			}
			doc = DocumentHelper.createDocument();
			root = standRoot(doc, header);

			if (header.containsKey("Userid") && !DCIString.isNullOrEmpty(header.get("Userid"))) {
				conn = this.dbobj.getConnection(ConnectionType.SYS);
				ps = conn.prepareStatement(this.cmd.getConnList());
				ps.setString(1, header.get("Userid"));
				ps.setString(2, header.get("Userid"));
				rs = ps.executeQuery();

				while (rs.next()) {
					tmp = root.element("Data").addElement("RecordList");
					tmp.addElement("Companyid").addText(rs.getString("conn_id"));
					tmp.addElement("CompanyName").addText(rs.getString("conn_name"));
				}
				root.element("Result").addText(String.valueOf(true));
			} else {
				root = setException(root, "E", s.getLanguage(lang, "user_not_exist"));
			}
		} catch (Exception ex) {
			root = setException(root, "E", "get conn list fail");
			ex.printStackTrace();
		} finally {
			this.dbctrl.closeConnection(rs, ps, conn);
			result = doc.asXML();
		}
		return result;
	}

	public String getKanbanList(HashMap<String, String> header) {
		String result = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Document doc = null;
		Element root = null;
		Element tmp = null;
		Singleton s = Singleton.getInstance();
		String lang = null;

		try {
			lang = header.get("LangType");
			if (DCIString.isNullOrEmpty(lang)) {
				lang = s.getDeflang();
			}
			doc = DocumentHelper.createDocument();
			root = standRoot(doc, header);

			if (header.containsKey("Userid") && !DCIString.isNullOrEmpty(header.get("Userid"))) {
				if (header.containsKey("Companyid") && !DCIString.isNullOrEmpty(header.get("Companyid"))) {
					conn = this.dbobj.getConnection(ConnectionType.SYS);
					ps = conn.prepareStatement(this.cmd.getKanbanList());
					ps.setString(1, header.get("Userid"));
					ps.setString(2, header.get("Companyid"));
					rs = ps.executeQuery();

					while (rs.next()) {
						tmp = root.element("Data").addElement("RecordList");
						tmp.addElement("Reportid").addText(rs.getString("func_id"));
						tmp.addElement("ReportName").addText(
								Singleton.getInstance().getLanguage(header.get("LangType"), rs.getString("func_name")));
					}
					root.element("Result").addText(String.valueOf(true));
				} else {
					root = setException(root, "E", s.getLanguage(lang, "conn_not_exist"));
				}
			} else {
				root = setException(root, "E", s.getLanguage(lang, "user_not_exist"));
			}
		} catch (Exception ex) {
			root = setException(root, "E", "get func list fail");
			ex.printStackTrace();
		} finally {
			this.dbctrl.closeConnection(rs, ps, conn);
			result = doc.asXML();
		}
		return result;
	}

	public String getKanBanData(HashMap<String, Object> header, HttpServletRequest request, boolean is_detail) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn2 = null;
		PreparedStatement ps2 = null;
		ResultSet rs2 = null;
		PreparedStatement ps3 = null;
		ResultSet rs3 = null;
		Connection conn5 = null;
		PreparedStatement ps5 = null;
		ResultSet rs5 = null;
		// HashMap<String, Object> datas = null;
		// ArrayList<HashMap<String, Object>> items = null;
		// HashMap<String, Object> tmp = null;
		HashMap<String, String[]> colConfig = null;
		HashMap<String, String> iconmap = null;
		HashMap<String, String> calcInfo = null;
		HashMap<String, String> appcols = null;
		int cnt = 0;
		int page = 0;
		int pagesize = 0;
		int totalRow = 0;
		String sqlcmd = null;
		ResultSetMetaData rsmd = null;
		String colName = null;
		String dataValue = null;
		boolean isSqlFilter = false;
		int rstp = -1;
		Document doc = null;
		Element root = null;
		Element etmp = null;
		String ecol = null;
		sqlKanBan kbcmd = new sqlKanBan();
		KanBanMethods kbm = null;
		Singleton s = Singleton.getInstance();
		String lang = null;
		String sql_id = null;
		String conn_id = null;
		String uid = null;
		String filter = null;
		String result = null;
		String tagName = null;
		int[] pageData = null;

		try {
			doc = DocumentHelper.createDocument();
			root = standRootForObj(doc, header);
			lang = header.get("LangType").toString();
			if (DCIString.isNullOrEmpty(lang)) {
				lang = s.getDeflang();
			}
			sql_id = header.get("Reportid").toString();
			conn_id = header.get("Companyid").toString();
			uid = header.get("Userid").toString();

			if (DCIString.isNullOrEmpty(sql_id) || DCIString.isNullOrEmpty(conn_id) || DCIString.isNullOrEmpty(uid)) {
				throw new Exception("head error");
			}
			kbm = new KanBanMethods(kbcmd, this.dbobj, this.dbctrl, lang);
			pageData = getPageData(header);
			filter = getFilter(header);
			conn = this.dbobj.getConnection(ConnectionType.SYS);
			ps = conn.prepareStatement(kbcmd.getSqlCommand(), ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			ps.setString(1, sql_id);
			ps.setString(2, uid);
			ps.setString(3, sql_id);
			ps.setString(4, sql_id);
			ps.setString(5, uid);
			ps.setString(6, sql_id);
			rs = ps.executeQuery();

			if (rs.next()) {
				ps3 = conn.prepareStatement(kbcmd.getColConfig(), ResultSet.TYPE_FORWARD_ONLY,
						ResultSet.CONCUR_READ_ONLY);
				ps3.setString(1, sql_id);
				ps3.setString(2, sql_id);
				rs3 = ps3.executeQuery();
				if (colConfig == null) {
					colConfig = new HashMap<String, String[]>();
				}
				if (calcInfo == null) {
					calcInfo = new HashMap<String, String>();
				}

				while (rs3.next()) {
					if (rs3.getString("tp").equals("1")) {
						colConfig.put(rs3.getString("col_id"),
								new String[] { rs3.getString("col_type"), rs3.getString("config_value") });
					} else if (rs3.getString("tp").equals("2")) {
						calcInfo.put(rs3.getString("col_id"), rs3.getString("config_value"));
					}
				}

				this.dbctrl.closeConnection(rs3, ps3, null);
				if (is_detail) {
					ps3 = conn.prepareStatement(this.cmd.getAppDtlCols());
				} else {
					ps3 = conn.prepareStatement(this.cmd.getAppCols());
				}
				ps3.setString(1, sql_id);
				ps3.setString(2, lang);
				rs3 = ps3.executeQuery();
				appcols = new HashMap<String, String>();

				while (rs3.next()) {
					appcols.put(rs3.getString("col_id"), rs3.getString("lang_value"));
					if (DCIString.isNullOrEmpty(ecol)) {
						ecol = rs3.getString("col_id") + "=" + rs3.getString("lang_value");
					} else {
						ecol += "|" + rs3.getString("col_id") + "=" + rs3.getString("lang_value");
					}
				}
				if (!is_detail) {
					root.element("Data").addElement("MAPPLIST").addCDATA(ecol);
				}

				page = pageData[0];
				pagesize = pageData[1];

				conn2 = DataDatabaseObject.getInstance().getConnection(conn_id);
				sqlcmd = rs.getString("sql_context");

				if (!DCIString.isNullOrEmpty(filter)) {
					if (rs.getString("is_cross").equals("1")) {
						if (kbm.sqlCheck(sqlcmd + " \r\n" + filter, conn2)) {
							sqlcmd += " \r\n" + filter;
							isSqlFilter = true;
						}
					} else {
						sqlcmd += " \r\n" + filter;
						isSqlFilter = true;
					}
				}

				if (!DCIString.isNullOrEmpty(rs.getString("group_by"))) {
					sqlcmd += "\r\n group by " + rs.getString("group_by");
				}

				if (rs.getString("is_cross").equals("1")) {
					rstp = ResultSet.TYPE_FORWARD_ONLY;
				} else {
					rstp = ResultSet.TYPE_SCROLL_INSENSITIVE;
				}

				ps2 = conn2.prepareStatement(sqlcmd, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				rs2 = ps2.executeQuery();

				if (rs.getString("is_cross").equals("1")) {
					conn5 = DataDatabaseObject.getInstance().getConnection(rs.getString("cross_conn_id"));
					sqlcmd = rs.getString("cross_sql_context");

					if (!DCIString.isNullOrEmpty(filter)) {
						if (kbm.sqlCheck(sqlcmd + " \r\n" + filter, conn5)) {
							sqlcmd += " \r\n" + filter;
							isSqlFilter = true;
						}
					}

					if (!DCIString.isNullOrEmpty(rs.getString("cross_group_by"))) {
						sqlcmd += "\r\n group by " + rs.getString("cross_group_by");
					}

					ps5 = conn5.prepareStatement(sqlcmd, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
					rs5 = ps5.executeQuery();

					ArrayList<Object> crossData = null;
					if (rs.getString("cross_type").equals(CrossDBType.join.toString())) {
						ArrayList<String[]> joinKeys = null;
						joinKeys = new ArrayList<String[]>(0);
						joinKeys.add(new String[] { rs.getString("join_key_set1"), rs.getString("join_key_set2") });
						crossData = new DatabaseFuncs().JoinResult(rs2, rs5, joinKeys);
					} else {
						crossData = new DatabaseFuncs().UnionAllResult(rs2, rs5);
					}

					// items = new ArrayList<HashMap<String, Object>>();
					if (crossData != null && crossData.size() > 0) {
						ConfigControl cc = new ConfigControl(false, s.getTestAreaConfigPath());
						HashMap<String, Object> firstRow = null;
						HashMap<String, Object> row = null;
						iconmap = kbm.getIconIDMap(conn);
						totalRow = crossData.size();

						if ((page - 1) * pagesize <= totalRow) {
							cnt = 1;
							firstRow = (HashMap<String, Object>) crossData.get(0);

							for (int p = (page - 1) * pagesize; p < totalRow; p++) {
								if (!is_detail) {
									etmp = root.element("Data").addElement("RecordList");
								}
								row = (HashMap<String, Object>) crossData.get(p);
								// tmp = new HashMap<String, Object>();

								for (String key : firstRow.keySet()) {
									colName = key;
									if (!appcols.containsKey(colName)) {
										continue;
									}
									if (is_detail) {
										etmp = root.element("Data").addElement("RecordList");
										etmp.addElement("id").addCDATA(appcols.get(colName));
										tagName = "value";
									} else {
										tagName = colName;
									}

									if (calcInfo.containsKey(colName)) {
										dataValue = DCINumber.calcNumber(row, calcInfo.get(colName));
									} else {
										if (row.containsKey(colName) && row.get(colName) != null
												&& !DCIString.isNullOrEmpty(row.get(colName).toString())) {
											dataValue = row.get(colName).toString().trim();
										} else {
											dataValue = "";
										}
									}

									if (colConfig != null && colConfig.containsKey(colName)) {
										if (colConfig.get(colName)[0].equals(ColumnType.MAPPING.toString())) {
											etmp.addElement(tagName).addText(
													kbm.mappingAnalysis(colConfig.get(colName)[1], dataValue));
										} else if (colConfig.get(colName)[0].equals(ColumnType.NUM.toString())) {
											etmp.addElement(tagName).addText(
													DCIString.numFormat(dataValue, colConfig.get(colName)[1]));
										} else if (colConfig.get(colName)[0].equals(ColumnType.LIGHT.toString())) {
											if (iconmap != null) {
												if (!DCIString.isNullOrEmpty(colConfig.get(colName)[1])) {
													dataValue = DCIString.transRangValue(dataValue,
															colConfig.get(colName)[1]);
												}
												if (iconmap.containsKey(dataValue)) {
													etmp.addElement(tagName).addText(
															getHostUrl(request, true) + iconmap.get(dataValue));
												}
											}
										} else if (colConfig.get(colName)[0].equals(ColumnType.PROGRESS.toString())) {
											etmp.addElement(tagName).addText(
													kbm.percentFormat(dataValue, colConfig.get(colName)[1]));
										} else if (colConfig.get(colName)[0].equals(ColumnType.MONEY.toString())) {
											if (DCIString.isNullOrEmpty(dataValue)) {
												etmp.addElement(tagName).addText("");
											} else {
												etmp.addElement(tagName)
														.addText(
																"$"
																		+ DCIString.numFormat(dataValue,
																				colConfig.get(colName)[1]));
											}
										} else if (colConfig.get(colName)[0].equals(ColumnType.CHECKBOX.toString())) {
											if (kbm.CheckBoxFormat(dataValue)) {
												etmp.addElement(tagName).addText(s.getLanguage(lang, "yes"));
											} else {
												etmp.addElement(tagName).addText(s.getLanguage(lang, "no"));
											}
										} else if (colConfig.get(colName)[0].equals(ColumnType.DATE.toString())) {
											etmp.addElement(tagName).addText(
													DCIString.dateFormat(dataValue, colConfig.get(colName)[1]));
										} else if (colConfig.get(colName)[0].equals(ColumnType.IMAGEMAPPING.toString())) {
											if (DCIString.isNullOrEmpty(dataValue)) {
												DCIString.Base64Encode("column data is null");
											} else {
												etmp.addElement(tagName).addText(
														getHostUrl(request, false)
																+ DCIString.Base64Encode(cc.getConfigXMLPath()
																		+ File.separator
																		+ APConstants.getUploadicondir()
																		+ File.separator + dataValue + "."
																		+ colConfig.get(colName)[1]));
											}
										} else {
											etmp.addElement(tagName).addText(dataValue);
										}
									} else {
										etmp.addElement(tagName).addText(dataValue);
									}
								}
								// items.add(tmp);
								cnt++;
								if (cnt > pagesize) {
									break;
								}
							}
						}
					}

				} else {

					// items = new ArrayList<HashMap<String, Object>>();
					ConfigControl cc = new ConfigControl(false, s.getTestAreaConfigPath());
					if (rs2.next()) {
						if (rsmd == null) {
							rsmd = rs2.getMetaData();
						}
						iconmap = kbm.getIconIDMap(conn);
						rs2.last();
						totalRow = rs2.getRow();
						rs2.beforeFirst();

						if ((page - 1) * pagesize <= totalRow) {
							if ((page - 1) * pagesize != 0) {
								rs2.absolute((page - 1) * pagesize);
							}
							cnt = 1;
							HashMap<String, String> oridatacols = new HashMap<String, String>();

							for (int i = 1; i <= rsmd.getColumnCount(); i++) {
								oridatacols.put(rsmd.getColumnName(i), "");
							}

							while (rs2.next()) {
								if (!is_detail) {
									etmp = root.element("Data").addElement("RecordList");
								}
								// tmp = new HashMap<String, Object>();
								for (int i = 1; i <= rsmd.getColumnCount(); i++) {
									colName = rsmd.getColumnName(i);
									if (!appcols.containsKey(colName)) {
										continue;
									}
									if (is_detail) {
										etmp = root.element("Data").addElement("RecordList");
										etmp.addElement("id").addCDATA(appcols.get(colName));
										tagName = "value";
									} else {
										tagName = colName;
									}
									if (calcInfo.containsKey(colName)) {
										dataValue = DCINumber.calcNumber(rs2, calcInfo.get(colName));
									} else {
										if (oridatacols.containsKey(colName)
												&& !DCIString.isNullOrEmpty(rs2.getString(colName))) {
											dataValue = rs2.getString(colName).trim();
										} else {
											dataValue = "";
										}
									}

									if (colConfig != null && colConfig.containsKey(colName)) {
										if (colConfig.get(colName)[0].equals(ColumnType.MAPPING.toString())) {
											etmp.addElement(tagName).addText(
													kbm.mappingAnalysis(colConfig.get(colName)[1], dataValue));
										} else if (colConfig.get(colName)[0].equals(ColumnType.NUM.toString())) {
											etmp.addElement(tagName).addText(
													DCIString.numFormat(dataValue, colConfig.get(colName)[1]));
										} else if (colConfig.get(colName)[0].equals(ColumnType.LIGHT.toString())) {
											if (iconmap != null) {
												if (!DCIString.isNullOrEmpty(colConfig.get(colName)[1])) {
													dataValue = DCIString.transRangValue(dataValue,
															colConfig.get(colName)[1]);
												}
												if (iconmap.containsKey(dataValue)) {
													etmp.addElement(tagName).addText(
															getHostUrl(request, true) + iconmap.get(dataValue));
												}
											}
										} else if (colConfig.get(colName)[0].equals(ColumnType.PROGRESS.toString())) {
											etmp.addElement(tagName).addText(
													kbm.percentFormat(dataValue, colConfig.get(colName)[1]));
										} else if (colConfig.get(colName)[0].equals(ColumnType.MONEY.toString())) {
											if (DCIString.isNullOrEmpty(dataValue)) {
												etmp.addElement(tagName).addText("");
											} else {
												etmp.addElement(tagName)
														.addText(
																"$"
																		+ DCIString.numFormat(dataValue,
																				colConfig.get(colName)[1]));
											}
										} else if (colConfig.get(colName)[0].equals(ColumnType.CHECKBOX.toString())) {
											if (kbm.CheckBoxFormat(dataValue)) {
												etmp.addElement(tagName).addText(s.getLanguage(lang, "yes"));
											} else {
												etmp.addElement(tagName).addText(s.getLanguage(lang, "no"));
											}
										} else if (colConfig.get(colName)[0].equals(ColumnType.DATE.toString())) {
											etmp.addElement(tagName).addText(
													DCIString.dateFormat(dataValue, colConfig.get(colName)[1]));
										} else if (colConfig.get(colName)[0].equals(ColumnType.IMAGEMAPPING.toString())) {
											if (DCIString.isNullOrEmpty(dataValue)) {
												DCIString.Base64Encode("column data is null");
											} else {
												etmp.addElement(tagName).addText(
														getHostUrl(request, false)
																+ DCIString.Base64Encode(cc.getConfigXMLPath()
																		+ File.separator
																		+ APConstants.getUploadicondir()
																		+ File.separator + dataValue + "."
																		+ colConfig.get(colName)[1]));
											}
										} else {
											etmp.addElement(tagName).addText(dataValue);
										}
									} else {
										etmp.addElement(tagName).addText(dataValue);
									}
								}
								if (is_detail) {
									break;
								} else {
									cnt++;
									if (cnt > pagesize) {
										break;
									}
								}
							}
						}
					}
				}
				// datas = new HashMap<String, Object>();
				// datas.put("items", items);
				// datas.put("total", totalRow);
				root.element("Result").addText(String.valueOf(true));
			} else {
				// kanban not found
				root = setException(root, "E", s.getLanguage(lang, "kanban_not_exist"));
			}
		} catch (Exception ex) {
			// totalRow = 0;
			// datas = new HashMap<String, Object>();
			if (is_detail) {
				root = setException(root, "E", "get kanban detail fail");
			} else {
				root = setException(root, "E", "get kanban head fail");
			}
			ex.printStackTrace();
		} finally {
			this.dbctrl.closeConnection(rs5, ps5, conn5);
			this.dbctrl.closeConnection(rs2, ps2, conn2);
			this.dbctrl.closeConnection(rs3, ps3, null);
			this.dbctrl.closeConnection(rs, ps, conn);
			//System.out.println(doc.asXML());
			result = doc.asXML();
		}
		return result;
	}

	private String getHostUrl(HttpServletRequest request, boolean useid) {
		String result = null;
		if (request == null) {
			result = "";
		} else {
			result = request.getRequestURL().toString().replace("AppReceiver.dsc", "ImageLoader.dsc?");
			if (useid) {
				result += "iconid=";
			} else {
				result += "imgpath=";
			}
		}
		return result;
	}

	private String getFilter(HashMap<String, Object> header) {
		String result = null;

		if (header == null || header.size() == 0) {
			result = "";
		} else {
			result = "";
			if (header.containsKey("FormQRY")) {
				ArrayList<HashMap<String, String>> qdatas = (ArrayList<HashMap<String, String>>) header.get("FormQRY");
				HashMap<String, String> tmp = null;
				for (int i = 0; i < qdatas.size(); i++) {
					tmp = qdatas.get(i);
					if (tmp.get("Qryid") != null && !DCIString.isNullOrEmpty(tmp.get("Qryid"))) {
						result += " and " + tmp.get("Qryid") + " = '" + tmp.get("QryValue") + "'";
					}
				}

			}
		}

		return result;
	}

	private int[] getPageData(HashMap<String, Object> header) {
		int[] result = null;
		int srow = 0;
		int erow = 0;

		try {
			if (DCIString.isNullOrEmpty(header.get("ShowBNo").toString())
					|| !DCIString.isInteger(header.get("ShowBNo").toString())) {
				srow = 1;
			} else {
				srow = Integer.parseInt(header.get("ShowBNo").toString());
			}

			if (DCIString.isNullOrEmpty(header.get("ShowENo").toString())
					|| !DCIString.isInteger(header.get("ShowENo").toString())) {
				erow = APConstants.getDefaultpagesize();
			} else {
				erow = Integer.parseInt(header.get("ShowENo").toString());
			}

			result = new int[2];

			result[1] = erow - srow + 1;

			if (srow == 0) {
				result[0] = 1;
			} else {
				result[0] = ((srow - 1) / erow) + 1;
			}

		} catch (Exception ex) {
			result = new int[] { 1, APConstants.getDefaultpagesize() };
		}

		return result;
	}
	
}
