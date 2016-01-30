package com.dci.jweb.PublicLib;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogControl {
	private DCIParameters params;
	private SimpleDateFormat nameFormat;
	private SimpleDateFormat logFormat;

	public LogControl() {
		this.params = DCIParameters.getInstance();
		this.nameFormat = new SimpleDateFormat("yyyyMMdd");
		this.logFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	}

	public void writeLog(Exception ex) {
		writeLog(this.logFormat.format(new Date()) + "  " + ex.getMessage() + "\r\n" + ex.getStackTrace()
				+ "\r\n");
	}

	public void writeLog(String value) {
		FileWriter out = null;
		BufferedWriter writer = null;

		try {
			File f = new File(getLogName());

			if (!f.exists()) {
				f.createNewFile();
			}

			out = new FileWriter(f);
			writer = new BufferedWriter(out);
			writer.write(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()) + "  " + value
					+ "\r\n");
		} catch (Exception ex2) {
			ex2.printStackTrace();
		} finally {
			try {
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private String getLogName() {
		String logName = null;
		Date today = new Date();
		logName = this.params.getLogPath() + File.pathSeparator + "DCILog" + this.nameFormat.format(today)
				+ ".log";

		return logName;
	}
}
