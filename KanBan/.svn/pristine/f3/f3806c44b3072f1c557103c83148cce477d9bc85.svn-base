package com.dsc.dci.sqlcode.funcs.wpp;

public class sqlWPP006 {
	public String F001Sql() {
		String sql = null;
		sql = "select MB001 + '/' + MB002 label , MB001 value from CMSMB order by MB001";
		return sql;
	}

	public String F002Sql() {
		String sql = null;
		sql = "select MC001 + '/' + MC002 label , MC001 value from CMSMC order by MC001";
		return sql;
	}

	public String HeadSql(String F001, String F002, String F003) {
		String sql = null;

		sql = "SELECT TL005, TL006, TL009 , TL010, TL011 , TL012 , TL016 FROM WPPTL " + " WHERE TL001 = '" + F001
				+ "' AND TL002 = '" + F002 + "' AND TL004 = '" + F003 + "'";

		return sql;
	}

	public String HeadSql2(String F001, String F002, String F003) {
		String sql = null;

		sql = "select case N0 when 0 then 0 else CONVERT(decimal, N1) / CONVERT(decimal, N0) end value "
				+ "  from (select isnull(SUM(cnt), 0) N0, "
				+ "                ISNULL(SUM(case TN022 when 'Y' then cnt when 'y' then cnt else 0 end), 0) N1 "
				+ "           from (SELECT TN022, COUNT('b') cnt FROM WPPTL "
				+ "                    JOIN WPPTN ON WPPTL.TL001 = WPPTN.TN001 AND WPPTL.TL002 = WPPTN.TN002 AND "
				+ "                                  WPPTN.TN005 = '2' AND TN007 < CAST(GETDATE() AS DATE) "
				+ "                   WHERE WPPTL.TL001 = '" + F001 + "' AND WPPTL.TL002 = '" + F002
				+ "' AND WPPTL.TL004 = '" + F003 + "' group by TN022) aa)  bb";

		return sql;
	}

	public String BodySql(String F001, String F002, String F003) {
		String sql = null;

		sql = "SELECT TN022, TN023, TN024 * -1 TN024, TN027, TN006, TN007, TN008 , TN009 , TN010,"
				+ "       TN008 + '-' + TN009 + '-' + TN010 ORDER_ID, TN011, TN012, TN013, TN014, TN015, "
				+ "       TN017, TN021, TN019, TN018, TN020, TN016, (SELECT distinct '※' "
				+ "           from WPPTP "
				+ "          where TP001 = a.TN008 AND TP002 = a.TN009 AND TP003 = a.TN010 and TP007 = 'N') NOTE, TN025, "
				+ "       b.MC007 FROM WPPTN a " + "  LEFT JOIN INVMC b ON a.TN013 = b.MC001 AND a.TN002 = b.MC002 "
				+ " WHERE TN001 = '" + F001 + "' AND a.TN002 = '" + F002 + "' AND a.TN005 = '" + F003 + "' and a.TN022 = 'N'"
				+ " ORDER BY TN006";

		return sql;
	}

	public String subMOsql(String TN008, String TN009, String TN010, String TN013) {
		String sql = null;
		sql = "SELECT TO009, case when TO022 < CONVERT(char(8), GETDATE(), 112) then 'Y' else 'N' end TO025, "
				+ "       case when TO023 < CONVERT(char(8), GETDATE(), 112) then 'R' else "
				+ "           case when TO023 > CONVERT(char(8), GETDATE(), 112) and DATEDIFF(DAY, TO015, TO022) > (select top 1 MA012 from WPPMA) then "
				+ "           'Y' else 'N' end end TO026, TO027, TO021, TO004 + '-' + TO005 MO_ID, TO014, TO023, TO016, "
				+ "TO010, TO011, TO017, TO013, TO018, TO022, TO015, TO024, TO012 FROM WPPTO "
				+ " WHERE WPPTO.TO001 = '" + TN008 + "' AND WPPTO.TO002 = '" + TN009 + "' AND WPPTO.TO003 = '" + TN010
				+ "' AND WPPTO.TO010 = '" + TN013 + "' ORDER BY WPPTO.TO004, WPPTO.TO005";

		return sql;
	}

	public String subNotesql(String TN008, String TN009, String TN010) {
		String sql = null;
		sql = "SELECT TP005,TP006 FROM WPPTP WHERE TP001='" + TN008 + "' AND TP002='" + TN009 + "' AND TP003='" + TN010
				+ "' AND TP007='N' order by TP006 desc , TP003";
		return sql;
	}
}
