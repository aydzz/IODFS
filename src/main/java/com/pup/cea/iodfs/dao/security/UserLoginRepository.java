package com.pup.cea.iodfs.dao.security;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pup.cea.iodfs.model.security.UserLogin;

public interface UserLoginRepository extends JpaRepository<UserLogin,Long>{
	UserLogin findByUsername (String username);
}
