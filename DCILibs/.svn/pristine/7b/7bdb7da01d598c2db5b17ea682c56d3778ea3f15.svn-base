package com.dci.jweb.PublicLib;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class DCIAPPHttpServletRequest extends HttpServletRequestWrapper {
	private final HashMap<String, String> appHeaders;

	public DCIAPPHttpServletRequest(HttpServletRequest request) {
		super(request);
		this.appHeaders = new HashMap<String, String>();
	}

	public void putHeader(String name, String value) {
		this.appHeaders.put(name, value);
	}

	public String getHeader(String name) {
		String headerValue = appHeaders.get(name);

		if (headerValue != null) {
			return headerValue;
		}

		return ((HttpServletRequest) getRequest()).getHeader(name);
	}

	public Enumeration<String> getHeaderNames() {
		Set<String> set = new HashSet<String>(appHeaders.keySet());

		Enumeration<String> e = ((HttpServletRequest) getRequest()).getHeaderNames();
		while (e.hasMoreElements()) {
			String n = e.nextElement();
			set.add(n);
		}

		return Collections.enumeration(set);
	}
}
