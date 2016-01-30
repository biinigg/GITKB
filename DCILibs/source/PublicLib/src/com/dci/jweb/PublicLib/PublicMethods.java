package com.dci.jweb.PublicLib;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.URLDecoder;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dci.jweb.DCIConstants.DCIWebConstants;

//import sun.misc.BASE64Decoder;
//import sun.misc.BASE64Encoder;

public class PublicMethods {
	/*
	 * public Boolean initCheck() { Boolean ok = false; ok = true; return ok; }
	 */

	/*
	 * public Boolean CheckLicense() { return true; // return false; }
	 */

	/*
	 * public String Base64Encode(String str) { String code = null; try { if
	 * (str != null && !str.equals("")) { code = new
	 * BASE64Encoder().encode(str.getBytes()); } } catch (Exception ex) { code =
	 * null; ex.printStackTrace(); } return code; }
	 */

	/*
	 * public String Base64Decode(String code) { String str = null; try { if
	 * (code != null && !code.equals("")) { BASE64Decoder decoder = new
	 * BASE64Decoder(); byte[] strByte = decoder.decodeBuffer(code); str = new
	 * String(strByte); } } catch (Exception ex) { str = null;
	 * ex.printStackTrace(); } return str; }
	 */

	/*
	 * public boolean isNullOrEmpty(String str) { boolean isnull = false; isnull
	 * = str == null & str.equals(""); return isnull; }
	 * 
	 * public String strFix(String str, int length, boolean fixLeft, String
	 * fixChar) { String tmp = null;
	 * 
	 * tmp = str;
	 * 
	 * for (int i = 0; i < length - str.length(); i++) { if (fixLeft) { tmp =
	 * fixChar + tmp; } else { tmp = tmp + fixChar; } }
	 * 
	 * return tmp; }
	 */

	public void downloadFile(HttpServletRequest request, HttpServletResponse response, String datas,
			String action, String name) {
		InputStream in = null;
		ServletOutputStream out = null;
		try {
			if (DCIString.isNullOrEmpty(datas)) {
				datas = "";
			} /*
			 * else { datas = URLDecoder.decode(datas, "UTF-8"); }
			 */
			// System.out.println(datas);
			if (request.getProtocol().equals("HTTP/1.0")) {
				response.setHeader("Pragma", "no-cache");
			} else if (request.getProtocol().equals("HTTP/1.1")) {
				response.setHeader("Cache-Control", "no-cache");
			}
			response.setDateHeader("Expires", 0);
			response.setCharacterEncoding("UTF-8");
			if (action.equals("excel")) {
				response.setContentType("application/vnd.ms-excel");
				response.setHeader("Content-Disposition", "attachment;filename=" + name + ".xls");
			} else if (action.equals("html")) {
				response.setContentType("application/octet-stream");
				response.setHeader("Content-Disposition", "attachment;filename=" + name + ".html");
			}
			response.resetBuffer();
			out = response.getOutputStream();

			in = new ByteArrayInputStream(datas.getBytes("UTF-8"));

			byte[] outputByte = new byte[2048];

			while (in.read(outputByte, 0, 2048) != -1) {
				out.write(outputByte, 0, 2048);
				outputByte = new byte[2048];
			}
			out.flush();
			response.flushBuffer();
		} catch (Exception ex) {

		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void downloadFile(HttpServletRequest request, HttpServletResponse response, byte[] datas,
			String action, String name) {
		InputStream in = null;
		ServletOutputStream out = null;
		try {
			if (datas == null) {
				datas = new byte[] {};
			} /*
			 * else { datas = URLDecoder.decode(datas, "UTF-8"); }
			 */
			// System.out.println(datas);
			if (request.getProtocol().equals("HTTP/1.0")) {
				response.setHeader("Pragma", "no-cache");
			} else if (request.getProtocol().equals("HTTP/1.1")) {
				response.setHeader("Cache-Control", "no-cache");
			}
			response.setDateHeader("Expires", 0);
			response.setCharacterEncoding("UTF-8");
			if (action.equals("excel")) {
				response.setContentType("application/vnd.ms-excel");
				response.setHeader("Content-Disposition", "attachment;filename=" + name);
			} else if (action.equals("html")) {
				response.setContentType("application/octet-stream");
				response.setHeader("Content-Disposition", "attachment;filename=" + name + ".html");
			}
			response.resetBuffer();
			out = response.getOutputStream();

			in = new ByteArrayInputStream(datas);

			byte[] outputByte = new byte[2048];

			while (in.read(outputByte, 0, 2048) != -1) {
				out.write(outputByte, 0, 2048);
				outputByte = new byte[2048];
			}
			out.flush();
			response.flushBuffer();
		} catch (Exception ex) {

		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public String getRealHardwareKey() {
		String Machine_str = getMachineSerialByHDD();
		String Machine_strII = "";
		Machine_strII = Machine_str + Machine_str + Machine_str;
		Machine_strII = new String(DCIString.Base64Encode(String.valueOf(Machine_strII)));
		Machine_strII = Machine_strII.toUpperCase();
		return Machine_strII;
	}

	private String getMachineSerialByHDD() {
		String result = "";
		try {
			result = getWMIValue("Select * from Win32_LogicalDisk where DeviceID='C:'", "VolumeSerialNumber");
		} catch (Exception e) {
			e.printStackTrace();
		}
		// System.out.println(result);
		return result;
	}

	private String getWMIValue(String wmiQueryStr, String wmiCommaSeparatedFieldName) {
		String result = "";
		try {
			File file = File.createTempFile("realhowto", ".vbs");
			file.deleteOnExit();
			String vbs = "";
			FileWriter fw = new java.io.FileWriter(file);
			vbs = "Set objWMIService = GetObject(\"winmgmts:\\\\.\\root\\cimv2\")\n"
					+ "Set colItems = objWMIService.ExecQuery _ \n" + "   (\"" + wmiQueryStr + "\") \n"
					+ "For Each objItem in colItems \n" + "    Wscript.Echo objItem."
					+ wmiCommaSeparatedFieldName + " \n" + "Next \n";

			fw.write(vbs);
			fw.close();
			Process p = Runtime.getRuntime().exec("cscript //NoLogo " + file.getPath());
			BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line;
			while ((line = input.readLine()) != null) {
				result += line;
			}
			input.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result.trim();
	}

	public boolean getVMData() {
		boolean checkResult = false;
		String Win32_ComputerSystem_model = "";
		String Win32_ComputerSystem_Manufacturer = "";
		String Win32_Processor_Name = "";
		try {
			Win32_ComputerSystem_model = getWMIValue("Select Model from Win32_ComputerSystem", "Model");
			Win32_ComputerSystem_Manufacturer = getWMIValue("Select Manufacturer from Win32_ComputerSystem",
					"Manufacturer");
			Win32_Processor_Name = getWMIValue("Select Name from Win32_Processor", "Name");
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (Win32_ComputerSystem_model.toLowerCase().contains("virtual")
				|| (Win32_ComputerSystem_Manufacturer.toUpperCase().contains("XEN") && Win32_Processor_Name
						.toUpperCase().contains("XEN"))) {
			checkResult = true;
		}
		return checkResult;
	}

	public String getHardwareKey(String guardip, int port) {
		String code = "";
		Socket socket = null;
		DataOutputStream out = null;
		InputStreamReader in = null;
		boolean isvm = false;

		try {
			isvm = getVMData();/* 是否為vm */// ekb強制認證gaurd service
			if (isvm) {
				socket = new Socket(guardip, port);
				socket.setSoTimeout(180000);
				out = new DataOutputStream(socket.getOutputStream());
				// 設定key值 ip+SFT+今天日期
				String key = getkey(isvm);
				out.writeUTF(key);
				String codetemp = "";
				// Socket回傳
				in = new InputStreamReader(socket.getInputStream());
				while (true) {
					int ch = in.read();
					codetemp += (char) ch;
					if (in.ready() == false)
						break;
				}
				in.close();
				socket.close();

				String dd = DCIString.Base64Decode(codetemp);

				code = XorFunction(key.split(";")[1], dd);// 傳出32碼
			} else {
				code = getRealHardwareKey().toUpperCase();
			}
			if (code != null && code.length() < 30) {
				code = String.format("%-" + 30 + "s", code).replace(' ', '0');
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			code = "";
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception ex) {

				}
			}
			if (socket != null && !socket.isClosed()) {
				try {
					socket.close();
				} catch (Exception ex) {

				}
			}
		}
		return code;
	}

	public String getkey(boolean isvm) {
		// 取得ip位址
		String key = "";
		String day = "";
		String recordIP = null;

		try {
			String ip = getServerIPAddr();
			if (isvm) {
				recordIP = readRegIP();
				if (!recordIP.equals("nofile")) {
					ip = recordIP;
				}
			}
			// 設定key值 ip+SFT+今天日期
			key = ip + ";" + "SFT" + day;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return key;
	}

	private String XorFunction(String pkkey, String FData) {
		String returnd = "";
		char[] a = pkkey.toCharArray();
		char[] b = FData.toCharArray();
		for (int i = 0; i < b.length; i++) {
			for (int j = 0; j < a.length; j++) {
				b[i] = (char) (b[i] ^ a[j]);
			}
			returnd += (char) b[i];
		}
		return returnd;
	}

	public String getServerIPAddr() {
		InetAddress netaddr = null;
		String ip = null;
		try {
			netaddr = InetAddress.getLocalHost();
			if (netaddr != null && netaddr.toString().split("/").length >= 2) {
				ip = netaddr.toString().split("/")[1];
			} else {
				ip = "";
			}
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ip;
	}

	public String readRegIP() {
		String filename = new ConfigControl(false, null).getConfigXMLPath() + File.separator + "KanBan.hwk";
		String result = null;
		File file = null;
		String[] ip = null;
		ObjectInputStream in = null;

		try {
			file = new File(filename);
			if (file.exists()) {
				in = new ObjectInputStream(new FileInputStream(filename));
				ip = (String[]) in.readObject();
				in.close();

				if (ip != null) {
					for (int i = 0; i < ip.length; i++) {
						if (i == 0) {
							result = DCIString.Base64Decode(ip[i]);
						} else {
							result += "." + DCIString.Base64Decode(ip[i]);
						}
					}
				}
			} else {
				result = "nofile";
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public synchronized void writeRegIP(String code) {
		String filename = new ConfigControl(false, null).getConfigXMLPath() + File.separator + "KanBan.hwk";
		FileOutputStream fos = null;
		ObjectOutputStream out = null;
		String[] ip = null;
		try {
			ip = code.split("\\.");
			for (int i = 0; i < ip.length; i++) {
				ip[i] = DCIString.Base64Encode(ip[i]);
			}
			fos = new FileOutputStream(filename);
			out = new ObjectOutputStream(fos);
			out.writeObject(ip);
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if (fos != null) {
					fos.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public ArrayList<String> getAllIP() {
		ArrayList<String> iplist = null;
		Enumeration<NetworkInterface> nets = null;
		Enumeration<InetAddress> inetAddresses = null;
		String tmp = null;

		try {
			nets = NetworkInterface.getNetworkInterfaces();
			iplist = new ArrayList<String>(0);
			for (NetworkInterface netint : Collections.list(nets)) {
				inetAddresses = netint.getInetAddresses();
				for (InetAddress inetAddress : Collections.list(inetAddresses)) {
					tmp = inetAddress.toString();
					tmp = tmp.substring(tmp.indexOf("/") + 1);
					if (tmp.split("\\.").length == 4) {
						if (!tmp.equals("127.0.0.1") && tmp.indexOf("127.0.0.1") == -1) {
							iplist.add(tmp);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return iplist;
	}

	public static String getUidFromKey(String key) {
		String uid = null;
		if (DCIString.isNullOrEmpty(key)) {
			uid = "";
		} else {
			try {
				String value = DCIString.strDecode(key);

				if (DCIString.isNullOrEmpty(value)) {
					uid = "";
				} else {
					String[] values = value.split("\\^");
					if (values == null || values.length != 2) {
						uid = "";
					} else {
						uid = values[0];
					}
				}
			} catch (Exception ex) {
				uid = "";
			}
		}
		return uid;
	}

	public void saveTxtFile(String path, String str) {
		File f = null;
		FileWriter fw = null;
		int cnt = 0;

		try {
			f = new File(path);
			while (f.exists()) {
				cnt++;
				f.delete();
				Thread.sleep(5);
				if (cnt == 3) {
					throw new Exception("Delte file fail when save data");
				}
			}

			fw = new FileWriter(f);
			fw.write(str);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (fw != null) {
				try {
					fw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	String[] ms = null;
	int max = 3;

	public void createPackageEnum() {
		this.ms = DCIWebConstants.getModuleids();
		for (int i = 0; i < this.max; i++) {
			System.out.print(this.ms[i] + ",");
			packageLoop(i + 1, this.ms[i]);
		}
	}

	private void packageLoop(int level, String tmp) {
		String ntmp = tmp;
		for (int i = level; i < this.max; i++) {
			if (ntmp == null) {
				ntmp = this.ms[i];
			} else {
				ntmp += "_" + this.ms[i];
			}
			System.out.print(ntmp + ",");
			if (i < this.max - 1) {
				packageLoop(i + 1, ntmp);
			}
			ntmp = tmp;
		}
	}
}
