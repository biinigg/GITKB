package com.dsc.dci.customer.dci.jweb.funcs.ekb.subkanban;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
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
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.dci.jweb.DCIEnums.Database.ConnectionType;
import com.dci.jweb.DataBaseLib.Database.DatabaseObjects;
import com.dci.jweb.PublicLib.APControl;
import com.dci.jweb.PublicLib.DBControl;
import com.dci.jweb.PublicLib.DCIDate;
import com.dci.jweb.PublicLib.DCIString;
import com.dsc.dci.customer.dci.sqlcode.funcs.ekb.sqlSubKanBan;
import com.dsc.dci.jweb.funcs.ekb.kanban.KanBan;

public class SubKanBan {
	//private final String WSURL = "http://10.40.70.25/wfsft/WFSFTService.asmx/XMLAdapter";
	 private final String WSURL =
	 "http://localhost/wfsft/WFSFTService.asmx/XMLAdapter";
	// private final String WSURL =
	// "http://digiwindemo.cloudapp.net/wfsft/WFSFTService.asmx/XMLAdapter";
	private sqlSubKanBan cmd;
	private DatabaseObjects dbobj;
	private DBControl dbctrl;
	private String uid;
	private String errmsg;

	public SubKanBan(String uid) {
		this.dbobj = DatabaseObjects.getInstance();
		this.cmd = new sqlSubKanBan();
		this.dbctrl = new DBControl();
		this.uid = uid;
		this.errmsg = null;
	}

	public HashMap<String, Object> getKBDatas(HttpSession session, String sql_id, String conn_id, String filter) {
		HashMap<String, Object> subData = null;
		subData = new KanBan(session, this.uid).getKanBanData("1", "99999999", filter, "", sql_id, conn_id);
		return subData;
	}

	public HashMap<String, Object> getInitData(HttpSession session, String sql_id, String subsql_id) {
		HashMap<String, Object> subData = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String legendValue = null;
		KanBan kb = null;
		try {
			kb = new KanBan(session, this.uid);
			subData = kb.getInitData(sql_id);

			if (subData == null) {
				subData = new HashMap<String, Object>();
			}
			conn = this.dbobj.getConnection(ConnectionType.SYS);

			ps = conn.prepareStatement(this.cmd.getSqlLegend());
			ps.setString(1, subsql_id);
			rs = ps.executeQuery();
			if (rs.next()) {
				if (DCIString.isNullOrEmpty(rs.getString("legend"))) {
					subData.put("sublegend", "");
				} else {
					legendValue = kb.buildLegendLangs(subsql_id, rs.getString("legend"),
							new APControl().getUserInfoFromSession(session, uid, "lang"));
					subData.put("sublegend", lightLegendBuilder(legendValue));
				}
			} else {
				subData.put("sublegend", "");
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			this.dbctrl.closeConnection(rs, ps, conn);
		}

		return subData;
	}

	private String lightLegendBuilder(String legendStr) {
		String legend = null;
		String[] legends = null;
		String[] item = null;

		try {
			if (!DCIString.isNullOrEmpty(legendStr)) {
				legends = legendStr.split(";");

				if (legends != null && legends.length > 0) {
					for (int i = 0; i < legends.length; i++) {
						if (legends[i].indexOf("=") != -1) {
							item = legends[i].split("=");
							if (DCIString.isNullOrEmpty(legend)) {
								legend = "<table>";
							}

							legend += "<td><img src='./../../ImageLoader.dsc?iconid=" + DCIString.Base64Encode(item[0])
									+ "' width='20' height='20'/></td><td><font size='3'>" + item[1] + "</font></td>";
						}
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			legend = "";
		} finally {
			if (DCIString.isNullOrEmpty(legend)) {
				legend = "";
			} else {
				legend += "</tr></table>";
			}
		}
		return legend;
	}

	public HashMap<String, Object> doAction(String conn_id, String action, String rowdatas) {
		HashMap<String, Object> result = null;
		HashMap<String, String> datas = null;
		boolean ok = false;

		result = new HashMap<String, Object>();
		if (!DCIString.isNullOrEmpty(rowdatas)) {
			datas = DCIString.jsonTranToStrMap(rowdatas);
			ok = callWebService(buildSXML(conn_id, action, datas));
		}

		result.put("success", ok);
		result.put("message", this.errmsg);
		return result;
	}

	private String buildSXML(String conn_id, String action, HashMap<String, String> datas) {
		String xml = null;
		org.dom4j.Document doc = DocumentHelper.createDocument();
		Element root = doc.addElement("STD_IN");
		Element data = null;
		Element head = null;
		Element body = null;
		Element record = null;
		String today = null;
		// String dbname = null;

		// dbname = DataDatabaseObject.getInstance().getDatabaseName(conn_id);
		root.addElement("Companyid").addText(datas.get("COMPANY"));
		root.addElement("Userid").addText(this.uid);
		root.addElement("DoAction").addText(action);
		root.addElement("Docase").addText("1");
		data = root.addElement("Data");
		head = data.addElement("FormHead");
		today = DCIDate.getToday("yyyyMMdd");
		if (action.equals("4")) {
			head.addElement("TableName").addText("MOCTC");
			record = head.addElement("RecordList");
			record.addElement("TC001").addText("541");
			record.addElement("TC003").addText(today);
			record.addElement("TC004").addText(datas.get("TA019"));
			record.addElement("TC005").addText(datas.get("TA021"));
			record.addElement("TC006").addText(datas.get("TA032"));
			record.addElement("TC007").addText("");
			record.addElement("TC013").addText("Y");
			record.addElement("TC014").addText(today);
			record.addElement("CREATOR").addText("DS");
			record.addElement("COMPANY").addText(datas.get("COMPANY"));

			body = data.addElement("FormBody");
			body.addElement("TableName").addText("MOCTD");
			record = body.addElement("RecordList");
			record.addElement("TD003").addText(datas.get("TA001"));
			record.addElement("TD004").addText(datas.get("TA002"));
			record.addElement("TD009").addText("");
			record.addElement("TD010").addText("");
			record.addElement("TD011").addText("");
			record.addElement("TD012").addText("");
		} else if (action.equals("9")) {
			head.addElement("TableName").addText("MOCTR");
			record = head.addElement("RecordList");
			record.addElement("TR001").addText("5C0");
			record.addElement("TR004").addText(datas.get("TA001"));
			record.addElement("TR005").addText(datas.get("TA002"));
			record.addElement("TR006").addText(datas.get("input01"));
			record.addElement("TR007").addText(datas.get("input02"));
			record.addElement("CREATOR").addText("DS");
			record.addElement("COMPANY").addText(datas.get("COMPANY"));
		}

		xml = doc.asXML();

		System.out.println("Request XML : \r\n" + xml);

		return xml;
	}

	public boolean callWebService(String data) {
		boolean success = false;
		int timeout = 50000;
		String strReturn = "";
		InputStream is = null;
		XMLInputFactory inputFactory = null;
		XMLEventReader eventReader = null;
		XMLEvent event = null;
		CloseableHttpResponse response = null;
		CloseableHttpClient httpclient = null;
		HttpPost httpPost = null;
		RequestConfig requestConfig = null;
		HttpEntity entity = null;
		ArrayList<NameValuePair> nvps = null;
		DocumentBuilderFactory factory = null;
		DocumentBuilder builder = null;
		Document document = null;
		NodeList list = null;
		NodeList list2 = null;

		try {
			httpclient = HttpClients.createDefault();
			requestConfig = RequestConfig.custom().setSocketTimeout(timeout).setConnectTimeout(timeout).build();
			httpPost = new HttpPost(this.WSURL);
			httpPost.addHeader(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded");
			httpPost.setConfig(requestConfig);
			nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair("sXML", data));
			httpPost.setEntity(new UrlEncodedFormEntity(nvps));

			response = httpclient.execute(httpPost);
			entity = response.getEntity();
			// System.out.println(EntityUtils.toString(entity));
			System.out.println("recv response");
			is = entity.getContent();
			inputFactory = XMLInputFactory.newInstance();
			eventReader = inputFactory.createXMLEventReader(is);
			strReturn = "";
			while (eventReader.hasNext()) {
				event = eventReader.nextEvent();
				// System.out.println(event.asCharacters().getData());
				if (!event.isStartDocument() && !event.isEndDocument() && !event.isStartElement()
						&& !event.isEndElement()) {
					//if (event.getEventType() == XMLStreamConstants.CHARACTERS) {
						strReturn = event.asCharacters().getData();
					//} else {
					//	strReturn += event.toString();
					//}
					// break;
					// System.out.println(strReturn);
				}
			}
			// System.out.println("out loop");
			EntityUtils.consume(entity);
			System.out.println(strReturn);
			if (strReturn.indexOf("<") != -1) {
				strReturn = strReturn.substring(strReturn.indexOf("<"));
			}
			System.out.println(strReturn);
			factory = DocumentBuilderFactory.newInstance();
			builder = factory.newDocumentBuilder();
			document = builder.parse(new InputSource(new StringReader(strReturn)));
			list = document.getChildNodes();

			for (int i = 0; i < list.getLength(); i++) {
				if (list.item(i).getNodeName().equals("STD_IN")) {
					list2 = list.item(i).getChildNodes();
					for (int j = 0; j < list2.getLength(); j++) {
						if (list2.item(j).getNodeName().equals("Result")) {
							success = list2.item(j).getTextContent().trim().toLowerCase().equals("success");
							continue;
						}
						if (list2.item(j).getNodeName().equals("Exception")) {
							if (!success) {
								this.errmsg = list2.item(j).getTextContent();
								// System.out.println(list2.item(j).getTextContent());
							}
						}
					}
				}
			}

		} catch (Exception e) {
			if (DCIString.isNullOrEmpty(this.errmsg)) {
				this.errmsg = e.getMessage();
			}
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
				e.printStackTrace();
			}
		}

		return success;
	}
}
