package com.dsc.dci.jweb.patchs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.dci.jweb.DCIEnums.Database.ConnectionType;
import com.dci.jweb.DCIEnums.Database.DBType;
import com.dci.jweb.DataBaseLib.Database.DatabaseObjects;
import com.dci.jweb.PublicLib.DBControl;
import com.dci.jweb.PublicLib.DCIString;
import com.dsc.dci.jweb.pub.Singleton;
import com.dsc.dci.sqlcode.patchs.sqlEKBP002001000001;

public class EKBP002001000001 {
	private sqlEKBP002001000001 cmd;
	private DatabaseObjects dbobj;
	private DBControl dbctrl;

	public EKBP002001000001(boolean execPatch) {
		this.dbobj = DatabaseObjects.getInstance();
		this.cmd = new sqlEKBP002001000001();
		this.dbctrl = new DBControl();
		if (execPatch) {
			executePatch();
		}
	}

	private void executePatch() {
		Connection conn = null;
		PreparedStatement ps = null;
		ArrayList<String> cmds = null;
		try {
			conn = this.dbobj.getConnection(ConnectionType.SYS);
			cmds = this.cmd.getSQLCollection(Singleton.getInstance().getSysDBType());
			System.out.println("Execute patch : "
					+ DCIString.transToDisplayVerFormat(this.getClass().getName().replace("EKBP", "")));
			for (int i = 0; i < cmds.size(); i++) {
				try {
					System.out.print("Patch " + String.valueOf(i + 1));
					ps = conn.prepareStatement(cmds.get(i));
					ps.execute();
					System.out.println("");
				} catch (Exception e) {
					System.err.println("\r\n" + e);
				} finally {
					this.dbctrl.closeConnection(null, ps, null);
				}
			}
			checkKanBanLegend(conn);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.dbctrl.closeConnection(null, ps, conn);
		}
	}

	public void checkKanBanLegend(Connection conn) {
		// DatabaseObjects dbo = DatabaseObjects.getInstance();
		// Connection conn = null;
		PreparedStatement ps = null;
		PreparedStatement ps2 = null;
		PreparedStatement ps3 = null;
		PreparedStatement ps4 = null;
		ResultSet rs = null;
		String[] langtps = new String[] { "CHS", "CHT" };
		String[] icons = null;
		String[] items = null;
		String sql_id = null;
		String legend = null;
		String newlegend = null;
		String sql1 = null;

		try {
			// conn = dbo.getConnection(ConnectionType.SYS);
			if (Singleton.getInstance().getSysDBType() == DBType.Oracle) {
				sql1 = this.cmd.getKanBanHaveLegend_ora();
			} else {
				sql1 = this.cmd.getKanBanHaveLegend();
			}
			ps = conn.prepareStatement(sql1);

			rs = ps.executeQuery();
			ps2 = conn.prepareStatement(this.cmd.deleteAllLegendCusLang());
			ps3 = conn.prepareStatement(this.cmd.addCUSLanguage());
			ps4 = conn.prepareStatement(this.cmd.updKanBanLegend());
			while (rs.next()) {
				legend = rs.getString("legend");
				sql_id = rs.getString("sql_id");
				if (!DCIString.isNullOrEmpty(legend) && legend.indexOf("=") != -1) {
					ps2.setString(1, sql_id + "_legend_%");
					ps2.executeUpdate();

					icons = legend.split(";");
					for (int i = 0; i < icons.length; i++) {
						items = icons[i].split("=");
						if (items.length == 2) {
							for (int j = 0; j < langtps.length; j++) {
								ps3.setString(1, langtps[j]);
								ps3.setString(2, sql_id + "_legend_" + items[0]);
								ps3.setString(3, items[1]);
								ps3.executeUpdate();
							}
						}
						if (i == 0) {
							newlegend = items[0];
						} else {
							newlegend += ";" + items[0];
						}
					}
					ps4.setString(1, newlegend);
					ps4.setString(2, sql_id);
					ps4.executeUpdate();
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			this.dbctrl.closeConnection(rs, ps, conn);
			this.dbctrl.closeConnection(null, ps2, null);
			this.dbctrl.closeConnection(null, ps3, null);
			this.dbctrl.closeConnection(null, ps4, null);
		}
	}
}
