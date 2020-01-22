package com.pup.cea.iodfs.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.pup.cea.iodfs.dao.DocumentRepository;
import com.pup.cea.iodfs.exception.FileStorageException;
import com.pup.cea.iodfs.exception.MyFileNotFoundException;
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
	

	
	public Document storeFile(MultipartFile file) {
		
		// Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            Document fileUpload = new Document(fileName, file.getContentType(), file.getBytes());

            return docRepo.save(fileUpload);
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public Document getFile(Long Id) {
        return docRepo.findById(Id)
                .orElseThrow(() -> new MyFileNotFoundException("File not found with id " + Id));
    }
	
	
	
	
}
