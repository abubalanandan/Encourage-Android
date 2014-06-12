package com.jhl.encourage.model;

public class Contact {
	String id;
	String name;
	String email;
	boolean selected = false;
	
	
	public Contact(String name, String email, boolean selected) {
		this.name = name;
		this.email = email;
		this.selected = selected;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
	
}
