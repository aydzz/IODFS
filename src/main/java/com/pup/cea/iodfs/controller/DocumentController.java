package com.pup.cea.iodfs.controller;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pup.cea.iodfs.ajax.form.ForwardDocumentForm;
import com.pup.cea.iodfs.model.Document;
import com.pup.cea.iodfs.model.Office;
import com.pup.cea.iodfs.model.UserInfo;
import com.pup.cea.iodfs.model.UserLogs;
import com.pup.cea.iodfs.model.security.UserLogin;
import com.pup.cea.iodfs.service.DocumentService;
import com.pup.cea.iodfs.service.OfficeService;
import com.pup.cea.iodfs.service.TypeService;
import com.pup.cea.iodfs.service.UserInfoService;
import com.pup.cea.iodfs.service.UserLogsService;

@Controller
@RequestMapping("/documents")
public class DocumentController {

	
	@Autowired
	DocumentService docService;
	@Autowired
	UserInfoService userInfoService;
	@Autowired 
	OfficeService officeService;
	@Autowired
	TypeService typeService;
	@Autowired 
	UserLogsService userLogsService;
	
	
	/*
	 * This is the longer process. Pwede kasing kunin na from userDetails pero di ko
	 * alam paano. Gumamit nalang ulit ako ng service at repo.
	 */
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
		model.addAttribute("typeList",typeService.findAll());
		
		return "documents/addDocuments";
	}
	@RequestMapping("/save")
	public String saveDocument(@ModelAttribute("documentObject")Document document, Model model) {
		
		//Initial Other Settings
		document.setCurrent_office(getUserInfo().getOffice());
		document.setSource_office(getUserInfo().getOffice());
		document.setForwarded_office(null);
		
		//all newly added document would have pending status
		document.setStatus("PENDING");
		
		//overriding the fetched date value from view
		Date date = new Date(); // this object contains the current date value
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		document.setDate_received(formatter.format(date));
		
		/*
		 * //userLogs Related UserLogs userLog = new UserLogs(); userLog =
		 * userLogsService.findByCts(document.getTrackingnum());
		 * userLog.setRemark(document.getRemark()); //taking the CURRENT REMARK that
		 * would change after an action userLogsService.saveUserLog(userLog);
		 */
		
		System.out.println(getAuth().getName());
		System.out.println(getUserInfo().getOffice());
		System.out.println(document.getRemark());
		
		docService.save(document);
		
		/* model.addAttribute("documentList",docService.findAll()); */
	
		return "redirect:/documents/ctsPage";
	}

	@RequestMapping("/ctsPage")
	public String ctsPage(Model model) {
		model.addAttribute("documentList",docService.findAll()); 
		return "documents/ctsPage";
	}

	@RequestMapping("/incoming")
	public String incomingDocuments(Model model) {
		model.addAttribute("documentList",docService.findIncoming(getUserInfo().getOffice().toString(), "FORWARDED"));
		return "documents/incomingDocuments";
		
		/*
		 * 	
		@Query(value="SELECT * FROM document u WHERE u.forwarded_office = :forwarded and u.status = :status",nativeQuery = true)
		List<Document> findIncoming(@Param("forwarded")String forwarded_office,
				 					 @Param("status") String status);
		
		 */
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
	
	//Forwarding with AJAX
	@PostMapping("/forward/ajaxecute")
	public ResponseEntity<?> ajaxecuteForwardDocument(@Valid @RequestBody ForwardDocumentForm forwardDocumentForm,
													  Errors errors){
		//If error, just return a 400 bad request, along with the error message
	       if (errors.hasErrors()) {
			
			/*
			 * result.setMsg(errors.getAllErrors() .stream().map(x -> x.getDefaultMessage())
			 * .collect(Collectors.joining(","))); return
			 * ResponseEntity.badRequest().body(result);
			 */
			 String errorMessage =  errors.getAllErrors() .stream().map(x -> x.getDefaultMessage())
					  .collect(Collectors.joining(",")).toString();
			 System.out.println(errorMessage);
			 return ResponseEntity.badRequest().body(errorMessage);
	       }
	       
	       //We can do the repository methods when the request doesnt have any errors
	       //saving the document using ajax
	       try {
	    	   Document document = docService.findByTrackingnum(forwardDocumentForm.getTrackingNumber());
				document.setStatus("FORWARDED");
				document.setForwarded_office(forwardDocumentForm.getDestination());
				document.setRemark(forwardDocumentForm.getRemark());
				docService.save(document);
	       }catch(Exception e) {
	    	   return ResponseEntity.badRequest().body(e.getStackTrace().toString());
	       }
		return ResponseEntity.ok(true);
		
	}
	//Forwarding needs refresh
	@RequestMapping("/forward/{trackingnum}/execute")
	public String executeForwardDocument(HttpServletRequest req,
										 @PathVariable("trackingnum")String trackingnum) {
		Document document = docService.findByTrackingnum(trackingnum);
		document.setStatus("FORWARDED");
		document.setForwarded_office(req.getParameter("destination"));
		document.setRemark(req.getParameter("remark"));
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
		document.setRemark(req.getParameter("remark"));
		docService.save(document);
		return "redirect:/documents/pending";
	}
	
	
	@RequestMapping("/profilePage")
	public String Profile(Model model) {
		model.addAttribute("userLoginList",userInfoService.findByUname(getUserInfo().getUsername().toString()));
		model.addAttribute("userInfoList",userInfoService.findByUnname(getUserInfo().getUsername().toString()));
		System.out.println(getUserInfo().getUsername().toString());
	return "documents/profilePage";
	}
	
	
	@RequestMapping("/profile/save")
	public String saveProfile(@ModelAttribute("userLoginObject")UserLogin user_login) {
		
		userInfoService.saves(user_login);
		
		return "redirect:/home";
	}
	
	
	//this request was transfered to '/api/changePassword'
	@RequestMapping("/profile/{userloginid}/edit")
	public String changePassword(@PathVariable("userloginid")Long userloginid,Model model) {
		model.addAttribute("userLoginObject",userInfoService.findUser(userloginid));
		return "documents/changePassword";
	}
	
}
