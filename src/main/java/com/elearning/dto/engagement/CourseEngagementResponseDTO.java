package com.elearning.dto.engagement;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;

@Data
@Builder
public class CourseEngagementResponseDTO {
    private Long id;
    private Long studentId;
    private String studentCountryCode;
    private String courseId;
    private String courseTitle;
    private LocalDate enrollmentDate;
    private String engagementType;
    private Integer lastLecture;
    private LocalDate lastActivityDate;
    private double percentComplete;
    private int monthsSinceActive;
    private double engagementScore;
    private boolean isActive;
} 