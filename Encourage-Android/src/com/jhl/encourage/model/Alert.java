package com.jhl.encourage.model;



public class Alert extends Notification{
	
	
	private String dateTimeDiff;
	private String contenType;
	
	private String authorName;
	private String title;
	private String details;
	
	
	

	public String getDateTimeDiff() {
		return dateTimeDiff;
	}

	public void setDateTimeDiff(String dateTimeDiff) {
		this.dateTimeDiff = dateTimeDiff;
	}

	public String getContenType() {
		return contenType;
	}

	public void setContenType(String contenType) {
		this.contenType = contenType;
	}

	

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}
	
	
	
	@Override
	public String toString() {
		return this.id + " " + this.details;
	}
	
	
}
