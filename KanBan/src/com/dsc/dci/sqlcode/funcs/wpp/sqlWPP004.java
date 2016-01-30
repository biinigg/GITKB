package com.dsc.dci.sqlcode.funcs.wpp;

public class sqlWPP004 {
	public String F001Sql() {
		String sql = null;
		sql = "select MB001 + '/' + MB002 label , MB001 value from CMSMB order by MB001";
		return sql;
	}

	public String F003_1Sql() {
		String sql = null;
		sql = "select MD001 + '/' + MD002 label , MD001 value from CMSMD order by MD001";
		return sql;
	}

	public String F003_2Sql() {
		String sql = null;
		sql = "select MA001 + '/' + MA002 label , MA001 value from PURMA order by MA001";
		return sql;
	}

	public String F006Sql() {
		String sql = null;
		sql = "select MC001 + '/' + MC002 label , MC001 value from CMSMC order by MC001";
		return sql;
	}

	public String F009Sql() {
		String sql = null;
		sql = "select isnull(MK002, '') + case isnull(MV002, '') "
				+ "  when '' then '' else '/' + MV002 end label, MK002 value from CMSMK a "
				+ "  left join CMSMV b on a.MK002 = b.MV002 where MK003 = '4'";

		return sql;
	}

	public String HeadSql(String F001, String F002, String F003) {
		String sql = null;
		sql = "select TA006,TA007,TA008,TA009,TA012,TA013,getdate() currdate from WPPTA where TA001 = '" + F001
				+ "' and TA002 = '" + F002 + "' and TA003 = '" + F003 + "' and TA005 = '2'";
		return sql;
	}

	public String BodySql(String F001, String F002, String F003, String F004, String F005, String F006, String F007,
			String F008, String F009, String F010) {
		String sql = null;

		sql = "select TG002, TG003, TG007, TG008, TG009, TG011, TG012, TG013, TG017, TG021, TG022, MB002, "
				+ "       MB003, MB004, MV002, TA006, TA007, TA015, TB010, TB021, ORDERID, "
				+ "       TG005 + '-' + TG006 MOID "
				+ "  from (select TG001, TG002, TG003, TG005, TG006, TG007, TG008, TG009, TG011, TG012, "
				+ "                case TG013 when 'Y' then 'R' else '' end TG013, TG016, TG017, TG018, TG019, TG020, "
				+ "                TG021, TG022, MB002, MB003, MB004, MV002 "
				+ "           from (select TG001, TG002, TG003, TG005, TG006, TG007, TG008, TG009, TG011, TG012, "
				+ "                         TG013, TG016, TG017, TG018, TG019, TG020, TG021, TG022, b.MB067, "
				+ "                         b.MB002, b.MB003, b.MB004, b.MV002 "
				+ "                    from (select TG001, TG002, TG003, TG005, TG006, TG007, TG008, TG009, TG011, "
				+ "                                  TG012, TG013, TG016, TG017, TG018, TG019, TG020, TG021, TG022 "
				+ "                             from WPPTG " + "                            where TG001 = '" + F001
				+ "' and TG016 = '" + F002 + "' and TG017 = '" + F003 + "' and TG005 <> '****' and TG004 = '" + F004 + "' ";

		if (F005.equals("Y")) {
			sql += " and TG003 = '" + F006 + "' ";
		}
		
		if (F010.equals("Y")) {
			sql += " and TG013 = 'Y' ";
		}
		
		if (!F007.equals("0")) {
			sql += " and TG022 = '" + F007 + "' ";
		}
		
		sql += ") a left join (select MB001, MB002, MB003, MB004, MB067, MV002 from INVMB mb "
				+ "                                left join CMSMV mv on mb.MB067 = mv.MV001) b on a.TG002 = b.MB001) c ";

		if (F008.equals("Y")) {
			sql += " where c.MB067 = '" + F009 + "' ";
		}
		
		sql += ") d left join (select TA001, TA002, TA006, TA007, TA015 - TA017 TA015 "
				+ "               from MOCTA) e on d.TG005 = e.TA001 and d.TG006 = e.TA002 "
				+ "  left join (select TB001, TB002, TB003, TB004, TB005, TB010, "
				+ "                    TB018 + '-' + TB019 + '-' + TB020 ORDERID, TB021 "
				+ "               from WPPTB) f on d.TG001 = f.TB001 and d.TG016 = f.TB002 and "
				+ "                                d.TG017 = f.TB003 and d.TG018 = f.TB004 and "
				+ "                                d.TG019 = f.TB005 " + " order by TG007, TG003, TG017, TG002, TG020";

		return sql;
	}

}
