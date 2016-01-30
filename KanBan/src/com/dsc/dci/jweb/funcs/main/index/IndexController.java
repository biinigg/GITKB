package com.dsc.dci.jweb.funcs.main.index;

import java.util.ArrayList;
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
import com.dsc.dci.jweb.pub.APPubMethods;

@Controller
@RequestMapping("/Funcs/Main/Index.dsc")
public class IndexController {
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody
	HashMap<String, ArrayList<HashMap<String, String>>> postMethod(HttpServletRequest request,
			HttpServletResponse response) {
		APControl apc = null;
		HashMap<String, ArrayList<HashMap<String, String>>> parameters = null;
		String dcikey = request.getParameter(DCIWebConstants.getPostTagId());
		String action = request.getParameter("action");
		String connid = request.getParameter("connid");
		String node = request.getParameter("node");
		String uid = request.getParameter("uid");
		HttpSession session = request.getSession();
		Index index = null;

		if (dcikey != null && dcikey.equals(DCIWebConstants.getPostTagValue())) {
			apc = new APControl();
			// System.out.println(uid);
			// System.out.println(apc.getUserInfoFromSession(session, uid,
			// "user_id"));
			//index = new Index(apc.getUserInfoFromSession(session, "user_id"));
			index = new Index(uid);

			if (action.equals("init")) {
				parameters = index.initIndex(session);
			} else if (action.equals("tree")) {
				parameters = index.getTreeNode(connid, apc.getUserInfoFromSession(session, uid, "lang"), node, "func");
				// parameters = index.getTreeNode(connid,
				// apc.getUserInfoFromSession(session, "lang"), node, "func");
			} else if (action.equals("favor")) {
				parameters = index.getTreeNode(connid, apc.getUserInfoFromSession(session, uid, "lang"), node, "favor");
				// parameters = index.getTreeNode(connid,
				// apc.getUserInfoFromSession(session, "lang"), node, "favor");
			} else if (action.equals("save")) {
				parameters = new APPubMethods().getResult(index.saveTreeDir(connid, request.getParameter("treeArr"),
						"func"));
			} else if (action.equals("savelang")) {
				parameters = new APPubMethods().getResult(index.saveCusLang(
						apc.getUserInfoFromSession(session, uid, "lang"), request.getParameter("langkey"),
						request.getParameter("langvalue"))
						&& index.saveTreeDir(connid, request.getParameter("treeArr"), "func"));
			} else if (action.equals("clean")) {
				parameters = new APPubMethods().getResult(index.cleanCusTree(connid, "func"));
			} else if (action.equals("savefavor")) {
				parameters = new APPubMethods().getResult(index.saveTreeDir(connid, request.getParameter("treeArr"),
						"favor"));
			}else if (action.equals("addfavor")) {
				parameters = new APPubMethods().getResult(index.saveTreeDir(connid, request.getParameter("treeArr"),
						"addfavor"));
			}  else if (action.equals("cleanfavor")) {
				parameters = new APPubMethods().getResult(index.cleanCusTree(connid, "favor"));
			} else if (action.equals("savelangfavor")) {
				parameters = new APPubMethods().getResult(index.saveCusLang(
						apc.getUserInfoFromSession(session, uid, "lang"), request.getParameter("langkey"),
						request.getParameter("langvalue"))
						&& index.saveTreeDir(connid, request.getParameter("treeArr"), "favor"));
			} else if (action.equals("kanbanCheck")) {
				parameters = index.checkKanban(connid, request.getParameter("func_id"));
			} else if (action.equals("dblist")) {
				parameters = index.getDBList(apc.getUserInfoFromSession(session, uid, "lang"));
			} else if (action.equals("pageTaskGap")) {
				parameters = index.getPageTaskGap();
			} else if (action.equals("marqueeData")) {
				parameters = index.getMarqueeData();
			}else if (action.equals("hasCusTree")) {
				parameters = index.checkCusTree(connid, uid);
			}
		}

		return parameters;
	}
}
