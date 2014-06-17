package com.jhl.encourage.model;

public class Notification {
	
	private String notificationType;
	private String alertKey;
	private String dateTime;
	private String dateTimeDiff;
	private String contenType;
	private String readStatus;
	private String authorName;
	private String title;
	private String details;
	
	public Notification() {
		
	}
	
	public Notification(String alertKey){
		this.alertKey = alertKey;
	}
	
	public String getNotificationType() {
		return notificationType;
	}

	public void setNotificationType(String notificationType) {
		this.notificationType = notificationType;
	}

	public String getAlertKey() {
		return alertKey;
	}

	public void setAlertKey(String alertKey) {
		this.alertKey = alertKey;
	}

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
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

	public String getReadStatus() {
		return readStatus;
	}

	public void setReadStatus(String readStatus) {
		this.readStatus = readStatus;
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
	public boolean equals(Object o) {
		if(o instanceof Notification){
			Notification n = (Notification)o;
			if(alertKey.equals(n.getAlertKey())){
				return true;
			}
		}
		return false;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.alertKey + " " + this.details;
	}
}
