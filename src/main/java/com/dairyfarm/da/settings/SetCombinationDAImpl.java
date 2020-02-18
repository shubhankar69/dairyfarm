package com.dairyfarm.da.settings;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.dairyfarm.da.master.CommonDA;
import com.dairyfarm.entity.settings.SetCombination;

@Repository
@Qualifier("SetCombinationDA")
public class SetCombinationDAImpl implements CommonDA<SetCombination> {
	
	@Autowired
	private EntityManager sessionFactory;
	
	@Override
	public List<SetCombination> getEntityObjList() {
		Session currentSession = sessionFactory.unwrap(Session.class);
		Query<SetCombination> theQuery = currentSession.createNamedQuery("SetCombination.findByAll", SetCombination.class);
		return theQuery.getResultList();
	}

	@Override
	public SetCombination getEntityObj(int id) {
		Session currentSession = sessionFactory.unwrap(Session.class);
		Query<SetCombination> q = currentSession.createNamedQuery("SetCombination.findById", SetCombination.class);
		q.setParameter("id", id);
		return q.getResultList().isEmpty() ? null : q.getResultList().get(0);
	}

	@Override
	public void saveEntityObj(SetCombination e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateEntityObj(SetCombination combo) {
		System.err.println("hello");
		Session currentSession = sessionFactory.unwrap(Session.class);
		currentSession.update(combo);
		System.err.println("dg");
	}

	@Override
	public void deleteEntityObj(SetCombination e) {
		// TODO Auto-generated method stub
		
	}
}
