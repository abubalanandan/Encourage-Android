package com.jhl.encourage.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class CareTask extends Notification{
	
	private String careTaskType ;
	
	private String providerName;
	private String careplanName;
	private String cpDetails;
	
	private String title;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getCareTaskType() {
		return careTaskType;
	}
	public void setCareTaskType(String careTaskType) {
		this.careTaskType = careTaskType;
	}
	
	public String getProviderName() {
		return providerName;
	}
	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}
	public String getCareplanName() {
		return careplanName;
	}
	public void setCareplanName(String careplanName) {
		this.careplanName = careplanName;
	}
	public String getCpDetails() {
		return cpDetails;
	}
	public void setCpDetails(String cpDetails) {
		this.cpDetails = cpDetails;
	}
	

	
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
		try {
			this.date = new SimpleDateFormat("MMMM dd yyyy hh:mmaa", Locale.ENGLISH).parse(this.dateTime.replace("@", " "));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@Override
	public String toString() {
		return this.id;
	}
	
	
}
