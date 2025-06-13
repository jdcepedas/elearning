package com.elearning.dto.student;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CourseProgressDTO {
    private String courseId;
    private String courseTitle;
    private double progress;
    private String lastActivityDate;
    private String engagementType;
}