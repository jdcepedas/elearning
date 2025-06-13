package com.elearning.controller;

import com.elearning.dto.course.CourseCreateDTO;
import com.elearning.dto.course.CourseResponseDTO;
import com.elearning.dto.course.StudentEnrollmentDTO;
import com.elearning.exception.ResourceNotFoundException;
import com.elearning.model.Course;
import com.elearning.model.CourseEngagement;
import com.elearning.service.CourseService;
import com.elearning.service.CourseEngagementService;
import com.elearning.mapper.CourseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private CourseEngagementService engagementService;

    @Autowired
    private CourseMapper courseMapper;

    @PostMapping
    public ResponseEntity<CourseResponseDTO> createCourse(@RequestBody CourseCreateDTO courseDTO) {
        Course course = courseMapper.toEntity(courseDTO);
        Course savedCourse = courseService.saveCourse(course);
        List<CourseEngagement> engagements = engagementService.findByCourseId(savedCourse.getId());
        double averageProgress = courseService.getAverageCourseProgress(savedCourse.getId());
        int activeStudentCount = courseService.getActiveStudentCount(savedCourse.getId());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(courseMapper.toResponseDTO(savedCourse, engagements, averageProgress, activeStudentCount));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseResponseDTO> getCourse(@PathVariable String id) {
        Course course = courseService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + id));
        List<CourseEngagement> engagements = engagementService.findByCourseId(id);
        double averageProgress = courseService.getAverageCourseProgress(id);
        int activeStudentCount = courseService.getActiveStudentCount(id);
        return ResponseEntity.ok(courseMapper.toResponseDTO(course, engagements, averageProgress, activeStudentCount));
    }

    @GetMapping
    public ResponseEntity<List<CourseResponseDTO>> getAllCourses() {
        List<Course> courses = courseService.findAll();
        List<CourseResponseDTO> responseDTOs = courses.stream()
                .map(course -> {
                    List<CourseEngagement> engagements = engagementService.findByCourseId(course.getId());
                    double averageProgress = courseService.getAverageCourseProgress(course.getId());
                    int activeStudentCount = courseService.getActiveStudentCount(course.getId());
                    return courseMapper.toResponseDTO(course, engagements, averageProgress, activeStudentCount);
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseDTOs);
    }

    @GetMapping("/title/{title}")
    public ResponseEntity<CourseResponseDTO> getCourseByTitle(@PathVariable String title) {
        Course course = courseService.findByTitle(title)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with title: " + title));
        List<CourseEngagement> engagements = engagementService.findByCourseId(course.getId());
        double averageProgress = courseService.getAverageCourseProgress(course.getId());
        int activeStudentCount = courseService.getActiveStudentCount(course.getId());
        return ResponseEntity.ok(courseMapper.toResponseDTO(course, engagements, averageProgress, activeStudentCount));
    }

    @GetMapping("/lectures/greater-than/{count}")
    public ResponseEntity<List<CourseResponseDTO>> getCoursesByMinLectureCount(@PathVariable Integer count) {
        List<Course> courses = courseService.findByLectureCountGreaterThan(count);
        List<CourseResponseDTO> responseDTOs = courses.stream()
                .map(course -> {
                    List<CourseEngagement> engagements = engagementService.findByCourseId(course.getId());
                    double averageProgress = courseService.getAverageCourseProgress(course.getId());
                    int activeStudentCount = courseService.getActiveStudentCount(course.getId());
                    return courseMapper.toResponseDTO(course, engagements, averageProgress, activeStudentCount);
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseDTOs);
    }

    @GetMapping("/lectures/less-than/{count}")
    public ResponseEntity<List<CourseResponseDTO>> getCoursesByMaxLectureCount(@PathVariable Integer count) {
        List<Course> courses = courseService.findByLectureCountLessThan(count);
        List<CourseResponseDTO> responseDTOs = courses.stream()
                .map(course -> {
                    List<CourseEngagement> engagements = engagementService.findByCourseId(course.getId());
                    double averageProgress = courseService.getAverageCourseProgress(course.getId());
                    int activeStudentCount = courseService.getActiveStudentCount(course.getId());
                    return courseMapper.toResponseDTO(course, engagements, averageProgress, activeStudentCount);
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseDTOs);
    }

    @GetMapping("/{courseId}/students")
    public ResponseEntity<List<StudentEnrollmentDTO>> getEnrolledStudents(
            @PathVariable String courseId) {
        List<CourseEngagement> engagements = engagementService.findByCourseId(courseId);
        List<StudentEnrollmentDTO> enrollmentDTOs = engagements.stream()
                .map(engagement -> StudentEnrollmentDTO.builder()
                        .studentId(engagement.getStudent().getId())
                        .countryCode(engagement.getStudent().getCountryCode())
                        .progress(engagement.getPercentComplete())
                        .lastActivityDate(engagement.getLastActivityDate().toString())
                        .engagementType(engagement.getEngagementType())
                        .build())
                .collect(Collectors.toList());
        return ResponseEntity.ok(enrollmentDTOs);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable String id) {
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }
} 