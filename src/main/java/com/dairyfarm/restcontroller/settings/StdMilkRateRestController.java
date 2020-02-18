package com.dairyfarm.restcontroller.settings;

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

import com.dairyfarm.entity.settings.StdMilkRate;
import com.dairyfarm.service.settings.StdMilkRateService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/stdmilkrate")
public class StdMilkRateRestController {

	@Autowired
	@Qualifier("StdMilkRateService")
	private StdMilkRateService<StdMilkRate> milkRateService;
	
	@GetMapping("/fetch/all")
	public JSONObject getAllMilkRateList() {
		return milkRateService.getAllListofData();
	}
	
	@GetMapping("/fetch/{id}")
	public JSONObject getBankMasterById(@PathVariable int id) {
		return milkRateService.getJsonObj(id);
	}
	
	@GetMapping("/fetch/{pagename}/{type}")
	public JSONObject getBankMasterBy(@PathVariable(name = "pagename") String pageName, @PathVariable(name = "type") String type) {
		return milkRateService.getJsonObj(type, pageName);
	}
	
	@GetMapping("/fetchbypagename/{pagename}")
	public JSONObject getBankMasterByPageName(@PathVariable(name = "pagename") String pageName) {
		return milkRateService.getStdMilkRateJsonObjByPageName(pageName);
	}
	
	@GetMapping("/fetchbytype/{type}")
	public JSONObject getBankMasterByType(@PathVariable(name = "type") String type) {
		return milkRateService.getStdMilkRateJsonObjByType(type);
	}
	
	@PostMapping("/update")
	public JSONObject updateBankMaster(@RequestBody JSONObject bankJobj) {
		return milkRateService.updateEntityObj(bankJobj);
	}
}