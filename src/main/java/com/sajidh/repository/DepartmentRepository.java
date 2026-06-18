package com.sajidh.repository;

import com.sajidh.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository
    extends JpaRepository<Department, Integer> {

}
