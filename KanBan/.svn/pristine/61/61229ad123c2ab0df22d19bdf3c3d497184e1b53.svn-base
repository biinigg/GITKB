package com.dsc.dci.jweb.patchs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;

import com.dci.jweb.DCIEnums.Database.ConnectionType;
import com.dci.jweb.DataBaseLib.Database.DatabaseObjects;
import com.dci.jweb.PublicLib.DBControl;
import com.dci.jweb.PublicLib.DCIString;
import com.dsc.dci.jweb.pub.Singleton;
import com.dsc.dci.sqlcode.patchs.sqlEKBP002001001002;

/*
 * 1.看板查詢面板縮合或展開記錄
 * 2.看板偶數列、奇數列顏色設定
 * 3.調整鍊結看板JSP導向位址機制，使其可自訂JSP位址
 * 4.修正客製路徑取得SQL(sqlEditor)
 * 5.權限複製按鈕修正tip未出現問題
 * 6.修正看板數值與金額欄位排序類型
 * 7.樹狀目錄新增功能，自動顯示於根目錄
 * 8.看板request timeout調整為300秒
*/
public class EKBP002001001002 {
	private sqlEKBP002001001002 cmd;
	private DatabaseObjects dbobj;
	private DBControl dbctrl;

	public EKBP002001001002(boolean execPatch) {
		this.dbobj = DatabaseObjects.getInstance();
		this.cmd = new sqlEKBP002001001002();
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
