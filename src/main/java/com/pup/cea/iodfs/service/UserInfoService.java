package com.pup.cea.iodfs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pup.cea.iodfs.dao.UserInfoRepository;
import com.pup.cea.iodfs.dao.security.UserLoginRepository;
import com.pup.cea.iodfs.model.Document;
import com.pup.cea.iodfs.model.UserInfo;
import com.pup.cea.iodfs.model.security.UserLogin;


@Service
public class UserInfoService {
	
	@Autowired 
	private UserInfoRepository userInfoRepos;
	
	@Autowired
	private UserLoginRepository userLoginRepos;
	
	@Autowired 
	private UserInfoRepository userInfoRepo;
	
	public List<UserInfo> findAll(){
		return userInfoRepo.findAll();
	}
	
	public UserInfo findByUsername(String username) {
		return userInfoRepo.findByUsername(username);
	}
	
	public void save(UserInfo user) {
		userInfoRepos.save(user);	
	}
	
	public UserInfo findUserInfo(Long id) {
		return userInfoRepo.findById(id).get();
	}
	
	public List<UserInfo> findByUnname(String username){
		return userInfoRepo.findByUnname(username);
	}
	
	public void delete(Long id) {
		userInfoRepo.deleteById(id);
	}
	
	/*FOR USER LOGIN REPO*/
	
	public List<UserLogin> findAlls(){
		return userLoginRepos.findAll();
	}
	
	
	public void saves(UserLogin user_login) {
		userLoginRepos.save(user_login);
		
	}
	public UserLogin findUser(Long id) {
		return userLoginRepos.findById(id).get();
	}
	public UserLogin findUsername(String username) {
		return userLoginRepos.findByUsername(username);
	}
	
	public List<UserLogin> findByUname(String username){
		return userLoginRepos.findByUname(username);
	}
	
	public void deletes(Long id) {
		userLoginRepos.deleteById(id);
	}
	
}
