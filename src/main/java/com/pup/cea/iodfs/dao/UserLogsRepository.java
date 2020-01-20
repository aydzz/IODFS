package com.pup.cea.iodfs.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pup.cea.iodfs.model.Document;
import com.pup.cea.iodfs.model.UserLogs;

public interface UserLogsRepository extends JpaRepository<UserLogs,Long> {
	
	UserLogs findByCts(String cts);
	
	
	@Query(value="SELECT * FROM user_logs u WHERE u.action LIKE :string",nativeQuery = true)
	List<UserLogs> findByAddedWildCard(@Param("string") String action);
	
	@Query(value="SELECT * FROM user_logs u WHERE u.datetime LIKE :string",nativeQuery = true)
	List<UserLogs> findByDateWildCard(@Param("string") String date);

}
