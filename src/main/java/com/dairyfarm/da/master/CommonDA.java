package com.dairyfarm.da.master;

import java.util.List;

public interface CommonDA<E> {

	public List<E> getEntityObjList();

	public E getEntityObj(int theId);
	
	public void saveEntityObj(E e);

	public void updateEntityObj(E e);
	
	public void deleteEntityObj(E e);
}