package com.pup.cea.iodfs.test.analytics;

import java.util.List;

public class MorrisBody {
	
	String message;
	List<MorrisData> chartData;
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public List<MorrisData> getChartData() {
		return chartData;
	}
	public void setChartData(List<MorrisData> chartData) {
		this.chartData = chartData;
	}

}
