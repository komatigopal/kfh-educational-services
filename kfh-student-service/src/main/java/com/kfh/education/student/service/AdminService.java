package com.kfh.education.student.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.kfh.education.student.dto.AdminDto;

public interface AdminService {

	ResponseEntity<?> createAuthenticationToken(AdminDto adminDto);

	Map<String, Object> updateStudentCourse(Long studentId, String courseName, String authHeader);

	Map<String, Object> deleteStudent(Long studentId, String authHeader);

	Map<String, Object> getStudents(String authHeader);
}