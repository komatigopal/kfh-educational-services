package com.kfh.education.courses.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.kfh.education.courses.dto.AdminDto;
import com.kfh.education.courses.dto.CourseDto;

public interface AdminService {

	ResponseEntity<?> createAuthenticationToken(AdminDto adminDto);

	Map<String, Object> addCourse(String courseName, String authHeader);

	Map<String, Object> updateCourse(CourseDto courseDto, String authHeader);

	Map<String, Object> deleteCourse(Long courseId, String authHeader);

}