package com.dci.jweb.PublicLib;

import java.util.ArrayList;
import java.util.Vector;

public class SQLInjectionMethods {
	private String[] codes;

	public SQLInjectionMethods() {

		this.codes = new String[] { "--", "'", "=" };
	}

	public boolean checkCode(String[] value) {
		boolean ok = true;
		for (int i = 0; i < value.length; i++) {
			ok = checkCode(value[i].toString());
			if (ok) {
				break;
			}
		}
		return ok;
	}

	public boolean checkCode(Object[] value) {
		boolean ok = true;
		for (int i = 0; i < value.length; i++) {
			ok = checkCode(value[i].toString());
			if (ok) {
				break;
			}
		}
		return ok;
	}

	public boolean checkCode(Vector<Object> value) {
		boolean ok = true;
		for (int i = 0; i < value.size(); i++) {
			ok = checkCode(value.get(i).toString());
			if (ok) {
				break;
			}
		}
		return ok;
	}

	public boolean checkCode(ArrayList<Object> value) {
		boolean ok = true;
		for (int i = 0; i < value.size(); i++) {
			ok = checkCode(value.get(i).toString());
			if (ok) {
				break;
			}
		}
		return ok;
	}

	public boolean checkCode(String value) {
		boolean ok = true;

		for (int j = 0; j < this.codes.length; j++) {
			if (value.indexOf(this.codes[j]) != -1) {
				ok = false;
				break;
			}
		}
		return ok;
	}
}
