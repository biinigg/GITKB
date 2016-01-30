package com.dsc.dci.jweb.funcs.ekb.pe001;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;

import com.dci.jweb.DCIEnums.Database.ConnectionType;
import com.dci.jweb.DataBaseLib.Database.DatabaseObjects;
import com.dci.jweb.PublicLib.DBControl;
import com.dci.jweb.PublicLib.DCIString;
import com.dsc.dci.jweb.pub.Singleton;
import com.dsc.dci.sqlcode.funcs.ekb.sqlPE001;

public class PE001Methods {
	private sqlPE001 cmd;
	private DatabaseObjects dbobj;
	private DBControl dbctrl;
	private String currLang;

	public PE001Methods(sqlPE001 cmd, DatabaseObjects dbobj, DBControl dbctrl, String currLang) {
		this.cmd = cmd;
		this.dbobj = dbobj;
		this.dbctrl = dbctrl;
		this.currLang = currLang;
	}

	public String mappingAnalysis(String mappingString, String value) {
		String mapValue = null;
		try {
			if (!DCIString.isNullOrEmpty(mappingString)) {
				String[] maps = mappingString.split(";");
				String[] mapItem = null;
				Singleton s = Singleton.getInstance();

				if (maps != null && maps.length > 0) {
					for (int i = 0; i < maps.length; i++) {
						if (maps[i].indexOf("=") != -1) {
							mapItem = maps[i].split("=");
							if (mapItem[0].trim().equals(value)) {
								if (this.currLang == null) {
									mapValue = mapItem[1].trim();
								} else {
									mapValue = s.getLanguage(this.currLang, mapItem[1].trim());
								}
								break;
							}
						}
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			mapValue = value;
		} finally {
			if (DCIString.isNullOrEmpty(mapValue)) {
				mapValue = value;
			}
		}
		return mapValue;
	}

	public HashMap<String, String> getIconIDMap(Connection conn) {
		boolean reconn = false;
		PreparedStatement ps = null;
		ResultSet rs = null;
		HashMap<String, String> result = null;

		try {
			if (conn == null || conn.isClosed()) {
				conn = this.dbobj.getConnection(ConnectionType.SYS);
			}
			ps = conn.prepareStatement(this.cmd.getIconIdMap(), ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			rs = ps.executeQuery();

			while (rs.next()) {
				if (result == null) {
					result = new HashMap<String, String>();
				}
				result.put(rs.getString("icon_map_key").trim(), DCIString.Base64Encode(rs.getString("icon_id").trim()));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (reconn) {
				this.dbctrl.closeConnection(null, ps, conn);
			} else {
				this.dbctrl.closeConnection(null, ps, null);
			}
		}

		return result;
	}

	public HashMap<String, String> getIconNameMap(Connection conn) {
		boolean reconn = false;
		PreparedStatement ps = null;
		ResultSet rs = null;
		HashMap<String, String> result = null;

		try {
			if (conn == null || conn.isClosed()) {
				conn = this.dbobj.getConnection(ConnectionType.SYS);
			}
			ps = conn.prepareStatement(this.cmd.getIconNameMap(), ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			rs = ps.executeQuery();

			while (rs.next()) {
				if (result == null) {
					result = new HashMap<String, String>();
				}
				result.put(rs.getString("icon_map_key").trim(),
						Singleton.getInstance().getLanguage(this.currLang, rs.getString("icon_name").replace(" ", "_")));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (reconn) {
				this.dbctrl.closeConnection(null, ps, conn);
			} else {
				this.dbctrl.closeConnection(null, ps, null);
			}
		}

		return result;
	}

	public String percentFormat(String value, String decs) {
		String result = null;
		try {
			if (DCIString.isNumber(value)) {
				if (DCIString.isNullOrEmpty(decs)) {
					decs = "2";
				}
				result = DCIString.numFormat(String.valueOf(Double.parseDouble(value) * 100), decs);
			}
		} catch (Exception ex) {
			result = value;
		} finally {
			if (result == null) {
				result = value;
			}
		}
		return result;
	}

	public boolean CheckBoxFormat(String value) {
		boolean result = false;
		try {
			if (!DCIString.isNullOrEmpty(value)) {
				value = value.trim();
				if (value.equals("1")) {
					result = true;
				} else if (value.toLowerCase().equals("y") || value.toLowerCase().equals("on")) {
					result = true;
				}
			}
		} catch (Exception ex) {
			result = false;
		}
		return result;
	}

	public boolean sqlCheck(String sql_text, Connection conn) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql_cmd = null;
		boolean sqlok = false;

		try {
			if (conn != null && !conn.isClosed()) {
				sql_cmd = sql_text + " and 1=2 ";

				ps = conn.prepareStatement(sql_cmd, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				rs = ps.executeQuery();
				sqlok = true;
			}
		} catch (Exception ex) {
			sqlok = false;
		} finally {
			this.dbctrl.closeConnection(rs, ps, null);
		}

		return sqlok;
	}
}
