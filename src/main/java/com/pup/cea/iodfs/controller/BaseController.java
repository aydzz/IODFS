package com.pup.cea.iodfs.controller;



import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.pup.cea.iodfs.model.UserInfo;
import com.pup.cea.iodfs.service.DocumentService;
import com.pup.cea.iodfs.service.UserInfoService;


@Controller
public class BaseController {
	

	
	@Autowired
	DocumentService docService;
	@Autowired
	UserInfoService userInfoService;
	
	private Authentication auth;
	private UserInfo userInfo;
	
	public Authentication getAuth() {
		return SecurityContextHolder.getContext().getAuthentication();
	}
	public UserInfo getUserInfo() {
		return userInfoService.findByUsername(getAuth().getName());
	}
	
	@RequestMapping("/")
	public String index() {
		return "redirect:/home";
	}
	@RequestMapping("/login")
	public String login(@RequestParam(value = "error", required = false) String error, Model model) {
		
		
		//PASSING THE MESSAGE TO THE PAGE
		//JS cant Read the Expression [[${errorMessage}]] it returns an error!
		/*
		 String errorMessage = null;
		 
		 if(error != null) {
			 errorMessage = "Username or Password is incorrect !!";
		 }
		 model.addAttribute("errorMessage", errorMessage);*/
		 return "loginPage";
	}
	@RequestMapping("/check-role")
	public String checkRole() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		Collection<? extends GrantedAuthority> authorities =((UserDetails)principal).getAuthorities();
		boolean isAdmin = authorities.contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
		boolean isUser =  authorities.contains(new SimpleGrantedAuthority("ROLE_USER"));
		//TEST
		boolean isOffice =  authorities.contains(new SimpleGrantedAuthority("ROLE_OFFICE"));
		//TEST
		
		System.out.println("YOU ARE IN /check-role");
		System.out.println("AUTHENTICATED USER'S NAME: " + auth.getName());
		System.out.println("IS AUTHENTICATED: " + auth.isAuthenticated());
		System.out.println("CONTAINS USER ROLE: " + authorities.contains(new SimpleGrantedAuthority("ROLE_USER")));
		//TEST
		System.out.println("CONTAINS OFFICE ROLE: " + authorities.contains(new SimpleGrantedAuthority("ROLE_OFFICE")));
		//TEST
		System.out.println("CONTAINS ADMIN ROLE: " + authorities.contains(new SimpleGrantedAuthority("ROLE_ADMIN")));
		
		if(isAdmin) {
			return "redirect:/administrator/home";
		}
		else if(isUser || isOffice) { //TESTTTTT
			return "redirect:/home"; 
		}
		else {
			//role is non existent
			return "redirect:/error";
		}
	}
	@RequestMapping("/home")
	public String home(Model model) {
		
		model.addAttribute("documentTot",docService.findAll().size());
		model.addAttribute("pendingDocument",docService.findByOfficeAndStatus(getUserInfo().getOffice(), "PENDING").size());
		model.addAttribute("incomingDocument",docService.findIncoming(getUserInfo().getOffice(), "FORWARDED").size());
	
		return "home";
	}
	@RequestMapping("/logout-success")
	public String logoutSuccess() {
		return "redirect:/login";
	}
	
	
	
}
