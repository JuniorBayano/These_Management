package com.example.api_management.request;

import jakarta.validation.constraints.NotNull;

public record UpdateGroupeRequest(
        @NotNull()
        String nomEncadreur,
        String theme
) {
}
