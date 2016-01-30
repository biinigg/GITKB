package com.dsc.dci.jweb.funcs.lkb.lkb001;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dci.jweb.DCIConstants.DCIWebConstants;

@Controller
@RequestMapping("/Funcs/LKB/LKB001.dsc")
public class LKB001Controller {
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody
	HashMap<String, ArrayList<Object>> postMethod(HttpServletRequest request, HttpServletResponse response) {
		HashMap<String, ArrayList<Object>> parameters = null;
		String dcikey = request.getParameter(DCIWebConstants.getPostTagId());
		String action = request.getParameter("action");
		String compID = request.getParameter("conn_id");
		String uid = request.getParameter("uid");
		HashMap<String, String> parames = null;

		parames = new HashMap<String, String>();
		parames.put("F001", request.getParameter("F001"));
		parames.put("F002", request.getParameter("F002"));
		parames.put("F003", request.getParameter("F003"));
		parames.put("F005", request.getParameter("F005"));
		parames.put("F008", request.getParameter("F008"));
		parames.put("updatetime", request.getParameter("currtime"));

		if (dcikey != null && dcikey.equals(DCIWebConstants.getPostTagValue())) {
			parameters = new HashMap<String, ArrayList<Object>>();
			if (action.equals("query")) {
				parameters = new LKB001(compID).getQueryData(parames);
			} else if (action.equals("F001")) {
				parameters.put("values", new LKB001(compID).getF001Data());
			} else if (action.equals("F002")) {
				parameters.put("values", new LKB001(compID).getF002Data(parames.get("F001")));
			} else if (action.equals("F004")) {
				// parameters.put("items", new EKBQ003().getF004Data());
			} else if (action.equals("F008")) {
				parameters.put("items", new LKB001(compID).getF008Data(parames.get("F001"), parames.get("F002")));
			} else if (action.equals("chart")) {
				// parameters.put("datas", new EKBQ002().getChartData(parames));
			}
		}

		return parameters;
	}
}
