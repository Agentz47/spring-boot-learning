package com.sajidh.controller;

import com.sajidh.dto.ApiResponse;
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
    public ResponseEntity<ApiResponse<Page<StudentResponseDTO>>> getAllStudents(
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

        Page<StudentResponseDTO> students =
                service.getAllStudents(
                        page,
                        size,
                        sortBy,
                        direction,
                        name,
                        course,
                        age,
                        department
                );

        ApiResponse<Page<StudentResponseDTO>> response =
                new ApiResponse<>(
                        true,
                        "Students retrieved successfully",
                        students
                );

        return ResponseEntity.ok(response);
    }

    @PostMapping("/students")
    public ResponseEntity<ApiResponse<StudentResponseDTO>> addStudent(
            @Valid
            @RequestBody StudentRequestDTO request
            ) {

        Student student =
                service.addStudent(request);

        StudentResponseDTO dto =
                new StudentResponseDTO(
                        student.getId(),
                        student.getName(),
                        student.getAge(),
                        student.getCourse(),
                        student.getDepartment().getName()
                );

        ApiResponse<StudentResponseDTO> response =
                new ApiResponse<>(
                        true,
                        "Student created successfully",
                        dto
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/students/{id}")
    public ResponseEntity<ApiResponse<StudentResponseDTO>> getStudentById(
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

        ApiResponse<StudentResponseDTO> response =
                new ApiResponse<>(
                        true,
                        "Student retrieved successfully",
                        dto
                );


        return ResponseEntity.ok(response);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/students/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteStudent(
            @PathVariable int id
    ) {

        service.deleteStudent(id);
    }

    @PutMapping("/students/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<StudentResponseDTO>> updateStudent(
            @PathVariable int id,
            @RequestBody StudentRequestDTO request
    ) {

        Student student =
                service.updateStudent(id, request);

        StudentResponseDTO dto =
                new StudentResponseDTO(
                        student.getId(),
                        student.getName(),
                        student.getAge(),
                        student.getCourse(),
                        student.getDepartment().getName()
                );

        ApiResponse<StudentResponseDTO> response =
                new ApiResponse<>(
                        true,
                        "Student updated successfully",
                        dto
                );

        return ResponseEntity
                .ok(response);
    }
}
