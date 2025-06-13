package com.elearning.seeder;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
public class StudentSeeder {
    private final WebClient webClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public void seedStudents() {
        List<StudentData> students = Arrays.asList(
            new StudentData("US", 2020, 18, "M"),
            new StudentData("US", 2020, 19, "F"),
            new StudentData("UK", 2021, 20, "M"),
            new StudentData("UK", 2021, 21, "F"),
            new StudentData("CO", 2022, 22, "M"),
            new StudentData("CO", 2022, 23, "F"),
            new StudentData("AR", 2023, 24, "M"),
            new StudentData("AR", 2023, 25, "F"),
            new StudentData("DE", 2020, 26, "M"),
            new StudentData("DE", 2021, 27, "F"),
            new StudentData("FR", 2022, 28, "M"),
            new StudentData("FR", 2023, 29, "F"),
            new StudentData("JP", 2020, 30, "M"),
            new StudentData("JP", 2021, 31, "F"),
            new StudentData("IN", 2022, 32, "M"),
            new StudentData("IN", 2023, 33, "F")
        );

        students.forEach(student -> {
            try {
                String response = webClient.post()
                    .uri("/students")
                    .bodyValue(student)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

                System.out.println("Created student from " + student.countryCode + " - Response: " + response);
            } catch (Exception e) {
                System.err.println("Error creating student from " + student.countryCode + ": " + e.getMessage());
            }
        });
    }

    @lombok.Data
    @lombok.AllArgsConstructor
    private static class StudentData {
        private String countryCode;
        private Integer yearEnrolled;
        private Integer ageEnrolled;
        private String gender;
    }
} 