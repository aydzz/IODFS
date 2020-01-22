package com.pup.cea.iodfs.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name="document")
public class Document{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	@Column(unique=true)
	private String trackingnum;
	private String doctype;
	private String description;
	private String date_received;
	//Tracker attribute
	private String source_office;
	private String current_office;
	private String forwarded_office;
	private String status;
	private String remark;
	
	//files
		private String fileName;
	    private String fileType;
	    @Lob
	    private byte[] data;
	
	@Column(name="email_address")
	private String emailAddress;
	
	
	public Document() {
		
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getDoctype() {
		return doctype;
	}
	public void setDoctype(String doctype) {
		this.doctype = doctype;
	}
	public String getTrackingnum() {
		return trackingnum;
	}
	public void setTrackingnum(String trackingnum) {
		this.trackingnum = trackingnum;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDate_received() {
		return date_received;
	}
	public void setDate_received(String date_received) {
		this.date_received = date_received;
	}
	
	//TRACKING G AND S

	public String getSource_office() {
		return source_office;
	}

	public void setSource_office(String source_office) {
		this.source_office = source_office;
	}

	public String getCurrent_office() {
		return current_office;
	}

	public void setCurrent_office(String current_office) {
		this.current_office = current_office;
	}

	public String getForwarded_office() {
		return forwarded_office;
	}

	public void setForwarded_office(String forwarded_office) {
		this.forwarded_office = forwarded_office;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	//files
	public Document(String fileName, String fileType, byte[] data) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.data = data;
    }


    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
	
	
	
	
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
}
