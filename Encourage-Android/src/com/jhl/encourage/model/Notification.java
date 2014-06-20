package com.jhl.encourage.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.jhl.encourage.utilities.JHConstants;

import android.util.Log;

public class Notification implements Comparable<Notification>{
	
	protected String notificationType;
	protected String id;
	protected String readStatus;
	protected Date date;
	protected String dateTime;
	
	public String getReadStatus() {
		return readStatus;
	}

	public void setReadStatus(String readStatus) {
		this.readStatus = readStatus;
	}
	
	public Notification() {
		
	}
	
	public Notification(String id){
		this.id = id;
	}
	
	public String getNotificationType() {
		return notificationType;
	}

	public void setNotificationType(String notificationType) {
		this.notificationType = notificationType;
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

	public Date getDate() {
		return this.date;
	}
	
	@Override
	public boolean equals(Object o) {
		if(o instanceof Notification){
			Notification n = (Notification)o;
			
			Log.d(JHConstants.LOG_TAG, "equals id "+id);
			Log.d(JHConstants.LOG_TAG, "equals n.getId() "+n.getId());
			Log.d(JHConstants.LOG_TAG, "equals notificationType "+notificationType);
			Log.d(JHConstants.LOG_TAG, "equals n.getNotificationType() "+n.getNotificationType());
			
			if(id.equals(n.getId()) && notificationType.equals(n.getNotificationType())){
				return true;
			}
		}
		return false;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public int compareTo(Notification another) {
		return this.date.compareTo(another.getDate());
	}
}
