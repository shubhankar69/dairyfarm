package com.dairyfarm.da.settings;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.dairyfarm.entity.settings.MiscSettings;

@Repository
@Qualifier("MiscSettingsDA")
public class MiscSettingsDAImpl implements MiscSettingsDA<MiscSettings> {
	
	@Autowired
	private EntityManager sessionFactory;
	
	@Override
	public List<MiscSettings> getEntityObjList() {
		Session currentSession = sessionFactory.unwrap(Session.class);
		Query<MiscSettings> theQuery = currentSession.createNamedQuery("MiscSettings.findByAll", MiscSettings.class);
		return theQuery.getResultList();
	}
	@Override
	public MiscSettings getEntityObj(int theId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void saveEntityObj(MiscSettings e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void updateEntityObj(MiscSettings e) {
		Session currentSession = sessionFactory.unwrap(Session.class);
		currentSession.update(e);
	}
	
	@Override
	public MiscSettings getEntityObj(String type, String name) {
		Session currentSession = sessionFactory.unwrap(Session.class);
		Query<MiscSettings> q = currentSession.createNamedQuery("MiscSettings.findByTypeName", MiscSettings.class);
		q.setParameter("typeName", type);
		q.setParameter("name", name);
		return q.getResultList().isEmpty() ? null : q.getResultList().get(0);
	}
	
	@Override
	public List<MiscSettings> getMiscSettingsByName(String name) {
		Session currentSession = sessionFactory.unwrap(Session.class);
		Query<MiscSettings> q = currentSession.createNamedQuery("MiscSettings.findByName", MiscSettings.class);
		q.setParameter("name", name);
		return q.getResultList();
	}
	
	@Override
	public List<MiscSettings> getMiscSettingsByType(String type) {
		Session currentSession = sessionFactory.unwrap(Session.class);
		Query<MiscSettings> q = currentSession.createNamedQuery("MiscSettings.findByType", MiscSettings.class);
		q.setParameter("typeName", type);
		return q.getResultList();
	}
	@Override
	public void deleteEntityObj(MiscSettings e) {
		// TODO Auto-generated method stub
		
	}
}
