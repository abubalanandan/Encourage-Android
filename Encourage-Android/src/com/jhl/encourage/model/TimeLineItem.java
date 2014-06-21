package com.jhl.encourage.model;

import java.util.HashMap;

public class TimeLineItem {

	private String timelineDate;
	private String filename;
	private String recordKey;
	private String timelineid;
	private String person;
	private String title;
	private String datatype;
	private String eventAddress;

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
	}

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
}
