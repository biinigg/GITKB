package com.dci.jweb.DCITags.Request;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class getExtLangJSFile extends SimpleTagSupport {
	private String currPath;

	@Override
	public void doTag() throws JspException {
		String tmp = null;
		String path = null;
		PageContext pageContext = null;
		JspWriter out = null;
		try {
			pageContext = (PageContext) getJspContext();
			out = pageContext.getOut();
			tmp = pageContext.getRequest().getParameter("");
			out.write(tmp);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getCurrPath() {
		return currPath;
	}

	public void setCurrPath(String currPath) {
		this.currPath = currPath;
	}

}
