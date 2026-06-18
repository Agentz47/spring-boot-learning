package com.sajidh.service;

import com.sajidh.exception.DepartmentNotFoundException;
import com.sajidh.model.Department;
import com.sajidh.repository.DepartmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {

    private final DepartmentRepository repository;

    public DepartmentService(
            DepartmentRepository repository
    ) {
        this.repository = repository;
    }

    public List<Department> getAllDepartments() {

        return repository.findAll();
    }

    public Department getDepartmentById(
            int id
    ) {

        return repository.findById(id)
                .orElseThrow(
                        () -> new DepartmentNotFoundException(
                                "Department not found with ID: " + id
                        )
                );
    }

    public Department addDepartment(
            Department department
    ) {

        return repository.save(department);
    }

    public void deleteDepartment(
            int id
    ) {

        repository.deleteById(id);
    }

    public Department updateDepartment(
            int id,
            Department updateDepartment
    ) {

        Department existingDepartment =
                repository.findById(id)
                        .orElse(null);

        if (existingDepartment == null) {

            return null;
        }

        existingDepartment.setName(
                updateDepartment.getName()
        );

        return repository.save(
                existingDepartment
        );
    }

}
