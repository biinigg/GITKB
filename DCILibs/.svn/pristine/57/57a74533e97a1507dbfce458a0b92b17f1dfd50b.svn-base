package com.dci.jweb.PublicLib;

import java.sql.Connection;
import java.sql.SQLException;

import com.dci.jweb.DCIEnums.Database.DBType;

public class DCIDBObj {
	public static DBType getDBType(Connection conn) {
		DBType dbtp = null;
		String driverName = null;

		try {
			if (conn != null && !conn.isClosed()) {
				driverName = conn.getMetaData().getDriverName();

				if (driverName.replaceAll(" ", "").toLowerCase().indexOf("oracle") != -1) {
					dbtp = DBType.Oracle;
				} else if (driverName.replaceAll(" ", "").toLowerCase().indexOf("sqlserver") != -1) {
					dbtp = DBType.SqlServer;
				}
			}
		} catch (SQLException e) {
			dbtp = null;
			e.printStackTrace();
		}

		return dbtp;
	}
}
