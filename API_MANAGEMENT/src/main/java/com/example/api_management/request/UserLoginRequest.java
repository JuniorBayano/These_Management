package com.example.api_management.request;

import jakarta.validation.constraints.NotNull;

public record UserLoginRequest(
        @NotNull
        String email,
        @NotNull
        String password
) {
}
