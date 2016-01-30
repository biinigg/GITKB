package com.dsc.dci.sqlcode.funcs.ekb;

public class sqlKanBan {
	public String getSqlColumns() {
		return "select a.*, "
				+ "case when b.lang_value is null or b.lang_value = '' then a.col_lang_key else b.lang_value end col_text "
				+ "from Sql_Column_Info a left join (select * from Multi_Language where lang = ?) b on a.col_lang_key = b.lang_key "
				+ "where sql_id = ? order by col_seq";

	}

	// public String getSqlCommand() {
	// return
	// "select a.*, b.filter_col,b.filter_condi,b.filter_value,b.sort_col,b.sort_type,b.page_size, "
	// +
	// "d.grid_font_size,d.grid_font_color,d.grid_row_height,d.grid_row_color,d.popup_width,"
	// + " case when c.cross_type is not null and c.conn_id is not null and "
	// + " c.sql_context is not null then 1 else 0 "
	// +
	// "        end is_cross, c.conn_id cross_conn_id, c.cross_type, c.group_by cross_group_by, "
	// +
	// "       c.order_by cross_order_by, c.sql_context cross_sql_context, c.join_key_set1, "
	// +
	// "       c.join_key_set2 from (select * from Sql_Info where sql_id = ?)a left join (select * from Sql_Condition "
	// +
	// "              where user_id = ? and sql_id = ?) b on a.sql_id = b.sql_id "
	// + "  left join (select * from Sql_Cross_Database "
	// + "              where sql_id = ?) c on a.sql_id = c.sql_id "
	// + "  left join (select * from Sql_Format "
	// +
	// "              where user_id = ? and sql_id = ?) d on a.sql_id = d.sql_id";
	//
	// }

	public String getSqlCommand() {
		String sql = null;
		sql = "select a.*, b.filter_col, b.filter_condi, b.filter_value, b.sort_col, b.sort_type,                   "
				+ "       b.page_size, d.grid_font_size, d.grid_font_color, d.grid_row_height,                           "
				+ "       d.grid_row_color, d.grid_row_even_color, d.popup_width,e.params_value op_is_open,case when c.sql_id is not null then 1 else 0 end is_cross      "
				+ "  from (select *                                                                                      "
				+ "           from Sql_Info                                                                              "
				+ "          where sql_id = ?) a                                                                   "
				+ "  left join (select *                                                                                 "
				+ "               from Sql_Condition                                                                     "
				+ "              where user_id = ? and sql_id = ?) b on a.sql_id = b.sql_id                     "
				+ "  left join (select max(sql_id) sql_id                                                                "
				+ "               from Sql_Cross_Database                                                                "
				+ "              where sql_id = ? and case when conn_id = '' then null else conn_id end is not null and sql_context is not null and case when cross_type = '' then null else cross_type end is not null  "
				// +"                and conn_id <> '' and cross_type  <> '' "
				+ "              group by sql_id) c on a.sql_id = c.sql_id                                               "
				+ "  left join (select *                                                                                 "
				+ "               from Sql_Format                                                                        "
				+ "              where user_id = ? and sql_id = ?) d on a.sql_id = d.sql_id                    "
				+ "  left join (select sql_id, params_value                                                                                "
				+ "               from KB_Init_Params                                                                        "
				+ "              where user_id = ? and sql_id = ? and params_id = 'op_panel_open' ) e on a.sql_id = e.sql_id                    ";
		return sql;
	}

	public String getCrossDBInfo() {
		String sql = null;

		sql = "select * from (select * from Sql_Cross_Database where sql_id = ? and conn_id is not null and sql_context is not null and "
				+ " case when cross_type = '' then null else cross_type end is not null) a left join (select * "
				+ "               from Sql_Cross_Join_Key "
				+ "              where sql_id = ? ) b on a.sql_id = b.sql_id and "
				+ "                                           a.cross_id = b.cross_id order by a.cross_seq ";

		return sql;
	}

	public String getIsCrossDB() {
		String sql = null;

		sql = "select 'b' from Sql_Cross_Database where sql_id = ? and conn_id is not null and sql_context is not null and cross_type is not null";

		return sql;
	}

	public String getColConfig() {
		return "select '1' tp ,col_id,col_type,config_value from Sql_Column_Info where sql_id = ? and col_type <> 'CHAR' "
				+ "union all "
				+ "select '2' tp ,col_id,col_type,calc_rule from Sql_Column_Info where sql_id = ? and case when calc_rule = '' then null else calc_rule end is not null ";
	}

	public String getIconInfo() {
		return "select icon_id,icon_path from Icon_Info";
	}

	public String getIconIdMap() {
		return "select icon_map_key,max(icon_id) icon_id from Icon_Info group by icon_map_key order by icon_map_key";
	}

	public String getIconNameMap() {
		return "select icon_map_key,icon_name from Icon_Info order by icon_map_key";
	}

	public String getMarqueeData() {
		return "select * from Marquee_Data where sql_id = ? and conn_id = ? and start_date <= ? and end_date >= ? "
				+ " order by start_date";
	}

	public String getAllLights() {
		return "select icon_map_key,icon_name from Icon_Info order by icon_id";
	}

	public String addSqlCondition() {
		return "INSERT INTO Sql_Condition (user_id,sql_id,filter_col,filter_condi,filter_value,sort_col,sort_type,page_size) VALUES "
				+ "(?,?,?,?,?,?,?,?)";
	}

	public String deleteSqlCondition() {
		return "delete from Sql_Condition where user_id = ? and sql_id = ?";
	}

	public String deleteSqlFormat() {
		String sql = null;

		sql = "delete from Sql_Format where user_id  = ? and sql_id = ?";

		return sql;
	}

	public String addSqlFromat() {
		String sql = null;

		sql = "insert into Sql_Format (user_id,sql_id,grid_font_size,grid_font_color,grid_row_height,grid_row_color,popup_width,grid_row_even_color) values (?,?,?,?,?,?,?,?)";

		return sql;
	}

	public String addAdvCondition() {
		return "INSERT INTO Sql_Advance_Condition (user_id,sql_id,adv_id,condi_relation,filter_col,filter_condi,filter_value,seq,filter_col_display,filter_condi_display,filter_value_display) VALUES "
				+ "(?,?,?,?,?,?,?,?,?,?,?)";
	}

	public String deleteAdvCondition() {
		return "delete from Sql_Advance_Condition where user_id = ? and sql_id = ?";
	}

	public String getAdvCondition() {
		return "select * from Sql_Advance_Condition where user_id = ? and sql_id = ? order by seq";
	}

	public String getSqlRelation() {
		return "select a.*,b.lang_key target_sql_name_id,c.col_ori_name  from Sql_Column_Relation a left join Menu b on a.target_sql_id = b.func_id join Sql_Column_Info c on a.target_sql_id = c.sql_id and a.target_col_id = c.col_id where a.sql_id = ? order by a.seq,a.col_id";
	}

	public String getFuncInfo() {
		return "select * from (select func_id, max(can_edit) can_edit "
				+ "           from (select func_id, can_edit from Role_Rule_Info "
				+ "                   where role_id in (select role_id "
				+ "                                      from Group_Role_Mapping "
				+ "                                     where group_id = (select group_id "
				+ "                                                         from User_Info "
				+ "                                                        where user_id = ?)) and "
				+ "                         conn_id = ? and func_id = ? union all "
				+ "                  select func_id, can_edit from User_Rule_Info "
				+ "                   where user_id = ? and conn_id = ? and func_id = ?) data "
				+ "          group by func_id) a join (select * from Menu "
				+ "         where visible = '1') b on a.func_id = b.func_id";

	}

	public String deleteSqlInitParams() {
		String sql = null;

		sql = "delete from Sql_Init_Params where sql_id = ? and user_id = ? and params_id = 'op_panel_open'";

		return sql;
	}

	public String addSqlInitParams() {
		String sql = null;

		sql = "INSERT INTO Sql_Init_Params(sql_id, user_id, params_id, params_value, params_desc, params_type)VALUES(?, ?, 'op_panel_open', ?, '看板查詢面板是否開啟', 'SYS')";

		return sql;
	}
}
