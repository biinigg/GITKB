package com.dsc.dci.jweb.pub;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dci.jweb.DCIConstants.DCIWebConstants;


@Controller
@RequestMapping("/SystemFuncs.dsc")
public class SystemFuncsController {
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody
	HashMap<String, Object> postMethod(HttpServletRequest request, HttpServletResponse response) {
		HashMap<String, Object> datas = null;

		String dcikey = request.getParameter(DCIWebConstants.getPostTagId());
		String action = request.getParameter("action");

		if (dcikey != null && dcikey.equals(DCIWebConstants.getPostTagValue())) {
			if (action.equals("langReload")) {
				datas = new HashMap<String, Object>();
				if (Singleton.getInstance().getDatabaseStatus()) {
					APPubMethods pm = new APPubMethods();
					pm.addLanguages(request.getSession().getServletContext().getRealPath("/"));
					pm.loadMultiLanguage();
					datas.put("result", true);
				} else {
					datas.put("result", false);
				}
			}
		}

		return datas;
	}
}