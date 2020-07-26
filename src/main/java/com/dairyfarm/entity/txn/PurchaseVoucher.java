package com.dairyfarm.entity.txn;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

import com.dairyfarm.entity.master.PartyMaster;
import com.dairyfarm.entity.master.SessionPeriod;

@Entity
@NamedQueries({
	@NamedQuery(name = "PurchaseVoucher.findByAll", query = "from PurchaseVoucher order by id"),
	@NamedQuery(name = "PurchaseVoucher.findBySessionId", query = "from PurchaseVoucher where sessionId = :sessionId order by sId desc"),
    @NamedQuery(name = "PurchaseVoucher.findById", query = "from PurchaseVoucher where id = :id"),
    @NamedQuery(name = "PurchaseVoucher.findByIds", query = "from PurchaseVoucher where id in :ids"),
	@NamedQuery(name = "PurchaseVoucher.findByPartyId", query = "from PurchaseVoucher where partyId = :partyId")
})

@Table(name = "purchase_voucher", catalog = "dairyfarm")
public class PurchaseVoucher implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "billNo")
	private Integer billNo;
	
	@Column(name = "sId")
	private Integer sId;
	
	@Column(name = "billDate")
	private Date billDate;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "partyId")
	private PartyMaster partyMaster;
	
	@OneToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinColumn(name = "pvid")
	private List<PurchaseVoucherDetails> purchaseVoucherDetails;
	
	@Column(name = "periodFromDate")
	private Date periodFromDate;
	
	@Column(name = "periodToDate")
	private Date periodToDate;
	
	@Column(name = "totalAdditions")
	private Double totalAdditions;
	
	@Column(name = "totalDeductions")
	private Double totalDeductions;
	
	@Column(name = "netAmount")
	private Double netAmount;
	
	@Column(name = "adjustment")
	private Double adjustment;
	
	@Column(name = "billTotal")
	private Double billTotal;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "sessionId")
	private SessionPeriod sessionPeriod;
	
	@Column(name = "createdOn")
	private Date createdOn;
	
	@Column(name = "updatedOn")
	private Date updatedOn;
	
	@Column(name = "totalRejected")
	private Double totalRejected;
	
	@Column(name = "totalSour")
	private Double totalSour;
	
	@Column(name = "totalKgfat")
	private Double totalKgfat;
	
	@Column(name = "totalKgsnf")
	private Double totalKgsnf;
	
	@Column(name = "totalFatAmount")
	private Double totalFatAmount;
	
	@Column(name = "totalSnfAmount")
	private Double totalSnfAmount;
	
	@Column(name = "totalQty")
	private Double totalQty;
	
	@Column(name = "totalMilkprice")
	private Double totalMilkprice;
	
	@Column(name = "commissionValue")
	private Double commissionValue;
	
	@Column(name = "commissionNarration")
	private String commissionNarration;
	
	@Column(name = "commissionType", columnDefinition = "VARCHAR(45) DEFAULT 'ADDITION'")
	private String commissionType;
	
	@Column(name = "splIncentiveValue")
	private Double splIncentiveValue;
	
	@Column(name = "splIncentiveNarration")
	private String splIncentiveNarration;
	
	@Column(name = "splIncentiveType")
	private String splIncentiveType;
	
	@Column(name = "headLoadValue")
	private Double headLoadValue;
	
	@Column(name = "headLoadCalVal")
	private Double headLoadCalVal;
	
	@Column(name = "headLoadNarration")
	private String headLoadNarration;
	
	@Column(name = "headLoadType")
	private String headLoadType;
	
	@Column(name = "sourMilkValue")
	private Double sourMilkValue;
	
	@Column(name = "sourMilkNarration")
	private String sourMilkNarration;
	
	@Column(name = "sourMilkType")
	private String sourMilkType;
	
	@Column(name = "addAdvanceValue")
	private Double addAdvanceValue;
	
	@Column(name = "addAdvanceNarration")
	private String addAdvanceNarration;
	
	@Column(name = "addAdvanceType")
	private String addAdvanceType;
	
	@Column(name = "testEquipMentValue")
	private Double testEquipMentValue;
	
	@Column(name = "testEquipMentNarration")
	private String testEquipMentNarration;
	
	@Column(name = "testEquipMentType")
	private String testEquipMentType;
	
	@Column(name = "lateArrivalValue")
	private Double lateArrivalValue;
	
	@Column(name = "lateArrivalNarration")
	private String lateArrivalNarration;
	
	@Column(name = "lateArrivalType")
	private String lateArrivalType;
	
	@Column(name = "arrearValue")
	private Double arrearValue;
	
	@Column(name = "arrearNarration")
	private String arrearNarration;
	
	@Column(name = "arrearType")
	private String arrearType;
	
	@Column(name = "adjustmentValue")
	private Double adjustmentValue;
	
	@Column(name = "adjustmentNarration")
	private String adjustmentNarration;
	
	@Column(name = "adjustmentType")
	private String adjustmentType;
	
	@Column(name = "addOthersValue")
	private Double addOthersValue;	
	
	@Column(name = "addOthersNarration")
	private String addOthersNarration;
	
	@Column(name = "addOthersType")
	private String addOthersType;
	
	@Column(name = "deductAdvanceValue")
	private Double deductAdvanceValue;
	
	@Column(name = "deductAdvanceNarration")
	private String deductAdvanceNarration;
	
	@Column(name = "deductAdvanceType")
	private String deductAdvanceType;
	
	@Column(name = "cattleFeedValue")
	private Double cattleFeedValue;
	
	@Column(name = "cattleFeedNarration")
	private String cattleFeedNarration;
	
	@Column(name = "cattleFeedType")
	private String cattleFeedType;
	
	@Column(name = "MTEValue")
	private Double MTEValue;
	
	@Column(name = "MTENarration")
	private String MTENarration;
	
	@Column(name = "MTEType")
	private String MTEType;
	
	@Column(name = "fodderValue")
	private Double fodderValue;
	
	@Column(name = "fodderNarration")
	private String fodderNarration;
	
	@Column(name = "fodderType")
	private String fodderType;
	
	@Column(name = "cowLoanValue")
	private Double cowLoanValue;
	
	@Column(name = "cowLoanNarration")
	private String cowLoanNarration;
	
	@Column(name = "cowLoanType")
	private String cowLoanType;

	@Column(name = "vaccineValue")
	private Double vaccineValue;
	
	@Column(name = "vaccineNarration")
	private String vaccineNarration;
	
	@Column(name = "vaccineType")
	private String vaccineType;
	
	@Column(name = "shareValue")
	private Double shareValue;
	
	@Column(name = "shareNarration")
	private String shareNarration;
	
	@Column(name = "shareType")
	private String shareType;
	
	@Column(name = "deductOthersValue")
	private Double deductOthersValue;
	
	@Column(name = "deductOthersNarration")
	private String deductOthersNarration;
	
	@Column(name = "deductOthersType")
	private String deductOthersType;
	
	@Column(name = "totalAvgRateKg")
	private Double totalAvgRateKg;
	
	@Column(name = "totalAvgKg")
	private Double totalAvgKg;
	
	@Column(name = "totalGoodFat")
	private Double totalGoodFat;
	
	@Column(name = "totalGoodSnf")
	private Double totalGoodSnf;
	
	@Column(name = "totalBadFat")
	private Double totalBadFat;
	
	@Column(name = "totalBadSnf")
	private Double totalBadSnf;
	
	@Column(name = "notes")
	private String notes;
	
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public int getBillNo() {
		return billNo;
	}
	public void setBillNo(Integer billNo) {
		this.billNo = billNo;
	}
	public Date getBillDate() {
		return billDate;
	}
	public void setBillDate(Date billDate) {
		this.billDate = billDate;
	}
	public PartyMaster getPartyMaster() {
		return partyMaster;
	}
	public void setPartyMaster(PartyMaster partyMaster) {
		this.partyMaster = partyMaster;
	}
	
	public List<PurchaseVoucherDetails> getPurchaseVoucherDetails() {
		return purchaseVoucherDetails;
	}
	public void setPurchaseVoucherDetails(List<PurchaseVoucherDetails> purchaseVoucherDetails) {
		this.purchaseVoucherDetails = purchaseVoucherDetails;
	}
	public Date getPeriodFromDate() {
		return periodFromDate;
	}
	public void setPeriodFromDate(Date periodFromDate) {
		this.periodFromDate = periodFromDate;
	}
	public Date getPeriodToDate() {
		return periodToDate;
	}
	public void setPeriodToDate(Date periodToDate) {
		this.periodToDate = periodToDate;
	}
	
	public Double getTotalAdditions() {
		return totalAdditions;
	}
	public void setTotalAdditions(Double totalAdditions) {
		this.totalAdditions = totalAdditions;
	}
	public Double getTotalDeductions() {
		return totalDeductions;
	}
	public void setTotalDeductions(Double totalDeductions) {
		this.totalDeductions = totalDeductions;
	}
	public Double getNetAmount() {
		return netAmount;
	}
	public void setNetAmount(Double netAmount) {
		this.netAmount = netAmount;
	}
	public Double getAdjustment() {
		return adjustment;
	}
	public void setAdjustment(Double adjustment) {
		this.adjustment = adjustment;
	}
	public Double getBillTotal() {
		return billTotal;
	}
	public void setBillTotal(Double billTotal) {
		this.billTotal = billTotal;
	}
	public SessionPeriod getSessionPeriod() {
		return sessionPeriod;
	}
	public void setSessionPeriod(SessionPeriod sessionPeriod) {
		this.sessionPeriod = sessionPeriod;
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
	public Double getTotalRejected() {
		return totalRejected;
	}
	public void setTotalRejected(Double totalRejected) {
		this.totalRejected = totalRejected;
	}
	public Double getTotalSour() {
		return totalSour;
	}
	public void setTotalSour(Double totalSour) {
		this.totalSour = totalSour;
	}
	public Double getTotalKgfat() {
		return totalKgfat;
	}
	public void setTotalKgfat(Double totalKgfat) {
		this.totalKgfat = totalKgfat;
	}
	public Double getTotalKgsnf() {
		return totalKgsnf;
	}
	public void setTotalKgsnf(Double totalKgsnf) {
		this.totalKgsnf = totalKgsnf;
	}
	public Double getTotalFatAmount() {
		return totalFatAmount;
	}
	public void setTotalFatAmount(Double totalFatAmount) {
		this.totalFatAmount = totalFatAmount;
	}
	public Double getTotalSnfAmount() {
		return totalSnfAmount;
	}
	public void setTotalSnfAmount(Double totalSnfAmount) {
		this.totalSnfAmount = totalSnfAmount;
	}
	public Double getTotalQty() {
		return totalQty;
	}
	public void setTotalQty(Double totalQty) {
		this.totalQty = totalQty;
	}
	public Double getTotalMilkprice() {
		return totalMilkprice;
	}
	public void setTotalMilkprice(Double totalMilkprice) {
		this.totalMilkprice = totalMilkprice;
	}
	public Double getCommissionValue() {
		return commissionValue;
	}
	public void setCommissionValue(Double commissionValue) {
		this.commissionValue = commissionValue;
	}
	public String getCommissionNarration() {
		return commissionNarration;
	}
	public void setCommissionNarration(String commissionNarration) {
		this.commissionNarration = commissionNarration;
	}
	public String getCommissionType() {
		return commissionType;
	}
	public void setCommissionType(String commissionType) {
		this.commissionType = commissionType;
	}
	public Double getSplIncentiveValue() {
		return splIncentiveValue;
	}
	public void setSplIncentiveValue(Double splIncentiveValue) {
		this.splIncentiveValue = splIncentiveValue;
	}
	public String getSplIncentiveNarration() {
		return splIncentiveNarration;
	}
	public void setSplIncentiveNarration(String splIncentiveNarration) {
		this.splIncentiveNarration = splIncentiveNarration;
	}
	public String getSplIncentiveType() {
		return splIncentiveType;
	}
	public void setSplIncentiveType(String splIncentiveType) {
		this.splIncentiveType = splIncentiveType;
	}
	public Double getHeadLoadValue() {
		return headLoadValue;
	}
	public void setHeadLoadValue(Double headLoadValue) {
		this.headLoadValue = headLoadValue;
	}
	public String getHeadLoadNarration() {
		return headLoadNarration;
	}
	public void setHeadLoadNarration(String headLoadNarration) {
		this.headLoadNarration = headLoadNarration;
	}
	public String getHeadLoadType() {
		return headLoadType;
	}
	public void setHeadLoadType(String headLoadType) {
		this.headLoadType = headLoadType;
	}
	public Double getSourMilkValue() {
		return sourMilkValue;
	}
	public void setSourMilkValue(Double sourMilkValue) {
		this.sourMilkValue = sourMilkValue;
	}
	public String getSourMilkNarration() {
		return sourMilkNarration;
	}
	public void setSourMilkNarration(String sourMilkNarration) {
		this.sourMilkNarration = sourMilkNarration;
	}
	public String getSourMilkType() {
		return sourMilkType;
	}
	public void setSourMilkType(String sourMilkType) {
		this.sourMilkType = sourMilkType;
	}
	public Double getAddAdvanceValue() {
		return addAdvanceValue;
	}
	public void setAddAdvanceValue(Double addAdvanceValue) {
		this.addAdvanceValue = addAdvanceValue;
	}
	public String getAddAdvanceNarration() {
		return addAdvanceNarration;
	}
	public void setAddAdvanceNarration(String addAdvanceNarration) {
		this.addAdvanceNarration = addAdvanceNarration;
	}
	public String getAddAdvanceType() {
		return addAdvanceType;
	}
	public void setAddAdvanceType(String addAdvanceType) {
		this.addAdvanceType = addAdvanceType;
	}
	public Double getTestEquipMentValue() {
		return testEquipMentValue;
	}
	public void setTestEquipMentValue(Double testEquipMentValue) {
		this.testEquipMentValue = testEquipMentValue;
	}
	public String getTestEquipMentNarration() {
		return testEquipMentNarration;
	}
	public void setTestEquipMentNarration(String testEquipMentNarration) {
		this.testEquipMentNarration = testEquipMentNarration;
	}
	public String getTestEquipMentType() {
		return testEquipMentType;
	}
	public void setTestEquipMentType(String testEquipMentType) {
		this.testEquipMentType = testEquipMentType;
	}
	public Double getLateArrivalValue() {
		return lateArrivalValue;
	}
	public void setLateArrivalValue(Double lateArrivalValue) {
		this.lateArrivalValue = lateArrivalValue;
	}
	public String getLateArrivalNarration() {
		return lateArrivalNarration;
	}
	public void setLateArrivalNarration(String lateArrivalNarration) {
		this.lateArrivalNarration = lateArrivalNarration;
	}
	public String getLateArrivalType() {
		return lateArrivalType;
	}
	public void setLateArrivalType(String lateArrivalType) {
		this.lateArrivalType = lateArrivalType;
	}
	public Double getArrearValue() {
		return arrearValue;
	}
	public void setArrearValue(Double arrearValue) {
		this.arrearValue = arrearValue;
	}
	public String getArrearNarration() {
		return arrearNarration;
	}
	public void setArrearNarration(String arrearNarration) {
		this.arrearNarration = arrearNarration;
	}
	public String getArrearType() {
		return arrearType;
	}
	public void setArrearType(String arrearType) {
		this.arrearType = arrearType;
	}
	public Double getAdjustmentValue() {
		return adjustmentValue;
	}
	public void setAdjustmentValue(Double adjustmentValue) {
		this.adjustmentValue = adjustmentValue;
	}
	public String getAdjustmentNarration() {
		return adjustmentNarration;
	}
	public void setAdjustmentNarration(String adjustmentNarration) {
		this.adjustmentNarration = adjustmentNarration;
	}
	public String getAdjustmentType() {
		return adjustmentType;
	}
	public void setAdjustmentType(String adjustmentType) {
		this.adjustmentType = adjustmentType;
	}
	public Double getAddOthersValue() {
		return addOthersValue;
	}
	public void setAddOthersValue(Double addOthersValue) {
		this.addOthersValue = addOthersValue;
	}
	public String getAddOthersNarration() {
		return addOthersNarration;
	}
	public void setAddOthersNarration(String addOthersNarration) {
		this.addOthersNarration = addOthersNarration;
	}
	public String getAddOthersType() {
		return addOthersType;
	}
	public void setAddOthersType(String addOthersType) {
		this.addOthersType = addOthersType;
	}
	public Double getDeductAdvanceValue() {
		return deductAdvanceValue;
	}
	public void setDeductAdvanceValue(Double deductAdvanceValue) {
		this.deductAdvanceValue = deductAdvanceValue;
	}
	public String getDeductAdvanceNarration() {
		return deductAdvanceNarration;
	}
	public void setDeductAdvanceNarration(String deductAdvanceNarration) {
		this.deductAdvanceNarration = deductAdvanceNarration;
	}
	public String getDeductAdvanceType() {
		return deductAdvanceType;
	}
	public void setDeductAdvanceType(String deductAdvanceType) {
		this.deductAdvanceType = deductAdvanceType;
	}
	public Double getCattleFeedValue() {
		return cattleFeedValue;
	}
	public void setCattleFeedValue(Double cattleFeedValue) {
		this.cattleFeedValue = cattleFeedValue;
	}
	public String getCattleFeedNarration() {
		return cattleFeedNarration;
	}
	public void setCattleFeedNarration(String cattleFeedNarration) {
		this.cattleFeedNarration = cattleFeedNarration;
	}
	public String getCattleFeedType() {
		return cattleFeedType;
	}
	public void setCattleFeedType(String cattleFeedType) {
		this.cattleFeedType = cattleFeedType;
	}
	public Double getMTEValue() {
		return MTEValue;
	}
	public void setMTEValue(Double mTEValue) {
		MTEValue = mTEValue;
	}
	public String getMTENarration() {
		return MTENarration;
	}
	public void setMTENarration(String mTENarration) {
		MTENarration = mTENarration;
	}
	public String getMTEType() {
		return MTEType;
	}
	public void setMTEType(String mTEType) {
		MTEType = mTEType;
	}
	public Double getFodderValue() {
		return fodderValue;
	}
	public void setFodderValue(Double fodderValue) {
		this.fodderValue = fodderValue;
	}
	public String getFodderNarration() {
		return fodderNarration;
	}
	public void setFodderNarration(String fodderNarration) {
		this.fodderNarration = fodderNarration;
	}
	public String getFodderType() {
		return fodderType;
	}
	public void setFodderType(String fodderType) {
		this.fodderType = fodderType;
	}
	public Double getCowLoanValue() {
		return cowLoanValue;
	}
	public void setCowLoanValue(Double cowLoanValue) {
		this.cowLoanValue = cowLoanValue;
	}
	public String getCowLoanNarration() {
		return cowLoanNarration;
	}
	public void setCowLoanNarration(String cowLoanNarration) {
		this.cowLoanNarration = cowLoanNarration;
	}
	public String getCowLoanType() {
		return cowLoanType;
	}
	public void setCowLoanType(String cowLoanType) {
		this.cowLoanType = cowLoanType;
	}
	public Double getVaccineValue() {
		return vaccineValue;
	}
	public void setVaccineValue(Double vaccineValue) {
		this.vaccineValue = vaccineValue;
	}
	public String getVaccineNarration() {
		return vaccineNarration;
	}
	public void setVaccineNarration(String vaccineNarration) {
		this.vaccineNarration = vaccineNarration;
	}
	public String getVaccineType() {
		return vaccineType;
	}
	public void setVaccineType(String vaccineType) {
		this.vaccineType = vaccineType;
	}
	public Double getShareValue() {
		return shareValue;
	}
	public void setShareValue(Double shareValue) {
		this.shareValue = shareValue;
	}
	public String getShareNarration() {
		return shareNarration;
	}
	public void setShareNarration(String shareNarration) {
		this.shareNarration = shareNarration;
	}
	public String getShareType() {
		return shareType;
	}
	public void setShareType(String shareType) {
		this.shareType = shareType;
	}
	public Double getDeductOthersValue() {
		return deductOthersValue;
	}
	public void setDeductOthersValue(Double deductOthersValue) {
		this.deductOthersValue = deductOthersValue;
	}
	public String getDeductOthersNarration() {
		return deductOthersNarration;
	}
	public void setDeductOthersNarration(String deductOthersNarration) {
		this.deductOthersNarration = deductOthersNarration;
	}
	public String getDeductOthersType() {
		return deductOthersType;
	}
	public void setDeductOthersType(String deductOthersType) {
		this.deductOthersType = deductOthersType;
	}
	public Double getTotalAvgRateKg() {
		return totalAvgRateKg;
	}
	public void setTotalAvgRateKg(Double totalAvgRateKg) {
		this.totalAvgRateKg = totalAvgRateKg;
	}
	public Double getTotalAvgKg() {
		return totalAvgKg;
	}
	public void setTotalAvgKg(Double totalAvgKg) {
		this.totalAvgKg = totalAvgKg;
	}
	public Double getTotalGoodFat() {
		return totalGoodFat;
	}
	public void setTotalGoodFat(Double totalGoodFat) {
		this.totalGoodFat = totalGoodFat;
	}
	public Double getTotalGoodSnf() {
		return totalGoodSnf;
	}
	public void setTotalGoodSnf(Double totalGoodSnf) {
		this.totalGoodSnf = totalGoodSnf;
	}
	public Double getTotalBadFat() {
		return totalBadFat;
	}
	public void setTotalBadFat(Double totalBadFat) {
		this.totalBadFat = totalBadFat;
	}
	public Double getTotalBadSnf() {
		return totalBadSnf;
	}
	public void setTotalBadSnf(Double totalBadSnf) {
		this.totalBadSnf = totalBadSnf;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	public Double getHeadLoadCalVal() {
		return headLoadCalVal;
	}
	public void setHeadLoadCalVal(Double headLoadCalVal) {
		this.headLoadCalVal = headLoadCalVal;
	}
	
	public Integer getsId() {
		return sId;
	}
	public void setsId(Integer sId) {
		this.sId = sId;
	}
}
