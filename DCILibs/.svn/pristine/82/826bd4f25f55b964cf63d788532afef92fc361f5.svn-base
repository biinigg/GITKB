package com.dci.jweb.PublicLib.Register;

import com.dci.jweb.PublicLib.DCIString;

public class RegisterCenter {

	private int[] p1 = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
	private int[] p2 = { 10, 11, 12, 13, 14, 15, 16, 17, 18, 19 };
	private int[] p3 = { 20, 21, 22, 23, 24, 25, 26, 27, 28, 29 };
	String[] serial;
	String[] hardware;

	public RegisterCenter() {

	}

	public void setExecuteCode(String serial, String hardware) {
		this.serial = DCIString.string2Array(serial);
		this.hardware = DCIString.string2Array(hardware.toUpperCase());
	}

	public boolean verifyExecuteCode(String verifyCode) {
		boolean ok = false;
		// String msg = "";
		String execCode = getExecuteCode();
		if (execCode.equals(verifyCode)) {
			// dao.update(serial, verifyCode, "");
			// msg = "SFTI27_00032";// SFTI27_00032 儲存完成
			ok = true;
		} else {
			// msg = "SFTI27_00013";// SFTI27_00013=執行碼錯誤
			ok = false;
		}
		return ok;
	}

	private String getExecuteCode() {
		String result = "";
		if (this.serial == null || this.hardware == null) {
			result = null;
		} else {
			String res = getResultValue(p1, p2);

			res += getResultValue(p2, p3);

			res += getResultValue(p3, p1);

			result = DCIString.Base64Encode(res);

		}
		return result;
	}

	// 第1~10與機碼的11~20每一字元獨立對應進行36進位加總，不考慮進位
	// 第11~20與機碼的21~30每一字元獨立對應進行36進位加總，不考慮進位
	// 第21~10與機碼的1~10每一字元獨立對應進行36進位加總，不考慮進位
	private String getResultValue(int[] p1, int[] p2) {
		String res = "";
		for (int i = 0; i < p1.length; i++) {
			String serial_byte = serial[p1[i]];
			String hardware_byte = hardware[p2[i]];

			int p1_value_10 = DCIString.parseString2Num(serial_byte, 36);
			int p2_value_10 = DCIString.parseString2Num(hardware_byte, 36);

			int iSum10 = p1_value_10 + p2_value_10;

			String sSum36 = DCIString.parseNum2String(iSum10, 36);

			String[] sSum36s = DCIString.string2Array(sSum36);

			res += sSum36s[sSum36s.length - 1];
		}

		return res.substring(1, 9);
	}

}
