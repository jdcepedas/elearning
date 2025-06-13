package com.elearning.dao;

import com.elearning.model.CourseEngagement;
import java.time.LocalDate;
import java.util.List;

public interface CourseEngagementDao extends GenericDao<CourseEngagement, Long> {
    List<CourseEngagement> findByStudentId(Long studentId);
    List<CourseEngagement> findByCourseId(String courseId);
    List<CourseEngagement> findByEnrollmentDateBetween(LocalDate startDate, LocalDate endDate);
    List<CourseEngagement> findByEngagementType(String type);
    List<CourseEngagement> findByLastActivityDateBefore(LocalDate date);
} 