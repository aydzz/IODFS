package com.pup.cea.iodfs.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pup.cea.iodfs.service.DocumentService;
import com.pup.cea.iodfs.service.NotificationService;
import com.pup.cea.iodfs.service.OfficeService;
import com.pup.cea.iodfs.service.TypeService;
import com.pup.cea.iodfs.service.UserInfoService;
import com.pup.cea.iodfs.service.UserLogsService;

@Controller
@RequestMapping("/public")
public class PublicController {
	
private static final Logger logger = LoggerFactory.getLogger(PublicController.class);
	
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
	
	@RequestMapping("/track")
	public String trackDocuments(@RequestParam(value = "filter", required = false) String filter,
							     Model model) {
		model.addAttribute("documentList",docService.findAll());
		return "trackDocuments";
	}
}
