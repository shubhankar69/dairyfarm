package com.dairyfarm.service.master;

import org.json.simple.JSONObject;

public interface CommonService<E> {

	public JSONObject getAllListofData();

	public JSONObject getJsonObj(int theId);
	
	public JSONObject saveEntityObj(E e);

	public JSONObject updateEntityObj(JSONObject json);
	
	public JSONObject deleteEntityObj(JSONObject json);
}