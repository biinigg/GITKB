package com.dsc.dci.sqlcode.funcs.lkb;

import com.dci.jweb.PublicLib.DCIString;

public class sqlLKB002 {
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

	public String F005Sql(String p1) {
		String sql = null;
		sql = "SELECT DISTINCT A.EQ_Reason, B.Code_Name "
				+ "  FROM EQ_Run A "
				+ "  JOIN Code_Mapping B ON A.EQ_Reason = B.Code_Value AND B.Code_Category = 'EQ_Reason' AND "
				+ "                         A.Factory_ID = '" + p1 + "' ORDER BY A.EQ_Reason";

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

	public String F009Sql(String p1, String p2) {
		String sql = null;
		sql = "SELECT DISTINCT A.EQ_Status, B.Code_Name " + "  FROM EQ_Run A "
				+ "  JOIN Code_Mapping B ON A.EQ_Status = B.Code_Value AND B.Code_Category = 'EQ_Status' "
				+ "  JOIN EQ C ON A.EQ_ID=C.EQ_ID " + "  where A.Factory_ID = '" + p1 + "' ";

		if (!DCIString.isNullOrEmpty(p2)) {
			sql += "AND C.WS_ID = '" + p2 + "' ";
		}

		sql += " ORDER BY A.EQ_Status";
		return sql;
	}

	public String querySql(String F001, String F002, String F003s, String F003e, String F005, boolean F006,
			String F008, String F009) {
		String sql = null;
		sql = "SELECT A.EQ_ID, D.WS_ID + '/' + D.WS_Name WS, C.EQ_Name, E.Code_Name EQ_TYPE, "
				+ "       A.Start_Time, B.Code_Name EQ_REASON_NAME, F.Code_Name EQ_STATUS_NAME, "
				+ "       F.Code_Color, A.Remark  FROM EQ_Run A "
				+ "  LEFT JOIN Code_Mapping B ON A.EQ_Reason = B.Code_Value AND "
				+ "                              B.Code_Category = 'EQ_Reason' "
				+ "  JOIN EQ C ON A.EQ_ID = C.EQ_ID AND A.Factory_ID = C.Factory_ID "
				+ "  JOIN WS D ON C.WS_ID = D.WS_ID AND A.Factory_ID = D.Factory_ID "
				+ "  LEFT JOIN Code_Mapping E ON C.EQ_Property = E.Code_Value AND "
				+ "                              E.Code_Category = 'EQ_Property' "
				+ "  LEFT JOIN Code_Mapping F ON A.EQ_Status = F.Code_Value AND "
				+ "                              F.Code_Category = 'EQ_Status' " + " WHERE A.Factory_ID = '"
				+ F001 + "' ";

		if (!DCIString.isNullOrEmpty(F002)) {
			sql += " AND C.WS_ID = '" + F002 + "' ";
		}

		if (!DCIString.isNullOrEmpty(F003s)) {
			sql += " AND A.End_Time >= '" + F003s + "' ";
		}

		if (!DCIString.isNullOrEmpty(F003e)) {
			sql += " AND A.Start_Time <= '" + F003e + "' ";
		}

		if (!DCIString.isNullOrEmpty(F005)) {
			sql += "AND B.Code_Value= '" + F005 + "' ";
		}

		if (F006) {
			sql += " AND B.Code_Value is not NULL ";
		}

		if (!DCIString.isNullOrEmpty(F008)) {
			F008 = F008.replace("(", "_");
			F008 = F008.replace(")", "");
			F008 = F008.replace(";", "','");
			F008 = "'" + F008 + "'";
			sql += "AND A.EQ_Key IN (" + F008 + ") ";
		}

		if (!DCIString.isNullOrEmpty(F009)) {
			sql += "AND A.EQ_Status = '" + F009 + "' ";
		}

		sql += " ORDER BY C.WS_ID, A.EQ_ID, A.Start_Time";
		return sql;
	}

	public String chartSql(String F001, String F002, String F003s, String F003e, String F005, boolean F006,
			String F008, String F009) {
		String sql = null;
		sql = "SELECT B.Code_Name EQ_REASON_NAME,COUNT(1) EQ_REASON_COUNT FROM EQ_Run A "
				+ "  LEFT JOIN Code_Mapping B ON A.EQ_Reason = B.Code_Value AND "
				+ "                              B.Code_Category = 'EQ_Reason' "
				+ "  JOIN EQ C ON A.EQ_ID = C.EQ_ID AND A.Factory_ID = C.Factory_ID "
				+ "  JOIN WS D ON C.WS_ID = D.WS_ID AND A.Factory_ID = D.Factory_ID "
				+ " WHERE A.Factory_ID = '" + F001 + "' AND B.Code_Value is not NULL ";

		if (!DCIString.isNullOrEmpty(F002)) {
			sql += " AND C.WS_ID = '" + F002 + "' ";
		}

		if (!DCIString.isNullOrEmpty(F003s)) {
			sql += " AND A.End_Time >= '" + F003s + "' ";
		}

		if (!DCIString.isNullOrEmpty(F003e)) {
			sql += " AND A.Start_Time <= '" + F003e + "' ";
		}

		if (!DCIString.isNullOrEmpty(F005)) {
			sql += "AND B.Code_Value= '" + F005 + "' ";
		}
		
		if(F006){
			sql += "AND B.Code_Value is not NULL " ;
		}
		

		if (!DCIString.isNullOrEmpty(F008)) {
			F008 = F008.replace("(", "_");
			F008 = F008.replace(")", "");
			F008 = F008.replace(";", "','");
			F008 = "'" + F008 + "'";
			sql += "AND A.EQ_Key IN (" + F008 + ") ";
		}

		if (!DCIString.isNullOrEmpty(F009)) {
			sql += "AND A.EQ_Status= '-1' ";
		}
		
		sql += " GROUP BY B.Code_Name";
		return sql;
	}

	public String chart2Sql(String F001, String F002, String F003s, String F003e, boolean F006,
			String F008, String F009) {
		String sql = null;
		sql = "SELECT B.Code_Name EQ_STATUS_NAME, "
				+ "       SUM(DATEDIFF(MINUTE, A.Start_Time, A.End_Time)) / 3600.00 EQ_STATUS_HOURS "
				+ "  FROM EQ_Run A  LEFT JOIN Code_Mapping B ON A.EQ_Status = B.Code_Value AND "
				+ "                              B.Code_Category = 'EQ_Status' "
				+ "  JOIN EQ C ON A.EQ_ID = C.EQ_ID AND A.Factory_ID = C.Factory_ID "
				+ "  JOIN WS D ON C.WS_ID = D.WS_ID AND A.Factory_ID = D.Factory_ID"
				+ " WHERE A.Factory_ID = '" + F001 + "' AND B.Code_Value is not NULL ";

		if (!DCIString.isNullOrEmpty(F002)) {
			sql += " AND C.WS_ID = '" + F002 + "' ";
		}

		if (!DCIString.isNullOrEmpty(F003s)) {
			sql += " AND A.End_Time >= '" + F003s + "' ";
		}

		if (!DCIString.isNullOrEmpty(F003e)) {
			sql += " AND A.Start_Time <= '" + F003e + "' ";
		}
		
		if(F006){
			sql += "AND B.Code_Value is not NULL " ;
		}
		

		if (!DCIString.isNullOrEmpty(F008)) {
			F008 = F008.replace("(", "_");
			F008 = F008.replace(")", "");
			F008 = F008.replace(";", "','");
			F008 = "'" + F008 + "'";
			sql += "AND A.EQ_Key IN (" + F008 + ") ";
		}

		if (!DCIString.isNullOrEmpty(F009)) {
			sql += "AND A.EQ_Status = '-1' ";
		}

		sql += " GROUP BY B.Code_Name";
		return sql;
	}
}
