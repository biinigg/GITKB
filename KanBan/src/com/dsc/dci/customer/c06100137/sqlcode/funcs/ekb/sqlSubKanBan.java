package com.dsc.dci.customer.c06100137.sqlcode.funcs.ekb;

public class sqlSubKanBan {

	public String getSqlCommand() {
		String sql = null;
		sql = "select a.*, b.filter_col, b.filter_condi, b.filter_value, b.sort_col, b.sort_type,                   "
				+ "       b.page_size, d.grid_font_size, d.grid_font_color, d.grid_row_height,                           "
				+ "       d.grid_row_color, d.grid_row_even_color, d.popup_width,case when c.sql_id is not null then 1 else 0 end is_cross      "
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
				+ "              where user_id = ? and sql_id = ?) d on a.sql_id = d.sql_id                    ";
		return sql;
	}
}
