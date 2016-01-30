package com.dsc.dci.sqlcode.configs;

public class sqlGroupConfig {
	public String allData(String filter) {
		return "select * from Group_Info where 1=1 " + filter + " order by group_id";
	}

	public String deleteData() {
		return "delete from Group_Info where group_id = ?";
	}

	public String addData() {
		return "insert into Group_Info (group_name,visible , group_desc,group_id) values (?,?,?,?)";
	}

	public String updateData() {
		return "update Group_Info set group_name = ?,visible = ? , group_desc = ? where group_id = ?";
	}

	public String maxID() {
		return "select Max(group_id) from Group_Info where group_id not in ('GSYS' , 'GUSR')";
	}

	public String groupRoles() {
		return "select '1' tp, role_id, role_name, role_desc from Role_Info a where not exists (select 'b' "
				+ " from Group_Role_Mapping b where group_id = ? and a.role_id = b.role_id) "
				+ "union all " 
				+ "select '2', a.role_id, role_name, role_desc from Group_Role_Mapping a "
				+ " join Role_Info b on a.role_id = b.role_id and group_id = ? "
				+"order by 1,2";
	}

	public String deleteGroupRoleMapping() {
		return "delete from Group_Role_Mapping where group_id = ?";
	}

	public String addGroupRoleMapping() {
		return "insert into Group_Role_Mapping (group_id, role_id) values (?,?)";
	}
	
	public String copyStep1(){
		String sql = null;
		sql = "insert into Group_Info (group_id,group_name,group_desc,visible) select ? , ? , ? , visible from Group_Info where group_id = ?";
		return sql;
	}
	
	public String copyStep2(){
		String sql = null;
		sql = "insert into Group_Role_Mapping (role_id,group_id)  select role_id,? from Group_Role_Mapping where group_id = ?";
		return sql;
	}

}
