package com.pup.cea.iodfs.ajax.response;

import java.util.List;

import com.pup.cea.iodfs.model.DocumentActivity;

public class DocumentActivityResponseBody {
	
	private String message;
	private List<DocumentActivity> activityList;
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public List<DocumentActivity> getActivityList() {
		return activityList;
	}
	public void setActivityList(List<DocumentActivity> activityList) {
		this.activityList = activityList;
	}
	
	

	

	

}
