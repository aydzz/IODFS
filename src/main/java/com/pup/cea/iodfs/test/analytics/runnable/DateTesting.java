package com.pup.cea.iodfs.test.analytics.runnable;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTesting {
	
	public static void main(String args[]) throws Exception{

		String sDate = "2020-01-10";
		
		SimpleDateFormat formatter = new SimpleDateFormat("MMMMM dd, yyyy");  
		
		Date parsedDate = new SimpleDateFormat("yyyy-MM-dd").parse(sDate);
		
		System.out.println("PARSED");
		System.out.println(parsedDate);
		System.out.println("PARSED WITH FORMATTER");
		System.out.println(formatter.format(parsedDate));
		
		
	}

}
