package com.elearning.service.impl;

import com.elearning.dao.StudentDao;
import com.elearning.dao.CourseDao;
import com.elearning.dao.CourseEngagementDao;
import com.elearning.exception.ResourceNotFoundException;
import com.elearning.model.Student;
import com.elearning.model.Course;
import com.elearning.model.CourseEngagement;
import com.elearning.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentDao studentDao;

    @Autowired
    private CourseDao courseDao;

    @Autowired
    private CourseEngagementDao engagementDao;

    @Override
    public Student saveStudent(Student student) {
        return studentDao.save(student);
    }

    @Override
    public Optional<Student> findById(Long id) {
        return studentDao.findById(id);
    }

    @Override
    public List<Student> findAll() {
        return studentDao.findAll();
    }

    @Override
    public void deleteStudent(Long id) {
        studentDao.deleteById(id);
    }

    @Override
    public List<Student> findByCountryCode(String countryCode) {
        return studentDao.findByCountryCode(countryCode);
    }

    @Override
    public List<Student> findByYearEnrolled(Integer year) {
        return studentDao.findByYearEnrolled(year);
    }

    @Override
    public List<Student> findByGender(String gender) {
        return studentDao.findByGender(gender);
    }

    @Override
    public Student enrollInCourse(Long studentId, String courseId, LocalDate enrollmentDate) {
        Student student = studentDao.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + studentId));
        
        Course course = courseDao.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + courseId));

        student.addCourse(course, enrollmentDate);
        return studentDao.save(student);
    }

    @Override
    public List<Course> getEnrolledCourses(Long studentId) {
        return engagementDao.findByStudentId(studentId).stream()
                .map(CourseEngagement::getCourse)
                .collect(Collectors.toList());
    }

    @Override
    public double getCourseProgress(Long studentId, String courseId) {
        return engagementDao.findByStudentId(studentId).stream()
                .filter(engagement -> engagement.getCourse().getId().equals(courseId))
                .findFirst()
                .map(CourseEngagement::getPercentComplete)
                .orElse(0.0);
    }
} 