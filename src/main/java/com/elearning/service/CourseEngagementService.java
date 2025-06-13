package com.elearning.service;

import com.elearning.dto.engagement.CourseEngagementCreateDTO;
import com.elearning.model.CourseEngagement;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CourseEngagementService {
    CourseEngagement saveEngagement(CourseEngagementCreateDTO engagement);
    Optional<CourseEngagement> findById(Long id);
    List<CourseEngagement> findAll();
    void deleteEngagement(Long id);
    
    // Custom business operations
    List<CourseEngagement> findByStudentId(Long studentId);
    List<CourseEngagement> findByCourseId(String courseId);
    List<CourseEngagement> findByEnrollmentDateBetween(LocalDate startDate, LocalDate endDate);
    List<CourseEngagement> findByEngagementType(String type);
    List<CourseEngagement> findByLastActivityDateBefore(LocalDate date);
    
    // Engagement tracking operations
    CourseEngagement recordLectureProgress(Long engagementId, int lectureNumber, LocalDate currentDate);
    double calculateEngagementScore(Long engagementId);
    boolean isEngagementActive(Long engagementId);
} 