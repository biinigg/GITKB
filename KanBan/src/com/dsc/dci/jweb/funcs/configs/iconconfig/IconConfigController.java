package com.dsc.dci.jweb.funcs.configs.iconconfig;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dci.jweb.DCIBeans.website.FileUploadBean;
import com.dci.jweb.DCIConstants.DCIWebConstants;
import com.dci.jweb.PublicLib.APControl;

@Controller
@RequestMapping("/Configs/IconConfig.dsc")
public class IconConfigController {
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody
	HashMap<String, Object> postMethod(HttpServletRequest request, HttpServletResponse response, FileUploadBean item,
			BindingResult result) {
		HashMap<String, Object> datas = null;
		String dcikey = request.getParameter(DCIWebConstants.getPostTagId());
		String action = request.getParameter("action");
		String uid = request.getParameter("uid");

		if (dcikey != null && dcikey.equals(DCIWebConstants.getPostTagValue())) {

			if (action == null || action.equals("")) {
				datas = new IconConfig().getQueryColumns(new APControl().getUserInfoFromSession(request.getSession(),
						uid, "lang"));
			} else if (action.equals("init")) {
				datas = new IconConfig().getIntiData(new APControl().getUserInfoFromSession(request.getSession(), uid,
						"lang"));
			} else if (action.equals("save")) {
				datas = new IconConfig().saveData(request.getParameter("datas"), request.getParameter("mode"), item,
						new APControl().getUserInfoFromSession(request.getSession(), uid, "lang"));
			} else if (action.equals("del")) {
				datas = new IconConfig().deleteData(request.getParameter("datas"));
			} else if (action.equals("search")) {
				datas = new IconConfig().getAllData(request.getParameter("page"), request.getParameter("pagesize"),
						request.getParameter("filter"),
						new APControl().getUserInfoFromSession(request.getSession(), uid, "lang"));
			} else if (action.equals("mapkeyCheck")) {
				datas = new IconConfig().mapkeyCheck(request.getParameter("icon_id"), request.getParameter("value"),
						new APControl().getUserInfoFromSession(request.getSession(), uid, "lang"));
			}
		}/*
		 * else{ try { response.sendRedirect(
		 * "/KanBan/FuncViews/Main/ErrorPage.jsp?errcode=dcie02"); } catch
		 * (IOException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } }
		 */

		return datas;
	}

}
