package com.dairyfarm.da.master;

import java.util.List;

import com.dairyfarm.entity.master.PartyMaster;

public interface PartyMasterDA<E> extends CommonDA<E> {
	
	public List<E> getPartyMasterListBy(int bankId);

	List<PartyMaster> getPartyMasterListByGroupId(int groupId);
}