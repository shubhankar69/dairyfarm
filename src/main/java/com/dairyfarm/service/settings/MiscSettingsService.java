package com.dairyfarm.service.settings;

import org.json.simple.JSONObject;

import com.dairyfarm.service.master.CommonService;

public interface MiscSettingsService<E> extends CommonService<E> {
	
	public JSONObject getJsonObj(String type, String name);
	
	public JSONObject getMiscSettingsJsonObjByName(String name);
	
	public JSONObject getMiscSettingsJsonObjByType(String type);

}