package com.elearning.dto.course;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class CourseResponseDTO {
    private String id;
    private String title;
    private Integer lectureCount;
    private double averageProgress;
    private int activeStudentCount;
    private List<StudentEnrollmentDTO> enrolledStudents;
}
