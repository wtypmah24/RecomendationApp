package com.example.recommendapp.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "Recommendation Application",
                description = "API for registration new users and posting review to another users.", version = "1.0.0",
                contact = @Contact(
                        name = "Taras Shevchenko",
                        email = "wtypmah48@gmail.com"
                )))

public class OpenApiConfig {
}