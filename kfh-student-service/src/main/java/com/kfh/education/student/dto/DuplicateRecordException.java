/**
 * 
 */
package com.kfh.education.student.dto;

public class DuplicateRecordException extends RuntimeException {

	private static final long serialVersionUID = 646182706219385282L;

	private final String message;

	public DuplicateRecordException(String message) {
		super(message);
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

}