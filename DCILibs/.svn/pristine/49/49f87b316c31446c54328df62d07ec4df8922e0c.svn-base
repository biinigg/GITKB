package com.dci.jweb.DataBaseLib.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;
import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbcp.BasicDataSourceFactory;

import com.dci.jweb.DCIEnums.Database.DBType;
import com.dci.jweb.DCIExceptions.Database.TestDatabaseException;
import com.dci.jweb.DCIInterface.Database.IDBInfoBean;

public class Database {

	public Database() {

	}

	public DataSource createConnectionPool(IDBInfoBean infos) {
		DataSource ds = null;
		Connection conn = null;
		try {
			if (infos != null) {
				Properties dsProperties = new Properties();
				dsProperties.setProperty("url",
						getUrl(infos.getDBType(), infos.getDBAddr(), infos.getDBPort(), infos.getDBName()));
				dsProperties.setProperty("driverClassName", getDrivname(infos.getDBType()));
				dsProperties.setProperty("username", infos.getUserName());
				dsProperties.setProperty("password", infos.getPassword());
				dsProperties.setProperty("maxActive", Integer.toString(infos.getMaxActive()));
				dsProperties.setProperty("minIdle", Integer.toString(infos.getMinIdle()));
				dsProperties.setProperty("maxIdle", Integer.toString(infos.getMaxIdle()));
				dsProperties.setProperty("maxWait", Long.toString(infos.getMaxWait()));
				dsProperties.setProperty("RemoveAbandoned", Boolean.toString(infos.isRemoveAbandoned()));
				dsProperties.setProperty("RemoveAbandonedTimeout",
						Integer.toString(infos.getRemoveAbandonedTimeout()));

				ds = BasicDataSourceFactory.createDataSource(dsProperties);

				// BasicDataSource bds = (BasicDataSource) ds;

				/*
				 * System.out.println("Maxactive:" + bds.getMaxActive() +
				 * "; MaxIdle:" + bds.getMaxIdle() + "; MinIdle:" +
				 * bds.getMinIdle());
				 */
				conn = ds.getConnection();
				System.out.println(infos.getConnID() + " connected");
			}
		} catch (Exception ex) {
			ds = null;
			// System.out.println(infos.getConnID() + " connect fail");
			// ex.printStackTrace();
			System.err.println(ex.getClass().getName() + ": " + infos.getConnID() + " connect fail" + " "
					+ ex.getMessage());
		} finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (Exception ex) {

			}
		}

		return ds;
	}

	public boolean testConnedtion(DBType dbtype, String addr, String port, String dbname, String userid,
			String pwd) throws Exception {
		boolean ok = false;
		Connection conn = null;

		try {
			Class.forName(getDrivname(dbtype));
			DriverManager.setLoginTimeout(3);
			conn = DriverManager.getConnection(getUrl(dbtype, addr, port, dbname), userid, pwd);

			if (conn != null && !conn.isClosed()) {
				ok = true;
			} else {
				ok = false;
			}
		} catch (Exception ex) {
			ok = false;
			throw new TestDatabaseException(ex.getMessage());
		} finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (Exception ex) {
			}
			conn = null;
		}

		return ok;
	}

	public String getUrl(DBType dbtype, String addr, String port, String dbname) {
		String url = null;

		if (dbtype == DBType.SqlServer) {
			url = "jdbc:sqlserver://" + addr + ":" + port + ";DatabaseName=" + dbname;
		} else if (dbtype == DBType.Oracle) {
			url = "jdbc:oracle:thin:@" + addr + ":" + port + ":" + dbname;
		} /*
		 * else if (dbtype == DBType.Informix) { String[] s = dbname.split("/");
		 * url = "jdbc:informix-sqli://" + addr + ":" + port + "/" + s[1] +
		 * ":INFORMIXSERVER=" + s[0]; } else if (dbtype == DBType.MySQL) { url =
		 * ""; } else if (dbtype == DBType.DB2) { url = "jdbc:derby:net://" +
		 * addr + ":" + port + "/" + dbname; }
		 */

		return url;
	}

	private String getDrivname(DBType dbtype) {
		String driverName = null;
		if (dbtype == DBType.SqlServer) {
			driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
		} else if (dbtype == DBType.Oracle) {
			driverName = "oracle.jdbc.driver.OracleDriver";
		}
		/*
		 * else if (dbtype == DBType.Informix) { driverName =
		 * "com.informix.jdbc.IfxDriver"; } else if (dbtype == DBType.MySQL) {
		 * driverName = "com.mysql.jdbc.Driver"; } else if (dbtype ==
		 * DBType.DB2) { driverName = "com.ibm.db2.jcc.DB2Driver"; }
		 */
		return driverName;
	}

}
