package com.dairyfarm.service.txn;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONObject;

import com.dairyfarm.entity.txn.PurchaseVoucher;
import com.dairyfarm.service.master.CommonService;

@SuppressWarnings("rawtypes")
public interface PurchaseVoucherService<E> extends CommonService<E> {
	
	public E getPurchaseVoucherObj(int theId);
	
	public List<E> getPurchaseVoucherObjs(List<Integer> ids);
	
	public List<HashMap> getPurchaseReportSummary(String fromDate, String toDate) throws ParseException;

	public JSONObject getPurchaseListBy(Integer sessionId, String fromDate, String toDate) throws ParseException;
	
	public JSONObject getPurchaseListBy(Integer sessionId, Integer partyId, String fromDate, String toDate) throws ParseException;
	
	public JSONObject getPurchaseVoucherBy(Integer sessionId, Integer billno) throws ParseException;
	
	public List<HashMap> getPurchaseReportDetailsBy(String fromDate, String toDate) throws ParseException;
	
	public List<HashMap> getPurchaseReportDetailsBy(String partyIds, String fromDate, String toDate) throws ParseException;
	
	public JSONObject saveEntityPurchaseObj(PurchaseVoucher pv, Integer sessionId);
	
	public JSONObject multiDeleteEntityObj(String ids);

	public Integer getMaxBillno(Integer sessionId);

	public JSONObject getAllListofData(Integer sessionID);

	public JSONObject getPurchaseListByPeriodDate(Integer sessionId, String fromDate, String toDate) throws ParseException;

	public JSONObject getPurchaseListByPartyPeriod(Integer sessionId, Integer partyId, String fromDate, String toDate)
			throws ParseException;

	public void savePurchaseVoucherObj(PurchaseVoucher pv);
}