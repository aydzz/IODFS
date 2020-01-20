package com.pup.cea.iodfs.ajax.response;

import java.util.List;

import com.pup.cea.iodfs.model.ReleasedDocument;

public class ReleasedDocumentResponseBody {
	
	private String message;
	private List<ReleasedDocument> list;
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public List<ReleasedDocument> getList() {
		return list;
	}
	public void setList(List<ReleasedDocument> list) {
		this.list = list;
	}
	

}
