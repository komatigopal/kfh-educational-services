package com.kfh.education.courses.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kfh.education.courses.dto.AdminDto;
import com.kfh.education.courses.dto.CourseDto;
import com.kfh.education.courses.entity.CourseEntity;
import com.kfh.education.courses.service.AdminService;

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
	@PostMapping("/after_auth/course")
	public Map<String, Object> addCourse(@RequestParam String courseName, HttpServletRequest request) {
		log.info("in addCourse method, courseName - {}", courseName);
		final String authHeader = request.getHeader("Authorization");
		return adminService.addCourse(courseName, authHeader);
	}

	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, dataType = "string", paramType = "header", example = "Bearer ") })
	@PutMapping("/after_auth/course")
	public Map<String, Object> updateCourse(@RequestBody CourseDto courseDto, HttpServletRequest request) {
		log.info("in updateCourse method, courseDto - {}", courseDto);
		final String authHeader = request.getHeader("Authorization");
		return adminService.updateCourse(courseDto, authHeader);
	}

	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, dataType = "string", paramType = "header", example = "Bearer ") })
	@DeleteMapping("/after_auth/course/{courseId}")
	public Map<String, Object> deleteCourse(@PathVariable Long courseId, HttpServletRequest request) {
		log.info("in deleteCourse method, courseId - {}", courseId);
		final String authHeader = request.getHeader("Authorization");
		return adminService.deleteCourse(courseId, authHeader);
	}

}
