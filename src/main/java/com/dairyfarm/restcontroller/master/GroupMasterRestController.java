package com.dairyfarm.restcontroller.master;

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

import com.dairyfarm.entity.master.GroupMaster;
import com.dairyfarm.service.master.CommonService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/groupmaster")
public class GroupMasterRestController {

	@Autowired
	@Qualifier("groupmasterService")
	private CommonService<GroupMaster> groupMasterService;
	
	@GetMapping("/fetch/all")
	public JSONObject getAllGroupMaster() {
		return groupMasterService.getAllListofData();
	}
	
	@GetMapping("/fetch/{id}")
	public JSONObject getGroupMasterById(@PathVariable int id) {
		return groupMasterService.getJsonObj(id);
	}
	
	@PostMapping("/create")
	public JSONObject createGroupMaster(@RequestBody GroupMaster group) {
		return groupMasterService.saveEntityObj(group);
	}
	
	@PostMapping("/update")
	public JSONObject updateGroupMaster(@RequestBody JSONObject groupJobj) {
		return groupMasterService.updateEntityObj(groupJobj);
	}
	
	@PostMapping("/delete")
	public JSONObject deleteGroupMaster(@RequestBody JSONObject bankJobj) {
		return groupMasterService.deleteEntityObj(bankJobj);
	}
}
