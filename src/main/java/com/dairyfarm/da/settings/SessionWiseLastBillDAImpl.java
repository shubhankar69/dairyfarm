package com.dairyfarm.da.settings;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.dairyfarm.entity.settings.SessionWiseLastBill;

@Repository
@Qualifier("sessionWiseLastBillDA")
public class SessionWiseLastBillDAImpl implements SessionWiseLastBillDA<SessionWiseLastBill> {
	
	@Autowired
	private EntityManager sessionFactory;

	@Override
	public List<SessionWiseLastBill> getEntityObjList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SessionWiseLastBill getEntityObj(int theId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<SessionWiseLastBill> getSessionLassBillObjBy(int sessionId) {
		Session currentSession = sessionFactory.unwrap(Session.class);
		Query<SessionWiseLastBill> theQuery = currentSession.createNamedQuery("SessionWiseLastBill.findBySessionId", SessionWiseLastBill.class);
		theQuery.setParameter("sessionId", sessionId);
		return theQuery.getResultList();
	}

	@Override
	public void saveEntityObj(SessionWiseLastBill e) {
		Session currentSession = sessionFactory.unwrap(Session.class);
		currentSession.save(e);
	}

	@Override
	public void updateEntityObj(SessionWiseLastBill e) {
		Session currentSession = sessionFactory.unwrap(Session.class);
		currentSession.update(e);
	}

	@Override
	public void deleteEntityObj(SessionWiseLastBill e) {
		// TODO Auto-generated method stub
		
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public Integer getLastBillNo(Integer sessionId) {
		Integer no = 0;
		Session currentSession = sessionFactory.unwrap(Session.class);
		Query theQuery = currentSession.createNativeQuery("select lastBillNo from dairyfarm.session_wise_last_bill where sessionId = ?");
		theQuery.setParameter(1, sessionId);
		List maxl1 = theQuery.getResultList();
		if(maxl1 != null && !maxl1.isEmpty()) {
			if(maxl1.get(0) != null) {
				no = Integer.parseInt(maxl1.get(0).toString());
			}
		}
		return no;
	}

}
