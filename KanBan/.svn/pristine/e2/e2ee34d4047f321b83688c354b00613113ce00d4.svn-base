package com.dsc.dci.jweb.funcs.wpp.wpp001;

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
@RequestMapping("/Funcs/WPP/WPP001.dsc")
public class WPP001Controller {
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody
	HashMap<String, ArrayList<Object>> postMethod(HttpServletRequest request, HttpServletResponse response) {
		HashMap<String, ArrayList<Object>> parameters = null;
		String dcikey = request.getParameter(DCIWebConstants.getPostTagId());
		String action = request.getParameter("action");
		String uid = request.getParameter("uid");
		HashMap<String, String> parames = null;
		String conn_id = request.getParameter("conn_id");
		WPP001 wpp01 = null;

		if (dcikey != null && dcikey.equals(DCIWebConstants.getPostTagValue())) {
			wpp01 = new WPP001(conn_id);
			if (action.equals("init")) {
				parameters = new WPP001(conn_id).getComboBoxData();
			} else if (action.equals("query")) {
				parames = new HashMap<String, String>();
				parames.put("F001", request.getParameter("F001"));
				parames.put("F002", request.getParameter("F002"));
				parames.put("F003", request.getParameter("F003"));
				parames.put("F004", request.getParameter("F004"));
				parames.put("F005", request.getParameter("F005"));
				parameters = wpp01.query(parames);
			} else if (action.equals("sub")) {
				String subtype = request.getParameter("SubType");
				parames = new HashMap<String, String>();
				parames.put("TB001", request.getParameter("TB001"));
				parames.put("TB002", request.getParameter("TB002"));
				parames.put("TB003", request.getParameter("TB003"));
				parames.put("TB004", request.getParameter("TB004"));
				parames.put("TB005", request.getParameter("TB005"));
				parames.put("TB006", request.getParameter("TB006"));
				parames.put("TB007", request.getParameter("TB007"));
				parames.put("ORDERID", request.getParameter("ORDERID"));
				parameters = wpp01.buildSubGrid(subtype, parames,
						new APControl().getUserInfoFromSession(request.getSession(), uid, "lang"));
			} else if (action.equals("excel")) {
				parames = new HashMap<String, String>();
				parames.put("F001", request.getParameter("F001"));
				parames.put("F002", request.getParameter("F002"));
				parames.put("F003", request.getParameter("F003"));
				parames.put("F004", request.getParameter("F004"));
				parames.put("F005", request.getParameter("F005"));
				new PublicMethods().downloadFile(
						request,
						response,
						wpp01.getExportData(parames, request.getParameter("cols"),
								new APControl().getUserInfoFromSession(request.getSession(), uid, "lang")), action,
						Singleton.getInstance().getExpExcelName(WPP001.class));
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
	// parames = new HashMap<String, String>();
	// parames.put("F001", request.getParameter("F001"));
	//
	// if (dcikey != null && dcikey.equals(SFTWebConstants.getPostTagValue())) {
	// if (action.equals("init")) {
	// parameters = new WPP001().getComboBoxData();
	// } else if (action.equals("query")) {
	// parames = new HashMap<String, String>();
	// parames.put("F001", request.getParameter("F001"));
	// parames.put("F002", request.getParameter("F002"));
	// parames.put("F003", request.getParameter("F003"));
	// parames.put("F004", request.getParameter("F004"));
	// parames.put("F005", request.getParameter("F005"));
	// parameters = new WPP001().query(parames);
	// } else if (action.equals("sub")) {
	// String subtype = request.getParameter("SubType");
	// parames = new HashMap<String, String>();
	// parames.put("TB001", request.getParameter("TB001"));
	// parames.put("TB002", request.getParameter("TB002"));
	// parames.put("TB003", request.getParameter("TB003"));
	// parames.put("TB004", request.getParameter("TB004"));
	// parames.put("TB005", request.getParameter("TB005"));
	// parames.put("TB006", request.getParameter("TB006"));
	// parames.put("TB007", request.getParameter("TB007"));
	// parameters = new WPP001().buildSubGrid(subtype, parames);
	// }
	// }
	//
	// return parameters;
	// }

}
