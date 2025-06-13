package com.elearning.dto.course;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StudentEnrollmentDTO {
    private Long studentId;
    private String countryCode;
    private double progress;
    private String lastActivityDate;
    private String engagementType;
}