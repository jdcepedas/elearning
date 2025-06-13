package com.elearning.service.impl;

import com.elearning.dao.CourseDao;
import com.elearning.dao.CourseEngagementDao;
import com.elearning.exception.ResourceNotFoundException;
import com.elearning.model.Course;
import com.elearning.model.Student;
import com.elearning.model.CourseEngagement;
import com.elearning.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseDao courseDao;

    @Autowired
    private CourseEngagementDao engagementDao;

    @Override
    public Course saveCourse(Course course) {
        return courseDao.save(course);
    }

    @Override
    public Optional<Course> findById(String id) {
        return courseDao.findById(id);
    }

    @Override
    public List<Course> findAll() {
        return courseDao.findAll();
    }

    @Override
    public void deleteCourse(String id) {
        courseDao.deleteById(id);
    }

    @Override
    public Optional<Course> findByTitle(String title) {
        return courseDao.findByTitle(title);
    }

    @Override
    public List<Course> findByLectureCountGreaterThan(Integer count) {
        return courseDao.findByLectureCountGreaterThan(count);
    }

    @Override
    public List<Course> findByLectureCountLessThan(Integer count) {
        return courseDao.findByLectureCountLessThan(count);
    }

    @Override
    public List<Student> getEnrolledStudents(String courseId) {
        return engagementDao.findByCourseId(courseId).stream()
                .map(CourseEngagement::getStudent)
                .collect(Collectors.toList());
    }

    @Override
    public double getAverageCourseProgress(String courseId) {
        List<CourseEngagement> engagements = engagementDao.findByCourseId(courseId);
        if (engagements.isEmpty()) {
            return 0.0;
        }
        
        return engagements.stream()
                .mapToDouble(CourseEngagement::getPercentComplete)
                .average()
                .orElse(0.0);
    }

    @Override
    public int getActiveStudentCount(String courseId) {
        LocalDate thirtyDaysAgo = LocalDate.now().minusDays(30);
        return (int) engagementDao.findByCourseId(courseId).stream()
                .filter(engagement -> engagement.getLastActivityDate().isAfter(thirtyDaysAgo))
                .count();
    }
} 