package com.elearning.service.impl;

import com.elearning.dao.CourseDao;
import com.elearning.dao.CourseEngagementDao;
import com.elearning.dao.StudentDao;
import com.elearning.dto.engagement.CourseEngagementCreateDTO;
import com.elearning.exception.ResourceNotFoundException;
import com.elearning.mapper.CourseEngagementMapper;
import com.elearning.model.Course;
import com.elearning.model.CourseEngagement;
import com.elearning.model.Student;
import com.elearning.service.CourseEngagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CourseEngagementServiceImpl implements CourseEngagementService {

    @Autowired
    private CourseEngagementDao engagementDao;
    @Autowired
    private CourseEngagementMapper engagementMapper;
    @Autowired
    private CourseDao courseDao;
    @Autowired
    private StudentDao studentDao;

    @Override
    public CourseEngagement saveEngagement(CourseEngagementCreateDTO dto) {
        Course course = courseDao.findById(dto.getCourseId())
                .orElseThrow(() -> new ResourceNotFoundException("Could not find course with id " + dto.getCourseId()));
        Student student = studentDao.findById(dto.getStudentId())
                .orElseThrow(() -> new ResourceNotFoundException("Could not find student with id " + dto.getStudentId()));
        CourseEngagement engagement = CourseEngagement.builder()
                .student(student)
                .course(course)
                .enrollmentDate(dto.getEnrollmentDate())
                .engagementType(dto.getEngagementType())
                .lastActivityDate(dto.getEnrollmentDate())
                .lastLecture(0)
                .build();
        return engagementDao.save(engagement);
    }

    @Override
    public Optional<CourseEngagement> findById(Long id) {
        return engagementDao.findById(id);
    }

    @Override
    public List<CourseEngagement> findAll() {
        return engagementDao.findAll();
    }

    @Override
    public void deleteEngagement(Long id) {
        engagementDao.deleteById(id);
    }

    @Override
    public List<CourseEngagement> findByStudentId(Long studentId) {
        return engagementDao.findByStudentId(studentId);
    }

    @Override
    public List<CourseEngagement> findByCourseId(String courseId) {
        return engagementDao.findByCourseId(courseId);
    }

    @Override
    public List<CourseEngagement> findByEnrollmentDateBetween(LocalDate startDate, LocalDate endDate) {
        return engagementDao.findByEnrollmentDateBetween(startDate, endDate);
    }

    @Override
    public List<CourseEngagement> findByEngagementType(String type) {
        return engagementDao.findByEngagementType(type);
    }

    @Override
    public List<CourseEngagement> findByLastActivityDateBefore(LocalDate date) {
        return engagementDao.findByLastActivityDateBefore(date);
    }

    @Override
    public CourseEngagement recordLectureProgress(Long engagementId, int lectureNumber, LocalDate currentDate) {
        CourseEngagement engagement = engagementDao.findById(engagementId)
                .orElseThrow(() -> new ResourceNotFoundException("Engagement not found with id: " + engagementId));
        
        engagement.watchLecture(lectureNumber, currentDate);
        return engagementDao.save(engagement);
    }

    @Override
    public double calculateEngagementScore(Long engagementId) {
        CourseEngagement engagement = engagementDao.findById(engagementId)
                .orElseThrow(() -> new ResourceNotFoundException("Engagement not found with id: " + engagementId));
        
        // Calculate engagement score based on:
        // 1. Course completion percentage (50% weight)
        // 2. Recent activity (30% weight)
        // 3. Consistency (20% weight)
        
        double completionScore = engagement.getPercentComplete() * 0.5;
        
        int monthsSinceActive = engagement.getMonthsSinceActive();
        double activityScore = (monthsSinceActive <= 1) ? 30.0 :
                             (monthsSinceActive <= 3) ? 20.0 :
                             (monthsSinceActive <= 6) ? 10.0 : 0.0;
        
        // Consistency score based on enrollment duration
        int monthsEnrolled = (int) java.time.temporal.ChronoUnit.MONTHS.between(
                engagement.getEnrollmentDate(),
                LocalDate.now()
        );
        double consistencyScore = Math.min(20.0, monthsEnrolled * 2.0);
        
        return completionScore + activityScore + consistencyScore;
    }

    @Override
    public boolean isEngagementActive(Long engagementId) {
        CourseEngagement engagement = engagementDao.findById(engagementId)
                .orElseThrow(() -> new ResourceNotFoundException("Engagement not found with id: " + engagementId));
        
        // Consider an engagement active if there has been activity in the last 30 days
        return engagement.getLastActivityDate().isAfter(LocalDate.now().minusDays(30));
    }
} 