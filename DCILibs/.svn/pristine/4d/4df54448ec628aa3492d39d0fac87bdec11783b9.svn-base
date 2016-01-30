package com.dci.jweb.PublicLib.Register;

import java.util.HashMap;

import com.dci.jweb.DCIConstants.DCIWebConstants;
import com.dci.jweb.PublicLib.DCIDate;
import com.dci.jweb.PublicLib.DCIString;

public class SerialAnalysis {
	private String[] sresilt;
	Integer[] radixs = { 30, 31, 32, 33, 34, 35, 36 };
	String[] radixsText = DCIString.string2Array("QSCWDVA");

	public SerialAnalysis() {

	}

	public boolean setSerial(String serialNum) {
		boolean ok = false;
		ok = verifySerialCode(serialNum);
		if (ok) {
			if (serialNum != null && serialNum.length() == 30) {
				sresilt = DCIString.string2Array(serialNum);
			} else {
				sresilt = new String[30];
			}
		}

		return ok;
	}

	public HashMap<String, String> analysisSerial() {
		HashMap<String, String> sd = new HashMap<String, String>();
		sd.put("expired_date", String.valueOf(cryptoString2Date()));
		sd.put("user_qty", String.valueOf(cryptoString2UserQty()));
		sd.put("area", cryptoString2Area());
		sd.put("expired_date", String.valueOf(cryptoString2Date()));
		if (sd.get("area").equals("0123456789")) {
			sd.put("display_expired_date", DCIDate.parseShowDate(String.valueOf(cryptoString2Date()), "/"));
		} else {
			sd.put("display_expired_date", String.valueOf("99999999"));
		}
		sd.put("serialnum", cryptoStringSerialNum());
		sd.put("module_id", cryptoString2ModuleId());

		return sd;
	}

	// /驗證序號是否正確
	private boolean verifySerialCode(String deStr) {
		boolean ok = true;
		sresilt = DCIString.string2Array(deStr);
		String code = getVerifyId();
		if (!code.equals(sresilt[sresilt.length - 1])) {
			// throw new Exception("SFTI27_00033");// SFTI27_00033 序號錯誤
			ok = false;
		}

		HashMap<String, String> sd = analysisSerial();
		if (sd.get("area").trim().equals("0123456789") && (Long.parseLong(sd.get("expired_date")) > 20981231)) {
			// throw new Exception("SFTI27_00034");// SFTI27_00034 借貨序號使用期限錯誤
			ok = false;
		}
		if (!verifyModuleID(sd.get("module_id"))) {

			// throw new Exception("SFTI27_00035");// SFTI27_00035 此序號不是EKB的序號
			ok = false;
		}
		return ok;
	}

	private boolean verifyModuleID(String id) {
		boolean ok = false;

		if (!DCIString.isNullOrEmpty(id)) {
			for (int i = 0; i < id.length(); i++) {
				if (id.substring(i, i + 1).equals("1")) {
					ok = true;
					break;
				}
			}
		}

		return ok;
	}

	private String getResultValue(int[] point) {
		String res = "";
		for (int i = 0; i < point.length; i++) {
			res += sresilt[point[i]];
		}
		return res;
	}

	private String getVerifyId() {
		int iSum10 = 0;
		for (int i = 0; i < sresilt.length - 1; i++) {
			int iValue10 = DCIString.parseString2Num(sresilt[i], 36);

			if (i == 1 || i == 10 || i == 23) {
				iSum10 -= iValue10;
			} else {
				iSum10 += iValue10;
			}
		}

		String sSum36 = DCIString.parseNum2String(iSum10, 36);
		String[] sSum36s = DCIString.string2Array(sSum36);
		return sSum36s[sSum36s.length - 1];
	}

	// {0,6,12,16,20,27}
	private int cryptoString2Date() {
		int[] point = { 0, 6, 12, 16, 20 };
		String radixText36 = getResultValue(point);
		return DCIString.parseString2Num(radixText36, searchRadixsText());
	}

	// {3,21}
	public int cryptoString2UserQty() {
		int[] point = { 3, 21, 22 };
		String radixText36 = getResultValue(point);
		return DCIString.parseString2Num(radixText36, 36);
	}

	// 區別碼,放至第18位
	private String cryptoString2Area() {
		return getAreaValue(sresilt[17]);
	}

	// {7,8,10,11}
	private String cryptoStringSerialNum() {
		int[] point = { 7, 8, 10, 11 };
		String radixText36 = getResultValue(point);
		int tmp = DCIString.parseString2Num(radixText36, 36);
		return String.format("%07d", tmp);
	}

	private String cryptoString2ModuleId() {
		int[] point = { 1, 2, 5, 9, 18, 25, 26 };
		String radixText36 = getResultValue(point);
		long iNum36to10_ID = DCIString.parseString2Long(radixText36, 36);

		int[] point2 = { 16, 17, 21, 27 };
		String radixText362 = getResultValue(point2);
		long iNum36to10 = DCIString.parseString2Long(radixText362, 36);

		long itmp = iNum36to10_ID - iNum36to10;

		String tmpModuleString = DCIString.parseLong2String(itmp, 2);

		tmpModuleString = DCIString.strFix(
				tmpModuleString.substring(0, tmpModuleString.length() + DCIWebConstants.getModulelength()
						- 35), DCIWebConstants.getModulelength(), true, "0");

		// switch (tmpModuleString.length()) {
		// case 33:
		// tmpModuleString = "001"; // LKB
		// break;
		// case 34:
		// // tmpModuleString = "010"; // WPP
		// tmpModuleString = "0" + tmpModuleString.substring(0, 2);
		// break;
		// case 35:
		// tmpModuleString = tmpModuleString.substring(0, 3);
		//
		// // tmpModuleString = "100"; // EKB
		// break;
		// default:
		// tmpModuleString = "000";
		// }
		// tmpModuleString = ("0000000000" + "0000000000" + "0000000000" +
		// "000000").substring(0,
		// 36 - tmpModuleString.length()) + tmpModuleString;
		return tmpModuleString;
	}

	private enum Area {
		NUMBER("0123456789"), CHARACTER("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
		private Area(String value) {
			this.value = value;
		}

		private String value;

		public String toString() {
			return value;
		}
	}

	private String getAreaValue(String key) {
		for (Area c : Area.values()) {
			String[] tmp = DCIString.string2Array(c.toString());
			if (ArraysSearch(tmp, key) > -1)
				return c.toString();
		}
		return "";
	}

	private static int ArraysSearch(String[] strs, String key) {
		for (int i = 0; i < strs.length; i++) {
			if (key.equals(strs[i]))
				return i;
		}
		return -1;
	}

	private int searchRadixsText() {
		String key = this.sresilt[27];
		return radixs[ArraysSearch(radixsText, key)];
	}
}
