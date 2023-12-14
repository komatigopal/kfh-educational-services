package com.kfh.education.courses.service;

import java.util.Map;

import com.kfh.education.courses.dto.CourseDto;

public interface CourseService {

	Map<String, Object> addCourse(String courseName, String authHeader);

	Map<String, Object> getCourses(String authHeader);

	Map<String, Object> updateCourse(CourseDto courseDto, String authHeader);

	Map<String, Object> deleteCourse(Long courseId, String authHeader);

	Map<String, Object> getCourseByName(String courseName, String authHeader);
}