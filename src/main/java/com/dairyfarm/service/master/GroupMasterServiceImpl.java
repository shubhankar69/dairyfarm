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
import com.dairyfarm.entity.master.GroupMaster;
import com.dairyfarm.entity.master.PartyMaster;
import com.fasterxml.jackson.databind.ObjectMapper;

@SuppressWarnings("unchecked")
@Service
@Qualifier("groupmasterService")
public class GroupMasterServiceImpl implements CommonService<GroupMaster> {

	@Autowired
	@Qualifier("groupmasterDA")
	private CommonDA<GroupMaster> groupDA;
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
		JSONArray groupMasterArr = new JSONArray();
		try {
			List<GroupMaster> groupMasterList = groupDA.getEntityObjList();
			if(groupMasterList != null && !groupMasterList.isEmpty()) {
				for(GroupMaster groupMasterObj : groupMasterList) {
					String groupMasterJsonString = mapper.writeValueAsString(groupMasterObj);
					groupMasterArr.add(new JSONParser().parse(groupMasterJsonString));
				}
				resJson.put("type", "success");
				resJson.put("msg", "success");
				resJson.put("data", groupMasterArr);
			} else {
				resJson.put("type", "error");
				resJson.put("msg", "no data found");
				resJson.put("data", groupMasterArr);
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
			GroupMaster groupObj = groupDA.getEntityObj(theId);
			if(groupObj != null) {
				String jsonStr = mapper.writeValueAsString(groupObj);
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
	public JSONObject saveEntityObj(GroupMaster groupMaster) {
		resJson = new JSONObject();
		try {
			groupMaster.setStatus(1);
			groupMaster.setCreatedOn(new Date());
			groupDA.saveEntityObj(groupMaster);
			resJson.put("type", "success");
			resJson.put("msg", "Data Saved Successfully..!!");
			resJson.put("data", groupMaster);
		} catch(Exception e) {
			resJson.put("type", "error");
			resJson.put("msg", "server error");
			e.printStackTrace();
		}
		return resJson;
	}

	@Override
	@Transactional
	public JSONObject updateEntityObj(JSONObject groupJson) {
		resJson = new JSONObject();
		try {
			Integer existingId = (Integer) groupJson.get("id");
			GroupMaster existingGroupMasterObj = groupDA.getEntityObj(existingId);
			if(existingId != null && existingGroupMasterObj != null) {
				existingGroupMasterObj.setGroupName(groupJson.get("groupName").toString());
				existingGroupMasterObj.setUpdatedOn(new Date());
				groupDA.updateEntityObj(existingGroupMasterObj);
				resJson.put("type", "success");
				resJson.put("msg", "Data Updated Successfully..!!");
				resJson.put("data", existingGroupMasterObj);
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
	public JSONObject deleteEntityObj(JSONObject groupJson) {
		resJson = new JSONObject();
		try {
			Integer existingId = (Integer) groupJson.get("id");
			List<PartyMaster> partyList = partyDA.getPartyMasterListBy(existingId);
			if(partyList == null || partyList.isEmpty()) {
				GroupMaster existingGroupMasterObj = groupDA.getEntityObj(existingId);
				if(existingId != null && existingGroupMasterObj != null) {
					existingGroupMasterObj.setUpdatedOn(new Date());
					existingGroupMasterObj.setStatus(0);
					groupDA.updateEntityObj(existingGroupMasterObj);
					resJson.put("type", "success");
					resJson.put("msg", "Data Deleted Successfully..!!");
					resJson.put("data", existingGroupMasterObj);
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
