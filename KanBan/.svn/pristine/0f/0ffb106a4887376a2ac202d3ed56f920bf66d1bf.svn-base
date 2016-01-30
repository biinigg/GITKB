package com.dsc.dci.jweb.patchs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;

import com.dci.jweb.DCIEnums.Database.ConnectionType;
import com.dci.jweb.DataBaseLib.Database.DatabaseObjects;
import com.dci.jweb.PublicLib.DBControl;
import com.dci.jweb.PublicLib.DCIString;
import com.dsc.dci.jweb.pub.Singleton;
import com.dsc.dci.sqlcode.patchs.sqlEKBP002001000005;
/*
 * 1.增加table Func_CUS記錄客製程式路徑
 * 2.優化介面顯示
 * 3.修正跨資料庫無資料錯誤
 * 4.修正多次重新整理無法正常使用錯誤
 * 5.修正看板進階查詢燈號條件設定異常
 * 6.修正[建立鍊結]檢核異常
 * 7.修正[看板設定]用看板名稱無法查詢問提
 * 
 * 
*/
public class EKBP002001000005 {

	private sqlEKBP002001000005 cmd;
	private DatabaseObjects dbobj;
	private DBControl dbctrl;

	public EKBP002001000005(boolean execPatch) {
		this.dbobj = DatabaseObjects.getInstance();
		this.cmd = new sqlEKBP002001000005();
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
					// System.out.print(cmds.get(i));
					System.out.print("Patch " + String.valueOf(i + 1));
					ps = conn.prepareStatement(cmds.get(i));
					ps.execute();
					System.out.println("");
				} catch (Exception e) {
					// e.printStackTrace();
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
