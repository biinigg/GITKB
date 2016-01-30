package com.dsc.dci.sqlcode.configs;

public class sqlConnConfig {
	public String allData(String filter) {
		return "select * from Conn_Info where 1=1 " + filter + " order by conn_id";
	}

	public String deleteData() {
		return "delete from Conn_Info where conn_id = ?";
	}

	public String addData() {
		return "insert into Conn_Info (conn_name,conn_desc,db_addr,db_name,db_user,db_pwd,db_type,db_port,visible,conn_id) values (?,?,?,?,?,?,?,?,?,?)";
	}

	public String updateData() {
		return "update Conn_Info set conn_name = ?, conn_desc = ? , db_addr = ?, db_name = ?, db_user = ? " + 
	           ", db_pwd = ?, db_type = ?, db_port = ?, visible = ? where conn_id = ?";
	}

	public String maxID() {
		return "select Max(conn_id) from Conn_Info where conn_id <> 'CSYS'";
	}

	public String nameCheck() {
		return "select 'b' from Conn_Info where conn_id <> ? and conn_name = ?";
	}
}
