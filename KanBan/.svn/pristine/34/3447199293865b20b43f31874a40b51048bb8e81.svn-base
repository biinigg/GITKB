package com.dsc.dci.jweb.patchs;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import com.dci.jweb.DCIEnums.Database.ConnectionType;
import com.dci.jweb.PublicLib.ConfigControl;
import com.dci.jweb.PublicLib.DCIString;
import com.dci.jweb.PublicLib.PublicMethods;
import com.dsc.dci.jweb.pub.APPubMethods;
import com.dsc.dci.jweb.pub.Singleton;

public class EKBVersionCheck {
	private final String[] vers = new String[] { "002001000001", "002001000002", "002001000003", "002001000004",
			"002001000005", "002001000006", "002001001000", "002001001001", "002001001002", "002001001003" };
	private ArrayList<String> versions;
	private Singleton s;

	private void setVersions() {
		this.s = Singleton.getInstance();
		this.versions = new ArrayList<String>();
		for (int i = 0; i < this.vers.length; i++) {
			this.versions.add(this.vers[i]);
		}
	}

	public EKBVersionCheck() {
		setVersions();
		checkCurrVersion();
	}

	public ArrayList<String> getVersionList() {
		ArrayList<String> ver = null;
		if (this.vers != null) {
			for (int i = 0; i < this.vers.length; i++) {
				if (ver == null) {
					ver = new ArrayList<String>(0);
				}

				ver.add(DCIString.transToDisplayVerFormat(this.vers[i]));
			}
		}
		return ver;
	}

	private void checkCurrVersion() {
		String patchVer = null;
		String lastVer = null;
		APPubMethods ap = new APPubMethods();
		boolean patchExec = false;

		patchVer = ap
				.readVersion(s.getContextPath() + File.separator + "System_Files" + File.separator + "Version.dci");
		lastVer = ap.readVersion(new ConfigControl(s.isTestArea(), s.getTestAreaConfigPath())
				.getConfigXMLPath("version.dci"));

		if (Long.parseLong(patchVer) > Long.parseLong(lastVer)) {
			for (int i = 0; i < this.versions.size(); i++) {
				if (Long.parseLong(this.versions.get(i)) > Long.parseLong(lastVer)
						&& Long.parseLong(this.versions.get(i)) <= Long.parseLong(patchVer)) {
					try {
						// System.out.println(EKBVersionCheck.class.getPackage().getName()
						// + ".EKBP"
						// + this.versions.get(i));
						Class.forName(EKBVersionCheck.class.getPackage().getName() + ".EKBP" + this.versions.get(i))
								.getConstructor(Boolean.TYPE).newInstance(true);
					} catch (Exception e) {
						e.printStackTrace();
						// break;
					}
					patchExec = true;
				}
			}
		}

		updCurrPatch(patchVer);
		lastVer = ap.readVersion(new ConfigControl(s.isTestArea(), s.getTestAreaConfigPath())
				.getConfigXMLPath("version.dci"));
		Singleton.getInstance().setCurrVer(DCIString.transToDisplayVerFormat(lastVer));

		patchExec = executeCusPatch() || patchExec;

		if (patchExec) {
			APPubMethods pm = new APPubMethods();
			pm.addLanguages(Singleton.getInstance().getContextPath());
			// pm.loadMultiLanguage();
		}
	}

	private boolean executeCusPatch() {
		boolean exec = false;
		JarInputStream jStream = null;
		JarEntry jEntry = null;
		File jarFile = null;
		String cusPatchJarPath = null;
		String cName = null;
		String patchClass = null;
		Class<?> c = null;

		cusPatchJarPath = Singleton.getInstance().getContextPath() + "/WEB-INF/lib/dcicuspatch.jar";
		jarFile = new File(cusPatchJarPath);

		if (jarFile.exists()) {
			try {
				jStream = new JarInputStream(new FileInputStream(cusPatchJarPath));
				while (true) {
					jEntry = jStream.getNextJarEntry();
					if (jEntry == null) {
						break;
					}
					if ((jEntry.getName().endsWith(".class"))) {
						cName = jEntry.getName().replaceAll("/", "\\.");
						patchClass = cName.substring(0, cName.lastIndexOf('.'));

						try {
							c = Class.forName(patchClass);
							if (!c.getName().substring(c.getName().lastIndexOf('.') + 1).startsWith("sql")) {
								c.getConstructor(Boolean.TYPE).newInstance(true);
								if (!exec) {
									exec = true;
								}
							}
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
				}

			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				if (jStream != null) {
					try {
						jStream.close();
					} catch (IOException e) {
					}
				}
			}
		}
		return exec;
	}

	private void updCurrPatch(String patchVer) {
		try {
			if (!DCIString.isNullOrEmpty(patchVer) && patchVer.length() == 12) {
				new PublicMethods().saveTxtFile(
						new ConfigControl(s.isTestArea(), s.getTestAreaConfigPath()).getConfigXMLPath("version.dci"),
						DCIString.strEncode(DCIString.Base64Encode(DCIString.transToDisplayVerFormat(patchVer))));
				// new
				// APPubMethods().saveDBVersion(DCIString.strEncode(DCIString.Base64Encode(patchVer)));
				new APPubMethods().saveDBVersion(DCIString.transToDisplayVerFormat(patchVer));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/*
	 * private String transVerFormat(String ver) { String result = null; String
	 * tmp = null; if (!DCIString.isNullOrEmpty(ver) && ver.length() == 12) {
	 * for (int i = 0; i < 4; i++) { tmp = ver.substring(i * 3, ((i + 1) * 3));
	 * if (i == 0) { result = String.valueOf(Integer.parseInt(tmp)); } else {
	 * result += "." + String.valueOf(Integer.parseInt(tmp)); } } }
	 * 
	 * if (result == null) { result = ver; } return result; }
	 */
}
