package com.kfh.education.student.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kfh.education.student.dto.AdminDto;
import com.kfh.education.student.service.AdminService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

@RestController
@RequestMapping("/admin")
public class AdminController {
	private final Logger log = LoggerFactory.getLogger(AdminController.class);

	@Autowired
	private AdminService adminService;

	@GetMapping("/getTkone")
	public ResponseEntity<?> createAuthenticationToken(@RequestParam String userName, String password) {
		// for login of admin username="admin", password="admin"
		AdminDto adminDto = new AdminDto(userName, password);
		return adminService.createAuthenticationToken(adminDto);
	}

	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, dataType = "string", paramType = "header", example = "Bearer ") })
	@GetMapping("/after_auth/student/getAll")
	public Map<String, Object> getStudents(HttpServletRequest request) {
		log.info("in getstudents method.");
		final String authHeader = request.getHeader("Authorization");
		return adminService.getStudents(authHeader);
	}

	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, dataType = "string", paramType = "header", example = "Bearer ") })
	@PutMapping("/after_auth/student/update/course/{studentId}")
	public Map<String, Object> updateStudentCourse(@PathVariable Long studentId, @RequestParam String courseName,
			HttpServletRequest request) {
		log.info("in updatestudent method, studentId - {}, courseName - {}", studentId, courseName);
		final String authHeader = request.getHeader("Authorization");
		return adminService.updateStudentCourse(studentId, courseName, authHeader);
	}

	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, dataType = "string", paramType = "header", example = "Bearer ") })
	@DeleteMapping("/after_auth/student/{studentId}")
	public Map<String, Object> deleteStudent(@PathVariable Long studentId, HttpServletRequest request) {
		log.info("in deletestudent method, studentId - {}", studentId);
		final String authHeader = request.getHeader("Authorization");
		return adminService.deleteStudent(studentId, authHeader);
	}
}
