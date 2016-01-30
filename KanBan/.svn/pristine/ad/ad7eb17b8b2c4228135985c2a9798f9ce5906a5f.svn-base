package com.dsc.dci.customer.c06100137.jweb.patchs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;

import com.dci.jweb.DCIEnums.Database.ConnectionType;
import com.dci.jweb.DataBaseLib.Database.DatabaseObjects;
import com.dci.jweb.PublicLib.DBControl;

public class CusPatchC06100137 {

	private sqlCusPatchC06100137 cmd;
	private DatabaseObjects dbobj;
	private DBControl dbctrl;

	public CusPatchC06100137(boolean execPatch) {
		this.dbobj = DatabaseObjects.getInstance();
		this.cmd = new sqlCusPatchC06100137();
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
			cmds = this.cmd.getSQLCollection();
			System.out.println("Execute Customer Patch");
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
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.dbctrl.closeConnection(null, ps, conn);
		}
	}
}
