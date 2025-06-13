package com.elearning.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "students")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "country_code")
    private String countryCode;

    @Column(name = "year_enrolled")
    private Integer yearEnrolled;

    @Column(name = "age_enrolled")
    private Integer ageEnrolled;

    @Column
    private String gender;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CourseEngagement> engagements;

    public void addCourse(Course newCourse, LocalDate enrollDate) {
        CourseEngagement engagement = new CourseEngagement(newCourse, enrollDate, "Enrollment");
        engagement.setStudent(this);
        if(engagements == null) {
            engagements = new ArrayList<>();
        }
        engagements.add(engagement);
    }

    public int getYearSinceEnrolled() {
        int currentYear = LocalDate.now().getYear();
        return currentYear - yearEnrolled;
    }

    public int getAge() {
        return ageEnrolled + getYearSinceEnrolled();
    }
}
