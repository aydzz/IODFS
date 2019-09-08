package com.pup.cea.iodfs.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="office")
public class Office {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	private String officename;
	private String officecode;
	private String officetype;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getOfficename() {
		return officename;
	}
	public void setOfficename(String officename) {
		this.officename = officename;
	}
	public String getOfficecode() {
		return officecode;
	}
	public void setOfficecode(String officecode) {
		this.officecode = officecode;
	}
	public String getOfficetype() {
		return officetype;
	}
	public void setOfficetype(String officetype) {
		this.officetype = officetype;
	}
	

}
