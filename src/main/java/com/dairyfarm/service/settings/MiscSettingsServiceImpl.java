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

import com.dairyfarm.da.settings.MiscSettingsDA;
import com.dairyfarm.entity.settings.MiscSettings;
import com.fasterxml.jackson.databind.ObjectMapper;

@SuppressWarnings("unchecked")
@Service
@Qualifier("MiscSettingsService")
public class MiscSettingsServiceImpl implements MiscSettingsService<MiscSettings> {

	@Autowired
	@Qualifier("MiscSettingsDA")
	private MiscSettingsDA<MiscSettings> miscSettingsDA;
	private ObjectMapper mapper;
	
	@Override
	@Transactional
	public JSONObject getAllListofData() {
		mapper = new ObjectMapper();
		JSONObject resJson = new JSONObject();
		JSONArray miscSettingsarr = new JSONArray();
		try {
			List<MiscSettings> miscSettingsList = miscSettingsDA.getEntityObjList();
			if(miscSettingsList != null && !miscSettingsList.isEmpty()) {
				for(MiscSettings miscSettingsObj : miscSettingsList) {
					String miscSettingsjsonString = mapper.writeValueAsString(miscSettingsObj);
					miscSettingsarr.add(new JSONParser().parse(miscSettingsjsonString));
				}
				resJson.put("type", "success");
				resJson.put("msg", "success");
				resJson.put("data", miscSettingsarr);
			} else {
				resJson.put("type", "error");
				resJson.put("msg", "no data found");
				resJson.put("data", miscSettingsarr);
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
	public JSONObject saveEntityObj(MiscSettings e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional
	public JSONObject updateEntityObj(JSONObject miscSettingsJson) {
		JSONObject resJson = new JSONObject();
		try {
			String type = (String) miscSettingsJson.get("typeName");
			String name = (String) miscSettingsJson.get("name");
			MiscSettings existingMiscSettingsObj = miscSettingsDA.getEntityObj(type, name);
			if(existingMiscSettingsObj != null) {
				existingMiscSettingsObj.setTypeName(miscSettingsJson.get("typeName").toString());
				existingMiscSettingsObj.setName(miscSettingsJson.get("name").toString());
				existingMiscSettingsObj.setDescription(miscSettingsJson.get("description").toString());
				existingMiscSettingsObj.setValue(Double.parseDouble(miscSettingsJson.get("value").toString()));
				existingMiscSettingsObj.setOperation(miscSettingsJson.get("operation").toString());
				existingMiscSettingsObj.setOperationOn(miscSettingsJson.get("operationOn").toString());
				existingMiscSettingsObj.setUpdatedOn(new Date());
				miscSettingsDA.updateEntityObj(existingMiscSettingsObj);
				resJson.put("type", "success");
				resJson.put("msg", "Data Updated Successfully..!!");
				resJson.put("data", existingMiscSettingsObj);
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
	
	@Override
	@Transactional
	public JSONObject getJsonObj(String type, String name) {
		mapper = new ObjectMapper();
		JSONObject resJson = new JSONObject();
		try {
			MiscSettings miscSettingsObj = miscSettingsDA.getEntityObj(type, name);
			if(miscSettingsObj != null) {
				String miscSettingsJsonString = mapper.writeValueAsString(miscSettingsObj);
				JSONObject miscSettingsJsonObj = (JSONObject) new JSONParser().parse(miscSettingsJsonString);
				resJson.put("type", "success");
				resJson.put("msg", "success");
				resJson.put("data", miscSettingsJsonObj);
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
	public JSONObject getMiscSettingsJsonObjByName(String name) {
		mapper = new ObjectMapper();
		JSONObject resJson = new JSONObject();
		JSONArray miscSettingsarr = new JSONArray();
		try {
			List<MiscSettings> miscSettingsList = miscSettingsDA.getMiscSettingsByName(name);
			if(miscSettingsList != null) {
				for(MiscSettings miscSettingsObj : miscSettingsList) {
					String miscSettingsjsonString = mapper.writeValueAsString(miscSettingsObj);
					miscSettingsarr.add(new JSONParser().parse(miscSettingsjsonString));
				}
				resJson.put("type", "success");
				resJson.put("msg", "success");
				resJson.put("data", miscSettingsarr);
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
	public JSONObject getMiscSettingsJsonObjByType(String type) {
		mapper = new ObjectMapper();
		JSONObject resJson = new JSONObject();
		JSONArray miscSettingsarr = new JSONArray();
		try {
			List<MiscSettings> miscSettingsList = miscSettingsDA.getMiscSettingsByType(type);
			if(miscSettingsList != null) {
				for(MiscSettings miscSettingsObj : miscSettingsList) {
					String miscSettingsjsonString = mapper.writeValueAsString(miscSettingsObj);
					miscSettingsarr.add(new JSONParser().parse(miscSettingsjsonString));
				}
				resJson.put("type", "success");
				resJson.put("msg", "success");
				resJson.put("data", miscSettingsarr);
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
}