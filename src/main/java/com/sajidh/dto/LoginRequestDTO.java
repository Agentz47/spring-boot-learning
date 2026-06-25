package com.sajidh.dto;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LoginRequestDTO {

    @NotBlank(
            message = "Username cannot be empty"
    )
    private String username;

    @NotBlank(
            message = "Password cannot be empty"
    )
    private String password;
}
