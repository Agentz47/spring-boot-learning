package com.sajidh.service;

import com.sajidh.exception.InvalidCredentialsException;
import lombok.extern.slf4j.Slf4j;

import com.sajidh.dto.*;
import com.sajidh.exception.RefreshTokenException;
import com.sajidh.exception.UsernameAlreadyExistsException;
import com.sajidh.model.AppUser;
import com.sajidh.model.RefreshToken;
import com.sajidh.model.Role;
import com.sajidh.repository.AppUserRepository;
import com.sajidh.security.JwtService;
import com.sajidh.security.RefreshTokenService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AppUserService {

    private final AppUserRepository repository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;


    public AppUserService(
            AppUserRepository repository,
            BCryptPasswordEncoder passwordEncoder,
            JwtService jwtService,
            RefreshTokenService refreshTokenService
    ) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.refreshTokenService = refreshTokenService;
    }
    public RegisterResponseDTO register(
            RegisterRequestDTO request
    ) {

        log.info(
                "Registration request received for username: '{}'.",
                request.getUsername()
        );

        if (
                repository.findByUsername(
                        request.getUsername()
                ).isPresent()
        ) {
            log.warn(
                    "Registration failed. Username '{}' already exists.",
                    request.getUsername()
            );

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

        log.info(
                "User '{}' registered successfully.",
                savedUser.getUsername()
        );

        return new RegisterResponseDTO(
                savedUser.getId(),
                savedUser.getUsername()
        );

    }

    public AuthResponse login(
            LoginRequestDTO request
    ) {

        AppUser user =
                repository.findByUsername(
                        request.getUsername()
                )
                        .orElseThrow(
                                ()-> {
                                    log.warn(
                                            "Login failed. Username '{}' not found.",
                                            request.getUsername()
                                    );

                                    return new InvalidCredentialsException(
                                            "Invalid username or password"
                                    );
                                }
                        );

        if (
                !passwordEncoder.matches(
                        request.getPassword(),
                        user.getPassword()
                )
        ) {
            log.warn(
                    "Login failed. Invalid password for username '{}'.",
                    request.getUsername()
            );

            throw new InvalidCredentialsException(
                    "Invalid username or password"
            );
        }

        String accessToken =
                jwtService.generateToken(
                        request.getUsername()
                );

        RefreshToken refreshToken =
                refreshTokenService.createRefreshToken(
                        request.getUsername()
                );

        log.info(
                "User '{}' logged in successfully",
                user.getUsername()
        );

        return new AuthResponse(
                accessToken,
                refreshToken.getToken()
        );

    }

    public RefreshTokenResponse refreshToken(
            RefreshTokenRequest request
    ) {

        log.info("Refresh token request received.");

        RefreshToken refreshToken =
                refreshTokenService
                        .findByToken(
                                request.getRefreshToken()
                        )
                        .orElseThrow(
                                () -> new RefreshTokenException(
                                        "Refresh token not found"
                                )
                        );
        refreshTokenService.verifyExpiration(
                refreshToken
        );

        AppUser user =
                refreshToken.getUser();

        log.info(
                "Refreshing access token for user '{}'.",
                user.getUsername()
        );

        String accessToken =
                jwtService.generateToken(
                        user.getUsername()
                );

        refreshTokenService.deleteRefreshToken(
                user
        );

        RefreshToken newRefreshToken =
                refreshTokenService
                        .createRefreshToken(
                                user.getUsername()
                        );

        log.info(
                "Access token refreshed for user '{}'.",
                user.getUsername()
        );

        return new RefreshTokenResponse(
                accessToken,
                newRefreshToken.getToken()
        );
    }

}
