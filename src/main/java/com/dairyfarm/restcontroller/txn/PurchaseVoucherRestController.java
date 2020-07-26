package com.dairyfarm.restcontroller.txn;

import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;

import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dairyfarm.entity.txn.PurchaseVoucher;
import com.dairyfarm.entity.txn.PurchaseVoucherDetails;
import com.dairyfarm.service.txn.PurchaseVoucherService;
import com.fasterxml.jackson.databind.ObjectMapper;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/purchasetxn")
public class PurchaseVoucherRestController {
	
	@Autowired
	@Qualifier("purchaseVoucherService")
	private PurchaseVoucherService<PurchaseVoucher> pvService;

	@GetMapping("/fetch/all")
	public JSONObject getAllPurchaseVoucherList(HttpSession session) {
		Integer sessionId = Integer.parseInt(session.getAttribute("sessionId").toString());
//		Integer sessionId = 1;
		return pvService.getAllListofData(sessionId);
	}
	
	@GetMapping("/fetch/{sessionId}/{frmDate}/{toDate}")
	public JSONObject getPurchaseVoucherListByBillDate(@PathVariable(name = "sessionId") Integer sessionId, @PathVariable(name = "frmDate") String fdate, @PathVariable(name = "toDate") String tdate, HttpSession session) throws ParseException {
		sessionId = Integer.parseInt(session.getAttribute("sessionId").toString());
//		sessionId = 1;
		return pvService.getPurchaseListBy(sessionId, fdate, tdate);
	}
	
	@GetMapping("/fetchbyperiod/{sessionId}/{periodfrmDate}/{periodtoDate}")
	public JSONObject getPurchaseVoucherListByPeriodDate(@PathVariable(name = "sessionId") Integer sessionId, @PathVariable(name = "periodfrmDate") String fdate, @PathVariable(name = "periodtoDate") String tdate, HttpSession session) throws ParseException {
		sessionId = Integer.parseInt(session.getAttribute("sessionId").toString());
//		sessionId = 1;
		return pvService.getPurchaseListByPeriodDate(sessionId, fdate, tdate);
	}
	
	@GetMapping("/fetch/{sessionId}/{partyId}/{frmDate}/{toDate}")
	public JSONObject getPurchaseVoucherListByParty(@PathVariable(name = "sessionId") Integer sessionId, @PathVariable(name = "partyId") Integer partyId, @PathVariable(name = "frmDate") String fdate, @PathVariable(name = "toDate") String tdate, HttpSession session) throws ParseException {
		sessionId = Integer.parseInt(session.getAttribute("sessionId").toString());
//		sessionId = 1;
		return pvService.getPurchaseListBy(sessionId, partyId, fdate, tdate);
	}
	
	@GetMapping("/fetchbyperiod/{sessionId}/{partyId}/{periodfrmDate}/{periodtoDate}")
	public JSONObject getPurchaseVoucherListByPartyPeriod(@PathVariable(name = "sessionId") Integer sessionId, @PathVariable(name = "partyId") Integer partyId, @PathVariable(name = "periodfrmDate") String fdate, @PathVariable(name = "periodtoDate") String tdate, HttpSession session) throws ParseException {
		sessionId = Integer.parseInt(session.getAttribute("sessionId").toString());
//		sessionId = 1;
		return pvService.getPurchaseListByPartyPeriod(sessionId, partyId, fdate, tdate);
	}
	
	@GetMapping("/fetchbybillno/{billNo}")
	public JSONObject getPurchaseVoucherByBillNo(@PathVariable(name = "billNo") Integer billNo, HttpSession session) throws ParseException {
		Integer sessionId = Integer.parseInt(session.getAttribute("sessionId").toString());
//		Integer sessionId = 1;
		return pvService.getPurchaseVoucherBy(sessionId, billNo);
	}
	
	@GetMapping("/fetch/{id}")
	public JSONObject getBankMasterById(@PathVariable int id) {
		return pvService.getJsonObj(id);
	}
	
	@GetMapping("/getmaxbillno")
	public Integer getMaxBillno(HttpSession session) {
		Integer sessionId = Integer.parseInt(session.getAttribute("sessionId").toString());
//		Integer sessionId = 1;
		return pvService.getMaxBillno(sessionId);
	}
	
	@PostMapping("/create")
	public JSONObject createPurchaseVoucher(@RequestBody PurchaseVoucher pv, HttpSession session) {
		Integer sessionId = Integer.parseInt(session.getAttribute("sessionId").toString());
//		Integer sessionId = 1;
		return pvService.saveEntityPurchaseObj(pv, sessionId);
	}
	
	
	/* for export and import*/
	@PostMapping("/createall")
	public void createPurchaseVoucherAll() throws IOException, org.json.simple.parser.ParseException {
		ObjectMapper mapper = new ObjectMapper();
		FileReader fis = new FileReader("C:\\Users\\sdutt\\Desktop\\abc.json");
		JSONObject jsonObject = (JSONObject) new JSONParser().parse(fis);
		JSONArray jarr = (JSONArray) jsonObject.get("data");
		for (int i = 0; i < jarr.size(); i++) {
			JSONObject jobj = (JSONObject) jarr.get(i);
			String dataS = mapper.writeValueAsString(jobj);
			PurchaseVoucher pv = mapper.readValue(dataS, PurchaseVoucher.class);
			pv.setId(null);
			for (int j = 0; j < pv.getPurchaseVoucherDetails().size(); j++) {
				PurchaseVoucherDetails details = pv.getPurchaseVoucherDetails().get(j);
				details.setId(null);
			}
			pvService.savePurchaseVoucherObj(pv);
		}
		fis.close();
	}
	
	@PostMapping("/update")
	public JSONObject updatePurchaseVoucher(@RequestBody JSONObject purchaseVoucherjson) {
		return pvService.updateEntityObj(purchaseVoucherjson);
	}
	
	@PostMapping("/delete")
	public JSONObject deletePurchaseVoucher(@RequestBody JSONObject pv) {
		return pvService.deleteEntityObj(pv);
	}
	
	@PostMapping("/multidelete/{ids}")
	public JSONObject multiDeletePurchaseVoucher(@PathVariable String ids) {
		return pvService.multiDeleteEntityObj(ids);
	}
}