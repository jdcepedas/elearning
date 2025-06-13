package com.elearning.seeder;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class DataSeederApplication {

    private static final String API_BASE_URL = "http://localhost:8080/api";
    private static final String TOKEN = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTc0ODAzMzA5NiwiZXhwIjoxNzQ4MTE5NDk2fQ.B_b2VVYhMxNYMLtE_svHzqiAb5y8yaCEFvZuO02ULpCMVhZRAiWo0619YgsiKi7PEdYuiG7nD6YANiaTNXX2IA";

    public static void main(String[] args) {
        SpringApplication.run(DataSeederApplication.class, args);
    }

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl(API_BASE_URL)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + TOKEN)
                .build();
    }

    @Bean
    public CommandLineRunner seedData(WebClient webClient) {
        return args -> {
            System.out.println("Starting data seeding...");
            
            // Create courses
            /*CourseSeeder courseSeeder = new CourseSeeder(webClient);
            courseSeeder.seedCourses();
            
            // Create students
            StudentSeeder studentSeeder = new StudentSeeder(webClient);
            studentSeeder.seedStudents();
            */
            
            // Create course engagements
            EngagementSeeder engagementSeeder = new EngagementSeeder(webClient);
            engagementSeeder.seedEngagements();
            
            System.out.println("Data seeding completed!");
        };
    }
} 