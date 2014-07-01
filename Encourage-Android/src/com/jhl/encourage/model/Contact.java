package com.jhl.encourage.model;

public class Contact {
	String id;
	String name;
	String email;
	boolean selected = false;
	boolean addToCC = false;
	
	
	public Contact() {
		// TODO Auto-generated constructor stub
	}
	
	public Contact(String name, String email, boolean selected, boolean addToCC) {
		this.name = name;
		this.email = email;
		this.selected = selected;
		this.addToCC = addToCC;
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
	
//	private String name1 = "";
//	private String email1 = "";
//	private boolean addToCC1 = false;
//	private String name2 = "";
//	private String email2 = "";
//	private boolean addToCC2 = false;
//	public String getName1() {
//		return name1;
//	}
//	public void setName1(String name1) {
//		this.name1 = name1;
//	}
//	public String getEmail1() {
//		return email1;
//	}
//	public void setEmail1(String email1) {
//		this.email1 = email1;
//	}
//	public boolean isAddToCC1() {
//		return addToCC1;
//	}
//	public void setAddToCC1(boolean addToCC1) {
//		this.addToCC1 = addToCC1;
//	}
//	public String getName2() {
//		return name2;
//	}
//	public void setName2(String name2) {
//		this.name2 = name2;
//	}
//	public String getEmail2() {
//		return email2;
//	}
//	public void setEmail2(String email2) {
//		this.email2 = email2;
//	}
//	public boolean isAddToCC2() {
//		return addToCC2;
//	}
//	public void setAddToCC2(boolean addToCC2) {
//		this.addToCC2 = addToCC2;
//	}
	
	
	@Override
	public boolean equals(Object o) {
		Contact other = (Contact)o;
		return this.email.equals(other.getEmail());
	}
}
