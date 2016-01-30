package com.dci.jweb.DCIEnums.Database;

public enum DBType {
	SqlServer, Oracle;

	// , DB2, MySQL, Informix
	public static String[] StringValues() {
		String[] values = new String[2];
		values[0] = DBType.SqlServer.toString();
		values[1] = DBType.Oracle.toString();
		// values[2] = DBType.Informix.toString();
		// values[2] = DBType.DB2.toString();
		// values[3] = DBType.MySQL.toString();
		return values;
	}

	public static String valueToLabel(DBType value) {
		String label = null;

		switch (value) {
		case Oracle:
			label = "Oracle";
			break;
		case SqlServer:
			label = "SQL Server";
			break;
		/*
		 * case Informix: label = "Informix"; break;
		 */
		default:
			label = "";
			break;
		}

		return label;
	}

	public static String valueToLabel(String value) {
		String label = null;

		switch (DBType.valueOf(value)) {
		case Oracle:
			label = "Oracle";
			break;
		case SqlServer:
			label = "SQL Server";
			break;
		/*
		 * case Informix: label = "Informix"; break;
		 */
		default:
			label = "";
			break;
		}

		return label;
	}

	public static String[] LabelValues() {
		String[] values = new String[2];
		values[0] = "SQL Server";
		values[1] = "Oracle";
		// values[2] = "Informix";
		// values[2] = DBType.DB2.toString();
		// values[3] = DBType.MySQL.toString();
		return values;
	}
}