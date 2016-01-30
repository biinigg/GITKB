package com.dsc.dci.sqlcode.funcs.wpp;

public class sqlWPP007 {
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

	public String BodySql(String F001, String F002) {
		String sql = null;

		sql = "SELECT TN031, TN032, TN022, TN023, TN024 * -1 TN024, value, TN006, TN007,TN008 , TN009 , TN010, "
				+ "       TN008 + '-' + TN009 + '-' + TN010 ORDER_ID, TN011, TN012, TN013, TN014, TN015, "
				+ "       TN017, TN021, TN019, TN018, TN020, TN016, "
				+ "       (SELECT distinct '※' from WPPTP where TP001 = a.TN008 AND TP002 = a.TN009 AND TP003 = a.TN010 and TP007 = 'N') NOTE "
				+ "      , TN025, INVMC.MC007 FROM WPPTN a "
				+ "  LEFT JOIN INVMC ON a.TN013 = INVMC.MC001 AND a.TN002 = INVMC.MC002 "
				+ "  LEFT JOIN (select TO001, TO002, TO003, "
				+ "       case P0 when 0 then 0 else convert(decimal, P1) / convert(decimal, P0) end value "
				+ "  from (select TO001, TO002, TO003, isnull(SUM(cnt), 0) P0, "
				+ "                isnull(sum(case TO014 when 'Y' then cnt when 'y' then cnt else 0 end), 0) P1 "
				+ "           from (SELECT TO001, TO002, TO003, TO014, COUNT('b') cnt "
				+ "                    FROM WPPTO  GROUP BY TO001, TO002, TO003, TO014) aa "
				+ "          group by TO001, TO002, TO003) aaa) T2 ON a.TN008 = T2.TO001 AND "
				+ "                                                                a.TN009 = T2.TO002 AND "
				+ "                                                                a.TN010 = T2.TO003 "
				+ " WHERE a.TN001 = '" + F001 + "' AND a.TN002 = '" + F002
				+ "' AND a.TN022 = 'N' ORDER BY TN006, TN007";

		return sql;
	}

	public String subMOsql(String TN008, String TN009, String TN010) {
		String sql = null;
		sql = "SELECT TO009, case when TO022 < CONVERT(char(8), GETDATE(), 112) then 'Y' else 'N' end TO025, "
				+ "       case when TO023 < CONVERT(char(8), GETDATE(), 112) then 'R' else "
				+ "           case when TO023 > CONVERT(char(8), GETDATE(), 112) and DATEDIFF(DAY, TO015, TO022) >= (select top 1 MA012 from WPPMA) then "
				+ "           'Y' else 'N' end end TO026, TO027, TO021, TO004 + '-' + TO005 MO_ID, TO014, TO023, TO016, TO010, "
				+ "       TO011, TO017, TO013, TO018, TO022, TO015, TO024, TO012 FROM WPPTO"
				+ " WHERE WPPTO.TO001 = '" + TN008 + "' AND WPPTO.TO002 = '" + TN009 + "' AND WPPTO.TO003 = '" + TN010
				+ "' ORDER BY WPPTO.TO004, WPPTO.TO005";

		return sql;
	}

	public String subNotesql(String TN008, String TB009, String TN010) {
		String sql = null;
		sql = "SELECT TP005,TP006 FROM WPPTP WHERE TP001='" + TN008 + "' AND TP002='" + TB009 + "' AND TP003='" + TN010
				+ "' AND TP007='N' order by TP006 desc , TP003";
		return sql;
	}
}
