package com.pup.cea.iodfs.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import com.pup.cea.iodfs.model.Document;

@Service
public class NotificationService {
	private JavaMailSender javaMailSender;
	
	private TemplateEngine templateEngine;
	
	@Autowired
	public NotificationService( JavaMailSender javaMailSender,TemplateEngine templateEngine) {
		this.javaMailSender = javaMailSender;	
		 this.templateEngine = templateEngine;
	}
	
	public void sendNotification(Document document, String subject, String message) throws MailException{
		SimpleMailMessage notificationMail = new SimpleMailMessage();
		
		
		notificationMail.setTo(document.getEmailAddress());
		notificationMail.setFrom("pup.interoffice.dfts@gmail.com");
		notificationMail.setSubject(subject);
		notificationMail.setText(message);
		
		javaMailSender.send(notificationMail);
	}
	
	 
	//BUILDER METHODS
    public String buildCreatedDocMail(String recipient, String action, String message, String dateCreated, String timeCreated) {
       
		/*
		 * String parsedData = ""; String iconName = "static/images/logo/iods-icon.ico";
		 * 
		 * ClassLoader classLoader = ClassLoader.getSystemClassLoader(); File file = new
		 * File(classLoader.getResource(iconName).getFile());
		 * System.out.println("File Found : " + file.exists()); try { byte[] data =
		 * Files.readAllBytes(file.toPath()); parsedData = "data:image/ico;base64,"+
		 * Base64.getEncoder().encodeToString(data);
		 * 
		 * 
		 * } catch (IOException e) { System.out.println("Cant read the Icon File");
		 * e.printStackTrace(); } System.out.println(parsedData);
		 * context.setVariable("imageData",parsedData );
		 */
    	Context context = new Context();
    	context.setVariable("recipient", recipient);
    	context.setVariable("action", action);
        context.setVariable("message", message);
        context.setVariable("dateCreated",dateCreated);
        context.setVariable("timeCreated", timeCreated);
        
        return templateEngine.process("public/notificationMail", context);
    }
    
    
    public String buildTransferedDocMail(String recipient, 
    									String action, 
    									String message, 
    									String dateTransfered, 
    									String timeTransfered,
    									String transferedTo,
    									String trackingNumber) {
    	Context context = new Context();
    	context.setVariable("recipient", recipient);
    	context.setVariable("action", action);
        context.setVariable("message", message);
        context.setVariable("dateTransfered",dateTransfered);
        context.setVariable("timeTransfered", timeTransfered);
        context.setVariable("transferedTo", transferedTo);
        context.setVariable("trackingNumber", trackingNumber);
        
        return templateEngine.process("public/notificationMail", context);
    }
	    public String buildReleasedDocMail(String recipient, 
				String action, 
				String message, 
				String dateReleased, 
				String timeReleased,
				String status,
				String remark,
				String trackingNumber) {
		Context context = new Context();
		context.setVariable("recipient", recipient);
		context.setVariable("action", action);
		context.setVariable("message", message);
		context.setVariable("dateReleased",dateReleased);
		context.setVariable("timeReleased", timeReleased);
		context.setVariable("status", status);
		context.setVariable("remark", remark);
		context.setVariable("trackingNumber", trackingNumber);
		
		return templateEngine.process("public/notificationMail", context);
	}
    //SENDER METHODS
    public void sendCreatedDocMail(String recipient, String message, String dateCreated, String timeCreated ) {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
        	MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            String content = this.buildCreatedDocMail(recipient,"created", message,dateCreated,timeCreated);
            
            messageHelper.setText(content, true);
            messageHelper.setFrom("pup.interoffice.dfts@gmail.com");
            messageHelper.setTo(recipient);
            messageHelper.setSubject("IODTFS Notificaton Service");
        };
        try {
        	javaMailSender.send(messagePreparator);
        	System.out.println("Email was sent successfully!");
        } catch (MailException e) {
            System.out.print("Error on Notification Service: Failed to send Email!");
        }
       
    }
    public void sendForwardedDocMail(String recipient, 
									String message, 
									String dateTransfered, 
									String timeTransfered,
									String transferedTo,
									String trackingNumber) {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
        	MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            String content = this.buildTransferedDocMail(recipient, "forwarded", message, dateTransfered, timeTransfered, transferedTo, trackingNumber);
            
            messageHelper.setText(content, true);
            messageHelper.setFrom("pup.interoffice.dfts@gmail.com");
            messageHelper.setTo(recipient);
            messageHelper.setSubject("IODTFS Notificaton Service");
        };
        try {
        	javaMailSender.send(messagePreparator);
        	System.out.println("Email was sent successfully!");
        } catch (MailException e) {
            System.out.print("Error on Notification Service: Failed to send Email!");
        }
       
    }
    public void sendRecievedDocMail(String recipient, 
			String message, 
			String dateTransfered, 
			String timeTransfered,
			String transferedTo,
			String trackingNumber) {
				MimeMessagePreparator messagePreparator = mimeMessage -> {
				MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
				String content = this.buildTransferedDocMail(recipient, "recieved", message, dateTransfered, timeTransfered, transferedTo, trackingNumber);
				
					messageHelper.setText(content, true);
					messageHelper.setFrom("pup.interoffice.dfts@gmail.com");
					messageHelper.setTo(recipient);
					messageHelper.setSubject("IODTFS Notificaton Service");
				};
				try {
					javaMailSender.send(messagePreparator);
					System.out.println("Email was sent successfully!");
				} catch (MailException e) {
					System.out.print("Error on Notification Service: Failed to send Email!");
				}
				
			}
    public void sendReleasedDocMail(String recipient, 
			String message, 
			String dateReleased, 
			String timeReleased,
			String status,
			String remark,
			String trackingNumber) {
				MimeMessagePreparator messagePreparator = mimeMessage -> {
				MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
				String content = this.buildReleasedDocMail(recipient, "released", message, dateReleased, timeReleased, status, remark, trackingNumber);
				
					messageHelper.setText(content, true);
					messageHelper.setFrom("pup.interoffice.dfts@gmail.com");
					messageHelper.setTo(recipient);
					messageHelper.setSubject("IODTFS Notificaton Service");
				};
				try {
					javaMailSender.send(messagePreparator);
					System.out.println("Email was sent successfully!");
				} catch (MailException e) {
					System.out.print("Error on Notification Service: Failed to send Email!");
				}
				
			}
    
    
	
}
