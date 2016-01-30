package com.dsc.dci.sqlcode.patchs;

import java.util.ArrayList;

import com.dci.jweb.DCIEnums.Database.DBType;

public class sqlEKBP002001001002 {

	private String[] cmds;

	public ArrayList<String> getSQLCollection(DBType dbtp) {
		ArrayList<String> sqls = null;
		Class<?> c = null;
		Object o = null;
		int mcnt = 0;

		try {
			if (dbtp == DBType.Oracle) {
				c = Oracle002001001002.class;
				mcnt = 4;
			} else if (dbtp == DBType.SqlServer) {
				c = SqlServer002001001002.class;
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
			sqls.add(addInitParamsDef());
		} catch (Exception ex) {

		}
		return sqls;
	}

	private String addInitParamsDef() {
		return "INSERT INTO Sql_Init_Params(sql_id, user_id, params_id, params_value, params_desc, params_type)VALUES('*****', '*****', 'op_panel_open', '1', '看板查詢面板是否開啟', 'SYS')";
	}
}

class Oracle002001001002 {
	public String cmd1() {
		return "alter table SQL_FORMAT add GRID_ROW_EVEN_COLOR CHAR(6)";
	}

	public String cmd2() {
		return "create table Sql_Init_Params                          "
				+ "(                                                     "
				+ "  sql_id       CHAR(5) not null,                      "
				+ "  user_id      VARCHAR2(30) not null,                 "
				+ "  params_id    VARCHAR2(20) not null,                 "
				+ "  params_value VARCHAR2(50),                          "
				+ "  params_desc  VARCHAR2(300),                         "
				+ "  params_type  CHAR(3)                                "
				+ ")                                                     "
				+ "tablespace SYSTEM                                     "
				+ "  pctfree 10                                          "
				+ "  pctused 40                                          "
				+ "  initrans 1                                          "
				+ "  maxtrans 255                                        "
				+ "  storage                                             "
				+ "  (                                                   "
				+ "    initial 64K                                       "
				+ "    minextents 1                                      "
				+ "    maxextents unlimited                              "
				+ "  )                                                   ";

	}

	public String cmd3() {
		return "alter table Sql_Init_Params                                                 "
				+ "  add constraint PK_Sql_Init_Params primary key (sql_id, user_id, params_id)"
				+ "  using index                                                               "
				+ "  tablespace SYSTEM                                                         "
				+ "  pctfree 10                                                                "
				+ "  initrans 2                                                                "
				+ "  maxtrans 255                                                              "
				+ "  storage                                                                   "
				+ "  (                                                                         "
				+ "    initial 64K                                                             "
				+ "    minextents 1                                                            "
				+ "    maxextents unlimited                                                    "
				+ "  )                                                                         ";

	}

	public String cmd4() {
		return "create or replace view KB_Init_Params as                                                                      "
				+ "select aa.sql_id, aa.user_id, aa.params_id,                                                                   "
				+ "       case                                                                                                   "
				+ "          when bb.params_value is null then                                                                   "
				+ "           aa.params_value                                                                                    "
				+ "          else                                                                                                "
				+ "           bb.params_value                                                                                    "
				+ "        end params_value, aa.params_desc, aa.params_type                                                      "
				+ "  from (select a.sql_id, b.user_id, c.params_id, c.params_value, c.params_desc,                               "
				+ "                c.params_type                                                                                 "
				+ "           from Sql_Info a                                                                                    "
				+ "           join User_Info b on 1 = 1                                                                          "
				+ "           join (select *                                                                                     "
				+ "                  from Sql_Init_Params                                                                        "
				+ "                 where sql_id = '*****' and user_id = '*****') c on 1 = 1) aa                                 "
				+ "  left join (select *                                                                                         "
				+ "               from Sql_Init_Params a                                                                         "
				+ "              where not exists                                                                                "
				+ "              (select 'b'                                                                                     "
				+ "                       from Sql_Init_Params b                                                                 "
				+ "                      where b.sql_id = '*****' and b.user_id = '*****' and                                    "
				+ "                            a.sql_id = b.sql_id and a.user_id = b.user_id)) bb on aa.sql_id =                 "
				+ "                                                                                  bb.sql_id and               "
				+ "                                                                                  aa.user_id =                "
				+ "                                                                                  bb.user_id and              "
				+ "                                                                                  aa.params_id =              "
				+ "                                                                                  bb.params_id                ";
	}
}

class SqlServer002001001002 {
	public String cmd1() {
		return "ALTER TABLE dbo.Sql_Format ADD grid_row_even_color char(6) NULL";
	}

	public String cmd2() {
		return "CREATE TABLE [dbo].[Sql_Init_Params](                                                                                                        "
				+ "	[sql_id] [char](5) NOT NULL,                                                                                                          "
				+ "	[user_id] [varchar](30) NOT NULL,                                                                                                     "
				+ "	[params_id] [varchar](20) NOT NULL,                                                                                                   "
				+ "	[params_value] [varchar](50) NULL,                                                                                                    "
				+ "	[params_desc] [nvarchar](300) NULL,                                                                                                   "
				+ "	[params_type] [char](3) NULL,                                                                                                         "
				+ " CONSTRAINT [PK_Sql_Init_Params] PRIMARY KEY CLUSTERED                                                                                       "
				+ "(                                                                                                                                            "
				+ "	[sql_id] ASC,                                                                                                                         "
				+ "	[user_id] ASC,                                                                                                                        "
				+ "	[params_id] ASC                                                                                                                       "
				+ ")WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]    "
				+ ") ON [PRIMARY]                                                                                                                               ";

	}

	public String cmd3() {
		return "CREATE VIEW [dbo].[KB_Init_Params]                                                                      "
				+ "AS                                                                                                      "
				+ "select aa.sql_id, aa.user_id, aa.params_id,                                                             "
				+ "       case                                                                                             "
				+ "          when bb.params_value is null then                                                             "
				+ "           aa.params_value                                                                              "
				+ "          else                                                                                          "
				+ "           bb.params_value                                                                              "
				+ "        end params_value, aa.params_desc, aa.params_type                                                "
				+ "  from (select a.sql_id, b.user_id, c.params_id, c.params_value, c.params_desc,                         "
				+ "                c.params_type                                                                           "
				+ "           from Sql_Info a                                                                              "
				+ "           join User_Info b on 1 = 1                                                                    "
				+ "           join (select *                                                                               "
				+ "                  from Sql_Init_Params                                                                  "
				+ "                 where sql_id = '*****' and user_id = '*****') c on 1 = 1) aa                           "
				+ "  left join (select *                                                                                   "
				+ "               from Sql_Init_Params a                                                                   "
				+ "              where not exists                                                                          "
				+ "              (select 'b'                                                                               "
				+ "                       from Sql_Init_Params b                                                           "
				+ "                      where b.sql_id = '*****' and b.user_id = '*****' and                              "
				+ "                            a.sql_id = b.sql_id and a.user_id = b.user_id)) bb on aa.sql_id =           "
				+ "                                                                                  bb.sql_id and         "
				+ "                                                                                  aa.user_id =          "
				+ "                                                                                  bb.user_id and        "
				+ "                                                                                  aa.params_id =        "
				+ "                                                                                  bb.params_id          ";
	}
}
