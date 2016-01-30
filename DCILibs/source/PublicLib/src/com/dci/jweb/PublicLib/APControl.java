package com.dci.jweb.PublicLib;

import java.util.Enumeration;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.dci.jweb.DCIConstants.DCIWebConstants;

public class APControl {
	public void clearSession(HttpSession session) {
		if (session != null) {
			Enumeration<String> sessions = session.getAttributeNames();

			while (sessions.hasMoreElements()) {
				session.removeAttribute(sessions.nextElement());
			}
		}
	}

	public void clearSession(HttpSession session, String uid) {
		if (session != null) {
			session.removeAttribute(DCIWebConstants.getUserInfoTag() + uid);
			session.removeAttribute(DCIWebConstants.getSessionForwardTagId() + uid);
		}
	}

	public boolean sessionCheck(HttpSession session) {
		boolean ok = false;
		if (session == null) {
			ok = false;
		} else if (session.getAttribute(DCIWebConstants.getUserInfoTag()) == null) {
			ok = false;
		} else {
			ok = true;
		}
		return ok;
	}

	public boolean sessionCheck(HttpSession session, String uid) {
		boolean ok = false;
		if (session == null) {
			ok = false;
		} else if (session.getAttribute(DCIWebConstants.getUserInfoTag() + uid) == null) {
			ok = false;
		} else {
			ok = true;
		}
		return ok;
	}

	public String getUserInfoFromSession(HttpSession session, String key) {
		String value = null;
		try {
			if (session == null) {
				value = null;
			} else {
				Object obj = session.getAttribute(DCIWebConstants.getUserInfoTag());

				if (obj == null) {
					value = null;
				} else {
					HashMap<String, String> userInfo = (HashMap<String, String>) obj;
					value = userInfo.get(key);
				}
			}
		} catch (Exception ex) {
			value = null;
			ex.printStackTrace();
		}
		return value;
	}

	public String getUserInfoFromSession(HttpSession session, String uid, String key) {
		String value = null;

		try {
			if (session == null) {
				value = null;
			} else {
				Object obj = session.getAttribute(DCIWebConstants.getUserInfoTag() + uid);

				if (obj == null) {
					value = null;
				} else {
					HashMap<String, String> userInfo = (HashMap<String, String>) obj;
					value = userInfo.get(key);
				}
			}
		} catch (Exception ex) {
			value = null;
			ex.printStackTrace();
		}
		return value;
	}

	public boolean getRequestIdentify(HttpServletRequest request) {
		boolean checkOK = false;
		try {
			if (request == null) {
				checkOK = false;
			} else {
				Object obj = request.getAttribute(DCIWebConstants.getPostTagId());
				if (obj == null) {
					checkOK = false;
				} else {
					if (obj.toString().equals(DCIWebConstants.getPostTagValue())) {
						checkOK = true;
					} else {
						checkOK = false;
					}
				}
			}
		} catch (Exception ex) {
			checkOK = false;
			ex.printStackTrace();
		}
		return checkOK;
	}

	public void setResponseIdentify(HttpServletRequest request) {
		request.setAttribute(DCIWebConstants.getForwardTagId(), DCIWebConstants.getForwardTagValue());
	}

	public String getRequestIP(HttpServletRequest request) {
		String ipAddress = request.getHeader("X-FORWARDED-FOR");
		if (ipAddress == null) {
			ipAddress = request.getRemoteAddr();
		}
		return ipAddress;
	}
}
