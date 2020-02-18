package com.dairyfarm.da.master;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.dairyfarm.entity.master.PartyMaster;

@Repository
@Qualifier("partymasterDA")
public class PartyMasterDAImpl implements PartyMasterDA<PartyMaster> {
	
	@Autowired
	private EntityManager sessionFactory;

	@Override
	public List<PartyMaster> getEntityObjList() {
		Session currentSession = sessionFactory.unwrap(Session.class);
		Query<PartyMaster> theQuery = currentSession.createNamedQuery("PartyMaster.findByAll", PartyMaster.class);
		return theQuery.getResultList();
	}

	@Override
	public PartyMaster getEntityObj(int id) {
		Session currentSession = sessionFactory.unwrap(Session.class);
		Query<PartyMaster> q = currentSession.createNamedQuery("PartyMaster.findById", PartyMaster.class);
		q.setParameter("id", id);
		return q.getResultList().isEmpty() ? null : q.getResultList().get(0);
	}

	@Override
	public void saveEntityObj(PartyMaster party) {
		Session currentSession = sessionFactory.unwrap(Session.class);
		currentSession.save(party);
	}

	@Override
	public void updateEntityObj(PartyMaster party) {
		Session currentSession = sessionFactory.unwrap(Session.class);
		currentSession.update(party);
	}

	@Override
	public List<PartyMaster> getPartyMasterListBy(int bankId) {
		Session currentSession = sessionFactory.unwrap(Session.class);
		List<PartyMaster> pm = null;
		try {
			Query<PartyMaster> q = currentSession.createNamedQuery("PartyMaster.findByBankId", PartyMaster.class);
			q.setParameter("bankId", bankId);
			pm = q.getResultList();
		} catch(NoResultException e) {
			pm = null;
		}
		return pm;
	}
	
	@Override
	public List<PartyMaster> getPartyMasterListByGroupId(int groupId) {
		Session currentSession = sessionFactory.unwrap(Session.class);
		List<PartyMaster> pm = null;
		try {
			Query<PartyMaster> q = currentSession.createNamedQuery("PartyMaster.findByGroupId", PartyMaster.class);
			q.setParameter("groupId", groupId);
			pm = q.getResultList();
		} catch(NoResultException e) {
			pm = null;
		}
		return pm;
	}

	@Override
	public void deleteEntityObj(PartyMaster e) {
		// TODO Auto-generated method stub
		
	}
}