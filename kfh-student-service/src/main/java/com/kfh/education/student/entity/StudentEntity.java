package com.kfh.education.student.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "student")
@EntityListeners(value = { AuditingEntityListener.class })
public class StudentEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long studentId;
	@Column(nullable = false)
	private String name;
	@Column(nullable = false)
	@Email
	private String email;
	@Column(nullable = false)
	private String telephoneNumber;
	@Lob
	@Column(nullable = false)
	private String address;
	@Column(nullable = false)
	private Date creationDate;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "course_id", nullable = true)
	private CourseEntity course;

	public StudentEntity() {

	}

	public StudentEntity(Long studentId, String name, String email, String telephoneNumber, String address,
			Date creationDate) {
		super();
		this.studentId = studentId;
		this.name = name.trim();
		this.email = email.trim().toLowerCase();
		this.telephoneNumber = telephoneNumber.trim();
		this.address = address.trim();
		this.creationDate = creationDate;
	}

	public StudentEntity(Long studentId, String name, String email, String telephoneNumber, String address,
			Date creationDate, CourseEntity course) {
		super();
		this.studentId = studentId;
		this.name = name.trim();
		this.email = email.trim().toLowerCase();
		this.telephoneNumber = telephoneNumber.trim();
		this.address = address.trim();
		this.creationDate = creationDate;
		this.course = course;
	}

	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
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

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public CourseEntity getCourse() {
		return course;
	}

	public void setCourse(CourseEntity course) {
		this.course = course;
	}

	@Override
	public String toString() {
		return "Student [studentId=" + studentId + ", name=" + name + ", email=" + email + ", telephoneNumber="
				+ telephoneNumber + ", address=" + address + ", creationDate=" + creationDate + ", course=" + course
				+ "]";
	}

}