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

public class getExtLangFilePath extends SimpleTagSupport {
	private String langDirPath;
	private String useType;

	public String getLangDirPath() {
		return langDirPath;
	}

	public void setLangDirPath(String langDirPath) {
		this.langDirPath = langDirPath;
	}

	public String getUseType() {
		return useType;
	}

	public void setUseType(String useType) {
		this.useType = useType;
	}

	@Override
	public void doTag() throws JspException {
		String langtp = null;
		PageContext pageContext = null;
		JspWriter out = null;
		String filename = null;
		String uid = null;

		try {
			pageContext = (PageContext) getJspContext();
			out = pageContext.getOut();

			if (this.useType.equals("0")) {
				uid = "";
			} else if (this.useType.equals("1")) {
				uid = PublicMethods.getUidFromKey(pageContext.getRequest().getParameter("key"));
			} else if (this.useType.equals("2")) {
				uid = pageContext.getRequest().getAttribute("uid") == null ? "" : pageContext.getRequest()
						.getAttribute("uid").toString();
			} else {
				uid = "";
			}

			langtp = new APControl().getUserInfoFromSession(pageContext.getSession(), uid, "lang");
			if (this.useType.equals("0") && !DCIString.isNullOrEmpty(pageContext.getRequest().getParameter("ltp"))) {
				langtp = pageContext.getRequest().getParameter("ltp");
			}
			if (langtp == null || langtp.equals("")) {
				langtp = Singleton.getInstance().getDeflang();
			}

			if (langtp.equals("CHS")) {
				filename = "ext-lang-zh_CN.js";
			} else {
				filename = "ext-lang-zh_TW.js";
			}

			filename = this.langDirPath + "/" + filename;
			out.write(filename);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
