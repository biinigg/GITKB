package com.dsc.dci.jweb.apenums;

import java.util.ArrayList;
import java.util.HashMap;

import com.dci.jweb.PublicLib.DCIString;
import com.dsc.dci.jweb.pub.Singleton;

public enum ColumnType {
	CHAR, NUM, DATE, LIGHT, MAPPING, MONEY, PROGRESS, CHECKBOX, IMAGELINK, IMAGEMAPPING;

	public static ArrayList<HashMap<String, String>> getComboData(String lang) {
		ColumnType[] values = ColumnType.values();

		ArrayList<HashMap<String, String>> items = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> tmp = null;
		for (int i = 0; i < values.length; i++) {
			tmp = new HashMap<String, String>();
			tmp.put("value", values[i].toString());
			if (DCIString.isNullOrEmpty(lang)) {
				tmp.put("label", values[i].toString());
			} else {
				tmp.put("label", Singleton.getInstance().getLanguage(lang, values[i].toString()));
			}
			items.add(tmp);
		}

		return items;
	}

	public static String[] StringValues() {
		String[] values = new String[ColumnType.values().length];
		ColumnType[] enumvalues = ColumnType.values();
		for (int i = 0; i < values.length; i++) {
			values[i] = enumvalues[i].toString();
		}

		return values;
	}
}
