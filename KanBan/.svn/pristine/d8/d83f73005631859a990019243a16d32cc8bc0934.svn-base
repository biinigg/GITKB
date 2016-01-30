package com.dsc.dci.jweb.funcs.configs.sysdbconfig;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dci.jweb.DCIBeans.Database.DBInfoBean;
import com.dci.jweb.DCIConstants.DCIWebConstants;
import com.dci.jweb.DCIEnums.Server.ConfigSaveStatus;
import com.dci.jweb.PublicLib.PublicMethods;
import com.dsc.dci.jweb.pub.Singleton;

@Controller
@RequestMapping("/Configs/SysDBConfig.dsc")
public class SysDBConfigController {
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody
	HashMap<String, Object> postMethod(HttpServletRequest request, HttpServletResponse response) {
		HashMap<String, Object> datas = null;
		DBInfoBean config = null;
		String dcikey = request.getParameter(DCIWebConstants.getPostTagId());
		String action = request.getParameter("action");

		if (dcikey != null && dcikey.equals(DCIWebConstants.getPostTagValue())) {
			datas = new HashMap<String, Object>();
			if (action == null || action.equals("")) {
				boolean isvm = new PublicMethods().getVMData();
				// isvm = true; //test
				config = new SysDBConfig().getConfigDate(isvm);
				datas.put("data", config);
				datas.put("isvm", isvm);
				datas.put("isTestArea", Singleton.getInstance().isTestArea());
				datas.put("success", true);
			} else if (action.equals("save")) {
				SysDBConfig sys = new SysDBConfig();
				sys.setCurrPath(request.getSession().getServletContext().getRealPath("/"));
				ConfigSaveStatus status = sys.saveData(request.getParameter("confdata"));
				if (status == ConfigSaveStatus.ConnectionSuccess) {
					datas.put("success", true);
				} else {
					datas.put("success", false);
					if (status == ConfigSaveStatus.GuardConnectFail) {
						datas.put("errorMessage", "Guard Server Connect Fail");
					} else {
						datas.put("errorMessage", status.toString() + "</br>" + sys.getErrMsg());
					}
				}
			} else if (action.equals("getAllIP")) {
				datas.put("ips", new SysDBConfig().getAllIPs());
			}
		}

		return datas;
	}

}
