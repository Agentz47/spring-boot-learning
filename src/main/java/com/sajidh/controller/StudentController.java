package com.sajidh.controller;

import com.sajidh.model.Student;
import com.sajidh.service.StudentService;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

public class StudentController {

    private final StudentService service;

    public StudentController(
            StudentService service
    ) {
        this.service = service;
    }

    @GetMapping("/students")
    public List<Student> getAllStudents() {

        return service.getAllStudents();
    }
}
