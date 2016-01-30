package com.dsc.dci.customer.c02420302.jweb.patchs;

import java.util.ArrayList;

public class sqlCusPatch02420302 {
	private ArrayList<String> sqls;

	public sqlCusPatch02420302() {
		this.sqls = new ArrayList<String>(0);
		setSqls();
	}

	private void setSqls() {
		this.sqls
		.add("insert into Func_CUS (package,func_id,func_url) values ('EKB','INDKB','./../../Customer/Funcs/IndKanBan02420302.jsp')");
				this.sqls
		.add(" insert into Role_Rule_Info (conn_id, role_id, func_id, can_edit)  values ( 'CSYS','RSYS','CombineConfig02420302',1) ");
		this.sqls
		.add(" insert into Menu_CUS(func_id, lang_key, parent_id, func_url, sort_id, is_program, visible, package) values ('CombineConfig02420302','CombineConfig02420302','Configs','./../../Customer/Configs/CombineConfig02420302.jsp',15,1,1,'SYS') ");
		this.sqls.add(" create table CombineKB(ckb_id char(5)not null,ckb_name nvarchar(150),head_kbid char(5)not null,body_kbid char(5)not null,combinecolumn nvarchar(150)not null,conn_id varchar(8) not null,"
				+ " CONSTRAINT ckb_id PRIMARY KEY CLUSTERED (ckb_id ASC)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]) ");
	}

	public ArrayList<String> getSQLCollection() {
		return this.sqls;
	}
}
