package com.sajidh.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
public class StudentRequestDTO {

    @NotBlank(
            message = "Name cannot be empty"
    )
    private String name;

    @Min(
            value = 17,
            message = "Age must be at least 17"
    )
    private int age;

    @NotBlank(
            message = "Course cannot be empty"
    )
    private String course;

    private int departmentId;

    public StudentRequestDTO() {

    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getCourse() {
        return course;
    }

    public int getDepartmentId() {return departmentId;}

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }
}
