package com.dsc.dci.sqlcode.configs;

public class sqlSQLEditor {
	public String getAllData(String filter) {
		return "select aa.* from (select a.*,b.lang_value from Sql_Info a left join (select * from Multi_Language where lang = ?) b on a.sql_name = b.lang_key ) aa where 1 = 1  "
				+ filter + " order by aa.sql_id";
		// +
		// "  left join Sql_Cross_Database b on a.sql_id = b.sql_id order by a.sql_id";
	}

	public String getSqlName() {
		return "select sql_name from Sql_Info where sql_id = ?";
	}

	public String getAllConns() {
		return "select conn_id,conn_name from Conn_Info where visible = 1 and conn_id <> 'CSYS' order by conn_id";
	}

	public String deleteData() {
		return "delete from Sql_Info where sql_id = ?";
	}

	public String addData() {
		return "insert into Sql_Info (sql_name,sql_context,group_by,order_by,legend,use_marquee,marquee_location,"
				+ "marquee_type,marquee_refresh_gap,auto_refresh,refresh_gap,use_popup,popup_dir,popup_refresh_gap,sql_id) values "
				+ "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	}

	public String updateData() {
		return "update Sql_Info set sql_name = ?,sql_context = ?,group_by = ?,order_by = ?,legend = ?,use_marquee = ?,"
				+ "marquee_location = ?,marquee_type = ?,marquee_refresh_gap = ?,auto_refresh = ?,refresh_gap = ?,use_popup = ?,"
				+ "popup_dir = ? ,popup_refresh_gap = ? where sql_id = ?";
	}

	public String maxID() {
		return "select Max(sql_id) from Sql_Info where sql_id like 'K%'";
	}

	public String openWinData(String filter) {
		return "select conn_id,conn_name from Conn_Info where visible = 1 and conn_id <> 'CSYS' " + filter
				+ " order by conn_id";
	}

	public String getSqlConns() {
		return "select sql_id,conn_id from Sql_Conn_Mapping where sql_id = ? order by conn_id";
	}

	public String deleteSqlConnMapping() {
		return "delete from Sql_Conn_Mapping where sql_id = ?";
	}

	public String addSqlConnMapping() {
		return "insert into Sql_Conn_Mapping (sql_id,conn_id) values (?,?)";
	}

	public String addSqlColumn() {
		return "insert into Sql_Column_Info (col_lang_key,col_type,col_ori_name,col_width,col_seq,config_value,visible,is_popup,popup_title,calc_rule,sql_id,col_id) values (?,?,?,?,?,?,?,?,?,?,?,?)";
	}

	public String updSqlColumn() {
		return "update Sql_Column_Info set col_lang_key = ?,col_type = ? ,col_ori_name = ?,col_width = ?,col_seq = ?,config_value = ?,visible = ?,is_popup = ?,popup_title = ?,calc_rule = ? where sql_id = ? and col_id = ?";
	}

	public String deleteSqlColumn() {
		return "delete from Sql_Column_Info where sql_id = ? and col_id = ?";
	}

	public String deleteSqlColumnWithHead() {
		return "delete from Sql_Column_Info where sql_id = ?";
	}

	public String getSqlColumnWithHead() {
		return "select a.*,b.cht,b.chs from Sql_Column_Info a left join Multi_Language_Row b on a.col_lang_key = b.lang_key where sql_id = ? order by col_seq,col_id";
	}

	public String getInitSqlColumns() {
		return "select s.*, case when k.cross_type is not null and k.conn_id is not null and "
				+ "               k.sql_context is not null then 1 else 0 end is_cross, k.cross_type, "
				+ "               k.sql_context cross_sql_context, k.conn_id cross_conn_id, k.order_by cross_order_by, "
				+ "               k.group_by cross_group_by "
				+ "  from (select a.sql_id, sql_context, group_by, order_by, conn_id from Sql_Info a "
				+ "           join Sql_Conn_Mapping b on a.sql_id = b.sql_id "
				+ "          where a.sql_id = ?) s left join (select * from Sql_Cross_Database "
				+ "              where sql_id = ? and conn_id is not null and sql_context is not null and cross_type is not null) k on s.sql_id = k.sql_id order by s.conn_id";
	}

	public String addCusLang() {
		return "insert into Multi_Language_CUS (lang,lang_key,lang_value) values (?,?,?)";
	}

	public String deleteCusLang() {
		return "delete from Multi_Language_CUS where lang_key = ?";
	}

	public String deleteAllLegendCusLang() {
		return "delete from Multi_Language_CUS where lang_key like ?";
	}
	
	public String deleteCusLangWithHead() {
		return "delete from Multi_Language_CUS " + " where exists (select 'b' from Sql_Column_Info b "
				+ "         where sql_id = ? and Multi_Language_CUS.lang_key = b.col_lang_key)  or lang_key = ?";

	}

	public String getAllLights() {
		return "select icon_id,icon_name,icon_map_key from Icon_Info order by icon_id";
	}

	public String addCusMenu() {
		return "insert into Menu_CUS (func_id,lang_key,parent_id,func_url,sort_id,is_program,visible,package) values (?,?,?,?,?,?,?,?)";
	}

	public String getCusMenuParentID() {
		return "select parent_id from Menu_CUS where func_id = ?";
	}

	public String deleteCusMenu() {
		return "delete from Menu_CUS where func_id = ?";
	}

	public String addSqlRelation() {
		return "insert into Sql_Column_Relation (sql_id,col_id,relation_id,target_sql_id,target_col_id,seq) values (?,?,?,?,?,?)";
	}

	public String deleteSqlRelation() {
		return "delete from Sql_Column_Relation where sql_id = ?";
	}

	public String deleteSqlRelation_col() {
		return "delete from Sql_Column_Relation where sql_id = ? and col_id = ?";
	}

	public String getSqlRelation() {
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

	public String getSqlColumns() {
		return "select a.sql_id, a.col_id, case when b.lang_value is null then a.col_lang_key else "
				+ "           b.lang_value end as col_name,a.calc_rule from Sql_Column_Info a "
				+ "  left join (select * from Multi_Language "
				+ "              where lang = ?) b on a.col_lang_key = b.lang_key order by sql_id, col_id";

	}

	public String addSqlCross2() {
		return "insert into Sql_Cross_Database (sql_id,cross_id,conn_id,cross_type,sql_context,group_by,order_by,cross_seq) values (?,?,?,?,?,?,?,?)";
	}

	public String deleteSqlCross2() {
		return "delete from Sql_Cross_Database where sql_id = ?";
	}

	public String addSqlJoinKeys() {
		return "insert into Sql_Cross_Join_Key (sql_id,cross_id,key_id,key_belong1,key_col1,key_belong2,key_col2) values (?,?,?,?,?,?,?)";
	}

	public String deleteSqlJoinKeys() {
		return "delete from Sql_Cross_Join_Key where sql_id = ?";
	}

	public String getCrossMain() {
		return "select * from (select * from Sql_Cross_Database where sql_id = ? and conn_id is not null and sql_context is not null and cross_type is not null) a left join "
				+ "(select * from Sql_Cross_Join_Key where sql_id = ?) b on a.cross_id = b.cross_id "
				+ "order by a.cross_seq,b.key_id";
	}

	public String addSqlCross() {
		return "insert into Sql_Cross_Database (sql_id,cross_id,conn_id,cross_type,sql_context,group_by,order_by,join_key_set1,join_key_set2,cross_seq) values (?,'C01',?,?,?,?,?,?,?,'1')";
	}

	public String deleteSqlCross() {
		return "delete from Sql_Cross_Database where sql_id = ?";
	}

	public String getSqlCross() {
		return "select * from Sql_Cross_Database where sql_id = ? and conn_id is not null and sql_context is not null and cross_type is not null order by cross_seq";
	}

	public String addRoleRule() {
		return "insert into Role_Rule_Info (conn_id,role_id,func_id,can_edit) values (?,'RSYS',?,'0')";
	}

	public String deleteRoleRule() {
		return "delete from Role_Rule_Info where role_id = 'RSYS' and func_id = ?";
	}

	public String deleteUserRoleRule() {
		return "delete from User_Rule_Info where user_id = 'DS' and func_id = ?";
	}

	public String deleteFuncRoleRule() {
		return "delete from Role_Rule_Info where func_id = ?";
	}

	public String deleteFuncUserRoleRule() {
		return "delete from User_Rule_Info where func_id = ?";
	}

	public String copyStep1() {
		String sql = null;
		sql = "insert into Sql_Info (sql_id,sql_name ,sql_context,group_by,order_by,legend,use_marquee,marquee_location,marquee_type,marquee_refresh_gap,auto_refresh,refresh_gap,use_popup,popup_dir,popup_refresh_gap) "
				+ "select ? sql_id, ? sql_name ,sql_context,group_by,order_by,legend,use_marquee,marquee_location,marquee_type,marquee_refresh_gap,auto_refresh,refresh_gap,use_popup,popup_dir,popup_refresh_gap from Sql_Info where sql_id = ?";
		return sql;
	}

	public String copyStep2() {
		String sql = null;
		sql = "insert into Sql_Column_Info (sql_id,col_id,col_lang_key,col_type,col_ori_name,col_width,col_seq,visible,locked,config_value,is_popup,popup_title) "
				+ "select ? sql_id,col_id,? + '_' + col_id col_lang_key,col_type,col_ori_name,col_width,col_seq,visible,locked,config_value,is_popup,popup_title from Sql_Column_Info where sql_id = ?";
		return sql;
	}

	public String copyStep2_oracle() {
		String sql = null;
		sql = "insert into Sql_Column_Info (sql_id,col_id,col_lang_key,col_type,col_ori_name,col_width,col_seq,visible,locked,config_value,is_popup,popup_title) "
				+ "select ? sql_id,col_id,? || '_' || col_id col_lang_key,col_type,col_ori_name,col_width,col_seq,visible,locked,config_value,is_popup,popup_title from Sql_Column_Info where sql_id = ?";
		return sql;
	}

	public String copyStep3() {
		String sql = null;
		sql = "insert into Sql_Conn_Mapping (sql_id,conn_id) select ? sql_id,conn_id from Sql_Conn_Mapping where sql_id = ?";
		return sql;
	}

	public String copyStep4() {
		String sql = null;
		sql = "insert into Sql_Column_Relation (sql_id,col_id,relation_id,target_sql_id,target_col_id,seq) select ? sql_id,col_id,relation_id,target_sql_id,target_col_id,seq from Sql_Column_Relation where sql_id = ?";
		return sql;
	}

	public String copyStep5() {
		String sql = null;
		sql = "insert into Sql_Cross_Database (sql_id,cross_id,conn_id,cross_type,sql_context,group_by,order_by,join_key_set1,join_key_set2,cross_seq) "
				+ "select ? sql_id,cross_id,conn_id,cross_type,sql_context,group_by,order_by,join_key_set1,join_key_set2,cross_seq from Sql_Cross_Database where sql_id = ? and conn_id is not null and sql_context is not null and cross_type is not null";
		return sql;
	}

	public String copyStep6() {
		String sql = null;
		sql = "insert into Multi_Language_CUS (lang,lang_key,lang_value) "
				+ "select a.lang,? + '_' + b.col_id lang_key,a.lang_value from Multi_Language_CUS a join (select col_lang_key,col_id from Sql_Column_Info where sql_id = ?) b on a.lang_key = b.col_lang_key";
		return sql;
	}

	public String copyStep6_oracle() {
		String sql = null;
		sql = "insert into Multi_Language_CUS (lang,lang_key,lang_value) "
				+ "select a.lang,? || '_' || b.col_id lang_key,a.lang_value from Multi_Language_CUS a join (select col_lang_key,col_id from Sql_Column_Info where sql_id = ?) b on a.lang_key = b.col_lang_key";
		return sql;
	}

	public String copyStep7() {
		String sql = null;
		sql = "insert into Multi_Language_CUS (lang,lang_key,lang_value) "
				+ "select lang,REPLACE(lang_key,?,?),lang_value from Multi_Language_CUS where lang_key like ?";
		return sql;
	}

	public String copyStep8() {
		String sql = null;
		sql = "insert into Sql_Cross_Join_Key (sql_id,cross_id,key_id,key_belong1,key_col1,key_belong2,key_col2) "
				+ "select ? sql_id,cross_id,key_id,key_belong1,key_col1,key_belong2,key_col2 from Sql_Cross_Join_Key where sql_id = ?";
		return sql;
	}

	public String getPwd() {
		String sql = null;
		sql = "select user_pwd from User_Info where user_id = ?";
		return sql;
	}

	public String getAllUsers() {
		String sql = null;
		sql = "select user_id,user_name from User_Info order by user_id";
		return sql;
	}

	public String getCusUrl() {
		String sql = null;
		sql = "select func_url from Func_CUS where package = 'EKB' and func_id = "
				+ "(select case  dataexist when 0 then 'ALL' else ? end from "
				+ "(select count('b') dataexist from Func_CUS where package = 'EKB' and func_id = ?)a)";

		return sql;
	}

}
