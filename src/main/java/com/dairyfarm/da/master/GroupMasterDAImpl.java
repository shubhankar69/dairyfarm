package com.dairyfarm.da.master;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.dairyfarm.entity.master.GroupMaster;

@Repository
@Qualifier("groupmasterDA")
public class GroupMasterDAImpl implements CommonDA<GroupMaster> {
	
	@Autowired
	private EntityManager sessionFactory;

	@Override
	public List<GroupMaster> getEntityObjList() {
		Session currentSession = sessionFactory.unwrap(Session.class);
		Query<GroupMaster> theQuery = currentSession.createNamedQuery("GroupMaster.findByAll", GroupMaster.class);
		return theQuery.getResultList();
	}

	@Override
	public GroupMaster getEntityObj(int theId) {
		Session currentSession = sessionFactory.unwrap(Session.class);
		Query<GroupMaster> q = currentSession.createNamedQuery("GroupMaster.findById", GroupMaster.class);
		q.setParameter("id", theId);
		return q.getResultList().isEmpty() ? null : q.getResultList().get(0);
	}

	@Override
	public void saveEntityObj(GroupMaster groupMaster) {
		Session currentSession = sessionFactory.unwrap(Session.class);
		currentSession.save(groupMaster);
	}

	@Override
	public void updateEntityObj(GroupMaster groupMaster) {
		Session currentSession = sessionFactory.unwrap(Session.class);
		currentSession.update(groupMaster);
	}

	@Override
	public void deleteEntityObj(GroupMaster e) {
		// TODO Auto-generated method stub
		
	}
}