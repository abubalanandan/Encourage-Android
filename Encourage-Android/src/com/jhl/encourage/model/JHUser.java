package com.jhl.encourage.model;

public class JHUser {

	private String token;
	private String username;
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public JHUser(String token, String username) {
		this.token = token;
		this.username = username;
	}
	
	
	
	
}
