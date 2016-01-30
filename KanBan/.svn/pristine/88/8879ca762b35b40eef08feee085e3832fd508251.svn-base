package com.dsc.dci.sqlcode.funcs.wpp;

public class sqlWPP003 {
	public String F001Sql() {
		String sql = null;
		sql = "select MB001 + '/' + MB002 label , MB001 value from CMSMB order by MB001";
		return sql;
	}

	public String F004_1Sql() {
		String sql = null;
		sql = "select MD001 + '/' + MD002 label , MD001 value from CMSMD order by MD001";
		return sql;
	}

	public String F004_2Sql() {
		String sql = null;
		sql = "select MA001 + '/' + MA002 label , MA001 value from PURMA order by MA001";
		return sql;
	}

	public String F006Sql() {
		String sql = null;
		sql = "select MC001 + '/' + MC002 label , MC001 value from CMSMC order by MC001";
		return sql;
	}

	public String HeadSql(String F001, String F002, String F003, String F004) {
		String sql = null;
		sql = "select TA006,TA007,TA008,TA009,TA012,TA013,getdate() currdate from WPPTA where TA001 = '" + F001
				+ "' and TA002 = '" + F003 + "' and TA003 = '" + F004 + "' and TA005 = '" + F002 + "'";
		return sql;
	}

	public String BodySql(String F001, String F002, String F003, String F004, String F005, String F006, String F007,
			String F008, String F009) {
		String sql = null;

		if (F002.equals("1")) {
			sql = "select TH005 + '-' + TH006 MOID, TH002 C02, TH003 C03, TH007 C07, TH008 C08, TH012 C12, "
					+ "       case TH013 when 'Y' then 'R' else '' end C13, TH021 C21, TH022 C22, TA006, "
					+ "       TA015 - TA017 TA015, TA007, c.MB002 PART01, TB010, TB018 + '-' + TB019 + '-' + TB020 ORDERID, "
					+ "       TB021, e.MB002, e.MB003, e.MB004, MV002 "
					+ "  from (select TH001, TH002, TH003, TH005, TH006, TH007, TH008, TH012, TH013, TH016, TH017, "
					+ "                TH018, TH019, TH020, TH021, TH022 from WPPTH where TH001 = '" + F001
					+ "' and TH016 = '" + F003 + "' and TH017 = '" + F004 + "' and TH005 <> '****' and TH004 = '" + F008 + "' ";

			if (F005.equals("Y")) {
				sql += " and TH003 = '" + F006 + "' ";
			}

			if (F009.equals("Y")) {
				sql += " and TH013 = 'Y' ";
			}

			if (!F007.equals("0")) {
				sql += " and TH022 = '" + F007 + "' ";
			}

			sql += " ) a " + "  left join MOCTA b on TH005 = TA001 and TH006 = TA002 "
					+ "  left join INVMB c on TA006 = c.MB001 "
					+ "  left join WPPTB d on TH001 = TB001 and TH016 = TB002 and TH017 = TB003 and "
					+ "                       TH018 = TB004 and TH019 = TB005 "
					+ "  left join INVMB e on TH002 = e.MB001 left join CMSMV f on e.MB067 = MV001 "
					+ " order by TH020, TH005, TH006, TH002, TH007, TH003";
		} else {
			sql = "select TG005 + '-' + TG006 MOID, TG002 C02, TG003 C03, TG007 C07, TG008 C08, TG012 C12, "
					+ "       case TG013 when 'Y' then 'R' else '' end C13, TG021 C21, TG022 C22, TA006, "
					+ "       TA015 - TA017 TA015, TA007, c.MB002 PART01, TB010, TB018 + '-' + TB019 + '-' + TB020 ORDERID, "
					+ "       TB021, e.MB002, e.MB003, e.MB004, MV002 "
					+ "  from (select TG001, TG002, TG003, TG005, TG006, TG007, TG008, TG012, TG013, TG016, TG017, "
					+ "                TG018, TG019, TG020, TG021, TG022 from WPPTG where TG001 = '" + F001
					+ "' and TG016 = '" + F003 + "' and TG017 = '" + F004 + "' and TG005 <> '****' and TG004 = '" + F008 + "' ";

			if (F005.equals("Y")) {
				sql += " and TG003 = '" + F006 + "' ";
			}

			if (F009.equals("Y")) {
				sql += " and TG013 = 'Y' ";
			}

			if (!F007.equals("0")) {
				sql += " and TG022 = '" + F007 + "' ";
			}

			sql += " ) a " + "  left join MOCTA b on TG005 = TA001 and TG006 = TA002 "
					+ "  left join INVMB c on TA006 = c.MB001 "
					+ "  left join WPPTB d on TG001 = TB001 and TG016 = TB002 and TG017 = TB003 and "
					+ "                       TG018 = TB004 and TG019 = TB005 "
					+ "  left join INVMB e on TG002 = e.MB001 left join CMSMV f on e.MB067 = MV001 "
					+ " order by TG020, TG005, TG006, TG002, TG007, TG003";
		}
		return sql;
	}
}
