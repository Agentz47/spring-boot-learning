package com.sajidh.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.persistence.GenerationType;
import jakarta.persistence.GeneratedValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Student {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private int id;

    @NotBlank(message = "Name cannot be empty")
    private String name;

    @Min(
          value = 17,
          message = "Age must be at least 17"
    )
    private int age;

    @NotBlank(message = "Course cannot be empty")
    private String course;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

}