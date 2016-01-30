package com.dsc.dci.jweb.funcs.main.index;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

import com.dci.jweb.DCIEnums.Database.ConnectionType;
import com.dci.jweb.DataBaseLib.Database.DataDatabaseObject;
import com.dci.jweb.DataBaseLib.Database.DatabaseObjects;
import com.dci.jweb.PublicLib.APControl;
import com.dci.jweb.PublicLib.DBControl;
import com.dci.jweb.PublicLib.DCIDate;
import com.dci.jweb.PublicLib.DCIString;
import com.dci.jweb.PublicLib.Register.RegisterObject;
import com.dsc.dci.jweb.pub.Singleton;
import com.dsc.dci.sqlcode.main.sqlIndex;

public class Index {
	private DatabaseObjects dbobj;
	private DBControl dbctrl;
	private sqlIndex cmd;
	private String uid;

	public Index(String uid) {
		this.dbobj = DatabaseObjects.getInstance();
		this.dbctrl = new DBControl();
		this.cmd = new sqlIndex();
		this.uid = uid;
	}

	public HashMap<String, ArrayList<HashMap<String, String>>> initIndex(HttpSession session) {
		HashMap<String, ArrayList<HashMap<String, String>>> initData = null;
		ArrayList<HashMap<String, String>> datas = null;
		ArrayList<HashMap<String, String>> tmpdatas = null;
		HashMap<String, String> tmp = null;
		APControl apc = null;

		apc = new APControl();
		initData = getDBList(apc.getUserInfoFromSession(session, uid, "lang"));

		if (initData == null) {
			initData = new HashMap<String, ArrayList<HashMap<String, String>>>();
		}

		datas = new ArrayList<HashMap<String, String>>();

		tmp = new HashMap<String, String>();
		// tmp.put("uid", apc.getUserInfoFromSession(session, "user_id"));
		tmp.put("uid", this.uid);
		tmp.put("uname", apc.getUserInfoFromSession(session, this.uid, "user_name"));
		tmp.put("gname", apc.getUserInfoFromSession(session, this.uid, "group_name"));
		tmp.put("ltime", apc.getUserInfoFromSession(session, this.uid, "login_time"));
		datas.add(tmp);
		initData.put("userInfo", datas);

		String ptgap = Singleton.getInstance().getSystemConfig("PageTimerGap");

		if (DCIString.isNullOrEmpty(ptgap) || !DCIString.isInteger(ptgap)) {
			ptgap = "900";
		}

		datas = new ArrayList<HashMap<String, String>>();

		tmp = new HashMap<String, String>();
		tmp.put("gap", ptgap);
		datas.add(tmp);
		initData.put("pageTimerGap", datas);

		datas = new ArrayList<HashMap<String, String>>();
		tmpdatas = initData.get("dblistvalue");
		boolean result = false;
		if (tmpdatas == null) {
			result = false;
		} else {
			// result = hasFavor(tmpdatas.get(0).get("value"),
			// apc.getUserInfoFromSession(session, "user_id"));
			result = hasFavor(tmpdatas.get(0).get("value"), this.uid);
		}
		tmp = new HashMap<String, String>();
		tmp.put("hasfavor", String.valueOf(result));
		datas.add(tmp);
		initData.put("hasfavor", datas);

		datas = new ArrayList<HashMap<String, String>>();
		datas.add(hasCusTree(tmpdatas.get(0).get("value"), this.uid));
		initData.put("hascus", datas);

		datas = new ArrayList<HashMap<String, String>>();
		tmp = new HashMap<String, String>();
		tmp.put("marquee_refresh_gap", Singleton.getInstance().getSystemConfig("MarqueeGap"));
		tmp.put("marquee_type", Singleton.getInstance().getSystemConfig("MarqueeType"));
		datas.add(tmp);
		initData.put("sysMarquee", datas);

		return initData;
	}

	private boolean hasFavor(String connid, String uid) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = this.dbobj.getConnection(ConnectionType.SYS);
			ps = conn.prepareStatement(this.cmd.hasFavor());
			ps.setString(1, uid);
			ps.setString(2, connid);
			rs = ps.executeQuery();
			if (rs.next()) {
				result = true;
			} else {
				result = false;
			}

		} catch (Exception ex) {
			result = false;
			ex.printStackTrace();
		} finally {
			this.dbctrl.closeConnection(rs, ps, conn);
		}
		return result;
	}

	private HashMap<String, String> hasCusTree(String connid, String uid) {
		HashMap<String, String> result = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			result = new HashMap<String, String>();
			result.put("hasdefcus", "false");
			result.put("hasfavcus", "false");

			conn = this.dbobj.getConnection(ConnectionType.SYS);
			ps = conn.prepareStatement(this.cmd.hasDefCus());
			ps.setString(1, uid);
			ps.setString(2, connid);
			ps.setString(3, uid);
			ps.setString(4, connid);
			rs = ps.executeQuery();

			while (rs.next()) {
				if (rs.getString("id").equals("1")) {
					result.put("hasdefcus", "true");
				} else if (rs.getString("id").equals("2")) {
					result.put("hasfavcus", "true");
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			this.dbctrl.closeConnection(rs, ps, conn);
		}
		return result;
	}

	public HashMap<String, ArrayList<HashMap<String, String>>> getDBList(String lang) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		HashMap<String, ArrayList<HashMap<String, String>>> initData = null;
		ArrayList<HashMap<String, String>> datas = null;
		HashMap<String, String> tmp = null;

		try {
			conn = this.dbobj.getConnection(ConnectionType.SYS);
			ps = conn.prepareStatement(this.cmd.getDBList());
			ps.setString(1, this.uid);
			ps.setString(2, this.uid);
			rs = ps.executeQuery();
			datas = new ArrayList<HashMap<String, String>>();
			while (rs.next()) {
				tmp = new HashMap<String, String>();
				tmp.put("label", Singleton.getInstance().getLanguage(lang, rs.getString("conn_name")));
				tmp.put("value", rs.getString("conn_id"));
				datas.add(tmp);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			this.dbctrl.closeConnection(rs, ps, conn);
		}

		if (initData == null) {
			initData = new HashMap<String, ArrayList<HashMap<String, String>>>();
		}

		initData.put("dblistvalue", datas);
		return initData;
	}

	public HashMap<String, ArrayList<HashMap<String, String>>> getTreeNode(String connid, String langtp, String node,
			String type) {
		String rootNodeName = "FuncTreeRoot";
		HashMap<String, ArrayList<HashMap<String, String>>> treeData = null;
		HashMap<String, ArrayList<HashMap<String, String>>> treeNode = null;
		ArrayList<HashMap<String, String>> datas = null;
		HashMap<String, String> tmp = null;
		HashMap<String, String> existFuncs = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			if (langtp == null || langtp.equals("")) {
				langtp = Singleton.getInstance().getDeflang();
			}

			conn = this.dbobj.getConnection(ConnectionType.SYS);
			if (type.equals("func")) {
				ps = conn.prepareStatement(this.cmd.getUserMenu());
			} else {
				ps = conn.prepareStatement(this.cmd.getFavoriteMenu());
			}

			ps.setString(1, this.uid);
			ps.setString(2, connid);
			ps.setString(3, this.uid);
			ps.setString(4, connid);
			ps.setString(5, langtp);
			rs = ps.executeQuery();

			treeNode = new HashMap<String, ArrayList<HashMap<String, String>>>();
			datas = new ArrayList<HashMap<String, String>>();
			existFuncs = new HashMap<String, String>();
			while (rs.next()) {
				if (rs.getString("is_program").equals("1")) {
					if (DCIString.isNullOrEmpty(rs.getString("package"))) {
						continue;
					} else if (!rs.getString("package").equals("SYS")) {
						if (!RegisterObject.getInstance().isPackageCanUse(rs.getString("package"))) {
							continue;
						}
					}
				}
				tmp = new HashMap<String, String>();
				tmp.put("id", rs.getString("func_id"));
				if (rs.getString("is_program").equals("1")) {
					tmp.put("leaf", "true");
				} else {
					tmp.put("leaf", "false");
				}
				if (rs.getObject("parent_id") == null || rs.getString("parent_id").equals("")) {
					tmp.put("parent", rootNodeName);
				} else {
					tmp.put("parent", rs.getString("parent_id"));
				}
				tmp.put("text", rs.getString("lang_value"));
				tmp.put("lang_key", rs.getString("lang_key"));
				tmp.put("url", rs.getString("func_url"));
				tmp.put("func_package", rs.getString("package"));
				tmp.put("can_edit", rs.getString("can_edit"));
				tmp.put("init_load_expanded", "0");
				// if (rs.getString("func_id").equals("TreeLogout")) {
				// existFuncs.put(rs.getString("func_id"),
				// rs.getString("sort_id"));
				// } else {
				existFuncs.put(rs.getString("func_id"), "");
				// }
				datas.add(tmp);

			}
			if (type.equals("func")) {
				newFuncCheck(conn, connid, datas, existFuncs, langtp);
			}
			buildNodeData(datas, treeNode, rootNodeName, 0);

			treeData = new HashMap<String, ArrayList<HashMap<String, String>>>();
			treeData.put("datas", treeNode.get(node));
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			this.dbctrl.closeConnection(rs, ps, conn);
		}
		// System.out.println(node + "---" +treeData);
		return treeData;
	}

	private ArrayList<HashMap<String, String>> newFuncCheck(Connection conn, String connid,
			ArrayList<HashMap<String, String>> datas, HashMap<String, String> existFuncs, String langtp) {
		HashMap<String, String> check = null;
		boolean reconn = false;
		PreparedStatement ps = null;
		PreparedStatement ps2 = null;
		ResultSet rs = null;
		HashMap<String, String> tmp = null;
		int sortid = 90000;
		Singleton s = Singleton.getInstance();
		if (datas != null) {
			check = hasCusTree(connid, this.uid);

			if (check != null && check.containsKey("hasdefcus")) {
				if (Boolean.valueOf(check.get("hasdefcus"))) {
					try {
						if (conn == null || conn.isClosed()) {
							conn = this.dbobj.getConnection(ConnectionType.SYS);
							reconn = true;
						}

						// if (existFuncs.containsKey("TreeLogout")) {
						// sortid =
						// Integer.parseInt(existFuncs.get("TreeLogout")) - 100;
						// }

						ps = conn.prepareStatement(this.cmd.getAllFuncs());

						ps.setString(1, this.uid);
						ps.setString(2, connid);
						rs = ps.executeQuery();
						ps2 = conn.prepareStatement(this.cmd.addCusMenu());
						while (rs.next()) {
							if (!existFuncs.containsKey(rs.getString("func_id"))) {
								System.out.println(rs.getString("func_id"));
								tmp = new HashMap<String, String>();
								tmp.put("id", rs.getString("func_id"));
								tmp.put("leaf", "true");
								tmp.put("parent", "FuncTreeRoot");
								tmp.put("text", s.getLanguage(langtp, rs.getString("lang_key")));
								tmp.put("lang_key", rs.getString("lang_key"));
								tmp.put("url", rs.getString("func_url"));
								tmp.put("func_package", rs.getString("package"));
								tmp.put("can_edit", rs.getString("can_edit"));
								tmp.put("init_load_expanded", "0");
								existFuncs.put(rs.getString("func_id"), "");
								datas.add(datas.size() - 1, tmp);
								ps2.setString(1, this.uid);
								ps2.setString(2, connid);
								ps2.setString(3, rs.getString("func_id"));
								ps2.setString(4, "");
								ps2.setString(5, rs.getString("lang_key"));
								ps2.setString(6, String.valueOf(sortid));
								ps2.setString(7, "1");
								ps2.executeUpdate();
								sortid++;
							}
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
				}
			}
		}
		return datas;
	}

	private void buildNodeData(ArrayList<HashMap<String, String>> datas,
			HashMap<String, ArrayList<HashMap<String, String>>> funcTree, String node, int level) {
		if (datas != null && datas.size() != 0) {
			ArrayList<HashMap<String, String>> tmp = null;
			ArrayList<HashMap<String, String>> childs = null;
			HashMap<String, String> tmpBean = null;
			if (funcTree == null) {
				funcTree = new HashMap<String, ArrayList<HashMap<String, String>>>();
			}
			tmp = dataSelect(datas, node);
			for (int i = 0; i < tmp.size(); i++) {
				tmpBean = (HashMap<String, String>) tmp.get(i);
				tmpBean.put("level", String.valueOf(level));
				childs = dataSelect(datas, tmpBean.get("id"));
				if (childs.size() != 0) {
					buildNodeData(datas, funcTree, tmpBean.get("id"), level++);
				}
			}
			funcTree.put(node, tmp);
		}
	}

	private ArrayList<HashMap<String, String>> dataSelect(ArrayList<HashMap<String, String>> datas, String id) {
		ArrayList<HashMap<String, String>> tmp = new ArrayList<HashMap<String, String>>(0);
		if (datas != null && datas.size() != 0) {
			for (int i = 0; i < datas.size(); i++) {
				if (((HashMap<String, String>) datas.get(i)).get("parent").equals(id)) {
					tmp.add(datas.get(i));
				}
			}
		}
		return tmp;
	}

	public boolean saveTreeDir(String connid, String params, String type) {
		boolean success = false;
		String[] funcs = null;
		String[] nodes = null;
		Connection conn = null;
		PreparedStatement ps = null;
		String sql = "";

		try {
			funcs = params.split(";");

			conn = this.dbobj.getConnection(ConnectionType.SYS);
			if (!type.equals("addfavor")) {
				if (type.equals("func")) {
					sql = this.cmd.delCusMenu();
				} else {
					sql = this.cmd.delFavoriteMenu();
				}
				ps = conn.prepareStatement(sql);
				ps.setString(1, this.uid);
				ps.setString(2, connid);
				ps.executeUpdate();
				this.dbctrl.closeConnection(null, ps, null);
			}
			if (!DCIString.isNullOrEmpty(params)) {
				if (type.equals("func")) {
					sql = this.cmd.addCusMenu();
				} else {
					sql = this.cmd.addFavoriteMenu();
				}
				ps = conn.prepareStatement(sql);
				for (int i = 0; i < funcs.length; i++) {
					nodes = funcs[i].split(",");
					ps.setString(1, this.uid);
					ps.setString(2, connid);
					ps.setString(3, nodes[0]);
					ps.setString(4, nodes[1]);
					ps.setString(5, nodes[2]);
					if (nodes[0].equals("TreeLogout")) {
						ps.setString(6, "95000");
					} else {
						ps.setString(6, nodes[3]);
					}
					if (nodes[4].toLowerCase().equals("true")) {
						ps.setString(7, "1");
					} else {
						ps.setString(7, "0");
					}
					ps.executeUpdate();
				}
			}
			success = true;

		} catch (Exception ex) {
			success = false;
			ex.printStackTrace();
		} finally {
			this.dbctrl.closeConnection(null, ps, conn);
		}

		return success;
	}

	public boolean saveCusLang(String lang, String lang_key, String lang_value) {
		boolean success = false;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = this.dbobj.getConnection(ConnectionType.SYS);
			ps = conn.prepareStatement(this.cmd.delCusLang());
			ps.setString(1, lang);
			ps.setString(2, lang_key);
			ps.executeUpdate();
			this.dbctrl.closeConnection(rs, ps, null);

			ps = conn.prepareStatement(this.cmd.addCusLang());
			ps.setString(1, lang);
			ps.setString(2, lang_key);
			ps.setString(3, lang_value);
			ps.executeUpdate();
			success = true;

		} catch (Exception ex) {
			success = false;
			ex.printStackTrace();
		} finally {
			this.dbctrl.closeConnection(rs, ps, conn);
		}

		return success;
	}

	public boolean cleanCusTree(String connid, String type) {
		boolean success = false;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = this.dbobj.getConnection(ConnectionType.SYS);
			if (type.equals("func")) {
				ps = conn.prepareStatement(this.cmd.delCusMenu());
			} else {
				ps = conn.prepareStatement(this.cmd.delFavoriteMenu());
			}
			ps.setString(1, this.uid);
			ps.setString(2, connid);
			ps.executeUpdate();
			success = true;

		} catch (Exception ex) {
			success = false;
			ex.printStackTrace();
		} finally {
			this.dbctrl.closeConnection(rs, ps, conn);
		}

		return success;
	}

	public HashMap<String, ArrayList<HashMap<String, String>>> checkKanban(String connid, String func_id) {
		HashMap<String, ArrayList<HashMap<String, String>>> datas = null;
		ArrayList<HashMap<String, String>> data = null;
		HashMap<String, String> tmp = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = this.dbobj.getConnection(ConnectionType.SYS);
			ps = conn.prepareStatement(this.cmd.getSqlConnMapping());

			ps.setString(1, func_id);
			ps.setString(2, connid);
			rs = ps.executeQuery();

			datas = new HashMap<String, ArrayList<HashMap<String, String>>>();
			data = new ArrayList<HashMap<String, String>>();
			tmp = new HashMap<String, String>();

			tmp.put("success", String.valueOf(rs.next()));
			data.add(tmp);
			datas.put("result", data);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			this.dbctrl.closeConnection(rs, ps, conn);
		}
		// System.out.println(node + "---" +treeData);
		return datas;
	}

	public HashMap<String, ArrayList<HashMap<String, String>>> getPageTaskGap() {
		HashMap<String, ArrayList<HashMap<String, String>>> datas = null;
		ArrayList<HashMap<String, String>> data = null;
		HashMap<String, String> tmp = null;

		try {
			datas = new HashMap<String, ArrayList<HashMap<String, String>>>();
			data = new ArrayList<HashMap<String, String>>();
			tmp = new HashMap<String, String>();

			tmp.put("gap", Singleton.getInstance().getSystemConfig("PageTimerGap"));
			data.add(tmp);
			datas.put("result", data);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		// System.out.println(node + "---" +treeData);
		return datas;
	}

	public HashMap<String, ArrayList<HashMap<String, String>>> getMarqueeData() {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		HashMap<String, ArrayList<HashMap<String, String>>> datas = null;
		ArrayList<HashMap<String, String>> marquee = null;
		HashMap<String, String> tmp = null;

		try {
			conn = this.dbobj.getConnection(ConnectionType.SYS);
			Calendar c = Calendar.getInstance();
			String currtime = DCIDate.parseString(c.getTime(), "yyyyMMddHHmmss");
			ps = conn.prepareStatement(this.cmd.getMarqueeData());
			ps.setString(1, currtime);
			ps.setString(2, currtime);
			rs = ps.executeQuery();
			marquee = new ArrayList<HashMap<String, String>>();

			while (rs.next()) {
				if (rs.getString("marquee_type").equals("3")) {
					tmp = new HashMap<String, String>();
					tmp.put("message", rs.getString("message"));
					marquee.add(tmp);
				} else {
					getAdvanceMarquee(rs.getString("conn_id"), rs.getString("message"), marquee);
				}
			}

			datas = new HashMap<String, ArrayList<HashMap<String, String>>>();
			datas.put("marquee", marquee);

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			this.dbctrl.closeConnection(rs, ps, conn);
		}

		return datas;
	}

	private void getAdvanceMarquee(String conn_id, String sql, ArrayList<HashMap<String, String>> marquee) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		HashMap<String, String> tmp = null;
		ResultSetMetaData rsmd = null;
		String tmpstr = null;

		try {
			if (DCIString.isNullOrEmpty(conn_id)) {
				conn = this.dbobj.getConnection(ConnectionType.SYS);
			} else {
				conn = DataDatabaseObject.getInstance().getConnection(conn_id);
			}
			if (conn != null && !conn.isClosed()) {
				ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				rs = ps.executeQuery();

				if (marquee == null) {
					marquee = new ArrayList<HashMap<String, String>>();
				}

				if (rs.next()) {
					if (rsmd == null) {
						rsmd = rs.getMetaData();
					}
					rs.beforeFirst();
					while (rs.next()) {
						for (int i = 1; i <= rsmd.getColumnCount(); i++) {
							if (i == 1) {
								tmpstr = rs.getString(rsmd.getColumnName(i)).trim();
							} else {
								tmpstr += "-" + rs.getString(rsmd.getColumnName(i)).trim();
							}
						}
						tmp = new HashMap<String, String>();
						tmp.put("message", tmpstr);
						marquee.add(tmp);
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			this.dbctrl.closeConnection(rs, ps, conn);
		}
	}

	public HashMap<String, ArrayList<HashMap<String, String>>> checkCusTree(String connid, String uid) {
		HashMap<String, ArrayList<HashMap<String, String>>> datas = null;
		ArrayList<HashMap<String, String>> data = null;

		try {
			datas = new HashMap<String, ArrayList<HashMap<String, String>>>();
			data = new ArrayList<HashMap<String, String>>();
			data.add(hasCusTree(connid, uid));
			datas.put("result", data);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		// System.out.println(node + "---" +treeData);
		return datas;
	}
}
