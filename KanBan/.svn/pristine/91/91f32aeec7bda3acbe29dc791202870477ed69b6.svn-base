package com.dsc.dci.jweb.pub;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import com.dci.jweb.DCIConstants.DCIWebConstants;
import com.dci.jweb.DCIEnums.Server.UserCountType;
import com.dci.jweb.PublicLib.DCIString;
import com.dci.jweb.PublicLib.Register.RegisterObject;

public class UserSyncSend {
	public void addUser(String uid, String ip, boolean is_lic) {
		UserCountType uctp = null;

		if (is_lic) {
			uctp = UserCountType.License;
		} else {
			uctp = UserCountType.OnLine;
		}

		sendPost("action=addUser&counttp=" + uctp.toString() + "&uid=" + uid + "&ip=" + ip);
	}

	public void removeUser(String uid, String ip, boolean is_lic) {
		UserCountType uctp = null;

		if (is_lic) {
			uctp = UserCountType.License;
		} else {
			uctp = UserCountType.OnLine;
		}

		sendPost("action=rmUser&counttp=" + uctp.toString() + "&uid=" + uid + "&ip=" + ip);
	}

	public int getUserNum(boolean is_lic) {
		UserCountType uctp = null;
		int result = 0;
		String key = "userNum";
		String response = null;

		if (is_lic) {
			uctp = UserCountType.License;
		} else {
			uctp = UserCountType.OnLine;
		}

		response = sendPost("action=getNum&counttp=" + uctp.toString());

		if (DCIString.isNullOrEmpty(response)) {
			result = 0;
		} else {
			HashMap<String, String> map = DCIString.jsonTranToStrMap(response);
			if (map != null && map.size() > 0) {
				if (map.containsKey(key)) {
					if (DCIString.isInteger(map.get(key))) {
						result = Integer.parseInt(map.get(key));
					} else {
						result = 0;
					}
				} else {
					result = 0;
				}
			} else {
				result = 0;
			}
		}
		return result;
	}

	public HashMap<String, String> getUserList(boolean is_lic) {
		UserCountType uctp = null;
		HashMap<String, String> result = null;

		String response = null;

		if (is_lic) {
			uctp = UserCountType.License;
		} else {
			uctp = UserCountType.OnLine;
		}

		response = sendPost("action=getMap&counttp=" + uctp.toString());

		try {
			result = DCIString.jsonTranToStrMap(response);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (result == null) {
				result = new HashMap<String, String>();
			} else {
				if (result.containsKey(APConstants.getErrkey())) {
					result.remove(APConstants.getErrkey());
				} else if (result.containsKey(APConstants.getReskey())) {
					result.remove(APConstants.getReskey());
				}
			}
		}

		return result;
	}

	public boolean getUserExist(boolean is_lic, String uid, String ip) {
		UserCountType uctp = null;
		boolean exist = false;
		HashMap<String, String> result = null;
		String response = null;

		if (is_lic) {
			uctp = UserCountType.License;
		} else {
			uctp = UserCountType.OnLine;
		}

		response = sendPost("action=userExist&counttp=" + uctp.toString() + "&uid=" + uid + "&ip=" + ip);

		try {
			result = DCIString.jsonTranToStrMap(response);
			if (result != null && result.size() > 0) {
				if (result.containsKey("uexist")) {
					exist = Boolean.parseBoolean(result.get("uexist"));
				} else {
					exist = false;
				}
			} else {
				exist = false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			exist = false;
		}

		return exist;
	}

	public boolean getLicStatus() {
		boolean status = false;
		HashMap<String, String> result = null;
		String response = null;

		response = sendPost("action=licStatus");

		try {
			result = DCIString.jsonTranToStrMap(response);
			if (result != null && result.size() > 0) {
				if (result.containsKey("licstatus")) {
					status = Boolean.parseBoolean(result.get("licstatus"));
				} else {
					status = false;
				}
			} else {
				status = false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			status = false;
		}

		return status;
	}

	public int getMaxUser() {
		int limit = 0;
		HashMap<String, String> result = null;
		String response = null;

		response = sendPost("action=MUNum");

		try {
			result = DCIString.jsonTranToStrMap(response);
			if (result != null && result.size() > 0) {
				if (result.containsKey("maxUser")) {
					limit = Integer.parseInt(result.get("maxUser"));
				} else {
					limit = 0;
				}
			} else {
				limit = 0;
			}

		} catch (Exception e) {
			e.printStackTrace();
			limit = 0;
		}

		return limit;
	}

	public void setROInfos() {
		HashMap<String, String> result = null;
		Object tmp = null;
		ArrayList<HashMap<String, String>> as = null;
		ArrayList<HashMap<String, String>> si = null;
		String response = null;

		response = sendPost("action=getROInfos");

		try {
			tmp = DCIString.jsonTranToObjMap(response,
					new HashMap<String, ArrayList<HashMap<String, String>>>().getClass());
			if (tmp != null) {
				result = (HashMap<String, String>) tmp;
				if (result.containsKey("as") && result.containsKey("si")) {
					as = (ArrayList<HashMap<String, String>>) DCIString.jsonTranToObjMap(result.get("as"),
							new ArrayList<HashMap<String, String>>().getClass());
					si = (ArrayList<HashMap<String, String>>) DCIString.jsonTranToObjMap(result.get("si"),
							new ArrayList<HashMap<String, String>>().getClass());
					RegisterObject.getInstance().setTestAreaInfos(as, si);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setTestAreaInfos(ArrayList<HashMap<String, String>> as, ArrayList<HashMap<String, String>> si) {
		ArrayList<HashMap<String, String>> serialInfos;
		ArrayList<HashMap<String, String>> allSerials;
		serialInfos = new ArrayList<HashMap<String, String>>();
		allSerials = new ArrayList<HashMap<String, String>>();
		if (as != null) {
			for (int i = 0; i < as.size(); i++) {
				allSerials.add(as.get(i));
			}
		}

		if (si != null) {
			for (int i = 0; i < si.size(); i++) {
				serialInfos.add(si.get(i));
			}
		}
	}

	private String sendPost(String urlParams) {
		String urlstr = Singleton.getInstance().getOfficialUrl() + "/UserSyncRecv.dsc";// "http://10.40.71.84:9090/KanBan/UserSyncRecv.dsc";
		// sendPost2(DCIWebConstants.getPostTagId() + "=" +
		// DCIWebConstants.getPostTagValue() + "&" + urlParams);
		// urlParams = "action=";
		String result = null;
		URL url = null;
		HttpURLConnection conn = null;
		DataOutputStream dos = null;
		int responseCode = 0;
		BufferedReader br = null;
		String inputLine = null;
		StringBuffer response = null;

		try {
			if (DCIString.isNullOrEmpty(urlstr)) {
				result = "";
			} else {
				url = new URL(urlstr);
				conn = (HttpURLConnection) url.openConnection();
				conn.setConnectTimeout(30000);
				conn.setDoOutput(true);
				conn.setRequestMethod("POST");
				conn.setRequestProperty("User-Agent",
						"Mozilla/5.0 (Windows NT 6.1; rv:14.0) Gecko/20100101 Firefox/14.0.1");
				conn.setRequestProperty("Accept-Charset", "utf-8");
				conn.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

				if (urlParams.toLowerCase().indexOf(DCIWebConstants.getPostTagId()) == -1) {
					urlParams = DCIWebConstants.getPostTagId() + "=" + DCIWebConstants.getPostTagValue() + "&"
							+ urlParams;
				}

				dos = new DataOutputStream(conn.getOutputStream());
				dos.writeBytes(urlParams);
				dos.flush();
				dos.close();
				// System.out.println(conn.getResponseCode());

				responseCode = conn.getResponseCode();

				if (responseCode == HttpURLConnection.HTTP_OK) {
					br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
					response = new StringBuffer();

					while ((inputLine = br.readLine()) != null) {
						response.append(inputLine);
					}

					result = response.toString();
				} else {
					result = "";
				}
			}
		} catch (Exception ex) {
			result = "";
			ex.printStackTrace();
		} finally {
			if (dos != null) {
				try {
					dos.close();
				} catch (IOException e) {
				}
			}

			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
				}
			}
			if (conn != null) {
				conn.disconnect();
			}
		}

		return result;
	}
}
