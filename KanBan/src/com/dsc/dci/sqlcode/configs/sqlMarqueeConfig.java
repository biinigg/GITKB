package com.dsc.dci.sqlcode.configs;

public class sqlMarqueeConfig {
	public String getAllData(String filter) {
		return "select * from Marquee_Info where 1=1 " + filter + " order by marquee_id";
	}

	public String deleteData() {
		return "delete from Marquee_Info where marquee_id = ?";
	}

	public String addData() {
		return "insert into Marquee_Info (marquee_type,message,start_date,end_date,marquee_id) values (?,?,?,?,?)";
	}

	public String updateData() {
		return "update Marquee_Info set marquee_type = ?, message = ? , start_date = ?, end_date = ? where marquee_id = ?";
	}

	public String maxID() {
		return "select Max(marquee_id) from Marquee_Info where marquee_id like ?";
	}

	public String getConns() {
		return "select a.marquee_id,a.conn_id,b.conn_name from Marquee_Conn_Mapping a left join Conn_Info b on a.conn_id = b.conn_id order by marquee_id,a.conn_id";
	}

	public String getSqls() {
		return "select a.marquee_id,a.sql_id,b.sql_name from Marquee_Sql_Mapping a left join Sql_Info b on a.sql_id = b.sql_id order by marquee_id,a.sql_id";
	}

	public String getConnList() {
		return "select conn_id,conn_name from Conn_Info where conn_id <> 'CSYS' order by conn_id";
	}

	public String getSqlList() {
		return "select sql_id,sql_name from Sql_Info order by sql_id";
	}

	public String addConnMapping() {
		return "insert into Marquee_Conn_Mapping (marquee_id, conn_id) values (?,?)";
	}

	public String addSqlMapping() {
		return "insert into Marquee_Sql_Mapping (marquee_id, sql_id) values (?,?)";
	}

	public String deleteConnWithHead() {
		return "delete from Marquee_Conn_Mapping where marquee_id = ?";
	}

	public String deleteSqlWithHead() {
		return "delete from Marquee_Sql_Mapping where marquee_id = ?";
	}
}
