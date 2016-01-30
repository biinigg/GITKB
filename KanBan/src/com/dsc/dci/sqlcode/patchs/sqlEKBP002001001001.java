package com.dsc.dci.sqlcode.patchs;

import java.util.ArrayList;

import com.dci.jweb.DCIEnums.Database.DBType;

public class sqlEKBP002001001001 {

	private String[] cmds;

	public ArrayList<String> getSQLCollection(DBType dbtp) {
		ArrayList<String> sqls = null;
		Class<?> c = null;
		Object o = null;
		int mcnt = 0;

		try {
			if (dbtp == DBType.Oracle) {
				c = Oracle002001001001.class;
				mcnt = 1;
			} else if (dbtp == DBType.SqlServer) {
				c = SqlServer002001001001.class;
				mcnt = 1;
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

class Oracle002001001001 {
	public String cmd1() {
		return "INSERT INTO Menu_STD (func_id, lang_key, parent_id, func_url, sort_id, is_program, visible, package) VALUES (N'TreeLogout', N'logout', NULL, NULL, 999999, 1, 1, N'SYS')";
	}
}

class SqlServer002001001001 {
	public String cmd1() {
		return "INSERT INTO Menu_STD (func_id, lang_key, parent_id, func_url, sort_id, is_program, visible, package) VALUES (N'TreeLogout', N'logout', NULL, NULL, 999999, 1, 1, N'SYS')";
	}
}
