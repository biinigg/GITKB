package com.dci.jweb.DCIClass.sftweb;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

public class DCIController {
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView ErrorEntry() {
		return new ModelAndView("logout");
	}
}
