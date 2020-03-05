package com.pup.cea.iodfs.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pup.cea.iodfs.model.DocumentHistory;

public interface DocumentHistoryRepository extends JpaRepository<DocumentHistory, Long> {
	
	@Query(value="SELECT * FROM document_history u WHERE u.trackingnum = :string ORDER BY u.date_time DESC",nativeQuery = true)
	List<DocumentHistory> findByTrackingnum(@Param("string") String trackingnum);

}
