package com.dairyfarm.service.txn;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dairyfarm.da.settings.SessionWiseLastBillDA;
import com.dairyfarm.da.txn.PurchaseVoucherDA;
import com.dairyfarm.entity.master.SessionPeriod;
import com.dairyfarm.entity.settings.SessionWiseLastBill;
import com.dairyfarm.entity.txn.PurchaseVoucher;
import com.dairyfarm.entity.txn.PurchaseVoucherDetails;
import com.fasterxml.jackson.databind.ObjectMapper;

@SuppressWarnings("unchecked")
@Service
@Qualifier("purchaseVoucherService")
public class PurchaseVoucherServiceImpl implements PurchaseVoucherService<PurchaseVoucher> {

	@Autowired
	@Qualifier("purchaseVoucherDA")
	private PurchaseVoucherDA<PurchaseVoucher> purchaseVoucherDA;

	@Autowired
	@Qualifier("sessionWiseLastBillDA")
	private SessionWiseLastBillDA<SessionWiseLastBill> billNoDA;

	private ObjectMapper mapper;
	private JSONObject resJson;

	@Override
	@Transactional
	public JSONObject getAllListofData() {
		mapper = new ObjectMapper();
		resJson = new JSONObject();
		JSONArray puchaseArr = new JSONArray();
		try {
			List<PurchaseVoucher> pvList = purchaseVoucherDA.getEntityObjList();
			if (pvList != null && !pvList.isEmpty()) {
				for (PurchaseVoucher pvObj : pvList) {
					String bankjsonString = mapper.writeValueAsString(pvObj);
					puchaseArr.add(new JSONParser().parse(bankjsonString));
				}
				resJson.put("type", "success");
				resJson.put("msg", "success");
				resJson.put("data", puchaseArr);
			} else {
				resJson.put("type", "error");
				resJson.put("msg", "No data found..!!");
				resJson.put("data", puchaseArr);
			}
		} catch (Exception e) {
			resJson.put("type", "error");
			resJson.put("msg", "Server error");
			e.printStackTrace();
		}
		return resJson;
	}

	@Override
	@Transactional
	public JSONObject getAllListofData(Integer sessionID) {
		mapper = new ObjectMapper();
		resJson = new JSONObject();
		JSONArray puchaseArr = new JSONArray();
		try {
			List<PurchaseVoucher> pvList = purchaseVoucherDA.getEntityObjList(sessionID);
			if (pvList != null && !pvList.isEmpty()) {
				for (PurchaseVoucher pvObj : pvList) {
					String bankjsonString = mapper.writeValueAsString(pvObj);
					puchaseArr.add(new JSONParser().parse(bankjsonString));
				}
				resJson.put("type", "success");
				resJson.put("msg", "success");
				resJson.put("data", puchaseArr);
			} else {
				resJson.put("type", "error");
				resJson.put("msg", "No data found..!!");
				resJson.put("data", puchaseArr);
			}
		} catch (Exception e) {
			resJson.put("type", "error");
			resJson.put("msg", "Server error");
			e.printStackTrace();
		}
		return resJson;
	}

	@Override
	@Transactional
	public JSONObject getJsonObj(int theId) {
		resJson = new JSONObject();
		mapper = new ObjectMapper();
		try {
			PurchaseVoucher purchaseObj = purchaseVoucherDA.getEntityObj(theId);
			if (purchaseObj != null) {
				String jsonStr = mapper.writeValueAsString(purchaseObj);
				resJson.put("type", "success");
				resJson.put("msg", "success");
				resJson.put("data", new JSONParser().parse(jsonStr));
			} else {
				resJson.put("type", "error");
				resJson.put("msg", "No data found..!!");
			}
		} catch (Exception e) {
			resJson.put("type", "error");
			resJson.put("msg", "Server error");
			e.printStackTrace();
		}
		return resJson;
	}

	@Override
	@Transactional
	public PurchaseVoucher getPurchaseVoucherObj(int theId) {
		return purchaseVoucherDA.getEntityObj(theId);
	}
	
	@Override
	@Transactional
	public List<PurchaseVoucher> getPurchaseVoucherObjs(List<Integer> ids) {
		return purchaseVoucherDA.getEntityObjs(ids);
	}

	@Override
	@Transactional
	public Integer getMaxBillno(Integer sessionId) {
		return billNoDA.getLastBillNo(sessionId);
	}

	@Override
	@Transactional
	public JSONObject saveEntityObj(PurchaseVoucher pv) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional
	public void savePurchaseVoucherObj(PurchaseVoucher pv) {
		purchaseVoucherDA.saveEntityObj(pv);
	}

	@Override
	@Transactional
	public JSONObject saveEntityPurchaseObj(PurchaseVoucher pv, Integer sessionId) {
		resJson = new JSONObject();
		try {
			Integer maxId = purchaseVoucherDA.getMaxSerialId();
			Integer billNo = billNoDA.getLastBillNo(sessionId);

			SessionPeriod sessionPeriod = new SessionPeriod();
			sessionPeriod.setId(sessionId);

			pv.setSessionPeriod(sessionPeriod);
			pv.setCreatedOn(new Date());
			pv.setsId(maxId + 1);
			pv.setBillNo(billNo + 1);

			for (PurchaseVoucherDetails pvDetails : pv.getPurchaseVoucherDetails()) {
				if (pvDetails != null) {
					pvDetails.setSessionPeriod(sessionPeriod);
					pvDetails.setCreatedOn(new Date());
				}
			}

			// updating last bill no Session Wise
			List<SessionWiseLastBill> sessionBillObj = billNoDA.getSessionLassBillObjBy(sessionId);
			if (sessionBillObj != null && !sessionBillObj.isEmpty()) {
				if (sessionBillObj.get(0) != null) {
					SessionWiseLastBill sessionWiseLastBill = sessionBillObj.get(0);
					sessionWiseLastBill.setLastBillNo(billNo + 1);
					billNoDA.updateEntityObj(sessionWiseLastBill);

					purchaseVoucherDA.saveEntityObj(pv);
					resJson.put("type", "success");
					resJson.put("msg", "Data Saved Successfully..!!");
					resJson.put("data", pv);
				} else {
					resJson.put("type", "error");
					resJson.put("msg", "Data not saved..!!");
				}
			} else {
				resJson.put("type", "error");
				resJson.put("msg", "Data not saved..!!");
			}
		} catch (Exception e) {
			resJson.put("type", "error");
			resJson.put("msg", "Server error");
			e.printStackTrace();
		}
		return resJson;
	}

	@Override
	@Transactional
	public JSONObject updateEntityObj(JSONObject purchaseVoucherjson) {
		resJson = new JSONObject();
		try {
			Integer existingPurchaseVoucherId = (Integer) purchaseVoucherjson.get("id");
			PurchaseVoucher pv = purchaseVoucherDA.getEntityObj(existingPurchaseVoucherId);
			if (pv != null) {
				purchaseVoucherDA.deleteEntityObj(pv);

				mapper = new ObjectMapper();
				String pvString = mapper.writeValueAsString(purchaseVoucherjson);
				PurchaseVoucher newPurchaseVoucher = mapper.readValue(pvString, PurchaseVoucher.class);
				newPurchaseVoucher.setId(null);
				newPurchaseVoucher.setCreatedOn(new Date());
				newPurchaseVoucher.setUpdatedOn(new Date());

				for (PurchaseVoucherDetails pvDetails : newPurchaseVoucher.getPurchaseVoucherDetails()) {
					if (pvDetails != null) {
						pvDetails.setId(null);
						pvDetails.setCreatedOn(new Date());
						pvDetails.setUpdatedOn(new Date());
					}
				}

				purchaseVoucherDA.saveEntityObj(newPurchaseVoucher);
				resJson.put("type", "success");
				resJson.put("msg", "Data Updated Successfully..!!");
				resJson.put("data", newPurchaseVoucher);
			} else {
				resJson.put("type", "error");
				resJson.put("msg", "No data found..!!");
			}
		} catch (Exception e) {
			resJson.put("type", "error");
			resJson.put("msg", "Server error");
			e.printStackTrace();
		}
		return resJson;
	}

	@Override
	@Transactional
	public JSONObject deleteEntityObj(JSONObject purchaseVoucherObj) {
		resJson = new JSONObject();
		try {
			Integer existingPurchaseVoucherId = (Integer) purchaseVoucherObj.get("id");
			PurchaseVoucher pv = purchaseVoucherDA.getEntityObj(existingPurchaseVoucherId);
			if (pv != null) {
				purchaseVoucherDA.deleteEntityObj(pv);
				resJson.put("type", "success");
				resJson.put("msg", "Data Deleted Successfully..!!");
				resJson.put("data", pv);
			} else {
				resJson.put("type", "error");
				resJson.put("msg", "No data found..!!");
			}
		} catch (Exception e) {
			resJson.put("type", "error");
			resJson.put("msg", "Server error");
			e.printStackTrace();
		}
		return resJson;
	}

	@Override
	@Transactional
	public JSONObject multiDeleteEntityObj(String ids) {
		resJson = new JSONObject();
		try {
			String[] idArr = ids.split(",");
			int count = 0;
			for (String id : idArr) {
				if (!id.equalsIgnoreCase("")) {
					Integer existingPurchaseVoucherId = Integer.parseInt(id);
					PurchaseVoucher pv = purchaseVoucherDA.getEntityObj(existingPurchaseVoucherId);
					if (pv != null) {
						count++;
						purchaseVoucherDA.deleteEntityObj(pv);
					}
				}
			}
			if (count > 0) {
				resJson.put("type", "success");
				resJson.put("msg", "Data Deleted Successfully..!!");
				resJson.put("data", "");
			} else {
				resJson.put("type", "error");
				resJson.put("msg", "No data found..!!");
			}
		} catch (Exception e) {
			resJson.put("type", "error");
			resJson.put("msg", "Server error");
			e.printStackTrace();
		}
		return resJson;
	}

	@SuppressWarnings("rawtypes")
	@Override
	@Transactional
	public List<HashMap> getPurchaseReportSummary(String fromDate, String toDate) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		List<HashMap> purchaseReportArr = new JSONArray();
		List purchaseReportSummList = purchaseVoucherDA.getPurchaseReportSummary(sdf.parse(fromDate),
				sdf.parse(toDate));
		for (int i = 0; i < purchaseReportSummList.size(); i++) {
			Object[] column = (Object[]) purchaseReportSummList.get(i);
			HashMap hm = new HashMap();
			hm.put("partyName", column[0] != null ? column[0].toString() : "NA");
			hm.put("totalMilkKg",
					new BigDecimal(column[1] != null ? column[1].toString() : "0").setScale(2, RoundingMode.HALF_UP));
			hm.put("totalMilkPrice",
					new BigDecimal(column[2] != null ? column[2].toString() : "0").setScale(2, RoundingMode.HALF_UP));
			hm.put("totalKGFat",
					new BigDecimal(column[3] != null ? column[3].toString() : "0").setScale(2, RoundingMode.HALF_UP));
			hm.put("totalKGSnf",
					new BigDecimal(column[4] != null ? column[4].toString() : "0").setScale(2, RoundingMode.HALF_UP));
			hm.put("commission",
					new BigDecimal(column[5] != null ? column[5].toString() : "0").setScale(2, RoundingMode.HALF_UP));
			hm.put("splIncentive",
					new BigDecimal(column[6] != null ? column[6].toString() : "0").setScale(2, RoundingMode.HALF_UP));
			hm.put("headLoad",
					new BigDecimal(column[7] != null ? column[7].toString() : "0").setScale(2, RoundingMode.HALF_UP));
			hm.put("sourMilk",
					new BigDecimal(column[8] != null ? column[8].toString() : "0").setScale(2, RoundingMode.HALF_UP));
			hm.put("addAdvance",
					new BigDecimal(column[9] != null ? column[9].toString() : "0").setScale(2, RoundingMode.HALF_UP));
			hm.put("testEquipMent",
					new BigDecimal(column[10] != null ? column[10].toString() : "0").setScale(2, RoundingMode.HALF_UP));
			hm.put("lateArrival",
					new BigDecimal(column[11] != null ? column[11].toString() : "0").setScale(2, RoundingMode.HALF_UP));
			hm.put("arrear",
					new BigDecimal(column[12] != null ? column[12].toString() : "0").setScale(2, RoundingMode.HALF_UP));
			hm.put("adjustment",
					new BigDecimal(column[13] != null ? column[13].toString() : "0").setScale(2, RoundingMode.HALF_UP));
			hm.put("addOthers",
					new BigDecimal(column[14] != null ? column[14].toString() : "0").setScale(2, RoundingMode.HALF_UP));
			hm.put("deductAdvance",
					new BigDecimal(column[15] != null ? column[15].toString() : "0").setScale(2, RoundingMode.HALF_UP));
			hm.put("cattleFeed",
					new BigDecimal(column[16] != null ? column[16].toString() : "0").setScale(2, RoundingMode.HALF_UP));
			hm.put("mte",
					new BigDecimal(column[17] != null ? column[17].toString() : "0").setScale(2, RoundingMode.HALF_UP));
			hm.put("fodder",
					new BigDecimal(column[18] != null ? column[18].toString() : "0").setScale(2, RoundingMode.HALF_UP));
			hm.put("cowLoan",
					new BigDecimal(column[19] != null ? column[19].toString() : "0").setScale(2, RoundingMode.HALF_UP));
			hm.put("vaccine",
					new BigDecimal(column[20] != null ? column[20].toString() : "0").setScale(2, RoundingMode.HALF_UP));
			hm.put("share",
					new BigDecimal(column[21] != null ? column[21].toString() : "0").setScale(2, RoundingMode.HALF_UP));
			hm.put("deductOthers",
					new BigDecimal(column[22] != null ? column[22].toString() : "0").setScale(2, RoundingMode.HALF_UP));
			hm.put("totalAdditions",
					new BigDecimal(column[23] != null ? column[23].toString() : "0").setScale(2, RoundingMode.HALF_UP));
			hm.put("totalDeductions",
					new BigDecimal(column[24] != null ? column[24].toString() : "0").setScale(2, RoundingMode.HALF_UP));
			hm.put("billTotal",
					new BigDecimal(column[25] != null ? column[25].toString() : "0").setScale(2, RoundingMode.HALF_UP));
			purchaseReportArr.add(hm);
		}
		return purchaseReportArr;
	}

	@SuppressWarnings("rawtypes")
	@Override
	@Transactional
	public List<HashMap> getPurchaseReportDetailsBy(String fromDate, String toDate) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
		List<HashMap> purchaseReportArr = new JSONArray();
		List<PurchaseVoucher> purchaseReportSummList = purchaseVoucherDA
				.getPurchaseListFromDateToDate(sdf.parse(fromDate), sdf.parse(toDate));
		LinkedHashSet<String> lhs = new LinkedHashSet<>();
		LinkedHashSet<String> uniquePartyLhs = new LinkedHashSet<>();
		for (PurchaseVoucher pvoucher : purchaseReportSummList) {
			uniquePartyLhs.add(pvoucher.getPartyMaster().getPartyName());
		}
		ArrayList<String> uniquePartyNameList = new ArrayList<>(uniquePartyLhs);
		Collections.sort(uniquePartyNameList);
		for (String partyName : uniquePartyNameList) {
			for (PurchaseVoucher pvoucher : purchaseReportSummList) {
				if (partyName.equalsIgnoreCase(pvoucher.getPartyMaster().getPartyName())) {
					HashMap hm = new HashMap();
					hm.put("partyName",
							pvoucher.getPartyMaster() != null
									? lhs.add(pvoucher.getPartyMaster().getPartyName())
											? pvoucher.getPartyMaster().getPartyName()
											: ""
									: "NA");
					hm.put("billNo", pvoucher.getBillNo());
					hm.put("billDate", sdf1.format(pvoucher.getBillDate()));
					hm.put("totalMilkKg", new BigDecimal(pvoucher.getTotalQty() != null ? pvoucher.getTotalQty() : 0)
							.setScale(2, RoundingMode.HALF_UP));
					hm.put("totalMilkPrice",
							new BigDecimal(pvoucher.getTotalMilkprice() != null ? pvoucher.getTotalMilkprice() : 0)
									.setScale(2, RoundingMode.HALF_UP));
					hm.put("totalKGFat", new BigDecimal(pvoucher.getTotalKgfat() != null ? pvoucher.getTotalKgfat() : 0)
							.setScale(2, RoundingMode.HALF_UP));
					hm.put("totalKGSnf", new BigDecimal(pvoucher.getTotalKgsnf() != null ? pvoucher.getTotalKgsnf() : 0)
							.setScale(2, RoundingMode.HALF_UP));
					hm.put("commission",
							new BigDecimal(pvoucher.getCommissionValue() != null ? pvoucher.getCommissionValue() : 0)
									.setScale(2, RoundingMode.HALF_UP));
					hm.put("splIncentive",
							new BigDecimal(
									pvoucher.getSplIncentiveValue() != null ? pvoucher.getSplIncentiveValue() : 0)
											.setScale(2, RoundingMode.HALF_UP));
					hm.put("headLoad",
							new BigDecimal(pvoucher.getHeadLoadValue() != null ? pvoucher.getHeadLoadValue() : 0)
									.setScale(2, RoundingMode.HALF_UP));
					hm.put("sourMilk",
							new BigDecimal(pvoucher.getSourMilkValue() != null ? pvoucher.getSourMilkValue() : 0)
									.setScale(2, RoundingMode.HALF_UP));
					hm.put("addAdvance",
							new BigDecimal(pvoucher.getAddAdvanceValue() != null ? pvoucher.getAddAdvanceValue() : 0)
									.setScale(2, RoundingMode.HALF_UP));
					hm.put("testEquipMent",
							new BigDecimal(
									pvoucher.getTestEquipMentValue() != null ? pvoucher.getTestEquipMentValue() : 0)
											.setScale(2, RoundingMode.HALF_UP));
					hm.put("lateArrival",
							new BigDecimal(pvoucher.getLateArrivalValue() != null ? pvoucher.getLateArrivalValue() : 0)
									.setScale(2, RoundingMode.HALF_UP));
					hm.put("arrear", new BigDecimal(pvoucher.getArrearValue() != null ? pvoucher.getArrearValue() : 0)
							.setScale(2, RoundingMode.HALF_UP));
					hm.put("adjustment",
							new BigDecimal(pvoucher.getAdjustmentValue() != null ? pvoucher.getAdjustmentValue() : 0)
									.setScale(2, RoundingMode.HALF_UP));
					hm.put("addOthers",
							new BigDecimal(pvoucher.getAddOthersValue() != null ? pvoucher.getAddOthersValue() : 0)
									.setScale(2, RoundingMode.HALF_UP));
					hm.put("deductAdvance",
							new BigDecimal(
									pvoucher.getDeductAdvanceValue() != null ? pvoucher.getDeductAdvanceValue() : 0)
											.setScale(2, RoundingMode.HALF_UP));
					hm.put("cattleFeed",
							new BigDecimal(pvoucher.getCattleFeedValue() != null ? pvoucher.getCattleFeedValue() : 0)
									.setScale(2, RoundingMode.HALF_UP));
					hm.put("mte", new BigDecimal(pvoucher.getMTEValue() != null ? pvoucher.getMTEValue() : 0)
							.setScale(2, RoundingMode.HALF_UP));
					hm.put("fodder", new BigDecimal(pvoucher.getFodderValue() != null ? pvoucher.getFodderValue() : 0)
							.setScale(2, RoundingMode.HALF_UP));
					hm.put("cowLoan",
							new BigDecimal(pvoucher.getCowLoanValue() != null ? pvoucher.getCowLoanValue() : 0)
									.setScale(2, RoundingMode.HALF_UP));
					hm.put("vaccine",
							new BigDecimal(pvoucher.getVaccineValue() != null ? pvoucher.getVaccineValue() : 0)
									.setScale(2, RoundingMode.HALF_UP));
					hm.put("share", new BigDecimal(pvoucher.getShareValue() != null ? pvoucher.getShareValue() : 0)
							.setScale(2, RoundingMode.HALF_UP));
					hm.put("deductOthers",
							new BigDecimal(
									pvoucher.getDeductOthersValue() != null ? pvoucher.getDeductOthersValue() : 0)
											.setScale(2, RoundingMode.HALF_UP));
					hm.put("totalAdditions",
							new BigDecimal(pvoucher.getTotalAdditions() != null ? pvoucher.getTotalAdditions() : 0)
									.setScale(2, RoundingMode.HALF_UP));
					hm.put("totalDeductions",
							new BigDecimal(pvoucher.getTotalDeductions() != null ? pvoucher.getTotalDeductions() : 0)
									.setScale(2, RoundingMode.HALF_UP));
					hm.put("billTotal", new BigDecimal(pvoucher.getBillTotal() != null ? pvoucher.getBillTotal() : 0)
							.setScale(2, RoundingMode.HALF_UP));
					purchaseReportArr.add(hm);
				}
			}
		}

		return purchaseReportArr;
	}

	@SuppressWarnings("rawtypes")
	@Override
	@Transactional
	public List<HashMap> getPurchaseReportDetailsBy(String partyIds, String fromDate, String toDate)
			throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
		List<HashMap> purchaseReportArr = new JSONArray();
		String[] partyIdArr = partyIds.split(",");
		for (int i = 0; i < partyIdArr.length; i++) {
			List<PurchaseVoucher> purchaseReportSummList = purchaseVoucherDA.getPurchaseListByPartyIdFromDateToDate(
					Integer.parseInt(partyIdArr[i]), sdf.parse(fromDate), sdf.parse(toDate));
			LinkedHashSet<String> lhs = new LinkedHashSet<>();
			for (PurchaseVoucher pvoucher : purchaseReportSummList) {
				HashMap hm = new HashMap();
				hm.put("partyName", pvoucher.getPartyMaster() != null
						? lhs.add(pvoucher.getPartyMaster().getPartyName()) ? pvoucher.getPartyMaster().getPartyName()
								: ""
						: "NA");
				hm.put("billNo", pvoucher.getBillNo());
				hm.put("billDate", sdf1.format(pvoucher.getBillDate()));
				hm.put("totalMilkKg", new BigDecimal(pvoucher.getTotalQty() != null ? pvoucher.getTotalQty() : 0)
						.setScale(2, RoundingMode.HALF_UP));
				hm.put("totalMilkPrice",
						new BigDecimal(pvoucher.getTotalMilkprice() != null ? pvoucher.getTotalMilkprice() : 0)
								.setScale(2, RoundingMode.HALF_UP));
				hm.put("totalKGFat", new BigDecimal(pvoucher.getTotalKgfat() != null ? pvoucher.getTotalKgfat() : 0)
						.setScale(2, RoundingMode.HALF_UP));
				hm.put("totalKGSnf", new BigDecimal(pvoucher.getTotalKgsnf() != null ? pvoucher.getTotalKgsnf() : 0)
						.setScale(2, RoundingMode.HALF_UP));
				hm.put("commission",
						new BigDecimal(pvoucher.getCommissionValue() != null ? pvoucher.getCommissionValue() : 0)
								.setScale(2, RoundingMode.HALF_UP));
				hm.put("splIncentive",
						new BigDecimal(pvoucher.getSplIncentiveValue() != null ? pvoucher.getSplIncentiveValue() : 0)
								.setScale(2, RoundingMode.HALF_UP));
				hm.put("headLoad", new BigDecimal(pvoucher.getHeadLoadValue() != null ? pvoucher.getHeadLoadValue() : 0)
						.setScale(2, RoundingMode.HALF_UP));
				hm.put("sourMilk", new BigDecimal(pvoucher.getSourMilkValue() != null ? pvoucher.getSourMilkValue() : 0)
						.setScale(2, RoundingMode.HALF_UP));
				hm.put("addAdvance",
						new BigDecimal(pvoucher.getAddAdvanceValue() != null ? pvoucher.getAddAdvanceValue() : 0)
								.setScale(2, RoundingMode.HALF_UP));
				hm.put("testEquipMent",
						new BigDecimal(pvoucher.getTestEquipMentValue() != null ? pvoucher.getTestEquipMentValue() : 0)
								.setScale(2, RoundingMode.HALF_UP));
				hm.put("lateArrival",
						new BigDecimal(pvoucher.getLateArrivalValue() != null ? pvoucher.getLateArrivalValue() : 0)
								.setScale(2, RoundingMode.HALF_UP));
				hm.put("arrear", new BigDecimal(pvoucher.getArrearValue() != null ? pvoucher.getArrearValue() : 0)
						.setScale(2, RoundingMode.HALF_UP));
				hm.put("adjustment",
						new BigDecimal(pvoucher.getAdjustmentValue() != null ? pvoucher.getAdjustmentValue() : 0)
								.setScale(2, RoundingMode.HALF_UP));
				hm.put("addOthers",
						new BigDecimal(pvoucher.getAddOthersValue() != null ? pvoucher.getAddOthersValue() : 0)
								.setScale(2, RoundingMode.HALF_UP));
				hm.put("deductAdvance",
						new BigDecimal(pvoucher.getDeductAdvanceValue() != null ? pvoucher.getDeductAdvanceValue() : 0)
								.setScale(2, RoundingMode.HALF_UP));
				hm.put("cattleFeed",
						new BigDecimal(pvoucher.getCattleFeedValue() != null ? pvoucher.getCattleFeedValue() : 0)
								.setScale(2, RoundingMode.HALF_UP));
				hm.put("mte", new BigDecimal(pvoucher.getMTEValue() != null ? pvoucher.getMTEValue() : 0).setScale(2,
						RoundingMode.HALF_UP));
				hm.put("fodder", new BigDecimal(pvoucher.getFodderValue() != null ? pvoucher.getFodderValue() : 0)
						.setScale(2, RoundingMode.HALF_UP));
				hm.put("cowLoan", new BigDecimal(pvoucher.getCowLoanValue() != null ? pvoucher.getCowLoanValue() : 0)
						.setScale(2, RoundingMode.HALF_UP));
				hm.put("vaccine", new BigDecimal(pvoucher.getVaccineValue() != null ? pvoucher.getVaccineValue() : 0)
						.setScale(2, RoundingMode.HALF_UP));
				hm.put("share", new BigDecimal(pvoucher.getShareValue() != null ? pvoucher.getShareValue() : 0)
						.setScale(2, RoundingMode.HALF_UP));
				hm.put("deductOthers",
						new BigDecimal(pvoucher.getDeductOthersValue() != null ? pvoucher.getDeductOthersValue() : 0)
								.setScale(2, RoundingMode.HALF_UP));
				hm.put("totalAdditions",
						new BigDecimal(pvoucher.getTotalAdditions() != null ? pvoucher.getTotalAdditions() : 0)
								.setScale(2, RoundingMode.HALF_UP));
				hm.put("totalDeductions",
						new BigDecimal(pvoucher.getTotalDeductions() != null ? pvoucher.getTotalDeductions() : 0)
								.setScale(2, RoundingMode.HALF_UP));
				hm.put("billTotal", new BigDecimal(pvoucher.getBillTotal() != null ? pvoucher.getBillTotal() : 0)
						.setScale(2, RoundingMode.HALF_UP));
				purchaseReportArr.add(hm);
			}
		}
		return purchaseReportArr;
	}

	@Override
	@Transactional
	public JSONObject getPurchaseListBy(Integer sessionId, String fromDate, String toDate) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		resJson = new JSONObject();
		try {
			List<PurchaseVoucher> pvList = purchaseVoucherDA.getPurchaseListFromDateToDate(sessionId,
					sdf.parse(fromDate), sdf.parse(toDate));
			if (pvList != null && !pvList.isEmpty()) {
				resJson.put("type", "success");
				resJson.put("msg", "success");
				resJson.put("data", pvList);
			} else {
				resJson.put("type", "error");
				resJson.put("msg", "No data found..!!");
				resJson.put("data", "");
			}
		} catch (Exception e) {
			resJson.put("type", "error");
			resJson.put("msg", "Server error");
			e.printStackTrace();
		}
		return resJson;
	}

	@Override
	@Transactional
	public JSONObject getPurchaseListByPeriodDate(Integer sessionId, String fromDate, String toDate)
			throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		resJson = new JSONObject();
		try {
			List<PurchaseVoucher> pvList = purchaseVoucherDA.getPurchaseListPeriodFromDateToDate(sessionId,
					sdf.parse(fromDate), sdf.parse(toDate));
			if (pvList != null && !pvList.isEmpty()) {
				resJson.put("type", "success");
				resJson.put("msg", "success");
				resJson.put("data", pvList);
			} else {
				resJson.put("type", "error");
				resJson.put("msg", "No data found..!!");
				resJson.put("data", "");
			}
		} catch (Exception e) {
			resJson.put("type", "error");
			resJson.put("msg", "Server error");
			e.printStackTrace();
		}
		return resJson;
	}

	@Override
	@Transactional
	public JSONObject getPurchaseListBy(Integer sessionId, Integer partyId, String fromDate, String toDate)
			throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		resJson = new JSONObject();
		try {
			List<PurchaseVoucher> pvList = purchaseVoucherDA.getPurchaseListByPartyIdFromDateToDate(sessionId, partyId,
					sdf.parse(fromDate), sdf.parse(toDate));
			if (pvList != null && !pvList.isEmpty()) {
				resJson.put("type", "success");
				resJson.put("msg", "success");
				resJson.put("data", pvList);
			} else {
				resJson.put("type", "error");
				resJson.put("msg", "No data found..!!");
				resJson.put("data", "");
			}
		} catch (Exception e) {
			resJson.put("type", "error");
			resJson.put("msg", "Server error");
			e.printStackTrace();
		}
		return resJson;
	}

	@Override
	@Transactional
	public JSONObject getPurchaseListByPartyPeriod(Integer sessionId, Integer partyId, String fromDate, String toDate)
			throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		resJson = new JSONObject();
		try {
			List<PurchaseVoucher> pvList = purchaseVoucherDA.getPurchaseListByPartyIdPeriodFromDateToDate(sessionId, partyId,
					sdf.parse(fromDate), sdf.parse(toDate));
			if (pvList != null && !pvList.isEmpty()) {
				resJson.put("type", "success");
				resJson.put("msg", "success");
				resJson.put("data", pvList);
			} else {
				resJson.put("type", "error");
				resJson.put("msg", "No data found..!!");
				resJson.put("data", "");
			}
		} catch (Exception e) {
			resJson.put("type", "error");
			resJson.put("msg", "Server error");
			e.printStackTrace();
		}
		return resJson;
	}

	@Override
	@Transactional
	public JSONObject getPurchaseVoucherBy(Integer sessionId, Integer billno) throws ParseException {
		resJson = new JSONObject();
		try {
			List<PurchaseVoucher> pvList = purchaseVoucherDA.getPurchaseVoucherByBillno(sessionId, billno);
			if (pvList != null && !pvList.isEmpty()) {
				if (pvList.get(0) != null) {
					resJson.put("type", "success");
					resJson.put("msg", "success");
					resJson.put("data", pvList.get(0));
				}
			} else {
				resJson.put("type", "error");
				resJson.put("msg", "No data found..!!");
				resJson.put("data", "");
			}
		} catch (Exception e) {
			resJson.put("type", "error");
			resJson.put("msg", "Server error");
			e.printStackTrace();
		}
		return resJson;
	}
}