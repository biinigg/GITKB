package com.dsc.dci.jweb.funcs.main.login;

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

@Controller
@RequestMapping("/LoginInfo.dsc")
public class LoginInfoController {
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody
	HashMap<String, Object> postMethod(HttpServletRequest request, HttpServletResponse response) {
		HashMap<String, Object> datas = null;

		String dcikey = request.getParameter(DCIWebConstants.getPostTagId());
		String action = request.getParameter("action");

		if (dcikey != null && dcikey.equals(DCIWebConstants.getPostTagValue())) {
			if (action.equals("userlist")) {
				datas = new Login().getUserList();
			} else if (action.equals("removeUser")) {
				datas = new Login().removeUser(request.getParameter("user_id"), request.getParameter("ip_addr"),
						request.getParameter("pwd"), request.getParameter("langtp"));
			}
		}

		return datas;
	}
}