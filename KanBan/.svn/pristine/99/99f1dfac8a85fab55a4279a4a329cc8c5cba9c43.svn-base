package com.dsc.dci.jweb.funcs.main.login;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.dci.jweb.DCIConstants.DCIWebConstants;
import com.dci.jweb.PublicLib.APControl;
import com.dci.jweb.PublicLib.DCIDate;
import com.dci.jweb.PublicLib.DCIString;
import com.dsc.dci.jweb.pub.APConstants;

@Controller
@RequestMapping("/Login.dsc")
public class LoginController {
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView postMethod(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView view = null;
		int loginStatus = 99;
		String checkcode = request.getParameter("checkcode");
		String dcikey = request.getParameter(DCIWebConstants.getPostTagId());
		String uid = request.getParameter("uid");
		String pwd = request.getParameter("pwd");
		String langtp = request.getParameter("langtp");
		String action = request.getParameter("action");
//		String result = new APPubMethods().sendPost("", "");
//		System.out.println(result);
		if (action != null && action.equals("logout")) {
			new Login().logout(request, uid);
			HashMap<String, String> model = new HashMap<String, String>();
			model.put("success", "true");
			view = new ModelAndView("logout");
			view.addAllObjects(model);
		} else if (action != null && action.equals("login")) {
			if (dcikey != null && dcikey.equals(DCIWebConstants.getPostTagValue()) && checkcode != null
					&& checkcode.equals(APConstants.getLoginpagecheckcode())) {

				Login login = new Login();
				// if
				// (request.getSession().getAttribute(DCIWebConstants.getUserInfoTag())
				// == null) {
				loginStatus = login.checkPwd(uid, pwd, new APControl().getRequestIP(request));
				// } else {
				// loginStatus = 8;
				// }
				if (loginStatus == 4) {
					HashMap<String, String> tmp = login.getUserInfo();
					if (tmp.size() == 0) {
						loginStatus = 5;
					} else {
						tmp.put("lang", langtp);
						// request.getSession().setAttribute(DCIWebConstants.getUserInfoTag(),
						// tmp);
						request.getSession().setAttribute(DCIWebConstants.getUserInfoTag() + uid, tmp);
					}
				}
				view = new ModelAndView();
				if (loginStatus == 4) {
					request.getSession().setAttribute(DCIWebConstants.getSessionForwardTagId() + uid,
							DCIWebConstants.getForwardTagValue());
					view.addObject("key", DCIString.strEncode(uid + "^" + DCIDate.getCurrTime()));
					view.setViewName("indexView");
				} else {
					view.addObject("ecode", "logine" + DCIString.strFix(String.valueOf(loginStatus), 2, true, "0"));
					view.addObject("ltp", langtp);
					view.setViewName("startView");
				}
			}
		}
		return view;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView getMethod(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView view = null;
		String dcikey = request.getParameter(DCIWebConstants.getPostTagId());
		String uid = request.getParameter("uid");
		String action = request.getParameter("action");

		if (action != null && action.equals("logout")) {
			new Login().logout(request, uid);
			HashMap<String, String> model = new HashMap<String, String>();
			model.put("success", "true");
			view = new ModelAndView("logout");
			view.addAllObjects(model);
		}
		return view;
	}
}
