package com.elearning.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

@Entity
@Table(name = "courses")
@Data
@Builder
public class Course {

    @Id
    @Column(name = "course_id", nullable = false, unique = true)
    private String id;

    @Column(nullable = false)
    private String title;

    @Column(name = "lecture_count")
    private Integer lectureCount;

    public Course() {
    }

    public Course(String id, String title, Integer lectureCount) {
        this.id = id;
        this.title = title;
        this.lectureCount = lectureCount;
    }
}
