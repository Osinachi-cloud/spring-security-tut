package com.example.springsecuritytut.students;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Student {

    @Id
    private  Integer studentId;
    private  String studentName;





}
