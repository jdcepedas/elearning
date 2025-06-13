package com.elearning.dao;

import com.elearning.model.Student;
import java.util.List;

public interface StudentDao extends GenericDao<Student, Long> {
    List<Student> findByCountryCode(String countryCode);
    List<Student> findByYearEnrolled(Integer year);
    List<Student> findByGender(String gender);
} 