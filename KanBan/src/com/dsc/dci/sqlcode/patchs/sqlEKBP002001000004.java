package com.dsc.dci.sqlcode.patchs;

import java.util.ArrayList;

import com.dci.jweb.DCIEnums.Database.DBType;

public class sqlEKBP002001000004 {

	private String[] cmds;

	public ArrayList<String> getSQLCollection(DBType dbtp) {
		ArrayList<String> sqls = null;
		Class<?> c = null;
		Object o = null;
		int mcnt = 0;

		try {
			if (dbtp == DBType.Oracle) {
				c = Oracle002001000004.class;
				mcnt = 0;
			} else if (dbtp == DBType.SqlServer) {
				c = SqlServer002001000004.class;
				mcnt = 2;
			}
			this.cmds = new String[mcnt];
			for (int i = 1; i <= this.cmds.length; i++) {
				this.cmds[i - 1] = "cmd" + i;
			}

			o = c.newInstance();

			sqls = new ArrayList<String>();

			for (int i = 0; i < this.cmds.length; i++) {
				sqls.add(c.getMethod(cmds[i]).invoke(o).toString());
			}
		} catch (Exception ex) {

		}
		return sqls;
	}
}

class Oracle002001000004 {

}

class SqlServer002001000004 {	
	public String cmd1() {
		return "sp_rename  '[Sql_Cross_Join_Key].[key_Belong1]' , 'key_belong1', 'COLUMN'";
	}
	
	public String cmd2() {
		return "sp_rename  '[Sql_Cross_Join_Key].[key_Belong2]' , 'key_belong2', 'COLUMN'";
	}
}
