package com.elearning.mapper;

import com.elearning.dto.engagement.CourseEngagementCreateDTO;
import com.elearning.dto.engagement.CourseEngagementResponseDTO;
import com.elearning.model.CourseEngagement;
import org.springframework.stereotype.Component;

@Component
public class CourseEngagementMapper {

    public CourseEngagement toEntity(CourseEngagementCreateDTO dto) {
        return CourseEngagement.builder()
                .enrollmentDate(dto.getEnrollmentDate())
                .engagementType(dto.getEngagementType())
                .lastActivityDate(dto.getEnrollmentDate())
                .lastLecture(0)
                .build();
    }

    public CourseEngagementResponseDTO toResponseDTO(CourseEngagement engagement, double engagementScore) {
        return CourseEngagementResponseDTO.builder()
                .id(engagement.getId())
                .studentId(engagement.getStudent().getId())
                .studentCountryCode(engagement.getStudent().getCountryCode())
                .courseId(engagement.getCourse().getId())
                .courseTitle(engagement.getCourse().getTitle())
                .enrollmentDate(engagement.getEnrollmentDate())
                .engagementType(engagement.getEngagementType())
                .lastLecture(engagement.getLastLecture())
                .lastActivityDate(engagement.getLastActivityDate())
                .percentComplete(engagement.getPercentComplete())
                .monthsSinceActive(engagement.getMonthsSinceActive())
                .engagementScore(engagementScore)
                .isActive(engagement.getLastActivityDate().isAfter(java.time.LocalDate.now().minusDays(30)))
                .build();
    }
} 