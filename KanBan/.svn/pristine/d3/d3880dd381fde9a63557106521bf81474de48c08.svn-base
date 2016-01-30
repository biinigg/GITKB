package com.dsc.dci.jweb.pub;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dci.jweb.DCIConstants.DCIWebConstants;
import com.dci.jweb.PublicLib.DCIDate;
import com.dci.jweb.PublicLib.DCIString;

@Controller
@RequestMapping("/PublicCtrl.dsc")
public class PublicController {
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody
	HashMap<String, Object> postMethod(HttpServletRequest request, HttpServletResponse response) {
		HashMap<String, Object> datas = null;

		String dcikey = request.getParameter(DCIWebConstants.getPostTagId());
		String action = request.getParameter("action");
		String uid = request.getParameter("uid");

		if (dcikey != null && dcikey.equals(DCIWebConstants.getPostTagValue())) {
			if (action.equals("getGFormat")) {
				datas = new APPubMethods().getGridFormat(
				// new APControl().getUserInfoFromSession(request.getSession(),
				// "user_id"),
						uid, request.getParameter("gridid"));
			} else if (action.equals("saveGFormat")) {
				datas = new APPubMethods().editGridFormat(
				// new APControl().getUserInfoFromSession(request.getSession(),
				// "user_id"),
						uid, request.getParameter("gridid"), request.getParameter("datas"));
			} else if (action.equals("clearGFormat")) {
				datas = new APPubMethods().editGridFormat(
				// new APControl().getUserInfoFromSession(request.getSession(),
				// "user_id"),
						uid, request.getParameter("gridid"), null);
			} else if (action.equals("B64Encode")) {
				datas = new HashMap<String, Object>();
				datas.put("result", DCIString.Base64Encode(request.getParameter("str")));
			} else if (action.equals("B64Decode")) {
				datas = new HashMap<String, Object>();
				datas.put("result", DCIString.Base64Decode(request.getParameter("str")));
			} else if (action.equals("langReload")) {
				datas = new HashMap<String, Object>();
				if (Singleton.getInstance().getDatabaseStatus()) {
					APPubMethods pm = new APPubMethods();
					pm.addLanguages(request.getSession().getServletContext().getRealPath("/"));
					pm.loadMultiLanguage();
					datas.put("result", true);
				} else {
					datas.put("result", false);
				}

			} else if (action.equals("getuid")) {
				String value = DCIString.strDecode(request.getParameter("key"));
				datas = new HashMap<String, Object>();

				if (DCIString.isNullOrEmpty(value)) {
					datas.put("result", false);
				} else {
					String[] values = value.split("\\^");
					if (values == null || values.length != 2) {
						datas.put("result", false);
					} else {
						if (Long.parseLong(DCIDate.getCurrTime()) - Long.parseLong(values[1]) > 10) {
							datas.put("result", false);
						} else {
							datas.put("uid", values[0]);
							datas.put("result", true);
						}
					}
				}
			}
		}

		return datas;
	}
}
