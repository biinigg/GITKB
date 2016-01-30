package com.dci.jweb.DCITags.Request;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class getReqParameter extends SimpleTagSupport {
	private String paramName;

	@Override
	public void doTag() throws JspException {
		String tmp = null;
		PageContext pageContext = null;
		JspWriter out = null;
		try {
			pageContext = (PageContext) getJspContext();
			out = pageContext.getOut();
			tmp = pageContext.getRequest().getParameter(this.paramName);
			if (tmp == null) {
				tmp = "";
			}
			out.write(tmp);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}
}
