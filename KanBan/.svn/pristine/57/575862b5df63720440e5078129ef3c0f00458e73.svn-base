package com.dsc.dci.sqlcode.funcs.wpp;

public class sqlWPP005 {
	public String F001Sql() {
		String sql = null;
		sql = "select MB001 + '/' + MB002 label , MB001 value from CMSMB order by MB001";
		return sql;
	}

	public String F006Sql() {
		String sql = null;
		sql = "select MC001 + '/' + MC002 label , MC001 value from CMSMC order by MC001";
		return sql;
	}

	/*
	 * public String HeadSql(String F001, String F002, String F003) { String sql
	 * = null; sql =
	 * "select TA006,TA007,TA008,TA009,TA012,TA013,getdate() currdate from WPPTA where TA001 = '"
	 * + F001 + "' and TA002 = '" + F002 + "' and TA003 = '" + F003 +
	 * "' and TA005 = '2'"; return sql; }
	 */

	public String BodySql(String F001, String F002, String F003, String F004, String F005, String F006) {
		String sql = null;

		sql = "select TK001, TK003, TK006, TK007, TK008, TK009, TK010, TK012, TK014, TK015, TK016, TK017, TK018, "
				+ "       TK019, TK020, TK023, TK022, TK025, TK027, TK029, TE003, MB002, MB003, currdate, TJ001, TJ002, "
				+ "       (select distinct n from ( "
				+ " SELECT '※' n from WPPTJ  where TJ001 = a.TJ001 AND TJ002 = a.TJ002 AND TJ007 = 'N' "
				+ " union all "
				+ " select '※' from WPPTP aa where TP007 = 'N' and exists (select 'b' from (select TA001,TA002, TA026,TA027,TA028 from MOCTA where TA001 = a.TJ001 and TA002 = a.TJ002) bb "
				+ "where aa.TP001 = bb.TA026 and aa.TP002 = bb.TA027 and aa.TP003 = bb.TA028) ) ccc "
				+ " ) NOTE "
				+ "  from (select TK017, TK018, getdate() currdate, "
				+ "                case TK019 when 'Y' then 'R' else '' end TK019, TK006, TK014, TK015, "
				+ "                TK001 + '-' + TK002 TK001, TK003, TK016, TK007, TK008, TK009, TK010 + '-' + TK011 TK010, "
				+ "                TK012 + '-' + TK013 TK012, TK020, TK012 TJ001, TK013 TJ002, TK013,TK012 TB006,"
				+ "                case isnull(TK025, '') when '' then TK024 else TK025 end TK025, "
				+ "                case isnull(TK015, '') when '' then '' else TK029 end TK029, "
				+ "                case isnull(TK027, '') when '' then TK012 + '-' + TK013 else TK027 + TK028 end TK027, "
				+ "                case isnull(TK023, '') when '' then TK005 else TK023 end TK023, TK022, TK004, TK002 "
				+ "           from WPPTK where TK004 = '" + F001 + "' and TK014 = '" + F002 + "' and TK021 = '****' ";

		if (F003.equals("Y")) {
			sql += " and TK007 = '" + F006 + "' ";
		}

		// if (F004.equals("N")) {
		// sql += " and TK021 = '****' ";
		// }

		if (F005.equals("Y")) {
			sql += " and TK019 = 'Y' ";
		} else if (F005.equals("N")) {
			sql += " and TK019 = 'N' ";
		}

		sql += ") a   left join INVMB b on a.TK006 = b.MB001 "
				// +
				// "  left join (select c.TB001, c.TB006, c.TB007, case d.TA005 when '2' then TB010 else '' end TB010 "
				// + "               from WPPTB c "
				// +
				// "               left join WPPTA d on c.TB001 = TA001 and c.TB002 = d.TA002 and "
				// +
				// "                                    c.TB003 = d.TA003 and c.TB004 = d.TA004) e "
				// +
				// "         on a.TK004 = e.TB001 AND a.TB006 = e.TB006 AND a.TK013 = e.TB007 "
				+ "  left join WPPTE f on a.TJ001 = f.TE001 and a.TJ002 = f.TE002 AND f.TE011='2' "
				+ " order by TK019 desc, TK015 desc,TK029, TK003, TK001, TK002";

		return sql;
	}

	public String subNotesql(String TJ001, String TJ002) {
		String sql = null;
		sql = "select TJ003 ,TJ005 ,TJ006 from WPPTJ where TJ001  = '" + TJ001 + "' and TJ002  = '" + TJ002
				+ "' and TJ007 = 'N'                                                 "
				+ "union all                                                                  "
				+ "select 'business', a.TP005, a.TP006 " + "  from WPPTP a where TP007 = 'N' and exists (select 'b' "
				+ "          from (select TA001, TA002, TA026, TA027, TA028 from MOCTA "
				+ "                  where TA001 = '" + TJ001 + "' and TA002 = '" + TJ002 + "') b "
				+ "         where a.TP001 = b.TA026 and a.TP002 = b.TA027 and a.TP003 = b.TA028)"
				+ "order by 3 desc , 1";
		return sql;
	}
}
