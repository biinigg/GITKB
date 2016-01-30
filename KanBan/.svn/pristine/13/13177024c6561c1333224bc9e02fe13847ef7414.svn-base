package com.dsc.dci.customer.c06100137.jweb.funcs.ekb.subkanban;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dci.jweb.DCIConstants.DCIWebConstants;

@Controller
@RequestMapping("/CUS/Funcs/EKB/SubKanBan06100137.dsc")
public class SubKanBan06100137Controller {
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody
	HashMap<String, Object> postMethod(HttpServletRequest request, HttpServletResponse response) {
		HashMap<String, Object> datas = null;
		String dcikey = request.getParameter(DCIWebConstants.getPostTagId());
		String action = request.getParameter("action");
		String uid = request.getParameter("uid");
		HttpSession session = null;

		if (dcikey != null && dcikey.equals(DCIWebConstants.getPostTagValue())) {
			session = request.getSession();
			if (action.equals("init")) {
				datas = new SubKanBan06100137(session, uid).getInitData(session, request.getParameter("sql_id"));
			}
		}
		return datas;
	}
}
