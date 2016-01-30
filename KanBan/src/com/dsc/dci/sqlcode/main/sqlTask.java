package com.dsc.dci.sqlcode.main;

public class sqlTask {
	public String getAllConns() {
		String sql = null;
		sql = "select * from Conn_Info where visible = '1' order by conn_id";
		return sql;
	}
}
