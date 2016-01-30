package com.dci.jweb.PublicLib.Register;

import java.util.ArrayList;
import java.util.HashMap;

import com.dci.jweb.DCIEnums.Server.PackageType;
import com.dci.jweb.PublicLib.DCIDate;
import com.dci.jweb.PublicLib.DCIString;
import com.dci.jweb.PublicLib.PublicMethods;
import com.dci.jweb.PublicLib.Register.RegisterCenter;
import com.dci.jweb.PublicLib.Register.SerialAnalysis;

public class RegisterObject {
	private static RegisterObject instance = null;
	private ArrayList<HashMap<String, String>> serialInfos;
	private ArrayList<HashMap<String, String>> allSerials;
	private String guardIP;
	private int guardPort;

	public RegisterObject() {
		this.serialInfos = new ArrayList<HashMap<String, String>>();
		this.allSerials = new ArrayList<HashMap<String, String>>();
	}

	public static RegisterObject getInstance() {
		if (instance == null) {
			synchronized (RegisterObject.class) {
				if (instance == null) {
					instance = new RegisterObject();
				}
			}
		}
		return instance;
	}

	public ArrayList<HashMap<String, String>> getAllSerials() {
		return this.allSerials;
	}

	public ArrayList<HashMap<String, String>> getSerialInfo() {
		return this.serialInfos;
	}

	public void setTestAreaInfos(ArrayList<HashMap<String, String>> as, ArrayList<HashMap<String, String>> si) {
		if (as != null) {
			this.allSerials.clear();
			for (int i = 0; i < as.size(); i++) {
				this.allSerials.add(as.get(i));
			}
		}

		if (si != null) {
			this.serialInfos.clear();
			for (int i = 0; i < si.size(); i++) {
				this.serialInfos.add(si.get(i));
			}
		}
	}

	public void setActiveSerial(ArrayList<HashMap<String, String>> value) {
		ArrayList<HashMap<String, String>> datas = null;
		HashMap<String, String> tmp = null;
		HashMap<String, String> tmpSerial = null;
		HashMap<String, String> gridDatas = null;
		RegisterCenter rc = new RegisterCenter();
		SerialAnalysis sa = new SerialAnalysis();
		String currdate = DCIDate.getToday("yyyyMMdd");
		String mkey = new PublicMethods().getHardwareKey(this.guardIP, this.guardPort);
		String failDate = null;
		this.allSerials = new ArrayList<HashMap<String, String>>();
		datas = new ArrayList<HashMap<String, String>>();
		if (value != null && value.size() > 0) {
			for (int i = 0; i < value.size(); i++) {
				tmp = value.get(i);
				if (sa.setSerial(tmp.get("serial_number"))) {
					tmpSerial = sa.analysisSerial();
					gridDatas = new HashMap<String, String>();
					for (String key : tmpSerial.keySet()) {
						gridDatas.put(key, tmpSerial.get(key));
					}
					gridDatas.put("module_name", PackageType.packageOf(tmpSerial.get("module_id")).toString()
							.replace("_", ";"));// 尚未授權
					gridDatas.put("isReg", "1");// 尚未授權
					gridDatas.put("isExpired", "false");
					gridDatas.put("regFail", "");
					gridDatas.put("serial", tmp.get("serial_number"));

					gridDatas.put(
							"display_serial",
							tmp.get("serial_number").substring(0, 3)
									+ "************************"
									+ tmp.get("serial_number").substring(
											tmp.get("serial_number").length() - 3));

					tmpSerial.put("regFail", "");

					if (DCIString.isNullOrEmpty(tmp.get("execute_code"))) {
						gridDatas.put("isReg", "1"); // 尚未授權
						if (tmpSerial.get("area").trim().equals("0123456789")) {
							if ((Long.parseLong(tmpSerial.get("expired_date")) > 20981231)
									|| (Long.parseLong(tmpSerial.get("expired_date")) < Long
											.parseLong(currdate))) {
								gridDatas.put("isExpired", "true");
							}
						} else {
							if (!DCIString.isNullOrEmpty(tmp.get("register_info"))) {
								failDate = DCIString.strDecode(tmp.get("register_info"));
								if (DCIString.isNumber(failDate)) {
									gridDatas.put("regFail", failDate);
									if (Long.parseLong(failDate) < Long.parseLong(DCIDate.getTodayAdd(-6,
											"yyyyMMdd"))) {
										this.allSerials.add(gridDatas);
										continue;
									}
								} else {
									this.allSerials.add(gridDatas);
									continue;
								}
							}

							datas.add(tmpSerial);
						}
					} else {
						if (tmpSerial.get("area").trim().equals("0123456789")) {
							if ((Long.parseLong(tmpSerial.get("expired_date")) > 20981231)
									|| (Long.parseLong(tmpSerial.get("expired_date")) < Long
											.parseLong(currdate))) {
								gridDatas.put("isExpired", "true");

								rc.setExecuteCode(tmp.get("serial_number"), mkey);
								if (rc.verifyExecuteCode(tmp.get("execute_code"))) {
									gridDatas.put("isReg", "3");// 授權成功
								} else {
									gridDatas.put("isReg", "2");// 授權失敗
								}
								this.allSerials.add(gridDatas);
								continue;
							}
						} else {
							if (!DCIString.isNullOrEmpty(tmp.get("register_info"))) {
								failDate = DCIString.strDecode(tmp.get("register_info"));
								gridDatas.put("isReg", "2");// 授權失敗
								if (DCIString.isNumber(failDate)) {
									gridDatas.put("regFail", failDate);
									if (Long.parseLong(failDate) < Long.parseLong(DCIDate.getTodayAdd(-6,
											"yyyyMMdd"))) {
										this.allSerials.add(gridDatas);
										continue;
									}
								} else {
									this.allSerials.add(gridDatas);
									continue;
								}
							}
						}
						rc.setExecuteCode(tmp.get("serial_number"), mkey);
						if (rc.verifyExecuteCode(tmp.get("execute_code"))) {
							datas.add(tmpSerial);
							gridDatas.put("isReg", "3");// 授權成功
						} else {
							gridDatas.put("isReg", "2");// 授權失敗
						}
					}

					this.allSerials.add(gridDatas);
				}

			}
		}

		for (int i = 0; i < this.allSerials.size(); i++) {
			gridDatas = this.allSerials.get(i);

			if (gridDatas.get("isReg").equals("3")) {
				if (gridDatas.get("area").equals("0123456789")) {
					if (gridDatas.get("isExpired").equals("true")) {
						gridDatas.put("status", "B"); // 過期
					} else {
						gridDatas.put("status", "A"); // 授權成功
					}
				} else {
					gridDatas.put("status", "A"); // 授權成功
				}
			} else {
				if (gridDatas.get("area").equals("0123456789")) {
					if (gridDatas.get("isExpired").equals("true")) {
						gridDatas.put("status", "B"); // 過期
					} else {
						if (gridDatas.get("isReg").equals("1")) {
							gridDatas.put("status", "C"); // 尚未授權
						} else {
							gridDatas.put("status", "D"); // 授權失敗
						}
					}
				} else {
					if (gridDatas.get("isReg").equals("1")) {
						gridDatas.put("status", "C"); // 尚未授權
					} else {
						gridDatas.put("status", "D"); // 授權失敗
					}
				}
			}
		}

		this.serialInfos = datas;
	}

	public boolean getLicenseStatus() {
		return this.serialInfos != null && this.serialInfos.size() > 0;
	}

	public int getMaxUser() {
		HashMap<String, String> tmp = null;
		int cnt = 0;
		for (int i = 0; i < this.serialInfos.size(); i++) {
			tmp = this.serialInfos.get(i);
			if (!DCIString.isNullOrEmpty(tmp.get("user_qty")) && DCIString.isNumber(tmp.get("user_qty"))) {
				cnt += Integer.parseInt(tmp.get("user_qty"));
			}
		}

		return cnt;
	}

	public HashMap<String, String> getPacksges() {
		HashMap<String, String> packages = null;
		HashMap<String, String> tmp = null;
		String func_package = null;

		for (int i = 0; i < this.serialInfos.size(); i++) {
			tmp = this.serialInfos.get(i);
			if (packages == null) {
				packages = new HashMap<String, String>();
			}
			func_package = PackageType.packageOf(tmp.get("module_id")).toString();
			if (packages.containsKey(func_package)) {
				packages.put(
						func_package,
						String.valueOf(Integer.parseInt(packages.get(func_package))
								+ Integer.parseInt(tmp.get("user_qty"))));
			} else {
				packages.put(func_package, tmp.get("user_qty"));
			}
		}

		return packages;
	}

	public boolean isPackageCanUse(String package_id) {
		boolean canuse = false;
		HashMap<String, String> tmp = null;
		String[] packages = null;
		if (!DCIString.isNullOrEmpty(package_id)) {
			if (package_id.equals("KBC")) {
				package_id = "EKB";
			}
			for (int i = 0; i < this.serialInfos.size(); i++) {
				tmp = this.serialInfos.get(i);
				packages = PackageType.packageOf(tmp.get("module_id")).toString().split("_");
				for (int j = 0; j < packages.length; j++) {
					if (package_id.equals(packages[j])) {
						canuse = true;
						break;
					}
				}
			}
		}
		return canuse;
	}

	public void setGuardInfo(String guardip, int guardport) {
		this.guardIP = guardip;
		this.guardPort = guardport;
	}

	public String getGuardIP() {
		return this.guardIP;
	}

	public int getGuardPort() {
		return this.guardPort;
	}
}
