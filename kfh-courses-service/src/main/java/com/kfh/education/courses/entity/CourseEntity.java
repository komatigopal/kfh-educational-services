package com.kfh.education.courses.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "course")
@EntityListeners(value = { AuditingEntityListener.class })
public class CourseEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long courseCode;
	@Column(nullable = false)
	private String name;
	private Date creationDate;

	public CourseEntity() {

	}

	public CourseEntity(Long courseCode, String name, Date creationDate) {
		super();
		this.courseCode = courseCode;
		this.name = name.trim().toUpperCase();
		this.creationDate = creationDate;
	}

	public Long getCourseCode() {
		return courseCode;
	}

	public void setCourseCode(Long courseCode) {
		this.courseCode = courseCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name.trim().toUpperCase();
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Override
	public String toString() {
		return "Course [courseCode=" + courseCode + ", name=" + name + ", creationDate=" + creationDate + "]";
	}

}