package com.dsc.dci.sqlcode.funcs.lkb;

import com.dci.jweb.PublicLib.DCIString;

public class sqlLKB003 {
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
		sql = "SELECT A.EQ_ID,A.EQ_Name FROM EQ A WHERE 1=1 AND A.Factory_ID='" + p1 + "' ORDER BY A.EQ_ID";
		return sql;
	}

	public String F005Sql(String p1, String p2) {
		String sql = null;
		sql = "SELECT A.EQ_ID,A.EQ_Name,A.WS_ID,B.WS_Name FROM EQ A JOIN WS B ON A.Factory_ID=B.Factory_ID AND A.WS_ID=B.WS_ID "
				+ "WHERE A.Factory_ID='" + p1 + "' ";

		if (!DCIString.isNullOrEmpty(p2)) {
			sql += "AND A.WS_ID = '" + p2 + "' ";
		}

		sql += " ORDER BY A.EQ_ID";
		return sql;
	}

	public String F006Sql(String p1) {
		String sql = null;
		sql = "SELECT A.EQ_Property, B.Code_Name FROM EQ A JOIN Code_Mapping B ON A.EQ_Property=B.Code_Value "
				+ "AND B.Code_Category='EQ_Property' WHERE A.Factory_ID='" + p1 + "' ORDER BY A.EQ_Property";
		return sql;
	}

	public String F006Sql() {
		String sql = null;
		sql = "SELECT OP_Run.Order_ID  FROM OP_Run ORDER BY OP_Run.Order_ID";
		return sql;
	}

	public String F011Sql(String p1, String p2) {
		String sql = null;

		sql = "SELECT A.OP_ID,A.OP_Name,A.OP_Desc,B.WS_ID,B.WS_Name " + "FROM OP A "
				+ "LEFT JOIN WS B on A.WS_ID=B.WS_ID AND A.Factory_ID=B.Factory_ID WHERE A.Factory_ID='"
				+ p1 + "' ";

		if (!DCIString.isNullOrEmpty(p2)) {
			sql += "AND A.WS_ID = '" + p2 + "' ";
		}
		sql += "ORDER BY A.OP_ID";

		return sql;
	}

	public String querySql(String F001, String F002, String F003s, String F003e, String F005, String F006,
			boolean F008, String F009s, String F009e, String F011) {
		String sql = null;
		sql = "SELECT A.Order_ID, C.WS_ID + '/' + C.WS_Name WS, "
				+ "       D.OP_ID + '/' + D.OP_Name + '/' + A.OP_SEQ OP, A.EQ_ID, B.EQ_Name, E.Code_Name, "
				+ "       A.Out_Time, A.Arrive_Qty, A.Out_Qty, A.Out_Qty / A.Arrive_Qty UTILITY, "
				+ "       A.Shrinkage_Qty, A.Surplus_Qty, A.Rework_Qty " + "  FROM OP_Run A "
				+ "  LEFT JOIN EQ B ON A.EQ_ID = B.EQ_ID "
				+ "  LEFT JOIN WS C ON A.Factory_ID = C.Factory_ID AND A.WS_ID = C.WS_ID "
				+ "  LEFT JOIN OP D ON A.Factory_ID = D.Factory_ID AND A.OP_ID = D.OP_ID "
				+ "  LEFT JOIN Code_Mapping E ON B.EQ_Property = E.Code_Value AND "
				+ "                              E.Code_Category = 'EQ_Property' "
				+ " WHERE A.Factory_ID = '" + F001 + "' ";

		// if (F002 != null && F002.trim().length() > 0) {
		if (!DCIString.isNullOrEmpty(F002)) {
			sql += "AND A.WS_ID = '" + F002 + "' ";
		}

		if (!DCIString.isNullOrEmpty(F003s) && !DCIString.isNullOrEmpty(F003e)) {
			sql += "AND A.Out_Time BETWEEN '" + F003s + "' AND '" + F003e + "'  ";
		} else if (!DCIString.isNullOrEmpty(F003s) && DCIString.isNullOrEmpty(F003e)) {
			sql += "AND A.Out_Time >= '" + F003s + "' ";
		} else if (!DCIString.isNullOrEmpty(F003e) && DCIString.isNullOrEmpty(F003s)) {
			sql += "AND A.Out_Time <= '" + F003e + "' ";
		}

		/*
		 * if (F004 != null && F004.trim().length() > 0) { sql +=
		 * "AND B.EQ_Property IN (" + F004 + ") "; }
		 */

		if (!DCIString.isNullOrEmpty(F005)) {
			F005 = F005.replace("(", "_");
			F005 = F005.replace(")", "");
			F005 = F005.replace(";", "','");
			F005 = "'" + F005 + "'";
			sql += "AND A.EQ_Key IN (" + F005 + ") ";
		}

		if (!DCIString.isNullOrEmpty(F006)) {
			sql += "AND A.Order_ID = '" + F006 + "' ";
		}

		if (F008) {
			sql += "AND A. Arrive_Qty != Out_Qty ";
		}

		if (!DCIString.isNullOrEmpty(F009s) && !DCIString.isNullOrEmpty(F009e)) {
			sql += "AND A.Out_Qty/A.Arrive_Qty BETWEEN " + F009s + " AND " + F009e + "  ";
		} else if (!DCIString.isNullOrEmpty(F009s) && DCIString.isNullOrEmpty(F009e)) {
			sql += "AND A.Out_Qty/A.Arrive_Qty >= " + F009s + " ";
		} else if (!DCIString.isNullOrEmpty(F009e) && DCIString.isNullOrEmpty(F009s)) {
			sql += "AND A.Out_Qty/A.Arrive_Qty <= " + F009s + " ";
		}

		if (!DCIString.isNullOrEmpty(F011)) {
			F011 = F011.replace(";", "','");
			F011 = "'" + F011 + "'";
			sql += "AND A.OP_ID IN (" + F011 + ") ";
		}

		sql += " ORDER BY A.Order_ID, A.WS_ID, A.OP_ID, A.OP_SEQ, A.Sequence";

		return sql;
	}

	public String seriesSql(String F001, String F002, String F003s, String F003e, String F005, String F006,
			String F011) {
		String sql = null;

		sql = "SELECT distinct A.EQ_ID series FROM OP_Run A " + "  LEFT JOIN EQ B ON A.EQ_ID = B.EQ_ID "
				+ "  LEFT JOIN WS C ON A.Factory_ID = C.Factory_ID AND A.WS_ID = C.WS_ID "
				+ "  LEFT JOIN OP D ON A.Factory_ID = D.Factory_ID AND A.OP_ID = D.OP_ID "
				+ "  LEFT JOIN Code_Mapping E ON B.EQ_Property = E.Code_Value AND "
				+ "                              E.Code_Category = 'EQ_Property' "
				+ " where A.Factory_ID = '" + F001 + "' ";

		if (!DCIString.isNullOrEmpty(F002)) {
			sql += "AND A.WS_ID = '" + F002 + "' ";
		}

		if (!DCIString.isNullOrEmpty(F003s) && !DCIString.isNullOrEmpty(F003e)) {
			sql += "AND A.Out_Time BETWEEN '" + F003s + "' AND '" + F003e + "'  ";
		} else if (!DCIString.isNullOrEmpty(F003s) && DCIString.isNullOrEmpty(F003e)) {
			sql += "AND A.Out_Time >= '" + F003s + "' ";
		} else if (!DCIString.isNullOrEmpty(F003e) && DCIString.isNullOrEmpty(F003s)) {
			sql += "AND A.Out_Time <= '" + F003e + "' ";
		}

		if (!DCIString.isNullOrEmpty(F005)) {
			F005 = F005.replace("(", "_");
			F005 = F005.replace(")", "");
			F005 = F005.replace(";", "','");
			F005 = "'" + F005 + "'";
			sql += "AND A.EQ_Key IN (" + F005 + ") ";
		}

		if (!DCIString.isNullOrEmpty(F006)) {
			sql += "AND A.Order_ID = '" + F006 + "' ";
		}

		if (!DCIString.isNullOrEmpty(F011)) {
			F011 = F011.replace(";", "','");
			F011 = "'" + F011 + "'";
			sql += "AND A.OP_ID IN (" + F011 + ") ";
		}

		return sql;
	}

	public String chartSql(String F001, String F002, String F003s, String F003e, String F005, String F006,
			String F007, String F011) {
		String sql = null;

		sql = "SELECT A.EQ_ID, B.EQ_Name, CONVERT(char(10),DATEADD(SECOND, - " + F007
				+ ", A.Out_Time) ,111) BELONG_DATE, " + "       SUM(A.Out_Qty) / SUM(A.Arrive_Qty) YIELD "
				+ "  FROM OP_Run A " + "  LEFT JOIN EQ B ON A.EQ_ID = B.EQ_ID "
				+ "  LEFT JOIN WS C ON A.Factory_ID = C.Factory_ID AND A.WS_ID = C.WS_ID "
				+ "  LEFT JOIN OP D ON A.Factory_ID = D.Factory_ID AND A.OP_ID = D.OP_ID "
				+ "  LEFT JOIN Code_Mapping E ON B.EQ_Property = E.Code_Value AND "
				+ "                              E.Code_Category = 'EQ_Property' "
				+ " WHERE 1 = 1 AND A.Factory_ID = '" + F001 + "'";

		if (!DCIString.isNullOrEmpty(F002)) {
			sql += "AND A.WS_ID = '" + F002 + "' ";
		}

		if (!DCIString.isNullOrEmpty(F003s) && !DCIString.isNullOrEmpty(F003e)) {
			sql += "AND A.Out_Time BETWEEN '" + F003s + "' AND '" + F003e + "'  ";
		} else if (!DCIString.isNullOrEmpty(F003s) && DCIString.isNullOrEmpty(F003e)) {
			sql += "AND A.Out_Time >= '" + F003s + "' ";
		} else if (!DCIString.isNullOrEmpty(F003e) && DCIString.isNullOrEmpty(F003s)) {
			sql += "AND A.Out_Time <= '" + F003e + "' ";
		}

		if (!DCIString.isNullOrEmpty(F005)) {
			F005 = F005.replace("(", "_");
			F005 = F005.replace(")", "");
			F005 = F005.replace(";", "','");
			F005 = "'" + F005 + "'";
			sql += "AND A.EQ_Key IN (" + F005 + ") ";
		}

		if (!DCIString.isNullOrEmpty(F006)) {
			sql += "AND A.Order_ID = '" + F006 + "' ";
		}

		if (!DCIString.isNullOrEmpty(F011)) {
			F011 = F011.replace(";", "','");
			F011 = "'" + F011 + "'";
			sql += "AND A.OP_ID IN (" + F011 + ") ";
		}

		sql += "GROUP BY A.EQ_ID, B.EQ_Name, CONVERT(char(10),DATEADD(SECOND, - " + F007
				+ ", A.Out_Time) ,111) " + "ORDER BY BELONG_DATE,A.EQ_ID";

		return sql;
	}

}
