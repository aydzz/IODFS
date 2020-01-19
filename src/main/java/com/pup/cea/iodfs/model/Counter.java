package com.pup.cea.iodfs.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="counter")
public class Counter {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;

	private int ctr;
	@Column(unique=true)
	private String docAbbrev;
	

	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}

	public int getCtr() {
		return ctr;
	}
	
	public void setCtr(int ctr) {
		this.ctr = ctr;
	}

	public String getDocAbbrev() {
		return docAbbrev;
	}
	
	public void setDocAbbrev(String docAbbrev) {
		this.docAbbrev = docAbbrev;
	}
}
