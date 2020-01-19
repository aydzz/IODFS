package com.pup.cea.iodfs.test;

import java.util.List;

public class AjaxResponseBody {

    String msg;
    List<User> result;

	String anotherMessage;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<User> getResult() {
        return result;
    }

    public void setResult(List<User> result) {
        this.result = result;
    }
    
    public String getAnotherMessage() {
		return anotherMessage;
	}

	public void setAnotherMessage(String anotherMessage) {
		this.anotherMessage = anotherMessage;
	}

    

}