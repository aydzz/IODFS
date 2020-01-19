package com.pup.cea.iodfs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pup.cea.iodfs.dao.HistoryRepository;

import com.pup.cea.iodfs.model.History;



@Service
public class HistoryService {
	
	@Autowired 
	private HistoryRepository historyRepos;

	
	public List<History> findAll(){
		return historyRepos.findAll();
	}
	


}
