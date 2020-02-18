package com.dairyfarm.service.settings;

import org.json.simple.JSONObject;

import com.dairyfarm.service.master.CommonService;

public interface StdMilkRateService<E> extends CommonService<E> {
	
	public JSONObject getJsonObj(String type, String pageName);
	
	public JSONObject getStdMilkRateJsonObjByPageName(String pageName);
	
	public JSONObject getStdMilkRateJsonObjByType(String type);
}