package com.dairyfarm.entity.master;

import java.io.Serializable;
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

@Entity
@NamedQueries({
	@NamedQuery(name = "BankMaster.findByAll", query = "from BankMaster order by id"),
    @NamedQuery(name = "BankMaster.findById", query = "from BankMaster where id = :id"),
    @NamedQuery(name = "BankMaster.findByIdStatus", query = "from BankMaster where id = :id"),
    @NamedQuery(name = "BankMaster.findByBankName", query = "from BankMaster where bankName = :bankName")
})
@Table(name = "bank_master", catalog = "dairyfarm")
@Where(clause = "status > 0")
public class BankMaster implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	@Column(name = "bankName")
	private String bankName;
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
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName.trim();
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
		return "BankMaster [id=" + id + "]";
	}
}