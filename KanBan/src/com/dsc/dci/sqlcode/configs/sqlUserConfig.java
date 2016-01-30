package com.dsc.dci.sqlcode.configs;

public class sqlUserConfig {
	public String getAllUserInfo(String filter) {
		return "select * from User_Info where 1=1 " + filter + " order by user_id";
	}

	public String getInitData() {
		return 
				"select '1' tp, conn_id value, conn_name label, 1 visible,'' package " +
				"  from Conn_Info " + 
				"union all " + 
				"select '2' tp, group_id, group_name, visible,'' package " + 
				"  from Group_Info " + 
				"union all " + 
				"select '3' tp, func_id value, " + 
				"       case " + 
				"          when b.lang_value is null or b.lang_value = '' then " + 
				"           a.lang_key " + 
				"          else " + 
				"           b.lang_value " + 
				"        end label, 1 , package" + 
				"  from (select func_id,lang_key, package from Menu where is_program = '1') a " + 
				"  left join Multi_Language b on a.lang_key = b.lang_key and b.lang = ? " + 
				" order by 1, 4 desc, 2";

	}

	public String deleteData() {
		return "delete from User_Info where user_id = ?";
	}

	public String addData() {
		return "insert into User_Info (user_name,user_pwd,group_id,visible,user_id) values (?,?,?,?,?)";
	}

	public String updateData() {
		return "update User_Info set user_name = ?,user_pwd = ?,group_id = ?,visible = ? where user_id = ?";
	}

	public String bodyData() {
		return "select a.*,b.package from User_Rule_Info a left join Menu b on a.func_id = b.func_id where user_id = ? order by a.conn_id,a.func_id";
	}
	
	public String bodyAdd(){
		return "insert into User_Rule_Info (can_edit,conn_id,user_id,func_id) values (?,?,?,?)";
	}
	
	public String bodyUpd(){
		return "update User_Rule_Info set can_edit = ? where conn_id = ? and user_id = ? and func_id = ? ";
	}
	
	public String bodyDel(){
		return "delete from User_Rule_Info where conn_id = ? and user_id = ? and func_id = ? ";
	}
	
	public String bodyDelWithHead(){
		return "delete from User_Rule_Info where user_id = ?";
	}
}
