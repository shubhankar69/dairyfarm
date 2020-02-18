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

import com.dairyfarm.entity.master.BankMaster;
import com.dairyfarm.service.master.CommonService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/bankmaster")
public class BankMasterRestController {

	@Autowired
	@Qualifier("bankmasterService")
	private CommonService<BankMaster> bankService;
	
	@GetMapping("/fetch/all")
	public JSONObject getAllBankList() {
		return bankService.getAllListofData();
	}
	
	@GetMapping("/fetch/{id}")
	public JSONObject getBankMasterById(@PathVariable int id) {
		return bankService.getJsonObj(id);
	}
	
	@PostMapping("/create")
	public JSONObject createBankMaster(@RequestBody BankMaster bank) {
		return bankService.saveEntityObj(bank);
	}
	
	@PostMapping("/update")
	public JSONObject updateBankMaster(@RequestBody JSONObject bankJobj) {
		return bankService.updateEntityObj(bankJobj);
	}
	
	@PostMapping("/delete")
	public JSONObject deleteBankMaster(@RequestBody JSONObject bankJobj) {
		return bankService.deleteEntityObj(bankJobj);
	}
}
