package com.pup.cea.iodfs.test;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.pup.cea.iodfs.test.analytics.ChartData;
import com.pup.cea.iodfs.test.analytics.MorrisBody;
import com.pup.cea.iodfs.test.analytics.MorrisData;
import com.pup.cea.iodfs.test.analytics.MorrisService;
import com.pup.cea.iodfs.test.analytics.ResponseBody;

import javax.validation.Valid;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@RestController
public class Controller {

	UserService userService;

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	@Autowired 
	MorrisService morrisService;

	@PostMapping("/api/login")
   public ResponseEntity<?> getSearchResultViaAjax(@Valid @RequestBody LoginForm loginForm, Errors errors) throws ParseException {

		LoginForm loginFormB = new LoginForm();
		
		loginFormB.setPassword("HAKDOG");
		loginFormB.setUsername("HAKDUG");
		
		//will try response body as a String Primitive
		String sampleString = "This is an individual String data";
		
		
       AjaxResponseBody result = new AjaxResponseBody();

       //If error, just return a 400 bad request, along with the error message
       if (errors.hasErrors()) {
           result.setMsg(errors.getAllErrors()
                   .stream().map(x -> x.getDefaultMessage())
                   .collect(Collectors.joining(",")));
           return ResponseEntity.badRequest().body(result);

       }
       
       
       List<User> users = userService.login(loginForm);
       if (users.isEmpty()) {
           result.setMsg("no user found!");
       } else {
           result.setMsg("success");
           result.setAnotherMessage("TANGINA MO");
       }
       result.setResult(users);
       
       return ResponseEntity.ok(result);
       

   }
	@PostMapping("/api/getChartData")
	public ResponseEntity<?> getChartData() throws Exception{
		
		MorrisBody body = new MorrisBody();
		List<MorrisData> list = new ArrayList<>();
		
		list = morrisService.propagateMorrisData();
		
		
		body.setChartData(list);
		body.setMessage("Morris Data is READY!");
		
		
		return ResponseEntity.ok(body) ;
	}
	
}