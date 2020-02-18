package com.dairyfarm.da.txn;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.dairyfarm.entity.txn.PurchaseVoucher;

@Repository
@Qualifier("purchaseVoucherDA")
public class PurchaseVoucherDAImpl implements PurchaseVoucherDA<PurchaseVoucher> {
	
	@Autowired
	private EntityManager sessionFactory;
	
	@Override
	public List<PurchaseVoucher> getEntityObjList() {
		Session currentSession = sessionFactory.unwrap(Session.class);
		Query<PurchaseVoucher> theQuery = currentSession.createNamedQuery("PurchaseVoucher.findByAll", PurchaseVoucher.class);
		return theQuery.getResultList();
	}
	
	@Override
	public PurchaseVoucher getEntityObj(int theId) {
		Session currentSession = sessionFactory.unwrap(Session.class);
		Query<PurchaseVoucher> q = currentSession.createNamedQuery("PurchaseVoucher.findById", PurchaseVoucher.class);
		q.setParameter("id", theId);
		return q.getResultList().isEmpty() ? null : q.getResultList().get(0);
	}
	
	@Override
	public void saveEntityObj(PurchaseVoucher pv) {
		Session currentSession = sessionFactory.unwrap(Session.class);
		currentSession.save(pv);
	}
	
	@Override
	public void updateEntityObj(PurchaseVoucher e) {
	}
	
	@Override
	public void deleteEntityObj(PurchaseVoucher pv) {
		Session currentSession = sessionFactory.unwrap(Session.class);
		currentSession.delete(pv);
	}
	
	@Override
	public List<PurchaseVoucher> getPurchVoucherListByPartyId(int partyId) {
		Session currentSession = sessionFactory.unwrap(Session.class);
		List<PurchaseVoucher> pm = null;
		try {
			Query<PurchaseVoucher> q = currentSession.createNamedQuery("PurchaseVoucher.findByPartyId", PurchaseVoucher.class);
			q.setParameter("partyId", partyId);
			pm = q.getResultList();
		} catch(NoResultException e) {
			pm = null;
		}
		return pm;
	}
	
	@Override
	public List<PurchaseVoucher> getPurchaseListFromDateToDate(Date fromDate, Date toDate) {
		Session currentSession = sessionFactory.unwrap(Session.class);
		Query<PurchaseVoucher> theQuery = currentSession.createQuery("from PurchaseVoucher where billDate between :fDate and :tDate", PurchaseVoucher.class);
		theQuery.setParameter("fDate", fromDate);
		theQuery.setParameter("tDate", toDate);
		return theQuery.getResultList();
	}
	
	@Override
	public List<PurchaseVoucher> getPurchaseListFromDateToDate(Integer sessionId, Date fromDate, Date toDate) {
		Session currentSession = sessionFactory.unwrap(Session.class);
		Query<PurchaseVoucher> theQuery = currentSession.createQuery("from PurchaseVoucher where sessionId = :sessionId and billDate between :fDate and :tDate", PurchaseVoucher.class);
		theQuery.setParameter("sessionId", sessionId);
		theQuery.setParameter("fDate", fromDate);
		theQuery.setParameter("tDate", toDate);
		return theQuery.getResultList();
	}
	
	@Override
	public List<PurchaseVoucher> getPurchaseListByPartyIdFromDateToDate(Integer partyId, Date fromDate, Date toDate) {
		Session currentSession = sessionFactory.unwrap(Session.class);
		Query<PurchaseVoucher> theQuery = currentSession.createQuery("from PurchaseVoucher where partyId = :partyId and billDate between :fDate and :tDate", PurchaseVoucher.class);
		theQuery.setParameter("partyId", partyId);
		theQuery.setParameter("fDate", fromDate);
		theQuery.setParameter("tDate", toDate);
		return theQuery.getResultList();
	}
	
	@Override
	public List<PurchaseVoucher> getPurchaseListByPartyIdFromDateToDate(Integer sessionId, Integer partyId, Date fromDate, Date toDate) {
		Session currentSession = sessionFactory.unwrap(Session.class);
		Query<PurchaseVoucher> theQuery = currentSession.createQuery("from PurchaseVoucher where sessionId = :sessionId and partyId = :partyId and billDate between :fDate and :tDate", PurchaseVoucher.class);
		theQuery.setParameter("sessionId", sessionId);
		theQuery.setParameter("partyId", partyId);
		theQuery.setParameter("fDate", fromDate);
		theQuery.setParameter("tDate", toDate);
		return theQuery.getResultList();
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public List getPurchaseReportSummary(Date fromDate, Date toDate) {
		Session currentSession = sessionFactory.unwrap(Session.class);
		String purchaseSummQuery = 
			"select p.partyName, sum(pv.totalQty), sum(pv.totalMilkprice), sum(pv.totalKgfat), sum(pv.totalKgsnf), " + 
			"sum(pv.commissionValue), sum(pv.splIncentiveValue), sum(pv.headLoadValue), sum(pv.sourMilkValue), " +
		    "sum(pv.addAdvanceValue), sum(pv.testEquipMentValue), sum(pv.lateArrivalValue), sum(pv.arrearValue), " +
		    "sum(pv.adjustmentValue), sum(pv.addOthersValue), sum(pv.deductAdvanceValue), sum(pv.cattleFeedValue), " +
		    "sum(pv.MTEValue), sum(pv.fodderValue), sum(pv.cowLoanValue), sum(pv.vaccineValue), sum(pv.shareValue), " +
		    "sum(pv.deductOthersValue), sum(pv.totalAdditions), sum(pv.totalDeductions), sum(pv.billTotal) " +
			"from dairyfarm.purchase_voucher pv " + 
			"INNER JOIN dairyfarm.party_master p " + 
			"ON pv.partyId = p.id " + 
			"where (pv.billDate between ?1 and ?2) " + 
			"group by pv.partyId " + 
			"order by p.partyName";
		Query q = currentSession.createNativeQuery(purchaseSummQuery);
		q.setParameter(1, fromDate);
		q.setParameter(2, toDate);
		return q.getResultList();
	}
	
}