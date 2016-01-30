package com.dsc.dci.jweb.aptags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import com.dci.jweb.PublicLib.APControl;
import com.dci.jweb.PublicLib.DCIString;
import com.dci.jweb.PublicLib.PublicMethods;
import com.dsc.dci.jweb.pub.Singleton;

public class getMultiLanguage extends SimpleTagSupport {
	private String langKey;
	private String requestTag;

	@Override
	public void doTag() throws JspException {
		Singleton s = Singleton.getInstance();
		String tmp = null;
		String langtp = null;
		PageContext pageContext = null;
		JspWriter out = null;
		try {
			pageContext = (PageContext) getJspContext();
			out = pageContext.getOut();

			if (!DCIString.isNullOrEmpty(pageContext.getRequest().getParameter("ltp"))) {
				langtp = pageContext.getRequest().getParameter("ltp");
			} else if (!DCIString.isNullOrEmpty(pageContext.getRequest().getParameter("key"))) {
				langtp = new APControl().getUserInfoFromSession(pageContext.getSession(),
						PublicMethods.getUidFromKey(pageContext.getRequest().getParameter("key")), "lang");
			} else {
				langtp = s.getDeflang();
			}

			// langtp = new
			// APControl().getUserInfoFromSession(pageContext.getSession(),
			// "lang");
			if (DCIString.isNullOrEmpty(langtp)) {
				langtp = s.getDeflang();
			}

			if (this.requestTag != null) {
				this.langKey = pageContext.getRequest().getParameter(this.requestTag);
			}

			if (DCIString.isNullOrEmpty(this.langKey)) {
				tmp = "";
			} else {
				tmp = s.getLanguage(langtp, this.langKey);
				if (tmp == null || tmp.equals("")) {
					tmp = this.langKey;
				}
			}
			out.write(tmp);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getLangKey() {
		return langKey;
	}

	public void setLangKey(String langKey) {
		this.langKey = langKey;
	}

	public String getRequestTag() {
		return requestTag;
	}

	public void setRequestTag(String requestTag) {
		this.requestTag = requestTag;
	}

}
