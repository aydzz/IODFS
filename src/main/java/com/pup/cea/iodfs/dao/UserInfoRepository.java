package com.pup.cea.iodfs.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pup.cea.iodfs.model.UserInfo;

public interface UserInfoRepository extends JpaRepository<UserInfo,Long> {
	public UserInfo findByUsername(String username);

}
