package com.pup.cea.iodfs.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pup.cea.iodfs.model.Counter;
import com.pup.cea.iodfs.model.Document;
import com.pup.cea.iodfs.model.Office;
import com.pup.cea.iodfs.model.UserInfo;
import com.pup.cea.iodfs.model.security.UserLogin;
import com.pup.cea.iodfs.model.Type;
import com.pup.cea.iodfs.service.CounterService;
import com.pup.cea.iodfs.service.DocumentService;
import com.pup.cea.iodfs.service.HistoryService;
import com.pup.cea.iodfs.service.OfficeService;
import com.pup.cea.iodfs.service.TypeService;
import com.pup.cea.iodfs.service.UserInfoService;
import com.pup.cea.iodfs.service.UserLogsService;

@Controller
@RequestMapping("/administrator")
public class AdministratorController {
	
	@Autowired
	DocumentService docService;
	@Autowired
	UserInfoService userInfoService;
	@Autowired 
	OfficeService officeService;
	@Autowired
	TypeService typeService;
	@Autowired
	CounterService counterService;
	@Autowired
	UserLogsService	userLogsService;
	@Autowired
	HistoryService	historyService;
	

	
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
		
		public PasswordEncoder passwordEncoder() {
		    return new BCryptPasswordEncoder();
		}
	
	@RequestMapping("/home")
	public String adminHome(Model model) {
		
		System.out.println(docService.findByStatusWildcard("PENDING%"));
		model.addAttribute("pendingCount",docService.findByStatusWildcard("PENDING%").size());
		model.addAttribute("forwardedCount",docService.findByStatusWildcard("FORWARDED%").size());
		model.addAttribute("releasedCount",docService.findByStatusWildcard("RELEASED%").size());
		
		model.addAttribute("documentTot",docService.findAll().size());
		model.addAttribute("userTot",userInfoService.findAll().size());
		model.addAttribute("officeTot",officeService.findAll().size());
		model.addAttribute("typeTot",typeService.findAll().size());
		return "administrator/home";
	}
	
	@RequestMapping("/users")
	public String adminusers(Model model) {
		
	model.addAttribute("userList",userInfoService.findAll());
		
		return "administrator/users";
	}
	

	@RequestMapping("/addUser")
	public String addUser(Model model) {
		
		UserInfo user= new UserInfo();
	
		
		
		model.addAttribute("userObject",user);
		model.addAttribute("officeList",officeService.findAll());
	
		return "administrator/addUser";
	}
	
	@RequestMapping("/save")
	public String saveUserInfo(@ModelAttribute("userObject")UserInfo user, 
							   @RequestParam String username, 
							   @RequestParam String password
							   ) {
		try {
		//Initial Other Settings
		UserLogin users = new UserLogin();
		//all newly added document would have pending status
		//overriding the fetched date value from view
		users.setUsername(username);
		/* users.setRole(role); */
		user.setFullname(user.getName() + " " + user.getLname());
		users.setPassword(password);
		users.setRole("USER");
		System.out.println(getAuth().getName());
		System.out.println(getUserInfo().getOffice());
		
		userInfoService.save(user);
		userInfoService.saves(users);
	
		}catch (DataIntegrityViolationException e) {
	        return "redirect:/administrator/addUser";
	    }
		return "redirect:/administrator/users";
	}
	
	
/*ADZZ LATEST EDITS*/
	@RequestMapping("/user/{userId}/edit")
	public String editUser(Model model,
						   @PathVariable("userId")Long userId) {
		UserInfo userInfo = userInfoService.findUserInfo(userId);
		
		System.out.println(userInfo.getLname());
		model.addAttribute("userObject",userInfo);
		model.addAttribute("officeList",officeService.findAll());
		
		
		
		return "administrator/addUser";
	}
	@RequestMapping("/user/{userId}/remove")
	public String removeUser(@PathVariable("userId")Long userId) {
		userInfoService.delete(userId);
		userInfoService.deletes(userId);
		
		
		return "redirect:/administrator/users";
	}
	
	
	//OFFICE
	@RequestMapping("/offices/view")
	public String viewOffices(Model model) {
		
		List<Office> list = officeService.findAll();
		model.addAttribute("officeList",list);
		
		return "administrator/viewOffice";
	}
	@RequestMapping("/offices/add")
	public String addOffice(Model model) {
		Office office = new Office();
		model.addAttribute("officeObject", office);
		return "administrator/addOffice";
	}
	@RequestMapping("/offices/save")
	public String saveOffice(@ModelAttribute("officeObject")Office office) {
		try {
		officeService.saveOffice(office);
		}catch (DataIntegrityViolationException e) {
			
	        return "redirect:/administrator/offices/add";
	    }
		return "redirect:/administrator/offices/view";
	}
	
	@RequestMapping("/offices/{officeId}/edit")
	public String editOffice(@PathVariable("officeId")Long officeId, Model model) {
		model.addAttribute("officeObject",officeService.findOffice(officeId));
		return "administrator/addOffice";
	}
	
	
	@RequestMapping("/offices/{officeId}/remove")
	public String removeOffice(@PathVariable("officeId")Long officeId) {
		officeService.delete(officeId);
			
		return "redirect:/administrator/offices/view";
	}
	
	//TYPE
	@RequestMapping("/type/view")
	public String viewDocType(Model model) {
		
		List<Type> list = typeService.findAll();
		model.addAttribute("typeList",list);
		
		return "administrator/viewType";
	}
	@RequestMapping("/type/add")
	public String addDocType(Model model) {
		Type types = new Type();
		model.addAttribute("typeObject",types);
		return "administrator/addType";
	}
	@RequestMapping("/type/save")
	public String saveDocType(@ModelAttribute("typeObject")Type type, Model model) {
		try {
		typeService.save(type);
		}catch (DataIntegrityViolationException e) {
			model.addAttribute("message", "Error! Duplicate Entry!");
	        return "redirect:/administrator/type/add";
	    }
		return "redirect:/administrator/type/view";
	}
	@RequestMapping("/type/{typeId}/edit")
	public String editDocType(@PathVariable("typeId")Long typeId, Model model) {
		model.addAttribute("typeObject",typeService.findDocType(typeId));
		return "administrator/addType";
	}
	
	
	
	@RequestMapping("/type/{typeId}/remove")
	public String removeType(@PathVariable("typeId")Long typeId,Model model) {
		typeService.delete(typeId);
			
		return "redirect:/administrator/type/view";
	}
	
	
	//adzz
	@RequestMapping("/userLogs")
	public String userLogs(Model model) {
		
	model.addAttribute("userLogs",userLogsService.findAll());
		
		return "administrator/userLogs";
	}
	
	@RequestMapping("/history")
	public String history(Model model) {
		
	model.addAttribute("history",historyService.findAll());
		
		return "administrator/history";
	}
}
