package com.dsc.dci.jweb.funcs.system.usersyncrecv;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dci.jweb.DCIConstants.DCIWebConstants;
import com.dci.jweb.PublicLib.DCIString;

@Controller
@RequestMapping("/UserSyncRecv.dsc")
public class UserSyncRecvController {
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody
	HashMap<String, String> postMethod(HttpServletRequest request, HttpServletResponse response) {
		HashMap<String, String> result = null;
		String dcikey = request.getParameter(DCIWebConstants.getPostTagId());
		String action = request.getParameter("action");
		String counttp = request.getParameter("counttp");
		String uid = request.getParameter("uid");
		String ip = request.getParameter("ip");
		// System.out.println("recv from " + request.getLocalAddr());
		if (dcikey != null && dcikey.equals(DCIWebConstants.getPostTagValue())) {
			if (DCIString.isNullOrEmpty(action)) {
				result = new HashMap<String, String>();
			} else if (action.equals("addUser")) {
				result = new UserSyncRecv().addUser(counttp, uid, ip);
			} else if (action.equals("rmUser")) {
				result = new UserSyncRecv().removeUser(counttp, uid, ip);
			} else if (action.equals("getNum")) {
				result = new UserSyncRecv().getUserNum(counttp);
			} else if (action.equals("getMap")) {
				result = new UserSyncRecv().getUserList(counttp);
			} else if (action.equals("userExist")) {
				result = new UserSyncRecv().usrExist(counttp, uid, ip);
			} else if (action.equals("licStatus")) {
				result = new UserSyncRecv().getLicStatus();
			} else if (action.equals("MUNum")) {
				result = new UserSyncRecv().getMaxUserNum();
			} else if (action.equals("getROInfos")) {
				result = new UserSyncRecv().getROInfos();
			} else {
				result = new HashMap<String, String>();
			}
		}

		return result;
	}
}
