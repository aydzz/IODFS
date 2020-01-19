package com.pup.cea.iodfs.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pup.cea.iodfs.model.Document;
import com.pup.cea.iodfs.model.UserInfo;
import com.pup.cea.iodfs.model.security.UserLogin;

public interface UserInfoRepository extends JpaRepository<UserInfo,Long> {
	public UserInfo findByUsername(String username);

	@Query(value="SELECT * FROM user u WHERE u.username = :username",nativeQuery = true)
	List<UserInfo> findByUnname(@Param("username")String username);
	
	
}
