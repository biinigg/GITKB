package com.dsc.dci.jweb.funcs.wpp.wpp004;

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
@RequestMapping("/Funcs/WPP/WPP004.dsc")
public class WPP004Controller {
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
				parameters = new WPP004(compID).getComboBoxData();
			} else if (action.equals("query")) {
				parames = new HashMap<String, String>();
				parames.put("F001", request.getParameter("F001"));
				parames.put("F002", request.getParameter("F002"));
				parames.put("F003", request.getParameter("F003"));
				parames.put("F004", request.getParameter("F004"));
				parames.put("F005", request.getParameter("F005"));
				parames.put("F006", request.getParameter("F006"));
				parames.put("F007", request.getParameter("F007"));
				parames.put("F008", request.getParameter("F008"));
				parames.put("F009", request.getParameter("F009"));
				parames.put("F010", request.getParameter("F010"));
				parameters = new WPP004(compID).query(parames);
			} else if (action.equals("excel")) {
				parames = new HashMap<String, String>();
				parames.put("F001", request.getParameter("F001"));
				parames.put("F002", request.getParameter("F002"));
				parames.put("F003", request.getParameter("F003"));
				parames.put("F004", request.getParameter("F004"));
				parames.put("F005", request.getParameter("F005"));
				parames.put("F006", request.getParameter("F006"));
				parames.put("F007", request.getParameter("F007"));
				parames.put("F008", request.getParameter("F008"));
				parames.put("F009", request.getParameter("F009"));
				parames.put("F010", request.getParameter("F010"));
				new PublicMethods().downloadFile(
						request,
						response,
						new WPP004(compID).getExportData(parames, request.getParameter("cols"),
								new APControl().getUserInfoFromSession(request.getSession(), uid, "lang")), action,
						Singleton.getInstance().getExpExcelName(WPP004.class));
			}
		}

		return parameters;
	}

	// @RequestMapping(method = RequestMethod.GET)
	// public @ResponseBody
	// HashMap<String, ArrayList<Object>> getMethod(HttpServletRequest request,
	// HttpServletResponse response) {
	// HashMap<String, ArrayList<Object>> parameters = null;
	// String dcikey = request.getParameter(SFTWebConstants.getPostTagId());
	// String action = request.getParameter("action");
	// HashMap<String, String> parames = null;
	//
	// if (action.equals("init")) {
	// parameters = new WPP004().getComboBoxData();
	// } else if (action.equals("query")) {
	// parames = new HashMap<String, String>();
	// parames.put("F001", request.getParameter("F001"));
	// parames.put("F002", request.getParameter("F002"));
	// parames.put("F003", request.getParameter("F003"));
	// parames.put("F004", request.getParameter("F004"));
	// parames.put("F005", request.getParameter("F005"));
	// parames.put("F006", request.getParameter("F006"));
	// parames.put("F007", request.getParameter("F007"));
	// parames.put("F008", request.getParameter("F008"));
	// parames.put("F009", request.getParameter("F009"));
	// parames.put("F010", request.getParameter("F010"));
	// parameters = new WPP004().query(parames);
	// }
	//
	// return parameters;
	// }

}
