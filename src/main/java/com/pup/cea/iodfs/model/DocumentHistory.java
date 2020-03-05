package com.pup.cea.iodfs.model;



import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="document_history")
public class DocumentHistory {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String trackingnum;
	private String action;
	private Date dateTime;
	private String srcOffice;
	private String currentOffice;
	private String forwardedOffice;
	public String getForwardedOffice() {
		return forwardedOffice;
	}
	public void setForwardedOffice(String forwardedOffice) {
		this.forwardedOffice = forwardedOffice;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTrackingnum() {
		return trackingnum;
	}
	public void setTrackingnum(String trackingnum) {
		this.trackingnum = trackingnum;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}

	public Date getDateTime() {
		return dateTime;
	}
	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}
	public String getSrcOffice() {
		return srcOffice;
	}
	public void setSrcOffice(String srcOffice) {
		this.srcOffice = srcOffice;
	}
	public String getCurrentOffice() {
		return currentOffice;
	}
	public void setCurrentOffice(String currentOffice) {
		this.currentOffice = currentOffice;
	}
	

	

}
