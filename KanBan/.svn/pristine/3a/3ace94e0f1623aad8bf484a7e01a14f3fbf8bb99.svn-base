package com.dsc.dci.sqlcode.configs;

import com.dci.jweb.DCIEnums.Database.DBType;

public class sqlIconConfig {
	public String getAllData(String filter) {
		return 
				"select aa.* from (select a.*,b.lang_value from Icon_Info a " +
				"           left join (select * " + 
				"                       from Multi_Language " + 
				"                      where lang = ?) b on a.icon_name = b.lang_key) aa where 1=1 " + filter + " order by icon_id";
	}

	public String getAllGroups() {
		return "select group_id,group_name,visible from Icon_Info order by icon_id,icon_map_key";
	}

	public String deleteData() {
		return "delete from Icon_Info where icon_id = ?";
	}

	public String addData() {
		return "insert into Icon_Info (icon_name,icon_map_key,icon_path,icon_type,icon_id) values (?,?,?,?,?)";
	}

	public String updateData() {
		return "update Icon_Info set icon_name = ?,icon_map_key = ?,icon_path = ?,icon_type = ? where icon_id = ?";
	}

	public String maxID() {
		return "select Max(icon_id) from Icon_Info";
	}

	public String maxKey(DBType dbtp) {
		String sql = null;
		if (dbtp == DBType.Oracle) {
			sql = "select Max(to_number(icon_map_key)) from Icon_Info";
		} else {
			sql = "select Max(convert(int,icon_map_key)) from Icon_Info";
		}
		return sql;
	}

	public String iconMapKey() {
		return "select 'b' from Icon_Info where icon_id <> ? and icon_map_key = ?";
	}

	public String addCusLang() {
		return "insert into Multi_Language_CUS (lang,lang_key,lang_value) values (?,?,?)";
	}

	public String deleteCusLang() {
		return "delete from Multi_Language_CUS where lang_key = ?";
	}
}
