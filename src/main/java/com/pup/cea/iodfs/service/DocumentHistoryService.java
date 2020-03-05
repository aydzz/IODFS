package com.pup.cea.iodfs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pup.cea.iodfs.dao.DocumentHistoryRepository;
import com.pup.cea.iodfs.model.DocumentHistory;

@Service
public class DocumentHistoryService {
	
	@Autowired
	DocumentHistoryRepository docHistoryRepo;
	
	public void save(DocumentHistory documentHistory) {
		docHistoryRepo.save(documentHistory);
	}
	
	public List<DocumentHistory> findAll(){
		return docHistoryRepo.findAll();
	}
	
	public List<DocumentHistory> findByTrackingnum(String trackingnum){
		return docHistoryRepo.findByTrackingnum(trackingnum);
	}
	

}
