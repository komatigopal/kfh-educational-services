package com.kfh.education.courses.dto;

public class ExceptionObject {
	private final String exceptionMessage;
	private final String exceptionType;

	public ExceptionObject(String exceptionMessage, String exceptionType) {
		super();
		this.exceptionMessage = exceptionMessage;
		this.exceptionType = exceptionType;
	}

	public String getExceptionMessage() {
		return exceptionMessage;
	}

	public String getExceptionType() {
		return exceptionType;
	}
}