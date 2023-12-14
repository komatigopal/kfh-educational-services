package com.kfh.education.courses.service.impl;

import java.util.Map;

import javax.security.auth.login.AccountNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.kfh.education.courses.dto.AdminDto;
import com.kfh.education.courses.dto.CourseDto;
import com.kfh.education.courses.service.AdminService;
import com.kfh.education.courses.service.CourseService;

@Service
public class AdminServiceImpl implements AdminService {
	private final Logger log = LoggerFactory.getLogger(AdminServiceImpl.class);

	@Autowired
	JwtGeneratorImpl jwtGeneratorImpl;

	@Autowired
	private CourseService courseService;

	@Override
	public ResponseEntity<?> createAuthenticationToken(AdminDto adminDto) {
		log.info("in createAuthenticationToken, adminDto - {}", adminDto);
		try {
			if (adminDto.getUserName() == null || adminDto.getPassword() == null) {
				throw new AccountNotFoundException("UserName or Password is Empty");
			}
			adminDto = this.getUserByNameAndPassword(adminDto);
			if (adminDto == null) {
				throw new AccountNotFoundException("UserName or Password is Invalid");
			}
			return new ResponseEntity<>(jwtGeneratorImpl.generateToken(adminDto.getUserName()), HttpStatus.OK);
		} catch (AccountNotFoundException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
		}
	}

	@Override
	public Map<String, Object> addCourse(String courseName, String authHeader) {
		return courseService.addCourse(courseName, authHeader);
	}

	@Override
	public Map<String, Object> updateCourse(CourseDto courseDto, String authHeader) {
		return courseService.updateCourse(courseDto, authHeader);
	}

	@Override
	public Map<String, Object> deleteCourse(Long courseId, String authHeader) {
		return courseService.deleteCourse(courseId, authHeader);
	}

	private AdminDto getUserByNameAndPassword(AdminDto adminDto) {
		if (adminDto.getUserName().equals("admin")) {
			if (!adminDto.getPassword().equals("admin")) {
				return null;
			}
		}
		return adminDto;
	}
}