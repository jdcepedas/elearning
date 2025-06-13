package com.elearning.dao.impl;

import com.elearning.dao.CourseEngagementDao;
import com.elearning.model.CourseEngagement;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class CourseEngagementDaoImpl extends GenericDaoImpl<CourseEngagement, Long> implements CourseEngagementDao {

    public CourseEngagementDaoImpl() {
        super(CourseEngagement.class);
    }

    @Override
    public List<CourseEngagement> findByStudentId(Long studentId) {
        return getEntityManager()
                .createQuery("SELECT ce FROM CourseEngagement ce WHERE ce.student.id = :studentId", CourseEngagement.class)
                .setParameter("studentId", studentId)
                .getResultList();
    }

    @Override
    public List<CourseEngagement> findByCourseId(String courseId) {
        return getEntityManager()
                .createQuery("SELECT ce FROM CourseEngagement ce WHERE ce.course.id = :courseId", CourseEngagement.class)
                .setParameter("courseId", courseId)
                .getResultList();
    }

    @Override
    public List<CourseEngagement> findByEnrollmentDateBetween(LocalDate startDate, LocalDate endDate) {
        return getEntityManager()
                .createQuery("SELECT ce FROM CourseEngagement ce WHERE ce.enrollmentDate BETWEEN :startDate AND :endDate", CourseEngagement.class)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .getResultList();
    }

    @Override
    public List<CourseEngagement> findByEngagementType(String type) {
        return getEntityManager()
                .createQuery("SELECT ce FROM CourseEngagement ce WHERE ce.engagementType = :type", CourseEngagement.class)
                .setParameter("type", type)
                .getResultList();
    }

    @Override
    public List<CourseEngagement> findByLastActivityDateBefore(LocalDate date) {
        return getEntityManager()
                .createQuery("SELECT ce FROM CourseEngagement ce WHERE ce.lastActivityDate < :date", CourseEngagement.class)
                .setParameter("date", date)
                .getResultList();
    }
} 