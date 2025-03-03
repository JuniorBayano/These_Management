package com.example.api_management.request;

import jakarta.validation.constraints.NotNull;

public record UserRequest(
        @NotNull()
        String username,
        @NotNull()
        String password,
        @NotNull()
        String email,
        @NotNull()
        String matricule,
        @NotNull()
        Integer groupe,
        @NotNull()
        String role
) {
}


