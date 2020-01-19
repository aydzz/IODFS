package com.pup.cea.iodfs.test.analytics;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class MorrisService {
	
	public List<MorrisData> propagateMorrisData() throws ParseException {
		
		List<MorrisData> list = new ArrayList<>();
		
		List<Date> dateList = new ArrayList<>();
		SimpleDateFormat formatter = new SimpleDateFormat("MMMMM dd, yyyy"); 
		
		String period[] = {"2011 Q3","2011 Q1","2010 Q4","2009 Q4","2008 Q4","2007 Q4", "2006 Q4","2005 Q4"};
		String periodA[] = {"2020-01-10","2020-01-11","2020-01-12","2020-01-13","2020-01-14","2020-01-15", "2020-01-16","2020-01-17"};
		int licensed[] = {100,200,300,400,500,600,700,800};
		int sorned[] = {1000,2000,3000,4000,5000,6000,7000,8000};
		
		for(int i = 0;i<8;i++) {
			MorrisData data = new MorrisData();
			/* Date parsedDate = new SimpleDateFormat("yyyy-MM-dd").parse(periodA[i]); */
			
			data.setLicensed(licensed[i]);
			data.setPeriod(periodA[i]);
			data.setSorned(sorned[i]);
			
			list.add(data);
		}
		
		return list;

		
		
	}
	
	

}
