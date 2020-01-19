package com.pup.cea.iodfs.dao.security;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pup.cea.iodfs.model.Document;
import com.pup.cea.iodfs.model.UserInfo;
import com.pup.cea.iodfs.model.security.UserLogin;

public interface UserLoginRepository extends JpaRepository<UserLogin,Long>{
	
	public UserLogin findByUsername (String username);
	
	@Query(value="SELECT * FROM user_login u WHERE u.username = :username",nativeQuery = true)
	List<UserLogin> findByUname(@Param("username")String username);
	
}
