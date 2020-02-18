package com.dairyfarm.entity.master;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;
import org.hibernate.annotations.Where;

import com.dairyfarm.helper.DateHandler;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@Entity
@Table(name = "session_period", catalog = "dairyfarm")
@NamedQueries({
	@NamedQuery(name = "SessionPeriod.findByAll", query = "from SessionPeriod order by id"),
    @NamedQuery(name = "SessionPeriod.findById", query = "from SessionPeriod where id = :id"),
    @NamedQuery(name = "SessionPeriod.findByIdStatus", query = "from SessionPeriod where id = :id"),
    @NamedQuery(name = "SessionPeriod.findBySessionperiod", query = "from SessionPeriod where date(NOW()) between fromDate AND toDate"),
    @NamedQuery(name = "SessionPeriod.findBySessionName", query = "from SessionPeriod where sessionName = :sessionName")
})
@Where(clause = "status > 0")
public class SessionPeriod implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	@Column(name = "fromDate")
	@JsonDeserialize(using = DateHandler.class)
	private Date fromDate;
	@Column(name = "toDate")
	@JsonDeserialize(using = DateHandler.class)
	private Date toDate;
	@Column(name = "sessionName")
	private String sessionName;
	@Column(name = "status")
	private int status;
	@Column(name = "createdOn")
	private Date createdOn;
	@Column(name = "updatedOn")
	private Date updatedOn;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Date getFromDate() {
		return fromDate;
	}
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	public Date getToDate() {
		return toDate;
	}
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
	public String getSessionName() {
		return sessionName;
	}
	public void setSessionName(String sessionName) {
		this.sessionName = sessionName;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	public Date getUpdatedOn() {
		return updatedOn;
	}
	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}
	@Override
	public String toString() {
		return "SessionPeriod [id=" + id + "]";
	}
}