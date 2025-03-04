package com.example.api_management.DTo;

import com.example.api_management.Entities.Role;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JuryDTO{
    private Long id;
    private String username;
    private String email;
    private String matricule;
}
