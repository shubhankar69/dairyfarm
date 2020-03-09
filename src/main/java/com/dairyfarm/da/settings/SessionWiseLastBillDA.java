package com.dairyfarm.da.settings;

import java.util.List;

import com.dairyfarm.da.master.CommonDA;

public interface SessionWiseLastBillDA<E> extends CommonDA<E> {

	public List<E> getSessionLassBillObjBy(int sessionId);

	public Integer getLastBillNo(Integer sessionId);
}
