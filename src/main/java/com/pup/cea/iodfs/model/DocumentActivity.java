package com.pup.cea.iodfs.model;

public class DocumentActivity {
	
	private String dateOfActivity;
	private int numberOfAction;
	private int transfer;
	private int successfulTransfer;
	private int creation;
	private int release;

	public void setCreation(int creation) {
		this.creation = creation;
	}
	public String getDateOfActivity() {
		return dateOfActivity;
	}
	public void setDateOfActivity(String dateOfActivity) {
		this.dateOfActivity = dateOfActivity;
	}
	public int getNumberOfAction() {
		return numberOfAction;
	}
	public void setNumberOfAction(int numberOfAction) {
		this.numberOfAction = numberOfAction;
	}
	
	public int getTransfer() {
		return transfer;
	}
	public void setTransfer(int transfer) {
		this.transfer = transfer;
	}
	public int getSuccessfulTransfer() {
		return successfulTransfer;
	}
	public void setSuccessfulTransfer(int successfulTransfer) {
		this.successfulTransfer = successfulTransfer;
	}
	public int getCreation() {
		return creation;
	}
	
	
	public int getRelease() {
		return release;
	}
	public void setRelease(int release) {
		this.release = release;
	}
	

}
