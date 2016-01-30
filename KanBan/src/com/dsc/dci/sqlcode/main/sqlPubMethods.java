package com.dsc.dci.sqlcode.main;

public class sqlPubMethods {
	public String getMultiLanguage() {
		String sql = null;
		sql = "SELECT lang_key, lang_value, lang " + "  FROM Multi_Language_CUS " + "union "
				+ "SELECT lang_key, lang_value, lang " + "  FROM Multi_Language_STD a " + " where not exists "
				+ " (select 'b' " + "          from Multi_Language_CUS b "
				+ "         where b.lang = a.lang and b.lang_key = a.lang_key) " + " order by lang";

		return sql;
	}

	public String getSystemConfig() {
		String sql = null;

		sql = "SELECT * from System_Config";

		return sql;
	}

	public String getGridFormat() {
		String sql = null;

		sql = "select * from Grid_Format where user_id = (select case when cnt = 0 then '$$$$$' else ? end "
				+ "                    from (select count('b') cnt from Grid_Format "
				+ "                            where user_id = ?) a) and grid_id = ? "
				+ " order by col_index,col_visible";

		return sql;
	}

	public String deleteGridFormat() {
		String sql = null;

		sql = "delete from Grid_Format where user_id  = ? and grid_id = ?";

		return sql;
	}

	public String addGridFormat() {
		String sql = null;

		sql = "insert into Grid_Format (user_id,grid_id,col_id,col_index,col_width,col_visible) values (?,?,?,?,?,?)";

		return sql;
	}

	public String getIconPath() {
		String sql = null;

		sql = "select icon_path from Icon_Info where icon_id = ?";

		return sql;
	}

	public String getRegistres() {
		return "select * from Registers";
	}

	public String addSTDLanguage() {
		return "insert into Multi_Language_STD (lang,lang_key,lang_value) values (?,?,?)";
	}

	public String truncateSTDLanguage() {
		return "truncate table Multi_Language_STD";
	}

	public String addDBVersion() {
		return "insert into System_Config (config_id,config_value,config_desc,config_type) values ('SYSVersion',?,'系統版本','0')";
	}

	public String updDBVersion() {
		return "update System_Config set config_value = ? where config_id = 'SYSVersion'";
	}
}
