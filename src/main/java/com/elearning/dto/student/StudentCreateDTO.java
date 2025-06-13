package com.elearning.dto.student;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StudentCreateDTO {
    private String countryCode;
    private Integer yearEnrolled;
    private Integer ageEnrolled;
    private String gender;
} 