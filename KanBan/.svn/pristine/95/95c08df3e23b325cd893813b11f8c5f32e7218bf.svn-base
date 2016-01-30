package com.dsc.dci.sqlcode.funcs.apps;

public class sqlAppFunctions {
	public String getConnList() {
		String sql = null;
		sql = "select c.conn_id, c.conn_name from (select distinct conn_id from (select * "
				+ "                    from User_Roles where user_id = ?) a "
				+ "           join Role_Rule_Info b on a.role_id = b.role_id union "
				+ "         select conn_id from User_Rule_Info where user_id = ?) conns join "
				+ "(select conn_id, conn_name from Conn_Info where conn_id <> 'CSYS' and visible = 1) c "
				+ "on conns.conn_id = c.conn_id order by c.conn_id";

		// sql =
		// "select conn_id,conn_name from Conn_Info where conn_id <> 'CSYS' order by conn_id";

		return sql;
	}

	public String getKanbanList() {
		String sql = null;
		sql = "select a.func_id, b.lang_key func_name from (select func_id from Group_Funcs "
				+ "          where user_id = ? and conn_id = ?) a join Menu b on a.func_id = b.func_id "
				+ " order by b.sort_id";

		return sql;
	}

	public String getAppCols() {
		String sql = null;
		sql = "select a.col_id, b.lang_value from (select col_id, col_lang_key from Sql_Column_Info "
				+ "          where sql_id = ? and is_app_col = 1) a left join (select lang_key, lang_value "
				+ "               from Multi_Language "
				+ "              where lang = ?) b on a.col_lang_key = b.lang_key";

		return sql;
	}

	public String getAppDtlCols() {
		String sql = null;
		sql = "select a.col_id, b.lang_value from (select col_id, col_lang_key from Sql_Column_Info "
				+ "          where sql_id = ? and is_app_dtl_col = 1) a left join (select lang_key, lang_value "
				+ "               from Multi_Language "
				+ "              where lang = ?) b on a.col_lang_key = b.lang_key";

		return sql;
	}
	

	public String getPushData() {
		String sql = null;
		sql = "select a.sql_id,a.user_id,a.msg,b.conn_id from Push_KanBan_Info a  join Sql_Conn_Mapping b on a.sql_id = b.sql_id and b.conn_id = case a.conn_id when '*****' then b.conn_id else a.conn_id end "+ 
              " order by a.sql_id ,b.conn_id";
		return sql;
	}
	
	public String getKanBanData() {
		String sql = null;
		sql = "select * from Sql_Info where sql_id = ?";
		return sql;
	}
	
}
