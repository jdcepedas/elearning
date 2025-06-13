package com.elearning.service;

import com.elearning.model.Student;
import com.elearning.model.Course;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface StudentService {
    Student saveStudent(Student student);
    Optional<Student> findById(Long id);
    List<Student> findAll();
    void deleteStudent(Long id);
    
    // Custom business operations
    List<Student> findByCountryCode(String countryCode);
    List<Student> findByYearEnrolled(Integer year);
    List<Student> findByGender(String gender);
    
    // Course enrollment operations
    Student enrollInCourse(Long studentId, String courseId, LocalDate enrollmentDate);
    List<Course> getEnrolledCourses(Long studentId);
    double getCourseProgress(Long studentId, String courseId);
} 