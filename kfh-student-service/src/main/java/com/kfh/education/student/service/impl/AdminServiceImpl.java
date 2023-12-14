package com.kfh.education.student.service.impl;

import java.util.Map;

import javax.security.auth.login.AccountNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.kfh.education.student.dto.AdminDto;
import com.kfh.education.student.service.AdminService;
import com.kfh.education.student.service.StudentService;

@Service
public class AdminServiceImpl implements AdminService {
	private final Logger log = LoggerFactory.getLogger(AdminServiceImpl.class);

	@Autowired
	JwtGeneratorImpl jwtGeneratorImpl;

	@Autowired
	private StudentService studentService;

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
	public Map<String, Object> updateStudentCourse(Long studentId, String courseName, String authHeader) {
		log.info("in updateStudentCourse, studentId - {}, courseName - {}", studentId, courseName);
		return studentService.updateStudentCourse(studentId, courseName, authHeader);
	}

	@Override
	public Map<String, Object> deleteStudent(Long studentId, String authHeader) {
		log.info("in deleteStudent, studentId - {}", studentId);
		return studentService.deleteStudent(studentId, authHeader);
	}

	@Override
	public Map<String, Object> getStudents(String authHeader) {
		log.info("in getStudents.");
		return studentService.getStudents(authHeader);
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