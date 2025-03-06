package com.example.api_management.request;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

public record SoutenanceRequest(
        @NotNull
        LocalDateTime date,

        @NotNull
        LocalDateTime heureDepart,

        @NotNull
        Integer groupeId,

        @NotNull
        Integer salleId,

        @NotNull
        List<Long> juryIds
) {
}