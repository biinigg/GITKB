package com.dsc.dci.jweb.pub;

import java.io.File;

public class APConstants {
	private static final String configFileName = "KanBanConfig.xml";
	private static final String DCIPatternKey = "DCIBlockPattern";
	private static final String DefLanguage = "CHT";
	private static final String LangKeySplit = ",";
	private static final String loginPageCheckCode = "743EA852-9849-45c0-A286-341A4C0D3098";
	private static final String UserKeyTag = "#";
	private static final String IconDir = "EKB" + File.separator + "Icons";
	private static final String UploadIconDir = IconDir + File.separator + "Upload";
	private static final String SysIconDir = IconDir + File.separator + "System";
	private static final int defaultPageSize = 20;
	private static final String resKey = "success";
	private static final String errKey = "err";

	public static String getConfigfilename() {
		return configFileName;
	}

	public static String getDcipatternkey() {
		return DCIPatternKey;
	}

	public static String getDeflanguage() {
		return DefLanguage;
	}

	public static String getLangkeysplit() {
		return LangKeySplit;
	}

	public static String getLoginpagecheckcode() {
		return loginPageCheckCode;
	}

	public static String getUserkeytag() {
		return UserKeyTag;
	}

	/*
	 * public static String getIcondir() { return IconDir; }
	 */

	public static int getDefaultpagesize() {
		return defaultPageSize;
	}

	public static String getUploadicondir() {
		return UploadIconDir;
	}

	public static String getSysicondir() {
		return SysIconDir;
	}

	public static String getReskey() {
		return resKey;
	}

	public static String getErrkey() {
		return errKey;
	}

}
