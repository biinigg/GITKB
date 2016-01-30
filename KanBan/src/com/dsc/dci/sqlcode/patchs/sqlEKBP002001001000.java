package com.dsc.dci.sqlcode.patchs;

import java.util.ArrayList;

import com.dci.jweb.DCIEnums.Database.DBType;

public class sqlEKBP002001001000 {

	private String[] cmds;

	public ArrayList<String> getSQLCollection(DBType dbtp) {
		ArrayList<String> sqls = null;
		Class<?> c = null;
		Object o = null;
		int mcnt = 0;

		try {
			if (dbtp == DBType.Oracle) {
				c = Oracle002001001000.class;
				mcnt = 2;
			} else if (dbtp == DBType.SqlServer) {
				c = SqlServer002001001000.class;
				mcnt = 3;
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

class Oracle002001001000 {
	public String cmd1() {
		return "create table Push_KanBan_Info                        "+
				"(                                                    "+
				"    sql_id   char(5) NOT NULL,                       "+
				"    conn_id  varchar2(8)  default '*****' NOT NULL,  "+
				"    user_id  varchar2(30)   default '*****' NOT NULL,"+
				"    msg      varchar2(500)                           "+
				")";
	}
	public String cmd2() {
		return "alter table Push_KanBan_Info                                              "+
				"  add constraint PK_Push_KanBan_Info primary key (sql_id, conn_id,user_id)"+
				"  using index                                                             "+
				"  tablespace SYSTEM                                                       "+
				"  pctfree 10                                                              "+
				"  initrans 2                                                              "+
				"  maxtrans 255                                                            "+
				"  storage                                                                 "+
				"  (                                                                       "+
				"    initial 64K                                                           "+
				"    next 1M                                                               "+
				"    minextents 1                                                          "+
				"    maxextents unlimited                                                  "+
				"    pctincrease 0                                                         "+
				"  )";  
	}
}

class SqlServer002001001000 {
	public String cmd1() {
		return "CREATE TABLE [dbo].[Push_KanBan_Info](                                                                                                      "
				+ "	[sql_id] [char](5) NOT NULL,                                                                                                         "
				+ "	[conn_id] [char](5) NOT NULL,                                                                                                        "
				+ "	[user_id] [varchar](30) NOT NULL,                                                                                                    "
				+ "	[msg] [nvarchar](500) NULL,                                                                                                          "
				+ " CONSTRAINT [PK_Push_KanBan_Info] PRIMARY KEY CLUSTERED                                                                                     "
				+ "(                                                                                                                                           "
				+ "	[sql_id] ASC,                                                                                                                        "
				+ "	[conn_id] ASC,                                                                                                                       "
				+ "	[user_id] ASC                                                                                                                        "
				+ ")WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]   "
				+ ") ON [PRIMARY]                                                                                                                              ";
	}

	public String cmd2() {
		return "ALTER TABLE [dbo].[Push_KanBan_Info] ADD  CONSTRAINT [DF_Push_KanBan_Info_conn_id]  DEFAULT ('*****') FOR [conn_id]";
	}

	public String cmd3() {
		return "ALTER TABLE [dbo].[Push_KanBan_Info] ADD  CONSTRAINT [DF_Push_KanBan_Info_user_id]  DEFAULT ('*****') FOR [user_id]";
	}
}
