package com.elearning.mapper;

import com.elearning.dto.course.CourseCreateDTO;
import com.elearning.dto.course.CourseResponseDTO;
import com.elearning.dto.course.StudentEnrollmentDTO;
import com.elearning.model.Course;
import com.elearning.model.CourseEngagement;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CourseMapper {

    public Course toEntity(CourseCreateDTO dto) {
        return Course.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .lectureCount(dto.getLectureCount())
                .build();
    }

    public CourseResponseDTO toResponseDTO(Course course, List<CourseEngagement> engagements, 
                                         double averageProgress, int activeStudentCount) {
        return CourseResponseDTO.builder()
                .id(course.getId())
                .title(course.getTitle())
                .lectureCount(course.getLectureCount())
                .averageProgress(averageProgress)
                .activeStudentCount(activeStudentCount)
                .enrolledStudents(mapEngagementsToEnrollments(engagements))
                .build();
    }

    private List<StudentEnrollmentDTO> mapEngagementsToEnrollments(List<CourseEngagement> engagements) {
        return engagements.stream()
                .map(engagement -> StudentEnrollmentDTO.builder()
                        .studentId(engagement.getStudent().getId())
                        .countryCode(engagement.getStudent().getCountryCode())
                        .progress(engagement.getPercentComplete())
                        .lastActivityDate(engagement.getLastActivityDate().toString())
                        .engagementType(engagement.getEngagementType())
                        .build())
                .collect(Collectors.toList());
    }
} 