package com.dsc.dci.sqlcode.patchs;

import java.util.ArrayList;

import com.dci.jweb.DCIEnums.Database.DBType;

public class sqlEKBP002001000003 {

	private String[] cmds;

	public ArrayList<String> getSQLCollection(DBType dbtp) {
		ArrayList<String> sqls = null;
		Class<?> c = null;
		Object o = null;
		int mcnt = 0;

		try {
			if (dbtp == DBType.Oracle) {
				c = Oracle002001000003.class;
				mcnt = 2;
			} else if (dbtp == DBType.SqlServer) {
				c = SqlServer002001000003.class;
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

	public String checkMultiJoin() {
		String sql = null;
		sql = "select sql_id,COUNT('b') from Sql_Cross_Database where conn_id is not null and sql_context is not null and cross_type is not null group by sql_id having COUNT('b') > 1";
		return sql;
	}

	public String getAllCrossData() {
		String sql = null;
		sql = "select * from Sql_Cross_Database where cross_type = 'join' or cross_type = 'left_join'";
		return sql;
	}

	public String insJoinKey() {
		String sql = null;
		sql = "insert into Sql_Cross_Join_Key (sql_id,cross_id,key_id,key_belong1,key_col1,key_belong2,key_col2) "
				+ "values (?,?,'JK01',0,?,1,?)";
		return sql;
	}

	public String delJoinKey() {
		String sql = null;
		sql = "delete from Sql_Cross_Join_Key where sql_id = ? and cross_id = ?";
		return sql;
	}
}

class Oracle002001000003 {
	public String cmd1() {
		return "create table Sql_Cross_Join_Key(" + "	sql_id char(5) NOT NULL," + "	cross_id char(3) NOT NULL,"
				+ "	key_id char(4) NOT NULL," + "	key_belong1 number(1)," + "	key_col1 varchar2(250),"
				+ "	key_belong2 number(1)," + "	key_col2 varchar2(250)" + ")";
	}

	public String cmd2() {
		return "alter table Sql_Cross_Join_Key                                               "
				+ "  add constraint PK_Sql_Cross_Join_Key primary key (sql_id,cross_id,key_id)      "
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

class SqlServer002001000003 {
	public String cmd1() {
		return "CREATE TABLE [dbo].[Sql_Cross_Join_Key](                                                                                                    "
				+ "	[sql_id] [char](5) NOT NULL,                                                                                                         "
				+ "	[cross_id] [char](3) NOT NULL,                                                                                                       "
				+ "	[key_id] [char](4) NOT NULL,                                                                                                         "
				+ "	[key_belong1] [smallint] NULL,                                                                                                       "
				+ "	[key_col1] [varchar](250) NULL,                                                                                                      "
				+ "	[key_belong2] [smallint] NULL,                                                                                                       "
				+ "	[key_col2] [varchar](250) NULL,                                                                                                      "
				+ " CONSTRAINT [PK_Sql_Cross_Join_Key] PRIMARY KEY CLUSTERED                                                                                   "
				+ "(                                                                                                                                           "
				+ "	[sql_id] ASC,                                                                                                                        "
				+ "	[cross_id] ASC,                                                                                                                      "
				+ "	[key_id] ASC                                                                                                                         "
				+ ")WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]   "
				+ ") ON [PRIMARY]                                                                                                                              ";
	}

	public String cmd2() {
		return "sp_rename  '[Sql_Cross_Join_Key].[key_Belong1]' , 'key_belong1', 'COLUMN'";
	}

	public String cmd3() {
		return "sp_rename  '[Sql_Cross_Join_Key].[key_Belong2]' , 'key_belong2', 'COLUMN'";
	}
}
