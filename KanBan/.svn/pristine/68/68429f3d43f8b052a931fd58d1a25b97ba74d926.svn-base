package com.dsc.dci.jweb.tasks;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;

import javax.sql.DataSource;

import com.dci.jweb.DCIBeans.Database.DBInfoBean;
import com.dci.jweb.DCIEnums.Database.ConnectionType;
import com.dci.jweb.DCIEnums.Database.DBType;
import com.dci.jweb.DCIInterface.Server.IDCITask;
import com.dci.jweb.DataBaseLib.Database.DataDatabaseObject;
import com.dci.jweb.DataBaseLib.Database.DatabaseObjects;
import com.dci.jweb.PublicLib.DBControl;
import com.dci.jweb.PublicLib.DCIString;
import com.dsc.dci.sqlcode.main.sqlTask;

public class ConnectionCheck implements Runnable, IDCITask {

	private final String taskid = "conncheck";
	private long gap;
	private boolean keeprun;

	public ConnectionCheck() {
		this.gap = 60000;
		this.keeprun = true;
	}

	private void todo() {
		HashMap<String, DataSource> conns = null;
		HashMap<String, String> checked = null;
		Connection conn = null;
		Connection dconn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		DBControl dbctrl = new DBControl();
		DataSource ds = null;
		boolean reconn = false;
		DBInfoBean bean = null;

		try {
			checked = new HashMap<String, String>();
			conns = DataDatabaseObject.getInstance().getAllConnetions();

			conn = DatabaseObjects.getInstance().getConnection(ConnectionType.SYS);
			ps = conn.prepareStatement(new sqlTask().getAllConns());
			rs = ps.executeQuery();

			while (rs.next()) {
				reconn = false;
				checked.put(rs.getString("conn_id"), "");
				if (conns.containsKey(rs.getString("conn_id"))) {
					ds = conns.get(rs.getString("conn_id"));
					if (ds == null) {
						reconn = true;
					} else {
						try {
							dconn = ds.getConnection();
							if (dconn == null || dconn.isClosed()) {
								System.out.println("closed conn : " + rs.getString("conn_id"));
								reconn = true;
							}
						} catch (Exception ex) {
							reconn = true;
						} finally {
							dbctrl.closeConnection(null, null, dconn);
						}
					}

					if (reconn) {
						bean = new DBInfoBean();
						DatabaseObjects.getInstance().copySysDBConfigSetting(bean);
						bean.setConnID(rs.getString("conn_id"));
						bean.setDBAddr(rs.getString("db_addr"));
						bean.setDBPort(rs.getString("db_port"));
						bean.setDBType(DBType.valueOf(rs.getString("db_type")));
						bean.setDBName(rs.getString("db_name"));
						bean.setUserName(rs.getString("db_user"));
						bean.setPassword(DCIString.Base64Decode(rs.getString("db_pwd")));
						DataDatabaseObject.getInstance().removeDataSource(rs.getString("conn_id"));
						DataDatabaseObject.getInstance().createDataDataSource(bean, rs.getString("conn_id"));
					}
				} else {
					bean = new DBInfoBean();
					DatabaseObjects.getInstance().copySysDBConfigSetting(bean);
					bean.setConnID(rs.getString("conn_id"));
					bean.setDBAddr(rs.getString("db_addr"));
					bean.setDBPort(rs.getString("db_port"));
					bean.setDBType(DBType.valueOf(rs.getString("db_type")));
					bean.setDBName(rs.getString("db_name"));
					bean.setUserName(rs.getString("db_user"));
					bean.setPassword(DCIString.Base64Decode(rs.getString("db_pwd")));
					DataDatabaseObject.getInstance().createDataDataSource(bean, rs.getString("conn_id"));
				}
			}

			for (String key : conns.keySet()) {
				if (!checked.containsKey(key)) {
					DataDatabaseObject.getInstance().removeDataSource(key);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dbctrl.closeConnection(rs, ps, conn);
		}

	}

	@Override
	public String getTaskID() {
		return this.taskid;
	}

	@Override
	public void setTaskGap(long gap) {
		this.gap = gap;
	}

	@Override
	public void stopTask() {
		this.keeprun = false;
	}

	@Override
	public void run() {
		try {
			while (this.keeprun) {
				try {
					todo();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				Thread.currentThread();
				Thread.sleep(this.gap);
			}
		} catch (InterruptedException e) {

		}
	}

}
