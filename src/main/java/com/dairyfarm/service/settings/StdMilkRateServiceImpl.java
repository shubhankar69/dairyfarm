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

import com.dairyfarm.da.settings.StdMilkRateDA;
import com.dairyfarm.entity.settings.StdMilkRate;
import com.fasterxml.jackson.databind.ObjectMapper;

@SuppressWarnings("unchecked")
@Service
@Qualifier("StdMilkRateService")
public class StdMilkRateServiceImpl implements StdMilkRateService<StdMilkRate> {
	
	@Autowired
	@Qualifier("stdmilkrateDA")
	private StdMilkRateDA<StdMilkRate> milkRateDA;
	private ObjectMapper mapper;

	
	@Override
	@Transactional
	public JSONObject getAllListofData() {
		mapper = new ObjectMapper();
		JSONObject resJson = new JSONObject();
		JSONArray milRatearr = new JSONArray();
		try {
			List<StdMilkRate> milkRateList = milkRateDA.getEntityObjList();
			if(milkRateList != null && !milkRateList.isEmpty()) {
				for(StdMilkRate milRateObj : milkRateList) {
					String milkRatejsonString = mapper.writeValueAsString(milRateObj);
					milRatearr.add(new JSONParser().parse(milkRatejsonString));
				}
				resJson.put("type", "success");
				resJson.put("msg", "success");
				resJson.put("data", milRatearr);
			} else {
				resJson.put("type", "error");
				resJson.put("msg", "no data found");
				resJson.put("data", milRatearr);
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
		mapper = new ObjectMapper();
		JSONObject resJson = new JSONObject();
		try {
			StdMilkRate milkRateObj = milkRateDA.getEntityObj(theId);
			if(milkRateObj != null) {
				String milkRateJsonString = mapper.writeValueAsString(milkRateObj);
				JSONObject milkJsonObj = (JSONObject) new JSONParser().parse(milkRateJsonString);
				resJson.put("type", "success");
				resJson.put("msg", "success");
				resJson.put("data", milkJsonObj);
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
	public JSONObject getJsonObj(String type, String pageName) {
		mapper = new ObjectMapper();
		JSONObject resJson = new JSONObject();
		try {
			StdMilkRate milkRateObj = milkRateDA.getEntityObj(type, pageName);
			if(milkRateObj != null) {
				String milkRateJsonString = mapper.writeValueAsString(milkRateObj);
				JSONObject milkJsonObj = (JSONObject) new JSONParser().parse(milkRateJsonString);
				resJson.put("type", "success");
				resJson.put("msg", "success");
				resJson.put("data", milkJsonObj);
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
	public JSONObject getStdMilkRateJsonObjByPageName(String pageName) {
		mapper = new ObjectMapper();
		JSONObject resJson = new JSONObject();
		JSONArray milRatearr = new JSONArray();
		try {
			List<StdMilkRate> milkRateList = milkRateDA.getStdMilkRateByPageName(pageName);
			if(milkRateList != null) {
				for(StdMilkRate milRateObj : milkRateList) {
					String milkRatejsonString = mapper.writeValueAsString(milRateObj);
					milRatearr.add(new JSONParser().parse(milkRatejsonString));
				}
				resJson.put("type", "success");
				resJson.put("msg", "success");
				resJson.put("data", milRatearr);
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
	public JSONObject getStdMilkRateJsonObjByType(String type) {
		mapper = new ObjectMapper();
		JSONObject resJson = new JSONObject();
		JSONArray milRatearr = new JSONArray();
		try {
			List<StdMilkRate> milkRateList = milkRateDA.getStdMilkRateByPageName(type);
			if(milkRateList != null) {
				for(StdMilkRate milRateObj : milkRateList) {
					String milkRatejsonString = mapper.writeValueAsString(milRateObj);
					milRatearr.add(new JSONParser().parse(milkRatejsonString));
				}
				resJson.put("type", "success");
				resJson.put("msg", "success");
				resJson.put("data", milRatearr);
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
	public JSONObject saveEntityObj(StdMilkRate e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional
	public JSONObject updateEntityObj(JSONObject milkRatejson) {
		JSONObject resJson = new JSONObject();
		try {
			String type = (String) milkRatejson.get("type");
			String pageName = (String) milkRatejson.get("pageName");
			StdMilkRate existingStdMilkRateObj = milkRateDA.getEntityObj(type, pageName);
			if(existingStdMilkRateObj != null) {
				existingStdMilkRateObj.setBadFat(Double.parseDouble(milkRatejson.get("badFat").toString()));
				existingStdMilkRateObj.setBadSnf(Double.parseDouble(milkRatejson.get("badSnf").toString()));
				existingStdMilkRateObj.setGoodSnf(Double.parseDouble(milkRatejson.get("goodSnf").toString()));
				existingStdMilkRateObj.setGoodFat(Double.parseDouble(milkRatejson.get("goodFat").toString()));
				existingStdMilkRateObj.setUpdatedOn(new Date());
				milkRateDA.updateEntityObj(existingStdMilkRateObj);
				resJson.put("type", "success");
				resJson.put("msg", "Data Updated Successfully..!!");
				resJson.put("data", existingStdMilkRateObj);
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