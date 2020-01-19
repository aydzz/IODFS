package com.pup.cea.iodfs.controller;



import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pup.cea.iodfs.model.UserInfo;
import com.pup.cea.iodfs.service.DocumentService;
import com.pup.cea.iodfs.service.NotificationService;
import com.pup.cea.iodfs.service.UserInfoService;


@Controller
public class BaseController {
	
	/*
	 * Use these to print error messages on console.
	 * 			private Logger logger = LoggerFactory.getLogger(BaseController.class);
	 * Requires try - catch( error )
	 * 			logger.info("ERROR MESSAGE" + error.getMessage());
	 */
	@Autowired
	DocumentService docService;
	@Autowired
	UserInfoService userInfoService;
	@Autowired
	NotificationService notificationService;
	
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
	//LOG IN METHODS for REQUESTS
	//FOR Refreshing log in!
	@RequestMapping("/login")
	public String login(@RequestParam(value = "error", required = false) String error, 
						@RequestParam(value="changedPassword", required = false)String changePassword,
						Model model,HttpSession session) {
		
		
		//PASSING THE MESSAGE TO THE PAGE
		//JS cant Read the Expression [[${errorMessage}]] it returns an error!
		
		 boolean loginFailed = false;
		 if(changePassword!= null) {
			 session.invalidate();
		 }
		 if(error != null) {
			 loginFailed = true;
		 }
		 
		 model.addAttribute("loginFailed", loginFailed);
		
		/*return "loginPage"; */
		 return "loginPage2";
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
