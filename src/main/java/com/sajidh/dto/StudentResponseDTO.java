package com.sajidh.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StudentResponseDTO {

    private int id;
    private String name;
    private int age;
    private String course;
    private String departmentName;
}
