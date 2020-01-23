package com.pup.cea.iodfs.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pup.cea.iodfs.ajax.form.PasswordForm;
import com.pup.cea.iodfs.ajax.response.DocumentActivityResponseBody;
import com.pup.cea.iodfs.ajax.response.ReleasedDocumentResponseBody;
import com.pup.cea.iodfs.model.Document;
import com.pup.cea.iodfs.model.ReleasedDocument;
import com.pup.cea.iodfs.model.UserInfo;
import com.pup.cea.iodfs.model.security.UserLogin;
import com.pup.cea.iodfs.service.DocumentService;
import com.pup.cea.iodfs.service.UserInfoService;
import com.pup.cea.iodfs.service.UserLogsService;
import com.pup.cea.iodfs.test.analytics.ResponseBody;


@Controller
@RequestMapping("/api")
public class MISController {
	
	@Autowired
	UserInfoService userInfoService;
	@Autowired
	UserLogsService userLogsService;
	@Autowired
	DocumentService documentService;
	
	//This is the longer process. Pwede kasing kunin na from userDetails pero di ko alam paano.
	//Gumamit nalang ulit ako ng service at repo.
	
	public Authentication getAuth() {
		return SecurityContextHolder.getContext().getAuthentication();
	}
	public UserInfo getUserInfo() {
		return userInfoService.findByUsername(getAuth().getName());
	}
	
	public PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}
	
	@PostMapping("/changePassword")
	public ResponseEntity<?> changePasswordRequest(@Valid @RequestBody PasswordForm passwordForm,
			  															  Errors errors){
		
		//If error, just return a 400 bad request, along with the error message
	       if (errors.hasErrors()) {
	    	 //convert the error errors to string format
			 String errorMessage =  errors.getAllErrors() .stream().map(x -> x.getDefaultMessage())
					  .collect(Collectors.joining(",")).toString();
			 System.out.println(errorMessage);
			 return ResponseEntity.badRequest().body(errorMessage);
	       }
	       
	       UserLogin thisUserLogin = userInfoService.findUsername(getAuth().getName());
	       System.out.println("PASSWORD FORM OLD PASSWORD: " + passwordForm.getOldPassword());
	       System.out.println("PASSWORD FORM new PASSWORD: " + passwordForm.getNewPassword());
	       System.out.println("PASSWORD FORM confirm PASSWORD: " + passwordForm.getConfirmPassword());
	       
	       System.out.println(passwordEncoder().matches(passwordForm.getOldPassword(), 
	    			   thisUserLogin.getPassword()));
	       
	       System.out.println(passwordForm.getNewPassword().equals(passwordForm.getConfirmPassword()));
	       
	       try {
	    	   if(passwordEncoder().matches(passwordForm.getOldPassword(), 
	    			   thisUserLogin.getPassword()) && 
	    			   passwordForm.getNewPassword().equals(passwordForm.getConfirmPassword())) {
	    		   
	    		   thisUserLogin.setPassword(passwordForm.getConfirmPassword());
	    		   userInfoService.saves(thisUserLogin);
	    		   System.out.println("NEW PASSWORD SAVED!");
	    		   System.out.println("PASSWORD: " + thisUserLogin.getPassword());
	    		   System.out.println("USERNAME: " + thisUserLogin.getUsername());
	    		   return ResponseEntity.ok(true);
	    	   }else {
	    		   return ResponseEntity.ok(false);
	    	   }
	       }catch(Exception e) {
	    	   System.out.println(e.getStackTrace().toString());
	    	   return ResponseEntity.badRequest().body(e.getStackTrace().toString());
	       }
	       
	}
	
	@PostMapping("/fetch-document-activity")
	public ResponseEntity<?> getDocumentActivityData(@Valid @RequestBody String range, Errors errors ){
		/* System.out.println("PASSED RANGE IS: " + lineGraphRange.getRange()); */
		System.out.println("PASSED RANGE IS: " + range);
		int rangeValue = 7;
		DocumentActivityResponseBody body = new DocumentActivityResponseBody();
		
		if(range !=null || range !="") {
			 try {
				 rangeValue = Integer.parseInt(range);
			 }catch(NumberFormatException e) {
				 return ResponseEntity.badRequest().body(e.getMessage());
			 }
		}
		 if (errors.hasErrors()) {
	    	 //convert the error errors to string format
			 String errorMessage =  errors.getAllErrors() .stream().map(x -> x.getDefaultMessage())
					  .collect(Collectors.joining(",")).toString();
			 System.out.println(errorMessage);
			 return ResponseEntity.badRequest().body(errorMessage);
	       }
	       
		try {
			body.setActivityList(userLogsService.getChartData(rangeValue));
			body.setMessage("DATA WAS FETCHED");
			/*
			*	throw new Exception("Error persisted on AJAX Request");
			*/
			
			return ResponseEntity.ok(body); 
			
			
		}catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getStackTrace().toString());
		}
		
	}
	
	@PostMapping("/fetch-released-document")
	public ResponseEntity<?> getReleasedDocumentData(){
		ReleasedDocumentResponseBody body = new ReleasedDocumentResponseBody();
		List<ReleasedDocument> list = new ArrayList<>();
		
		ReleasedDocument completed = new ReleasedDocument();
		completed.setLabel("Completed");
		completed.setValue(documentService.findByStatusWildcard("RELEASED:COMPLETED%").size());
		list.add(completed);
		
		ReleasedDocument deficient = new ReleasedDocument();
		deficient.setLabel("Deficient");
		deficient.setValue(documentService.findByStatusWildcard("RELEASED:DEFICIENT%").size());
		list.add(deficient);

		ReleasedDocument rejected = new ReleasedDocument();
		rejected.setLabel("Rejected");
		rejected.setValue(documentService.findByStatusWildcard("RELEASED:REJECTED%").size());
		list.add(rejected);
		
		body.setMessage("Data fetched successfully");
		body.setList(list);

		return ResponseEntity.ok(body);
	}
	
	@PostMapping("/fetch-file-data")
	public ResponseEntity<?> getReleasedDocument(@RequestBody Long id){
		
		Document document = documentService.getFile(id);
		
		System.out.println("file name is: " + document.getFileName());
		return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(document.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + document.getFileName() + "\"")
                .body(new ByteArrayResource(document.getData()));
	}
	
	

}
