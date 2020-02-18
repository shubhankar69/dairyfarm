package com.dairyfarm.restcontroller.settings;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dairyfarm.entity.settings.SetCombination;
import com.dairyfarm.service.master.CommonService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/setcombination")
public class SetCombinationRestController {
	
	@Autowired
	@Qualifier("SetCombinationService")
	private CommonService<SetCombination> commonService;
	
	@GetMapping("/fetch/all")
	public JSONObject getAllMiscSettingsList() {
		return commonService.getAllListofData();
	}
	
	@PostMapping("/update")
	public JSONObject updateMiscSettings(@RequestBody JSONObject miscSettingsJobj) {
		return commonService.updateEntityObj(miscSettingsJobj);
	}
}
