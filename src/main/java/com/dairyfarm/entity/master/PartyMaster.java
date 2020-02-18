package com.dairyfarm.entity.master;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;
import org.hibernate.annotations.Where;

@Entity
@NamedQueries({
	@NamedQuery(name = "PartyMaster.findByAll", query = "from PartyMaster order by id"),
    @NamedQuery(name = "PartyMaster.findById", query = "from PartyMaster where id = :id"),
    @NamedQuery(name = "PartyMaster.findByIdStatus", query = "from PartyMaster where id = :id AND status = :status"),
    @NamedQuery(name = "PartyMaster.findByBankId", query = "from PartyMaster where bankId = :bankId"),
    @NamedQuery(name = "PartyMaster.findByGroupId", query = "from PartyMaster where groupId = :groupId"),
    @NamedQuery(name = "PartyMaster.findByPartyName", query = "from PartyMaster where partyName = :partyName")
})

@Table(name = "party_master", catalog = "dairyfarm")
@Where(clause = "status > 0")
public class PartyMaster implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	@Column(name = "plantNo")
	private int plantNo;
	@Column(name = "plantName")
	private String plantName;
	@Column(name = "partyName")
	private String partyName;
	@Column(name = "address")
	private String address;
	@Column(name = "phone")
	private String phone;
	@Column(name = "partyBankAccNo")
	private String partyBankAccNo;
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "groupId")
	private GroupMaster groupMaster;
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "bankId")
	private BankMaster bankMaster;
	@Column(name = "balance")
	private Double balance;
	@Column(name = "nickName")
	private String nickName;	
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
	public int getPlantNo() {
		return plantNo;
	}
	public void setPlantNo(int plantNo) {
		this.plantNo = plantNo;
	}
	public String getPlantName() {
		return plantName;
	}
	public void setPlantName(String plantName) {
		this.plantName = plantName;
	}
	public String getPartyName() {
		return partyName;
	}
	public void setPartyName(String partyName) {
		this.partyName = partyName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getPartyBankAccNo() {
		return partyBankAccNo;
	}
	public void setPartyBankAccNo(String partyBankAccNo) {
		this.partyBankAccNo = partyBankAccNo;
	}
	public BankMaster getBankMaster() {
		return bankMaster;
	}
	public void setBankMaster(BankMaster bankMaster) {
		this.bankMaster = bankMaster;
	}
	public GroupMaster getGroupMaster() {
		return groupMaster;
	}
	public void setGroupMaster(GroupMaster groupMaster) {
		this.groupMaster = groupMaster;
	}
	public Double getBalance() {
		return balance;
	}
	public void setBalance(Double balance) {
		this.balance = balance;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
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
		return "PartyMaster [id=" + id + "]";
	}
}