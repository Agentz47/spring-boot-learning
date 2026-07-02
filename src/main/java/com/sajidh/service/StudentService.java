package com.sajidh.service;

import com.sajidh.dto.StudentRequestDTO;
import com.sajidh.dto.StudentResponseDTO;
import com.sajidh.exception.DepartmentNotFoundException;
import com.sajidh.exception.StudentNotFoundException;
import com.sajidh.model.Department;
import com.sajidh.model.Student;
import com.sajidh.repository.DepartmentRepository;
import com.sajidh.repository.StudentRepository;

import com.sajidh.specification.StudentSpecification;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    private final StudentRepository repository;
    private final DepartmentRepository departmentRepository;

    private StudentResponseDTO mapToDTO(
            Student student
    ) {
        return new StudentResponseDTO(
                student.getId(),
                student.getName(),
                student.getAge(),
                student.getCourse(),
                student.getDepartment().getName()
        );
    }

    public StudentService(
            StudentRepository repository,
            DepartmentRepository departmentRepository
    ) {
        this.repository = repository;
        this.departmentRepository =
                departmentRepository;
    }

    public Page<StudentResponseDTO> getAllStudents(
            int page,
            int size,
            String sortBy,
            String direction,
            String name,
            String course,
            Integer age,
            String department
    ) {

        Sort sort =
                direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                        : Sort.by(sortBy).ascending();

        Pageable pageable =
                PageRequest.of(
                        page,
                        size,
                        sort
                );

        Specification<Student> specification =
                Specification
                        .where(StudentSpecification.hasName(name))
                        .and(StudentSpecification.hasCourse(course))
                        .and(StudentSpecification.hasAge(age))
                        .and(StudentSpecification.hasDepartment(department));

        Page<Student> students =
                repository.findAll(
                        specification,
                        pageable
                );

        return students.map(
                this::mapToDTO
        );
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
            StudentRequestDTO request

    ) {

        Department department =
                departmentRepository
                        .findById(
                                request.getDepartmentId()
                        )
                        .orElseThrow(
                                ()-> new DepartmentNotFoundException(
                                        "Department not found"
                                )
                        );

        Student student =
                new Student();

        student.setName(
                request.getName()
        );

        student.setAge(
                request.getAge()
        );

        student.setCourse(
                request.getCourse()
        );
        student.setDepartment(
                department
        );

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
