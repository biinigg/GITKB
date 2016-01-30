package com.dsc.dci.jweb.funcs.configs.sqleditor;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dci.jweb.DCIConstants.DCIWebConstants;
import com.dci.jweb.PublicLib.APControl;

@Controller
@RequestMapping("/Configs/SQLEditor.dsc")
public class SQLEditorController {
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody
	HashMap<String, Object> postMethod(HttpServletRequest request, HttpServletResponse response) {
		HashMap<String, Object> datas = null;

		String dcikey = request.getParameter(DCIWebConstants.getPostTagId());
		String action = request.getParameter("action");
		String uid = request.getParameter("uid");

		if (dcikey != null && dcikey.equals(DCIWebConstants.getPostTagValue())) {

			if (action == null || action.equals("")) {
				datas = new SQLEditor().getQueryColumns(
						new APControl().getUserInfoFromSession(request.getSession(), uid, "lang"),
						request.getParameter("btnid"));
			} else if (action.equals("init")) {
				datas = new SQLEditor().getInitData(new APControl().getUserInfoFromSession(request.getSession(), uid,
						"lang"));
			} else if (action.equals("save")) {
				datas = new SQLEditor().saveData(request.getParameter("datas"), request.getParameter("mode"),
						new APControl().getUserInfoFromSession(request.getSession(), uid, "lang"));
			} else if (action.equals("del")) {
				datas = new SQLEditor().deleteData(request.getParameter("datas"));
			} else if (action.equals("search")) {
				datas = new SQLEditor().getAllData(request.getParameter("page"), request.getParameter("pagesize"),
						request.getParameter("filter"),
						new APControl().getUserInfoFromSession(request.getSession(), uid, "lang"));
			} else if (action.equals("openwin") || action.equals("connlist")) {
				datas = new SQLEditor().getOpenWinData(request.getParameter("filter"), action);
			} else if (action.equals("openwinQcombo")) {
				datas = new SQLEditor().getQueryColumns(
						new APControl().getUserInfoFromSession(request.getSession(), uid, "lang"),
						request.getParameter("btnid"));
			} else if (action.equals("sqlConns")) {
				datas = new SQLEditor().getSqlConns(request.getParameter("sqlid"));
			} else if (action.equals("bodyData")) {
				datas = new SQLEditor().getBodyData(request.getParameter("keys"),
						new APControl().getUserInfoFromSession(request.getSession(), uid, "lang"));
			} else if (action.equals("bodySave")) {
				datas = new SQLEditor().saveBody(request.getParameter("datas"), request.getParameter("allAdd"));
			} else if (action.equals("bodyDelete")) {
				datas = new SQLEditor().deleteBody(request.getParameter("datas"));
			} else if (action.equals("bodyBuild")) {
				datas = new SQLEditor().getSqlColumns(request.getParameter("sql_id"),
						new APControl().getUserInfoFromSession(request.getSession(), uid, "lang"));
			} else if (action.equals("legendopenwin")) {
				datas = new SQLEditor().getAllLights(new APControl().getUserInfoFromSession(request.getSession(), uid,
						"lang"));
			} else if (action.equals("sqlcollist")) {
				datas = new SQLEditor().getAllSqls(new APControl().getUserInfoFromSession(request.getSession(), uid,
						"lang"));
			} else if (action.equals("crossdbinfo")) {
				datas = new SQLEditor().getSqlCrossDB(request.getParameter("sql_id"));
			} else if (action.equals("checkSql")) {
				datas = new SQLEditor().sqlCheck(request.getParameter("sql_text"), request.getParameter("group_by"),
						request.getParameter("order_by"), request.getParameter("conn_id"),
						new APControl().getUserInfoFromSession(request.getSession(), uid, "lang"));
			} else if (action.equals("copysql")) {
				datas = new SQLEditor().copySql(request.getParameter("sql_id"), request.getParameter("sql_name"),
						new APControl().getUserInfoFromSession(request.getSession(), uid, "lang"));
			} else if (action.equals("createLink")) {
				datas = new SQLEditor().createLink(request.getParameter("func_id"), request.getParameter("conn_id"),
						request.getParameter("linkuid"), request);
			} else if (action.equals("linkusers")) {
				datas = new SQLEditor().getAllUsers();
			} else if (action.equals("langList")) {
				datas = new SQLEditor().getLangList(request.getParameter("langtp"), request.getParameter("key"));
			} else if (action.equals("crossMain")) {
				datas = new SQLEditor().getCrossMain(request.getParameter("sql_id"));
			} else if (action.equals("getKeyColumns")) {
				datas = new SQLEditor().getKeyCols(request.getParameter("sqls"));
			}
		}

		return datas;
	}
}
