package com.sajidh.repository;

import com.sajidh.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository
    extends JpaRepository<Student, Integer> {

    List<Student> findByCourse(
            String course
    );

    List<Student> findByAgeGreaterThan(
            int age
    );

    List<Student> findByCourseAndAgeGreaterThan(
            String course,
            int age
    );
}


