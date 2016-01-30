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
import com.dsc.dci.sqlcode.patchs.sqlEKBP002001000003;

public class EKBP002001000003 {
	private sqlEKBP002001000003 cmd;
	private DatabaseObjects dbobj;
	private DBControl dbctrl;

	public EKBP002001000003(boolean execPatch) {
		this.dbobj = DatabaseObjects.getInstance();
		this.cmd = new sqlEKBP002001000003();
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
					System.err.println("\r\n" + e );
				} finally {
					this.dbctrl.closeConnection(null, ps, null);
				}
			}

			new EKBP002001000001(false).checkKanBanLegend(conn);
			transJoinKey(conn);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.dbctrl.closeConnection(null, ps, conn);
		}
	}

	private void transJoinKey(Connection conn) {
		boolean reconn = false;
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		PreparedStatement ps3 = null;
		PreparedStatement ps4 = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		String sid = null;
		String cid = null;

		try {
			if (conn == null || conn.isClosed()) {
				conn = this.dbobj.getConnection(ConnectionType.SYS);
				reconn = true;
			}
			ps4 = conn.prepareStatement(this.cmd.checkMultiJoin());

			rs2 = ps4.executeQuery();

			if (!rs2.next()) {

				ps1 = conn.prepareStatement(this.cmd.getAllCrossData());

				rs = ps1.executeQuery();
				ps2 = conn.prepareStatement(this.cmd.insJoinKey());
				ps3 = conn.prepareStatement(this.cmd.delJoinKey());
				while (rs.next()) {
					if (!DCIString.isNullOrEmpty(rs.getString("join_key_set1"))
							&& !DCIString.isNullOrEmpty(rs.getString("join_key_set2"))) {
						sid = rs.getString("sql_id");
						cid = rs.getString("cross_id");

						ps3.setString(1, sid);
						ps3.setString(2, cid);
						ps3.executeUpdate();

						ps2.setString(1, sid);
						ps2.setString(2, cid);
						ps2.setString(3, rs.getString("join_key_set1"));
						ps2.setString(4, rs.getString("join_key_set2"));
						ps2.executeUpdate();
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (reconn) {
				this.dbctrl.closeConnection(rs, ps1, conn);
			} else {
				this.dbctrl.closeConnection(rs, ps1, null);
			}
			this.dbctrl.closeConnection(null, ps2, null);
			this.dbctrl.closeConnection(null, ps3, null);
			this.dbctrl.closeConnection(rs2, ps4, null);
		}
	}
}
