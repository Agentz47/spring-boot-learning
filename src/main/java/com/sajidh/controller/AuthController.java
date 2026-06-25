package com.sajidh.controller;

import com.sajidh.dto.LoginRequestDTO;
import com.sajidh.dto.LoginResponseDTO;
import com.sajidh.dto.RegisterRequestDTO;
import com.sajidh.dto.RegisterResponseDTO;
import com.sajidh.service.AppUserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AppUserService service;

    public AuthController(
            AppUserService service
    ) {
        this.service = service;
    }

    @PostMapping("/register")
    public RegisterResponseDTO register(
            @Valid
            @RequestBody RegisterRequestDTO request

    ) {

        return service.register(
                request
        );
    }

    @PostMapping("/login")
    public LoginResponseDTO login(
            @Valid
            @RequestBody LoginRequestDTO request
    ) {


        return service.login(
                request
        );


    }
}
