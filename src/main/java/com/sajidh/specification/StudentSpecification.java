package com.sajidh.specification;

import com.sajidh.model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
public final class StudentSpecification {
    
    private StudentSpecification() {

    }

    public static Specification<Student> hasName(
            String name
    ) {

        return (root, query, cb) -> {
            if (
                    name == null ||
                            name.isBlank()
            ) {
                return cb.conjunction();
            }

            return cb.like(

                    cb.lower(
                            root.get("name")
                    ),

                    "%" + name.toLowerCase() + "%"
            );
        };
    }

    public static Specification<Student> hasCourse(
            String course
    ) {

        return (root, query, cb) -> {
            if (
                    course == null ||
                            course.isBlank()
            ) {
                return cb.conjunction();
            }

            return cb.equal(

                    cb.lower(

                            root.get("course")
                    ),

                    course.toLowerCase()
            );
        };
    }

    public static Specification<Student> hasAge(
            Integer age
    ) {

        return (root, query, cb) -> {

            if (age == null) {
                return cb.conjunction();
            }

            return cb.equal(
                    root.get("age"),
                    age
            );
        };
    }

    public static Specification<Student> hasDepartment(
            String department
    ) {

        return (root, query, cb) -> {

            if (
                    department == null ||
                            department.isBlank()
            ) {
                return cb.conjunction();
            }

            return cb.equal(
                    cb.lower(
                            root.get("department")
                                    .get("name")
                    ),
                    department.toLowerCase()
            );
        };
    }
}
