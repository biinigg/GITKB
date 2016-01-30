package com.dsc.dci.jweb.funcs.configs.pe000config;

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
@RequestMapping("/Configs/PE000Config.dsc")
public class PE000ConfigController {
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody
	HashMap<String, Object> postMethod(HttpServletRequest request, HttpServletResponse response) {
		HashMap<String, Object> datas = null;

		String dcikey = request.getParameter(DCIWebConstants.getPostTagId());
		String action = request.getParameter("action");
		String uid = request.getParameter("uid");
		
		if (dcikey != null && dcikey.equals(DCIWebConstants.getPostTagValue())) {
			if (action.equals("bodyData")) {
				datas = new PE000Config().getAllDatas();
			} else if (action.equals("bodySave")) {
				datas = new PE000Config().saveBody(request.getParameter("datas"));
			} else if (action.equals("init")) {
				datas = new PE000Config().getIntiData(new APControl().getUserInfoFromSession(request.getSession(), uid,
						"lang"));
			} else if (action.equals("bodyDelete")) {
				datas = new PE000Config().deleteBody(request.getParameter("datas"));
			}
		}
		return datas;
	}
}
