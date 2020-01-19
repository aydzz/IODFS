package com.pup.cea.iodfs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pup.cea.iodfs.dao.UserInfoRepository;
import com.pup.cea.iodfs.dao.UserLogsRepository;
import com.pup.cea.iodfs.dao.security.UserLoginRepository;
import com.pup.cea.iodfs.model.Document;
import com.pup.cea.iodfs.model.UserInfo;
import com.pup.cea.iodfs.model.UserLogs;
import com.pup.cea.iodfs.model.security.UserLogin;


@Service
public class UserLogsService {
	
	@Autowired 
	private UserLogsRepository userLogsRepos;

	
	public List<UserLogs> findAll(){
		return userLogsRepos.findAll();
	}
	


}
