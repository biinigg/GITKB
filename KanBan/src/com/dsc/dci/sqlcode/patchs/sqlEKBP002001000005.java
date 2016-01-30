package com.dsc.dci.sqlcode.patchs;

import java.util.ArrayList;

import com.dci.jweb.DCIEnums.Database.DBType;

public class sqlEKBP002001000005 {

	private String[] cmds;

	public ArrayList<String> getSQLCollection(DBType dbtp) {
		ArrayList<String> sqls = null;
		Class<?> c = null;
		Object o = null;
		int mcnt = 0;

		try {
			if (dbtp == DBType.Oracle) {
				c = Oracle002001000005.class;
				mcnt = 2;
			} else if (dbtp == DBType.SqlServer) {
				c = SqlServer002001000005.class;
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

class Oracle002001000005 {
	public String cmd1() {
		return "create table Func_CUS                     "+
				"(                                             "+
				"    package     char(3) NOT NULL, "+
				"    func_id     varchar2(30) NOT NULL,        "+
				"    func_url     varchar2(150)                "+
				")                                             "; 
	}

	public String cmd2() {
		return "alter table Func_CUS                                               "
				+ "  add constraint PK_Func_CUS primary key (package,func_id)      "
				+ "  using index                                                                    "
				+ "  tablespace SYSTEM                                                              "
				+ "  pctfree 10                                                                     "
				+ "  initrans 2                                                                     "
				+ "  maxtrans 255                                                                   "
				+ "  storage                                                                        "
				+ "  (                                                                              "
				+ "    initial 64K                                                                  "
				+ "    next 1M                                                                      "
				+ "    minextents 1                                                                 "
				+ "    maxextents unlimited                                                         "
				+ "    pctincrease 0                                                                "
				+ "  )                                                                             ";
	}
}

class SqlServer002001000005 {
	public String cmd1() {
		return "CREATE TABLE [dbo].[Func_CUS](                                                                                                              "
				+ " [package] [char](3) NOT NULL,"
				+ "	[func_id] [varchar](30) NOT NULL,                                                                                                    "
				+ "	[func_url] [varchar](150) NULL,                                                                                                      "
				+ " CONSTRAINT [PK_Func_CUS] PRIMARY KEY CLUSTERED                                                                                             "
				+ "(                                                                                                                                           "
				+ " [package]  ASC, "
				+ "	[func_id] ASC                                                                                                                        "
				+ ")WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]   "
				+ ") ON [PRIMARY]                                                                                                                              ";
	}
}
