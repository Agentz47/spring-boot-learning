package com.sajidh.service;

import com.sajidh.dto.LoginRequestDTO;
import com.sajidh.dto.LoginResponseDTO;
import com.sajidh.dto.RegisterRequestDTO;
import com.sajidh.dto.RegisterResponseDTO;
import com.sajidh.exception.UsernameAlreadyExistsException;
import com.sajidh.model.AppUser;
import com.sajidh.model.Role;
import com.sajidh.repository.AppUserRepository;
import com.sajidh.security.JwtService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AppUserService {

    private final AppUserRepository repository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AppUserService(
            AppUserRepository repository,
            BCryptPasswordEncoder passwordEncoder,
            JwtService jwtService
    ) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }
    public RegisterResponseDTO register(
            RegisterRequestDTO request
    ) {

        System.out.println("Checking the username");

        if (
                repository.findByUsername(
                        request.getUsername()
                ).isPresent()
        ) {
            System.out.println("Already exists!");

            throw new UsernameAlreadyExistsException(
                    "Username already exists"
            );
        }

        AppUser user = new AppUser();

        user.setUsername(
                request.getUsername()
        );

        user.setPassword(
                passwordEncoder.encode(
                        request.getPassword()
                )
        );

        user.setRole(
                Role.USER
        );

        AppUser savedUser =
                repository.save(user);

        return new RegisterResponseDTO(
                savedUser.getId(),
                savedUser.getUsername()
        );
    }

    public LoginResponseDTO login(
            LoginRequestDTO request
    ) {

        AppUser user =
                repository.findByUsername(
                        request.getUsername()
                )
                        .orElseThrow(
                                ()-> new RuntimeException(
                                        "Invalid username or password"
                                )
                        );

        if (
                !passwordEncoder.matches(
                        request.getPassword(),
                        user.getPassword()
                )
        ) {

            throw new RuntimeException(
                    "Invalid username or password"
            );
        }

        return new LoginResponseDTO(
                jwtService.generateToken(
                        user.getUsername()
                )
        );

    }

}
