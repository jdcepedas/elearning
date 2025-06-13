package com.elearning.seeder;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@RequiredArgsConstructor
public class EngagementSeeder {
    private final WebClient webClient;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Random random = new Random();

    public void seedEngagements() {
        // Course IDs from CourseSeeder
        List<String> courseIds = Arrays.asList(
            "CS101", "CS102", "CS201", "CS202", 
            "CS301", "CS302", "CS401", "CS402"
        );

        // Create engagements for each student (IDs 1-16) with random courses
        for (long studentId = 1; studentId <= 16; studentId++) {
            // Each student enrolls in 2-4 random courses
            int numCourses = random.nextInt(3) + 2;
            for (int i = 0; i < numCourses; i++) {
                String courseId = courseIds.get(random.nextInt(courseIds.size()));
                LocalDate enrollmentDate = LocalDate.now().minusDays(random.nextInt(365));
                
                try {
                    EngagementData engagement = new EngagementData(
                        studentId,
                        courseId,
                        enrollmentDate.toString(),
                        "Enrollment"
                    );

                    String response = webClient.post()
                        .uri("/engagements")
                        .bodyValue(engagement)
                        .retrieve()
                        .bodyToMono(String.class)
                        .block();

                    System.out.println("Created engagement for student " + studentId + 
                                     " in course " + courseId + " - Response: " + response);

                    // Record some random lecture progress
                    if (response != null) {
                        recordLectureProgress(response, courseId);
                    }
                } catch (Exception e) {
                    System.err.println("Error creating engagement for student " + studentId + 
                                     " in course " + courseId + ": " + e.getMessage());
                }
            }
        }
    }

    private void recordLectureProgress(String engagementId, String courseId) {
        // Extract the engagement ID from the response
        try {
            // Assuming the response contains the engagement ID
            Long id = objectMapper.readTree(engagementId).get("id").asLong();
            
            // Record progress for 1-5 random lectures
            int numLectures = random.nextInt(5) + 1;
            for (int lecture = 1; lecture <= numLectures; lecture++) {
                try {
                    String response = webClient.post()
                        .uri("/engagements/" + id + "/lecture/" + lecture)
                        .retrieve()
                        .bodyToMono(String.class)
                        .block();

                    System.out.println("Recorded progress for lecture " + lecture + 
                                     " in engagement " + id + " - Response: " + response);
                } catch (Exception e) {
                    System.err.println("Error recording lecture progress: " + e.getMessage());
                }
            }
        } catch (Exception e) {
            System.err.println("Error parsing engagement ID: " + e.getMessage());
        }
    }

    @lombok.Data
    @lombok.AllArgsConstructor
    private static class EngagementData {
        private Long studentId;
        private String courseId;
        private String enrollmentDate;
        private String engagementType;
    }
} 