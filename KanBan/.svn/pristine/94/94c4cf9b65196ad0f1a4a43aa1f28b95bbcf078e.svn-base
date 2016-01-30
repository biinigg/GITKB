package com.dsc.dci.jweb.funcs.lkb.lkb002;

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
@RequestMapping("/Funcs/LKB/LKB002.dsc")
public class LKB002Controller {
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
		parames.put("F008", request.getParameter("F008"));
		parames.put("F009", request.getParameter("F009"));

		if (dcikey != null && dcikey.equals(DCIWebConstants.getPostTagValue())) {
			parameters = new HashMap<String, ArrayList<Object>>();
			if (action.equals("query")) {
				parameters.put("rows", new LKB002(compID).getQueryData(parames));
			} else if (action.equals("F001")) {
				parameters.put("values", new LKB002(compID).getF001Data());
			} else if (action.equals("F002")) {
				parameters.put("values", new LKB002(compID).getF002Data(parames.get("F001")));
			} else if (action.equals("F004")) {
				// parameters.put("items", new EKBQ003().getF004Data());
			} else if (action.equals("F005")) {
				parameters.put("values", new LKB002(compID).getF005Data(parames.get("F001")));
			} else if (action.equals("F008")) {
				parameters.put("items", new LKB002(compID).getF008Data(parames.get("F001"), parames.get("F002")));
			} else if (action.equals("F009")) {
				parameters.put("values", new LKB002(compID).getF009Data(parames.get("F001"), parames.get("F002")));
			} else if (action.equals("chart")) {
				parameters.put("datas", new LKB002(compID).getChartData(parames));
			} else if (action.equals("chart2")) {
				parameters.put("datas", new LKB002(compID).getChart2Data(parames));
			} else if (action.equals("excel") || action.equals("html")) {
				// new PublicMethods().downloadFile(request, response,
				// exceldatas, action, "LKB002");
				new PublicMethods().downloadFile(
						request,
						response,
						new LKB002(compID).exportFile(parames, request.getParameter("cols"),
								request.getParameter("ctype")), action, "LKB002");
			}
		}

		return parameters;
	}
}