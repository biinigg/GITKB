package com.dci.jweb.DataBaseLib.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;

import com.dci.jweb.DCIEnums.Database.CrossDBType;

public class DatabaseFuncs {

	/*
	 * public ArrayList<Object> JoinResult2(ResultSet rs1, ResultSet rs2,
	 * ArrayList<String[]> cols) { ArrayList<Object> datas = null;
	 * HashMap<String, Object> tmp = null; ResultSetMetaData mrs1 = null;
	 * ResultSetMetaData mrs2 = null; boolean match = true; if (rs1 != null &&
	 * rs2 != null && cols != null) { try { mrs1 = rs1.getMetaData(); mrs2 =
	 * rs2.getMetaData(); rs1.beforeFirst(); rs2.beforeFirst();
	 * 
	 * while (rs1.next()) { rs2.beforeFirst(); while (rs2.next()) { for (int i =
	 * 0; i < cols.size(); i++) { if
	 * (!rs1.getString(cols.get(i)[0]).equals(rs2.getString(cols.get(i)[1]))) {
	 * match = false; break; } } if (match) { if (datas == null) { datas = new
	 * ArrayList<Object>(); } tmp = new HashMap<String, Object>(); for (int i =
	 * 1; i <= mrs1.getColumnCount(); i++) { tmp.put(mrs1.getColumnName(i),
	 * rs1.getString(i)); } for (int i = 1; i <= mrs2.getColumnCount(); i++) {
	 * tmp.put(mrs2.getColumnName(i), rs2.getString(i)); } datas.add(tmp); }
	 * match = true; } rs2.beforeFirst(); }
	 * 
	 * } catch (Exception ex) { datas = null; ex.printStackTrace(); } } return
	 * datas; }
	 */

	public ArrayList<HashMap<String, Object>> ComplexJoinResult(HashMap<String, Object> crossInfos,
			ResultSet mainRS) {
		ArrayList<HashMap<String, Object>> datas = null;
		HashMap<String, ArrayList<HashMap<String, Object>>> source = null;
		ArrayList<HashMap<String, Object>> tmp = null;
		ArrayList<HashMap<String, Object>> rows = null;
		ArrayList<HashMap<String, Object>> tmprows = null;
		HashMap<String, Object> row = null;
		HashMap<String, HashMap<String, String>> colnames = null;
		HashMap<String, ArrayList<String[]>> condigp = null;
		ResultSetMetaData rsmd = null;
		ResultSetMetaData subrsmd = null;
		ArrayList<String[]> subcondi = null;
		ArrayList<Integer> idxs = null;
		HashMap<String, HashMap<String, HashMap<String, ArrayList<Integer>>>> idxMap = null;
		HashMap<String, HashMap<String, ArrayList<Integer>>> tbIdxMap = null;
		HashMap<String, ArrayList<Integer>> tmpIdxMap = null;
		HashMap<String, String> joinedtb = null;
		String rsid = null;
		String lastkey = null;
		String vkey = null;
		String vkey2 = null;
		ResultSet[] rss = null;
		ArrayList<String[]> joincondi = null;
		HashMap<String, CrossDBType> joinTypeMap = null;

		if (crossInfos != null && crossInfos.size() > 0) {
			HashMap<String, Object> ctmp = null;

			int ccnt = 0;
			rss = new ResultSet[crossInfos.size() + 1];
			rss[0] = mainRS;
			ccnt = 1;
			joincondi = new ArrayList<String[]>();
			joinTypeMap = new HashMap<String, CrossDBType>();

			for (String k : crossInfos.keySet()) {
				ctmp = (HashMap<String, Object>) crossInfos.get(k);
				joinTypeMap.put(new String(new byte[] { Byte.parseByte(String.valueOf(65 + ccnt)) }),
						CrossDBType.valueOf(ctmp.get("type").toString()));
				rss[ccnt] = (ResultSet) ctmp.get("rs");
				joincondi.addAll((ArrayList<String[]>) ctmp.get("keys"));
				ccnt++;
			}

			datas = new ArrayList<HashMap<String, Object>>();
			if (rss != null && joincondi != null) {
				try {
					colnames = colnameRepeat(rss);
					source = new HashMap<String, ArrayList<HashMap<String, Object>>>();
					idxMap = new LinkedHashMap<String, HashMap<String, HashMap<String, ArrayList<Integer>>>>();
					condigp = condiAnalysis(joincondi, colnames);
					for (int i = 1; i < rss.length; i++) {
						rsid = new String(new byte[] { Byte.parseByte(String.valueOf(65 + i)) });
						tmp = new ArrayList<HashMap<String, Object>>();
						rsmd = rss[i].getMetaData();
						while (rss[i].next()) {
							lastkey = null;
							for (String ckey : condigp.keySet()) {
								if (ckey.indexOf(rsid) != -1) {
									if (lastkey == null || !lastkey.equals(ckey)) {
										subcondi = condigp.get(ckey);
									}
									vkey = ckey + "#*$$*#";
									for (int j = 0; j < subcondi.size(); j++) {
										if (rss[i].getString(subcondi.get(j)[ckey.indexOf(rsid)]) == null) {
											vkey += "-$N$-$*$";
										} else {
											vkey += rss[i].getString(subcondi.get(j)[ckey.indexOf(rsid)])
													.trim() + "$*$";
										}
									}
									if (idxMap.containsKey(ckey)) {
										tbIdxMap = idxMap.get(ckey);
									} else {
										tbIdxMap = new HashMap<String, HashMap<String, ArrayList<Integer>>>();
									}

									if (tbIdxMap.containsKey(rsid)) {
										tmpIdxMap = tbIdxMap.get(rsid);
									} else {
										tmpIdxMap = new HashMap<String, ArrayList<Integer>>();
									}

									if (tmpIdxMap.containsKey(vkey)) {
										idxs = tmpIdxMap.get(vkey);
									} else {
										idxs = new ArrayList<Integer>();
									}
									idxs.add(rss[i].getRow() - 1);
									tmpIdxMap.put(vkey, idxs);
									tbIdxMap.put(rsid, tmpIdxMap);
									idxMap.put(ckey, tbIdxMap);
									lastkey = ckey;
								}
							}
							row = new HashMap<String, Object>();
							for (int j = 1; j <= rsmd.getColumnCount(); j++) {
								row.put(rsid + "$" + rsmd.getColumnName(j) + "^" + j, rss[i].getString(j));
							}
							tmp.add(row);
						}

						source.put(rsid, tmp);
					}

					rsmd = rss[0].getMetaData();
					while (rss[0].next()) {
						row = null;
						joinedtb = new HashMap<String, String>();
						rows = new ArrayList<HashMap<String, Object>>();

						for (String ckey : condigp.keySet()) {
							if (ckey.indexOf("A") != -1) {
								subcondi = condigp.get(ckey);
								vkey = ckey + "#*$$*#";
								if (rows.size() == 0) {
									for (int j = 0; j < subcondi.size(); j++) {
										if (rss[0].getString(subcondi.get(j)[0]) == null) {
											vkey += "-$N$-$*$";
										} else {
											vkey += rss[0].getString(subcondi.get(j)[0]).trim() + "$*$";
										}
									}

									if (checkJoinCondi(ckey, vkey, ckey.substring(1), idxMap)) {
										idxs = idxMap.get(ckey).get(ckey.substring(1)).get(vkey);

										for (int j = 0; j < idxs.size(); j++) {
											row = new HashMap<String, Object>();
											joinedtb.put("A", "A");
											for (int i = 1; i <= rsmd.getColumnCount(); i++) {
												row.put(colnames.get("A$" + rsmd.getColumnName(i) + "^" + i)
														.get("name"), rss[0].getString(i));
											}

											for (int i = 1; i < rss.length; i++) {
												rsid = new String(new byte[] { Byte.parseByte(String
														.valueOf(65 + i)) });
												if (rsid.equals(ckey.substring(1))) {
													tmp = source.get(rsid);
													joinedtb.put(rsid, rsid);
													for (String colname : tmp.get(idxs.get(j)).keySet()) {
														row.put(colnames.get(colname).get("name"),
																tmp.get(idxs.get(j)).get(colname));
													}
													break;
												}
											}
											rows.add(row);
										}
										continue;
									} else {
										rsid = ckey.substring(1);
										if (joinTypeMap.get(rsid) == CrossDBType.left_join) {
											row = new HashMap<String, Object>();
											joinedtb.put("A", "A");
											for (int i = 1; i <= rsmd.getColumnCount(); i++) {
												row.put(colnames.get("A$" + rsmd.getColumnName(i) + "^" + i)
														.get("name"), rss[0].getString(i));
											}

											subrsmd = rss[rsid.getBytes()[0] - 65].getMetaData();
											joinedtb.put(rsid, rsid);
											for (int k = 1; k <= subrsmd.getColumnCount(); k++) {
												row.put(colnames.get(
														rsid + "$" + subrsmd.getColumnName(k) + "^" + k).get(
														"name"), "");
											}
											rows.add(row);
										}
									}

								} else {
									rsid = ckey.substring(1);
									tmprows = new ArrayList<HashMap<String, Object>>();
									for (int i = 0; i < rows.size(); i++) {
										row = copyRow(rows.get(i));
										vkey = ckey + "#*$$*#";
										for (int j = 0; j < subcondi.size(); j++) {
											if (row.get(subcondi.get(j)[2]) == null) {
												vkey += "-$N$-$*$";
											} else {
												vkey += row.get(subcondi.get(j)[2]).toString().trim() + "$*$";
											}
										}

										tmp = source.get(rsid);
										joinedtb.put(rsid, rsid);
										if (checkJoinCondi(ckey, vkey, rsid, idxMap)) {
											idxs = idxMap.get(ckey).get(rsid).get(vkey);

											for (int j = 0; j < idxs.size(); j++) {
												row = copyRow(rows.get(i));
												for (String colname : tmp.get(idxs.get(j)).keySet()) {
													row.put(colnames.get(colname).get("name"),
															tmp.get(idxs.get(j)).get(colname));
												}
												tmprows.add(row);
											}
										} else {
											if (joinTypeMap.get(rsid) == CrossDBType.left_join) {
												row = copyRow(rows.get(i));
												subrsmd = rss[rsid.getBytes()[0] - 65].getMetaData();
												joinedtb.put(rsid, rsid);
												for (int k = 1; k <= subrsmd.getColumnCount(); k++) {
													row.put(colnames.get(
															rsid + "$" + subrsmd.getColumnName(k) + "^" + k)
															.get("name"), "");
												}
												tmprows.add(row);
											}
										}
									}

									rows.clear();
									rows.addAll(tmprows);
								}
							} else {
								if (rows.size() > 0) {
									subcondi = condigp.get(ckey);
									if (alljoined(ckey, joinedtb)) {
										for (int i = rows.size() - 1; i >= 0; i--) {
											row = rows.get(i);
											vkey = ckey + "#*$$*#";
											for (int j = 0; j < subcondi.size(); j++) {
												if (row.get(subcondi.get(j)[2]) == null) {
													vkey += "-$N$-$*$";
												} else {
													vkey += row.get(subcondi.get(j)[2]).toString().trim()
															+ "$*$";
												}
											}

											vkey2 = ckey + "#*$$*#";
											for (int j = 0; j < subcondi.size(); j++) {
												if (row.get(subcondi.get(j)[3]) == null) {
													vkey2 += "-$N$-$*$";
												} else {
													vkey2 += row.get(subcondi.get(j)[3]).toString().trim()
															+ "$*$";
												}
											}

											if (!vkey.equals(vkey2)) {
												rows.remove(i);
												if (joinTypeMap.get(ckey.substring(1)) == CrossDBType.left_join) {
													subrsmd = rss[ckey.substring(1).getBytes()[0] - 65]
															.getMetaData();
													for (int k = 1; k <= subrsmd.getColumnCount(); k++) {
														row.put(colnames.get(
																rsid + "$" + subrsmd.getColumnName(k) + "^"
																		+ k).get("name"), "");
													}

													rows.add(i, row);
												}
												// else {
												// rows.remove(i);
												// }
											}
										}
									} else {
										tmprows = new ArrayList<HashMap<String, Object>>();
										for (int i = 0; i < rows.size(); i++) {
											row = copyRow(rows.get(i));
											vkey = ckey + "#*$$*#";
											if (joinedtb.containsKey(ckey.substring(0, 1))) {
												rsid = ckey.substring(1);
												for (int j = 0; j < subcondi.size(); j++) {
													if (row.get(subcondi.get(j)[2]) == null) {
														vkey += "-$N$-$*$";
													} else {
														vkey += row.get(subcondi.get(j)[2]).toString().trim()
																+ "$*$";
													}
												}
											} else {
												rsid = ckey.substring(0, 1);
												for (int j = 0; j < subcondi.size(); j++) {
													if (row.get(subcondi.get(j)[3]) == null) {
														vkey += "-$N$-$*$";
													} else {
														vkey += row.get(subcondi.get(j)[3]).toString().trim()
																+ "$*$";
													}
												}
											}

											if (checkJoinCondi(ckey, vkey, rsid, idxMap)) {
												idxs = idxMap.get(ckey).get(rsid).get(vkey);
												tmp = source.get(rsid);
												joinedtb.put(rsid, rsid);

												for (int j = 0; j < idxs.size(); j++) {
													row = copyRow(rows.get(i));
													for (String colname : tmp.get(idxs.get(j)).keySet()) {
														row.put(colnames.get(colname).get("name"),
																tmp.get(idxs.get(j)).get(colname));
													}
													tmprows.add(row);
												}
											} else {
												if (joinTypeMap.get(rsid) == CrossDBType.left_join) {
													subrsmd = rss[rsid.getBytes()[0] - 65].getMetaData();
													joinedtb.put(rsid, rsid);
													row = copyRow(rows.get(i));
													for (int k = 1; k <= subrsmd.getColumnCount(); k++) {
														row.put(colnames.get(
																rsid + "$" + subrsmd.getColumnName(k) + "^"
																		+ k).get("name"), "");
													}
													tmprows.add(row);
												}
											}
										}

										rows.clear();
										rows.addAll(tmprows);
									}

								} else {
									rows.clear();
									break;
								}
							}
						}

						if (rows != null && rows.size() > 0) {
							datas.addAll(rows);
						}
					}

				} catch (Exception ex) {
					ex.printStackTrace();
				} finally {
					if (source != null) {
						source.clear();
						source = null;
					}
					if (tmp != null) {
						tmp.clear();
						tmp = null;
					}
					if (rows != null) {
						rows.clear();
						rows = null;
					}
					if (tmprows != null) {
						tmprows.clear();
						tmprows = null;
					}
					// if (row != null) {
					// row.clear();
					// row = null;
					// }
					if (colnames != null) {
						colnames.clear();
						colnames = null;
					}
					if (condigp != null) {
						condigp.clear();
						condigp = null;
					}
					if (subcondi != null) {
						subcondi.clear();
						subcondi = null;
					}
					if (idxs != null) {
						idxs.clear();
						idxs = null;
					}
					if (idxMap != null) {
						idxMap.clear();
						idxMap = null;
					}
					if (tbIdxMap != null) {
						tbIdxMap.clear();
						tbIdxMap = null;
					}
					if (tmpIdxMap != null) {
						tmpIdxMap.clear();
						tmpIdxMap = null;
					}
					if (joinedtb != null) {
						joinedtb.clear();
						joinedtb = null;
					}
				}

			}
		}
		return datas;
	}

	public ArrayList<Object> JoinResult(Connection conn, String sql1, String sql2, ArrayList<String[]> cols,
			boolean sortByCols) {
		ArrayList<Object> datas = null;
		PreparedStatement ps1 = null;
		ResultSet rs1 = null;
		PreparedStatement ps2 = null;
		ResultSet rs2 = null;

		try {
			if (conn != null && !conn.isClosed()) {

				if (sortByCols) {
					sql1 = "select * from (" + sql1 + ") as data1 order by ";
					sql2 = "select * from (" + sql2 + ") as data2 order by ";

					for (int i = 0; i < cols.size(); i++) {
						if (i == 0) {
							sql1 += cols.get(i)[0];
							sql2 += cols.get(i)[1];
						} else {
							sql1 += "," + cols.get(i)[0];
							sql2 += "," + cols.get(i)[1];
						}
					}
				}

				ps1 = conn.prepareStatement(sql1, ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
				rs1 = ps1.executeQuery();
				ps2 = conn.prepareStatement(sql2, ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
				rs2 = ps2.executeQuery();
				datas = JoinResult(rs1, rs2, cols);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return datas;
	}

	public ArrayList<Object> JoinResult(ResultSet rs1, ResultSet rs2, ArrayList<String[]> cols) {
		ArrayList<Object> datas = null;
		HashMap<String, Object> tmp = null;
		ResultSetMetaData mrs1 = null;
		ResultSetMetaData mrs2 = null;
		String key = null;
		ArrayList<Integer> idxs = null;
		HashMap<String, Object> hm2 = null;
		HashMap<String, String> colnames = null;
		if (rs1 != null && rs2 != null && cols != null) {
			try {
				mrs1 = rs1.getMetaData();
				mrs2 = rs2.getMetaData();
				colnames = colnameRepeat(mrs1, mrs2);
				hm2 = new HashMap<String, Object>();

				while (rs2.next()) {
					key = "";
					for (int i = 0; i < cols.size(); i++) {
						if (rs2.getString(cols.get(i)[1]) == null) {
							key += "-$N$-$*$";
						} else {
							key += rs2.getString(cols.get(i)[1]).trim() + "$*$";
						}
					}

					if (hm2.containsKey(key)) {
						idxs = (ArrayList<Integer>) hm2.get(key);
					} else {
						idxs = new ArrayList<Integer>();
					}
					idxs.add(rs2.getRow());
					hm2.put(key, idxs);
				}

				while (rs1.next()) {
					key = "";
					for (int i = 0; i < cols.size(); i++) {
						if (rs1.getString(cols.get(i)[0]) == null) {
							key += "-$N$-$*$";
						} else {
							key += rs1.getString(cols.get(i)[0]).trim() + "$*$";
						}
					}

					if (hm2.containsKey(key)) {
						idxs = (ArrayList<Integer>) hm2.get(key);
						for (int j = 0; j < idxs.size(); j++) {
							rs2.absolute(idxs.get(j));

							if (datas == null) {
								datas = new ArrayList<Object>();
							}
							tmp = new HashMap<String, Object>();
							for (int i = 1; i <= mrs1.getColumnCount(); i++) {
								tmp.put(colnames.get(mrs1.getColumnName(i) + "A" + i), rs1.getString(i));
							}
							for (int i = 1; i <= mrs2.getColumnCount(); i++) {
								tmp.put(colnames.get(mrs2.getColumnName(i) + "B" + i), rs2.getString(i));
							}
							datas.add(tmp);
						}
					}
				}

			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				disposers(rs1);
				disposers(rs2);
				if (hm2 != null) {
					hm2.clear();
					hm2 = null;
				}
				if (colnames != null) {
					colnames.clear();
					colnames = null;
				}
			}

		}
		return datas;
	}

	public ArrayList<HashMap<String, Object>> JoinResult(ResultSet[] rss, ArrayList<String[]> joincondi) {
		ArrayList<HashMap<String, Object>> datas = null;
		HashMap<String, ArrayList<HashMap<String, Object>>> source = null;
		ArrayList<HashMap<String, Object>> tmp = null;
		ArrayList<HashMap<String, Object>> rows = null;
		ArrayList<HashMap<String, Object>> tmprows = null;
		HashMap<String, Object> row = null;
		HashMap<String, HashMap<String, String>> colnames = null;
		HashMap<String, ArrayList<String[]>> condigp = null;
		ResultSetMetaData rsmd = null;
		ArrayList<String[]> subcondi = null;
		ArrayList<Integer> idxs = null;
		HashMap<String, HashMap<String, HashMap<String, ArrayList<Integer>>>> idxMap = null;
		HashMap<String, HashMap<String, ArrayList<Integer>>> tbIdxMap = null;
		HashMap<String, ArrayList<Integer>> tmpIdxMap = null;
		HashMap<String, String> joinedtb = null;
		String rsid = null;
		String lastkey = null;
		String vkey = null;
		String vkey2 = null;

		datas = new ArrayList<HashMap<String, Object>>();
		if (rss != null && joincondi != null) {
			try {
				colnames = colnameRepeat(rss);
				source = new HashMap<String, ArrayList<HashMap<String, Object>>>();
				idxMap = new LinkedHashMap<String, HashMap<String, HashMap<String, ArrayList<Integer>>>>();
				condigp = condiAnalysis(joincondi, colnames);
				for (int i = 1; i < rss.length; i++) {
					rsid = new String(new byte[] { Byte.parseByte(String.valueOf(65 + i)) });
					tmp = new ArrayList<HashMap<String, Object>>();
					rsmd = rss[i].getMetaData();
					while (rss[i].next()) {
						lastkey = null;
						for (String ckey : condigp.keySet()) {
							if (ckey.indexOf(rsid) != -1) {
								if (lastkey == null || !lastkey.equals(ckey)) {
									subcondi = condigp.get(ckey);
								}
								vkey = ckey + "#*$$*#";
								for (int j = 0; j < subcondi.size(); j++) {
									if (rss[i].getString(subcondi.get(j)[ckey.indexOf(rsid)]) == null) {
										vkey += "-$N$-$*$";
									} else {
										vkey += rss[i].getString(subcondi.get(j)[ckey.indexOf(rsid)]).trim()
												+ "$*$";
									}
								}
								if (idxMap.containsKey(ckey)) {
									tbIdxMap = idxMap.get(ckey);
								} else {
									tbIdxMap = new HashMap<String, HashMap<String, ArrayList<Integer>>>();
								}

								if (tbIdxMap.containsKey(rsid)) {
									tmpIdxMap = tbIdxMap.get(rsid);
								} else {
									tmpIdxMap = new HashMap<String, ArrayList<Integer>>();
								}

								if (tmpIdxMap.containsKey(vkey)) {
									idxs = tmpIdxMap.get(vkey);
								} else {
									idxs = new ArrayList<Integer>();
								}
								idxs.add(rss[i].getRow() - 1);
								tmpIdxMap.put(vkey, idxs);
								tbIdxMap.put(rsid, tmpIdxMap);
								idxMap.put(ckey, tbIdxMap);
								lastkey = ckey;
							}
						}
						row = new HashMap<String, Object>();
						for (int j = 1; j <= rsmd.getColumnCount(); j++) {
							row.put(rsid + "$" + rsmd.getColumnName(j) + "^" + j, rss[i].getString(j));
						}
						tmp.add(row);
					}

					source.put(rsid, tmp);
				}

				rsmd = rss[0].getMetaData();
				while (rss[0].next()) {
					row = null;
					joinedtb = new HashMap<String, String>();
					rows = new ArrayList<HashMap<String, Object>>();

					for (String ckey : condigp.keySet()) {
						if (ckey.indexOf("A") != -1) {
							subcondi = condigp.get(ckey);
							vkey = ckey + "#*$$*#";
							if (rows.size() == 0) {
								for (int j = 0; j < subcondi.size(); j++) {
									if (rss[0].getString(subcondi.get(j)[0]) == null) {
										vkey += "-$N$-$*$";
									} else {
										vkey += rss[0].getString(subcondi.get(j)[0]).trim() + "$*$";
									}
								}

								if (checkJoinCondi(ckey, vkey, ckey.substring(1), idxMap)) {
									idxs = idxMap.get(ckey).get(ckey.substring(1)).get(vkey);

									for (int j = 0; j < idxs.size(); j++) {
										row = new HashMap<String, Object>();
										joinedtb.put("A", "A");
										for (int i = 1; i <= rsmd.getColumnCount(); i++) {
											row.put(colnames.get("A$" + rsmd.getColumnName(i) + "^" + i).get(
													"name"), rss[0].getString(i));
										}

										for (int i = 1; i < rss.length; i++) {
											rsid = new String(new byte[] { Byte.parseByte(String
													.valueOf(65 + i)) });
											if (rsid.equals(ckey.substring(1))) {
												tmp = source.get(rsid);
												joinedtb.put(rsid, rsid);
												for (String colname : tmp.get(idxs.get(j)).keySet()) {
													row.put(colnames.get(colname).get("name"),
															tmp.get(idxs.get(j)).get(colname));
												}
												break;
											}
										}
										rows.add(row);
									}
									continue;
								} else {
									rows.clear();
									break;
								}

							} else {
								rsid = ckey.substring(1);
								tmprows = new ArrayList<HashMap<String, Object>>();
								for (int i = 0; i < rows.size(); i++) {
									row = copyRow(rows.get(i));
									vkey = ckey + "#*$$*#";
									for (int j = 0; j < subcondi.size(); j++) {
										if (row.get(subcondi.get(j)[2]) == null) {
											vkey += "-$N$-$*$";
										} else {
											vkey += row.get(subcondi.get(j)[2]).toString().trim() + "$*$";
										}
									}

									if (checkJoinCondi(ckey, vkey, rsid, idxMap)) {
										idxs = idxMap.get(ckey).get(rsid).get(vkey);
										tmp = source.get(rsid);
										joinedtb.put(rsid, rsid);

										for (int j = 0; j < idxs.size(); j++) {
											row = copyRow(rows.get(i));
											for (String colname : tmp.get(idxs.get(j)).keySet()) {
												row.put(colnames.get(colname).get("name"),
														tmp.get(idxs.get(j)).get(colname));
											}
											tmprows.add(row);
										}
									}
								}

								rows.clear();
								rows.addAll(tmprows);
							}
						} else {
							if (rows.size() > 0) {
								subcondi = condigp.get(ckey);
								if (alljoined(ckey, joinedtb)) {
									for (int i = rows.size() - 1; i >= 0; i--) {
										row = rows.get(i);
										vkey = ckey + "#*$$*#";
										for (int j = 0; j < subcondi.size(); j++) {
											if (row.get(subcondi.get(j)[2]) == null) {
												vkey += "-$N$-$*$";
											} else {
												vkey += row.get(subcondi.get(j)[2]).toString().trim() + "$*$";
											}
										}

										vkey2 = ckey + "#*$$*#";
										for (int j = 0; j < subcondi.size(); j++) {
											if (row.get(subcondi.get(j)[3]) == null) {
												vkey2 += "-$N$-$*$";
											} else {
												vkey2 += row.get(subcondi.get(j)[3]).toString().trim()
														+ "$*$";
											}
										}

										if (!vkey.equals(vkey2)) {
											rows.remove(i);
										}
									}
								} else {
									tmprows = new ArrayList<HashMap<String, Object>>();
									for (int i = 0; i < rows.size(); i++) {
										row = copyRow(rows.get(i));
										vkey = ckey + "#*$$*#";
										if (joinedtb.containsKey(ckey.substring(0, 1))) {
											rsid = ckey.substring(1);
											for (int j = 0; j < subcondi.size(); j++) {
												if (row.get(subcondi.get(j)[2]) == null) {
													vkey += "-$N$-$*$";
												} else {
													vkey += row.get(subcondi.get(j)[2]).toString().trim()
															+ "$*$";
												}
											}
										} else {
											rsid = ckey.substring(0, 1);
											for (int j = 0; j < subcondi.size(); j++) {
												if (row.get(subcondi.get(j)[3]) == null) {
													vkey += "-$N$-$*$";
												} else {
													vkey += row.get(subcondi.get(j)[3]).toString().trim()
															+ "$*$";
												}
											}
										}

										if (checkJoinCondi(ckey, vkey, rsid, idxMap)) {
											idxs = idxMap.get(ckey).get(rsid).get(vkey);
											tmp = source.get(rsid);
											joinedtb.put(rsid, rsid);

											for (int j = 0; j < idxs.size(); j++) {
												row = copyRow(rows.get(i));
												for (String colname : tmp.get(idxs.get(j)).keySet()) {
													row.put(colnames.get(colname).get("name"),
															tmp.get(idxs.get(j)).get(colname));
												}
												tmprows.add(row);
											}
										}
									}

									rows.clear();
									rows.addAll(tmprows);
								}

							} else {
								rows.clear();
								break;
							}
						}
					}

					if (rows != null && rows.size() > 0) {
						datas.addAll(rows);
					}
				}

			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				if (source != null) {
					source.clear();
					source = null;
				}
				if (tmp != null) {
					tmp.clear();
					tmp = null;
				}
				if (rows != null) {
					rows.clear();
					rows = null;
				}
				if (tmprows != null) {
					tmprows.clear();
					tmprows = null;
				}
				// if (row != null) {
				// row.clear();
				// row = null;
				// }
				if (colnames != null) {
					colnames.clear();
					colnames = null;
				}
				if (condigp != null) {
					condigp.clear();
					condigp = null;
				}
				if (subcondi != null) {
					subcondi.clear();
					subcondi = null;
				}
				if (idxs != null) {
					idxs.clear();
					idxs = null;
				}
				if (idxMap != null) {
					idxMap.clear();
					idxMap = null;
				}
				if (tbIdxMap != null) {
					tbIdxMap.clear();
					tbIdxMap = null;
				}
				if (tmpIdxMap != null) {
					tmpIdxMap.clear();
					tmpIdxMap = null;
				}
				if (joinedtb != null) {
					joinedtb.clear();
					joinedtb = null;
				}
			}

		}
		return datas;
	}

	private HashMap<String, Object> copyRow(HashMap<String, Object> row) {
		HashMap<String, Object> crow = new HashMap<String, Object>();

		if (row != null) {
			for (String key : row.keySet()) {
				crow.put(key, row.get(key));
			}
		}

		return crow;
	}

	private boolean alljoined(String ckey, HashMap<String, String> joinedtb) {
		boolean ok = true;
		String tmp = null;
		if (ckey != null && joinedtb != null) {
			for (int i = 0; i < ckey.length(); i++) {
				tmp = ckey.substring(i, i + 1);
				if (!joinedtb.containsKey(tmp)) {
					ok = false;
					break;
				}
			}
		}

		return ok;
	}

	private boolean checkJoinCondi(String ckey, String vkey, String rsid,
			HashMap<String, HashMap<String, HashMap<String, ArrayList<Integer>>>> idxMap) {
		boolean ok = false;

		if (ckey != null && ckey.length() > 0 && idxMap != null) {
			if (idxMap.containsKey(ckey)) {
				if (idxMap.get(ckey) != null) {
					if (idxMap.get(ckey).get(rsid) != null) {
						ok = idxMap.get(ckey).get(rsid).containsKey(vkey);
					}
				}
			}
		}

		return ok;
	}

	private LinkedHashMap<String, ArrayList<String[]>> condiAnalysis(ArrayList<String[]> joincondi,
			HashMap<String, HashMap<String, String>> cols) {
		LinkedHashMap<String, ArrayList<String[]>> condiGroup = null;
		HashMap<String, ArrayList<String[]>> tmpgp = null;
		String[] tmp = null;
		String[] tmpcol1 = null;
		String[] tmpcol2 = null;
		ArrayList<String[]> condis = null;
		String key = null;

		condiGroup = new LinkedHashMap<String, ArrayList<String[]>>();
		tmpgp = new HashMap<String, ArrayList<String[]>>();
		for (int i = 0; i < joincondi.size(); i++) {
			tmp = joincondi.get(i);
			if (tmp != null && tmp.length == 2) {
				tmpcol1 = tmp[0].split("\\.");
				tmpcol2 = tmp[1].split("\\.");

				if (tmpcol1[0].getBytes()[0] < tmpcol2[0].getBytes()[0]) {
					key = tmpcol1[0] + tmpcol2[0];
				} else {
					key = tmpcol2[0] + tmpcol1[0];
				}
				if (tmpgp.containsKey(key)) {
					condis = tmpgp.get(key);
				} else {
					condis = new ArrayList<String[]>();
				}
				if (tmpcol1[0].getBytes()[0] < tmpcol2[0].getBytes()[0]) {
					condis.add(new String[] { tmpcol1[1], tmpcol2[1], getNewColName(cols, tmpcol1),
							getNewColName(cols, tmpcol2) });
				} else {
					condis.add(new String[] { tmpcol2[1], tmpcol1[1], getNewColName(cols, tmpcol2),
							getNewColName(cols, tmpcol1) });
				}
				tmpgp.put(key, condis);
			}
		}

		if (tmpgp != null && tmpgp.size() > 0) {
			String[] keylist = new String[tmpgp.size()];
			int cnt = 0;
			for (String k : tmpgp.keySet()) {
				keylist[cnt] = k;
				cnt++;
			}
			Arrays.sort(keylist);
			for (int i = 0; i < keylist.length; i++) {
				condiGroup.put(keylist[i], tmpgp.get(keylist[i]));
			}
		}

		return condiGroup;
	}

	private String getNewColName(HashMap<String, HashMap<String, String>> cols, String[] name) {
		String result = null;
		HashMap<String, String> tmp = null;
		if (name != null && name.length >= 2) {
			if (cols == null) {
				result = name[1];
			} else {
				for (String key : cols.keySet()) {
					tmp = cols.get(key);
					if (tmp.get("rsid").equals(name[0]) && tmp.get("oriname").equals(name[1])) {
						result = tmp.get("name");
						break;
					}
				}
			}
		}

		if (result == null) {
			result = "";
		}
		return result;
	}

	public ArrayList<HashMap<String, Object>> JoinResult(ArrayList<HashMap<String, Object>> set1,
			ArrayList<HashMap<String, Object>> set2, ArrayList<String[]> cols) {
		ArrayList<HashMap<String, Object>> datas = null;
		HashMap<String, Object> tmp = null;
		HashMap<String, Object> row = null;
		ArrayList<Integer> idxs = null;
		HashMap<String, ArrayList<Integer>> idxMap = null;
		String key = null;
		int cnt = 0;

		datas = new ArrayList<HashMap<String, Object>>();

		if (set1 != null && set2 != null && cols != null) {
			idxMap = new HashMap<String, ArrayList<Integer>>();

			for (int i = 0; i < set2.size(); i++) {
				key = "";
				tmp = set2.get(i);
				for (int j = 0; j < cols.size(); j++) {
					if (tmp.get(cols.get(j)[1]) == null) {
						key += "-$N$-$*$";
					} else {
						key += tmp.get(cols.get(j)[1]).toString().trim() + "$*$";
					}
				}

				if (idxMap.containsKey(key)) {
					idxs = idxMap.get(key);
				} else {
					idxs = new ArrayList<Integer>();
				}
				idxs.add(i);
				idxMap.put(key, idxs);
			}

			for (int i = 0; i < set1.size(); i++) {
				key = "";
				tmp = set1.get(i);
				for (int j = 0; j < cols.size(); j++) {
					if (tmp.get(cols.get(j)[0]) == null) {
						key += "-$N$-$*$";
					} else {
						key += tmp.get(cols.get(j)[0]).toString().trim() + "$*$";
					}
				}

				if (idxMap.containsKey(key)) {
					idxs = idxMap.get(key);
					for (int j = 0; j < idxs.size(); j++) {
						if (datas == null) {
							datas = new ArrayList<HashMap<String, Object>>();
						}
						row = new HashMap<String, Object>();
						for (String colname : tmp.keySet()) {
							row.put(colname, tmp.get(colname));
						}
						tmp = set2.get(idxs.get(j));
						for (String colname : tmp.keySet()) {
							row.put(colname, tmp.get(colname));
						}
						datas.add(row);
					}
				}
			}

		}
		return datas;
	}

	public ArrayList<Object> MathJoinResult(ResultSet rs1, ResultSet rs2, ArrayList<String[]> cols,
			HashMap<String, String> mathcol) {
		ArrayList<Object> datas = null;
		HashMap<String, Object> tmp = null;
		ResultSetMetaData mrs1 = null;
		ResultSetMetaData mrs2 = null;
		String key = null;
		ArrayList<Integer> idxs = null;
		HashMap<String, Object> hm2 = null;
		HashMap<String, String> colnames = null;
		String[] mrules = null;
		String[] rulecol = null;
		double mathvalue = 0;

		if (rs1 != null && rs2 != null && cols != null) {
			try {
				mrs1 = rs1.getMetaData();
				mrs2 = rs2.getMetaData();
				colnames = colnameRepeat(mrs1, mrs2);
				hm2 = new HashMap<String, Object>();

				while (rs2.next()) {
					key = "";
					for (int i = 0; i < cols.size(); i++) {
						if (rs2.getString(cols.get(i)[1]) == null) {
							key += "-$N$-$*$";
						} else {
							key += rs2.getString(cols.get(i)[1]).trim() + "$*$";
						}
					}

					if (hm2.containsKey(key)) {
						idxs = (ArrayList<Integer>) hm2.get(key);
					} else {
						idxs = new ArrayList<Integer>();
					}
					idxs.add(rs2.getRow());
					hm2.put(key, idxs);
				}

				while (rs1.next()) {
					key = "";
					for (int i = 0; i < cols.size(); i++) {
						if (rs1.getString(cols.get(i)[0]) == null) {
							key += "-$N$-$*$";
						} else {
							key += rs1.getString(cols.get(i)[0]).trim() + "$*$";
						}
					}

					if (hm2.containsKey(key)) {
						idxs = (ArrayList<Integer>) hm2.get(key);
						for (int j = 0; j < idxs.size(); j++) {
							rs2.absolute(idxs.get(j));

							if (datas == null) {
								datas = new ArrayList<Object>();
							}
							tmp = new HashMap<String, Object>();
							for (int i = 1; i <= mrs1.getColumnCount(); i++) {
								tmp.put(colnames.get(mrs1.getColumnName(i) + "A" + i), rs1.getString(i));
							}
							for (int i = 1; i <= mrs2.getColumnCount(); i++) {
								tmp.put(colnames.get(mrs2.getColumnName(i) + "B" + i), rs2.getString(i));
							}

							if (mathcol != null && mathcol.size() > 0) {
								for (String mcol : mathcol.keySet()) {
									mathvalue = 0;
									if (mathcol.get(mcol) == null || mathcol.get(mcol).trim().length() == 0) {
										tmp.put(mcol, "");
									} else {
										mrules = mathcol.get(mcol).split("\\$");

										for (int i = 0; i < mrules.length; i++) {
											if (i == 0) {
												if (tmp.containsKey(mrules[i])) {
													if (tmp.get(mrules[i]) != null
															&& isNumber(tmp.get(mrules[i].trim()).toString()
																	.trim())) {
														mathvalue = Double.parseDouble(tmp
																.get(mrules[i].trim()).toString().trim());
													}
												} else if (mrules[i] != null && mrules[i].trim().length() > 0
														&& isNumber(mrules[i].trim())) {
													mathvalue = Double.parseDouble(mrules[i].trim());
												} else {
													tmp.put(mcol, "");
													break;
												}

											} else {
												rulecol = mrules[i].split("#");
												if (rulecol != null && rulecol.length >= 2) {
													if (tmp.containsKey(rulecol[1].trim())) {
														if (tmp.get(rulecol[1].trim()) != null
																&& isNumber(tmp.get(rulecol[1].trim())
																		.toString().trim())) {
															if (rulecol[0].equals("+")) {
																mathvalue = mathvalue
																		+ Double.parseDouble(tmp
																				.get(rulecol[1].trim())
																				.toString().trim());
															} else if (rulecol[0].equals("-")) {
																mathvalue = mathvalue
																		- Double.parseDouble(tmp
																				.get(rulecol[1].trim())
																				.toString().trim());
															} else if (rulecol[0].equals("*")) {
																mathvalue = mathvalue
																		* Double.parseDouble(tmp
																				.get(rulecol[1].trim())
																				.toString().trim());
															} else if (rulecol[0].equals("/")) {
																if (Double.parseDouble(tmp.get(rulecol[1])
																		.toString()) != 0) {
																	mathvalue = mathvalue
																			/ Double.parseDouble(tmp
																					.get(rulecol[1].trim())
																					.toString().trim());
																} else {
																	tmp.put(mcol, "");
																	break;
																}
															} else {
																tmp.put(mcol, "");
																break;
															}
														}
													} else if (rulecol[1] != null
															&& rulecol[1].trim().length() > 0
															&& isNumber(rulecol[1].trim())) {

														if (rulecol[0].equals("+")) {
															mathvalue = mathvalue
																	+ Double.parseDouble(rulecol[1].trim());
														} else if (rulecol[0].equals("-")) {
															mathvalue = mathvalue
																	- Double.parseDouble(rulecol[1].trim());
														} else if (rulecol[0].equals("*")) {
															mathvalue = mathvalue
																	* +Double.parseDouble(rulecol[1].trim());
														} else if (rulecol[0].equals("/")) {
															if (Double.parseDouble(rulecol[1].trim()) != 0) {
																mathvalue = mathvalue
																		/ Double.parseDouble(rulecol[1]
																				.trim());
															} else {
																tmp.put(mcol, "");
																break;
															}
														} else {
															tmp.put(mcol, "");
															break;
														}
													} else {
														tmp.put(mcol, "");
														break;
													}
												} else {
													tmp.put(mcol, "");
													break;
												}
											}
										}
										if (!tmp.containsKey(mcol)) {
											tmp.put(mcol, mathvalue);
										}
									}
								}
							}

							datas.add(tmp);
						}
					}
				}

			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				disposers(rs1);
				disposers(rs2);
				if (hm2 != null) {
					hm2.clear();
					hm2 = null;
				}
				if (colnames != null) {
					colnames.clear();
					colnames = null;
				}
			}

		}
		return datas;
	}

	public ArrayList<Object> LeftJoinResult(Connection conn, String sql1, String sql2,
			ArrayList<String[]> cols, boolean sortByCols) {
		ArrayList<Object> datas = null;
		PreparedStatement ps1 = null;
		ResultSet rs1 = null;
		PreparedStatement ps2 = null;
		ResultSet rs2 = null;
		// long l1 = 0;
		try {
			if (conn != null && !conn.isClosed()) {

				if (sortByCols) {
					sql1 = "select * from (" + sql1 + ") as data1 order by ";
					sql2 = "select * from (" + sql2 + ") as data2 order by ";

					for (int i = 0; i < cols.size(); i++) {
						if (i == 0) {
							sql1 += cols.get(i)[0];
							sql2 += cols.get(i)[1];
						} else {
							sql1 += "," + cols.get(i)[0];
							sql2 += "," + cols.get(i)[1];
						}
					}
				}

				ps1 = conn.prepareStatement(sql1, ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
				rs1 = ps1.executeQuery();
				ps2 = conn.prepareStatement(sql2, ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
				rs2 = ps2.executeQuery();
				datas = LeftJoinResult(rs1, rs2, cols);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return datas;
	}

	public ArrayList<Object> LeftJoinResult(ResultSet rs1, ResultSet rs2, ArrayList<String[]> cols) {
		ArrayList<Object> datas = null;
		HashMap<String, Object> tmp = null;
		ResultSetMetaData mrs1 = null;
		ResultSetMetaData mrs2 = null;
		String key = null;
		ArrayList<Integer> idxs = null;
		HashMap<String, Object> hm2 = null;
		HashMap<String, String> colnames = null;
		if (rs1 != null && rs2 != null && cols != null) {
			try {
				mrs1 = rs1.getMetaData();
				mrs2 = rs2.getMetaData();
				colnames = colnameRepeat(mrs1, mrs2);
				hm2 = new HashMap<String, Object>();

				while (rs2.next()) {
					key = "";
					for (int i = 0; i < cols.size(); i++) {
						if (rs2.getString(cols.get(i)[1]) == null) {
							key += "-$N$-$*$";
						} else {
							key += rs2.getString(cols.get(i)[1]).trim() + "$*$";
						}
					}

					if (hm2.containsKey(key)) {
						idxs = (ArrayList<Integer>) hm2.get(key);
					} else {
						idxs = new ArrayList<Integer>();
					}
					idxs.add(rs2.getRow());
					hm2.put(key, idxs);
				}

				while (rs1.next()) {
					key = "";
					for (int i = 0; i < cols.size(); i++) {
						if (rs1.getString(cols.get(i)[0]) == null) {
							key += "-$N$-$*$";
						} else {
							key += rs1.getString(cols.get(i)[0]).trim() + "$*$";
						}
					}

					if (hm2.containsKey(key)) {
						idxs = (ArrayList<Integer>) hm2.get(key);
						for (int j = 0; j < idxs.size(); j++) {
							rs2.absolute(idxs.get(j));

							if (datas == null) {
								datas = new ArrayList<Object>();
							}
							tmp = new HashMap<String, Object>();
							for (int i = 1; i <= mrs1.getColumnCount(); i++) {
								tmp.put(colnames.get(mrs1.getColumnName(i) + "A" + i), rs1.getString(i));
							}
							for (int i = 1; i <= mrs2.getColumnCount(); i++) {
								tmp.put(colnames.get(mrs2.getColumnName(i) + "B" + i), rs2.getString(i));
							}
							datas.add(tmp);
						}
					} else {
						tmp = new HashMap<String, Object>();
						for (int i = 1; i <= mrs1.getColumnCount(); i++) {
							tmp.put(colnames.get(mrs1.getColumnName(i) + "A" + i), rs1.getString(i));
						}
						for (int i = 1; i <= mrs2.getColumnCount(); i++) {
							tmp.put(colnames.get(mrs2.getColumnName(i) + "B" + i), null);
						}
						datas.add(tmp);
					}
				}

			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				disposers(rs1);
				disposers(rs2);
				if (hm2 != null) {
					hm2.clear();
					hm2 = null;
				}
				if (colnames != null) {
					colnames.clear();
					colnames = null;
				}
			}

		}
		return datas;
	}

	public ArrayList<HashMap<String, Object>> LeftJoinResult(ResultSet[] rss, ArrayList<String[]> joincondi) {
		ArrayList<HashMap<String, Object>> datas = null;
		HashMap<String, ArrayList<HashMap<String, Object>>> source = null;
		ArrayList<HashMap<String, Object>> tmp = null;
		ArrayList<HashMap<String, Object>> rows = null;
		ArrayList<HashMap<String, Object>> tmprows = null;
		HashMap<String, Object> row = null;
		HashMap<String, HashMap<String, String>> colnames = null;
		HashMap<String, ArrayList<String[]>> condigp = null;
		ResultSetMetaData rsmd = null;
		ResultSetMetaData subrsmd = null;
		ArrayList<String[]> subcondi = null;
		ArrayList<Integer> idxs = null;
		HashMap<String, HashMap<String, HashMap<String, ArrayList<Integer>>>> idxMap = null;
		HashMap<String, HashMap<String, ArrayList<Integer>>> tbIdxMap = null;
		HashMap<String, ArrayList<Integer>> tmpIdxMap = null;
		HashMap<String, String> joinedtb = null;
		String rsid = null;
		String lastkey = null;
		String vkey = null;
		String vkey2 = null;

		datas = new ArrayList<HashMap<String, Object>>();
		if (rss != null && joincondi != null) {
			try {
				colnames = colnameRepeat(rss);
				source = new HashMap<String, ArrayList<HashMap<String, Object>>>();
				idxMap = new LinkedHashMap<String, HashMap<String, HashMap<String, ArrayList<Integer>>>>();
				condigp = condiAnalysis(joincondi, colnames);
				for (int i = 1; i < rss.length; i++) {
					rsid = new String(new byte[] { Byte.parseByte(String.valueOf(65 + i)) });
					tmp = new ArrayList<HashMap<String, Object>>();
					rsmd = rss[i].getMetaData();
					while (rss[i].next()) {
						lastkey = null;
						for (String ckey : condigp.keySet()) {
							if (ckey.indexOf(rsid) != -1) {
								if (lastkey == null || !lastkey.equals(ckey)) {
									subcondi = condigp.get(ckey);
								}
								vkey = ckey + "#*$$*#";
								for (int j = 0; j < subcondi.size(); j++) {
									if (rss[i].getString(subcondi.get(j)[ckey.indexOf(rsid)]) == null) {
										vkey += "-$N$-$*$";
									} else {
										vkey += rss[i].getString(subcondi.get(j)[ckey.indexOf(rsid)]).trim()
												+ "$*$";
									}
								}
								if (idxMap.containsKey(ckey)) {
									tbIdxMap = idxMap.get(ckey);
								} else {
									tbIdxMap = new HashMap<String, HashMap<String, ArrayList<Integer>>>();
								}

								if (tbIdxMap.containsKey(rsid)) {
									tmpIdxMap = tbIdxMap.get(rsid);
								} else {
									tmpIdxMap = new HashMap<String, ArrayList<Integer>>();
								}

								if (tmpIdxMap.containsKey(vkey)) {
									idxs = tmpIdxMap.get(vkey);
								} else {
									idxs = new ArrayList<Integer>();
								}
								idxs.add(rss[i].getRow() - 1);
								tmpIdxMap.put(vkey, idxs);
								tbIdxMap.put(rsid, tmpIdxMap);
								idxMap.put(ckey, tbIdxMap);
								lastkey = ckey;
							}
						}
						row = new HashMap<String, Object>();
						for (int j = 1; j <= rsmd.getColumnCount(); j++) {
							row.put(rsid + "$" + rsmd.getColumnName(j) + "^" + j, rss[i].getString(j));
						}
						tmp.add(row);
					}

					source.put(rsid, tmp);
				}

				rsmd = rss[0].getMetaData();
				while (rss[0].next()) {
					row = null;
					joinedtb = new HashMap<String, String>();
					rows = new ArrayList<HashMap<String, Object>>();

					for (String ckey : condigp.keySet()) {
						if (ckey.indexOf("A") != -1) {
							subcondi = condigp.get(ckey);
							vkey = ckey + "#*$$*#";
							if (rows.size() == 0) {
								for (int j = 0; j < subcondi.size(); j++) {
									if (rss[0].getString(subcondi.get(j)[0]) == null) {
										vkey += "-$N$-$*$";
									} else {
										vkey += rss[0].getString(subcondi.get(j)[0]).trim() + "$*$";
									}
								}

								if (checkJoinCondi(ckey, vkey, ckey.substring(1), idxMap)) {
									idxs = idxMap.get(ckey).get(ckey.substring(1)).get(vkey);

									for (int j = 0; j < idxs.size(); j++) {
										row = new HashMap<String, Object>();
										joinedtb.put("A", "A");
										for (int i = 1; i <= rsmd.getColumnCount(); i++) {
											row.put(colnames.get("A$" + rsmd.getColumnName(i) + "^" + i).get(
													"name"), rss[0].getString(i));
										}

										for (int i = 1; i < rss.length; i++) {
											rsid = new String(new byte[] { Byte.parseByte(String
													.valueOf(65 + i)) });
											if (rsid.equals(ckey.substring(1))) {
												tmp = source.get(rsid);
												joinedtb.put(rsid, rsid);
												for (String colname : tmp.get(idxs.get(j)).keySet()) {
													row.put(colnames.get(colname).get("name"),
															tmp.get(idxs.get(j)).get(colname));
												}
												break;
											}
										}
										rows.add(row);
									}
									continue;
								} else {
									row = new HashMap<String, Object>();
									joinedtb.put("A", "A");
									for (int i = 1; i <= rsmd.getColumnCount(); i++) {
										row.put(colnames.get("A$" + rsmd.getColumnName(i) + "^" + i).get(
												"name"), rss[0].getString(i));
									}

									rsid = ckey.substring(1);
									subrsmd = rss[rsid.getBytes()[0] - 65].getMetaData();
									joinedtb.put(rsid, rsid);
									for (int k = 1; k <= subrsmd.getColumnCount(); k++) {
										row.put(colnames.get(rsid + "$" + subrsmd.getColumnName(k) + "^" + k)
												.get("name"), "");
									}
									rows.add(row);
								}

							} else {
								rsid = ckey.substring(1);
								tmprows = new ArrayList<HashMap<String, Object>>();
								for (int i = 0; i < rows.size(); i++) {
									row = copyRow(rows.get(i));
									vkey = ckey + "#*$$*#";
									for (int j = 0; j < subcondi.size(); j++) {
										if (row.get(subcondi.get(j)[2]) == null) {
											vkey += "-$N$-$*$";
										} else {
											vkey += row.get(subcondi.get(j)[2]).toString().trim() + "$*$";
										}
									}

									tmp = source.get(rsid);
									joinedtb.put(rsid, rsid);
									if (checkJoinCondi(ckey, vkey, rsid, idxMap)) {
										idxs = idxMap.get(ckey).get(rsid).get(vkey);

										for (int j = 0; j < idxs.size(); j++) {
											row = copyRow(rows.get(i));
											for (String colname : tmp.get(idxs.get(j)).keySet()) {
												row.put(colnames.get(colname).get("name"),
														tmp.get(idxs.get(j)).get(colname));
											}
											tmprows.add(row);
										}
									} else {
										row = copyRow(rows.get(i));
										subrsmd = rss[rsid.getBytes()[0] - 65].getMetaData();
										joinedtb.put(rsid, rsid);
										for (int k = 1; k <= subrsmd.getColumnCount(); k++) {
											row.put(colnames.get(
													rsid + "$" + subrsmd.getColumnName(k) + "^" + k).get(
													"name"), "");
										}
										tmprows.add(row);
									}
								}

								rows.clear();
								rows.addAll(tmprows);
							}
						} else {
							if (rows.size() > 0) {
								subcondi = condigp.get(ckey);
								if (alljoined(ckey, joinedtb)) {
									for (int i = rows.size() - 1; i >= 0; i--) {
										row = rows.get(i);
										vkey = ckey + "#*$$*#";
										for (int j = 0; j < subcondi.size(); j++) {
											if (row.get(subcondi.get(j)[2]) == null) {
												vkey += "-$N$-$*$";
											} else {
												vkey += row.get(subcondi.get(j)[2]).toString().trim() + "$*$";
											}
										}

										vkey2 = ckey + "#*$$*#";
										for (int j = 0; j < subcondi.size(); j++) {
											if (row.get(subcondi.get(j)[3]) == null) {
												vkey2 += "-$N$-$*$";
											} else {
												vkey2 += row.get(subcondi.get(j)[3]).toString().trim()
														+ "$*$";
											}
										}

										if (!vkey.equals(vkey2)) {
											subrsmd = rss[ckey.substring(1).getBytes()[0] - 65].getMetaData();
											for (int k = 1; k <= subrsmd.getColumnCount(); k++) {
												row.put(colnames.get(
														rsid + "$" + subrsmd.getColumnName(k) + "^" + k).get(
														"name"), "");
											}

											rows.remove(i);
											rows.add(i, row);
										}
									}
								} else {
									tmprows = new ArrayList<HashMap<String, Object>>();
									for (int i = 0; i < rows.size(); i++) {
										row = copyRow(rows.get(i));
										vkey = ckey + "#*$$*#";
										if (joinedtb.containsKey(ckey.substring(0, 1))) {
											rsid = ckey.substring(1);
											for (int j = 0; j < subcondi.size(); j++) {
												if (row.get(subcondi.get(j)[2]) == null) {
													vkey += "-$N$-$*$";
												} else {
													vkey += row.get(subcondi.get(j)[2]).toString().trim()
															+ "$*$";
												}
											}
										} else {
											rsid = ckey.substring(0, 1);
											for (int j = 0; j < subcondi.size(); j++) {
												if (row.get(subcondi.get(j)[3]) == null) {
													vkey += "-$N$-$*$";
												} else {
													vkey += row.get(subcondi.get(j)[3]).toString().trim()
															+ "$*$";
												}
											}
										}

										if (checkJoinCondi(ckey, vkey, rsid, idxMap)) {
											idxs = idxMap.get(ckey).get(rsid).get(vkey);
											tmp = source.get(rsid);
											joinedtb.put(rsid, rsid);

											for (int j = 0; j < idxs.size(); j++) {
												row = copyRow(rows.get(i));
												for (String colname : tmp.get(idxs.get(j)).keySet()) {
													row.put(colnames.get(colname).get("name"),
															tmp.get(idxs.get(j)).get(colname));
												}
												tmprows.add(row);
											}
										} else {
											subrsmd = rss[rsid.getBytes()[0] - 65].getMetaData();
											joinedtb.put(rsid, rsid);
											row = copyRow(rows.get(i));
											for (int k = 1; k <= subrsmd.getColumnCount(); k++) {
												row.put(colnames.get(
														rsid + "$" + subrsmd.getColumnName(k) + "^" + k).get(
														"name"), "");
											}
											tmprows.add(row);
										}
									}

									rows.clear();
									rows.addAll(tmprows);
								}

							} else {
								rows.clear();
								break;
							}
						}
					}

					if (rows != null && rows.size() > 0) {
						datas.addAll(rows);
					}
				}

			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				if (source != null) {
					source.clear();
					source = null;
				}
				if (tmp != null) {
					tmp.clear();
					tmp = null;
				}
				if (rows != null) {
					rows.clear();
					rows = null;
				}
				if (tmprows != null) {
					tmprows.clear();
					tmprows = null;
				}
				// if (row != null) {
				// row.clear();
				// row = null;
				// }
				if (colnames != null) {
					colnames.clear();
					colnames = null;
				}
				if (condigp != null) {
					condigp.clear();
					condigp = null;
				}
				if (subcondi != null) {
					subcondi.clear();
					subcondi = null;
				}
				if (idxs != null) {
					idxs.clear();
					idxs = null;
				}
				if (idxMap != null) {
					idxMap.clear();
					idxMap = null;
				}
				if (tbIdxMap != null) {
					tbIdxMap.clear();
					tbIdxMap = null;
				}
				if (tmpIdxMap != null) {
					tmpIdxMap.clear();
					tmpIdxMap = null;
				}
				if (joinedtb != null) {
					joinedtb.clear();
					joinedtb = null;
				}
			}

		}
		return datas;
	}

	public ArrayList<Object> UnionAllResult(Connection conn, String sql1, String sql2) {
		ArrayList<Object> datas = null;
		PreparedStatement ps1 = null;
		ResultSet rs1 = null;
		PreparedStatement ps2 = null;
		ResultSet rs2 = null;

		try {
			if (conn != null && !conn.isClosed()) {
				ps1 = conn.prepareStatement(sql1, ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
				rs1 = ps1.executeQuery();
				ps2 = conn.prepareStatement(sql2, ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
				rs2 = ps2.executeQuery();
				datas = UnionAllResult(rs1, rs2);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return datas;
	}

	public ArrayList<HashMap<String, Object>> UnionAllResult(ResultSet[] rss) {
		ArrayList<HashMap<String, Object>> datas = null;
		HashMap<String, Object> tmp = null;
		ResultSetMetaData mrs1 = null;
		HashMap<String, String> colnames = null;

		if (rss != null) {
			try {
				mrs1 = rss[0].getMetaData();
				colnames = colnameRepeat(mrs1, null);
				// rs1.beforeFirst();
				while (rss[0].next()) {
					if (datas == null) {
						datas = new ArrayList<HashMap<String, Object>>();
					}
					tmp = new HashMap<String, Object>();
					for (int i = 1; i <= mrs1.getColumnCount(); i++) {
						tmp.put(colnames.get(mrs1.getColumnName(i) + "A" + i), rss[0].getString(i));
					}
					datas.add(tmp);
				}

				// rs2.beforeFirst();
				for (int j = 1; j < rss.length; j++) {
					while (rss[j].next()) {
						if (datas == null) {
							datas = new ArrayList<HashMap<String, Object>>();
						}
						tmp = new HashMap<String, Object>();
						for (int i = 1; i <= mrs1.getColumnCount(); i++) {
							if (i > rss[j].getMetaData().getColumnCount()) {
								tmp.put(colnames.get(mrs1.getColumnName(i) + "A" + i), "");
							} else {
								tmp.put(colnames.get(mrs1.getColumnName(i) + "A" + i), rss[j].getString(i));
							}
						}
						datas.add(tmp);
					}
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				for (int j = 0; j < rss.length; j++) {
					disposers(rss[j]);
				}
				if (colnames != null) {
					colnames.clear();
					colnames = null;
				}
			}

		}
		return datas;
	}

	public ArrayList<Object> UnionAllResult(ResultSet rs1, ResultSet rs2) {
		ArrayList<Object> datas = null;
		HashMap<String, Object> tmp = null;
		ResultSetMetaData mrs1 = null;
		HashMap<String, String> colnames = null;

		if (rs1 != null && rs2 != null) {
			try {
				mrs1 = rs1.getMetaData();
				colnames = colnameRepeat(mrs1, null);
				rs1.beforeFirst();
				while (rs1.next()) {
					if (datas == null) {
						datas = new ArrayList<Object>();
					}
					tmp = new HashMap<String, Object>();
					for (int i = 1; i <= mrs1.getColumnCount(); i++) {
						tmp.put(colnames.get(mrs1.getColumnName(i) + "A" + i), rs1.getString(i));
					}
					datas.add(tmp);
				}

				rs2.beforeFirst();
				while (rs2.next()) {
					if (datas == null) {
						datas = new ArrayList<Object>();
					}
					tmp = new HashMap<String, Object>();
					for (int i = 1; i <= mrs1.getColumnCount(); i++) {
						if (i > rs2.getMetaData().getColumnCount()) {
							tmp.put(colnames.get(mrs1.getColumnName(i) + "A" + i), "");
						} else {
							tmp.put(colnames.get(mrs1.getColumnName(i) + "A" + i), rs2.getString(i));
						}
					}
					datas.add(tmp);
				}

			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				disposers(rs1);
				disposers(rs2);
				if (colnames != null) {
					colnames.clear();
					colnames = null;
				}
			}

		}
		return datas;
	}

	public ArrayList<HashMap<String, Object>> UnionAllResultN(ResultSet rs1, ResultSet rs2) {
		ArrayList<HashMap<String, Object>> datas = null;
		HashMap<String, Object> tmp = null;
		ResultSetMetaData mrs1 = null;
		HashMap<String, String> colnames = null;

		if (rs1 != null && rs2 != null) {
			try {
				mrs1 = rs1.getMetaData();
				colnames = colnameRepeat(mrs1, null);
				rs1.beforeFirst();
				while (rs1.next()) {
					if (datas == null) {
						datas = new ArrayList<HashMap<String, Object>>();
					}
					tmp = new HashMap<String, Object>();
					for (int i = 1; i <= mrs1.getColumnCount(); i++) {
						tmp.put(colnames.get(mrs1.getColumnName(i) + "A" + i), rs1.getString(i));
					}
					datas.add(tmp);
				}

				rs2.beforeFirst();
				while (rs2.next()) {
					if (datas == null) {
						datas = new ArrayList<HashMap<String, Object>>();
					}
					tmp = new HashMap<String, Object>();
					for (int i = 1; i <= mrs1.getColumnCount(); i++) {
						if (i > rs2.getMetaData().getColumnCount()) {
							tmp.put(colnames.get(mrs1.getColumnName(i) + "A" + i), "");
						} else {
							tmp.put(colnames.get(mrs1.getColumnName(i) + "A" + i), rs2.getString(i));
						}
					}
					datas.add(tmp);
				}

			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				disposers(rs1);
				disposers(rs2);
				if (colnames != null) {
					colnames.clear();
					colnames = null;
				}
			}

		}
		return datas;
	}

	public ArrayList<Object> UnionResult(Connection conn, String sql1, String sql2) {
		ArrayList<Object> datas = null;
		PreparedStatement ps1 = null;
		ResultSet rs1 = null;
		PreparedStatement ps2 = null;
		ResultSet rs2 = null;

		try {
			if (conn != null && !conn.isClosed()) {
				ps1 = conn.prepareStatement(sql1, ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
				rs1 = ps1.executeQuery();
				ps2 = conn.prepareStatement(sql2, ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
				rs2 = ps2.executeQuery();
				datas = UnionResult(rs1, rs2);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return datas;
	}

	public ArrayList<Object> UnionResult(ResultSet rs1, ResultSet rs2) {
		ArrayList<Object> datas = null;
		HashMap<String, Object> tmp = null;
		ResultSetMetaData mrs1 = null;
		HashMap<String, String> colnames = null;
		HashMap<String, Integer> addedData = null;
		String key = null;

		if (rs1 != null && rs2 != null) {
			try {
				mrs1 = rs1.getMetaData();
				colnames = colnameRepeat(mrs1, null);
				addedData = new HashMap<String, Integer>();
				rs1.beforeFirst();
				while (rs1.next()) {
					key = "";
					if (datas == null) {
						datas = new ArrayList<Object>();
					}
					tmp = new HashMap<String, Object>();
					for (int i = 1; i <= mrs1.getColumnCount(); i++) {
						tmp.put(colnames.get(mrs1.getColumnName(i) + "A" + i), rs1.getString(i));
						key += rs1.getString(i).trim() + "$^*^$";
					}
					if (!addedData.containsKey(key)) {
						datas.add(tmp);
						addedData.put(key, 0);
					}
				}

				rs2.beforeFirst();
				while (rs2.next()) {
					key = "";
					if (datas == null) {
						datas = new ArrayList<Object>();
					}
					tmp = new HashMap<String, Object>();
					for (int i = 1; i <= mrs1.getColumnCount(); i++) {
						if (i > rs2.getMetaData().getColumnCount()) {
							tmp.put(colnames.get(mrs1.getColumnName(i) + "A" + i), "");
							key += i + "overcol-$^*^$";
						} else {
							tmp.put(colnames.get(mrs1.getColumnName(i) + "A" + i), rs2.getString(i));
							key += rs2.getString(i).trim() + "$^*^$";
						}
					}
					if (!addedData.containsKey(key)) {
						datas.add(tmp);
						addedData.put(key, 0);
					}
				}

			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				disposers(rs1);
				disposers(rs2);
				if (colnames != null) {
					colnames.clear();
					colnames = null;
				}
			}

		}
		return datas;
	}

	public ArrayList<HashMap<String, Object>> UnionResult(ResultSet[] rss) {
		ArrayList<HashMap<String, Object>> datas = null;
		HashMap<String, Object> tmp = null;
		ResultSetMetaData mrs1 = null;
		HashMap<String, String> colnames = null;
		HashMap<String, Integer> addedData = null;
		String key = null;

		if (rss != null) {
			try {
				mrs1 = rss[0].getMetaData();
				colnames = colnameRepeat(mrs1, null);
				addedData = new HashMap<String, Integer>();
				// rs1.beforeFirst();
				while (rss[0].next()) {
					key = "";
					if (datas == null) {
						datas = new ArrayList<HashMap<String, Object>>();
					}
					tmp = new HashMap<String, Object>();
					for (int i = 1; i <= mrs1.getColumnCount(); i++) {
						tmp.put(colnames.get(mrs1.getColumnName(i) + "A" + i), rss[0].getString(i));
						key += rss[0].getString(i).trim() + "$^*^$";
					}
					if (!addedData.containsKey(key)) {
						datas.add(tmp);
						addedData.put(key, 0);
					}
				}

				// rs2.beforeFirst();
				for (int j = 1; j < rss.length; j++) {
					while (rss[j].next()) {
						key = "";
						if (datas == null) {
							datas = new ArrayList<HashMap<String, Object>>();
						}
						tmp = new HashMap<String, Object>();
						for (int i = 1; i <= mrs1.getColumnCount(); i++) {
							if (i > rss[j].getMetaData().getColumnCount()) {
								tmp.put(colnames.get(mrs1.getColumnName(i) + "A" + i), "");
								key += i + "overcol-$^*^$";
							} else {
								tmp.put(colnames.get(mrs1.getColumnName(i) + "A" + i), rss[j].getString(i));
								key += rss[j].getString(i).trim() + "$^*^$";
							}
						}
						if (!addedData.containsKey(key)) {
							datas.add(tmp);
							addedData.put(key, 0);
						}
					}
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				for (int j = 0; j < rss.length; j++) {
					disposers(rss[j]);
				}
				if (colnames != null) {
					colnames.clear();
					colnames = null;
				}
			}

		}
		return datas;
	}

	public HashMap<String, String> colnameRepeat_ori(ResultSet[] rss) {
		HashMap<String, String> names = null;
		HashMap<String, Integer> names2 = null;
		ResultSetMetaData[] mrs = null;
		String tmpChar = null;
		try {
			names = new LinkedHashMap<String, String>();
			names2 = new HashMap<String, Integer>();
			for (int i = 0; i < rss.length; i++) {
				if (i == 0) {
					mrs = new ResultSetMetaData[rss.length];
				}
				mrs[i] = rss[i].getMetaData();
			}
			for (int j = 0; j < mrs.length; j++) {
				tmpChar = new String(new byte[] { Byte.parseByte(String.valueOf(65 + j)) });
				if (mrs[j] != null) {
					for (int i = 1; i <= mrs[j].getColumnCount(); i++) {
						if (names2.containsKey(mrs[j].getColumnName(i).toLowerCase())) {
							names2.put(mrs[j].getColumnName(i).toLowerCase(),
									names2.get(mrs[j].getColumnName(i)) + 1);
						} else {
							names2.put(mrs[j].getColumnName(i).toLowerCase(), 0);
						}
						if (names2.get(mrs[j].getColumnName(i).toLowerCase()) == 0) {
							names.put(mrs[j].getColumnName(i) + "$" + tmpChar + "$" + i,
									mrs[j].getColumnName(i));
						} else {
							names.put(
									mrs[j].getColumnName(i) + "$" + tmpChar + "$" + i,
									mrs[j].getColumnName(i)
											+ names2.get(mrs[j].getColumnName(i).toLowerCase()));
						}
					}
				}
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		return names;
	}

	public HashMap<String, HashMap<String, String>> colnameRepeat(ResultSet[] rss) {
		HashMap<String, HashMap<String, String>> names = null;
		HashMap<String, Integer> names2 = null;
		ResultSetMetaData[] mrs = null;
		HashMap<String, String> nameSet = null;
		String tmpChar = null;
		try {
			names = new LinkedHashMap<String, HashMap<String, String>>();
			names2 = new HashMap<String, Integer>();
			for (int i = 0; i < rss.length; i++) {
				if (i == 0) {
					mrs = new ResultSetMetaData[rss.length];
				}
				mrs[i] = rss[i].getMetaData();
			}
			for (int j = 0; j < mrs.length; j++) {
				tmpChar = new String(new byte[] { Byte.parseByte(String.valueOf(65 + j)) });
				if (mrs[j] != null) {
					for (int i = 1; i <= mrs[j].getColumnCount(); i++) {
						if (names2.containsKey(mrs[j].getColumnName(i).toLowerCase())) {
							names2.put(mrs[j].getColumnName(i).toLowerCase(),
									names2.get(mrs[j].getColumnName(i).toLowerCase()) + 1);
						} else {
							names2.put(mrs[j].getColumnName(i).toLowerCase(), 0);
						}
						nameSet = new HashMap<String, String>();
						nameSet.put("rsid", tmpChar);
						nameSet.put("oriname", mrs[j].getColumnName(i));
						if (names2.get(mrs[j].getColumnName(i).toLowerCase()) == 0) {
							nameSet.put("name", mrs[j].getColumnName(i));
						} else {
							nameSet.put(
									"name",
									mrs[j].getColumnName(i)
											+ names2.get(mrs[j].getColumnName(i).toLowerCase()));
						}
						names.put(tmpChar + "$" + mrs[j].getColumnName(i) + "^" + i, nameSet);
					}
				}
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		return names;
	}

	public HashMap<String, HashMap<String, String>> colnameRepeat(ResultSetMetaData[] mrs) {
		HashMap<String, HashMap<String, String>> names = null;
		HashMap<String, Integer> names2 = null;
		// ResultSetMetaData[] mrs = null;
		HashMap<String, String> nameSet = null;
		String tmpChar = null;
		try {
			names = new LinkedHashMap<String, HashMap<String, String>>();
			names2 = new HashMap<String, Integer>();
			for (int j = 0; j < mrs.length; j++) {
				tmpChar = new String(new byte[] { Byte.parseByte(String.valueOf(65 + j)) });
				if (mrs[j] != null) {
					for (int i = 1; i <= mrs[j].getColumnCount(); i++) {
						if (names2.containsKey(mrs[j].getColumnName(i).toLowerCase())) {
							names2.put(mrs[j].getColumnName(i).toLowerCase(),
									names2.get(mrs[j].getColumnName(i).toLowerCase()) + 1);
						} else {
							names2.put(mrs[j].getColumnName(i).toLowerCase(), 0);
						}
						nameSet = new HashMap<String, String>();
						nameSet.put("rsid", tmpChar);
						nameSet.put("oriname", mrs[j].getColumnName(i));
						if (names2.get(mrs[j].getColumnName(i).toLowerCase()) == 0) {
							nameSet.put("name", mrs[j].getColumnName(i));
						} else {
							nameSet.put(
									"name",
									mrs[j].getColumnName(i)
											+ names2.get(mrs[j].getColumnName(i).toLowerCase()));
						}
						names.put(tmpChar + "$" + mrs[j].getColumnName(i) + "^" + i, nameSet);
					}
				}
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		return names;
	}

	public HashMap<String, String> colnameRepeat(ResultSetMetaData mrs1, ResultSetMetaData mrs2) {
		HashMap<String, String> names = null;
		HashMap<String, Integer> names2 = null;

		try {
			names = new LinkedHashMap<String, String>();
			new HashMap<String, String>();
			names2 = new HashMap<String, Integer>();
			if (mrs1 != null) {
				for (int i = 1; i <= mrs1.getColumnCount(); i++) {
					if (names2.containsKey(mrs1.getColumnName(i).toLowerCase())) {
						names2.put(mrs1.getColumnName(i).toLowerCase(),
								names2.get(mrs1.getColumnName(i).toLowerCase()) + 1);
					} else {
						names2.put(mrs1.getColumnName(i).toLowerCase(), 0);
					}
					if (names2.get(mrs1.getColumnName(i).toLowerCase()) == 0) {
						names.put(mrs1.getColumnName(i) + "A" + i, mrs1.getColumnName(i));
					} else {
						names.put(mrs1.getColumnName(i) + "A" + i,
								mrs1.getColumnName(i) + names2.get(mrs1.getColumnName(i).toLowerCase()));
					}
				}
			}
			if (mrs2 != null) {
				for (int i = 1; i <= mrs2.getColumnCount(); i++) {
					if (names2.containsKey(mrs2.getColumnName(i).toLowerCase())) {
						names2.put(mrs2.getColumnName(i).toLowerCase(),
								names2.get(mrs2.getColumnName(i).toLowerCase()) + 1);
					} else {
						names2.put(mrs2.getColumnName(i).toLowerCase(), 0);
					}
					if (names2.get(mrs2.getColumnName(i).toLowerCase()) == 0) {
						names.put(mrs2.getColumnName(i) + "B" + i, mrs2.getColumnName(i));
					} else {
						names.put(mrs2.getColumnName(i) + "B" + i,
								mrs2.getColumnName(i) + names2.get(mrs2.getColumnName(i).toLowerCase()));
					}
				}
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		return names;
	}

	private void disposers(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
				rs = null;
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	private boolean isNumber(String value) {
		boolean result = false;

		try {
			result = isInteger(value);
			if (!result) {
				result = isLong(value);
				if (!result) {
					Double.parseDouble(value);
					result = true;
				}
			}
		} catch (Exception ex) {
			result = false;
		}
		return result;
	}

	private boolean isLong(String value) {
		boolean result = false;

		try {
			Long.parseLong(value);

			result = true;
		} catch (Exception ex) {
			result = false;
		}
		return result;
	}

	private boolean isInteger(String value) {
		boolean result = false;

		try {
			Integer.parseInt(value);

			result = true;
		} catch (Exception ex) {
			result = false;
		}
		return result;
	}

}
