package com.sajidh.repository;

import com.sajidh.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
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


