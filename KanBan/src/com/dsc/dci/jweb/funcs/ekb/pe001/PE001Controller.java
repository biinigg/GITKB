package com.dsc.dci.jweb.funcs.ekb.pe001;

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
import com.dci.jweb.PublicLib.PublicMethods;
import com.dsc.dci.jweb.funcs.configs.sqleditor.SQLEditor;

@Controller
@RequestMapping("/Funcs/EKB/PE001.dsc")
public class PE001Controller {
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
				datas = new PE001(session, uid).getInitData(request.getParameter("conn_id"),uid);
			} else if (action.equals("query")) {
				datas = new PE001(session, uid).getSqlColumns(request.getParameter("sql_id"),
						new APControl().getUserInfoFromSession(request.getSession(), uid, "lang"));
				new PE001(session, uid).saveBody(datas);
				datas = new PE001(session, uid).getKanBanData(request.getParameter("page"),
						request.getParameter("pagesize"), request.getParameter("filter"), request.getParameter("sort"),
						request.getParameter("sql_id"), request.getParameter("conn_id"));
			} else if (action.equals("marqueeData")) {
				datas = new PE001(session, uid).getMarqueeData(request.getParameter("sql_id"),
						request.getParameter("conn_id"));
			} else if (action.equals("saveFormat") || action.equals("loadDefFormat")) {
				datas = new PE001(session, uid).saveFromat(request.getParameter("datas"),
						request.getParameter("gridid"), request.getParameter("coldatas"));
			} else if (action.equals("saveCondi") || action.equals("loadDefCondi")) {
				datas = new PE001(session, uid).saveData(request.getParameter("datas"),
						request.getParameter("sql_id"), request.getParameter("advDatas"));
			} else if (action.equals("funcInfo")) {
					//String conn_id = new PE001(session, uid).getConnid(request.getParameter("func_id"),uid);
					//if(conn_id.length()!=0&&conn_id!=null){
						datas = new PE001(session, uid).getFuncInfo(request.getParameter("conn_id"),request.getParameter("func_id"));
						datas.put("conn_id",request.getParameter("conn_id"));
					//}
			} else if (action.equals("excel") || action.equals("html")) {
				new PublicMethods().downloadFile(
						request,
						response,
						new PE001(session, uid).exportFile(request.getParameter("filter"),
								request.getParameter("sort"), request.getParameter("func_id"),
								request.getParameter("conn_id"), request.getParameter("cols"),
								request.getParameter("ctype")), action, request.getParameter("func_id"));
			}
		}
		return datas;
	}
}
