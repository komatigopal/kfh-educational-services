package com.kfh.education.courses.dto;

import java.io.Serializable;

public class CourseDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long courseCode;
	private String name;

	public CourseDto(long courseCode, String name) {
		super();
		this.courseCode = courseCode;
		this.name = name.trim().toUpperCase();
	}

	public long getCourseCode() {
		return courseCode;
	}

	public void setCourseCode(long courseCode) {
		this.courseCode = courseCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name.trim().toUpperCase();
	}

	@Override
	public String toString() {
		return "Course [courseCode=" + courseCode + ", name=" + name + "]";
	}

}