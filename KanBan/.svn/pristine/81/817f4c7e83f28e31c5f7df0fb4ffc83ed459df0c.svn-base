package com.dsc.dci.jweb.funcs.configs.connconfig;

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
@RequestMapping("/Configs/ConnConfig.dsc")
public class ConnConfigController {
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody
	HashMap<String, Object> postMethod(HttpServletRequest request, HttpServletResponse response) {
		HashMap<String, Object> datas = null;

		String dcikey = request.getParameter(DCIWebConstants.getPostTagId());
		String action = request.getParameter("action");
		String uid = request.getParameter("uid");

		if (dcikey != null && dcikey.equals(DCIWebConstants.getPostTagValue())) {

			if (action == null || action.equals("")) {
				datas = new ConnConfig().getQueryColumns(new APControl().getUserInfoFromSession(request.getSession(),
						uid, "lang"));
			} else if (action.equals("init")) {
				datas = new ConnConfig().getIntiData();
			} else if (action.equals("save")) {
				datas = new ConnConfig().saveData(request.getParameter("datas"), request.getParameter("mode"));
			} else if (action.equals("del")) {
				datas = new ConnConfig().deleteData(request.getParameter("datas"));
			} else if (action.equals("search")) {
				datas = new ConnConfig().allData(request.getParameter("page"), request.getParameter("pagesize"),
						request.getParameter("filter"));
			} else if (action.equals("connCheck")) {
				datas = new ConnConfig().connCheck(request.getParameter("datas"));
			} else if (action.equals("checkName")) {
				datas = new ConnConfig().nameCheck(request.getParameter("conn_id"), request.getParameter("conn_name"),
						new APControl().getUserInfoFromSession(request.getSession(), uid,"lang"));
			}
		}

		return datas;
	}
}
