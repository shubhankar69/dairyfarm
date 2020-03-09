package com.dairyfarm.entity.settings;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

@Entity
@NamedQueries({
	@NamedQuery(name = "SessionWiseLastBill.findByAll", query = "from SessionWiseLastBill order by id"),
	@NamedQuery(name = "SessionWiseLastBill.findBySessionId", query = "from SessionWiseLastBill where sessionId = :sessionId")
})
@Table(name = "session_wise_last_bill", catalog = "dairyfarm")
public class SessionWiseLastBill implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "sessionId")
	private Integer sessionId;
	
	@Column(name = "lastBillNo")
	private Integer lastBillNo;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getSessionId() {
		return sessionId;
	}

	public void setSessionId(Integer sessionId) {
		this.sessionId = sessionId;
	}

	public Integer getLastBillNo() {
		return lastBillNo;
	}

	public void setLastBillNo(Integer lastBillNo) {
		this.lastBillNo = lastBillNo;
	}

	@Override
	public String toString() {
		return "SessionWiseLastBill [id=" + id + "]";
	}
	
}
