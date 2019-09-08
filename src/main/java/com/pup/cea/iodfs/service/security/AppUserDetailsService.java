package com.pup.cea.iodfs.service.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.pup.cea.iodfs.dao.UserInfoRepository;
import com.pup.cea.iodfs.dao.security.UserLoginRepository;
import com.pup.cea.iodfs.model.UserInfo;
import com.pup.cea.iodfs.model.security.UserLogin;

@Service
public class AppUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UserLoginRepository userLoginRepo;
	@Autowired
	private UserInfoRepository userInfoRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserLogin userLogin = userLoginRepo.findByUsername(username);
		UserInfo userInfo = userInfoRepo.findByUsername(username);
		
			
		if(userLogin==null)
			throw new UsernameNotFoundException("User not Found!");

		return new CurrentUser(userLogin, userInfo);	
	}
}
