package com.pup.cea.iodfs.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.pup.cea.iodfs.model.Office;

public interface OfficeRepository extends JpaRepository<Office,Long> {

	
	void deleteById(Long id);

}
