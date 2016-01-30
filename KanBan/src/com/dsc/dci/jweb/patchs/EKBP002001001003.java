package com.dsc.dci.jweb.patchs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;

import com.dci.jweb.DCIEnums.Database.ConnectionType;
import com.dci.jweb.DataBaseLib.Database.DatabaseObjects;
import com.dci.jweb.PublicLib.DBControl;
import com.dci.jweb.PublicLib.DCIString;
import com.dsc.dci.jweb.pub.Singleton;
import com.dsc.dci.sqlcode.patchs.sqlEKBP002001001003;

/*
 * 1.Firefox 關閉後不會自動登出  6H
 * 2.修正單身於編輯模式下，重新查詢單頭時不會出現警告訊息  6H
 * 3.修正看版編輯單身在有關連看板的情況下，如未修改或進入關聯看板畫面而修改該欄位的其他欄位，會將關聯資料刪除。 1H
 * 4.0026992: WPPQ01的開工風險,完工風險判斷式需修改 0.5H
 * 5.0026998: WPPQ07 預交訂單的生產工單資料開窗問題 1H
 * 6.調整電子看板設定在無編輯權限時，不可進行複製。  1H
 * 7.WPPQ06 訂單出貨排程動態看板開窗問題  0.5H
 * 8.修正LKB開窗異常 1H
 * 9.WPP加入excel匯出功能  44H
 * 10.LKB、WPP調整為已多語系顯示 16H///11月
 * 11.將目前版本寫入資料庫中 2H
 * 12.0027699: WPP查詢報表修改 10H
 */
public class EKBP002001001003 {
	private sqlEKBP002001001003 cmd;
	private DatabaseObjects dbobj;
	private DBControl dbctrl;

	public EKBP002001001003(boolean execPatch) {
		this.dbobj = DatabaseObjects.getInstance();
		this.cmd = new sqlEKBP002001001003();
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

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.dbctrl.closeConnection(null, ps, conn);
		}
	}
}
