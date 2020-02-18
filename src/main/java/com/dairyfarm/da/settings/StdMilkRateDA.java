package com.dairyfarm.da.settings;

import java.util.List;

import com.dairyfarm.da.master.CommonDA;

public interface StdMilkRateDA<E> extends CommonDA<E> {
	
	public E getEntityObj(String type, String pageName);
	
	public List<E> getStdMilkRateByPageName(String pageName);
	
	public List<E> getStdMilkRateByType(String type);
}
