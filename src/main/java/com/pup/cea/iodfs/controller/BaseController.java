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


@Controller
public class BaseController {
	@RequestMapping("/")
	public String index() {
		return "redirect:/home";
	}
	@RequestMapping("/login")
	public String login() {
		return "loginPage";
	}
	@RequestMapping("/check-role")
	public String checkRole() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		Collection<? extends GrantedAuthority> authorities =((UserDetails)principal).getAuthorities();
		boolean isAdmin = authorities.contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
		boolean isUser =  authorities.contains(new SimpleGrantedAuthority("ROLE_USER"));
		
		System.out.println("YOU ARE IN /check-role");
		System.out.println("AUTHENTICATED USER'S NAME: " + auth.getName());
		System.out.println("IS AUTHENTICATED: " + auth.isAuthenticated());
		System.out.println("CONTAINS USER ROLE: " + authorities.contains(new SimpleGrantedAuthority("ROLE_USER")));
		System.out.println("CONTAINS ADMIN ROLE: " + authorities.contains(new SimpleGrantedAuthority("ROLE_ADMIN")));
		
		if(isAdmin) {
			return "redirect:/administrator/home";
		}
		else if(isUser) {
			return "redirect:/home";
		}
		else {
			//role is non existent
			return "redirect:/error";
		}
	}
	@RequestMapping("/home")
	public String home() {
		return "home";
	}
	@RequestMapping("/logout-success")
	public String logoutSuccess() {
		return "redirect:/login";
	}
	
	
	
}
