package com.dairyfarm.restcontroller.master;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dairyfarm.entity.master.PartyMaster;
import com.dairyfarm.service.master.CommonService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/partymaster")
public class PartyMasterRestController {

	@Autowired
	@Qualifier("partymasterService")
	private CommonService<PartyMaster> partyMasterService;
	private ObjectMapper mapper;
	
	@GetMapping("/fetch/all")
	public JSONObject getAllPartyMaster() {
		return partyMasterService.getAllListofData();
	}
	
	@GetMapping("/fetch/{id}")
	public JSONObject getPartyMasterById(@PathVariable int id) {
		return partyMasterService.getJsonObj(id);
	}
	
	@PostMapping("/create")
	public JSONObject createPartyMaster(@RequestBody PartyMaster party) {
		return partyMasterService.saveEntityObj(party);
	}
	
	@PostMapping("/update")
	public JSONObject updatePartyMaster(@RequestBody PartyMaster partyobj) throws JsonProcessingException, ParseException {
		mapper = new ObjectMapper();
		String partyJsonString = mapper.writeValueAsString(partyobj);
		JSONObject partyJson = (JSONObject) new JSONParser().parse(partyJsonString);
		return partyMasterService.updateEntityObj(partyJson);
	}
	
	@PostMapping("/delete")
	public JSONObject deletePartyMaster(@RequestBody JSONObject bankJobj) {
		return partyMasterService.deleteEntityObj(bankJobj);
	}
}