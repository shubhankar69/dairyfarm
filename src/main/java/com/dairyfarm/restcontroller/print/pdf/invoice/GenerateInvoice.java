package com.dairyfarm.restcontroller.print.pdf.invoice;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dairyfarm.entity.txn.PurchaseVoucher;
import com.dairyfarm.entity.txn.PurchaseVoucherDetails;
import com.dairyfarm.helper.AmountToWordConverter;
import com.dairyfarm.service.txn.PurchaseVoucherService;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.WriterProperties;

@RestController
@RequestMapping("/invoice")
public class GenerateInvoice {

//	public static final String BASEURI = "C:\\Users\\sdutt\\OneDrive\\Desktop\\Itext7PdfHtml\\Source\\";
//	public static final String VMFILENAME = "purchaseInvoice.html";
//	public static final String LOGOPATH = "C:\\Users\\sdutt\\OneDrive\\Desktop\\Itext7PdfHtml\\Source\\anmol_logo.jpg/";
//	public static final String TARGET = "C:\\Users\\sdutt\\OneDrive\\Desktop\\Itext7PdfHtml/Destination\\";
//	public static final String DEST = String.format("%svmDemoHTML1.pdf", TARGET);
	
	@Autowired
	@Qualifier("purchaseVoucherService")
	private PurchaseVoucherService<PurchaseVoucher> purchaseVoucherDA;
	private SimpleDateFormat ddMMyyyy = null;
	private AmountToWordConverter amountToWord = null;
	
	public static final String BASEURI = "reports\\";

	@RequestMapping(value = "/print/{id}", method = RequestMethod.GET, produces = "application/pdf")
	public ResponseEntity<byte[]> getInvoicePDF(@PathVariable(name = "id") Integer id) throws IOException {
		ResponseEntity<byte[]> response = null;
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_PDF);
			headers.add("Access-Control-Allow-Origin", "*");
			headers.add("Access-Control-Allow-Methods", "GET");
			headers.add("Access-Control-Allow-Headers", "Content-Type");
			headers.add("Content-Disposition", "filename=" + "Invoice.pdf");
			headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
			headers.add("Pragma", "no-cache");
			headers.add("Expires", "0");

			response = new ResponseEntity<>(generate(id), headers, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public byte[] generate(Integer theId) throws IOException {
//        LicenseKey.loadLicenseFile("/home/suman/Desktop/itextkey1555063329632_0.xml");
//        File file = new File(TARGET);
//        file.mkdirs();

//        VelocityEngine engine = new VelocityEngine();
//        Properties prop = new Properties();
//        prop.put("file.resource.loader.path", BASEURI);
//        engine.init(prop);
		
        ddMMyyyy = new SimpleDateFormat("dd-MM-yyyy");
        amountToWord = new AmountToWordConverter();

		VelocityEngine engine = new VelocityEngine();
		engine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
		engine.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
		engine.init();
		
		VelocityContext context = new VelocityContext();
		List<HashMap> purchaseDetailsArr = new JSONArray();
		PurchaseVoucher pv = purchaseVoucherDA.getPurchaseVoucherObj(theId);
		if(pv != null) {
			BigDecimal totalMilkPrice = pv.getTotalMilkprice() != null ? new BigDecimal(pv.getTotalMilkprice()).setScale(2, RoundingMode.HALF_UP) : BigDecimal.ZERO;
			BigDecimal totalAdditions = pv.getTotalAdditions() != null ? new BigDecimal(pv.getTotalAdditions()).setScale(2, RoundingMode.HALF_UP) : BigDecimal.ZERO;
			BigDecimal grossAmount = totalMilkPrice.add(totalAdditions).setScale(2, RoundingMode.HALF_UP);
			BigDecimal billTotal = pv.getBillTotal() != null ? new BigDecimal(pv.getBillTotal()).setScale(2, RoundingMode.HALF_UP) : BigDecimal.ZERO;
			
			context.put("billNo", pv.getBillNo());
			context.put("billDate", ddMMyyyy.format(pv.getBillDate()));
			context.put("periodFromDate", ddMMyyyy.format(pv.getPeriodFromDate()));
			context.put("periodToDate", ddMMyyyy.format(pv.getPeriodToDate()));
			context.put("partyName", pv.getPartyMaster().getPartyName());
			context.put("partyAddress", pv.getPartyMaster().getAddress());
			context.put("plantName", pv.getPartyMaster().getPlantName());
			context.put("accNo", pv.getPartyMaster().getPartyBankAccNo());
			context.put("totalQty", pv.getTotalQty() != null ? new BigDecimal(pv.getTotalQty()).setScale(3, RoundingMode.HALF_UP) : "");
			context.put("totalFatAmount", pv.getTotalFatAmount() != null ? new BigDecimal(pv.getTotalFatAmount()).setScale(2, RoundingMode.HALF_UP) : "");
			context.put("totalSnfAmount", pv.getTotalSnfAmount() != null ? new BigDecimal(pv.getTotalSnfAmount()).setScale(2, RoundingMode.HALF_UP) : "");
			context.put("totalMilkPrice", pv.getTotalMilkprice() != null ? new BigDecimal(pv.getTotalMilkprice()).setScale(2, RoundingMode.HALF_UP) : "");
			context.put("commissionNarration", pv.getCommissionNarration() != null ? pv.getCommissionNarration() : "");
			context.put("commissionValue", pv.getCommissionValue() != null ? new BigDecimal(pv.getCommissionValue()).setScale(2, RoundingMode.HALF_UP) : "");
			context.put("splIncentiveNarration", pv.getSplIncentiveNarration() != null ? pv.getSplIncentiveNarration() : "");
			context.put("splIncentiveValue", pv.getSplIncentiveValue() != null ? new BigDecimal(pv.getSplIncentiveValue()).setScale(2, RoundingMode.HALF_UP) : "");
			context.put("headLoadNarration", pv.getHeadLoadNarration() != null ? pv.getHeadLoadNarration() : "");
			context.put("headLoadValue", pv.getHeadLoadValue() != null ? new BigDecimal(pv.getHeadLoadValue()).setScale(2, RoundingMode.HALF_UP) : "");
			context.put("sourMilkNarration", pv.getSourMilkNarration() != null ? pv.getSourMilkNarration() : "");
			context.put("sourMilkValue", pv.getSourMilkValue() != null ? new BigDecimal(pv.getSourMilkValue()).setScale(2, RoundingMode.HALF_UP) : "");
			context.put("addAdvanceNarration", pv.getAddAdvanceNarration() != null ? pv.getAddAdvanceNarration() : "");
			context.put("addAdvanceValue", pv.getAddAdvanceValue() != null ? new BigDecimal(pv.getAddAdvanceValue()).setScale(2, RoundingMode.HALF_UP) : "");
			context.put("testEquipMentNarration", pv.getTestEquipMentNarration() != null ? pv.getTestEquipMentNarration() : "");
			context.put("testEquipMentValue", pv.getTestEquipMentValue() != null ? new BigDecimal(pv.getTestEquipMentValue()).setScale(2, RoundingMode.HALF_UP) : "");
			context.put("lateArrivalNarration", pv.getLateArrivalNarration() != null ? pv.getLateArrivalNarration() : "");
			context.put("lateArrivalValue", pv.getLateArrivalValue() != null ? new BigDecimal(pv.getLateArrivalValue()).setScale(2, RoundingMode.HALF_UP) : "");
			context.put("arrearNarration", pv.getArrearNarration() != null ? pv.getArrearNarration() : "");
			context.put("arrearValue", pv.getArrearValue() != null ? new BigDecimal(pv.getArrearValue()).setScale(2, RoundingMode.HALF_UP) : "");
			context.put("adjustmentNarration", pv.getAdjustmentNarration() != null ? pv.getAdjustmentNarration() : "");
			context.put("adjustmentValue", pv.getAdjustmentValue() != null ? new BigDecimal(pv.getAdjustmentValue()).setScale(2, RoundingMode.HALF_UP) : "");
			context.put("addOthersNarration", pv.getAddOthersNarration() != null ? pv.getAddOthersNarration() : "");
			context.put("addOthersValue", pv.getAddOthersValue() != null ? new BigDecimal(pv.getAddOthersValue()).setScale(2, RoundingMode.HALF_UP) : "");
			context.put("totalAdditions", pv.getTotalAdditions() != null ? new BigDecimal(pv.getTotalAdditions()).setScale(2, RoundingMode.HALF_UP) : "");
			context.put("deductAdvanceNarration", pv.getDeductAdvanceNarration() != null ? pv.getDeductAdvanceNarration() : "");
			context.put("deductAdvanceValue", pv.getDeductAdvanceValue() != null ? new BigDecimal(pv.getDeductAdvanceValue()).setScale(2, RoundingMode.HALF_UP) : "");
			context.put("cattleFeedNarration", pv.getCattleFeedNarration() != null ? pv.getCattleFeedNarration() : "");
			context.put("cattleFeedValue", pv.getCattleFeedValue() != null ? new BigDecimal(pv.getCattleFeedValue()).setScale(2, RoundingMode.HALF_UP) : "");
			context.put("MTENarration", pv.getMTENarration() != null ? pv.getMTENarration() : "");
			context.put("MTEValue", pv.getMTEValue() != null ? new BigDecimal(pv.getMTEValue()).setScale(2, RoundingMode.HALF_UP) : "");
			context.put("fodderNarration", pv.getFodderNarration() != null ? pv.getFodderNarration() : "");
			context.put("fodderValue", pv.getFodderValue() != null ? new BigDecimal(pv.getFodderValue()).setScale(2, RoundingMode.HALF_UP) : "");
			context.put("cowLoanNarration", pv.getCowLoanNarration() != null ? pv.getCowLoanNarration() : "");
			context.put("cowLoanValue", pv.getCowLoanValue() != null ? new BigDecimal(pv.getCowLoanValue()).setScale(2, RoundingMode.HALF_UP) : "");
			context.put("vaccineNarration", pv.getVaccineNarration() != null ? pv.getVaccineNarration() : "");
			context.put("vaccineValue", pv.getVaccineValue() != null ? new BigDecimal(pv.getVaccineValue()).setScale(2, RoundingMode.HALF_UP) : "");
			context.put("shareNarration", pv.getShareNarration() != null ? pv.getShareNarration() : "");
			context.put("shareValue", pv.getShareValue() != null ? new BigDecimal(pv.getShareValue()).setScale(2, RoundingMode.HALF_UP) : "");
			context.put("deductOthersNarration", pv.getDeductOthersNarration() != null ? pv.getDeductOthersNarration() : "");
			context.put("deductOthersValue", pv.getDeductOthersValue() != null ? new BigDecimal(pv.getDeductOthersValue()).setScale(2, RoundingMode.HALF_UP) : "");
			context.put("totalDeductions", pv.getTotalDeductions() != null ? new BigDecimal(pv.getTotalDeductions()).setScale(2, RoundingMode.HALF_UP) : "");
			context.put("grossAmount", grossAmount);
			context.put("billTotal", billTotal);
			context.put("billTotalAmtToWord", amountToWord.convertToIndianCurrency(billTotal.toString()));
			
			for (PurchaseVoucherDetails pvDeatilsObj : pv.getPurchaseVoucherDetails()) {
				HashMap map = new HashMap();
				map.put("supplyDate", ddMMyyyy.format(pvDeatilsObj.getDate()));
				map.put("qty", pvDeatilsObj.getQty() != null ? new BigDecimal(pvDeatilsObj.getQty()).setScale(3, RoundingMode.HALF_UP) : BigDecimal.ZERO);
				map.put("shift", pvDeatilsObj.getShift() != null && !pvDeatilsObj.getShift().trim().equalsIgnoreCase("") ? pvDeatilsObj.getShift().charAt(0) : "");
				map.put("fatP", pvDeatilsObj.getFatP() != null ? new BigDecimal(pvDeatilsObj.getFatP()).setScale(2, RoundingMode.HALF_UP) : BigDecimal.ZERO);
				map.put("fatQuality", pvDeatilsObj.getFatQuality() != null ? pvDeatilsObj.getFatQuality() : "");
				map.put("snfP", pvDeatilsObj.getSnfP() != null ? new BigDecimal(pvDeatilsObj.getSnfP()).setScale(2, RoundingMode.HALF_UP) : BigDecimal.ZERO);
				map.put("snfQuality", pvDeatilsObj.getSnfQuality() != null ? pvDeatilsObj.getSnfQuality() : "");
				map.put("fateRate", pvDeatilsObj.getFatRate() != null ? new BigDecimal(pvDeatilsObj.getFatRate()).setScale(2, RoundingMode.HALF_UP) : BigDecimal.ZERO);
				map.put("snfRate", pvDeatilsObj.getSnfRate() != null ? new BigDecimal(pvDeatilsObj.getSnfRate()).setScale(2, RoundingMode.HALF_UP) : BigDecimal.ZERO);
				map.put("milkRate", pvDeatilsObj.getMilkRate() != null ? new BigDecimal(pvDeatilsObj.getMilkRate()).setScale(2, RoundingMode.HALF_UP) : BigDecimal.ZERO);
				map.put("fatAmount", pvDeatilsObj.getFatAmount() != null ? new BigDecimal(pvDeatilsObj.getFatAmount()).setScale(2, RoundingMode.HALF_UP) : BigDecimal.ZERO);
				map.put("snfAmount", pvDeatilsObj.getSnfAmount() != null ? new BigDecimal(pvDeatilsObj.getSnfAmount()).setScale(2, RoundingMode.HALF_UP) : BigDecimal.ZERO);
				map.put("milkPrice", pvDeatilsObj.getMilkPrice() != null ? new BigDecimal(pvDeatilsObj.getMilkPrice()).setScale(2, RoundingMode.HALF_UP) : BigDecimal.ZERO);
				purchaseDetailsArr.add(map);
			}
			
			context.put("purchaseDetails", purchaseDetailsArr);
		}
		
		StringWriter writer = new StringWriter();
		Template template = engine.getTemplate("reports/purchaseinvoice.html");
		template.merge(context, writer);
		
		return createPdf(BASEURI, writer.toString());
	}

	public byte[] createPdf(String baseUri, String html) throws IOException {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
//      ConverterProperties properties = new ConverterProperties();
//      properties.setBaseUri(baseUri);
//      HtmlConverter.convertToPdf(html, new FileOutputStream(dest), properties);
//      HtmlConverter.convertToPdf(new File(src), new File(dest));

		ConverterProperties properties = new ConverterProperties();
		properties.setBaseUri(baseUri);
		PdfWriter writer = new PdfWriter(baos, new WriterProperties().setFullCompressionMode(true));
		HtmlConverter.convertToPdf(html, writer, properties);
		/* for logo */
//      Document document = HtmlConverter.convertToDocument(html, writer, properties);

//      ImageData imageData = ImageDataFactory.create(LOGOPATH);
//      Image image = new Image(imageData).scaleAbsolute(110, 48).setFixedPosition(35, 498);//anmol
//      Image image = new Image(imageData).scaleAbsolute(110, 48).setFixedPosition(35, 738);//ambo
//      PdfDocument pdfDoc = document.getPdfDocument();
//      ImageEventHandler handler = new ImageEventHandler(image); 
//      pdfDoc.addEventHandler(PdfDocumentEvent.END_PAGE, handler);
//      pdfDoc.close();
//      document.add(image);
//      document.close();
		return baos.toByteArray();
	}
}