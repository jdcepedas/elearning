package com.elearning.controller;

import com.elearning.dto.engagement.CourseEngagementCreateDTO;
import com.elearning.dto.engagement.CourseEngagementResponseDTO;
import com.elearning.exception.ResourceNotFoundException;
import com.elearning.model.CourseEngagement;
import com.elearning.service.CourseEngagementService;
import com.elearning.mapper.CourseEngagementMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/engagements")
public class CourseEngagementController {

    @Autowired
    private CourseEngagementService engagementService;

    @Autowired
    private CourseEngagementMapper engagementMapper;

    @PostMapping
    public ResponseEntity<CourseEngagementResponseDTO> createEngagement(
            @RequestBody CourseEngagementCreateDTO engagementDTO) {
        CourseEngagement engagement = engagementService.saveEngagement(engagementDTO);
        double engagementScore = engagementService.calculateEngagementScore(engagement.getId());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(engagementMapper.toResponseDTO(engagement, engagementScore));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseEngagementResponseDTO> getEngagement(@PathVariable Long id) {
        CourseEngagement engagement = engagementService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Engagement not found with id: " + id));
        double engagementScore = engagementService.calculateEngagementScore(id);
        return ResponseEntity.ok(engagementMapper.toResponseDTO(engagement, engagementScore));
    }

    @GetMapping
    public ResponseEntity<List<CourseEngagementResponseDTO>> getAllEngagements() {
        List<CourseEngagement> engagements = engagementService.findAll();
        List<CourseEngagementResponseDTO> responseDTOs = engagements.stream()
                .map(engagement -> {
                    double engagementScore = engagementService.calculateEngagementScore(engagement.getId());
                    return engagementMapper.toResponseDTO(engagement, engagementScore);
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseDTOs);
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<CourseEngagementResponseDTO>> getEngagementsByStudent(
            @PathVariable Long studentId) {
        List<CourseEngagement> engagements = engagementService.findByStudentId(studentId);
        List<CourseEngagementResponseDTO> responseDTOs = engagements.stream()
                .map(engagement -> {
                    double engagementScore = engagementService.calculateEngagementScore(engagement.getId());
                    return engagementMapper.toResponseDTO(engagement, engagementScore);
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseDTOs);
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<CourseEngagementResponseDTO>> getEngagementsByCourse(
            @PathVariable String courseId) {
        List<CourseEngagement> engagements = engagementService.findByCourseId(courseId);
        List<CourseEngagementResponseDTO> responseDTOs = engagements.stream()
                .map(engagement -> {
                    double engagementScore = engagementService.calculateEngagementScore(engagement.getId());
                    return engagementMapper.toResponseDTO(engagement, engagementScore);
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseDTOs);
    }

    @GetMapping("/date-range")
    public ResponseEntity<List<CourseEngagementResponseDTO>> getEngagementsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<CourseEngagement> engagements = engagementService.findByEnrollmentDateBetween(startDate, endDate);
        List<CourseEngagementResponseDTO> responseDTOs = engagements.stream()
                .map(engagement -> {
                    double engagementScore = engagementService.calculateEngagementScore(engagement.getId());
                    return engagementMapper.toResponseDTO(engagement, engagementScore);
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseDTOs);
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<CourseEngagementResponseDTO>> getEngagementsByType(
            @PathVariable String type) {
        List<CourseEngagement> engagements = engagementService.findByEngagementType(type);
        List<CourseEngagementResponseDTO> responseDTOs = engagements.stream()
                .map(engagement -> {
                    double engagementScore = engagementService.calculateEngagementScore(engagement.getId());
                    return engagementMapper.toResponseDTO(engagement, engagementScore);
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseDTOs);
    }

    @PostMapping("/{id}/lecture/{lectureNumber}")
    public ResponseEntity<CourseEngagementResponseDTO> recordLectureProgress(
            @PathVariable Long id,
            @PathVariable int lectureNumber) {
        CourseEngagement engagement = engagementService.recordLectureProgress(id, lectureNumber, LocalDate.now());
        double engagementScore = engagementService.calculateEngagementScore(id);
        return ResponseEntity.ok(engagementMapper.toResponseDTO(engagement, engagementScore));
    }

    @GetMapping("/{id}/score")
    public ResponseEntity<Double> getEngagementScore(@PathVariable Long id) {
        double score = engagementService.calculateEngagementScore(id);
        return ResponseEntity.ok(score);
    }

    @GetMapping("/{id}/active")
    public ResponseEntity<Boolean> isEngagementActive(@PathVariable Long id) {
        boolean isActive = engagementService.isEngagementActive(id);
        return ResponseEntity.ok(isActive);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEngagement(@PathVariable Long id) {
        engagementService.deleteEngagement(id);
        return ResponseEntity.noContent().build();
    }
} 