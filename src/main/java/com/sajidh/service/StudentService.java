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

    public Student addStudent(
            Student student
    ) {
        if (student.getAge() <= 16) {

            throw new RuntimeException(
                    "Age Must Be Over 16!"
            );

        }

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
