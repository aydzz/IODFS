package com.pup.cea.iodfs.test.analytics;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ChartData {
	
	private String date;
	private String count;
	private String Type;
	
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("MMMMM dd, yyyy");  
		Date parsedDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
		
		this.date = formatter.format(parsedDate).toString();
	}
	
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public String getType() {
		return Type;
	}
	public void setType(String type) {
		Type = type;
	}
	
	


}
