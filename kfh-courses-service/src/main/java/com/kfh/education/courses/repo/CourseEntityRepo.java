package com.kfh.education.courses.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.kfh.education.courses.entity.CourseEntity;

@Repository
public interface CourseEntityRepo extends CrudRepository<CourseEntity, Long> {
	CourseEntity findByName(String name);
}
