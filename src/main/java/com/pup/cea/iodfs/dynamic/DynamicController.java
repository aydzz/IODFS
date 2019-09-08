package com.pup.cea.iodfs.dynamic;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dynamic")
public class DynamicController {
	
	@RequestMapping("/home")
	public String home(){
		return "dynamic/home";
	}
	@RequestMapping("/table")
	public String table() {
		return "dynamic/dynaTable";
	}
}
