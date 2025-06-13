package com.elearning;

import com.elearning.model.Course;
import com.elearning.model.Student;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;

public class Main {

    private static final Random random = new Random();

    public static void main(String[] args) {

    }

    private static String getRandomVal(String... data) {
        return data[random.nextInt(data.length)];
    }

    public static Student getRandomStudent(Course... courses) {
        int maxYear = LocalDate.now().getYear() + 1;
        Student student = new Student(
                null,
                getRandomVal("AU", "CA", "CO", "MX", "US", "AR"),
                random.nextInt(2015, maxYear),
                random.nextInt(18, 90),
                getRandomVal("M", "F", "U"),
                new ArrayList<>()
        );

        return student;
    }
}
