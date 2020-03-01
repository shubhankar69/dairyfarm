package com.dairyfarm.da.txn;

import java.util.Date;
import java.util.List;

import com.dairyfarm.da.master.CommonDA;
import com.dairyfarm.entity.txn.PurchaseVoucherDetails;

public interface PurchaseVoucherDA<E> extends CommonDA<E> {

	public List<E> getPurchVoucherListByPartyId(int partyId);
	
	public List<E> getPurchaseListFromDateToDate(Date fromDate, Date toDate);
	
	public List<E> getPurchaseListFromDateToDate(Integer sessionId, Date fromDate, Date toDate);
	
	public List<E> getPurchaseListByPartyIdFromDateToDate(Integer partyId, Date fromDate, Date toDate);
	
	public List<E> getPurchaseListByPartyIdFromDateToDate(Integer sessionId, Integer partyId, Date fromDate, Date toDate);
	
	@SuppressWarnings("rawtypes")
	public List getPurchaseReportSummary(Date fromDate, Date toDate);
	
	public void deletePVDetailsObj(PurchaseVoucherDetails pvDetails);
}
