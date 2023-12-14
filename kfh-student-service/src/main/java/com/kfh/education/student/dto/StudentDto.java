package com.kfh.education.student.dto;

import java.io.Serializable;

public class StudentDto implements Serializable {
	private static final long serialVersionUID = 1L;
	private String name;
	private String email;
	private String telephoneNumber;
	private String address;

	public StudentDto() {

	}

	public StudentDto(String name, String email, String telephoneNumber, String address) {
		super();
		this.name = name.trim();
		this.email = email.trim().toLowerCase();
		this.telephoneNumber = telephoneNumber.trim();
		this.address = address.trim();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name.trim();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email.trim().toLowerCase();
	}

	public String getTelephoneNumber() {
		return telephoneNumber;
	}

	public void setTelephoneNumber(String telephoneNumber) {
		this.telephoneNumber = telephoneNumber.trim();
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address.trim();
	}

	@Override
	public String toString() {
		return "Student [name=" + name + ", email=" + email + ", telephoneNumber=" + telephoneNumber + ", address="
				+ address + "]";
	}

}