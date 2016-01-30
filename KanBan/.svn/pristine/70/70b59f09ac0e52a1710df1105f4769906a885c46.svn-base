package com.dsc.dci.sqlcode.configs;

public class sqlRoleConfig {
	public String allData(String filter) {
		return "select * from Role_Info where 1=1 " + filter + " order by role_id";
	}

	public String deleteData() {
		return "delete from Role_Info where role_id = ?";
	}

	public String addData() {
		return "insert into Role_Info (role_name, role_desc, visible, role_id) values (?,?,?,?)";
	}

	public String updateData() {
		return "update Role_Info set role_name = ?,role_desc = ? , visible = ? where role_id = ?";
	}

	public String maxID() {
		return "select Max(role_id) from Role_Info where role_id not in ('RSYS' , 'RUSR') ";
	}

	public String bodyData() {
		return "select a.*,b.package from Role_Rule_Info a left join Menu b on a.func_id = b.func_id where role_id = ? order by a.conn_id,a.func_id";
	}
	
	public String bodyAdd(){
		return "insert into Role_Rule_Info (can_edit,conn_id,role_id,func_id) values (?,?,?,?)";
	}
	
	public String bodyUpd(){
		return "update Role_Rule_Info set can_edit = ? where conn_id = ? and role_id = ? and func_id = ? ";
	}
	
	public String bodyDel(){
		return "delete from Role_Rule_Info where conn_id = ? and role_id = ? and func_id = ? ";
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
	
	public String copyStep1(){
		String sql = null;
		sql = "insert into Role_Info (role_id,role_name,role_desc,visible) select ? , ? , ? , visible from Role_Info where role_id = ?";
		return sql;
	}
	
	public String copyStep2(){
		String sql = null;
		sql = "insert into Role_Rule_Info (conn_id,role_id,func_id,can_edit)  select conn_id,?,func_id,can_edit from Role_Rule_Info where role_id = ?";
		return sql;
	}

}
