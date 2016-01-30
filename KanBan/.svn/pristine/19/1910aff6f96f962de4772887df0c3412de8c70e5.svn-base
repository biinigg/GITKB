package com.dsc.dci.sqlcode.patchs;

import java.util.ArrayList;

import com.dci.jweb.DCIEnums.Database.DBType;

public class sqlEKBP002001001003 {

	private String[] cmds;

	public ArrayList<String> getSQLCollection(DBType dbtp) {
		ArrayList<String> sqls = null;
		Class<?> c = null;
		Object o = null;
		int mcnt = 0;

		try {
			if (dbtp == DBType.Oracle) {
				c = Oracle002001001003.class;
				mcnt = 0;
			} else if (dbtp == DBType.SqlServer) {
				c = SqlServer002001001003.class;
				mcnt = 0;
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
			sqls.add(addDBVersion());
		} catch (Exception ex) {

		}
		return sqls;
	}

	public String addDBVersion() {
		return "insert into System_Config (config_id,config_value,config_desc,config_type) values ('SYSVersion','2.1.1.3','系統版本','0')";
	}
}

class Oracle002001001003 {
}

class SqlServer002001001003 {
}
