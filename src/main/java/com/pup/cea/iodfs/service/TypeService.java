package com.pup.cea.iodfs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pup.cea.iodfs.dao.TypeRepository;
import com.pup.cea.iodfs.dao.UserInfoRepository;
import com.pup.cea.iodfs.model.Type;
import com.pup.cea.iodfs.model.UserInfo;



@Service
public class TypeService {
	
	@Autowired 
	TypeRepository typeRepo;

	public void save(Type type) {
		typeRepo.save(type);
	}
	
	public List<Type> findAll(){
		return typeRepo.findAll();
	}
	
	public Type findByDocType(String docType) {
		return typeRepo.findByDocType(docType);
	}
	
	public Type findDocType(Long id) {
		return typeRepo.findById(id).get();
	}
	
	
	
	
	

}
