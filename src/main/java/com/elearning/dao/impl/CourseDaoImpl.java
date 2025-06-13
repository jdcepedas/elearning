package com.elearning.dao.impl;

import com.elearning.dao.CourseDao;
import com.elearning.model.Course;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CourseDaoImpl extends GenericDaoImpl<Course, String> implements CourseDao {

    public CourseDaoImpl() {
        super(Course.class);
    }

    @Override
    public Optional<Course> findByTitle(String title) {
        return getEntityManager()
                .createQuery("SELECT c FROM courses c WHERE c.title = :title", Course.class)
                .setParameter("title", title)
                .getResultStream()
                .findFirst();
    }

    @Override
    public List<Course> findByLectureCountGreaterThan(Integer count) {
        return getEntityManager()
                .createQuery("SELECT c FROM courses c WHERE c.lectureCount > :count", Course.class)
                .setParameter("count", count)
                .getResultList();
    }

    @Override
    public List<Course> findByLectureCountLessThan(Integer count) {
        return getEntityManager()
                .createQuery("SELECT c FROM courses c WHERE c.lectureCount < :count", Course.class)
                .setParameter("count", count)
                .getResultList();
    }
} 