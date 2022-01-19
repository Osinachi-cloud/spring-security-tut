package com.example.springsecuritytut.students;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("api/vi/students")

public class StudentsController {

    private final List<Student> STUDENTS = Arrays.asList(
            new Student(1, "Osinachi"),
            new Student(2, "Uchenna")

    );

    @GetMapping(path="{studentId}")
    public Student getStudent (@PathVariable("studentId") Integer studentId){
        return STUDENTS.stream()
                .filter(student ->
                    studentId.equals(student.getStudentId())
                )
                .findFirst()
                .orElseThrow(()-> new IllegalStateException("Student" + studentId + "does not exist"));
    }
}
