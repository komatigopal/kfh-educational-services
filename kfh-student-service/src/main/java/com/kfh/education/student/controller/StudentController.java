package com.kfh.education.student.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kfh.education.student.dto.StudentDto;
import com.kfh.education.student.service.StudentService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

@RestController
@RequestMapping("/student")
public class StudentController {
	private final Logger log = LoggerFactory.getLogger(StudentController.class);
	@Autowired
	private StudentService studentService;

	@PostMapping
	public Map<String, Object> saveStudent(@RequestBody StudentDto studentDto) {
		log.info("in saveStudent method, studentDto - {}", studentDto);
		return studentService.saveStudent(studentDto);
	}

	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, dataType = "string", paramType = "header", example = "Bearer ") })
	@PutMapping("/allocate/course/{studentId}")
	public Map<String, Object> allocateCourse(@PathVariable Long studentId, @RequestParam String courseName,
			HttpServletRequest request) {
		log.info("in updatestudent method, studentId - {}, courseName - {}", studentId, courseName);
		final String authHeader = request.getHeader("Authorization");
		return studentService.updateStudentCourse(studentId, courseName, authHeader);
	}

	/*
	 * @GetMapping("/{studentId}") public StudentEntity getStudent(@PathVariable
	 * Long studentId) { log.info("in getstudent method, studentId - {}",
	 * studentId); return studentService.getStudent(studentId); }
	 */

	/*
	 * @PutMapping public StudentEntity updateStudent(@RequestBody StudentDto
	 * studentDto) { log.info("in updateStudent method, studentDto - {}",
	 * studentDto); // return studentService.updatestudent(studentDto); return null;
	 * }
	 */

	/*
	 * @PutMapping("/update/course/{studentId}") public StudentEntity
	 * updateStudentCourse(@PathVariable Long studentId, @RequestParam String
	 * courseName) {
	 * log.info("in updatestudent method, studentId - {}, courseName - {}",
	 * studentId, courseName); return studentService.updateStudentCourse(studentId,
	 * courseName); }
	 */

}
