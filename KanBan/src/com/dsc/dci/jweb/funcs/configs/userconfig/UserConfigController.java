package com.dsc.dci.jweb.funcs.configs.userconfig;

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
@RequestMapping("/Configs/UserConfig.dsc")
public class UserConfigController {
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody
	HashMap<String, Object> postMethod(HttpServletRequest request, HttpServletResponse response) {
		HashMap<String, Object> datas = null;

		String dcikey = request.getParameter(DCIWebConstants.getPostTagId());
		String action = request.getParameter("action");
		String uid = request.getParameter("uid");

		if (dcikey != null && dcikey.equals(DCIWebConstants.getPostTagValue())) {

			if (action == null || action.equals("")) {
				datas = new UserConfig().getQueryColumns(new APControl().getUserInfoFromSession(request.getSession(),
						uid,"lang"));
			} else if (action.equals("init")) {
				datas = new UserConfig().getIntiData(new APControl().getUserInfoFromSession(request.getSession(),
						uid,"lang"));
			} else if (action.equals("save")) {
				datas = new UserConfig().saveData(request.getParameter("datas"), request.getParameter("mode"));
			} else if (action.equals("del")) {
				datas = new UserConfig().deleteData(request.getParameter("datas"));
			} else if (action.equals("search")) {
				datas = new UserConfig().getAllUserInfo(request.getParameter("page"), request.getParameter("pagesize"),
						request.getParameter("filter"));
			}else if (action.equals("bodyData")) {
				datas = new UserConfig().getBodyData(request.getParameter("keys"));
			} else if (action.equals("bodySave")) {
				datas = new UserConfig().saveBody(request.getParameter("datas"));
			} else if (action.equals("bodyDelete")) {
				datas = new UserConfig().deleteBody(request.getParameter("datas"));
			}

		}

		return datas;
	}
}
