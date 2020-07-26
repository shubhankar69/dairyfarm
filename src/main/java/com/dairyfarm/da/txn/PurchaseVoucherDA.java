package com.dairyfarm.da.txn;

import java.util.Date;
import java.util.List;

import com.dairyfarm.da.master.CommonDA;
import com.dairyfarm.entity.txn.PurchaseVoucher;
import com.dairyfarm.entity.txn.PurchaseVoucherDetails;

public interface PurchaseVoucherDA<E> extends CommonDA<E> {
	
	public List<E> getEntityObjs(List<Integer> ids);

	public List<E> getPurchVoucherListByPartyId(int partyId);
	
	public List<E> getPurchaseListFromDateToDate(Date fromDate, Date toDate);
	
	public List<E> getPurchaseListFromDateToDate(Integer sessionId, Date fromDate, Date toDate);
	
	public List<E> getPurchaseListByPartyIdFromDateToDate(Integer partyId, Date fromDate, Date toDate);
	
	public List<E> getPurchaseListByPartyIdFromDateToDate(Integer sessionId, Integer partyId, Date fromDate, Date toDate);
	
	public List<E> getPurchaseVoucherByBillno(Integer sessionId, Integer billNo);
	
	@SuppressWarnings("rawtypes")
	public List getPurchaseReportSummary(Date fromDate, Date toDate);
	
	public void deletePVDetailsObj(PurchaseVoucherDetails pvDetails);

	public Integer getMaxSerialId();

	public List<E> getEntityObjList(Integer sessionId);

	public List<PurchaseVoucher> getPurchaseListPeriodFromDateToDate(Integer sessionId, Date fromDate, Date toDate);

	public List<PurchaseVoucher> getPurchaseListByPartyIdPeriodFromDateToDate(Integer sessionId, Integer partyId,
			Date fromDate, Date toDate);
}
