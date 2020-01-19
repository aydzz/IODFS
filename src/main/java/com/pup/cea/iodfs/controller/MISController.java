package com.pup.cea.iodfs.controller;

import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pup.cea.iodfs.ajax.form.PasswordForm;
import com.pup.cea.iodfs.model.UserInfo;
import com.pup.cea.iodfs.model.security.UserLogin;
import com.pup.cea.iodfs.service.UserInfoService;


@Controller
@RequestMapping("/api")
public class MISController {
	
	@Autowired
	UserInfoService userInfoService;
	
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
	
	

}
