package com.dsc.dci.customer.c02420302.sqlcode.funcs.configs;

public class sqlCombineConfig02420302 {
	public String allData(String filter) {
		return "select * from CombineKB  where 1=1 " + filter + " order by ckb_id";
	}
	public String connData() {
		return " SELECT  conn_id, conn_name FROM  Conn_Info WHERE  (visible = '1') AND (conn_id <> 'CSYS') ";
	}
	public String getAllUsers() {
		String sql = null;
		sql = "select user_id,user_name from User_Info order by user_id";
		return sql;
	}
	public String getPwd() {
		String sql = null;
		sql = "select user_pwd from User_Info where user_id = ?";
		return sql;
	}
	public String columnData() {
		return " select col_id from  Sql_Column_Info "+
				" where sql_id = ? and col_type='CHAR'  and col_id in (select col_id from  Sql_Column_Info where sql_id = ? and col_type='CHAR') ";
	}		
	public String kbData() {
		return "  select distinct sql_id,sql_name,lang_value from Sql_Info join Role_Rule_Info on sql_id = func_id and conn_id = ?   join Multi_Language_CUS on lang_key = sql_id where lang=? ";
	}
	public String deleteData() {
		return " delete from CombineKB where ckb_id = ? ; ";
	}
	public String addData() {
		return "insert  into CombineKB  (ckb_name, head_kbid, body_kbid, combinecolumn, conn_id,ckb_id) values (?,?,?,?,?,?)";
	}

	public String updateData() {
		return "update CombineKB set ckb_name = ?,head_kbid = ? , body_kbid = ?, combinecolumn = ? , conn_id = ?   where ckb_id = ?";
	}
//	public String[] addCusKanBanData() {
//		String[] sql=new String[6];
//		sql[0]=" delete Role_Rule_Info where conn_id=? and role_id='RSYS' and func_id=? and can_edit='0' ";
//		sql[1]=		" delete Sql_Conn_Mapping where sql_id=? and conn_id = ? ";
//		sql[2]=		" delete Menu_CUS where func_id=? ";
//		sql[3]=		" insert into Role_Rule_Info (conn_id, role_id, func_id, can_edit) values (?,'RSYS',?,'0') ";
//		sql[4]=		" insert into Sql_Conn_Mapping (sql_id, conn_id) values (?,?) ";
//		sql[5]=		" insert into Menu_CUS (func_id, lang_key, parent_id, func_url, sort_id, is_program, visible, package) values (?,?,NULL,'./../../Customer/Funcs/EKB/KanBan02420302.jsp','1','1','1','EKB') ";
//		return sql;
//	}
	public String delCKBRole_Rule_Info() {
		String sql;
		sql=  " delete Role_Rule_Info where conn_id=? and role_id='RSYS' and func_id=? and can_edit='0'";
		return sql;
	}
	public String delCKBSql_Conn_Mapping() {
		String sql;
		sql=  "delete Sql_Conn_Mapping where sql_id=? and conn_id = ?";
		return sql;
	}
	public String delCKBMenu_CUS() {
		String sql;
		sql=  " delete Menu_CUS where func_id=?";
		return sql;
	}
	public String addCKBRole_Rule_Info() {
		String sql;
		sql=  " insert into Role_Rule_Info (conn_id, role_id, func_id, can_edit) values (?,'RSYS',?,'0')";
		return sql;
	}
	public String addCKBSql_Conn_Mapping() {
		String sql;
		sql=  "  insert into Sql_Conn_Mapping (sql_id, conn_id) values (?,?) ";
		return sql;
	}
	public String addCKBMenu_CUS() {
		String sql;
		sql=  "  insert into Menu_CUS (func_id, lang_key, parent_id, func_url, sort_id, is_program, visible, package) values (?,?,NULL,'./../../Customer/Funcs/EKB/KanBan02420302.jsp','1','1','1','EKB') ";
		return sql;
	}
//	public String[] updateCusKanBanData() {
//		String[] sql=new String[3];
//		sql[0]=      "update Role_Rule_Info set conn_id = ?  where func_id = ?";
//		sql[1]=		" update Sql_Conn_Mapping set conn_id = ?  where sql_id = ? ";
//		sql[2]=		"update Menu_CUS set lang_key=?  where func_id = ? ";
//		return sql;
//	}
	public String updCKBRole_Rule_Info() {
		String sql;
		sql=  "update Role_Rule_Info set conn_id = ?  where func_id = ?";
		return sql;
	}
	public String updCKBSql_Conn_Mapping() {
		String sql;
		sql=	" update Sql_Conn_Mapping set conn_id = ?  where sql_id = ? ";
		return sql;
	}
	public String updCKBMenu_CUS() {
		String sql;
		sql=	"update Menu_CUS set lang_key=?  where func_id = ? ";
		return sql;
	}
	public String deleteCKBRole_Rule_Info() {
		String sql;
		sql=      " delete from Role_Rule_Info where func_id = ? ;";
		return sql;
	}
	public String deleteCKBSql_Conn_Mapping() {
		String sql;
		sql=      " delete from Sql_Conn_Mapping where sql_id = ? ;";
		return sql;
	}
	public String deleteCKBMenu_CUS() {
		String sql;
		sql=      "delete from Menu_CUS where func_id = ? ;";
		return sql;
	}
	
	public String maxID() {
		return " select Max(ckb_id) from CombineKB  ";
	}

	public String bodyDelWithHead(){
		return "delete from Role_Rule_Info where role_id = ?";
	}
	
	public String getInitData(){
		return 
				"select '1' tp, conn_id value, conn_name label ,'' package" +
				"  from Conn_Info " + 
				"union all " + 
				//"select '2' tp, role_id value, role_name label " + 
				//"  from Role_Info " + 
				//"union all " + 
				"select '2' tp, func_id value, " + 
				"       case " + 
				"          when b.lang_value is null or b.lang_value = '' then " + 
				"           a.lang_key " + 
				"          else " + 
				"           b.lang_value " + 
				"        end label , package" + 
				"  from (select func_id, lang_key, package from Menu where is_program = '1') a " + 
				"  left join Multi_Language b on a.lang_key = b.lang_key and b.lang = ? " + 
				" order by 1, 2";
	}
	public String delGrid_Format() {
		return " delete from Grid_Format where grid_id=?  ";
	}
	public String delSql_Advance_Condition() {
		return " delete from Sql_Advance_Condition where sql_id=?  ";
	}
	public String delSql_Condition() {
		return " delete from Sql_Condition where sql_id=?  ";
	}
	public String delSql_Init_Params() {
		return " delete from Sql_Init_Params where sql_id=?  ";
	}
	public String delSql_Format() {
		return " delete from Sql_Format where sql_id=?  ";
	}
}
