package com.elearning.mapper;

import com.elearning.dto.student.StudentCreateDTO;
import com.elearning.dto.student.StudentResponseDTO;
import com.elearning.dto.student.CourseProgressDTO;
import com.elearning.model.Student;
import com.elearning.model.CourseEngagement;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class StudentMapper {

    public Student toEntity(StudentCreateDTO dto) {
        return Student.builder()
                .countryCode(dto.getCountryCode())
                .yearEnrolled(dto.getYearEnrolled())
                .ageEnrolled(dto.getAgeEnrolled())
                .gender(dto.getGender())
                .build();
    }

    public StudentResponseDTO toResponseDTO(Student student, List<CourseEngagement> engagements) {
        return StudentResponseDTO.builder()
                .id(student.getId())
                .countryCode(student.getCountryCode())
                .yearEnrolled(student.getYearEnrolled())
                .ageEnrolled(student.getAgeEnrolled())
                .gender(student.getGender())
                .currentAge(student.getAge())
                .yearsSinceEnrolled(student.getYearSinceEnrolled())
                .enrolledCourses(mapEngagementsToProgress(engagements))
                .build();
    }

    private List<CourseProgressDTO> mapEngagementsToProgress(List<CourseEngagement> engagements) {
        return engagements.stream()
                .map(engagement -> CourseProgressDTO.builder()
                        .courseId(engagement.getCourse().getId())
                        .courseTitle(engagement.getCourse().getTitle())
                        .progress(engagement.getPercentComplete())
                        .lastActivityDate(engagement.getLastActivityDate().toString())
                        .engagementType(engagement.getEngagementType())
                        .build())
                .collect(Collectors.toList());
    }
} 