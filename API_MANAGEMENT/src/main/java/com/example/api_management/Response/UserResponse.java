package com.example.api_management.Response;

public record UserResponse(
        String name,
        String password,
        String email,
        String matricule,
        Integer groupenumber
) {
}
