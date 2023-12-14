package com.kfh.education.student.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.kfh.education.student.entity.StudentEntity;

@Repository
public interface StudentEntityRepo extends CrudRepository<StudentEntity, Long> {

}
