package com.pup.cea.iodfs.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="type")
public class Type {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	@Column(unique=true)
	private String docType;
	private String docAbbrev;
	

	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}

	public String getDocType() {
		return docType;
	}
	
	public void setDocType(String docType) {
		this.docType = docType;
	}

	public String getDocAbbrev() {
		return docAbbrev;
	}
	
	public void setDocAbbrev(String docAbbrev) {
		this.docAbbrev = docAbbrev;
	}
}
