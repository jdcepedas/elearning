package com.elearning.service;

import com.elearning.model.Course;
import com.elearning.model.Student;
import java.util.List;
import java.util.Optional;

public interface CourseService {
    Course saveCourse(Course course);
    Optional<Course> findById(String id);
    List<Course> findAll();
    void deleteCourse(String id);
    
    // Custom business operations
    Optional<Course> findByTitle(String title);
    List<Course> findByLectureCountGreaterThan(Integer count);
    List<Course> findByLectureCountLessThan(Integer count);
    
    // Student engagement operations
    List<Student> getEnrolledStudents(String courseId);
    double getAverageCourseProgress(String courseId);
    int getActiveStudentCount(String courseId);
} 