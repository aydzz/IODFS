package com.pup.cea.iodfs.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pup.cea.iodfs.model.Counter;
import com.pup.cea.iodfs.model.Document;
import com.pup.cea.iodfs.model.Type;

public interface CounterRepository extends JpaRepository<Counter,Long> {
	Counter findByCtr(String ctr);
	
}
