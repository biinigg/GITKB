package com.dsc.dci.jweb.funcs.configs.iconconfig;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;

import com.dci.jweb.DCIBeans.website.FileUploadBean;
import com.dci.jweb.DCIEnums.Database.ConnectionType;
import com.dci.jweb.DataBaseLib.Database.DatabaseObjects;
import com.dci.jweb.PublicLib.ConfigControl;
import com.dci.jweb.PublicLib.DBControl;
import com.dci.jweb.PublicLib.DCIString;
import com.dsc.dci.jweb.pub.APConstants;
import com.dsc.dci.jweb.pub.APPubMethods;
import com.dsc.dci.jweb.pub.Singleton;
import com.dsc.dci.sqlcode.configs.sqlIconConfig;

public class IconConfig {

	private sqlIconConfig cmd;
	private DatabaseObjects dbobj;
	private DBControl dbctrl;

	public IconConfig() {
		this.dbobj = DatabaseObjects.getInstance();
		this.cmd = new sqlIconConfig();
		this.dbctrl = new DBControl();
	}

	public HashMap<String, Object> getAllData(String strpage, String strpagesize, String filter, String lang) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		HashMap<String, Object> datas = null;
		ArrayList<HashMap<String, Object>> items = null;
		HashMap<String, Object> tmp = null;
		Singleton s = Singleton.getInstance();
		int cnt = 0;
		int page = 0;
		int pagesize = 0;
		int totalRow = 0;
		try {
			conn = this.dbobj.getConnection(ConnectionType.SYS);
			ps = conn.prepareStatement(this.cmd.getAllData(filter), ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			ps.setString(1, lang);
			rs = ps.executeQuery();
			items = new ArrayList<HashMap<String, Object>>();
			if (DCIString.isNullOrEmpty(strpage)) {
				page = 1;
			} else {
				page = Integer.parseInt(strpage);
			}
			if (DCIString.isNullOrEmpty(strpagesize)) {
				pagesize = APConstants.getDefaultpagesize();
			} else {
				pagesize = Integer.parseInt(strpagesize);
			}
			if (rs.next()) {
				rs.last();
				totalRow = rs.getRow();
				rs.beforeFirst();
				if ((page - 1) * pagesize <= totalRow) {
					if ((page - 1) * pagesize != 0) {
						rs.absolute((page - 1) * pagesize);
					}
					cnt = 1;
					while (rs.next()) {
						tmp = new HashMap<String, Object>();
						tmp.put("id", cnt);
						tmp.put("icon_id", rs.getString("icon_id"));
						tmp.put("icon_name", rs.getString("icon_name"));
						tmp.put("icon_show_name", s.getLanguage(lang, rs.getString("icon_name")));
						tmp.put("icon_map_key", rs.getString("icon_map_key"));
						tmp.put("icon_path", rs.getString("icon_path"));
						tmp.put("icon_type", rs.getString("icon_type"));
						tmp.put("icon_id_show", DCIString.Base64Encode(rs.getString("icon_id")));
						tmp.put("icon_names",
								s.getLanguage("CHS", rs.getString("icon_name")) + "$"
										+ s.getLanguage("CHT", rs.getString("icon_name")));
						items.add(tmp);
						cnt++;
						if (cnt > pagesize) {
							break;
						}
					}
				}
			} else {
				totalRow = 0;
			}

			datas = new HashMap<String, Object>();
			datas.put("items", items);
			datas.put("total", totalRow);

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			this.dbctrl.closeConnection(rs, ps, conn);
		}

		// System.out.println("execute getAllUserInfo");

		return datas;
	}

	public HashMap<String, Object> getQueryColumns(String lang) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		HashMap<String, Object> datas = null;
		ArrayList<HashMap<String, Object>> cols = null;
		HashMap<String, Object> tmp = null;

		try {
			conn = this.dbobj.getConnection(ConnectionType.SYS);
			ps = conn.prepareStatement(this.cmd.getAllData(" and 1=2 "), ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			ps.setString(1, lang);
			rs = ps.executeQuery();
			if (rs != null) {
				ResultSetMetaData rsmd = rs.getMetaData();
				cols = new ArrayList<HashMap<String, Object>>();
				for (int i = 1; i <= rsmd.getColumnCount(); i++) {
					if (!rsmd.getColumnName(i).toLowerCase().equals("icon_type")
							&& !rsmd.getColumnName(i).toLowerCase().equals("icon_path")
							&& !rsmd.getColumnName(i).toLowerCase().equals("lang_value")) {
						tmp = new HashMap<String, Object>();
						tmp.put("value", rsmd.getColumnName(i).toLowerCase());
						tmp.put("label", Singleton.getInstance().getLanguage(lang, rsmd.getColumnName(i).toLowerCase()));
						cols.add(tmp);
					}
				}
			}

			datas = new HashMap<String, Object>();
			datas.put("cols", cols);

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			this.dbctrl.closeConnection(rs, ps, conn);
		}

		return datas;
	}

	public HashMap<String, Object> getIntiData(String lang) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		HashMap<String, Object> datas = null;
		ArrayList<HashMap<String, Object>> groups = null;
		HashMap<String, Object> tmp = null;

		try {
			conn = this.dbobj.getConnection(ConnectionType.SYS);
			ps = conn.prepareStatement(this.cmd.getAllGroups(), ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			rs = ps.executeQuery();
			groups = new ArrayList<HashMap<String, Object>>();

			while (rs.next()) {
				tmp = new HashMap<String, Object>();
				tmp.put("value", rs.getString("group_id"));
				if (rs.getInt("visible") == 1) {
					tmp.put("label",
							rs.getString("group_name") + "(" + Singleton.getInstance().getLanguage(lang, "enable")
									+ ")");
				} else {
					tmp.put("label",
							rs.getString("group_name") + "(" + Singleton.getInstance().getLanguage(lang, "disable")
									+ ")");
				}
				groups.add(tmp);
			}

			datas = new HashMap<String, Object>();
			datas.put("groups", groups);

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			this.dbctrl.closeConnection(rs, ps, conn);
		}

		return datas;
	}

	public HashMap<String, Object> saveData(String formDatas, String mode, FileUploadBean file, String lang) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		HashMap<String, Object> datas = null;
		HashMap<String, Object> result = null;
		HashMap<String, Object> resultdata = null;
		String sql = null;
		boolean boolResult = false;
		String resultMsg = "";
		String newid = null;
		String filePath = null;
		String filename = null;
		String mapkey = null;
		String iconname = null;
		String[][] langValue = null;
		String[] langtmp = null;

		try {
			datas = DCIString.jsonTranToObjMap(formDatas);// new
															// ObjectMapper().readValue(formDatas,
															// HashMap.class);
			conn = this.dbobj.getConnection(ConnectionType.SYS);

			if (mode.equals("Add")) {
				sql = this.cmd.addData();
				newid = getMaxID(conn);
				if (file == null || file.getFile() == null) {
					throw new Exception("no file upload");
				} else {
					filename = file.getFile().getOriginalFilename();
					filename = newid + filename.substring(filename.lastIndexOf(46));
					filePath = new ConfigControl(false, null).getConfigXMLPath() + File.separator
							+ APConstants.getUploadicondir() + File.separator + filename;
					if (new APPubMethods().saveUploadFile(filename, file)) {
						throw new Exception("save file fail");
					}
				}
				mapkey = getMaxKey(conn);
			} else {
				sql = this.cmd.updateData();
				newid = datas.get("icon_id").toString();
				filePath = datas.get("icon_path").toString();
				mapkey = datas.get("icon_map_key").toString();
			}

			if (datas.get("icon_name") == null || DCIString.isNullOrEmpty(datas.get("icon_name").toString())) {
				iconname = newid;
			} else {
				iconname = datas.get("icon_name").toString();
			}

			ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			ps.setString(1, iconname);
			ps.setString(2, mapkey);
			ps.setString(3, filePath);
			ps.setString(4, datas.get("icon_type").toString());
			ps.setString(5, newid);
			ps.executeUpdate();

			langValue = new String[2][];

			if (datas.get("icon_names") == null || DCIString.isNullOrEmpty(datas.get("icon_names").toString())
					|| datas.get("icon_names").toString().indexOf("$") == -1
					|| datas.get("icon_names").toString().length() == 1) {
				langValue[0] = new String[] { "CHS", iconname, "" };
				langValue[1] = new String[] { "CHT", iconname, "" };
			} else {
				langtmp = datas.get("icon_names").toString().split("\\$");
				if (langtmp.length == 2) {
					langValue[0] = new String[] { "CHS", iconname, datas.get("icon_names").toString().split("\\$")[0] };
					langValue[1] = new String[] { "CHT", iconname, datas.get("icon_names").toString().split("\\$")[1] };
				} else {
					if (datas.get("icon_names").toString().startsWith("$")) {
						langValue[0] = new String[] { "CHS", iconname, "" };
						langValue[1] = new String[] { "CHT", iconname,
								datas.get("icon_names").toString().split("\\$")[1] };
					} else {
						langValue[0] = new String[] { "CHS", iconname,
								datas.get("icon_names").toString().split("\\$")[0] };
						langValue[1] = new String[] { "CHT", iconname, "" };
					}
				}
			}

			saveLangs(conn, langValue);
			resultdata = getAllData("1", "1", " and icon_id = '" + newid + "'", lang);

			boolResult = true;
		} catch (Exception ex) {
			ex.printStackTrace();
			boolResult = false;
			resultMsg = ex.getMessage();
		} finally {
			this.dbctrl.closeConnection(rs, ps, conn);
			result = new HashMap<String, Object>();
			result.put("success", boolResult);
			result.put("resultData", resultdata);
			result.put("errorMessage", resultMsg);
		}
		return result;
	}

	public HashMap<String, Object> deleteData(String formdatas) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		HashMap<String, Object> datas = null;
		HashMap<String, Object> result = null;
		String iconname = null;
		String[][] langValue = null;
		boolean boolResult = false;
		String resultMsg = "";
		try {
			datas = DCIString.jsonTranToObjMap(formdatas);// new
															// ObjectMapper().readValue(formdatas,
															// HashMap.class);

			if (!new APPubMethods().deleteUploadFile(datas.get("icon_path").toString())) {
				throw new Exception("delete file fail");
			}

			conn = this.dbobj.getConnection(ConnectionType.SYS);
			ps = conn.prepareStatement(this.cmd.deleteData(), ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);

			ps.setString(1, datas.get("icon_id").toString());
			ps.executeUpdate();

			if (datas.get("icon_name") == null || DCIString.isNullOrEmpty(datas.get("icon_name").toString())) {
				iconname = datas.get("icon_id").toString();
			} else {
				iconname = datas.get("icon_name").toString();
			}

			langValue = new String[2][];

			langValue[0] = new String[] { "CHS", iconname, "" };
			langValue[1] = new String[] { "CHT", iconname, "" };

			saveLangs(conn, langValue);

			boolResult = true;
		} catch (Exception ex) {
			ex.printStackTrace();
			boolResult = false;
			resultMsg = ex.getMessage();
		} finally {
			this.dbctrl.closeConnection(rs, ps, conn);
			result = new HashMap<String, Object>();
			result.put("success", boolResult);
			result.put("errorMessage", resultMsg);
		}
		return result;
	}

	private String getMaxKey(Connection conn) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		String key = null;
		boolean reconn = false;
		try {
			if (conn == null || conn.isClosed()) {
				conn = this.dbobj.getConnection(ConnectionType.SYS);
				reconn = true;
			}
			ps = conn.prepareStatement(this.cmd.maxKey(Singleton.getInstance().getSysDBType()));

			rs = ps.executeQuery();
			if (rs.next()) {
				if (DCIString.isNullOrEmpty(rs.getString(1)) || !DCIString.isInteger(rs.getString(1))) {
					key = "1";
				} else {
					key = String.valueOf(rs.getInt(1) + 1);
				}
			} else {
				key = "1";
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			key = "";
		} finally {
			if (reconn) {
				this.dbctrl.closeConnection(rs, ps, conn);
			} else {
				this.dbctrl.closeConnection(rs, ps, null);
			}
		}
		return key;
	}

	private String getMaxID(Connection conn) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		String id = null;
		boolean reconn = false;
		try {
			if (conn == null || conn.isClosed()) {
				conn = this.dbobj.getConnection(ConnectionType.SYS);
				reconn = true;
			}
			ps = conn.prepareStatement(this.cmd.maxID(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

			rs = ps.executeQuery();
			if (rs.next()) {
				id = rs.getString(1);
				if (DCIString.isNullOrEmpty(id)) {
					id = "I001";
				} else {
					id = id.substring(0, 1)
							+ DCIString.strFix(String.valueOf(Integer.parseInt(id.substring(1)) + 1), 3, true, "0");
				}
			} else {
				id = "I001";
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			id = "";
		} finally {
			if (reconn) {
				this.dbctrl.closeConnection(rs, ps, conn);
			} else {
				this.dbctrl.closeConnection(rs, ps, null);
			}
		}
		return id;
	}

	public HashMap<String, Object> mapkeyCheck(String id, String key, String lang) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		HashMap<String, Object> result = null;

		try {

			conn = this.dbobj.getConnection(ConnectionType.SYS);
			ps = conn.prepareStatement(this.cmd.iconMapKey(), ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			ps.setString(1, id);
			ps.setString(2, key);
			rs = ps.executeQuery();
			result = new HashMap<String, Object>();
			if (rs.next()) {
				result.put("success", false);
				result.put("msg", Singleton.getInstance().getLanguage(lang, "icon_map_key_exist"));
			} else {
				result.put("success", true);
				result.put("msg", "");
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			if (result == null) {
				result = new HashMap<String, Object>();
			}
			result.put("success", false);
			result.put("msg", ex.getMessage());
		} finally {
			this.dbctrl.closeConnection(rs, ps, conn);
		}

		return result;
	}

	private void saveLangs(Connection conn, String[][] langs) throws Exception {
		PreparedStatement ps = null;
		boolean reconn = false;

		try {
			if (langs != null && langs.length > 0) {
				if (conn == null || conn.isClosed()) {
					conn = this.dbobj.getConnection(ConnectionType.SYS);
					reconn = true;
				}
				ps = conn.prepareStatement(this.cmd.deleteCusLang(), ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
				ps.setString(1, langs[0][1]);
				ps.executeUpdate();

				this.dbctrl.closeConnection(null, ps, null);

				ps = conn.prepareStatement(this.cmd.addCusLang(), ResultSet.TYPE_SCROLL_INSENSITIVE,
						ResultSet.CONCUR_READ_ONLY);
				for (int i = 0; i < langs.length; i++) {
					if (!DCIString.isNullOrEmpty(langs[i][2])) {
						ps.setString(1, langs[i][0]);
						ps.setString(2, langs[i][1]);
						ps.setString(3, langs[i][2]);
						ps.addBatch();
					}
				}

				ps.executeBatch();

				for (int i = 0; i < langs.length; i++) {
					if (DCIString.isNullOrEmpty(langs[i][2])) {
						Singleton.getInstance().removeMultiLanguage(langs[i][0], langs[i][1]);
					} else {
						Singleton.getInstance().addMultiLanguage(langs[i][0], langs[i][1], langs[i][2]);
					}
				}

			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		} finally {
			if (reconn) {
				this.dbctrl.closeConnection(null, ps, conn);
			} else {
				this.dbctrl.closeConnection(null, ps, null);
			}
		}
	}
}
