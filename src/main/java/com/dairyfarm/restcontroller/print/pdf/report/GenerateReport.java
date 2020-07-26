package com.dairyfarm.restcontroller.print.pdf.report;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dairyfarm.da.master.PartyMasterDA;
import com.dairyfarm.entity.master.PartyMaster;
import com.dairyfarm.entity.txn.PurchaseVoucher;
import com.dairyfarm.service.txn.PurchaseVoucherService;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.WriterProperties;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/report")
public class GenerateReport {

	public static final String BASEURI = "reports\\";

	@Autowired
	@Qualifier("purchaseVoucherService")
	private PurchaseVoucherService<PurchaseVoucher> pvImpl;

	@Autowired
	@Qualifier("partymasterDA")
	private PartyMasterDA<PartyMaster> partyMasterDA;

	private SimpleDateFormat yyyyMMdd = null;
	private SimpleDateFormat ddMMyyyy = null;

	@RequestMapping(value = "/partyprintlist", method = RequestMethod.GET, produces = "application/pdf")
	public ResponseEntity<byte[]> getPartyReportPDF() throws IOException {
		ResponseEntity<byte[]> response = null;
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_PDF);
			headers.add("Access-Control-Allow-Origin", "*");
			headers.add("Access-Control-Allow-Methods", "GET");
			headers.add("Access-Control-Allow-Headers", "Content-Type");
			headers.add("Content-Disposition", "filename=" + "Supplier Report.pdf");
			headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
			headers.add("Pragma", "no-cache");
			headers.add("Expires", "0");
			response = new ResponseEntity<>(partyReport(), headers, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	@RequestMapping(value = "/purchasesummary/{fdate}/{tdate}", method = RequestMethod.GET, produces = "application/pdf")
	public ResponseEntity<byte[]> getpurchaseSummaryReportPDF(@PathVariable(name = "fdate") String fdate,
			@PathVariable(name = "tdate") String tdate) throws IOException {
		ResponseEntity<byte[]> response = null;
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_PDF);
			headers.add("Access-Control-Allow-Origin", "*");
			headers.add("Access-Control-Allow-Methods", "GET");
			headers.add("Access-Control-Allow-Headers", "Content-Type");
			headers.add("Content-Disposition",
					"filename=" + "PurchaseSummaryReport_" + fdate + "_to_" + tdate + ".pdf");
			headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
			headers.add("Pragma", "no-cache");
			headers.add("Expires", "0");
			response = new ResponseEntity<>(purchaseSummaryReport(fdate, tdate), headers, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	@RequestMapping(value = "/purchasedetail/{partyNames}/{fdate}/{tdate}", method = RequestMethod.GET)
	public ResponseEntity<byte[]> getpurchaseDetailReportPDF(@PathVariable(name = "partyNames") String partyNames,
			@PathVariable(name = "fdate") String fdate, @PathVariable(name = "tdate") String tdate) throws IOException {
		ResponseEntity<byte[]> response = null;
		try {

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_PDF);
			headers.add("Access-Control-Allow-Origin", "*");
			headers.add("Access-Control-Allow-Methods", "GET");
			headers.add("Access-Control-Allow-Headers", "Content-Type");
			headers.add("Content-Disposition", "filename=" + "PurchaseDetailReport_" + fdate + "_to_" + tdate + ".pdf");
			headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
			headers.add("Pragma", "no-cache");
			headers.add("Expires", "0");

			response = new ResponseEntity<>(purchaseDetailsReportBy(partyNames, fdate, tdate), headers, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	@RequestMapping(value = "/purchasedetail/{fdate}/{tdate}", method = RequestMethod.GET)
	public ResponseEntity<byte[]> getpurchaseDetailReportPDF(@PathVariable(name = "fdate") String fdate,
			@PathVariable(name = "tdate") String tdate) throws IOException {
		ResponseEntity<byte[]> response = null;
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_PDF);
			headers.add("Access-Control-Allow-Origin", "*");
			headers.add("Access-Control-Allow-Methods", "GET");
			headers.add("Access-Control-Allow-Headers", "Content-Type");
			headers.add("Content-Disposition", "filename=" + "PurchaseDetailReport_" + fdate + "_to_" + tdate + ".pdf");
			headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
			headers.add("Pragma", "no-cache");
			headers.add("Expires", "0");

			response = new ResponseEntity<>(purchaseDetailsReportBy(fdate, tdate), headers, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public byte[] partyReport() throws IOException, ParseException {

		VelocityEngine engine = new VelocityEngine();
		engine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
		engine.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
		engine.init();

		VelocityContext context = new VelocityContext();

		List<HashMap> partyList = new ArrayList<>();
		List<PartyMaster> pList = partyMasterDA.getEntityObjList();
		if (pList != null && !pList.isEmpty()) {
			for (PartyMaster partyMaster : pList) {
				if (partyMaster != null) {
					HashMap hm = new HashMap();
					hm.put("name", partyMaster.getPartyName());
					hm.put("address",
							partyMaster.getAddress() != null && !partyMaster.getAddress().equalsIgnoreCase("")
									? partyMaster.getAddress()
									: "");
					hm.put("phone",
							partyMaster.getPhone() != null && !partyMaster.getPhone().equalsIgnoreCase("")
									? partyMaster.getPhone()
									: "");
					partyList.add(hm);
				}
			}
		}
		context.put("partyList", partyList);

		StringWriter writer = new StringWriter();
		Template template = engine.getTemplate("reports/partyReport.vm");
		template.merge(context, writer);

		return createPdf(BASEURI, writer.toString());
	}

	public byte[] purchaseSummaryReport(String fromDate, String toDate) throws IOException, ParseException {
		yyyyMMdd = new SimpleDateFormat("yyyy-MM-dd");
		ddMMyyyy = new SimpleDateFormat("dd-MM-yyyy");

		VelocityEngine engine = new VelocityEngine();
		engine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
		engine.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
		engine.init();

		VelocityContext context = new VelocityContext();
		context.put("fromDate", ddMMyyyy.format(yyyyMMdd.parse(fromDate)));
		context.put("toDate", ddMMyyyy.format(yyyyMMdd.parse(toDate)));
		context.put("purchaseSummList", pvImpl.getPurchaseReportSummary(fromDate, toDate));

		StringWriter writer = new StringWriter();
		Template template = engine.getTemplate("reports/purchase_report_summary.vm");
		template.merge(context, writer);

		return createPdf(BASEURI, writer.toString());
	}

	public byte[] purchaseDetailsReportBy(String fromDate, String toDate) throws IOException, ParseException {
		yyyyMMdd = new SimpleDateFormat("yyyy-MM-dd");
		ddMMyyyy = new SimpleDateFormat("dd-MM-yyyy");
		/* velocity engine initialization */
		VelocityEngine engine = new VelocityEngine();
		engine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
		engine.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
		engine.init();
		
		/* velocity context */
		VelocityContext context = new VelocityContext();
		context.put("fromDate", ddMMyyyy.format(yyyyMMdd.parse(fromDate)));
		context.put("toDate", ddMMyyyy.format(yyyyMMdd.parse(toDate)));
		context.put("purchaseSummList", pvImpl.getPurchaseReportDetailsBy(fromDate, toDate));

		StringWriter writer = new StringWriter();
		Template template = engine.getTemplate("reports/purchase_report_details.vm");
		template.merge(context, writer);

		return createPdf(BASEURI, writer.toString());
	}

	public byte[] purchaseDetailsReportBy(String partyIds, String fromDate, String toDate)
			throws IOException, ParseException {
		yyyyMMdd = new SimpleDateFormat("yyyy-MM-dd");
		ddMMyyyy = new SimpleDateFormat("dd-MM-yyyy");

		VelocityEngine engine = new VelocityEngine();
		engine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
		engine.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
		engine.init();

		VelocityContext context = new VelocityContext();
		context.put("fromDate", ddMMyyyy.format(yyyyMMdd.parse(fromDate)));
		context.put("toDate", ddMMyyyy.format(yyyyMMdd.parse(toDate)));
		context.put("purchaseSummList", pvImpl.getPurchaseReportDetailsBy(partyIds, fromDate, toDate));

		StringWriter writer = new StringWriter();
		Template template = engine.getTemplate("reports/purchase_report_details.vm");
		template.merge(context, writer);

		return createPdf(BASEURI, writer.toString());
	}

	public byte[] createPdf(String baseUri, String html) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ConverterProperties properties = new ConverterProperties();
		properties.setBaseUri(baseUri);
		PdfWriter writer = new PdfWriter(baos, new WriterProperties().setFullCompressionMode(true));
		HtmlConverter.convertToPdf(html, writer, properties);
		return baos.toByteArray();
	}
}