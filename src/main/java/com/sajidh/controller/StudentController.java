package com.sajidh.controller;

import com.sajidh.dto.StudentRequestDTO;
import com.sajidh.dto.StudentResponseDTO;
import com.sajidh.model.Student;
import com.sajidh.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.ArrayList;
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
    public Page<StudentResponseDTO> getAllStudents(
            @RequestParam(
                    defaultValue = "0"
            )
            int page,

            @RequestParam(
                    defaultValue = "10"
            )
            int size,

            @RequestParam(defaultValue = "id")
            String sortBy,

            @RequestParam(defaultValue = "asc")
            String direction,

            @RequestParam(required = false)
            String name,

            @RequestParam(required = false)
            String course,

            @RequestParam(required = false)
            Integer age,

            @RequestParam(required = false)
            String department
    ) {

        return service.getAllStudents(
                page,
                size,
                sortBy,
                direction,
                name,
                course,
                age,
                department
        );
    }

    @PostMapping("/students")
    public Student Student(
            @Valid
            @RequestBody StudentRequestDTO request
            ) {

        return service.addStudent(request);
    }

    @GetMapping("/students/{id}")
    public ResponseEntity<StudentResponseDTO> getStudentById(
            @PathVariable int id
    ) {

        Student student =
                service.getStudentById(id);

        StudentResponseDTO dto =
                new StudentResponseDTO(
                        student.getId(),
                        student.getName(),
                        student.getAge(),
                        student.getCourse(),
                        student.getDepartment().getName()
                );


        return ResponseEntity.ok(dto);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/students/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteStudent(
            @PathVariable int id
    ) {

        service.deleteStudent(id);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PutMapping("/students/{id}")
    @PreAuthorize("hasRole('ADMIN')")
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
