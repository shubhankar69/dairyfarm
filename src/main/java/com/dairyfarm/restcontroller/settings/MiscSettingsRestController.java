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

import com.dairyfarm.entity.settings.MiscSettings;
import com.dairyfarm.service.settings.MiscSettingsService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/miscsettings")
public class MiscSettingsRestController {

	@Autowired
	@Qualifier("MiscSettingsService")
	private MiscSettingsService<MiscSettings> miscSettingsService;
	
	@GetMapping("/fetch/all")
	public JSONObject getAllMiscSettingsList() {
		return miscSettingsService.getAllListofData();
	}
	
	@GetMapping("/fetch/{id}")
	public JSONObject getMiscSettingsById(@PathVariable int id) {
		return miscSettingsService.getJsonObj(id);
	}
	
	@GetMapping("/fetch/{type}/{name}")
	public JSONObject getMiscSettingsBy(@PathVariable(name = "name") String name, @PathVariable(name = "type") String type) {
		return miscSettingsService.getJsonObj(type, name);
	}
	
	@GetMapping("/fetchbyname/{name}")
	public JSONObject getMiscSettingsByPageName(@PathVariable(name = "name") String name) {
		return miscSettingsService.getMiscSettingsJsonObjByName(name);
	}
	
	@GetMapping("/fetchbytype/{type}")
	public JSONObject getMiscSettingsByType(@PathVariable(name = "type") String type) {
		return miscSettingsService.getMiscSettingsJsonObjByType(type);
	}
	
	@PostMapping("/update")
	public JSONObject updateMiscSettings(@RequestBody JSONObject miscSettingsJobj) {
		return miscSettingsService.updateEntityObj(miscSettingsJobj);
	}
}