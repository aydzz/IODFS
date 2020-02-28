package com.pup.cea.iodfs.controller;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.pup.cea.iodfs.ajax.form.ForwardDocumentForm;
import com.pup.cea.iodfs.exception.FileStorageException;
import com.pup.cea.iodfs.model.Document;
import com.pup.cea.iodfs.model.Office;
import com.pup.cea.iodfs.model.UserInfo;
import com.pup.cea.iodfs.model.UserLogs;
import com.pup.cea.iodfs.model.security.UserLogin;
import com.pup.cea.iodfs.payload.UploadFileResponse;
import com.pup.cea.iodfs.service.DocumentService;
import com.pup.cea.iodfs.service.NotificationService;
import com.pup.cea.iodfs.service.OfficeService;
import com.pup.cea.iodfs.service.TypeService;
import com.pup.cea.iodfs.service.UserInfoService;
import com.pup.cea.iodfs.service.UserLogsService;



@Controller
@RequestMapping("/documents")
public class DocumentController {

	private static final Logger logger = LoggerFactory.getLogger(DocumentController.class);
	
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
	@Autowired
	NotificationService notifService;
	
	
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
		model.addAttribute("typeList",typeService.findAll());
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
	public String saveDocument(@ModelAttribute("documentObject")Document document, Model model, @RequestParam("file") MultipartFile file) {
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
		
	   String fileName = StringUtils.cleanPath(file.getOriginalFilename());

	   //added this condition since empty file is being added on NULL
	   if(file.getSize() > 0) {
		   try {
	            // Check if the file's name contains invalid characters
	            if(fileName.contains("..")) {
	                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
	            }
	            
	            document.setFileName(fileName);
	            document.setFileType(file.getContentType());
	            document.setData(file.getBytes());
	            
	            Document fileUpload = new Document(fileName, file.getContentType(), file.getBytes());
	            
	            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
	                    .path("downloadFile/")
	                    .path(Long.toString(fileUpload.getId()))
	                    .toUriString();
	            

	            new UploadFileResponse(fileUpload.getFileName(), fileDownloadUri,
	                    file.getContentType(), file.getSize());
	    		
	        } catch (IOException ex) {
	            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
	        }
	   }else {
		   document.setFileName(null);
           document.setFileType(null);
           document.setData(null);
	   }
		
	    
		//Document fileUpload = docService.storeFile(file);
		
		System.out.println(getAuth().getName());
		System.out.println(getUserInfo().getOffice());
		
		docService.save(document);
		
		//------NOTIFICATION----------
		if(document.getEmailAddress()!=null) {
	      	  notifService.sendNotification(document, 
		        		"Your Document was added Succesfully", 
		        		"Please wait patiently while your document is being processed.\n We will notify you for further details.\n\n\n" +
		        		"Time of inclusion: " + date.getHours()+":"+date.getMinutes() + "\n" +
		        		"Date of inclusion: " + new SimpleDateFormat("MMM dd, yyyy").format(date));
	      }else {
	    	  //do nothing
	      }
		//------NOTIFICATION----------
		
		return "redirect:/documents/ctsPage";
	}

	@RequestMapping("/ctsPage")
	public String ctsPage(Model model) {
		
		model.addAttribute("documentList",docService.findAll());
		
		return "documents/ctsPage";
	}
	
	
	//file download
	
	@GetMapping("/documents/track/downloadFile/{documentId}")
	public String download(@PathVariable("documentId")Long Id, HttpServletResponse response) {
		Document documentFile = docService.getFile(Id);
		try {
			response.setHeader("Content-Disposition", "inline; filename=\"" + documentFile.getFileName() + "\"");
			OutputStream out = response.getOutputStream();
			response.setContentType(documentFile.getFileType());

			ByteArrayInputStream inputstream = new ByteArrayInputStream(documentFile.getData());
			IOUtils.copy(inputstream, out);
			out.flush();
			out.close();

		} catch (IOException e) {
			System.out.println(e.toString());
			//Handle exception here
		}

		return "redirect:/documents/track";
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
		System.out.println(docService.findByTrackingnum(trackingnum).getDescription());
		
		
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
		
		//------NOTIFICATION----------
		Document documentNotif = newDocument;
		Date dateNotif = new Date();
		if(documentNotif.getEmailAddress()!=null) {
	      	  notifService.sendNotification(documentNotif, 
		        		"Document was Delivered", 
		        		"Your document was successfully forwarded.\n\n\n" +
        				"Tracking Number: " + documentNotif.getTrackingnum() + "\n" + 
		        		"Time of delivery: " + dateNotif.getHours()+":"+dateNotif.getMinutes() + "\n" +
		        		"Date of delivery: " + new SimpleDateFormat("MMM dd, yyyy").format(dateNotif)+"\n" + 
		        		"Current Office: " + documentNotif.getCurrent_office()); 
	      }else {
	    	  //do nothing
	      }
		//------NOTIFICATION----------
		
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
				
				//------NOTIFICATION----------
				Document documentNotif = document;
				Date dateNotif = new Date();
				if(documentNotif.getEmailAddress()!=null) {
			      	  notifService.sendNotification(documentNotif, 
				        		"Document was Transfered", 
				        		"Your document was transfered to another office.\n\n\n" +
				        		"Tracking Number: " + forwardDocumentForm.getTrackingNumber() + "\n" + 
				        		"Time of transfer: " + dateNotif.getHours()+":"+dateNotif.getMinutes() + "\n" +
				        		"Date of transfer: " + new SimpleDateFormat("MMM dd, yyyy").format(dateNotif)+"\n" + 
				        		"Forwarded Office: " + forwardDocumentForm.getDestination()); 
			      }else {
			    	  //do nothing
			      }
				//------NOTIFICATION----------
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
		
		//------NOTIFICATION----------
		Document documentNotif = document;
		Date dateNotif = new Date();
		if(documentNotif.getEmailAddress()!=null) {
	      	  notifService.sendNotification(documentNotif, 
		        		"Document was Released!", 
		        		"Claim your document at your department.\n\n\n" +
        				"Tracking Number: " + documentNotif.getTrackingnum() + "\n" + 
		        		"Time of release: " + dateNotif.getHours()+":"+dateNotif.getMinutes() + "\n" +
		        		"Date of release: " + new SimpleDateFormat("MMM dd, yyyy").format(dateNotif)+"\n" + 
		        		"Remark: " + documentNotif.getRemark()); 
	      }else {
	    	  //do nothing
	      }
		//------NOTIFICATION----------
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
