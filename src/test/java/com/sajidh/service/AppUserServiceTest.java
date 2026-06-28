package com.sajidh.service;

import com.sajidh.dto.RegisterRequestDTO;
import com.sajidh.dto.RegisterResponseDTO;
import com.sajidh.exception.UsernameAlreadyExistsException;
import com.sajidh.model.AppUser;
import com.sajidh.model.Role;
import com.sajidh.repository.AppUserRepository;
import com.sajidh.security.JwtService;
import com.sajidh.security.RefreshTokenService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.mockito.ArgumentCaptor;


import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;


@ExtendWith(MockitoExtension.class)
class AppUserServiceTest {

    @Mock
    private AppUserRepository repository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private RefreshTokenService refreshTokenService;

    @InjectMocks
    private AppUserService service;

    @Test
    void register_ShouldSaveUser_WhenUsernameDoesNotExist() {

        RegisterRequestDTO request =
                new RegisterRequestDTO();

        request.setUsername("sajidh");

        request.setPassword("123456");

        when(
                repository.findByUsername(
                        "sajidh"
                )
        ).thenReturn(
                Optional.empty()
        );

        when(
                passwordEncoder.encode("123456")
        ).thenReturn(
                "encodedPassword"
        );

        AppUser savedUser =
                new AppUser();

        savedUser.setId(1L);
        savedUser.setUsername("sajidh");
        savedUser.setPassword("encodedPassword");
        savedUser.setRole(Role.USER);

        when(

                repository.save(
                        any(AppUser.class)
                )
        ).thenReturn(
                savedUser
        );

        RegisterResponseDTO response =
                service.register(
                        request
                );

        ArgumentCaptor<AppUser> captor =
                ArgumentCaptor.forClass(
                        AppUser.class
                );

        verify(repository).save(
                captor.capture()
        );

        AppUser capturedUser =
                captor.getValue();

        assertEquals(
                1L,
                response.getId()
        );

        assertEquals(
                "sajidh",
                response.getUsername()
        );

        verify(
                repository,
                times(1)
        ).save(
                any(AppUser.class)
        );

    }

    @Test
    void register_ShouldSaveUser_WhenUsernameAlreadyExist() {

        RegisterRequestDTO request =
                new RegisterRequestDTO();

        request.setUsername("sajidh");
        request.setPassword("123456");

        AppUser existingUser =
                new AppUser();

        existingUser.setUsername("sajidh");

        when(
                repository.findByUsername(
                        "sajidh"
                )
        ).thenReturn(
                Optional.of(existingUser)
        );

        assertThrows(
                UsernameAlreadyExistsException.class,
                () -> service.register(request)
        );
    }
}
