package com.pup.cea.iodfs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class DocumentFlowSystemApplication extends SpringBootServletInitializer {
	
	public static void main(String[] args) {
		SpringApplication.run(DocumentFlowSystemApplication.class, args);
	}

	@Override
	  protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
	      return builder.sources(DocumentFlowSystemApplication.class);
	  }
	
	
}
