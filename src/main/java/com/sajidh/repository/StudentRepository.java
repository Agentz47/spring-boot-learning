package com.sajidh.repository;

import com.sajidh.model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository
    extends JpaRepository<Student, Integer>,
        JpaSpecificationExecutor<Student> {

}


