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

    public StudentRequestDTO(int id, String name, int age, String course) {
        this.name = name;
        this.age = age;
        this.course = course;
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

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setCourse(String course) {
        this.course = course;
    }
}
