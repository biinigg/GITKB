package com.dsc.dci.sqlcode.funcs.ekb;

public class sqlPE001 {
	public String getSqlColumns() {
		return "select a.*, "
				+ "case when b.lang_value is null or b.lang_value = '' then a.col_lang_key else b.lang_value end col_text "
				+ "from Sql_Column_Info a left join (select * from Multi_Language where lang = ?) b on a.col_lang_key = b.lang_key "
				+ "where sql_id = ? order by col_seq";

	}
	public String getRoleGrantKBID(){
		return " SELECT func_id "
			     + " FROM  Role_Rule_Info "
			     + " WHERE conn_id = ? AND (role_id = (select role_id from Group_Role_Mapping where group_id= (Select group_id from User_Info where user_id=? ))) AND (func_id LIKE 'PE%' and func_id<>'PE001' and func_id<>'PE000Config') ";
		
	}

	public String getSqlCommand() {
		return "select a.*, b.filter_col,b.filter_condi,b.filter_value,b.sort_col,b.sort_type,b.page_size, "
				+ "d.grid_font_size,d.grid_font_color,d.grid_row_height,d.grid_row_color,d.popup_width,"
				+ " case when c.cross_type is not null and c.conn_id is not null and "
				+ " c.sql_context is not null then 1 else 0 "
				+ "        end is_cross, c.conn_id cross_conn_id, c.cross_type, c.group_by cross_group_by, "
				+ "       c.order_by cross_order_by, c.sql_context cross_sql_context, c.join_key_set1, "
				+ "       c.join_key_set2 from (select * from Sql_Info where sql_id = ?)a left join (select * from Sql_Condition "
				+ "              where user_id = ? and sql_id = ?) b on a.sql_id = b.sql_id "
				+ "  left join (select * from Sql_Cross_Database "
				+ "              where sql_id = ?) c on a.sql_id = c.sql_id "
				+ "  left join (select * from Sql_Format "
				+ "              where user_id = ? and sql_id = ?) d on a.sql_id = d.sql_id";

	}

	public String getColConfig() {
		return "select '1' tp ,col_id,col_type,config_value from Sql_Column_Info where sql_id = ? and col_type <> 'CHAR' "
				+ "union all "
				+ "select '2' tp ,col_id,col_type,calc_rule from Sql_Column_Info where sql_id = ? and calc_rule is not null and calc_rule <> ''";
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

		sql = "insert into Sql_Format (user_id,sql_id,grid_font_size,grid_font_color,grid_row_height,grid_row_color,popup_width) values (?,?,?,?,?,?,?)";

		return sql;
	}
	public String getSqlData(){
		String sql = null;
		sql=" Declare @date as nvarchar(max) "
           +" Declare @sql as nvarchar(max) "
			+" With my_date as ( "
			+" select distinct out_date from Process_Exception_History "
			+" where out_date > dateadd(m,-6,getdate()) "
			+" ) "
			+" select @date=left(report_date,len(report_date)-1) from ( "
			+" select ( "
			+" select '['+convert(char(10),out_date)+']' +',' "
			+" from my_date "
			+" order by out_date "
			+" for XML PATH('') ) as report_date ) T "
			+" set @sql = "
			+" 'select b.process_id,b.process_name,b.report_id,b.report_name,b.dept_name,b.owner,'+@date+' from  "
			+" ( "
			+" select report_id,exc_count,out_date from Process_Exception_History "
			+" where conn_id=''%conn_id%'') p "
			+" PIVOT "
			+" (sum(exc_count) "
			+" FOR out_date in  "
			+" ('+@date+' ) "
			+" ) AS pvt "
			+" join Process_Exception b on pvt.report_id=b.report_id "
			+" where is_work=''Y'' "
			+" order by b.sequ_num' "
			+" execute sp_executesql @sql "
			+" ";
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
	public String getConn_id(){
		return "SELECT conn_id FROM Group_Funcs where func_id=? and user_id=?";
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
	//****sqlSQLEditor.java
	public String getInitSqlColumns() {
		return "select s.*, case when k.cross_type is not null and k.conn_id is not null and "
				+ "               k.sql_context is not null then 1 else 0 end is_cross, k.cross_type, "
				+ "               k.sql_context cross_sql_context, k.conn_id cross_conn_id, k.order_by cross_order_by, "
				+ "               k.group_by cross_group_by "
				+ "  from (select a.sql_id, sql_context, group_by, order_by, conn_id from Sql_Info a "
				+ "           join Sql_Conn_Mapping b on a.sql_id = b.sql_id "
				+ "          where a.sql_id = ?) s left join (select * from Sql_Cross_Database "
				+ "              where sql_id = ?) k on s.sql_id = k.sql_id order by s.conn_id";
	}
	
	public String getSqlColumnWithHead() {
		return "select a.*,b.cht,b.chs from Sql_Column_Info a left join Multi_Language_Row b on a.col_lang_key = b.lang_key where sql_id = ? order by col_seq,col_id";
	}
	
	public String deleteSqlColumnWithHead() {
		return "delete from Sql_Column_Info where sql_id = ?";
	}
	public String addSqlColumn() {
		return "insert into Sql_Column_Info (col_lang_key,col_type,col_ori_name,col_width,col_seq,config_value,visible,is_popup,popup_title,calc_rule,sql_id,col_id) values (?,?,?,?,?,?,?,?,?,?,?,?)";
	}

	public String updSqlColumn() {
		return "update Sql_Column_Info set col_lang_key = ?,col_type = ? ,col_ori_name = ?,col_width = ?,col_seq = ?,config_value = ?,visible = ?,is_popup = ?,popup_title = ?,calc_rule = ? where sql_id = ? and col_id = ?";
	}
	public String addSqlRelation() {
		return "insert into Sql_Column_Relation (sql_id,col_id,relation_id,target_sql_id,target_col_id,seq) values (?,?,?,?,?,?)";
	}
	public String deleteSqlRelation_col() {
		return "delete from Sql_Column_Relation where sql_id = ? and col_id = ?";
	}
	public String addCusLang() {
		return "insert into Multi_Language_CUS (lang,lang_key,lang_value) values (?,?,?)";
	}

	public String deleteCusLang() {
		return "delete from Multi_Language_CUS where lang_key = ?";
	}
	public String getSqlRelationEditor() {
		return "select col_id,target_sql_id, target_col_id, sql_name as target_sql_name, case "
				+ "          when lang_value is null then col_lang_key else "
				+ "           lang_value end as target_col_name, seq "
				+ "  from (select a.*, b.sql_name, c.col_lang_key "
				+ "           from (select col_id,target_sql_id, target_col_id, seq "
				+ "                    from Sql_Column_Relation where sql_id = ?) a "
				+ "           join Sql_Info b on a.target_sql_id = b.sql_id "
				+ "           join Sql_Column_Info c on a.target_sql_id = c.sql_id and "
				+ "                                     a.target_col_id = c.col_id) data "
				+ "  left join (select * from Multi_Language "
				+ "              where lang = ?) d on data.col_lang_key = d.lang_key order by col_id,seq";

	}
	//@@@@
}
