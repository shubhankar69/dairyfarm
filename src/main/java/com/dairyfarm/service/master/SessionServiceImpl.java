package com.dairyfarm.service.master;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
import com.dairyfarm.entity.master.SessionPeriod;
import com.fasterxml.jackson.databind.ObjectMapper;


@SuppressWarnings("unchecked")
@Service
@Qualifier("sessionService")
public class SessionServiceImpl implements CommonService<SessionPeriod> {
	
	@Autowired
	@Qualifier("sessionDA")
	private CommonDA<SessionPeriod> sessionDA;
	private ObjectMapper mapper;
	private JSONObject resJson;
	
	@Override
	@Transactional
	public JSONObject getAllListofData() {
		mapper = new ObjectMapper();
		resJson = new JSONObject();
		JSONArray sessionarr = new JSONArray();
		try {
			List<SessionPeriod> sessionList = sessionDA.getEntityObjList();
			if(sessionList != null && !sessionList.isEmpty()) {
				for(SessionPeriod sessionObj : sessionList) {
					String sessionjsonString = mapper.writeValueAsString(sessionObj);
					sessionarr.add(new JSONParser().parse(sessionjsonString));
				}
				resJson.put("type", "success");
				resJson.put("msg", "success");
				resJson.put("data", sessionarr);
			} else {
				resJson.put("type", "error");
				resJson.put("msg", "no data found");
				resJson.put("data", sessionarr);
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
			SessionPeriod sessionObj = sessionDA.getEntityObj(theId);
			if(sessionObj != null) {
				String jsonStr = mapper.writeValueAsString(sessionObj);
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
	public JSONObject saveEntityObj(SessionPeriod session) {
		resJson = new JSONObject();
		try {
			List<SessionPeriod> sessionList = sessionDA.getEntityObjList();
			if(sessionList != null && !sessionList.isEmpty()) {
				int lastIndex = sessionList.size() - 1;
				SessionPeriod lastSession = sessionList.get(lastIndex);
				Date toDate = lastSession.getToDate();
				
				Calendar fromCal = Calendar.getInstance();
				fromCal.setTime(toDate);
				int fromYear = fromCal.get(Calendar.YEAR);
				fromCal.add(Calendar.DATE, 1);
				
				Calendar toCal = Calendar.getInstance();
				toCal.setTime(toDate);
				int toYear = toCal.get(Calendar.YEAR);
				toCal.add(Calendar.DATE, 1);
				toCal.add(Calendar.YEAR, 1); // to get previous year add 1
				toCal.add(Calendar.DAY_OF_MONTH, -1);
				
				session.setSessionName(fromYear+"-"+(toYear+1));
		        session.setFromDate(fromCal.getTime());
		        session.setToDate(toCal.getTime());
		        session.setStatus(1);
				session.setCreatedOn(new Date());
				sessionDA.saveEntityObj(session);
				
				resJson.put("type", "success");
				resJson.put("msg", "Data Saved Successfully..!!");
				resJson.put("data", session);
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
	public JSONObject updateEntityObj(JSONObject sessionJobj) {
		resJson = new JSONObject();
		DateFormat d = new SimpleDateFormat ("dd-MM-yyyy");
		try {
			Integer existingId = (Integer) sessionJobj.get("id");
			SessionPeriod existingSessionPeriodObj = sessionDA.getEntityObj(existingId);
			if(existingId != null && existingSessionPeriodObj != null) {
				existingSessionPeriodObj.setSessionName(sessionJobj.get("sessionName").toString());
				existingSessionPeriodObj.setFromDate(d.parse(sessionJobj.get("fromDate").toString()));
				existingSessionPeriodObj.setToDate(d.parse(sessionJobj.get("toDate").toString()));
				existingSessionPeriodObj.setUpdatedOn(new Date());
				sessionDA.updateEntityObj(existingSessionPeriodObj);
				resJson.put("type", "success");
				resJson.put("msg", "Data Updated Successfully..!!");
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
	public JSONObject deleteEntityObj(JSONObject sessionJobj) {
		resJson = new JSONObject();
		try {
			Integer existingId = (Integer) sessionJobj.get("id");
			SessionPeriod existingSessionPeriodObj = sessionDA.getEntityObj(existingId);
			if(existingId != null && existingSessionPeriodObj != null) {
				existingSessionPeriodObj.setUpdatedOn(new Date());
				existingSessionPeriodObj.setStatus(0);
				sessionDA.updateEntityObj(existingSessionPeriodObj);
				resJson.put("type", "success");
				resJson.put("msg", "Data Deleted Successfully..!!");
				resJson.put("data", existingSessionPeriodObj);
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