package com.elearning.dto.course;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CourseCreateDTO {
    private String id;
    private String title;
    private Integer lectureCount;
} 