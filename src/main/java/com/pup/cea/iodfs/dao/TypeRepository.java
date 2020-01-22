package com.pup.cea.iodfs.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pup.cea.iodfs.model.Document;
import com.pup.cea.iodfs.model.Type;

public interface TypeRepository extends JpaRepository<Type,Long> {
	Type findByDocType(String docType);
	
	void deleteById(Long Id);
}
