package com.dci.jweb.PublicLib;

public class DCIParameters {
	private static DCIParameters _parames;
	private String logPath;

	public DCIParameters() {

	}

	public static DCIParameters getInstance() {
		synchronized (DCIParameters.class) {
			if (_parames == null) {
				_parames = new DCIParameters();
			}
		}
		return _parames;
	}

	public void setParameters(String logPath) {
		this.logPath = logPath;

	}

	public String getLogPath() {
		return this.logPath;
	}

}
