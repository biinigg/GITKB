package com.dsc.dci.jweb.funcs.lkb.layoutkanban;

import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.dci.jweb.DCIConstants.DCIWebConstants;

@Controller
@RequestMapping("/Funcs/LKB/LayoutKanBan.dsc")
public class LayoutKanBanController {
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody
	ArrayList<Object> postMethod(HttpServletRequest request, HttpServletResponse response) {
		ArrayList<Object> parameters = null;
		String funcid = request.getParameter("funcid");
		String dcikey = request.getParameter(DCIWebConstants.getPostTagId());
		String action = request.getParameter("action");
		String connid = request.getParameter("connid");

		if (dcikey != null && dcikey.equals(DCIWebConstants.getPostTagValue())) {
			if (action == null || action.equals("")) {
				parameters = new LayoutKanBan().buileData(funcid, connid);
			} else if (action.equals("getlkbinfo")) {
				parameters = new LayoutKanBan().getLKBInfo(funcid, connid);
			} else if (action.equals("refresh")) {
				parameters = new LayoutKanBan().buileReflashData(funcid, connid);
			}

		}

		return parameters;
	}

	// @RequestMapping(method = RequestMethod.GET)
	// public @ResponseBody
	// ArrayList<Object> getMethod(HttpServletRequest request,
	// HttpServletResponse response) {
	// ArrayList<Object> parameters = null;
	// String funcid = request.getParameter("funcid");
	// String lkbcolor = request.getParameter("lkbcolor");
	//
	// if (lkbcolor != null && lkbcolor.equals("1234")) {
	// parameters = new LayoutKanBan().buileReflashData(funcid);
	// } else {
	// parameters = new LayoutKanBan().buileData(funcid);
	// }
	//
	// return parameters;
	// }
}
