package com.dsc.dci.sqlcode.funcs.wpp;

public class sqlWPP002 {
	public String F001Sql() {
		String sql = null;
		sql = "select MB001 + '/' + MB002 label , MB001 value from CMSMB order by MB001";
		return sql;
	}

	public String F005Sql() {
		String sql = null;
		sql = "select MC001 + '/' + MC002 label , MC001 value from CMSMC order by MC001";
		return sql;
	}

	public String HeadSql(String F001, String F002, String F004) {
		String sql = null;
		sql = "select TA006,TA007,TA008,TA009,TA012,TA013,getdate() currdate from WPPTA where TA001 = '" + F001
				+ "' and TA002 = '" + F002 + "' and TA005 = '" + F004 + "'";
		return sql;
	}

	public String BodySql(String F001, String F002, String F003, String F004, String F005, String F006, String F007,
			String MB01) {
		String sql = null;

		if (F004.equals("2")) {
			sql = "select dtp, TG002 C02, TG003 C03, MOID, TG007 C07, TG008 C08, TG009 C09, TG010 C10, TG011 C11, "
					+ "       case TG013 when 'Y' then 'R' else '' end C13, TG012 C12, TG017 C17, TG021 C21, TG022 C22, "
					+ "       TA006, TA007, TA015 - TA017 TA015, MB002, MB003, MB004, MV002, TB010, "
					+ "       TB018 + '-' + TB019 + '-' + TB020 ORDERID, TB021 "
					+ "  from (select '1' dtp,TG002, TG003, TG005 + '-' + TG006 MOID, TG007, TG008, TG009, TG010, TG011, "
					+ "                TG013, TG012, TG017, NULL TG021, TG022, TG020, TG005, TG006, TG001, TG016, TG018, "
					+ "                TG019 from WPPTG where TG001 = '" + F001 + "' and TG016 = '" + F002
					+ "' and TG004 = '" + F006 + "' ";

			if (F003.equals("Y")) {
				sql += " and TG003 = '" + F005 + "' ";
			}

			if (!F007.equals("0")) {
				sql += " and TG022 = '" + F007 + "'";
			}

			sql += "         union all select '0' dtp, TF002, TF003, ";

			if (MB01 != null && MB01.equals("N") && F006.equals("1")) {
				sql += " case len(isnull(TF008,'')) when 4 then SUBSTRING(TF008,1,2) + ':' + SUBSTRING(TF008,3,2) else TF008 end + '-庫存', null, 0, 0, isnull(TF004,0) ";
			} else {
				sql += " case len(isnull(TF008,'')) when 4 then SUBSTRING(TF008,1,2) + ':' + SUBSTRING(TF008,3,2) else TF008 end + '-庫存+待驗', null, 0, 0, isnull(TF004,0) + isnull(TF005,0) ";
			}

			sql += ", 0, 'N', null, '', ";
			if (MB01.equals("N") && F006.equals("1")) {
				sql += " TF006 ";
			} else {
				sql += " TF006-TF005 ";
			}

			sql += ", '', '', '', '', TF001, '', '', '' from WPPTF tf "
					+ "          where exists (select 'b' from WPPTG "
					+ "                  where TG001 = tf.TF001 and TG002 = tf.TF002 and TG003 = tf.TF003 and "
					+ "                        TG001 = '" + F001 + "' and TG016 = '" + F002 + "' and TG004 = '" + F006
					+ "' ";

			if (F003.equals("Y")) {
				sql += " and TG003 = '" + F005 + "' ";
			}

			if (!F007.equals("0")) {
				sql += " and TG022 = '" + F007 + "'";
			}

			sql += ")) a left join MOCTA b on a.TG005 = b.TA001 and a.TG006 = b.TA002 "
					+ "  left join INVMB c on a.TG002 = c.MB001 left join CMSMV d on c.MB067 = d.MV001 "
					+ "  left join WPPTB e on TG001 = TB001 and TG016 = TB002 and TG017 = TB003 and "
					+ "                       TG018 = TB004 and TG019 = TB005 "
					+ " order by TG003, TG002, TG007, TG010 desc";

		} else if (F004.equals("1")) {

			sql = "select dtp, TH002 C02, TH003 C03, MOID, TH007 C07, TH008 C08, TH009 C09, TH010 C10, TH011 C11, "
					+ "       case TH013 when 'Y' then 'R' else '' end C13, TH012 C12, TH017 C17, TH021 C21, TH022 C22, "
					+ "       TA006, TA007, TA015 - TA017 TA015, MB002, MB003, MB004, MV002, TB010, "
					+ "       TB018 + '-' + TB019 + '-' + TB020 ORDERID, TB021 "
					+ "  from (select '1' dtp,TH002, TH003, TH005 + '-' + TH006 MOID, TH007, TH008, TH009, TH010, TH011, "
					+ "                TH013, TH012, TH017, NULL TH021, TH022, TH020, TH005, TH006, TH001, TH016, TH018, "
					+ "                TH019 from WPPTH where TH001 = '" + F001 + "' and TH016 = '" + F002
					+ "' and TH004 = '" + F006 + "' ";

			if (F003.equals("Y")) {
				sql += " and TH003 = '" + F005 + "' ";
			}

			if (!F007.equals("0")) {
				sql += " and TH022 = '" + F007 + "'";
			}

			sql += " union all select '0' dtp, TF002, TF003,";
			if (MB01 != null && MB01.equals("N") && F006.equals("1")) {
				sql += " case len(isnull(TF008,'')) when 4 then SUBSTRING(TF008,1,2) + ':' + SUBSTRING(TF008,3,2) else TF008 end + '-庫存', TF007, 0, 0, isnull(TF004,0)-isnull(TF009,0) ";
			} else if (MB01 != null && MB01.equals("Y") && F006.equals("1")) {
				sql += " case len(isnull(TF008,'')) when 4 then SUBSTRING(TF008,1,2) + ':' + SUBSTRING(TF008,3,2) else TF008 end + '-庫存+待驗', TF007, 0, 0, isnull(TF004,0)+isnull(TF005,0)-isnull(TF009,0) ";
			} else {
				sql += " case len(isnull(TF008,'')) when 4 then SUBSTRING(TF008,1,2) + ':' + SUBSTRING(TF008,3,2) else TF008 end + '-庫存+待驗', TF007, 0, 0, isnull(TF004,0)+isnull(TF005,0)-isnull(TF010,0) ";
			}
			// if (F006.equals("1")) {
			// sql += " isnull(TF004,0)+isnull(TF005,0)-isnull(TF009,0) ";
			// } else {
			// sql += " isnull(TF004,0)+isnull(TF005,0)-isnull(TF010,0) ";
			// }

			sql += ", 0, 'N', null, '', ";
			if (MB01.equals("N") && F006.equals("1")) {
				sql += " TF006 ";
			} else {
				sql += " TF006-TF005 ";
			}
			
			sql += " , '', '0000', '', '', TF001, '', '', '' from WPPTF tf "
					+ "          where exists (select 'b' from WPPTH "
					+ "                  where TH001 = tf.TF001 and TH002 = tf.TF002 and TH003 = tf.TF003 and "
					+ "                        TH001 = '" + F001 + "' and TH016 = '" + F002 + "' and TH004 = '" + F006
					+ "' ";

			if (F003.equals("Y")) {
				sql += " and TH003 = '" + F005 + "' ";
			}

			if (!F007.equals("0")) {
				sql += " and TH022 = '" + F007 + "'";
			}

			sql += ")) a left join MOCTA b on a.TH005 = b.TA001 and a.TH006 = b.TA002 "
					+ "  left join INVMB c on a.TH002 = c.MB001 left join CMSMV d on c.MB067 = d.MV001 "
					+ "  left join WPPTB e on TH001 = TB001 and TH016 = TB002 and TH017 = TB003 and "
					+ "                       TH018 = TB004 and TH019 = TB005 "
					+ " order by TH003, TH002, TH007, TH010 desc ";

		}

		// System.out.println(sql);
		return sql;
	}

	public String subGridSql(String TF002, String TF003) {
		String sql = null;
		sql = "select * from (" + "SELECT TD001 + '-' + TD002 + '-' + TD003 TD001, TD012, TD008, TD009, "
				+ "       isnull(TD008, 0) - isnull(TD015, 0) TD015, " + "       (SELECT SUM(TK008) "
				+ "           FROM WPPTK "
				+ "          where TK010 = a.TD001 and WPPTK.TK011 = a.TD002 and TK030 = a.TD003) TK008, "
				+ "       NULL NOTE " + "  FROM PURTD a " + " where TD004 = '"
				+ TF002
				+ "' and TD007 = '"
				+ TF003
				+ "' and TD016 = 'N' "
				+ " "
				+ "union all "
				+ " "
				+ "SELECT TA001 + '-' + TA002, TA010, TA015, TA007, "
				+ "       isnull(TA015, 0) - isnull(TA017, 0) - isnull(TA018, 0), "
				+ "       (SELECT SUM(TK008) "
				+ "           FROM WPPTK "
				+ "          where TK012 = a.TA001 and WPPTK.TK013 = a.TA002), "
				+ "       (select top 1 TB011 "
				+ "           from WPPTB b "
				+ "          where exists (select 'b' from WPPTA "
				+ "                         where TA001 = b.TB001 and TA002 = b.TB002 and TA003 = b.TB003 and "
				+ "                               TA004 = b.TB004 and TA005 = '2') "
				+ "            and exists (select 'b' from WPPTK where TK027 = b.TB006 and TK028 = b.TB007)) NOTE "
				+ "  FROM MOCTA a "
				+ " where TA006 = '"
				+ TF002
				+ "' and TA020 = '"
				+ TF003
				+ "' and TA011 <> 'Y' and TA011 <> 'y' ) data " + " order by 2";

		return sql;
	}

	public String getParameter() {
		return "select * from WPPMB";
	}
}
