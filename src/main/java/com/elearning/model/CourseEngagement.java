package com.elearning.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.Period;

@Entity
@Table(name = "course_engagement")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CourseEngagement {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @Column(name = "enrollment_date", nullable = false)
    private LocalDate enrollmentDate;

    @Column(name = "engagement_type")
    private String engagementType;

    @Column(name = "last_lecture")
    private Integer lastLecture;

    @Column(name = "last_activity_date")
    private LocalDate lastActivityDate;


    public CourseEngagement(Course course, LocalDate enrollmentDate,
                            String engagementType) {
        this.course = course;
        this.enrollmentDate = enrollmentDate;
        this.engagementType = engagementType;
        this.lastActivityDate = enrollmentDate;
        this.lastLecture = 0;
    }

    public String getCourseId() {
        return course.getId();
    }

    public int getEnrollmentYear() {
        return enrollmentDate.getYear();
    }

    public int getLastActivityYear() {
        return lastActivityDate.getYear();
    }

    public String getLastActivityMonth() {
        return "%tb".formatted(lastActivityDate);
    }

    public double getPercentComplete() {
        return lastLecture * 100.0 / course.getLectureCount();
    }

    public int getMonthsSinceActive() {
        LocalDate now = LocalDate.now();
        long months = Period.between(lastActivityDate, now).toTotalMonths();
        return (int) months;
    }

    public void watchLecture(int lectureNumber, LocalDate currentDate) {
        lastLecture = Math.max(lectureNumber, lastLecture);
        lastActivityDate = currentDate;
        engagementType = "Lecture " + lastLecture;
    }

    @Override
    public String toString() {
        return "%s: %s %d %s [%d]".formatted(
                course.getId(),
                getLastActivityMonth(),
                getLastActivityYear(),
                engagementType,
                getMonthsSinceActive()
        );
    }
 }
