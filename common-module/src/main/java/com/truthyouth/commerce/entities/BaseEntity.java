package com.truthyouth.commerce.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import lombok.Data;

@MappedSuperclass
@Data
public abstract class BaseEntity {
	
	@Column(name = "date_created", nullable = false, updatable = false)
	protected Date dateCreated;
	
	@Column(name = "date_updated", nullable = false)
	protected Date dateUpdated;
	
	protected String status;
	
	public BaseEntity() {
		super();
	    this.dateCreated = new Date();
	    this.dateUpdated = new Date();
	    this.status = "ACTIVE";
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Date getDateUpdated() {
		return dateUpdated;
	}

	public void setStatus(String statu) {
		this.status = statu;
	}	
	
	public String getStatus() {
		return status;
	}	

	public void setDateUpdated(Date dateUpdated) {
		this.dateUpdated = dateUpdated;
	}	
}
