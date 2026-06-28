package com.sajidh.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI studentManagementApi() {

        final String securitySchemeName = "Bearer Authentication";

        return new OpenAPI()
                .info(
                        new Info()
                                .title("Student Management API")
                                .description("REST API built with Spring Boot, JWT Authentication, Refresh Tokens and Role-Based Authorization.")
                                .version("1.0.0")
                                .contact(
                                        new Contact()
                                                .name("Sajidh Yazeen")
                                                .email("sjidh163@gmail.com")
                                )
                )
                .addSecurityItem(
                        new SecurityRequirement()
                                .addList(securitySchemeName)
                )
                .components(
                        new Components()
                                .addSecuritySchemes(
                                        securitySchemeName,
                                        new SecurityScheme()
                                                .name(securitySchemeName)
                                                .type(SecurityScheme.Type.HTTP)
                                                .scheme("bearer")
                                                .bearerFormat("JWT")
                                )
                );
    }
}
