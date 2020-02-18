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
import com.dairyfarm.da.txn.PurchaseVoucherDAImpl;
import com.dairyfarm.entity.master.BankMaster;
import com.dairyfarm.entity.master.GroupMaster;
import com.dairyfarm.entity.master.PartyMaster;
import com.dairyfarm.entity.txn.PurchaseVoucher;
import com.fasterxml.jackson.databind.ObjectMapper;

@SuppressWarnings("unchecked")
@Service
@Qualifier("partymasterService")
public class PartyMasterServiceImpl implements CommonService<PartyMaster> {
	
	@Autowired
	@Qualifier("partymasterDA")
	private CommonDA<PartyMaster> partyDA;
	
	@Autowired
	@Qualifier("groupmasterDA")
	private CommonDA<GroupMaster> groupDA;
	
	@Autowired
	@Qualifier("bankmasterDA")
	private CommonDA<BankMaster> bankDA;
	
	@Autowired
	@Qualifier("purchaseVoucherDA")
	private PurchaseVoucherDAImpl pvDAImpl;
	
	private ObjectMapper mapper;
	private JSONObject resJson;

	@Override
	@Transactional
	public JSONObject getAllListofData() {
		mapper = new ObjectMapper();
		resJson = new JSONObject();
		JSONArray partyarr = new JSONArray();
		try {
			List<PartyMaster> partyList = partyDA.getEntityObjList();
			if(partyList != null && !partyList.isEmpty()) {
				for(PartyMaster partyObj : partyList) {
					String partyjsonString = mapper.writeValueAsString(partyObj);
					partyarr.add(new JSONParser().parse(partyjsonString));
				}
				resJson.put("type", "success");
				resJson.put("msg", "success");
				resJson.put("data", partyarr);
			} else {
				resJson.put("type", "error");
				resJson.put("msg", "no data found");
				resJson.put("data", partyarr);
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
			PartyMaster partyObj = partyDA.getEntityObj(theId);
			if(partyObj != null) {
				String jsonStr = mapper.writeValueAsString(partyObj);
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
	public JSONObject saveEntityObj(PartyMaster party) {
		resJson = new JSONObject();
		try {
			party.setStatus(1);
			party.setCreatedOn(new Date());
			if(party.getBankMaster().getId() == null) {
				party.setBankMaster(null);
			}
			if(party.getGroupMaster().getId() == null) {
				party.setGroupMaster(null);
			}
			partyDA.saveEntityObj(party);
			
			resJson.put("type", "success");
			resJson.put("msg", "Data Saved Successfully..!!");
			resJson.put("data", party);
		} catch(Exception e) {
			resJson.put("type", "error");
			resJson.put("msg", "server error");
			e.printStackTrace();
		}
		return resJson;
	}

	@Override
	@Transactional
	public JSONObject updateEntityObj(JSONObject partyJobj) {
		mapper = new ObjectMapper();
		resJson = new JSONObject();
		try {
			
			Integer partyId = partyJobj.get("id") != null ? Integer.parseInt(partyJobj.get("id").toString()) : 0;
			PartyMaster partymasterObj = partyDA.getEntityObj(partyId);
			
			JSONObject bankJson = partyJobj.get("bankMaster") != null ? (JSONObject)  partyJobj.get("bankMaster") : null;
			Integer bankId = bankJson != null ? Integer.parseInt(bankJson.get("id") != null ? bankJson.get("id").toString() : "0") : 0;
			BankMaster bankMasterObj = bankDA.getEntityObj(bankId);
			
			JSONObject groupJson = partyJobj.get("groupMaster") != null ? (JSONObject) partyJobj.get("groupMaster") : null;
			Integer groupId = groupJson != null ? Integer.parseInt(groupJson.get("id") != null ? groupJson.get("id").toString() : "0") : 0;
			GroupMaster groupObj = groupDA.getEntityObj(groupId);
			
			if(partyId != 0 && partymasterObj != null) {
				partymasterObj.setPlantNo(Integer.parseInt(partyJobj.get("plantNo").toString()));
				partymasterObj.setPlantName(partyJobj.get("plantName").toString());
				partymasterObj.setAddress(partyJobj.get("address").toString());
				partymasterObj.setBalance(Double.parseDouble(partyJobj.get("balance") != null ? partyJobj.get("balance").toString() : "0"));
				partymasterObj.setNickName(partyJobj.get("nickName").toString());
				partymasterObj.setPartyBankAccNo(partyJobj.get("partyBankAccNo").toString());
				partymasterObj.setPartyName(partyJobj.get("partyName").toString());
				partymasterObj.setPhone(partyJobj.get("phone").toString());
				partymasterObj.setUpdatedOn(new Date());
				if(bankMasterObj != null && bankId != 0) {
					partymasterObj.setBankMaster(bankMasterObj);
				} else {
					partymasterObj.setBankMaster(null);
				}
				if(groupObj != null && groupId != 0) {
					partymasterObj.setGroupMaster(groupObj);
				} else {
					partymasterObj.setGroupMaster(null);
				}
				partyDA.updateEntityObj(partymasterObj);
				resJson.put("type", "success");
				resJson.put("msg", "Data Updated Successfully..!!");
				resJson.put("data", partymasterObj);
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
	public JSONObject deleteEntityObj(JSONObject partyObj) {
		resJson = new JSONObject();
		try {
			Integer existingId = Integer.parseInt(partyObj.get("id").toString());
			List<PurchaseVoucher> pvList = pvDAImpl.getPurchVoucherListByPartyId(existingId);
			if(pvList == null || pvList.isEmpty()) {
				PartyMaster existingPartyObj = partyDA.getEntityObj(existingId);
				if(existingId != null && existingPartyObj != null) {
					if(existingPartyObj.getBankMaster() == null || existingPartyObj.getBankMaster().getId() == null) {
						existingPartyObj.setBankMaster(null);
					}
					if(existingPartyObj.getGroupMaster() == null || existingPartyObj.getGroupMaster().getId() == null) {
						existingPartyObj.setGroupMaster(null);
					}
					existingPartyObj.setUpdatedOn(new Date());
					existingPartyObj.setStatus(0);
					partyDA.updateEntityObj(existingPartyObj);
					resJson.put("type", "success");
					resJson.put("msg", "Data Deleted Successfully..!!");
					resJson.put("data", existingPartyObj);
				} else {
					resJson.put("type", "error");
					resJson.put("msg", "no data found");
				}
			} else {
				resJson.put("type", "error");
				resJson.put("msg", "Already tagged with purchase transaction..!!");
			}
		} catch(Exception e) {
			resJson.put("type", "error");
			resJson.put("msg", "server error");
			e.printStackTrace();
		}
		return resJson;
	}
}