package com.dsc.dci.sqlcode.funcs.lkb;

import com.dci.jweb.PublicLib.DCIString;

public class sqlLKB001 {
	public String F001Sql() {
		String sql = null;
		sql = "SELECT A.Factory_ID,A.Factory_ID + '/' + A.Factory_Name Factory_Name  FROM Factory A ORDER BY A.Factory_ID";
		return sql;
	}

	public String F002Sql(String p1) {
		String sql = null;
		sql = "SELECT A.WS_ID,A.WS_Name FROM WS A WHERE A.Factory_ID='" + p1 + "' ORDER BY A.WS_ID";
		return sql;
	}

	public String F008Sql(String p1, String p2) {
		String sql = null;
		sql = "SELECT A.EQ_ID,A.EQ_Name,A.WS_ID,B.WS_Name FROM EQ A JOIN WS B ON A.Factory_ID=B.Factory_ID AND A.WS_ID=B.WS_ID "
				+ "WHERE A.Factory_ID='" + p1 + "' ";

		if (!DCIString.isNullOrEmpty(p2)) {
			sql += "AND A.WS_ID = '" + p2 + "' ";
		}

		sql += " ORDER BY A.EQ_ID";
		return sql;
	}

	public String eqsSql(String F001, String F002, String F008, String currtime) {
		String sql = null;
		sql = "SELECT A.EQ_ID,rtrim(A.EQ_ID) + '_' + rtrim(A.WS_ID) EQKey, A.EQ_Name, A.EQ_Image, B.Code_Name, B.Code_Image, B.Code_Color, "
				+ "B.Code_Value,A.EQ_Time,floor((DATEDIFF(SECOND,A.EQ_Time,'" + currtime + "')/60.0) + 0.5) timegap "
				+ "  FROM EQ A "
				+ "  JOIN Code_Mapping B ON A.EQ_Status = B.Code_Value AND B.Code_Category = 'EQ_Status' "
				+ " WHERE A.Factory_ID = '" + F001 + "' ";

		if (!DCIString.isNullOrEmpty(F002)) {
			sql += " AND A.WS_ID = '" + F002 + "' ";
		}

		if (!DCIString.isNullOrEmpty(F008)) {
			F008 = F008.replace("(", "_");
			F008 = F008.replace(")", "");
			F008 = F008.replace(";", "','");
			F008 = "'" + F008 + "'";
			sql += "AND rtrim(A.EQ_ID) + '_' + rtrim(A.WS_ID) IN (" + F008 + ") ";
		}

		sql += "  ORDER BY A.EQ_ID";
		return sql;
	}

	public String utilitySql(String F001, String F008, String sd, String ed, String id, String date) {
		String sql = null;
		sql = "SELECT '"
				+ date
				+ "' date, isnull(AVG(UTILITY),0) UTILITY "
				+ "  FROM (SELECT A.EQ_ID,SUM(CASE WHEN A.Code_Capacity = '1' THEN A.DIFF_SEC ELSE 0.00 END) / "
				+ "                       SUM(CASE WHEN A.Code_Capacity != '0' THEN A.DIFF_SEC ELSE 0.00 END) UTILITY "
				+ "           FROM (SELECT A.EQ_ID,DATEDIFF(SECOND, CASE WHEN A.Start_Time < '"
				+ sd
				+ "' "
				+ "                                                 THEN '"
				+ sd
				+ "' ELSE A.Start_Time END, "
				+ "                                                 CASE WHEN A.End_Time > '"
				+ ed
				+ "' "
				+ "                                                 THEN '"
				+ ed
				+ "' ELSE A.End_Time END) DIFF_SEC, "
				+ "                                                 A.EQ_Status, B.Code_Capacity "
				+ "                    FROM EQ_Run A "
				+ "                    JOIN Code_Mapping B ON A.EQ_Status = B.Code_Value AND B.Code_Category = 'EQ_Status' "
				+ "                   WHERE A.Factory_ID = '" + F001 + "' $####$"
				+ "                         AND A.Start_Time <= '" + ed + "' "
				+ "                         AND A.End_Time >= '" + sd + "' "
				+ "                  ) A  GROUP BY A.EQ_ID) A" + id;

		if (!DCIString.isNullOrEmpty(F008)) {
			F008 = F008.replace("(", "_");
			F008 = F008.replace(")", "");
			F008 = F008.replace(";", "','");
			F008 = "'" + F008 + "'";
			sql = sql.replace("$####$", "AND A.EQ_Key IN (" + F008 + ") ");
		} else {
			sql = sql.replace("$####$", "");
		}

		return sql;
	}

	public String utilityByEqSql(String F001, String eq, String sd, String ed, String id, String date) {
		String sql = null;
		sql = "SELECT '" + date + "' date, isnull(SUM(CASE WHEN Code_Capacity = '1' THEN DIFF_SEC "
				+ "                ELSE 0.00 END) / " + "       SUM(CASE WHEN Code_Capacity != '0' THEN DIFF_SEC "
				+ "                ELSE 0.00 END),0) UTILITY  FROM (SELECT A.EQ_ID, DATEDIFF(SECOND, "
				+ "        CASE WHEN A.Start_Time < '" + sd + "' THEN " + "             '" + sd
				+ "' ELSE A.Start_Time END,  CASE WHEN A.End_Time > '" + ed + "' THEN " + "             '" + ed
				+ "' ELSE A.End_Time END) " + "  DIFF_SEC, A.EQ_Status, B.Code_Capacity FROM EQ_Run A "
				+ "           JOIN Code_Mapping B ON A.EQ_Status = B.Code_Value AND "
				+ "                                  B.Code_Category = 'EQ_Status' "
				+ "          WHERE A.Factory_ID = '" + F001 + "' AND A.EQ_Key = '" + eq + "' AND "
				+ "                A.Start_Time <= '" + ed + "' AND A.End_Time >= '" + sd + "') A"
				+ id;

		return sql;
	}

}
