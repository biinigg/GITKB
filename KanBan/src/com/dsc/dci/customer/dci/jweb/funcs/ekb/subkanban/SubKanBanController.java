package com.dsc.dci.customer.dci.jweb.funcs.ekb.subkanban;

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
@RequestMapping("/CUS/Funcs/EKB/SubKanBan.dsc")
public class SubKanBanController {
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
				datas = new SubKanBan(uid).getInitData(session, request.getParameter("sql_id"),
						request.getParameter("subsql_id"));
			} else if (action.equals("getSubKBData")) {
				datas = new SubKanBan(uid).getKBDatas(session, request.getParameter("sql_id"),
						request.getParameter("conn_id"), request.getParameter("filter"));
			} else if (action.equals("doaction")) {
				datas = new SubKanBan(uid).doAction(request.getParameter("conn_id"),
						request.getParameter("btn_action"), request.getParameter("rowdata"));
			}
		}
		return datas;
	}
}
