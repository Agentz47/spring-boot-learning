package com.sajidh.service;

import com.sajidh.exception.StudentNotFoundException;
import com.sajidh.model.Student;
import com.sajidh.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

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
                .orElseThrow(
                        () -> new StudentNotFoundException(
                                "Student not found with ID: " + id
                        )
                );

    }

    public Student addStudent(
            Student student
    ) {

        return repository.save(student);
    }

    public void deleteStudent(
            int id
    ) {

        repository.deleteById(id);
    }


    public Student updateStudent(
            int id,
            Student updateStudent
    ) {

        Student existingStudent =
                repository.findById(id)
                        .orElse(null);

        if (existingStudent == null) {

            return null;

        }

        existingStudent.setName(
                updateStudent.getName()
        );

        existingStudent.setAge(
                updateStudent.getAge()
        );

        existingStudent.setCourse(
                updateStudent.getCourse()
        );

        return repository.save(
                existingStudent
        );

    }

}
