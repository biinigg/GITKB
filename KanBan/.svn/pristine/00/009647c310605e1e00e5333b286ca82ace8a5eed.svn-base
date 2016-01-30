package com.dsc.dci.jweb.funcs.configs.marqueeconfig;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dci.jweb.DCIConstants.DCIWebConstants;
import com.dci.jweb.PublicLib.APControl;

@Controller
@RequestMapping("/Configs/MarqueeConfig.dsc")
public class MarqueeConfigController {
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody
	HashMap<String, Object> postMethod(HttpServletRequest request, HttpServletResponse response) {
		HashMap<String, Object> datas = null;
		String dcikey = request.getParameter(DCIWebConstants.getPostTagId());
		String action = request.getParameter("action");
		String uid = request.getParameter("uid");

		if (dcikey != null && dcikey.equals(DCIWebConstants.getPostTagValue())) {
			if (action.equals("initQuery")) {
				datas = new MarqueeConfig().getQueryColumns(
						new APControl().getUserInfoFromSession(request.getSession(), uid, "lang"),
						request.getParameter("btnid"));
			} else if (action.equals("init")) {
				datas = new MarqueeConfig().getIntiData(new APControl().getUserInfoFromSession(request.getSession(),
						uid, "lang"));
			} else if (action.equals("save")) {
				datas = new MarqueeConfig().saveData(request.getParameter("datas"), request.getParameter("mode"),
						new APControl().getUserInfoFromSession(request.getSession(), uid, "lang"));
			} else if (action.equals("del")) {
				datas = new MarqueeConfig().deleteData(request.getParameter("datas"));
			} else if (action.equals("search")) {
				datas = new MarqueeConfig().getAllData(request.getParameter("page"), request.getParameter("pagesize"),
						request.getParameter("filter"),
						new APControl().getUserInfoFromSession(request.getSession(), uid, "lang"));
			} else if (action.equals("openwinQcombo")) {
				datas = new MarqueeConfig().getQueryColumns(
						new APControl().getUserInfoFromSession(request.getSession(), uid, "lang"),
						request.getParameter("btnid"));
			} else if (action.equals("openwin")) {
				datas = new MarqueeConfig().getOpenWinData(request.getParameter("btnid"),
						new APControl().getUserInfoFromSession(request.getSession(), uid, "lang"));
			}
		}
		return datas;
	}
}
