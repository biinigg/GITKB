package com.dsc.dci.sqlcode.main;

public class sqlIndex {

	public String getDBList() {
		String sql = null;

		sql = "select c.conn_id, c.conn_name from (select distinct conn_id from (select * "
				+ "                    from User_Roles where user_id = ?) a "
				+ "           join Role_Rule_Info b on a.role_id = b.role_id union "
				+ "         select conn_id from User_Rule_Info where user_id = ?) conns join "
				+ "(select case conn_id when 'CSYS' then '$$$$' else conn_id end sort_id, conn_id, conn_name "
				+ "          from Conn_Info where visible = 1) c on conns.conn_id = c.conn_id " + " order by c.sort_id";

		return sql;
	}

	/*
	 * public String getAuthList() { String sql = null;
	 * 
	 * sql =
	 * "select menu.func_id, parent_id, func_url, sort_id, can_edit, roles.conn_id, conn_name "
	 * +
	 * "  from (select func_id, parent_id, func_url, sort_id, lang_key from Menu "
	 * +
	 * "          where visible = 1) menu join (select a.func_id, can_edit, b.conn_id "
	 * + "          from (select func_id, can_edit from User_Rule_Info " +
	 * "                  where user_id = ?) a join (select * " +
	 * "                 from User_Rule_Use_DB " +
	 * "                where user_id = ?) b on a.func_id = b.func_id " +
	 * "        union all " + "        select func_id, can_edit, conn_id " +
	 * "          from (select aa.func_id, bb.can_edit, aa.conn_id from (select * "
	 * + "                            from Role_Rule_Use_DB a " +
	 * "                           where exists (select 'b' " +
	 * "                                    from User_Roles b " +
	 * "                                   where user_id = ? and a.role_id = b.role_id)) aa "
	 * + "                   join (select func_id, max(can_edit) can_edit " +
	 * "                          from Role_Rule_Info a " +
	 * "                         where exists (select 'b' " +
	 * "                                  from User_Roles b " +
	 * "                                 where user_id = ? and a.role_id = b.role_id) "
	 * +
	 * "                         group by func_id) bb on aa.func_id = bb.func_id) as aa "
	 * + "         where not exists (select 'b' from User_Rule_Info c " +
	 * "                 where user_id = ? and aa.func_id = c.func_id)) roles "
	 * +
	 * "  on menu.func_id = roles.func_id join Conn_Info conn on roles.conn_id = conn.conn_id "
	 * + "   order by conn_id,parent_id,sort_id";
	 * 
	 * return sql; }
	 */

	public String getUserMenu_ori() {
		String sql = null;

		sql = "select func_id,parent_id,sort_id,func_url,is_program,lang_key,lang_value,can_edit,package "
				+ "  from (select aaa.*, bbb.can_edit "
				+ "           from (select a.*, case when b.lang_value is null then a.lang_key when b.lang_value = '' then a.lang_key else lang_value end lang_value "
				+ "                    from (select * "
				+ "                             from User_Menu "
				+ "                            where user_id = case when exists (select 'b' "
				+ "                                     from User_Menu "
				+ "                                    where user_id = ? and conn_id = ?) then ? else 'def' end "
				+ "                              and conn_id = case when exists (select 'b' "
				+ "                                     from User_Menu "
				+ "                                    where user_id = ? and conn_id = ?) then ? else 'all' end) a "
				+ "                    left join (SELECT lang_key, lang_value, lang "
				+ "                                FROM Multi_Language_CUS "
				+ "                               where lang = ? "
				+ "                              union "
				+ "                              SELECT lang_key, lang_value, lang "
				+ "                                FROM Multi_Language_STD a "
				+ "                               where lang = ? and not exists "
				+ "                               (select 'b' "
				+ "                                        from Multi_Language_CUS b "
				+ "                                       where b.lang = a.lang and b.lang_key = a.lang_key)) b on a.lang_key = "
				+ "                                                                                                b.lang_key) aaa "
				+ "           left join (select aa.func_id, aa.can_edit, bb.conn_id "
				+ "                       from (select func_id, MAX(can_edit) can_edit "
				+ "                                from Role_Rule_Info a "
				+ "                               where exists " + "                               (select 'b' "
				+ "                                        from User_Roles b "
				+ "                                       where user_id = ? and a.role_id = b.role_id) "
				+ "                               group by func_id) aa "
				+ "                       join (select func_id, conn_id "
				+ "                              from Role_Rule_Use_DB a "
				+ "                             where exists (select 'b' "
				+ "                                      from User_Roles b "
				+ "                                     where user_id = ? and conn_id = ? and "
				+ "                                           a.role_id = b.role_id)) bb "
				+ "                         on aa.func_id = bb.func_id) bbb "
				+ "                  on 1 = case when aaa.func_id = bbb.func_id then 1 else 2 end) data "
				+ " where is_program = 0 or can_edit = 0 or can_edit = 1" + " order by parent_id,sort_id";

		return sql;
	}

	public String getUserMenu() {
		String sql = null;

		sql = "select menu.* , case when lang_value is null or lang_value = '' then menu.lang_key else lang_value end lang_value  from "
				+ "(select d.*, e.can_edit "
				+ "  from (select case cnt when 0 then 'def' else user_id end user_id, case cnt when 0 then 'all' else conn_id end conn_id "
				+ "           from (select aa.*, case when c.user_id is null or c.user_id = '' then 0 else 1 end cnt "
				+ "                    from (select * "
				+ "                             from (select user_id "
				+ "                                      from User_Info "
				+ "                                     where user_id = ?) a "
				+ "                             join (select conn_id "
				+ "                                    from Conn_Info "
				+ "                                   where conn_id = ?) b on 1 = 1) aa "
				+ "                    left join (select distinct user_id, conn_id "
				+ "                                from Tree_CUS) c on aa.user_id = c.user_id and "
				+ "                                                    aa.conn_id = c.conn_id) aaa) aaaa "
				+ "  join User_Menu d on aaaa.user_id = d.user_id and aaaa.conn_id = d.conn_id "
				+ "  left join (select func_id, can_edit "
				+ "               from Group_Funcs "
				+ "              where user_id = ? and conn_id = ? "
				+ " union all select func_id, 0  from Menu_STD where func_id = 'TreeLogout') e on d.func_id = e.func_id "
				+ " where 1 = case is_program when 1 then case when can_edit is not null then 1 else 0 end else 1 end) menu "
				+ " left join (select * from Multi_Language where lang = ?) lang on menu.lang_key = lang.lang_key "
				+ " order by parent_id,sort_id";

		return sql;
	}

	public String getFavoriteMenu() {
		String sql = null;

		sql = "select menu.* , case when lang_value is null or lang_value = '' then menu.lang_key else lang_value end lang_value "
				+ "  from (select a.*, b.can_edit "
				+ "           from (select * "
				+ "                    from User_Favorite "
				+ "                   where user_id = ? and conn_id = ?) a "
				+ "           left join (select func_id, can_edit "
				+ "                       from Group_Funcs "
				+ "                      where user_id = ? and conn_id = ? "
				+ " union all select func_id, 0  from Menu_STD where func_id = 'TreeLogout') b on a.func_id = "
				+ "                                                                       b.func_id "
				+ "          where 1 = case is_program when 1 then case when can_edit is not null then 1 else 0 end else 1 end) menu "
				+ "  left join (select * "
				+ "               from Multi_Language "
				+ "              where lang = ?) lang on menu.lang_key = lang.lang_key "
				+ " order by parent_id, sort_id";

		return sql;
	}

	public String addFavoriteMenu() {
		String sql = null;

		sql = "insert into Tree_Favorite (user_id,conn_id,func_id,parent_id,lang_key,sort_id,is_program) values (?,?,?,?,?,?,?) ";

		return sql;
	}

	public String delFavoriteMenu() {
		String sql = null;

		sql = "delete from Tree_Favorite where user_id = ? and conn_id = ?";

		return sql;
	}

	public String delCusMenu() {
		String sql = null;

		sql = "delete from Tree_CUS where user_id = ? and conn_id = ?";

		return sql;
	}

	public String addCusMenu() {
		String sql = null;

		sql = "insert into Tree_CUS (user_id,conn_id,func_id,parent_id,lang_key,sort_id,is_program) values (?,?,?,?,?,?,?)";

		return sql;
	}

	public String delCusLang() {
		String sql = null;

		sql = "delete from Multi_Language_CUS where lang = ? and lang_key = ?";

		return sql;
	}

	public String addCusLang() {
		String sql = null;

		sql = "insert into Multi_Language_CUS (lang,lang_key,lang_value) values (?,?,?)";

		return sql;
	}

	public String getSqlConnMapping() {
		return "select 'b' from Sql_Conn_Mapping where sql_id = ? and conn_id = ?";
	}

	public String hasFavor() {
		return "select 'b' from User_Favorite where user_id = ? and conn_id = ? and is_program = '1'";

	}

	public String getMarqueeData() {
		return "select * from Sys_Marquee_Data where start_date <= ? and end_date >= ? order by start_date";
	}

	public String hasDefCus() {
		return "select '1' id,'b' from Tree_CUS where user_id = ? and conn_id = ?  union all "
				+ "select '2' id,'b'  from Tree_Favorite where user_id = ? and conn_id = ?";

	}

	public String getAllFuncs() {
		return "select d.*, e.can_edit from (select * from User_Menu "
				+ "          where user_id = 'def' and conn_id = 'all') d left join (select func_id, can_edit "
				+ "               from Group_Funcs where user_id = ? and conn_id = ? ) e on d.func_id = e.func_id "
				+ " where is_program = 1 and can_edit is not null";

	}
}
