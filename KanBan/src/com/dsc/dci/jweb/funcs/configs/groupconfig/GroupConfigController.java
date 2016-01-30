package com.dsc.dci.jweb.funcs.configs.groupconfig;

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
@RequestMapping("/Configs/GroupConfig.dsc")
public class GroupConfigController {
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody
	HashMap<String, Object> postMethod(HttpServletRequest request, HttpServletResponse response) {
		HashMap<String, Object> datas = null;

		String dcikey = request.getParameter(DCIWebConstants.getPostTagId());
		String action = request.getParameter("action");
		String uid = request.getParameter("uid");

		if (dcikey != null && dcikey.equals(DCIWebConstants.getPostTagValue())) {

			if (action == null || action.equals("")) {
				datas = new GroupConfig().getQueryColumns(new APControl().getUserInfoFromSession(request.getSession(),
						uid, "lang"));
			} else if (action.equals("save")) {
				datas = new GroupConfig().saveData(request.getParameter("datas"), request.getParameter("mode"));
			} else if (action.equals("del")) {
				datas = new GroupConfig().deleteData(request.getParameter("datas"));
			} else if (action.equals("search")) {
				datas = new GroupConfig().allData(request.getParameter("page"), request.getParameter("pagesize"),
						request.getParameter("filter"));
			} else if (action.equals("roles")) {
				datas = new GroupConfig().getGroupRoles(request.getParameter("keys"));
			} else if (action.equals("copygroup")) {
				datas = new GroupConfig().copyGroup(request.getParameter("group_id"),
						request.getParameter("group_name"),
						new APControl().getUserInfoFromSession(request.getSession(), uid, "lang"));
			}
		}

		return datas;
	}
}
