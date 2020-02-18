package com.dairyfarm.da.master;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.dairyfarm.entity.master.SessionPeriod;

/**
 * 
 * @author sdutt
 *
 */

@Repository
@Qualifier("sessionDA")
public class SessionDAImpl implements CommonDA<SessionPeriod> {
	
	@Autowired
	private EntityManager sessionFactory;
			
	@Override
	public List<SessionPeriod> getEntityObjList() {
		Session currentSession = sessionFactory.unwrap(Session.class);
		Query<SessionPeriod> theQuery = currentSession.createNamedQuery("SessionPeriod.findByAll", SessionPeriod.class);
		return theQuery.getResultList();
	}
	
	@Override
	public SessionPeriod getEntityObj(int id) {
		Session currentSession = sessionFactory.unwrap(Session.class);
		Query<SessionPeriod> q = currentSession.createNamedQuery("SessionPeriod.findById", SessionPeriod.class);
		q.setParameter("id", id);
		return q.getResultList().isEmpty() ? null : q.getResultList().get(0);
	}
	
	@Override
	public void saveEntityObj(SessionPeriod session) {
		Session currentSession = sessionFactory.unwrap(Session.class);
		currentSession.save(session);
	}
	
	@Override
	public void updateEntityObj(SessionPeriod session) {
		Session currentSession = sessionFactory.unwrap(Session.class);
		currentSession.update(session);
	}

	@Override
	public void deleteEntityObj(SessionPeriod e) {
		// TODO Auto-generated method stub
		
	}
}