package com.pup.cea.iodfs.controller;


import java.text.SimpleDateFormat;
import java.util.Date;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pup.cea.iodfs.model.Document;
import com.pup.cea.iodfs.model.UserInfo;
import com.pup.cea.iodfs.service.DocumentService;
import com.pup.cea.iodfs.service.OfficeService;
import com.pup.cea.iodfs.service.UserInfoService;

@Controller
@RequestMapping("/documents")
public class DocumentController {

	
	@Autowired
	DocumentService docService;
	@Autowired
	UserInfoService userInfoService;
	@Autowired 
	OfficeService officeService;
	
	//This is the longer process. Pwede kasing kunin na from userDetails pero di ko alam paano.
	//Gumamit nalang ulit ako ng service at repo.
	private Authentication auth;
	private UserInfo userInfo;
	
	public Authentication getAuth() {
		return SecurityContextHolder.getContext().getAuthentication();
	}
	public UserInfo getUserInfo() {
		return userInfoService.findByUsername(getAuth().getName());
	}

	@RequestMapping("/track")
	public String trackDocuments(Model model) {
		
		model.addAttribute("documentList",docService.findAll());
		return "documents/trackDocuments";
	}
	@RequestMapping("/add")
	public String addDocuments(Model model) {
		Document documents = new Document();
		
		model.addAttribute("documentObject",documents);
		
		return "documents/addDocuments";
	}
	@RequestMapping("/save")
	public String saveDocument(@ModelAttribute("documentObject")Document document) {
		//Initial Other Settings
		document.setCurrent_office(getUserInfo().getOffice());
		document.setSource_office(getUserInfo().getOffice());
		document.setForwarded_office(null);
		//all newly added document would have pending status
		document.setStatus("PENDING");
		//overriding the fetched date value from view
		Date date = new Date(); // this object contains the current date value
		SimpleDateFormat formatter = new SimpleDateFormat("MMMMM dd, yyyy");
		document.setDate_received(formatter.format(date));
		
		System.out.println(getAuth().getName());
		System.out.println(getUserInfo().getOffice());
		
		docService.save(document);
		
		return "redirect:/documents/track";
	}
	@RequestMapping("/incoming")
	public String incomingDocuments(Model model) {
		model.addAttribute("documentList",docService.findIncoming(getUserInfo().getOffice().toString(), "FORWARDED"));
		return "documents/incomingDocuments";
	}
	@RequestMapping("/pending")
	public String pendingDocuments(Model model) {
		model.addAttribute("documentList",docService.findByOfficeAndStatus(getUserInfo().getOffice().toString(), "PENDING"));
		return "documents/pendingDocuments";
	}
	@RequestMapping("/forwarded")
	public String forwardedDocuments(Model model) {
		model.addAttribute("documentList",docService.findByOfficeAndStatus(getUserInfo().getOffice().toString(), "FORWARDED"));
		return "documents/forwardedDocuments";
	}
	
	@RequestMapping("/forward/{trackingnum}")
	public String forwardDocument(@PathVariable("trackingnum")String trackingnum,
								  Model model) {
		
		model.addAttribute("document",docService.findByTrackingnum(trackingnum));
		model.addAttribute("officeList",officeService.findAll());
		return "documents/forwardDocument";
	}
	@RequestMapping("/release/{trackingnum}")
	public String releaseDocument(@PathVariable("trackingnum")String trackingnum,
								  Model model) {
		model.addAttribute("document",docService.findByTrackingnum(trackingnum));
		return "documents/releaseDocument";
	}
	@RequestMapping("/receive/{trackingnum}")
	public String receiveDocument(@PathVariable("trackingnum")String trackingnum,
			  					  Model model) {
		Document document = docService.findByTrackingnum(trackingnum);
		Document newDocument = new Document();
		
		String sourceOffice = document.getCurrent_office();
		
		newDocument = document;
		
		newDocument.setCurrent_office(getUserInfo().getOffice().toString());
		newDocument.setSource_office(sourceOffice);
		newDocument.setForwarded_office(null);
		newDocument.setStatus("PENDING");
		
		System.out.println(sourceOffice);
		System.out.println(getUserInfo().getOffice().toString());
		
		docService.save(newDocument);
		
		return "redirect:/documents/incoming";
	}
	@RequestMapping("/forward/{trackingnum}/execute")
	public String executeForwardDocument(HttpServletRequest req,
										 @PathVariable("trackingnum")String trackingnum) {
		Document document = docService.findByTrackingnum(trackingnum);
		document.setStatus("FORWARDED");
		document.setForwarded_office(req.getParameter("destination"));
		
		docService.save(document);
		return "redirect:/documents/pending";
	}
	@RequestMapping("/release/{trackingnum}/execute")
	public String executeReleaseDocument(HttpServletRequest req,
										 @PathVariable("trackingnum")String trackingnum) {
		Document document = docService.findByTrackingnum(trackingnum);
		document.setStatus("RELEASED:"+req.getParameter("result"));
		document.setCurrent_office(null);
		document.setForwarded_office(null);
		document.setDescription(req.getParameter("desc"));
		docService.save(document);
		return "redirect:/documents/pending";
	}
}
