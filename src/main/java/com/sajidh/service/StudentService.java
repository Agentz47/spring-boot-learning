package com.sajidh.service;

import com.sajidh.model.Student;
import com.sajidh.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    private final StudentRepository repository;

    public StudentService(
            StudentRepository repository
    ) {
        this.repository = repository;
    }

    public List<Student> getAllStudents() {

        return repository.findAll();
    }

    public Student getStudentById(
            int id
    ) {

        return repository.findById(id)
                .orElse(null);

    }


}
