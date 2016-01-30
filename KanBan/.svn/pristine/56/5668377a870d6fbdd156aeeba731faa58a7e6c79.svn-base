package com.dsc.dci.jweb.pub;

import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dci.jweb.DCIConstants.DCIWebConstants;
import com.dci.jweb.DCIEnums.Database.DBType;
import com.dci.jweb.PublicLib.APControl;
import com.dci.jweb.PublicLib.DCIString;
import com.dci.jweb.PublicLib.PublicMethods;

@Controller
@RequestMapping("/DCIAppDatas.dsc")
public class DCIAppDatas {
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody
	HashMap<String, DCIAppDataBean> postMethod(HttpServletRequest request, HttpServletResponse response) {
		HashMap<String, DCIAppDataBean> model = null;
		HashMap<String, String> values = null;
		String langtp = null;
		String checkcode = request.getParameter("dcicode");
		String keystr = request.getParameter("keys");
		String action = request.getParameter("action");
		String uid = request.getParameter("uid");
		String loginKey = request.getParameter("loginKey");
		String[] keys = null;
		// System.out.println("dciapp uid " + uid);
		// System.out.println("dciapp loginkey " + loginKey);

		if (DCIString.isNullOrEmpty(uid)) {
			if (!DCIString.isNullOrEmpty(loginKey)) {
				uid = PublicMethods.getUidFromKey(loginKey);
			}
		}

		if (checkcode != null && checkcode.equals("7558BE96-996B-4a34-99FE-9AC3B1478D08")) {
			Singleton s = Singleton.getInstance();
			model = new HashMap<String, DCIAppDataBean>();
			DCIAppDataBean parameters = new DCIAppDataBean();
			parameters.setDefLangType(Singleton.getInstance().getDeflang());
			if (action != null && action.equals("login")) {
				parameters.setFrowardTagValue(APConstants.getLoginpagecheckcode());
			} else {
				parameters.setFrowardTagValue(DCIWebConstants.getForwardTagValue());
			}
			parameters.setDCITagValue(DCIWebConstants.getPostTagValue());
			parameters.setDBType(DBType.StringValues());
			if (action != null && action.equals("login")) {
				if (DCIString.isNullOrEmpty(request.getParameter("langtp"))) {
					langtp = s.getDeflang();
				} else {
					langtp = request.getParameter("langtp");
				}
			} else {
				langtp = new APControl().getUserInfoFromSession(request.getSession(), uid, "lang");
			}
			parameters.setLangType(langtp);
			parameters.setCurrVersion("V" + s.getCurrVer());
			if (keystr != null && keystr.length() > 0) {
				keys = keystr.split(APConstants.getLangkeysplit());
				if (keys != null && keys.length > 0) {
					if (langtp == null || langtp.equals("")) {
						langtp = s.getDeflang();
					}

					for (int i = 0; i < keys.length; i++) {
						if (values == null) {
							values = new HashMap<String, String>();
						}
						values.put(keys[i], s.getLanguage(langtp, keys[i]));
					}
				}
				parameters.setLangValues(values);
			}

			parameters.setUserID(uid);
			model.put("parameters", parameters);
		}
		return model;
	}
}
