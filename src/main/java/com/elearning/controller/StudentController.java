package com.elearning.controller;

import com.elearning.dto.student.CourseProgressDTO;
import com.elearning.dto.student.StudentCreateDTO;
import com.elearning.dto.student.StudentResponseDTO;
import com.elearning.exception.ResourceNotFoundException;
import com.elearning.model.Student;
import com.elearning.model.CourseEngagement;
import com.elearning.service.StudentService;
import com.elearning.service.CourseEngagementService;
import com.elearning.mapper.StudentMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/students")
@Tag(name = "Student Management", description = "APIs for managing students and their course enrollments")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private CourseEngagementService engagementService;

    @Autowired
    private StudentMapper studentMapper;

    @Operation(summary = "Create a new student", description = "Creates a new student and returns the created student with their details")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Student created successfully",
            content = @Content(schema = @Schema(implementation = StudentResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping
    public ResponseEntity<StudentResponseDTO> createStudent(@RequestBody StudentCreateDTO studentDTO) {
        Student student = studentMapper.toEntity(studentDTO);
        Student savedStudent = studentService.saveStudent(student);
        List<CourseEngagement> engagements = engagementService.findByStudentId(savedStudent.getId());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(studentMapper.toResponseDTO(savedStudent, engagements));
    }

    @Operation(summary = "Get student by ID", description = "Retrieves a student's details and their course engagements by student ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Student found",
            content = @Content(schema = @Schema(implementation = StudentResponseDTO.class))),
        @ApiResponse(responseCode = "404", description = "Student not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<StudentResponseDTO> getStudent(
            @Parameter(description = "ID of the student to retrieve", required = true)
            @PathVariable Long id) {
        Student student = studentService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + id));
        List<CourseEngagement> engagements = engagementService.findByStudentId(id);
        return ResponseEntity.ok(studentMapper.toResponseDTO(student, engagements));
    }

    @Operation(summary = "Get all students", description = "Retrieves a list of all students with their course engagements")
    @ApiResponse(responseCode = "200", description = "List of students retrieved successfully",
        content = @Content(schema = @Schema(implementation = StudentResponseDTO.class)))
    @GetMapping
    public ResponseEntity<List<StudentResponseDTO>> getAllStudents() {
        List<Student> students = studentService.findAll();
        List<StudentResponseDTO> responseDTOs = students.stream()
                .map(student -> {
                    List<CourseEngagement> engagements = engagementService.findByStudentId(student.getId());
                    return studentMapper.toResponseDTO(student, engagements);
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseDTOs);
    }

    @Operation(summary = "Get students by country", description = "Retrieves a list of students from a specific country")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "List of students retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "No students found for the given country code")
    })
    @GetMapping("/country/{countryCode}")
    public ResponseEntity<List<StudentResponseDTO>> getStudentsByCountry(
            @Parameter(description = "Country code to filter students", required = true)
            @PathVariable String countryCode) {
        List<Student> students = studentService.findByCountryCode(countryCode);
        List<StudentResponseDTO> responseDTOs = students.stream()
                .map(student -> {
                    List<CourseEngagement> engagements = engagementService.findByStudentId(student.getId());
                    return studentMapper.toResponseDTO(student, engagements);
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseDTOs);
    }

    @Operation(summary = "Get students by enrollment year", description = "Retrieves a list of students enrolled in a specific year")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "List of students retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "No students found for the given year")
    })
    @GetMapping("/year/{year}")
    public ResponseEntity<List<StudentResponseDTO>> getStudentsByYear(
            @Parameter(description = "Year of enrollment to filter students", required = true)
            @PathVariable Integer year) {
        List<Student> students = studentService.findByYearEnrolled(year);
        List<StudentResponseDTO> responseDTOs = students.stream()
                .map(student -> {
                    List<CourseEngagement> engagements = engagementService.findByStudentId(student.getId());
                    return studentMapper.toResponseDTO(student, engagements);
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseDTOs);
    }

    @Operation(summary = "Get students by gender", description = "Retrieves a list of students by gender")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "List of students retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "No students found for the given gender")
    })
    @GetMapping("/gender/{gender}")
    public ResponseEntity<List<StudentResponseDTO>> getStudentsByGender(
            @Parameter(description = "Gender to filter students", required = true)
            @PathVariable String gender) {
        List<Student> students = studentService.findByGender(gender);
        List<StudentResponseDTO> responseDTOs = students.stream()
                .map(student -> {
                    List<CourseEngagement> engagements = engagementService.findByStudentId(student.getId());
                    return studentMapper.toResponseDTO(student, engagements);
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(responseDTOs);
    }

    @Operation(summary = "Enroll student in a course", description = "Enrolls a student in a specific course")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Student enrolled successfully"),
        @ApiResponse(responseCode = "404", description = "Student or course not found"),
        @ApiResponse(responseCode = "400", description = "Invalid enrollment request")
    })
    @PostMapping("/{studentId}/courses/{courseId}")
    public ResponseEntity<StudentResponseDTO> enrollInCourse(
            @Parameter(description = "ID of the student to enroll", required = true)
            @PathVariable Long studentId,
            @Parameter(description = "ID of the course to enroll in", required = true)
            @PathVariable String courseId) {
        Student student = studentService.enrollInCourse(studentId, courseId, LocalDate.now());
        List<CourseEngagement> engagements = engagementService.findByStudentId(studentId);
        return ResponseEntity.ok(studentMapper.toResponseDTO(student, engagements));
    }

    @Operation(summary = "Get student's enrolled courses", description = "Retrieves a list of courses a student is enrolled in with their progress")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "List of courses retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "Student not found")
    })
    @GetMapping("/{studentId}/courses")
    public ResponseEntity<List<CourseProgressDTO>> getEnrolledCourses(
            @Parameter(description = "ID of the student to get courses for", required = true)
            @PathVariable Long studentId) {
        List<CourseEngagement> engagements = engagementService.findByStudentId(studentId);
        List<CourseProgressDTO> progressDTOs = engagements.stream()
                .map(engagement -> CourseProgressDTO.builder()
                        .courseId(engagement.getCourse().getId())
                        .courseTitle(engagement.getCourse().getTitle())
                        .progress(engagement.getPercentComplete())
                        .lastActivityDate(engagement.getLastActivityDate().toString())
                        .engagementType(engagement.getEngagementType())
                        .build())
                .collect(Collectors.toList());
        return ResponseEntity.ok(progressDTOs);
    }

    @Operation(summary = "Delete a student", description = "Deletes a student and all their course engagements")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Student deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Student not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(
            @Parameter(description = "ID of the student to delete", required = true)
            @PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }
} 