package com.dsc.dci.jweb.funcs.wpp.wpp007;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dci.jweb.DCIConstants.DCIWebConstants;
import com.dci.jweb.PublicLib.APControl;
import com.dci.jweb.PublicLib.PublicMethods;
import com.dsc.dci.jweb.pub.Singleton;

@Controller
@RequestMapping("/Funcs/WPP/WPP007.dsc")
public class WPP007Controller {
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody
	HashMap<String, ArrayList<Object>> postMethod(HttpServletRequest request, HttpServletResponse response) {
		HashMap<String, ArrayList<Object>> parameters = null;
		String dcikey = request.getParameter(DCIWebConstants.getPostTagId());
		String action = request.getParameter("action");
		String uid = request.getParameter("uid");
		HashMap<String, String> parames = null;
		String compID = request.getParameter("conn_id");

		if (dcikey != null && dcikey.equals(DCIWebConstants.getPostTagValue())) {
			if (action.equals("init")) {
				parameters = new WPP007(compID).getComboBoxData();
			} else if (action.equals("query")) {
				parames = new HashMap<String, String>();
				parames.put("F001", request.getParameter("F001"));
				parames.put("F002", request.getParameter("F002"));
				parameters = new WPP007(compID).query(parames);
			} else if (action.equals("sub")) {
				String subtype = request.getParameter("SubType");
				parames = new HashMap<String, String>();
				parames.put("TN008", request.getParameter("TN008"));
				parames.put("TN009", request.getParameter("TN009"));
				parames.put("TN010", request.getParameter("TN010"));
				parameters = new WPP007(compID).buildSubGrid(subtype, parames);
			} else if (action.equals("excel")) {
				parames = new HashMap<String, String>();
				parames.put("F001", request.getParameter("F001"));
				parames.put("F002", request.getParameter("F002"));
				new PublicMethods().downloadFile(
						request,
						response,
						new WPP007(compID).getExportData(parames, request.getParameter("cols"),
								new APControl().getUserInfoFromSession(request.getSession(), uid, "lang")), action,
						Singleton.getInstance().getExpExcelName(WPP007.class));
			}
		}

		return parameters;
	}
}
