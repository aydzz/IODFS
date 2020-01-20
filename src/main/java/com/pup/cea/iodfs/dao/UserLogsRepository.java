package com.pup.cea.iodfs.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pup.cea.iodfs.model.Document;
import com.pup.cea.iodfs.model.UserLogs;

public interface UserLogsRepository extends JpaRepository<UserLogs,Long> {
	
	UserLogs findByCts(String cts);
	
	
	@Query(value="SELECT * FROM user_logs u WHERE u.action LIKE :action AND u.datetime LIKE :date",nativeQuery = true)
	List<UserLogs> findByActionWildCard(@Param("action") String action, @Param("date") String date);
	
	@Query(value="SELECT * FROM user_logs u WHERE u.datetime LIKE :string",nativeQuery = true)
	List<UserLogs> findByDateWildCard(@Param("string") String date);

}
