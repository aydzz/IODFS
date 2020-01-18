package com.pup.cea.iodfs.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/test")
public class TestController {
	
	@RequestMapping("/swalSubmit")
	public String swalSubmit() {
		return "test/pages/swal-submit-sample";
	}


}
