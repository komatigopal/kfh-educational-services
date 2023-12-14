package com.kfh.education.courses.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kfh.education.courses.dto.CourseDto;
import com.kfh.education.courses.dto.DependencyException;
import com.kfh.education.courses.entity.CourseEntity;
import com.kfh.education.courses.repo.CourseEntityRepo;
import com.kfh.education.courses.service.CourseService;

@Service
public class CourseServiceImpl implements CourseService {
	private final Logger log = LoggerFactory.getLogger(CourseServiceImpl.class);
	@Autowired
	private CourseEntityRepo courseEntityRepo;
	@Autowired
	JwtGeneratorImpl jwtGeneratorImpl;

	@Override
	public Map<String, Object> addCourse(String courseName, String authHeader) {
		Map<String, Object> map = new HashMap<String, Object>();
		log.info("in addCourse method, courseName - {}", courseName);
		CourseEntity courseEntity = courseEntityRepo.findByName(courseName);
		if (Objects.nonNull(courseEntity)) {
			throw new DependencyException("Course is already exist in courses. Course datails - " + courseEntity);
		}
		courseEntity = new CourseEntity(null, courseName, new Date());
		courseEntity = courseEntityRepo.save(courseEntity);
		Map<String, String> jwtTokenGen = jwtGeneratorImpl
				.generateToken(jwtGeneratorImpl.getUserNameFromToken(authHeader));
		if (Objects.nonNull(jwtTokenGen)) {
			String token = jwtTokenGen.get("token");
			map.put("token", token);
		}
		map.put("course", courseEntity);
		return map;
	}

	@Override
	public Map<String, Object> getCourses(String authHeader) {
		log.info("in getCourses method.");
		Map<String, Object> map = new HashMap<String, Object>();
		List<CourseEntity> courses = (List<CourseEntity>) courseEntityRepo.findAll();
		Map<String, String> jwtTokenGen = jwtGeneratorImpl
				.generateToken(jwtGeneratorImpl.getUserNameFromToken(authHeader));
		if (Objects.nonNull(jwtTokenGen)) {
			String token = jwtTokenGen.get("token");
			map.put("token", token);
		}
		map.put("courses", courses);
		return map;
	}

	@Override
	public Map<String, Object> updateCourse(CourseDto courseDto, String authHeader) {
		Map<String, Object> map = new HashMap<String, Object>();
		log.info("in updateCourse method, courseDto - {}", courseDto);
		CourseEntity courseEntity = this.getCourse(courseDto.getCourseCode());
		if (Objects.nonNull(courseDto.getName()) && !courseDto.getName().equals("")
				&& !courseDto.getName().equals(courseEntity.getName())) {
			courseEntity.setName(courseDto.getName());
			courseEntity = courseEntityRepo.save(courseEntity);
			Map<String, String> jwtTokenGen = jwtGeneratorImpl
					.generateToken(jwtGeneratorImpl.getUserNameFromToken(authHeader));
			if (Objects.nonNull(jwtTokenGen)) {
				String token = jwtTokenGen.get("token");
				map.put("token", token);
			}
			map.put("course", courseEntity);
			return map;
		} else {
			throw new DependencyException("Updated course name can not be null or empty or same as previous name.");
		}
	}

	@Override
	public Map<String, Object> deleteCourse(Long courseId, String authHeader) {
		Map<String, Object> map = new HashMap<String, Object>();
		log.info("in deleteCourse method, courseId - {}", courseId);
		CourseEntity courseEntity = this.getCourse(courseId);
		courseEntityRepo.delete(courseEntity);
		Map<String, String> jwtTokenGen = jwtGeneratorImpl
				.generateToken(jwtGeneratorImpl.getUserNameFromToken(authHeader));
		if (Objects.nonNull(jwtTokenGen)) {
			String token = jwtTokenGen.get("token");
			map.put("token", token);
		}
		return map;
	}

	@Override
	public Map<String, Object> getCourseByName(String courseName, String authHeader) {
		Map<String, Object> map = new HashMap<String, Object>();
		log.info("in getCourseByName method, courseName - {}", courseName);
		try {
			CourseEntity courseEntity = courseEntityRepo.findByName(courseName);
			if (Objects.nonNull(courseEntity)) {
				Map<String, String> jwtTokenGen = jwtGeneratorImpl
						.generateToken(jwtGeneratorImpl.getUserNameFromToken(authHeader));
				if (Objects.nonNull(jwtTokenGen)) {
					String token = jwtTokenGen.get("token");
					map.put("token", token);
				}
				map.put("course", courseEntity);
				return map;
			} else {
				throw new EntityNotFoundException("Course with name - " + courseName + " not found");
			}
		} catch (Exception e) {
			throw new EntityNotFoundException("Course with name - " + courseName + " not found");
		}
	}

	private CourseEntity getCourse(Long courseId) {
		log.info("in getCourse method, courseId - {}", courseId);
		try {
			CourseEntity courseEntity = courseEntityRepo.findById(courseId).get();
			if (Objects.nonNull(courseEntity)) {
				return courseEntity;
			} else {
				throw new EntityNotFoundException("Course with id - " + courseId + " not found");
			}
		} catch (Exception e) {
			throw new EntityNotFoundException("Course with id - " + courseId + " not found");
		}
	}
}