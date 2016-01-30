package com.dci.jweb.DCIInterface.Database;

import com.dci.jweb.DCIEnums.Database.DBType;

public interface IDBInfoBean {
	// /DBConfig
	public String getConnID();

	public String getDBAddr();

	public String getDBPort();

	public String getDBName();

	public String getUserName();

	public String getPassword();

	public DBType getDBType();

	public void setConnID(String newvalue);

	public void setDBAddr(String newvalue);

	public void setDBPort(String newvalue);

	public void setDBName(String newvalue);

	public void setUserName(String newvalue);

	public void setPassword(String newvalue);

	public void setDBType(DBType newvalue);

	// /DBPool

	public int getMaxActive();

	public long getMaxWait();

	public int getMaxIdle();

	public int getMinIdle();

	public boolean isRemoveAbandoned();

	public int getRemoveAbandonedTimeout();

	public void setMaxActive(int newvalue);

	public void setMaxWait(long newvalue);

	public void setMaxIdle(int newvalue);

	public void setMinIdle(int newvalue);

	public void setRemoveAbandoned(boolean newvalue);

	public void setRemoveAbandonedTimeout(int newvalue);

}
