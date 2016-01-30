package com.dsc.dci.jweb.funcs.configs.regserial;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dci.jweb.DCIConstants.DCIWebConstants;

@Controller
@RequestMapping("/Configs/RegSerial.dsc")
public class RegSerialController {
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody
	HashMap<String, Object> postMethod(HttpServletRequest request, HttpServletResponse response) {
		HashMap<String, Object> datas = null;

		String dcikey = request.getParameter(DCIWebConstants.getPostTagId());
		String action = request.getParameter("action");

		if (dcikey != null && dcikey.equals(DCIWebConstants.getPostTagValue())) {
			if (action.equals("init")) {
				datas = new RegSerial().getInitData();
			} else if (action.equals("save_serial")) {
				datas = new RegSerial().saveData(request.getParameter("serial_number"));
			} else if (action.equals("save_execute")) {
				datas = new RegSerial().saveExecuteData(request.getParameter("serial_number"),
						request.getParameter("execute_code"));
			}
		}

		return datas;
	}
}
