/**
 * 
 */
package com.kfh.education.courses.dto;

public class DependencyException extends RuntimeException {

	private static final long serialVersionUID = 646182706219385282L;

	private final String message;

	public DependencyException(String message) {
		super(message);
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

}