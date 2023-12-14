package com.kfh.education.student.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.kfh.education.student.dto.DependencyException;
import com.kfh.education.student.dto.StudentDto;
import com.kfh.education.student.entity.CourseEntity;
import com.kfh.education.student.entity.StudentEntity;
import com.kfh.education.student.repo.StudentEntityRepo;
import com.kfh.education.student.service.StudentService;

@Service
public class StudentServiceImpl implements StudentService {
	private final Logger log = LoggerFactory.getLogger(StudentServiceImpl.class);
	@Autowired
	private StudentEntityRepo studentEntityRepo;

	@Autowired
	JwtGeneratorImpl jwtGeneratorImpl;

	@Autowired
	private RestTemplate restTemplate;

	@Value("${courses.applicatoion.getCourseByName.url}")
	private String url;

	@Override
	public Map<String, Object> saveStudent(StudentDto studentDto) {
		Map<String, Object> map = new HashMap<String, Object>();
		log.info("in saveStudent method, studentDto - {}", studentDto);
		StudentEntity studentEntity = new StudentEntity(null, studentDto.getName(), studentDto.getEmail(),
				studentDto.getTelephoneNumber(), studentDto.getAddress(), new Date());
		studentEntity = studentEntityRepo.save(studentEntity);
		Map<String, String> jwtTokenGen = jwtGeneratorImpl.generateToken(studentEntity.getStudentId().toString());
		if (Objects.nonNull(jwtTokenGen)) {
			String token = jwtTokenGen.get("token");
			map.put("token", token);
		}
		map.put("student", studentEntity);
		return map;
	}

	@Override
	public Map<String, Object> getStudents(String authHeader) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<StudentEntity> students = (List<StudentEntity>) studentEntityRepo.findAll();
		Map<String, String> jwtTokenGen = jwtGeneratorImpl
				.generateToken(jwtGeneratorImpl.getUserNameFromToken(authHeader));
		if (Objects.nonNull(jwtTokenGen)) {
			String token = jwtTokenGen.get("token");
			map.put("token", token);
		}
		map.put("students", students);
		return map;
	}

	@Override
	public Map<String, Object> deleteStudent(Long studentId, String authHeader) {
		Map<String, Object> map = new HashMap<String, Object>();
		StudentEntity studentEntity = this.getStudent(studentId);
		studentEntityRepo.delete(studentEntity);
		Map<String, String> jwtTokenGen = jwtGeneratorImpl
				.generateToken(jwtGeneratorImpl.getUserNameFromToken(authHeader));
		if (Objects.nonNull(jwtTokenGen)) {
			String token = jwtTokenGen.get("token");
			map.put("token", token);
		}
		return map;
	}

	@Override
	public Map<String, Object> updateStudentCourse(Long studentId, String courseName, String authHeader) {
		Map<String, Object> map = new HashMap<String, Object>();
		StudentEntity studentEntity = this.getStudent(studentId);
		CourseEntity courseEntity = this.findCourseByName(courseName, authHeader);
		log.info("studentEntity - {}, courseEntity - {}", studentEntity, courseEntity);
		if (studentEntity.getCourse() != null
				&& studentEntity.getCourse().getName().equalsIgnoreCase(courseEntity.getName())) {
			throw new DependencyException("Updated course can not be same as previous course for Student with id - "
					+ studentId + ", Student details - " + studentEntity);
		}
		studentEntity.setCourse(courseEntity);
		studentEntity = studentEntityRepo.save(studentEntity);
		Map<String, String> jwtTokenGen = jwtGeneratorImpl
				.generateToken(jwtGeneratorImpl.getUserNameFromToken(authHeader));
		if (Objects.nonNull(jwtTokenGen)) {
			String token = jwtTokenGen.get("token");
			map.put("token", token);
		}
		map.put("student", studentEntity);
		return map;
	}

	private CourseEntity findCourseByName(String courseName, String authHeader) {
		CourseEntity course = new CourseEntity();
		log.info("in findCourseByName method, courseName - {}, url - {}", courseName, url);
		try {
			HttpHeaders headers = createHttpHeaders(authHeader);
			HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
			ResponseEntity<HashMap> response = restTemplate.exchange(url, HttpMethod.GET, entity,
					new ParameterizedTypeReference<HashMap>() {
					}, courseName);
			log.info("response - {}", response);
			HashMap hashMap = response.getBody();
			LinkedHashMap<Object, Object> linkedHashMap = new LinkedHashMap<>();
			for (Object key : hashMap.keySet()) {
				log.info("key - {}, hashMap.get(key) - {}", key, hashMap.get(key));
				if (key.equals("course")) {
					linkedHashMap = (LinkedHashMap<Object, Object>) hashMap.get(key);
					break;
				}
			}
			for (Object key : linkedHashMap.keySet()) {
				log.info("key - {}, linkedHashMap.get(key) - {}", key, linkedHashMap.get(key));
				if (key.equals("courseCode")) {
					course.setCourseCode(new Long((Integer) linkedHashMap.get(key)));
				} else if (key.equals("name")) {
					course.setName((String) linkedHashMap.get(key));
				} else if (key.equals("creationDate")) {
					String date = (String) linkedHashMap.get(key);
					log.info("date - {}", date);
					date = date.split("T")[0];
					log.info("date - {}", date);
					course.setCreationDate(new SimpleDateFormat("yyyy-MM-dd").parse(date));
				}
			}
			log.info("course - {}", course);
			return course;
		} catch (HttpClientErrorException e) {
			log.error("e.getResponseBodyAsString() - {}", e.getResponseBodyAsString());
			throw new DependencyException(e.getResponseBodyAsString());
		} catch (Exception e) {
			log.error("e - {}", e.getMessage());
			throw new DependencyException(e.getMessage().toString());
		}
	}

	private HttpHeaders createHttpHeaders(String authHeader) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		if (!authHeader.contains("Bearer")) {
			authHeader = "Bearer " + authHeader;
		}
		headers.add("Authorization", authHeader);
		return headers;
	}

	private StudentEntity getStudent(Long studentId) {
		log.info("in getstudent method, studentId - {}", studentId);
		try {
			StudentEntity studentEntity = studentEntityRepo.findById(studentId).get();
			if (Objects.nonNull(studentEntity)) {
				return studentEntity;
			} else {
				throw new EntityNotFoundException("Student with id - " + studentId + " not found");
			}
		} catch (Exception e) {
			throw new EntityNotFoundException("Student with id - " + studentId + " not found");
		}
	}
}