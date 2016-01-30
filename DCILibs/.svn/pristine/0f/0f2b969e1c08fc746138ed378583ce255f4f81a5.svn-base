package com.dci.jweb.DCIInterface.Database;

import java.sql.Connection;

import com.dci.jweb.DCIBeans.Database.DBInfoBean;
import com.dci.jweb.DCIEnums.Database.ConnectionType;
import com.dci.jweb.DCIEnums.Database.DBType;

public interface IDBObjects {
	// public void createDataSource(String addr, String port, String name,
	// String user, String pwd,
	// DBType dbtype, ConnectionType conntype);
	public void createDataSource(IDBInfoBean DBInfo, ConnectionType conntype);

	public Connection getConnection(ConnectionType conntype);

	public void clearDataSource(ConnectionType conntype);

	public boolean isDataSourceExist(ConnectionType conntype);

	public void copySysDBConfigSetting(IDBInfoBean bean);
}
