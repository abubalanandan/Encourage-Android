package com.jhl.encourage.model;



public class Alert extends Notification{
	
	
	private String dateTimeDiff;
	private String contenType;
	
	private String authorName;
	private String title;
	private String details;
	
	
	private String alertType;
	
	private String authorProfilePicName;
	public String getAuthorProfilePicName() {
		return authorProfilePicName;
	}

	public void setAuthorProfilePicName(String authorProfilePicName) {
		this.authorProfilePicName = authorProfilePicName;
	}



	private String address; // New Jersey City University 2039 Kennedy Boulevard
	private String url; // full url
	private String imageName; // 70799886-2AB7-E64A-0A8D-8DDE2C55D992.jpg etc

	public String getAlertType() {
		return alertType;
	}

	public void setAlertType(String alertType) {
		this.alertType = alertType;
	}

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

	

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
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
