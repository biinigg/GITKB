package com.dsc.dci.sqlcode.funcs.wpp;

import com.dci.jweb.PublicLib.DCIString;

public class sqlWPP001 {
	public String F001Sql() {
		String sql = null;
		sql = "select MB001 + '/' + MB002 label , MB001 value from CMSMB order by MB001";
		return sql;
	}

	public String F003Sql() {
		String sql = null;
		sql = "select MD001 + '/' + MD002 label , MD001 value from CMSMD order by MD001";
		return sql;
	}

	public String F004Sql() {
		String sql = null;
		sql = "select MA001 + '/' + MA002 label , MA001 value from PURMA order by MA001";
		return sql;
	}

	public String HeadSql(String f001, String f002, String f003, String f004) {
		String sql = null;

		sql = "select TA001,TA002,TA003,TA004,TA006,TA007,TA008,TA009,TA010,TA011,TA012,TA013,getdate() currdate from WPPTA where TA001 = '"
				+ f001 + "' and TA002 = '" + f002 + "' ";

		if (f002.equals("1")) {
			if (!DCIString.isNullOrEmpty(f003)) {
				sql += " and TA003 = '" + f003 + "'";
			}
		} else {
			if (!DCIString.isNullOrEmpty(f004)) {
				sql += " and TA003 = '" + f004 + "'";
			}
		}

		sql += " and TA005 = '2'";

		return sql;
	}

	public String BodySql(String TA001, String TA002, String TA003, String TA004, String MA012, String MA007) {
		String sql = null;

		sql = "select TB006 + '-' + TB007 MOID, TA006, TA007, TA011, TA012, TA014, TA015, TA017, TA033, "
				+ "       TA024 + '-' + TA025 PORDERID, TB018 + '-' + TB019 + '-' + TB020 ORDERID, MB002, MB003, "
				+ "       case '"
				+ MA007
				+ "' when 'Y' then TB022 else '' end TB022,TB021, TB023, TE003, "
				+ "       case when TB010 < CONVERT(char(8) ,GETDATE(),112) and TA013 = 'Y' and TA011 = '1' then 'R' else '' end TB015, "
				+ "       case when TB011 < CONVERT(char(8) ,GETDATE(),112) and TA013 = 'Y' and TA011 NOT IN ('Y', 'y') then 'R' "
				+ "            when TB011 > CONVERT(char(8) ,GETDATE(),112) and TA013 = 'Y' "
				+ "             and DATEDIFF(DAY, TB010, TA012) > '"
				+ MA012
				+ "' then 'Y' else '' end TB016, TB008, "
				+ "       case TB009 when 'Y' then '※' else '' end TB009, TB010, TB011, TB012, TB013, TB014, "
				+ "       case TB017 when 'Y' then 'R'  else '' end TB017,TB001,TB002,TB003,TB004,TB005,TB006, TB007,"
				+ "       (select distinct n from (select '※' n from WPPTJ where TJ001 = a.TB006 AND TJ002 = a.TB007 AND TJ007 = 'N' "
				+ "       union all "
				+ "       select '※' from WPPTP where TP001 = a.TB018 AND TP002 = a.TB019 AND TP003 = TB020 AND TP007='N') ccc) NOTE "
				+ "  from (select TB001,TB002,TB003,TB004,TB005,TB006, TB007, TB008, TB009, TB010, TB011, TB012, TB013, TB014, TB017 "
				+ "    , TB018, TB019, TB020, TB021, TB022, TB023  from WPPTB where TB001 = '"
				+ TA001
				+ "' and TB002 = '"
				+ TA002
				+ "' and TB003 = '"
				+ TA003
				+ "' and TB004 = '"
				+ TA004
				+ "') a "
				+ "  left join MOCTA b on a.TB006 = b.TA001 and a.TB007 = b.TA002 left join INVMB c on b.TA006 = c.MB001 "
				// +
				// "  left join COPTC d on b.TA026 = d.TC001 and b.TA027 = d.TC001 left join COPMA e on d.TC004 = e.MA001 "
				// +
				// "  left join COPTD f on TA026 = TD001 and TA027 = TD002 and TA028 = TD003 "
				+ "  left join WPPTE g on a.TB006 = g.TE001 and a.TB007 = g.TE002 and g.TE011 = '2' order by TB008";

		return sql;
	}

	public String subTB009Headsql() {
		String sql = null;
		sql = "select MA008 ,MA009 ,MA010 ,MA011 from WPPMA";
		return sql;
	}

	public String subTB009sql(String TB001, String TB002, String TB003, String TB004, String TB005) {
		String sql = null;
		sql = "select TC007 ,TC008 ,TC009 ,TC010 ,TC011 ,TC012 from WPPTC where TC001 = '" + TB001 + "' and TC002 = '"
				+ TB002 + "' and TC003 = '" + TB003 + "' and TC004 = '" + TB004 + "' and TC005 = '" + TB005
				+ "' order by TC007";
		return sql;
	}

	public String subNotesql(String TB006, String TB007, String TB018, String TB019, String TB020) {
		String sql = null;
		sql = "select TJ003 ,TJ005 ,TJ006 from WPPTJ where TJ001  = '" + TB006 + "' and TJ002  = '" + TB007
				+ "' and TJ007 = 'N' union all select 'business', TP005, TP006 from WPPTP where TP001   = '" + TB018
				+ "' and TP002   = '" + TB019 + "' and TP003   = '" + TB020 + "' and TP007 = 'N'  order by 3 desc , 1";
		return sql;
	}

	public String subTB017sql(String TB001, String TB002, String TB003, String TB004, String TB005) {
		String sql = null;
		sql = "select * "
				+ "  from (select TG001, TG002, TG003, TG005, TG006, TG007, TG008, TG012, TG013, TG016, TG017, "
				+ "                TG018, TG019, TG021, (select max(TB011) from MOCTB b "
				+ "                   where b.TB001 = a.TG005 and b.TB002 = a.TG006  and b.TB003 = a.TG002) TB011 "
				+ "           from WPPTG a where TG001 = '" + TB001 + "' and TG016 = '" + TB002 + "' and TG017 = '"
				+ TB003 + "' and TG018 = '" + TB004 + "' and TG019 = '" + TB005
				+ "' and TG013 = 'Y' and TG004 = '2') aa "
				+ " left join (select MB001, MB002, MB003, MB004, MB025, d.MV002 MAN01, e.MV002 MAN02 "
				+ "          from INVMB c left join CMSMV d on c.MB018 = d.MV001 "
				+ "          left join CMSMV e on c.MB067 = e.MV001) bb on aa.TG002 = bb.MB001 "
				+ " order by aa.TG007, aa.TG002";

		return sql;
	}
}
