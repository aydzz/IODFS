package com.pup.cea.iodfs.test;

import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import antlr.collections.List;

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
