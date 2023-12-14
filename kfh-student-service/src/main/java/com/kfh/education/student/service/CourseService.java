package com.kfh.education.student.service;

import java.util.Map;

public interface CourseService {
	Map<String, Object> getCourses(String authHeader);
}