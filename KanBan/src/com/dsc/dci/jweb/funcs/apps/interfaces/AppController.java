package com.dsc.dci.jweb.funcs.apps.interfaces;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.dci.jweb.DCIConstants.DCIWebConstants;
import com.dci.jweb.PublicLib.DCIString;

@Controller
@RequestMapping("/AppReceiver.dsc")
public class AppController {
	@RequestMapping(method = RequestMethod.POST)
	public void postMethod(HttpServletRequest request, HttpServletResponse response) {
		String result = null;
		// String dcikey = request.getParameter(DCIWebConstants.getPostTagId());
		String action = null;
		HashMap<String, String> datas = null;
		AppFunctions funcs = null;

		funcs = new AppFunctions();
		datas = funcs.getXMLDatas(request);
		// System.out.println(jb.toString());
		// System.out.println("posted3");
		// datas = jb.toString();
		if (datas == null || datas.size() == 0) {
			// xml格式或內容錯誤
			result = funcs.getEmptyError(null, "E", "XML error");
		} else {
			if (datas.containsKey("DoAction")) {
				action = datas.get("DoAction");
				if (DCIString.isNullOrEmpty(action)) {
					// 請求項目異常
					result = funcs.getEmptyError(datas, "E", "action error");
				} else {
					if (action.equals("GetCompList")) {
						result = funcs.getConnList(datas);
					} else if (action.equals("GetReportList")) {
						result = funcs.getKanbanList(datas);
					} else if (action.equals("GetReportHead")) {
						// funcs.getXMLObjDatas();
						result = funcs.getKanBanData(funcs.getXMLObjDatas(), request, false);
					} else if (action.equals("GetReportDetail")) {
						// funcs.getXMLObjDatas();
						result = funcs.getKanBanData(funcs.getXMLObjDatas(), request, true);
					} else {
						result = funcs.getEmptyError(datas, "E", "action error");
					}
				}
			} else {
				// 請求項目異常
				result = funcs.getEmptyError(datas, "E", "action error");
			}
		}
		sendResponse(response, result);
	}

	private void sendResponse(HttpServletResponse response, String result) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			out.write(result.getBytes());
			response.setContentType("text/xml");
			response.setContentLength(out.size());
			response.getOutputStream().write(out.toByteArray());
			response.getOutputStream().flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
			}
		}
	}
}
