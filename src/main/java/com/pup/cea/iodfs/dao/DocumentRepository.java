package com.pup.cea.iodfs.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pup.cea.iodfs.model.Document;

public interface DocumentRepository extends JpaRepository<Document,Long> {
	Document findByTrackingnum(String trackingnum);
	
	@Query(value="SELECT * FROM document u WHERE u.current_office = :current and u.status = :status",nativeQuery = true)
	List<Document> findByOfficeAndStatus(@Param("current")String current_office,
										 @Param("status") String status);
	
	@Query(value="SELECT * FROM document u WHERE u.forwarded_office = :forwarded and u.status = :status",nativeQuery = true)
	List<Document> findIncoming(@Param("forwarded")String forwarded_office,
			 					 @Param("status") String status);
}
