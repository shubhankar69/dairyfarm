package com.dairyfarm.da.settings;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.dairyfarm.entity.settings.StdMilkRate;

@Repository
@Qualifier("stdmilkrateDA")
public class StdMilkRateDAImpl implements StdMilkRateDA<StdMilkRate> {

	@Autowired
	private EntityManager sessionFactory;
			
	@Override
	public List<StdMilkRate> getEntityObjList() {
		Session currentSession = sessionFactory.unwrap(Session.class);
		Query<StdMilkRate> theQuery = currentSession.createNamedQuery("StdMilkRate.findByAll", StdMilkRate.class);
		return theQuery.getResultList();
	}
	
	@Override
	public StdMilkRate getEntityObj(int id) {
		Session currentSession = sessionFactory.unwrap(Session.class);
		Query<StdMilkRate> q = currentSession.createNamedQuery("StdMilkRate.findById", StdMilkRate.class);
		q.setParameter("id", id);
		return q.getResultList().isEmpty() ? null : q.getResultList().get(0);
	}
	
	@Override
	public StdMilkRate getEntityObj(String type, String pageName) {
		Session currentSession = sessionFactory.unwrap(Session.class);
		Query<StdMilkRate> q = currentSession.createNamedQuery("StdMilkRate.findByTypePageName", StdMilkRate.class);
		q.setParameter("type", type);
		q.setParameter("pageName", pageName);
		return q.getResultList().isEmpty() ? null : q.getResultList().get(0);
	}

	@Override
	public List<StdMilkRate> getStdMilkRateByPageName(String pageName) {
		Session currentSession = sessionFactory.unwrap(Session.class);
		Query<StdMilkRate> q = currentSession.createNamedQuery("StdMilkRate.findByPageName", StdMilkRate.class);
		q.setParameter("pageName", pageName);
		return q.getResultList();
	}

	@Override
	public List<StdMilkRate> getStdMilkRateByType(String type) {
		Session currentSession = sessionFactory.unwrap(Session.class);
		Query<StdMilkRate> q = currentSession.createNamedQuery("StdMilkRate.findByType", StdMilkRate.class);
		q.setParameter("type", type);
		return q.getResultList();
	}
	
	@Override
	public void saveEntityObj(StdMilkRate e) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void updateEntityObj(StdMilkRate e) {
		Session currentSession = sessionFactory.unwrap(Session.class);
		currentSession.update(e);
	}

	@Override
	public void deleteEntityObj(StdMilkRate e) {
		// TODO Auto-generated method stub
		
	}
}