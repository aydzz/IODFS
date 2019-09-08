package com.pup.cea.iodfs.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/administrator")
public class AdministratorController {
	
	@RequestMapping("/home")
	public String adminHome() {
		return "administrator/home";
	}
}
