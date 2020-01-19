package com.pup.cea.iodfs.test.analytics;

import java.util.List;

public class ResponseBody {
	
	String message;
	List<ChartData> chartData;
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public List<ChartData> getChartData() {
		return chartData;
	}
	public void setChartData(List<ChartData> chartData) {
		this.chartData = chartData;
	}
	
	
	

}
