package com.dsc.dci.jweb.funcs.configs.roleconfig;

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
@RequestMapping("/Configs/RoleConfig.dsc")
public class RoleConfigController {
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody
	HashMap<String, Object> postMethod(HttpServletRequest request, HttpServletResponse response) {
		HashMap<String, Object> datas = null;

		String dcikey = request.getParameter(DCIWebConstants.getPostTagId());
		String action = request.getParameter("action");
		String uid = request.getParameter("uid");

		if (dcikey != null && dcikey.equals(DCIWebConstants.getPostTagValue())) {

			if (action == null || action.equals("")) {
				datas = new RoleConfig().getQueryColumns(new APControl().getUserInfoFromSession(request.getSession(),
						uid, "lang"));
			} else if (action.equals("init")) {
				datas = new RoleConfig().getIntiData(new APControl().getUserInfoFromSession(request.getSession(), uid,
						"lang"));
			} else if (action.equals("save")) {
				datas = new RoleConfig().saveData(request.getParameter("datas"), request.getParameter("mode"));
			} else if (action.equals("del")) {
				datas = new RoleConfig().deleteData(request.getParameter("datas"));
			} else if (action.equals("search")) {
				datas = new RoleConfig().allData(request.getParameter("page"), request.getParameter("pagesize"),
						request.getParameter("filter"));
			} else if (action.equals("bodyData")) {
				datas = new RoleConfig().getBodyData(request.getParameter("keys"));
			} else if (action.equals("bodySave")) {
				datas = new RoleConfig().saveBody(request.getParameter("datas"));
			} else if (action.equals("bodyDelete")) {
				datas = new RoleConfig().deleteBody(request.getParameter("datas"));
			} else if (action.equals("copyrole")) {
				datas = new RoleConfig().copyRole(request.getParameter("role_id"), request.getParameter("role_name"),
						new APControl().getUserInfoFromSession(request.getSession(), uid, "lang"));
			}
		}

		return datas;
	}
}
