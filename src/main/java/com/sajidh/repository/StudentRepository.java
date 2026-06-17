package com.sajidh.repository;

import com.sajidh.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
public interface StudentRepository
    extends JpaRepository<Student, Integer> {

}


