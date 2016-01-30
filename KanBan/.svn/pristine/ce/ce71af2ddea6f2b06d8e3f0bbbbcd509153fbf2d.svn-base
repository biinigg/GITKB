package com.dsc.dci.sqlcode.configs;

public class sqlSysDBConfig {
	public String deleteSysConn() {
		return "delete from Conn_Info where conn_id = 'CSYS'";
	}

	public String addSysConn() {
		return "insert into Conn_Info (conn_name,conn_desc,db_addr,db_name,db_user,db_pwd,db_type,db_port,visible,conn_id) values ('system_conn','system connection is read only',?,?,?,?,?,?,'1','CSYS')";
	}

	public String deleteSysIcon() {
		return "delete from Icon_Info where icon_type = 'SYS' or icon_id <= ?";
	}

	public String addSysIcon() {
		return "insert into Icon_Info (icon_id,icon_name,icon_map_key,icon_path,icon_type) values (?,?,?,?,'SYS')";
	}
}
