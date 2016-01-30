package com.dci.jweb.DCITags.Request;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class getReqAttribut extends SimpleTagSupport {
	private String attrName;

	@Override
	public void doTag() throws JspException {
		Object tmp = null;
		PageContext pageContext = null;
		JspWriter out = null;
		try {
			pageContext = (PageContext) getJspContext();
			out = pageContext.getOut();
			tmp = pageContext.getRequest().getAttribute(this.attrName);
			if (tmp == null) {
				tmp = "";
			}
			out.write(tmp.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getAttrName() {
		return attrName;
	}

	public void setAttrName(String attrName) {
		this.attrName = attrName;
	}
}
