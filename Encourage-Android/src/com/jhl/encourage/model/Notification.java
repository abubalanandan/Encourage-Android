package com.jhl.encourage.model;

public class Notification {
	
	private String id;
	private String message;
	private String time;
	private int type;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	
	@Override
	public boolean equals(Object o) {
		if(o instanceof Notification){
			Notification n = (Notification)o;
			if(id.equals(n.getId())){
				return true;
			}
		}
		return false;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.id + " " + this.message;
	}
}
