package com.kfh.education.student.service;

import java.util.Map;

import com.kfh.education.student.dto.StudentDto;

public interface StudentService {

	Map<String, Object> saveStudent(StudentDto studentDto);

	Map<String, Object> deleteStudent(Long studentId, String authHeader);

	Map<String, Object> getStudents(String authHeader);

	Map<String, Object> updateStudentCourse(Long studentId, String courseName, String authHeader);

}
