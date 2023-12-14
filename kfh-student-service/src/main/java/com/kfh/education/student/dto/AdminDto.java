package com.kfh.education.student.dto;

import java.io.Serializable;

public class AdminDto implements Serializable {
	private static final long serialVersionUID = 1L;
	private String name;
	private String password;
	private String userName;

	public AdminDto() {

	}

	public AdminDto(String userName, String password) {
		super();
		this.password = password.trim();
		this.userName = userName.trim();
	}

	public AdminDto(String name, String userName, String password) {
		super();
		this.name = name.trim();
		this.password = password.trim();
		this.userName = userName.trim();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name.trim();
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password.trim();
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName.trim();
	}

	@Override
	public String toString() {
		return "Admin [name=" + name + ", password=" + password + ", userName=" + userName + "]";
	}
}