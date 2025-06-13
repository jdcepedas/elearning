package com.elearning.dao;

import com.elearning.model.Course;
import java.util.List;
import java.util.Optional;

public interface CourseDao extends GenericDao<Course, String> {
    Optional<Course> findByTitle(String title);
    List<Course> findByLectureCountGreaterThan(Integer count);
    List<Course> findByLectureCountLessThan(Integer count);
} 