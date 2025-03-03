package com.example.api_management.request;

import jakarta.validation.constraints.NotNull;

public record UserRequest(
        @NotNull(message = "Le nom est requis")
        String name,
        @NotNull(message = "Le mot de passe est requis")
        String password,
        @NotNull(message = "L'email est requis")
        String email,
        @NotNull(message = "Le matricule est requis")
        String matricule,
        @NotNull(message = "Le matricule est requis")
        Integer numerogroupe,
        @NotNull(message = "Le role est requis")
        String role
) {
}
