package com.dairyfarm.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dairyfarm.entity.master.SessionPeriod;

@Controller
public class ReactController {
	
//	@RequestMapping(value = "/{path:\\w+}/**{path:?!(\\.js|\\.css)$}", method = RequestMethod.GET)
//	@RequestMapping(value = "/**/{path:[^\\.]*}", method = RequestMethod.GET) 
//	public String indexHtml() {
//		return "forward:/";
//	}
	
	@RequestMapping(value = "/", method = RequestMethod.POST) 
	public String indexHtml(@ModelAttribute("sessionObj") SessionPeriod session, HttpSession sessionObj) {
		Integer sessionId = Integer.parseInt(sessionObj.getAttribute("sessionId") != null ? sessionObj.getAttribute("sessionId").toString() : "0");
		if(session == null) {
			return "index";
		} else if(sessionId > 0) {
			return "reactindex";
		} else if(session.getId() == null) {
			return "index";
		} else if(session.getId() > 0) {
			sessionObj.setAttribute("sessionId", session.getId());
			return "reactindex";
		}  else {
			return "index";
		}
	}
	
	@RequestMapping(value = "/index", method = RequestMethod.GET) 
	public String index(Model theModel, HttpSession sessionObj) {
		Integer sessionId = Integer.parseInt(sessionObj.getAttribute("sessionId") != null ? sessionObj.getAttribute("sessionId").toString() : "0");
		if(sessionId > 0) {
			return "reactindex";
		} else {
			return "/index";
		}
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String index(HttpSession sessionObj) {
		if(sessionObj != null) {
			sessionObj.invalidate();
		}
		return "/index";
	}
}