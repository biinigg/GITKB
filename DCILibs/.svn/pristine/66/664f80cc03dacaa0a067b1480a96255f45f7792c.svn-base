package com.dci.jweb.PublicLib;

import java.util.HashMap;

import org.codehaus.jackson.map.ObjectMapper;

public class DCIHashMap {
	public static String hashToJson(HashMap<String, String> hash) {
		String result = null;

		ObjectMapper objectMapper = new ObjectMapper();
		try {
			if (hash == null || hash.size() == 0) {
				result = "";
			} else {
				result = objectMapper.writeValueAsString(hash);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = "";
		}

		return result;
	}

	public static HashMap<String, String> buildResponseData(String msg, String responseText) {
		HashMap<String, String> result = new HashMap<String, String>();
		result.put("msg", msg);
		result.put("result", responseText);
		return result;
	}
}
