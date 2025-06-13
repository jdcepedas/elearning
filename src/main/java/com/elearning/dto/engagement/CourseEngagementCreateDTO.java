package com.elearning.dto.engagement;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;

@Data
@Builder
public class CourseEngagementCreateDTO {
    private Long studentId;
    private String courseId;
    private LocalDate enrollmentDate;
    private String engagementType;
} 