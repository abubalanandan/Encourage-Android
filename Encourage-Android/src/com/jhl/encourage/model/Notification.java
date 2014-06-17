package com.jhl.encourage.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Notification implements Comparable<Notification>{
	
	private String notificationType;
	private String alertKey;
	private String dateTime;
	private String dateTimeDiff;
	private String contenType;
	private String readStatus;
	private String authorName;
	private String title;
	private String details;
	private Date date;
	
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
		try {
			this.date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH).parse(this.dateTime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	
	public Date getDate() {
		return this.date;
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
		return this.alertKey + " " + this.details;
	}
	
	@Override
	public int compareTo(Notification another) {
		return this.date.compareTo(another.getDate());
	}
}
