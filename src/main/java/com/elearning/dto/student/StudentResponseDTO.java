package com.elearning.dto.student;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class StudentResponseDTO {
    private Long id;
    private String countryCode;
    private Integer yearEnrolled;
    private Integer ageEnrolled;
    private String gender;
    private Integer currentAge;
    private Integer yearsSinceEnrolled;
    private List<CourseProgressDTO> enrolledCourses;
}
