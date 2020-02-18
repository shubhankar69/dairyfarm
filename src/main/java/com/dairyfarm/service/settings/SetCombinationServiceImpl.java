package com.dairyfarm.service.settings;

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
import com.dairyfarm.entity.settings.SetCombination;
import com.dairyfarm.service.master.CommonService;
import com.fasterxml.jackson.databind.ObjectMapper;

@SuppressWarnings("unchecked")
@Service
@Qualifier("SetCombinationService")
public class SetCombinationServiceImpl implements CommonService<SetCombination> {

	@Autowired
	@Qualifier("SetCombinationDA")
	private CommonDA<SetCombination> commonDA;
	private ObjectMapper mapper;
	
	@Override
	@Transactional
	public JSONObject getAllListofData() {
		mapper = new ObjectMapper();
		JSONObject resJson = new JSONObject();
		JSONArray setCombinationArr = new JSONArray();
		try {
			List<SetCombination> setComboList = commonDA.getEntityObjList();
			if(setComboList != null && !setComboList.isEmpty()) {
				for(SetCombination setComboListObj : setComboList) {
					String setComboListString = mapper.writeValueAsString(setComboListObj);
					setCombinationArr.add(new JSONParser().parse(setComboListString));
				}
				resJson.put("type", "success");
				resJson.put("msg", "success");
				resJson.put("data", setCombinationArr);
			} else {
				resJson.put("type", "error");
				resJson.put("msg", "no data found");
				resJson.put("data", setCombinationArr);
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional
	public JSONObject saveEntityObj(SetCombination e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional
	public JSONObject updateEntityObj(JSONObject json) {
		JSONObject resJson = new JSONObject();
		try {
			Integer id = Integer.parseInt(json.get("id").toString());
			SetCombination existingSetCombinationObj = commonDA.getEntityObj(id);
			if(existingSetCombinationObj != null) {
				existingSetCombinationObj.setSetStandard(json.get("setStandard").toString());
				existingSetCombinationObj.setSetRate(json.get("setRate").toString());
				existingSetCombinationObj.setUpdatedOn(new Date());
				commonDA.updateEntityObj(existingSetCombinationObj);
				resJson.put("type", "success");
				resJson.put("msg", "Data Updated Successfully..!!");
				resJson.put("data", existingSetCombinationObj);
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
	public JSONObject deleteEntityObj(JSONObject json) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
