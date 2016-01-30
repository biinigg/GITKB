package com.dci.jweb.DCIConstants;

public class DCIWebConstants {
	private static final String serverDefPort = "7100";
	private static final String userInfoTag = "userInfo$$";
	private static final String postTagId = "DCITag";
	private static final String postTagValue = "dci_ekb";
	private static final String forwardTagId = "forwardTag";
	private static final String forwardTagValue = "targetDci";
	private static final String sessionForwardTagId = "forwardTag$^";
	private static final String KeyToken1 = "#";
	private static final String KeyToken2 = "@";
	private static final String EKBInitVersion = "2.1.0.000";
	private static final int KeyLength = 3;
	private static final String[] ModuleIDS = new String[] { "EKB", "WPP", "LKB", "YFSL001" };

	// private static final String configFileName = "SFTDBConfig.xml";

	public static String getUserInfoTag() {
		return userInfoTag;
	}

	public static String getPostTagId() {
		return postTagId;
	}

	public static String getPostTagValue() {
		return postTagValue;
	}

	public static String getForwardTagId() {
		return forwardTagId;
	}

	public static String getForwardTagValue() {
		return forwardTagValue;
	}

	public static String getSessionForwardTagId() {
		return sessionForwardTagId;
	}

	public static String getKeytoken1() {
		return KeyToken1;
	}

	public static String getKeytoken2() {
		return KeyToken2;
	}

	public static int getKeylength() {
		return KeyLength;
	}

	public static String getEkbinitversion() {
		return EKBInitVersion;
	}

	public static int getModulelength() {
		return ModuleIDS.length;
	}

	// public static String getConfigfilename() {
	// return configFileName;
	// }

	public static String[] getModuleids() {
		return ModuleIDS;
	}

	public static String getServerdefport() {
		return serverDefPort;
	}

}
