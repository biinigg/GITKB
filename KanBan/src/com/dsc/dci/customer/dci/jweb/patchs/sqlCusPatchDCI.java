package com.dsc.dci.customer.dci.jweb.patchs;

import java.util.ArrayList;

public class sqlCusPatchDCI {
	private ArrayList<String> sqls;

	public sqlCusPatchDCI() {
		this.sqls = new ArrayList<String>(0);
		setSqls();
	}

	private void setSqls() {
		//this.sqls.add("insert into Multi_Language_CUS (lang,lang_key,lang_value) values ('ttt','123','123')");
	}

	public ArrayList<String> getSQLCollection() {
		return this.sqls;
	}
}
