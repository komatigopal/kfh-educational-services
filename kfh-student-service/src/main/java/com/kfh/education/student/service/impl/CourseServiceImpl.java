package com.kfh.education.student.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.kfh.education.student.dto.DependencyException;
import com.kfh.education.student.entity.CourseEntity;
import com.kfh.education.student.service.CourseService;

@Service
public class CourseServiceImpl implements CourseService {
	private final Logger log = LoggerFactory.getLogger(CourseServiceImpl.class);
	@Autowired
	private RestTemplate restTemplate;

	@Value("${courses.applicatoion.getCourses.url}")
	private String url;

	@Autowired
	JwtGeneratorImpl jwtGeneratorImpl;

	@Override
	public Map<String, Object> getCourses(String authHeader) {
		Map<String, Object> map = new HashMap<String, Object>();
		log.info("in getCourses method.");
		try {
			// List<CourseEntity> courses = restTemplate.getForObject(url, List.class);
			List<CourseEntity> courses = this.getCourseList(authHeader);
			Map<String, String> jwtTokenGen = jwtGeneratorImpl
					.generateToken(jwtGeneratorImpl.getUserNameFromToken(authHeader));
			if (Objects.nonNull(jwtTokenGen)) {
				String token = jwtTokenGen.get("token");
				map.put("token", token);
			}
			map.put("courses", courses);
			return map;
		} catch (HttpClientErrorException e) {
			log.error("e.getResponseBodyAsString() - {}", e.getResponseBodyAsString());
			throw new DependencyException(e.getResponseBodyAsString());
		} catch (Exception e) {
			log.error("e - {}", e.getMessage());
			throw new DependencyException(e.getMessage().toString());
		}
	}

	private List<CourseEntity> getCourseList(String authHeader) {
		List<CourseEntity> courses = new ArrayList<>();
		log.info("in findCourseByName method, authHeader - {}, url - {}", authHeader, url);
		try {
			HttpHeaders headers = createHttpHeaders(authHeader);
			HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
			ResponseEntity<HashMap> response = restTemplate.exchange(url, HttpMethod.GET, entity, HashMap.class);
			log.info("response - {}", response);
			HashMap hashMap = response.getBody();
			for (Object key : hashMap.keySet()) {
				log.info("key - {}, s.getBody().get(key) - {}", key, hashMap.get(key));
				if (key.equals("courses")) {
					courses = (List<CourseEntity>) hashMap.get(key);
					break;
				}
			}
			log.info("courses - {}", courses);
			return courses;
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

}