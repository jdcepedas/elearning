package com.elearning.seeder;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
public class CourseSeeder {
    private final WebClient webClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public void seedCourses() {
        List<CourseData> courses = Arrays.asList(
            new CourseData("CS101", "Introduction to Programming", 12),
            new CourseData("CS102", "Data Structures and Algorithms", 15),
            new CourseData("CS201", "Object-Oriented Programming", 10),
            new CourseData("CS202", "Database Systems", 14),
            new CourseData("CS301", "Web Development", 16),
            new CourseData("CS302", "Mobile App Development", 12),
            new CourseData("CS401", "Machine Learning", 20),
            new CourseData("CS402", "Cloud Computing", 18)
        );

        courses.forEach(course -> {
            try {
                String response = webClient.post()
                    .uri("/courses")
                    .bodyValue(course)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

                System.out.println("Created course: " + course.title + " - Response: " + response);
            } catch (Exception e) {
                System.err.println("Error creating course " + course.title + ": " + e.getMessage());
            }
        });
    }

    @lombok.Data
    @lombok.AllArgsConstructor
    private static class CourseData {
        private String id;
        private String title;
        private int lectureCount;
    }
} 