package com.pup.cea.iodfs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pup.cea.iodfs.dao.UserInfoRepository;
import com.pup.cea.iodfs.model.UserInfo;

@Service
public class UserInfoService {
	@Autowired 
	private UserInfoRepository userInfoRepo;
	
	public UserInfo findByUsername(String username) {
		return userInfoRepo.findByUsername(username);
	}

}
