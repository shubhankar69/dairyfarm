package com.dairyfarm.entity.txn;

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

import com.dairyfarm.entity.master.SessionPeriod;

@Entity
@NamedQueries({
	@NamedQuery(name = "PurchaseVoucherDetails.findByAll", query = "from PurchaseVoucherDetails order by id"),
    @NamedQuery(name = "PurchaseVoucherDetails.findById", query = "from PurchaseVoucher where id = :id")
})

@Table(name = "purchase_voucher_details", catalog = "dairyfarm")
public class PurchaseVoucherDetails implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "pvid")
	private int pvid;
	
	@Column(name = "date")
	private Date date;
	
	@Column(name = "qty")
	private Double qty;
	
	@Column(name = "fatP")
	private Double fatP;
	
	@Column(name = "clrP")
	private Double clrP;
	
	@Column(name = "snfP")
	private Double snfP;
	
	@Column(name = "kgFat")
	private Double kgFat;
	
	@Column(name = "kgSnf")
	private Double kgSnf;
	
	@Column(name = "fatRate")
	private Double fatRate;
	
	@Column(name = "snfRate")
	private Double snfRate;
	
	@Column(name = "fatAmount")
	private Double fatAmount;
	
	@Column(name = "snfAmount")
	private Double snfAmount;
	
	@Column(name = "milkPrice")
	private Double milkPrice;
	
	@Column(name = "milkRate")
	private Double milkRate;
	
	@Column(name = "rejectedQty")
	private Double rejectedQty;
	
	@Column(name = "sourQty")
	private Double sourQty;
	
	@Column(name = "shift")
	private String shift;
	
	@Column(name = "fatQuality")
	private String fatQuality;
	
	@Column(name = "snfQuality")
	private String snfQuality;
	
	@Column(name = "setStandard")
	private String setStandard;
	
	@Column(name = "setRate")
	private String setRate;	
	
	@Column(name = "remarks")
	private String remarks;
	
	@Column(name = "createdOn")
	private Date createdOn;
	
	@Column(name = "updatedOn")
	private Date updatedOn;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "sessionId")
	private SessionPeriod sessionPeriod;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getPvid() {
		return pvid;
	}

	public void setPvid(int pvid) {
		this.pvid = pvid;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Double getQty() {
		return qty;
	}

	public void setQty(Double qty) {
		this.qty = qty;
	}

	public Double getFatP() {
		return fatP;
	}

	public void setFatP(Double fatP) {
		this.fatP = fatP;
	}

	public Double getSnfP() {
		return snfP;
	}

	public void setSnfP(Double snfP) {
		this.snfP = snfP;
	}

	public Double getKgFat() {
		return kgFat;
	}

	public void setKgFat(Double kgFat) {
		this.kgFat = kgFat;
	}

	public Double getKgSnf() {
		return kgSnf;
	}

	public void setKgSnf(Double kgSnf) {
		this.kgSnf = kgSnf;
	}

	public Double getFatRate() {
		return fatRate;
	}

	public void setFatRate(Double fatRate) {
		this.fatRate = fatRate;
	}

	public Double getSnfRate() {
		return snfRate;
	}

	public void setSnfRate(Double snfRate) {
		this.snfRate = snfRate;
	}

	public Double getFatAmount() {
		return fatAmount;
	}

	public void setFatAmount(Double fatAmount) {
		this.fatAmount = fatAmount;
	}

	public Double getSnfAmount() {
		return snfAmount;
	}

	public void setSnfAmount(Double snfAmount) {
		this.snfAmount = snfAmount;
	}

	public Double getMilkPrice() {
		return milkPrice;
	}

	public void setMilkPrice(Double milkPrice) {
		this.milkPrice = milkPrice;
	}

	public Double getMilkRate() {
		return milkRate;
	}

	public void setMilkRate(Double milkRate) {
		this.milkRate = milkRate;
	}

	public Double getRejectedQty() {
		return rejectedQty;
	}

	public void setRejectedQty(Double rejectedQty) {
		this.rejectedQty = rejectedQty;
	}

	public Double getSourQty() {
		return sourQty;
	}

	public void setSourQty(Double sourQty) {
		this.sourQty = sourQty;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	public String getFatQuality() {
		return fatQuality;
	}

	public void setFatQuality(String fatQuality) {
		this.fatQuality = fatQuality;
	}

	public String getSnfQuality() {
		return snfQuality;
	}

	public void setSnfQuality(String snfQuality) {
		this.snfQuality = snfQuality;
	}

	public String getSetStandard() {
		return setStandard;
	}

	public void setSetStandard(String setStandard) {
		this.setStandard = setStandard;
	}

	public String getSetRate() {
		return setRate;
	}

	public void setSetRate(String setRate) {
		this.setRate = setRate;
	}

	public String getShift() {
		return shift;
	}

	public void setShift(String shift) {
		this.shift = shift;
	}

	public SessionPeriod getSessionPeriod() {
		return sessionPeriod;
	}

	public void setSessionPeriod(SessionPeriod sessionPeriod) {
		this.sessionPeriod = sessionPeriod;
	}

	public Double getClrP() {
		return clrP;
	}

	public void setClrP(Double clrP) {
		this.clrP = clrP;
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
}