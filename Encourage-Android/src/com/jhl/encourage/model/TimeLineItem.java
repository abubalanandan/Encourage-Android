package com.jhl.encourage.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class TimeLineItem implements Comparable<TimeLineItem>{

	private String timelineDate;
	private String filename;
	private String recordKey;
	private String timelineid;
	private String person;
	private String title;
	private String datatype;
	private String type;
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	private String eventAddress;
	private Date date;

	public Date getDate(){
		return date;
	}
	
	public String getEventAddress() {
		return eventAddress;
	}

	public void setEventAddress(String eventAddress) {
		this.eventAddress = eventAddress;
	}

	private HashMap<String, String> details;

	public String getTimelineDate() {
		return timelineDate;
	}

	public void setTimelineDate(String timelineDate) {
		this.timelineDate = timelineDate;
		try {
			this.date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH).parse(this.timelineDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getRecordKey() {
		return recordKey;
	}

	public void setRecordKey(String recordKey) {
		this.recordKey = recordKey;
	}

	public String getTimelineid() {
		return timelineid;
	}

	public void setTimelineid(String timelineid) {
		this.timelineid = timelineid;
	}

	public String getPerson() {
		return person;
	}

	public void setPerson(String person) {
		this.person = person;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDatatype() {
		return datatype;
	}

	public void setDatatype(String datatype) {
		this.datatype = datatype;
	}

	public HashMap<String, String> getDetails() {
		return details;
	}

	public void setDetails(String detailObject) {
		this.details = new HashMap<String,String>();
		if(detailObject!=null && detailObject.length() > 0){
			String[] details = detailObject.split("\\^~\\^");
			for(String detail:details){
				String[] pair = detail.split("&nbsp;\\|&nbsp;");
				String key ="";
				String value="";
				if(pair.length!=0)
				 key = pair[0];
				
				if(pair.length>1)
				 value = pair[1];
				this.details.put(key, value);
			}
		}
	}

	@Override
	public boolean equals(Object o) {
		if(o instanceof TimeLineItem){
			TimeLineItem item = (TimeLineItem)o;
			
		
			if(timelineid.equalsIgnoreCase(item.getTimelineid()) ){
				return true;
			}
		}
		return false;
	}
	
	@Override
	public int compareTo(TimeLineItem another) {
		return this.date.compareTo(another.getDate());
	}

	@Override
	public int hashCode() {
		return Integer.parseInt(this.timelineid);
	}

}
