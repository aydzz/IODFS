package com.pup.cea.iodfs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pup.cea.iodfs.dao.DocumentRepository;
import com.pup.cea.iodfs.model.Document;



@Service
public class DocumentService {
	
	@Autowired
	DocumentRepository docRepo;
	
	public void save(Document document) {
		docRepo.save(document);
		
	}
	public List<Document> findAll(){
		return docRepo.findAll();
	}
	public Document findByTrackingnum(String trackingnum) {
		return docRepo.findByTrackingnum(trackingnum);
	}
	public List<Document> findByOfficeAndStatus(String current_office, String status){
		return docRepo.findByOfficeAndStatus(current_office, status);
	}
	public List<Document> findIncoming(String forwarded_office, String status){
		return docRepo.findIncoming(forwarded_office, status);
	}
	
	public List<Document> findByStatusWildcard(String string){
		return docRepo.findByStatusWildCard(string);
		
	}
	
	
}
