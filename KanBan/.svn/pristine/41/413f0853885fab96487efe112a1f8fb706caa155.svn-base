package com.dsc.dci.sqlcode.main;

public class sqlLogin {
	public String getPwd() {
		String sql = null;

		sql = "select a.*,b.group_name, b.visible group_visible from (select user_name, user_pwd, group_id, visible "
				+ "           from User_Info  where user_id = ?) a "
				+ "  join Group_Info b on a.group_id = b.group_id";

		return sql;
	}
}
