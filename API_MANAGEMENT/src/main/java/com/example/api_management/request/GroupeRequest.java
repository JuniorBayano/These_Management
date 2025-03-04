package com.example.api_management.request;

import jakarta.validation.constraints.NotNull;

public record GroupeRequest(
        @NotNull()
        Integer numeroDeGroupe,
        @NotNull()
        String nomDeEncadreur,
        @NotNull()
        String theme
) {
}
