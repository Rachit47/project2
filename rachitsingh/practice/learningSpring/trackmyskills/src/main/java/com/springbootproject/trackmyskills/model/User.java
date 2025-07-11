package com.springbootproject.trackmyskills.model;

public class User {
	private Integer userID;
	private String username;

	public User() {
	}

	public User(Integer userID, String username) {
		this.setUserID(userID);
		this.setUsername(username);
	}

	public Integer getUserID() {
		return userID;
	}

	public void setUserID(Integer userID) {
		this.userID = userID;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
