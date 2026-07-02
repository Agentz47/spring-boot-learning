package com.sajidh.controller;


import com.sajidh.dto.RegisterRequestDTO;
import com.sajidh.dto.RegisterResponseDTO;
import com.sajidh.security.JwtAuthenticationFilter;
import com.sajidh.service.AppUserService;
import org.junit.jupiter.api.Test;
import com.sajidh.controller.AuthController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.hasItems;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AppUserService service;

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Test
    void register_ShouldReturnRegisterResponse() throws Exception {


        RegisterResponseDTO response =
                new RegisterResponseDTO(
                        1L,
                        "sajidh"
                );

        when(

                service.register(
                        any(RegisterRequestDTO.class)
                )
        ).thenReturn(
                response
        );

        mockMvc.perform(
                post("/auth/register")
        .contentType(
                MediaType.APPLICATION_JSON
        )
                .content(
                        """
                        {
                        "username":"sajidh",
                        "password":"123456"
                        }
                        """
                )
                )
                .andExpect(
                        status().isOk()
                )
                .andExpect(
                        jsonPath("$.id")
                                .value(1)
                )
                .andExpect(
                        jsonPath("$.username")
                                .value("sajidh")
                );
    }

    @Test
    void register_ShouldReturnBadRequest_WhenRequestIsInvalid() throws Exception {

        mockMvc.perform(
                        post("/auth/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                    {
                        "username":"",
                        "password":""
                    }
                    """)
                )

                .andExpect(status().isBadRequest())

                .andExpect(jsonPath("$.message")
                        .value("Validation Failed"))

                .andExpect(
                        jsonPath("$.errors",
                                hasItems(
                                        "Username cannot be empty",
                                        "Password cannot be empty"
                                )));
    }
}
