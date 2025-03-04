package com.example.api_management.request;

import jakarta.validation.constraints.NotNull;

public record SalleRequest(
        @NotNull()
        String nomSalle,
        @NotNull()
        Integer nbrePlaces
) {
}
