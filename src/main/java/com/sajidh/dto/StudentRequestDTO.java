package com.sajidh.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
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

}
