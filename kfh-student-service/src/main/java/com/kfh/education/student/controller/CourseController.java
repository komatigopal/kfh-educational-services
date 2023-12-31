package com.kfh.education.student.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kfh.education.student.service.CourseService;

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
	@GetMapping
	public Map<String, Object> getCourses(HttpServletRequest request) {
		log.info("in getCourses method.");
		final String authHeader = request.getHeader("Authorization");
		return courseService.getCourses(authHeader);
	}
}