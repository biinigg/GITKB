package com.dsc.dci.customer.c02420302.jweb.funcs.configs.combineconfig;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dci.jweb.DCIConstants.DCIWebConstants;
import com.dci.jweb.PublicLib.APControl;

@Controller
@RequestMapping("/Configs/CombineConfig.dsc")
public class CombineConfig02420302Controller {
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
			if (action == null || action.equals("")) {
				datas = new CombineConfig02420302(session, uid).getQueryColumns(new APControl().getUserInfoFromSession(request.getSession(),
						uid, "lang"));
			} else if (action.equals("init")) {
				datas = new CombineConfig02420302(session, uid).getIntiData(new APControl().getUserInfoFromSession(request.getSession(), uid,
						"lang"));
			}else if (action.equals("KBData")) {
					datas = new CombineConfig02420302(session, uid).getKBData(request.getParameter("conn_id"));
			} else if (action.equals("conn")) {
				datas = new CombineConfig02420302(session, uid).initConnData();
			} else if (action.equals("loadHeadPanel")) {
				datas = new CombineConfig02420302(session, uid).reloadHeadPanel(request.getParameter("datas"));
			} else if (action.equals("colData")) {
				datas = new CombineConfig02420302(session, uid).getColData(request.getParameter("head_id"),request.getParameter("body_id"));
			} else if (action.equals("save")) {
				datas = new CombineConfig02420302(session, uid).saveData(request.getParameter("datas"), request.getParameter("mode"),new APControl().getUserInfoFromSession(request.getSession(), uid, "lang"));
			} else if (action.equals("del")) {
				datas = new CombineConfig02420302(session, uid).deleteData(request.getParameter("datas"));
			} else if (action.equals("search")) {
				datas = new CombineConfig02420302(session, uid).allData(request.getParameter("page"), request.getParameter("pagesize"),
						request.getParameter("filter"));
			} else if (action.equals("linkusers")) {
				datas = new CombineConfig02420302(session, uid).getAllUsers();
			} else if (action.equals("createLink")) {
				datas = new CombineConfig02420302(session, uid).createLink(request.getParameter("func_id"), request.getParameter("conn_id"),
						request.getParameter("linkuid"), request);
			}
		}
		return datas;
	}
}
