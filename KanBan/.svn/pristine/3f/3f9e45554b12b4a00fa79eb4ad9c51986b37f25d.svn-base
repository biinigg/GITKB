package com.dsc.dci.jweb.funcs.lkb.lkb003;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dci.jweb.DCIConstants.DCIWebConstants;
import com.dci.jweb.PublicLib.PublicMethods;

@Controller
@RequestMapping("/Funcs/LKB/LKB003.dsc")
public class LKB003Controller {
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody
	HashMap<String, ArrayList<Object>> postMethod(HttpServletRequest request, HttpServletResponse response) {
		HashMap<String, ArrayList<Object>> parameters = null;
		String dcikey = request.getParameter(DCIWebConstants.getPostTagId());
		String compID = request.getParameter("conn_id");
		String action = request.getParameter("action");
		String uid = request.getParameter("uid");
		//String exceldatas = request.getParameter("exceldatas");
		HashMap<String, String> parames = null;

		parames = new HashMap<String, String>();
		parames.put("F001", request.getParameter("F001"));
		parames.put("F002", request.getParameter("F002"));
		parames.put("F003s", request.getParameter("F003s"));
		parames.put("F003e", request.getParameter("F003e"));
		parames.put("F005", request.getParameter("F005"));
		parames.put("F006", request.getParameter("F006"));
		parames.put("F007", request.getParameter("F007"));
		parames.put("F008", request.getParameter("F008"));
		parames.put("F009s", request.getParameter("F009s"));
		parames.put("F009e", request.getParameter("F009e"));
		parames.put("F011", request.getParameter("F011"));

		if (dcikey != null && dcikey.equals(DCIWebConstants.getPostTagValue())) {
			parameters = new HashMap<String, ArrayList<Object>>();
			if (action.equals("query")) {
				parameters.put("rows", new LKB003(compID).getQueryData(parames));
				parameters.put("series", new LKB003(compID).getSeriesData(parames));
				parameters.put("chartdatas", new LKB003(compID).getChartData(parames));
			} else if (action.equals("F001")) {
				parameters.put("values", new LKB003(compID).getF001Data());
			} else if (action.equals("F002")) {
				parameters.put("values", new LKB003(compID).getF002Data(parames.get("F001")));
			} else if (action.equals("F004")) {
				// parameters.put("items", new LKB003().getF004Data());
			} else if (action.equals("F005")) {
				parameters.put("items", new LKB003(compID).getF005Data(parames.get("F001"), parames.get("F002")));
			} else if (action.equals("F006")) {
				parameters.put("items", new LKB003(compID).getF006Data());
			} else if (action.equals("F011")) {
				parameters.put("items", new LKB003(compID).getF011Data(parames.get("F001"), parames.get("F002")));
			} else if (action.equals("chart")) {
				parameters.put("datas", new LKB003(compID).getChartData(parames));
			} else if (action.equals("series")) {
				parameters.put("series", new LKB003(compID).getSeriesData(parames));
			} else if (action.equals("excel") || action.equals("html")) {
				// new PublicMethods().downloadFile(request, response,
				// exceldatas, action, "LKB003");
				new PublicMethods().downloadFile(
						request,
						response,
						new LKB003(compID).exportFile(parames, request.getParameter("cols"),
								request.getParameter("ctype")), action, "LKB003");
			}
		}

		return parameters;
	}
}
