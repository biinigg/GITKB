package com.dsc.dci.jweb.tasks;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.events.XMLEvent;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.dci.jweb.DCIEnums.Database.ConnectionType;
import com.dci.jweb.DCIInterface.Server.IDCITask;
import com.dci.jweb.DataBaseLib.Database.DataDatabaseObject;
import com.dci.jweb.DataBaseLib.Database.DatabaseObjects;
import com.dci.jweb.PublicLib.DBControl;
import com.dci.jweb.PublicLib.DCIString;
import com.dsc.dci.sqlcode.funcs.apps.sqlAppFunctions;

public class AppPushKanBan implements Runnable, IDCITask {

	private final String taskid = "push2app";
	private long gap;
	private boolean keeprun;

	public AppPushKanBan() {
		this.gap = 5000;
		this.keeprun = true;
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

	private void todo() {
		DatabaseObjects dbobj = null;
		sqlAppFunctions cmd = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		//System.out.println("check msg");
		try {
			dbobj = DatabaseObjects.getInstance();
			cmd = new sqlAppFunctions();
			conn = dbobj.getConnection(ConnectionType.SYS);

			ps = conn.prepareStatement(cmd.getPushData());

			rs = ps.executeQuery();

			while (rs.next()) {
				if (rs.getString("msg").indexOf("@") == -1) {
					appCallWebService(rs.getString("msg"));
				} else {
					sendKBData2App(conn, rs.getString("sql_id"), rs.getString("conn_id"), rs.getString("msg"));
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			new DBControl().closeConnection(rs, ps, conn);
		}
	}

	private void sendKBData2App(Connection conn, String sql_id, String conn_id, String msg) {
		boolean reconn = false;
		Connection kbConn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		PreparedStatement ps2 = null;
		ResultSet rs2 = null;
		String sqlcmd = null;
		sqlAppFunctions cmd = null;
		ResultSetMetaData rsmd = null;

		try {
			if (conn == null || conn.isClosed()) {
				conn = DatabaseObjects.getInstance().getConnection(ConnectionType.SYS);
				reconn = true;
			}
			cmd = new sqlAppFunctions();

			ps = conn.prepareStatement(cmd.getKanBanData());
			ps.setString(1, sql_id);
			rs = ps.executeQuery();

			if (rs.next()) {
				sqlcmd = rs.getString("sql_context");

				if (!DCIString.isNullOrEmpty(rs.getString("group_by"))) {
					sqlcmd += "\r\n group by " + rs.getString("group_by");
				}

				if (!DCIString.isNullOrEmpty(rs.getString("order_by"))) {
					sqlcmd += "\r\n order by " + rs.getString("order_by");
				}

				kbConn = DataDatabaseObject.getInstance().getConnection(conn_id);
				ps2 = kbConn.prepareStatement(sqlcmd);
				rs2 = ps2.executeQuery();

				rsmd = rs2.getMetaData();
				while (rs2.next()) {
					for (int i = 1; i <= rsmd.getColumnCount(); i++) {
						msg = msg.replace("@" + rsmd.getColumnName(i), rs2.getString(rsmd.getColumnName(i)));
					}
					//System.out.println(msg);
					if (msg.indexOf("@") == -1) {
						appCallWebService(msg);
					} else {
						System.err.println(this.getClass().getName() + ": " + "create app message fail " + sql_id + "-"
								+ conn_id);
					}
				}

			}
		} catch (Exception ex) {
			System.err.println(ex.getClass().getName() + ": " + "send message to app fail " + sql_id + "-" + conn_id
					+ " " + ex.getMessage());
		} finally {
			DBControl dbctrl = new DBControl();
			dbctrl.closeConnection(rs2, ps2, kbConn);
			if (reconn) {
				dbctrl.closeConnection(rs, ps, conn);
			} else {
				dbctrl.closeConnection(rs, ps, null);
			}
		}

	}

	private boolean appCallWebService(String data) {
		boolean success = false;
		String url = "http://localhost/MobileSite/pushservice.asmx/PushDevicePMG";
		String strReturn = "";
		InputStream is = null;
		XMLInputFactory inputFactory = null;
		XMLEventReader eventReader = null;
		XMLEvent event = null;
		CloseableHttpResponse response = null;
		CloseableHttpClient httpclient = null;
		HttpPost httpPost = null;
		HttpEntity entity = null;
		ArrayList<NameValuePair> nvps = null;
		// url = "http://10.20.88.196/eKB/pushservice.asmx/PushDevicePMG";
		try {
			//System.out.println(url);
			httpclient = HttpClients.createDefault();
			httpPost = new HttpPost(url);
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(5000).setConnectTimeout(5000).build();
			httpPost.setConfig(requestConfig);
			httpPost.addHeader(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded");
			nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair("pKey", ""));
			nvps.add(new BasicNameValuePair("pPushMsg", DCIString
					.Base64Encode("<PushMsg><From>eKB</From><TargetUser>DS</TargetUser><Message><alert>" + data
							+ "</alert><keyfield></keyfield></Message></PushMsg>")));
			httpPost.setEntity(new UrlEncodedFormEntity(nvps));

			response = httpclient.execute(httpPost);
			entity = response.getEntity();
			//System.out.println(EntityUtils.toString(entity));
			is = entity.getContent();
			inputFactory = XMLInputFactory.newInstance();
			eventReader = inputFactory.createXMLEventReader(is);
			strReturn = "";
			while (eventReader.hasNext()) {
				event = eventReader.nextEvent();
				//System.out.println(event);
				if (!event.isStartDocument() && !event.isEndDocument() && !event.isStartElement()
						&& !event.isEndElement()) {
					if (event.isCharacters()) {
						strReturn = event.asCharacters().getData();
					} else {
						strReturn = event.toString();
					}
				}
			}
			EntityUtils.consume(entity);
			//System.out.println("return msg : " + strReturn);
			System.out.println(DCIString.Base64Decode(strReturn));

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (response != null) {
					response.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

			try {
				if (is != null) {
					is.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return success;
	}

}
