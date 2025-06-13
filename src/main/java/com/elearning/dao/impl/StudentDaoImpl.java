package com.elearning.dao.impl;

import com.elearning.dao.StudentDao;
import com.elearning.model.Student;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StudentDaoImpl extends GenericDaoImpl<Student, Long> implements StudentDao {

    public StudentDaoImpl() {
        super(Student.class);
    }

    @Override
    public List<Student> findByCountryCode(String countryCode) {
        return getEntityManager()
                .createQuery("SELECT s FROM students s WHERE s.countryCode = :countryCode", Student.class)
                .setParameter("countryCode", countryCode)
                .getResultList();
    }

    @Override
    public List<Student> findByYearEnrolled(Integer year) {
        return getEntityManager()
                .createQuery("SELECT s FROM students s WHERE s.yearEnrolled = :year", Student.class)
                .setParameter("year", year)
                .getResultList();
    }

    @Override
    public List<Student> findByGender(String gender) {
        return getEntityManager()
                .createQuery("SELECT s FROM students s WHERE s.gender = :gender", Student.class)
                .setParameter("gender", gender)
                .getResultList();
    }
} 