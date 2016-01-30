package com.dci.jweb.DCITags.Request;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class getReqHeader extends SimpleTagSupport {
	private String headerName;

	@Override
	public void doTag() throws JspException {
		String tmp = null;
		PageContext pageContext = null;
		JspWriter out = null;
		try {
			pageContext = (PageContext) getJspContext();
			out = pageContext.getOut();
			tmp = ((HttpServletRequest) pageContext.getRequest()).getHeader(this.headerName);
			if (tmp == null) {
				tmp = "";
			}
			out.write(tmp);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getHeaderName() {
		return headerName;
	}

	public void setHeaderName(String headerName) {
		this.headerName = headerName;
	}
}