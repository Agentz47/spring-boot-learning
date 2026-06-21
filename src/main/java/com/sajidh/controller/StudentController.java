package com.sajidh.controller;

import com.sajidh.dto.StudentRequestDTO;
import com.sajidh.dto.StudentResponseDTO;
import com.sajidh.model.Student;
import com.sajidh.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
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
    public List<StudentResponseDTO> getAllStudents() {

        List<Student> students =
                service.getAllStudents();

        List<StudentResponseDTO> dtos =
                new ArrayList<>();

        return students.stream()
                .map(student ->
                        new StudentResponseDTO(
                                student.getId(),
                                student.getName(),
                                student.getAge(),
                                student.getCourse(),
                                student.getDepartment().getName()
                        )
                )
                .toList();

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

    @GetMapping("/students/course/{course}")
    public List<StudentResponseDTO> getStudentsByCourse(
            @PathVariable String course
    ) {

        List<Student> students =
                service.getStudentsByCourse(
                        course
                );

        return students.stream()
                .map(student ->
                        new StudentResponseDTO(
                                student.getId(),
                                student.getName(),
                                student.getAge(),
                                student.getCourse(),
                                student.getDepartment().getName()
                        )
                )
                .toList();

    }

    @GetMapping("/students/age/{age}")
    public List<StudentResponseDTO> getStudentsOlderThan(
            @PathVariable int age
    ) {

        List<Student> students =
                service.getStudentsOlderThan(
                        age
                );

        return students.stream()
                .map(student ->
                        new StudentResponseDTO(
                                student.getId(),
                                student.getName(),
                                student.getAge(),
                                student.getCourse(),
                                student.getDepartment().getName()
                        )
                )
                .toList();

    }

    @GetMapping("/students/search/{course}/{age}")
    public List<StudentResponseDTO>
    getStudentsByCourseAndAge(
            @PathVariable String course,
            @PathVariable int age
    ) {

        List<Student> students =
                service.getStudentsByCourseAndAge(
                        course,
                        age
                );

        return students.stream()
                .map(student ->
                        new StudentResponseDTO(
                                student.getId(),
                                student.getName(),
                                student.getAge(),
                                student.getCourse(),
                                student.getDepartment().getName()
                        )
                )
                .toList();
    }
}
