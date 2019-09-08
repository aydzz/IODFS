package com.pup.cea.iodfs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pup.cea.iodfs.dao.OfficeRepository;
import com.pup.cea.iodfs.model.Office;

@Service
public class OfficeService {
	@Autowired
	OfficeRepository officeRepo;
	
	public List<Office> findAll(){
		return officeRepo.findAll();
	}
	

}
