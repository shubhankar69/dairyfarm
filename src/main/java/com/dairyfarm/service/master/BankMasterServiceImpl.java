package com.dairyfarm.service.master;

import java.util.Date;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dairyfarm.da.master.CommonDA;
import com.dairyfarm.da.master.PartyMasterDA;
import com.dairyfarm.entity.master.BankMaster;
import com.dairyfarm.entity.master.PartyMaster;
import com.fasterxml.jackson.databind.ObjectMapper;

@SuppressWarnings("unchecked")
@Service
@Qualifier("bankmasterService")
public class BankMasterServiceImpl implements CommonService<BankMaster> {
	
	@Autowired
	@Qualifier("bankmasterDA")
	private CommonDA<BankMaster> bankDA;
	@Autowired
	@Qualifier("partymasterDA")
	private PartyMasterDA<PartyMaster> partyDA;
	private ObjectMapper mapper;
	private JSONObject resJson;

	@Override
	@Transactional
	public JSONObject getAllListofData() {
		mapper = new ObjectMapper();
		resJson = new JSONObject();
		JSONArray bankarr = new JSONArray();
		try {
			List<BankMaster> bankList = bankDA.getEntityObjList();
			if(bankList != null && !bankList.isEmpty()) {
				for(BankMaster bankObj : bankList) {
					String bankjsonString = mapper.writeValueAsString(bankObj);
					bankarr.add(new JSONParser().parse(bankjsonString));
				}
				resJson.put("type", "success");
				resJson.put("msg", "success");
				resJson.put("data", bankarr);
			} else {
				resJson.put("type", "error");
				resJson.put("msg", "no data found");
				resJson.put("data", bankarr);
			}
		} catch(Exception e) {
			resJson.put("type", "error");
			resJson.put("msg", "server error");
			e.printStackTrace();
		}
		return resJson;
	}
	
	@Override
	@Transactional
	public JSONObject getJsonObj(int theId) {
		resJson = new JSONObject();
		mapper = new ObjectMapper();
		try {
			BankMaster bankObj = bankDA.getEntityObj(theId);
			if(bankObj != null) {
				String jsonStr = mapper.writeValueAsString(bankObj);
				resJson.put("type", "success");
				resJson.put("msg", "success");
				resJson.put("data", new JSONParser().parse(jsonStr));
			} else {
				resJson.put("type", "error");
				resJson.put("msg", "no data found");
			}
		} catch(Exception e) {
			resJson.put("type", "error");
			resJson.put("msg", "server error");
			e.printStackTrace();
		}
		return resJson;
	}

	@Override
	@Transactional
	public JSONObject saveEntityObj(BankMaster bankObj) {
		resJson = new JSONObject();
		try {
			bankObj.setStatus(1);
			bankObj.setCreatedOn(new Date());
			bankDA.saveEntityObj(bankObj);
			resJson.put("type", "success");
			resJson.put("msg", "Data Saved Successfully..!!");
			resJson.put("data", bankObj);
		} catch(Exception e) {
			resJson.put("type", "error");
			resJson.put("msg", "server error");
			e.printStackTrace();
		}
		return resJson;
	}

	@Override
	@Transactional
	public JSONObject updateEntityObj(JSONObject bankJobj) {
		resJson = new JSONObject();
		try {
			Integer existingId = (Integer) bankJobj.get("id");
			BankMaster existingBankMasterkObj = bankDA.getEntityObj(existingId);
			if(existingId != null && existingBankMasterkObj != null) {
				existingBankMasterkObj.setBankName(bankJobj.get("bankName").toString());
				existingBankMasterkObj.setUpdatedOn(new Date());
				bankDA.updateEntityObj(existingBankMasterkObj);
				resJson.put("type", "success");
				resJson.put("msg", "Data Updated Successfully..!!");
				resJson.put("data", existingBankMasterkObj);
			} else {
				resJson.put("type", "error");
				resJson.put("msg", "no data found");
			}
		} catch (Exception e) {
			resJson.put("type", "error");
			resJson.put("msg", "server error");
			e.printStackTrace();
		}
		return resJson;
	}
	
	@Override
	@Transactional
	public JSONObject deleteEntityObj(JSONObject bankJobj) {
		resJson = new JSONObject();
		try {
			Integer existingBankId = (Integer) bankJobj.get("id");
			List<PartyMaster> partyList = partyDA.getPartyMasterListBy(existingBankId);
			if(partyList == null || partyList.isEmpty()) {
				BankMaster existingBankObj = bankDA.getEntityObj(existingBankId);
				if(existingBankId != null && existingBankObj != null) {
					existingBankObj.setUpdatedOn(new Date());
					existingBankObj.setStatus(0);
					bankDA.updateEntityObj(existingBankObj);
					resJson.put("type", "success");
					resJson.put("msg", "Data Deleted Successfully..!!");
					resJson.put("data", existingBankObj);
				} else {
					resJson.put("type", "error");
					resJson.put("msg", "no data found");
				}
			} else {
				resJson.put("type", "error");
				resJson.put("msg", "Already tagged with supplier..!!");
			}
		} catch(Exception e) {
			resJson.put("type", "error");
			resJson.put("msg", "server error");
			e.printStackTrace();
		}
		return resJson;
	}
}