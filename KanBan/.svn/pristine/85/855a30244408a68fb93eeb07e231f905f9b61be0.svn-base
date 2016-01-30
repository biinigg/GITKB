package com.dsc.dci.jweb.patchs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;

import com.dci.jweb.DCIEnums.Database.ConnectionType;
import com.dci.jweb.DataBaseLib.Database.DatabaseObjects;
import com.dci.jweb.PublicLib.DBControl;
import com.dci.jweb.PublicLib.DCIString;
import com.dsc.dci.jweb.pub.Singleton;
import com.dsc.dci.sqlcode.patchs.sqlEKBP002001000002;

public class EKBP002001000002 {

	private sqlEKBP002001000002 cmd;
	private DatabaseObjects dbobj;
	private DBControl dbctrl;

	public EKBP002001000002(boolean execPatch) {
		this.dbobj = DatabaseObjects.getInstance();
		this.cmd = new sqlEKBP002001000002();
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
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.dbctrl.closeConnection(null, ps, conn);
		}
	}
}
