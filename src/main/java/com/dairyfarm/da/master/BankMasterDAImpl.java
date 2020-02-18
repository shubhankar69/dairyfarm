package com.dairyfarm.da.master;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.dairyfarm.entity.master.BankMaster;

@Repository
@Qualifier("bankmasterDA")
public class BankMasterDAImpl implements CommonDA<BankMaster> {

	@Autowired
	private EntityManager sessionFactory;
			
	@Override
	public List<BankMaster> getEntityObjList() {
		Session currentSession = sessionFactory.unwrap(Session.class);
		Query<BankMaster> theQuery = currentSession.createNamedQuery("BankMaster.findByAll", BankMaster.class);
		return theQuery.getResultList();
	}
	
	@Override
	public BankMaster getEntityObj(int id) {
		Session currentSession = sessionFactory.unwrap(Session.class);
		Query<BankMaster> q = currentSession.createNamedQuery("BankMaster.findById", BankMaster.class);
		q.setParameter("id", id);
		return q.getResultList().isEmpty() ? null : q.getResultList().get(0);
	}
	
	@Override
	public void saveEntityObj(BankMaster bank) {
		Session currentSession = sessionFactory.unwrap(Session.class);
		currentSession.save(bank);
	}
	
	@Override
	public void updateEntityObj(BankMaster bank) {
		Session currentSession = sessionFactory.unwrap(Session.class);
		currentSession.update(bank);
	}

	@Override
	public void deleteEntityObj(BankMaster e) {
		// TODO Auto-generated method stub
		
	}
}