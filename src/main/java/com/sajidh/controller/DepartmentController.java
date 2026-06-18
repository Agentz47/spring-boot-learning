package com.sajidh.controller;

import com.sajidh.model.Department;
import com.sajidh.service.DepartmentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/departments")
public class DepartmentController {

    private final DepartmentService service;

    public DepartmentController(
            DepartmentService service
    ) {
        this.service = service;
    }

    @PostMapping
    public Department department(
            @Valid
            @RequestBody Department department
    ) {

        return service.addDepartment(department);
    }

    @GetMapping
    public List<Department> getAllDepartments() {

        return service.getAllDepartments();
    }

    @GetMapping("/{id}")
    public Department getDepartmentById(
            @PathVariable int id
    ) {

        return service.getDepartmentById(id);
    }

    @PutMapping("/{id}")
    public Department updateDepartment(
            @PathVariable int id,
            @RequestBody Department department
    ) {

        return service.updateDepartment(
                id,
                department
        );
    }

    @DeleteMapping("/{id}")
    public String deleteDepartment(
            @PathVariable int id
    ) {

        service.deleteDepartment(id);

        return "Department Deleted Successfully!";
    }
}
