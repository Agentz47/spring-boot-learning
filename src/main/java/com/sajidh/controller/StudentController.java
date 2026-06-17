package com.sajidh.controller;

import com.sajidh.model.Student;
import com.sajidh.service.StudentService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;

@RestController
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

    @PostMapping("/students")
    public Student student(
            @RequestBody Student student
    ) {

        return service.addStudent(student);
    }

    @GetMapping("/students/{id}")
    public Student getStudentById(
            @PathVariable int id
    ) {

        return service.getStudentById(id);
    }

    @DeleteMapping("/students/{id}")
    public String deleteStudent(
            @PathVariable int id
    ) {

        service.deleteStudent(id);

        return "Student Deleted Successfully!";
    }

    @PutMapping("/students/{id}")
    public Student updateStudent(
            @PathVariable int id,
            @RequestBody Student student
    ) {

        return service.updateStudent(
                id,
                student
        );
    }
}
