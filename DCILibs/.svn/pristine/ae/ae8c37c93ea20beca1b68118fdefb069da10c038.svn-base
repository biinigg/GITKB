package com.dci.jweb.DCITags.Request;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class getErrorCode extends SimpleTagSupport {
	private boolean showCode;

	@Override
	public void doTag() throws JspException {
		String tmp = null;
		PageContext pageContext = null;
		JspWriter out = null;
		try {
			pageContext = (PageContext) getJspContext();
			out = pageContext.getOut();
			tmp = pageContext.getRequest().getParameter("errcode");
			if (this.showCode) {
				out.write(tmp);
			} else {
				out.write(codeConvert(tmp));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String codeConvert(String code) {
		String msg = "";
		int intcode = 0;

		if (code == null || code.isEmpty()) {
			msg = "系統異常";
		} else {
			if (code.indexOf("dcie") == -1) {
				msg = "無法識別的錯誤碼 : " + code;
			} else {
				try {
					intcode = Integer.parseInt(code.toLowerCase().replace("dcie", ""));

					switch (intcode) {
					case 1:
						msg = "作業逾時或非法的進入網址";
						break;
					case 2:
						msg = "頁面提交驗證碼驗證失敗";
						break;
					case 3:
						msg = "導頁驗證碼驗證失敗";
						break;
					case 4:
						msg = "系統連接驗證碼為空值";
						break;
					case 5:
						msg = "系統連接驗證碼解析失敗";
						break;
					case 6:
						msg = "系統代碼錯誤";
						break;
					case 7:
						msg = "系統連接驗證碼過期";
						break;
					case 8:
						msg = "使用者代號為空值";
						break;
					case 9:
						msg = "系統啟動失敗，無法進入";
						break;
					case 10:
						msg = "資料庫連線異常";
						break;
					case 99:
						msg = "無法識別的錯誤碼 : " + code;
						break;
					default:
						msg = "無法識別的錯誤碼 : " + code;
						break;
					}

				} catch (Exception ex) {
					msg = "無法識別的錯誤碼 : " + code;
				}
			}
		}

		return msg;
	}

	public boolean getShowCode() {
		return showCode;
	}

	public void setShowCode(boolean showCode) {
		this.showCode = showCode;
	}
}
