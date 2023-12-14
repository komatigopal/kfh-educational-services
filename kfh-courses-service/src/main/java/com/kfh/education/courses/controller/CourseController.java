package com.kfh.education.courses.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kfh.education.courses.dto.CourseDto;
import com.kfh.education.courses.entity.CourseEntity;
import com.kfh.education.courses.service.CourseService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

@RestController
@RequestMapping("/course")
public class CourseController {
	private final Logger log = LoggerFactory.getLogger(CourseController.class);
	@Autowired
	private CourseService courseService;

	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, dataType = "string", paramType = "header", example = "Bearer ") })
	@GetMapping("/getAll")
	public Map<String, Object> getCourses(HttpServletRequest request) {
		log.info("in getCourses method.");
		final String authHeader = request.getHeader("Authorization");
		return courseService.getCourses(authHeader);
	}

	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, dataType = "string", paramType = "header", example = "Bearer ") })
	@GetMapping("/{courseName}")
	public Map<String, Object> getCourseByName(@PathVariable String courseName, HttpServletRequest request) {
		log.info("in getCourseByName method, courseName - {}", courseName);
		final String authHeader = request.getHeader("Authorization");
		return courseService.getCourseByName(courseName, authHeader);
	}
}
