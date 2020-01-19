package com.pup.cea.iodfs.ajax.form;

import org.hibernate.validator.constraints.NotBlank;

public class ForwardDocumentForm {
	
	@SuppressWarnings("deprecation")
	@NotBlank(message="Tracking number cannot be Empty")
	private String trackingNumber;
	
	@SuppressWarnings("deprecation")
	@NotBlank(message="Destination cannot be Empty")
	private String destination;
	
	/*
	 * //this can be empty. nilagay ko lang para testing for error handling
	 * 
	 * @SuppressWarnings("deprecation")
	 * 
	 * @NotBlank(message="Remarks cannot be Empty")
	 */
	private String remark;

	public String getTrackingNumber() {
		return trackingNumber;
	}

	public void setTrackingNumber(String trackingNumber) {
		this.trackingNumber = trackingNumber;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}
