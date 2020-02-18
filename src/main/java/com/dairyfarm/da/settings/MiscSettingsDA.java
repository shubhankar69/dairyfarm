package com.dairyfarm.da.settings;

import java.util.List;

import com.dairyfarm.da.master.CommonDA;

public interface MiscSettingsDA<E> extends CommonDA<E> {
	
	public E getEntityObj(String type, String name);
	
	public List<E> getMiscSettingsByName(String name);
	
	public List<E> getMiscSettingsByType(String type);
}