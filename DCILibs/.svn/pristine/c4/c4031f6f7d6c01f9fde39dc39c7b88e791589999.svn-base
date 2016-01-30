package com.dci.jweb.DataBaseLib.Database;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;

import com.dci.jweb.DCIBeans.Database.DBInfoBean;
import com.dci.jweb.DCIEnums.Database.ConnectionType;
import com.dci.jweb.DCIEnums.Database.DBType;
import com.dci.jweb.DCIInterface.Database.IDBInfoBean;
import com.dci.jweb.DCIInterface.Database.IDBObjects;

/**
 * @Author Ben
 * 
 * @Description :共用的DataSource，包含對ERP與SYS的連線。
 * 
 * @ModifyMemo : 2013/5/6 first version
 * 
 */
public class DatabaseObjects implements IDBObjects {
	private static DatabaseObjects _parames;

	private DataSource erpDatasource;
	private DataSource sysDatasource;

	// public DatabaseObjects() {
	// this.erpDatasource = null;
	// this.sysDatasource = null;
	// }

	public static DatabaseObjects getInstance() {
		synchronized (DatabaseObjects.class) {
			if (_parames == null) {
				_parames = new DatabaseObjects();
			}
		}
		return _parames;
	}

	public void clearDataSource(ConnectionType conntype) {
		if (conntype == ConnectionType.SYS) {
			// this.sftDBInfo = null;
			this.sysDatasource = null;
		} else if (conntype == ConnectionType.ERP) {
			// this.erpDBInfo = null;
			this.erpDatasource = null;
		}
	}

	public void createDataSource(IDBInfoBean DBInfo, ConnectionType conntype) {

		try {
			if (conntype == ConnectionType.ERP) {
				this.erpDatasource = new Database().createConnectionPool(DBInfo);
			} else if (conntype == ConnectionType.SYS) {
				this.sysDatasource = new Database().createConnectionPool(DBInfo);
			} else {
				System.out.println("SFTErr-DB001:Connection type setting error " + conntype.toString());
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("SFTErr-DB003:DataSource create error");
		}
	}

	public Connection getConnection(ConnectionType conntype) {
		Connection conn = null;
		try {
			if (conntype == ConnectionType.ERP) {
				if (this.erpDatasource != null) {
					conn = this.erpDatasource.getConnection();
				}
			} else if (conntype == ConnectionType.SYS) {
				if (this.sysDatasource != null) {
					conn = this.sysDatasource.getConnection();
				}
			}
		} catch (Exception ex) {
			conn = null;
			ex.printStackTrace();
			System.out.println("SFTErr-DB002:Get connection from datasource error by type " + conntype);
		}
		return conn;
	}

	public boolean isDataSourceExist(ConnectionType conntype) {
		boolean exist = false;
		if (conntype == ConnectionType.ERP) {
			exist = this.erpDatasource != null;
		} else if (conntype == ConnectionType.SYS) {
			exist = this.sysDatasource != null;
		}
		return exist;
	}

	public void copySysDBConfigSetting(IDBInfoBean bean) {
		if (bean == null) {
			bean = new DBInfoBean();
		}

		if (this.sysDatasource == null) {
			bean = null;
		} else {
			BasicDataSource bds = (BasicDataSource) this.sysDatasource;

			bean.setMaxActive(bds.getMaxActive());
			bean.setMaxIdle(bds.getMaxIdle());
			bean.setMinIdle(bds.getMinIdle());
			bean.setMaxWait(bds.getMaxWait());
			bean.setRemoveAbandoned(bds.getRemoveAbandoned());
			bean.setRemoveAbandonedTimeout(bds.getRemoveAbandonedTimeout());
		}
	}

	public DBInfoBean copySysDBConfigSetting() {
		DBInfoBean bean = new DBInfoBean();

		if (this.sysDatasource == null) {
			bean = null;
		} else {
			BasicDataSource bds = (BasicDataSource) this.sysDatasource;

			bean.setMaxActive(bds.getMaxActive());
			bean.setMaxIdle(bds.getMaxIdle());
			bean.setMinIdle(bds.getMinIdle());
			bean.setMaxWait(bds.getMaxWait());
			bean.setRemoveAbandoned(bds.getRemoveAbandoned());
			bean.setRemoveAbandonedTimeout(bds.getRemoveAbandonedTimeout());
		}

		return bean;
	}
}
