package com.dci.jweb.DataBaseLib.Database;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

import javax.sql.DataSource;

import com.dci.jweb.DCIBeans.Database.DBInfoBean;
import com.dci.jweb.DCIEnums.Database.DBType;
import com.dci.jweb.DCIInterface.Database.IDBInfoBean;

/**
 * @Author : Ben
 * 
 * @Description : 各公司別的DataSource
 * 
 * @ModifyMemo : 2013/5/6 first version
 * 
 */

public class DataDatabaseObject {
	private static DataDatabaseObject _parames;
	private HashMap<String, DataSource> dataSourceMap;

	public DataDatabaseObject() {

	}

	public static DataDatabaseObject getInstance() {
		synchronized (DataDatabaseObject.class) {
			if (_parames == null) {
				_parames = new DataDatabaseObject();
			}
		}
		return _parames;
	}

	public void clearDataSource(String compcode) {
		this.dataSourceMap.remove(compcode);
	}

	public void clearDataSource() {
		if (this.dataSourceMap != null) {
			this.dataSourceMap.clear();
		}
	}

	public boolean isDataSourceExist() {
		boolean exist = false;

		if (this.dataSourceMap != null && !this.dataSourceMap.isEmpty() && this.dataSourceMap.size() > 0) {
			exist = true;
		}

		return exist;
	}

	public boolean isDataSourceExist(String compcode) {
		boolean exist = false;

		if (this.dataSourceMap != null && !this.dataSourceMap.isEmpty() && this.dataSourceMap.size() > 0) {
			if (this.dataSourceMap.containsKey(compcode)) {
				exist = true;
			}
		}

		return exist;
	}

	// public void createSFTDataSource(String addr, String port, String name,
	// String user, String pwd,
	// DBType dbtype, String compcode) {
	public void createDataDataSource(IDBInfoBean DBInfo, String compcode) {
		DataSource dataDatasource;
		// DBInfoBean DBInfo = null;
		if (this.dataSourceMap == null) {
			this.dataSourceMap = new HashMap<String, DataSource>();
		}
		if (!this.dataSourceMap.containsKey(compcode)) {
			try {
				dataDatasource = new Database().createConnectionPool(DBInfo);

				if (dataDatasource != null) {
					this.dataSourceMap.put(compcode, dataDatasource);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				System.out.println("SFTErr-DB003:DataSource create error");
			}
		}
	}

	public void removeDataSource(String compcode) {
		try {
			if (this.dataSourceMap != null && this.dataSourceMap.size() != 0
					&& this.dataSourceMap.containsKey(compcode)) {
				this.dataSourceMap.remove(compcode);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public Connection getConnection(String compcode) {
		Connection conn = null;
		try {
			if (this.dataSourceMap != null && this.dataSourceMap.size() != 0) {
				if (this.dataSourceMap.containsKey(compcode)) {
					conn = ((DataSource) this.dataSourceMap.get(compcode)).getConnection();
				} else {
					System.out.println("SFTErr-DB004:Connection " + compcode + " not exists");
				}
			}
		} catch (Exception ex) {
			conn = null;
			ex.printStackTrace();
			System.out.println("SFTErr-DB002:Get connection from datasource error by type DATA");
		}

		return conn;
	}

	public HashMap<String, DataSource> getAllConnetions() {
		if (this.dataSourceMap == null) {
			return new HashMap<String, DataSource>();
		} else {
			return this.dataSourceMap;
		}
	}

	public String getDatabaseName(String compcode) {
		String dbname = null;
		Connection conn = null;

		try {
			conn = this.getConnection(compcode);

			if (conn != null) {
				dbname = conn.getCatalog();
			}
		} catch (Exception ex) {
			dbname = "";
		} finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
					conn = null;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		if (dbname == null) {
			dbname = "";
		}

		return dbname;
	}
}
