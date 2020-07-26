package com.dairyfarm.restcontroller.master;

import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dairyfarm.entity.master.SessionPeriod;
import com.dairyfarm.service.master.CommonService;

/**
 * 
 * @author sdutt
 *
 */

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/sessionperiod")
public class SessionRestController {
	
	@Autowired
	@Qualifier("sessionService")
	private CommonService<SessionPeriod> sessionService;
	
	@GetMapping("/fetch/all")
	public JSONObject getAllSession() {
		return sessionService.getAllListofData();
	}
	
	@GetMapping("/fetch/{id}")
	public JSONObject getSessionById(@PathVariable int id) {
		return sessionService.getJsonObj(id);
	}
	
	@GetMapping("/fetch/activesession")
	public Integer getActiveSession(HttpSession session) {
		return Integer.parseInt(session.getAttribute("sessionId") != null ? session.getAttribute("sessionId").toString() : "0");
	}
	
	@PostMapping("/create")
	public JSONObject createSessionPeriod() {
		SessionPeriod session = new SessionPeriod();
		return sessionService.saveEntityObj(session);
	}
	
	@PostMapping("/update")
	public JSONObject updateSessionPeriod(@RequestBody JSONObject sessionJobj) {
		return sessionService.updateEntityObj(sessionJobj);
	}
	
	@PostMapping("/delete")
	public JSONObject deleteSessionPeriod(@RequestBody JSONObject sessionJobj) {
		return sessionService.deleteEntityObj(sessionJobj);
	}
}