package com.sajidh.dto;


public class StudentResponseDTO {

    private int id;
    private String name;
    private int age;
    private String course;

    private String departmentName;

    public StudentResponseDTO(int id, String name, int age, String course, String departmentName) {

        this.id = id;
        this.name = name;
        this.age = age;
        this.course = course;
        this.departmentName = departmentName;
    }

    public int getId() {
        return id;
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
    public String getDepartmentName() {return departmentName;}
}
