package com.dsc.dci.customer.c02420302.sqlcode.funcs.ekb;

public class sqlSubKanBan02420302 {
	public String initData() {
		return " select ckb_id, ckb_name, head_kbid, body_kbid, combinecolumn, conn_id,m1.lang_value as head_name,m2.lang_value as body_name from CombineKB "+
				" join Multi_Language_CUS m1 on  m1.lang_key=head_kbid and m1.lang = ? "+
				" join Multi_Language_CUS m2 on  m2.lang_key=body_kbid and m2.lang = ? "+
				" where ckb_id=?   ";
	}
	public String initHPheadData(){
		String sql="";
		sql=" select   A.ckb_id, head_kbid, body_kbid, "+
			" B.filter_col, B.filter_condi, B.filter_value, sort_col, sort_type, page_size, "+
			" params_value, "+
			" condi_relation, D.filter_col, D.filter_condi, D.filter_value, filter_col_display,filter_condi_display, filter_value_display,	 "+
			" grid_font_size, grid_font_color, grid_row_height, grid_row_color, popup_width, grid_row_even_color  "+
			" from CombineKB A "+
			" left join Sql_Condition B on  B.sql_id =A.ckb_id and B.user_id = ? "+
			" left join Sql_Init_Params C on C.sql_id = A.ckb_id and C.user_id = ? and params_id = 'op_panel_open'  "+
			" left join Sql_Advance_Condition D on D.sql_id = A.ckb_id and D.user_id = ? "+
			" left join Sql_Format E on E.sql_id = A.ckb_id and E.user_id = ? "+
			" where  A.ckb_id = ? ";
		return sql;
	}
	public String initHPbodyData(){
		String sql="";
		sql=" select   B.filter_col, B.filter_condi, B.filter_value, sort_col, sort_type, page_size, "+
				" params_value, "+
				" grid_font_size, grid_font_color, grid_row_height, grid_row_color, popup_width, grid_row_even_color  "+
				" from Sql_Info A "+
				" left join Sql_Condition B on  B.sql_id =A.sql_id and B.user_id=? "+
				" left join KB_Init_Params C on C.sql_id = A.sql_id  and params_id = 'op_panel_open' and C.user_id=? "+
				" left join Sql_Format D on D.sql_id = A.sql_id and D.user_id=? "+
				"  where  A.sql_id = ?  ";
		return sql;
	}
	public String getAdvCondition() {
		return "select * from Sql_Advance_Condition where user_id = ? and sql_id = ? order by seq";
	}

	
}
