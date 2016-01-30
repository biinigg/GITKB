package com.dsc.dci.sqlcode.patchs;

import java.util.ArrayList;

import com.dci.jweb.DCIEnums.Database.DBType;

public class sqlEKBP002001000001 {
	private String[] cmds;

	public ArrayList<String> getSQLCollection(DBType dbtp) {
		ArrayList<String> sqls = null;
		Class<?> c = null;
		// Method[] m = null;
		Object o = null;
		int mcnt = 0;
		try {
			if (dbtp == DBType.Oracle) {
				c = Oracle002001000001.class;
				mcnt = 16;
			} else if (dbtp == DBType.SqlServer) {
				c = SqlServer002001000001.class;
				mcnt = 18;
			}
			this.cmds = new String[mcnt];
			for (int i = 1; i <= this.cmds.length; i++) {
				this.cmds[i - 1] = "cmd" + i;
			}

			o = c.newInstance();
			// m = c.getDeclaredMethods();

			sqls = new ArrayList<String>();

			for (int i = 0; i < this.cmds.length; i++) {
				sqls.add(c.getMethod(cmds[i]).invoke(o).toString());
			}
		} catch (Exception ex) {

		}
		return sqls;
	}

	public String getKanBanHaveLegend() {
		return "select sql_id,legend from Sql_Info where case when legend = '' then null else legend end is not null";
	}

	public String getKanBanHaveLegend_ora() {
		return "select sql_id,legend from Sql_Info where legend is not null and length(legend) > 0";
	}

	public String updKanBanLegend() {
		return "update Sql_Info set legend = ? where sql_id = ?";
	}

	public String addCUSLanguage() {
		return "insert into Multi_Language_CUS (lang,lang_key,lang_value) values (?,?,?)";
	}

	public String deleteAllLegendCusLang() {
		return "delete from Multi_Language_CUS where lang_key like ?";
	}
}

class Oracle002001000001 {
	public String cmd1() {
		return "alter table Sql_Condition rename to Sql_Condition_bk";
	}

	public String cmd2() {
		return "ALTER TABLE Sql_Condition_bk DROP PRIMARY KEY";
	}

	public String cmd3() {
		return "create table Sql_Condition( " + "	user_id varchar2(30) NOT NULL, " + "	sql_id char(5) NOT NULL, "
				+ "	filter_col varchar2(1500), " + "	filter_condi varchar2(20), " + "	filter_value varchar2(250), "
				+ "	sort_col varchar2(250), " + "	sort_type varchar2(4), " + "	page_size number(4) " + ")";

	}

	public String cmd4() {
		return "alter table Sql_Condition " + "  add constraint PK_Sql_Condition primary key (user_id,sql_id) "
				+ "  using index " + "  tablespace SYSTEM " + "  pctfree 10 " + "  initrans 2 " + "  maxtrans 255 "
				+ "  storage " + "  ( " + "    initial 64K " + "    next 1M " + "    minextents 1 "
				+ "    maxextents unlimited " + "    pctincrease 0 " + "  )";
	}

	public String cmd5() {
		return "create table Sql_Format( " + "	user_id varchar2(30) NOT NULL, " + "	sql_id char(5) NOT NULL, "
				+ "	grid_font_size number(4), " + "	grid_font_color char(6), " + "	grid_row_height number(6), "
				+ "	grid_row_color char(6), " + "	popup_width number(6) " + ")";
	}

	public String cmd6() {
		return "alter table Sql_Format " + "  add constraint PK_Sql_Format primary key (user_id,sql_id) "
				+ "  using index " + "  tablespace SYSTEM " + "  pctfree 10 " + "  initrans 2 " + "  maxtrans 255 "
				+ "  storage " + "  ( " + "    initial 64K " + "    next 1M " + "    minextents 1 "
				+ "    maxextents unlimited " + "    pctincrease 0 " + "  )";
	}

	public String cmd7() {
		return "insert into Sql_Condition (user_id,sql_id,filter_col,filter_condi,filter_value,sort_col,sort_type,page_size) "
				+ " select user_id,sql_id,filter_col,filter_condi,filter_value,sort_col,sort_type,page_size from Sql_Condition_bk";

	}

	public String cmd8() {
		return "insert into Sql_Format (user_id,sql_id,grid_font_size,grid_font_color,grid_row_height,grid_row_color,popup_width) "
				+ " select user_id,sql_id,grid_font_size,grid_font_color,grid_row_height,grid_row_color,popup_width from Sql_Condition_bk";

	}

	public String cmd9() {
		return " drop table Sql_Condition_bk";
	}

	public String cmd10() {
		return "ALTER TABLE Conn_Info " + "  MODIFY (db_name   varchar2(40), " + "	db_user   varchar2(40))";

	}

	public String cmd11() {
		return "ALTER TABLE Marquee_Info MODIFY message varchar2(2000)";

	}

	public String cmd12() {
		return "INSERT INTO System_Config (config_id, config_value, config_desc, config_type) VALUES ('MarqueeGap', '180','跑馬燈間隔(秒)', '1')";

	}

	public String cmd13() {
		return "INSERT INTO System_Config (config_id, config_value, config_desc, config_type) VALUES ('MarqueeType', '1','跑馬燈顯示方式', '1')";
	}

	public String cmd14() {
		return "create or replace view Marquee_Data as "
				+ "select bb.sql_id, cc.conn_id, aa.marquee_type, aa.message,aa.start_date,aa.end_date "
				+ "  from (select c.sql_id, b.conn_id, a.* "
				+ "           from (select marquee_id, marquee_type, message,start_date,end_date "
				+ "                    from Marquee_Info "
				+ "                   where marquee_type in (1, 2)) a "
				+ "           left join Marquee_Conn_Mapping b on 1 = case when a.marquee_type = 1 then 0 else case when a.marquee_id = b.marquee_id then 1 else 0 end end "
				+ "           left join Marquee_Sql_Mapping c on a.marquee_id = c.marquee_id) aa "
				+ "  join Sql_Info bb on 1 = case when aa.sql_id is null or aa.sql_id = '' then 1 else case when aa.sql_id = bb.sql_id then 1 else 0 end end "
				+ "  join Conn_Info cc on 1 = case when aa.conn_id is null or aa.conn_id = '' then 1 else case when aa.conn_id = cc.conn_id then 1 else 0 end end";

	}

	public String cmd15() {
		return "create or replace view Sys_Marquee_Data as "
				+ "select bb.conn_id, aa.marquee_type, aa.message, aa.start_date, aa.end_date "
				+ "  from (select case a.marquee_type "
				+ "                   when 3 then "
				+ "                    'CSYS' "
				+ "                   else "
				+ "                    b.conn_id "
				+ "                 end conn_id, a.* "
				+ "           from (select marquee_id, marquee_type, message, start_date, end_date "
				+ "                    from Marquee_Info "
				+ "                   where marquee_type in (3, 4)) a "
				+ "           left join Marquee_Conn_Mapping b on a.marquee_id = b.marquee_id) aa "
				+ "  join Conn_Info bb on 1 = case when aa.conn_id is null or aa.conn_id = '' then 1 else case when aa.conn_id = bb.conn_id then 1 else 0 end end";

	}

	public String cmd16() {
		return "alter table " + "   SQL_COLUMN_INFO " + "add " + "   (is_app_col NUMBER(1) default 1, "
				+ "    is_app_dtl_col NUMBER(1) default 1)";

	}

}

class SqlServer002001000001 {
	public String cmd1() {
		return "EXEC sp_rename 'Sql_Condition', 'Sql_Condition_bk';";
	}

	public String cmd2() {
		return "ALTER TABLE Sql_Condition_bk DROP CONSTRAINT PK_Sql_Condition;";
	}

	public String cmd3() {
		return "CREATE TABLE [dbo].[Sql_Condition](                                                                                                            "
				+ "	[user_id] [varchar](30) NOT NULL,                                                                                                       "
				+ "	[sql_id] [char](5) NOT NULL,                                                                                                            "
				+ "	[filter_col] [varchar](1500) NULL,                                                                                                      "
				+ "	[filter_condi] [varchar](20) NULL,                                                                                                      "
				+ "	[filter_value] [nvarchar](250) NULL,                                                                                                    "
				+ "	[sort_col] [varchar](250) NULL,                                                                                                         "
				+ "	[sort_type] [varchar](4) NULL,                                                                                                          "
				+ "	[page_size] [int] NULL,                                                                                                                 "
				+ " CONSTRAINT [PK_Sql_Condition] PRIMARY KEY CLUSTERED                                                                                           "
				+ "(                                                                                                                                              "
				+ "	[user_id] ASC,                                                                                                                          "
				+ "	[sql_id] ASC                                                                                                                            "
				+ ")WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]      "
				+ ") ON [PRIMARY]                                                                                                                                 ";
	}

	public String cmd4() {
		return "CREATE TABLE [dbo].[Sql_Format](                                                                                                                "
				+ "	[user_id] [varchar](30) NOT NULL,                                                                                                        "
				+ "	[sql_id] [char](5) NOT NULL,                                                                                                             "
				+ "	[grid_font_size] [int] NULL,                                                                                                             "
				+ "	[grid_font_color] [char](6) NULL,                                                                                                        "
				+ "	[grid_row_height] [int] NULL,                                                                                                            "
				+ "	[grid_row_color] [char](6) NULL,                                                                                                         "
				+ "	[popup_width] [int] NULL,                                                                                                                "
				+ " CONSTRAINT [PK_Sql_Format] PRIMARY KEY CLUSTERED                                                                                               "
				+ "(                                                                                                                                               "
				+ "	[user_id] ASC,                                                                                                                           "
				+ "	[sql_id] ASC                                                                                                                             "
				+ ")WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]       "
				+ ") ON [PRIMARY]                                                                                                                                  ";
	}

	public String cmd5() {
		return "insert into dbo.Sql_Condition (user_id,sql_id,filter_col,filter_condi,filter_value,sort_col,sort_type,page_size) "
				+ "select user_id,sql_id,filter_col,filter_condi,filter_value,sort_col,sort_type,page_size from dbo.Sql_Condition_bk; ";
	}

	public String cmd6() {
		return "insert into dbo.Sql_Format (user_id,sql_id,grid_font_size,grid_font_color,grid_row_height,grid_row_color,popup_width) "
				+ "select user_id,sql_id,grid_font_size,grid_font_color,grid_row_height,grid_row_color,popup_width from dbo.Sql_Condition_bk;";
	}

	public String cmd7() {
		return "drop table Sql_Condition_bk;";
	}

	public String cmd8() {
		return "alter table [dbo].[Marquee_Info] alter column [message] [nvarchar](2000);";
	}

	public String cmd9() {
		return "alter table [dbo].[Conn_Info] alter column [db_name] [varchar](40);";
	}

	public String cmd10() {
		return "alter table [dbo].[Conn_Info] alter column [db_user] [varchar](40);";
	}

	public String cmd11() {
		return "INSERT INTO dbo.System_Config (config_id, config_value, config_desc, config_type) VALUES ('MarqueeGap', '180','跑馬燈間隔(秒)', '1')";
	}

	public String cmd12() {
		return "INSERT INTO dbo.System_Config (config_id, config_value, config_desc, config_type) VALUES ('MarqueeType', '1','跑馬燈顯示方式', '1')";
	}

	public String cmd13() {
		return "IF  EXISTS (SELECT * FROM sys.views WHERE object_id = OBJECT_ID(N'[dbo].[Marquee_Data]')) DROP VIEW [dbo].[Marquee_Data]";
	}

	public String cmd14() {
		return "CREATE VIEW [dbo].[Marquee_Data]                                                                                                                            "
				+ "AS                                                                                                                                                          "
				+ "select bb.sql_id, cc.conn_id, aa.marquee_type, aa.message,aa.start_date,aa.end_date                                                                         "
				+ "  from (select c.sql_id, b.conn_id, a.*                                                                                                                     "
				+ "           from (select marquee_id, marquee_type, message,start_date,end_date                                                                               "
				+ "                    from Marquee_Info                                                                                                                       "
				+ "                   where marquee_type in ('1', '2')) a                                                                                                      "
				+ "           left join Marquee_Conn_Mapping b on 1 = case when a.marquee_type = '1' then 0 else case when a.marquee_id = b.marquee_id then 1 else 0 end end   "
				+ "           left join Marquee_Sql_Mapping c on a.marquee_id = c.marquee_id) aa                                                                               "
				+ "  join Sql_Info bb on 1 = case when aa.sql_id is null or aa.sql_id = '' then 1 else case when aa.sql_id = bb.sql_id then 1 else 0 end end                   "
				+ "  join Conn_Info cc on 1 = case when aa.conn_id is null or aa.conn_id = '' then 1 else case when aa.conn_id = cc.conn_id then 1 else 0 end end              ";
	}

	public String cmd15() {
		return "IF  EXISTS (SELECT * FROM sys.views WHERE object_id = OBJECT_ID(N'[dbo].[Sys_Marquee_Data]')) DROP VIEW [dbo].[Sys_Marquee_Data]";
	}

	public String cmd16() {
		return "CREATE VIEW [dbo].[Sys_Marquee_Data]                                                                                                               "
				+ "AS                                                                                                                                                 "
				+ "select bb.conn_id, aa.marquee_type, aa.message, aa.start_date, aa.end_date                                                                         "
				+ "  from (select case a.marquee_type                                                                                                                 "
				+ "                   when '3' then                                                                                                                   "
				+ "                    'CSYS'                                                                                                                         "
				+ "                   else                                                                                                                            "
				+ "                    b.conn_id                                                                                                                      "
				+ "                 end conn_id, a.*                                                                                                                  "
				+ "           from (select marquee_id, marquee_type, message, start_date, end_date                                                                    "
				+ "                    from Marquee_Info                                                                                                              "
				+ "                   where marquee_type in ('3', '4')) a                                                                                             "
				+ "           left join Marquee_Conn_Mapping b on a.marquee_id = b.marquee_id) aa                                                                     "
				+ "  join Conn_Info bb on 1 = case when aa.conn_id is null or aa.conn_id = '' then 1 else case when aa.conn_id = bb.conn_id then 1 else 0 end end     ";
	}

	public String cmd17() {
		return "ALTER TABLE Sql_Column_Info ADD is_app_col smallint NOT NULL DEFAULT(1)";
	}

	public String cmd18() {
		return "ALTER TABLE Sql_Column_Info ADD is_app_dtl_col smallint NOT NULL DEFAULT(1) ";
	}
}
