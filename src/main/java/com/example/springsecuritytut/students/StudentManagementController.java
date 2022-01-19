package com.example.springsecuritytut.students;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;


@RestController
@RequestMapping("management/api/vi/students")
public class StudentManagementController {

    private static final List<Student> STUDENTS = Arrays.asList(
            new Student(1, "new Osinachi"),
            new Student(2, "new Uchenna")

    );

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN, ROLE_ADMINTRAINEE')")
    public List<Student> getAllStudents(){
        System.out.println("get all students");
        return STUDENTS;
    }
    @PostMapping
    @PreAuthorize("hasAuthority('student:write')")
    public void registeredNewStudent(@RequestBody Student student){
        System.out.println("registered user");
        System.out.println(student);
    }

    @DeleteMapping(path= "{studentId}")
    @PreAuthorize("hasAuthority('student:write')")
    public void deleteStudent(@PathVariable("studentId") Integer studentId){
        System.out.println("deleted successfully");
        System.out.println(studentId);
    }

    @PreAuthorize("hasAuthority('student:write')")
    @PutMapping(path={"studentId"})
    public void updateStudent(@PathVariable("studentId") Integer studentId,@RequestBody Student student){
        System.out.println("updated successfully");
        System.out.println(String.format("%s %s",studentId, student));
    }
}






