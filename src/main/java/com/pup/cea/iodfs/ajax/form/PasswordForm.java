package com.pup.cea.iodfs.ajax.form;

import org.hibernate.validator.constraints.NotBlank;

public class PasswordForm {
	
	@SuppressWarnings("deprecation")
	@NotBlank(message="Old Password Field Cannot be Empty")
	private String oldPassword;
	
	@SuppressWarnings("deprecation")
	@NotBlank(message="Old Password Field Cannot be Empty")
	private String newPassword;
	
	@SuppressWarnings("deprecation")
	@NotBlank(message="Old Password Field Cannot be Empty")
	private String confirmPassword;
	
	
	public String getOldPassword() {
		return oldPassword;
	}
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	

}
