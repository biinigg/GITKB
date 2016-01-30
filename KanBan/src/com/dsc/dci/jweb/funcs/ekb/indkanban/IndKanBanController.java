package com.dsc.dci.jweb.funcs.ekb.indkanban;

import java.util.HashMap;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.InternalResourceView;
import org.springframework.web.servlet.view.RedirectView;

import com.dci.jweb.DCIConstants.DCIWebConstants;
import com.dci.jweb.PublicLib.DCIString;

@Controller
@RequestMapping("/EKB/OpenLink/IndKanBan.dsc")
public class IndKanBanController {
	/*
	 * @RequestMapping(method = RequestMethod.GET) public ModelAndView
	 * getMethod(HttpServletRequest request, HttpServletResponse response) {
	 * String indkey = request.getParameter("indkey"); String pagekey =
	 * request.getParameter("pagedkey"); String dcikey = null; ModelAndView view
	 * = null; HashMap<String, String> params = null; final String kburl =
	 * "./../../FuncViews/Funcs/IndKanBan.jsp";
	 * 
	 * if (DCIString.isNullOrEmpty(indkey)) { if
	 * (DCIString.isNullOrEmpty(pagekey)) { view = new ModelAndView(new
	 * RedirectView("./../../FuncViews/Main/ErrorPage.jsp?errcode=dcie01")); }
	 * else {
	 * 
	 * } } else { IndKanBan ikb = new IndKanBan(); params =
	 * ikb.decodeParams(indkey); dcikey = params.get("dcikey");
	 * 
	 * if (dcikey != null && dcikey.equals(DCIWebConstants.getPostTagValue())) {
	 * try { if (ikb.transCheck(request, params)) { view = new ModelAndView(new
	 * RedirectView(kburl)); view.addObject("pagekey",
	 * ikb.encodePageParams(params)); } else { view = new ModelAndView(new
	 * RedirectView("./../../")); view.addObject("ecode", "logine" +
	 * DCIString.strFix(String.valueOf(ikb.getLoginStatus()), 2, true, "0")); }
	 * } catch (Exception e) { e.printStackTrace(); } } else { view = new
	 * ModelAndView(new
	 * RedirectView("./../../FuncViews/Main/ErrorPage.jsp?errcode=dcie02")); } }
	 * return view; }
	 */

	@RequestMapping(value = "/model", method = RequestMethod.GET)
	public ModelAndView getMethod(HttpServletRequest request, HttpServletResponse response) {
		String indkey = request.getParameter("indkey");
		String pagekey = request.getParameter("pagedkey");
		String dcikey = null;
		ModelAndView view = null;
		HashMap<String, String> params = null;
		// final String kburl = "./../../FuncViews/Funcs/IndKanBan.jsp";

		if (DCIString.isNullOrEmpty(indkey)) {
			if (DCIString.isNullOrEmpty(pagekey)) {
				view = new ModelAndView(new RedirectView("./../../FuncViews/Main/ErrorPage.jsp?errcode=dcie01"));
			} else {

			}
		} else {
			IndKanBan ikb = new IndKanBan();
			params = ikb.decodeParams(indkey);
			dcikey = params.get("dcikey");

			if (dcikey != null && dcikey.equals(DCIWebConstants.getPostTagValue())) {
				try {
					if (ikb.transCheck(request, params)) {
						request.setAttribute("pagekey", ikb.encodePageParams(params));
						request.setAttribute("uid", params.get("user_id"));
						// InternalResourceView v = new
						// InternalResourceView(kburl);
						// Properties p = new Properties();
						// p.setProperty("pagekey",
						// ikb.encodePageParams(params));
						// v.setAttributes(p);
						view = new ModelAndView(new InternalResourceView(ikb.getCUSUrl()));

						// view.addObject("pagekey",
						// ikb.encodePageParams(params));
					} else {
						view = new ModelAndView(new RedirectView("./../../"));
						view.addObject("ecode",
								"logine" + DCIString.strFix(String.valueOf(ikb.getLoginStatus()), 2, true, "0"));
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				view = new ModelAndView(new RedirectView("./../../FuncViews/Main/ErrorPage.jsp?errcode=dcie02"));
			}
		}
		return view;
	}

	// @RequestMapping(value = "/forward", method = RequestMethod.GET)
	// public String getMethod(HttpServletRequest request, HttpServletResponse
	// response) {
	// String indkey = request.getParameter("indkey");
	// String pagekey = request.getParameter("pagedkey");
	// String dcikey = null;
	// String view = null;
	// HashMap<String, String> params = null;
	// final String kburl = "./../../FuncViews/Funcs/IndKanBan.jsp";
	//
	// if (DCIString.isNullOrEmpty(indkey)) {
	// if (DCIString.isNullOrEmpty(pagekey)) {
	// view = "./../../FuncViews/Main/ErrorPage.jsp?errcode=dcie01";
	// } else {
	//
	// }
	// } else {
	// IndKanBan ikb = new IndKanBan();
	// params = ikb.decodeParams(indkey);
	// dcikey = params.get("dcikey");
	//
	// if (dcikey != null && dcikey.equals(DCIWebConstants.getPostTagValue())) {
	// try {
	// if (ikb.transCheck(request, params)) {
	// view = "forward:" + kburl;
	// } else {
	// view = "./../../?ecode=logine"
	// + DCIString.strFix(String.valueOf(ikb.getLoginStatus()), 2, true, "0");
	// }
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// } else {
	// view = "./../../FuncViews/Main/ErrorPage.jsp?errcode=dcie02";
	// }
	// }
	// return view;
	// }

	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody
	HashMap<String, Object> postMethod(HttpServletRequest request, HttpServletResponse response) {
		HashMap<String, Object> datas = null;
		String dcikey = request.getParameter(DCIWebConstants.getPostTagId());
		String action = request.getParameter("action");

		if (dcikey != null && dcikey.equals(DCIWebConstants.getPostTagValue())) {
			datas = new HashMap<String, Object>();
			if (action.equals("getparams")) {
				HashMap<String, String> data = new IndKanBan().decodePageParams(request.getParameter("pagekey"));
				if (data == null) {
					datas.put("success", false);
					datas.put("errorMessage", "");
				} else {
					datas.put("success", true);
					datas.put("params", data);
				}
			} else if (action.equals("kanbanCheck")) {
				// datas.put("ips", new SysDBConfig().getAllIPs());
			}
		}

		return datas;
	}
}
