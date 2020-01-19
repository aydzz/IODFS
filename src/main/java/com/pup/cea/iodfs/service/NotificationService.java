package com.pup.cea.iodfs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.pup.cea.iodfs.model.Document;

@Service
public class NotificationService {
	private JavaMailSender javaMailSender;
	
	@Autowired
	public NotificationService( JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;	
	}
	
	public void sendNotification(Document document, String subject, String message) throws MailException{
		SimpleMailMessage notificationMail = new SimpleMailMessage();
		
		notificationMail.setTo(document.getEmailAddress());
		notificationMail.setFrom("pup.interoffice.dfts@gmail.com");
		notificationMail.setSubject(subject);
		notificationMail.setText(message);
		
		javaMailSender.send(notificationMail);
	}
	
}
