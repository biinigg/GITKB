package com.dci.jweb.DCIEnums.Server;

import com.dci.jweb.DCIConstants.DCIWebConstants;

public enum PackageType {
	EKB, EKB_WPP, EKB_WPP_LKB, EKB_WPP_LKB_YFSL001, EKB_WPP_YFSL001, EKB_LKB, EKB_LKB_YFSL001, EKB_YFSL001, WPP, WPP_LKB, WPP_LKB_YFSL001, WPP_YFSL001, LKB, LKB_YFSL001, YFSL001, NONE;

	public static PackageType packageOf_ori(String value) {
		if (value.equals("100")) {
			return PackageType.EKB;
		} else if (value.equals("111")) {
			return PackageType.EKB_WPP_LKB;
		} else if (value.equals("101")) {
			return PackageType.EKB_LKB;
		} else if (value.equals("110")) {
			return PackageType.EKB_WPP;
		} else if (value.equals("010")) {
			return PackageType.WPP;
		} else if (value.equals("011")) {
			return PackageType.WPP_LKB;
		} else if (value.equals("001")) {
			return PackageType.LKB;
		} else {
			return PackageType.NONE;
		}
	}

	public static PackageType packageOf(String value) {
		PackageType tp = PackageType.NONE;
		String packageName = "";

		for (int i = 0; i < value.length(); i++) {
			if (value.substring(i, i + 1).equals("1")) {
				if (packageName.length() == 0) {
					packageName = DCIWebConstants.getModuleids()[i];
				} else {
					packageName += "_" + DCIWebConstants.getModuleids()[i];
				}
			}
		}

		if (packageName.length() == 0) {
			tp = PackageType.NONE;
		} else {
			tp = PackageType.valueOf(packageName);
		}

		return tp;
	}
}
