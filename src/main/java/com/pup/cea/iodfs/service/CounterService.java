package com.pup.cea.iodfs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pup.cea.iodfs.dao.CounterRepository;
import com.pup.cea.iodfs.dao.TypeRepository;
import com.pup.cea.iodfs.dao.UserInfoRepository;
import com.pup.cea.iodfs.model.Counter;
import com.pup.cea.iodfs.model.Type;
import com.pup.cea.iodfs.model.UserInfo;



@Service
public class CounterService {
	
	@Autowired 
	CounterRepository counterRepo;

	public void save(Counter counter) {
		counterRepo.save(counter);
	}
	
	public List<Counter> findAll(){
		return counterRepo.findAll();
	}
	
	public Counter findByCtr(String ctr) {
		return counterRepo.findByCtr(ctr);
	}
	
	
	
	
	

}
