package com.dci.jweb.PublicLib;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.StringWriter;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import com.dci.jweb.DCIBeans.Database.DBInfoBean;
import com.dci.jweb.DCIEnums.Database.DBType;
import com.dci.jweb.DCIEnums.Server.LangType;

public class ConfigControl {
	private boolean isTestArea;
	private String contextPath;

	public ConfigControl(boolean isTestArea, String contextPath) {
		this.isTestArea = isTestArea;
		this.contextPath = contextPath;
	}

	public DBInfoBean DBConfigLoader(String filename) {
		String path = null;
		XMLInputFactory inputFactory = null;
		InputStream in = null;
		XMLEventReader eventReader = null;
		XMLEvent event = null;
		StartElement startElement = null;
		DBInfoBean config = null;

		try {
			path = getConfigXMLPath(filename);
			if (path == null || path.length() == 0) {
				throw new Exception("Error Get SYS DataBase Config Path Fail;");
			} else {
				File f = new File(path);
				if (!f.exists()) {
					throw new Exception("Error SYS DataBase Config Not Found;");
				}
			}
			inputFactory = XMLInputFactory.newInstance();
			in = new FileInputStream(path);
			eventReader = inputFactory.createXMLEventReader(in);

			while (eventReader.hasNext()) {
				event = eventReader.nextEvent();

				if (event.isStartElement()) {
					startElement = event.asStartElement();
					// System.out.println(startElement.getName().getLocalPart());
					if (startElement.getName().getLocalPart().equals("DCISYS")) {
						config = new DBInfoBean();
						config.setConnID("SYSDB");
						continue;
					}

					if (event.asStartElement().getName().getLocalPart().equals("DBAddr")) {
						event = eventReader.nextEvent();
						config.setDBAddr(event.asCharacters().getData());
						continue;
					}

					if (event.asStartElement().getName().getLocalPart().equals("DBPort")) {
						event = eventReader.nextEvent();
						config.setDBPort(event.asCharacters().getData());
						continue;
					}

					if (event.asStartElement().getName().getLocalPart().equals("DBName")) {
						event = eventReader.nextEvent();
						config.setDBName(event.asCharacters().getData());
						continue;
					}

					if (event.asStartElement().getName().getLocalPart().equals("UserName")) {
						event = eventReader.nextEvent();
						config.setUserName(event.asCharacters().getData());
						continue;
					}

					if (event.asStartElement().getName().getLocalPart().equals("Password")) {
						event = eventReader.nextEvent();
						config.setPassword(DCIString.Base64Decode(event.asCharacters().getData()));
						// System.out.println(event.asCharacters().getData());
						continue;
					}

					if (event.asStartElement().getName().getLocalPart().equals("DBType")) {
						event = eventReader.nextEvent();
						config.setDBType(DBType.valueOf(event.asCharacters().getData()));
						continue;
					}

					if (event.asStartElement().getName().getLocalPart().equals("LangType")) {
						event = eventReader.nextEvent();
						config.setLangType(LangType.valueOf(event.asCharacters().getData()));
						continue;
					}

					if (event.asStartElement().getName().getLocalPart().equals("GuardIP")) {
						event = eventReader.nextEvent();
						if (event.isEndElement()) {
							config.setGuardIP("");
						} else {
							config.setGuardIP(event.asCharacters().getData());
						}
						continue;
					}

					if (event.asStartElement().getName().getLocalPart().equals("GuardPort")) {
						event = eventReader.nextEvent();
						int port = 6666;
						if (!DCIString.isNullOrEmpty(event.asCharacters().getData())) {
							if (DCIString.isInteger(event.asCharacters().getData())) {
								port = Integer.parseInt(event.asCharacters().getData());
							}
						}
						config.setGuardPort(port);
						continue;
					}

					if (event.asStartElement().getName().getLocalPart().equals("maxActive")) {
						event = eventReader.nextEvent();
						config.setMaxActive(Integer.parseInt(event.asCharacters().getData()));
						continue;
					}

					if (event.asStartElement().getName().getLocalPart().equals("maxWait")) {
						event = eventReader.nextEvent();
						config.setMaxWait(Long.parseLong(event.asCharacters().getData()));
						continue;
					}

					if (event.asStartElement().getName().getLocalPart().equals("maxIdle")) {
						event = eventReader.nextEvent();
						config.setMaxIdle(Integer.parseInt(event.asCharacters().getData()));
						continue;
					}

					if (event.asStartElement().getName().getLocalPart().equals("minIdle")) {
						event = eventReader.nextEvent();
						config.setMinIdle(Integer.parseInt(event.asCharacters().getData()));
						continue;
					}

					if (event.asStartElement().getName().getLocalPart().equals("RemoveAbandoned")) {
						event = eventReader.nextEvent();
						config.setRemoveAbandoned(Boolean.valueOf(event.asCharacters().getData()));
						continue;
					}

					if (event.asStartElement().getName().getLocalPart().equals("RemoveAbandonedTimeout")) {
						event = eventReader.nextEvent();
						config.setRemoveAbandonedTimeout(Integer.parseInt(event.asCharacters().getData()));
						continue;
					}

					if (event.asStartElement().getName().getLocalPart().equals("OfficialUrl")) {
						event = eventReader.nextEvent();
						if (event.isEndElement()) {
							config.setOfficialUrl("");
						} else {
							config.setOfficialUrl(event.asCharacters().getData());
						}
						continue;
					}
				}

				// if (event.isEndElement()) {
				// EndElement endElement = event.asEndElement();
				// if (endElement.getName().getLocalPart().equals("SFTSYS")) {
				// configs = new Object[2];
				// configs[0] = config;
				// configs[1] = pool;
				// }
				// }

			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return config;
	}

	public String getConfigXMLPath(String filename) {
		String path = null;
		try {
			path = getConfigXMLPath() + File.separator + filename;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return path;
	}

	public String getConfigXMLPath() {
		String path = null;
		try {
			if (this.isTestArea) {
				path = this.contextPath;
			} else {
				if (System.getProperty("os.name").toLowerCase().indexOf("windows") == -1) {
					String userhome = System.getProperty("user.home");
					path = userhome.substring(0, userhome.lastIndexOf(File.separator)) + File.separator
							+ "DBAA1DEE-7B51-48d1-AC7F-E16963E3465F";
				} else {
					path = readRegistry("HKLM\\SOFTWARE\\Microsoft\\Windows NT\\CurrentVersion\\ProfileList",
							"ProfilesDirectory") + File.separator + "DBAA1DEE-7B51-48d1-AC7F-E16963E3465F";
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return path;
	}

	private String readRegistry(String location, String key) {
		String result = null;
		Process process = null;
		try {
			process = Runtime.getRuntime().exec("reg query " + '"' + location + "\" /v " + key);

			StreamReader reader = new StreamReader(process.getInputStream());
			reader.start();
			process.waitFor();
			reader.join();

			String[] parsed = reader.getResult().split("\\s+");

			if (parsed != null) {
				for (int i = 0; i < parsed.length; i++) {
					if (parsed[i].toLowerCase().indexOf("%systemdrive%") != -1
							|| parsed[i].toLowerCase().indexOf(
									System.getenv("SystemDrive").toLowerCase() + File.separator) != -1) {
						result = parsed[i];
					} else {
						if (result != null) {
							result += " " + parsed[i];
						}
					}

					// if (parsed[i].toLowerCase().indexOf("%systemdrive%") ==
					// -1) {
					// if (result != null) {
					// result += " " + parsed[i];
					// }
					// } else {
					// result = parsed[i];
					// }
				}
			}

			if (result == null) {
				result = System.getenv("SystemDrive") + File.separator;
			}

			// if (parsed.length > 1) {
			// result = parsed[parsed.length - 1];
			// }
			// System.out.println(result);
			result = result.replace("%SystemDrive%", System.getenv("SystemDrive"));
		} catch (Exception e) {
			result = System.getenv("SystemDrive") + File.separator;
		}

		return result;
	}

	class StreamReader extends Thread {
		private InputStream is;
		private StringWriter sw = new StringWriter();

		public StreamReader(InputStream is) {
			this.is = is;
		}

		public void run() {
			try {
				int c;
				while ((c = is.read()) != -1)
					sw.write(c);
			} catch (Exception e) {
			}
		}

		public String getResult() {
			return sw.toString();
		}
	}
}
