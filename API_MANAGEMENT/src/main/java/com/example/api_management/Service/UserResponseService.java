package com.example.api_management.Service;

import com.example.api_management.Entities.User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserResponseService {

    public Map<String, Object> createLoginResponse(User existingUser) {
        return Map.of(
                "message", "Login successful",
                "user", Map.of(
                        "id", existingUser.getId(),
                        "username", existingUser.getUsername(),
                        "email", existingUser.getEmail(),
                        "matricule", existingUser.getMatricule(),
                        "role", existingUser.getRole().name(),
                        "groupe", existingUser.getGroupe() != null ? Map.of(
                                "numeroGroupe", existingUser.getGroupe().getNumeroGroupe(),
                                "encadreur", existingUser.getGroupe().getEncadreur(),
                                "theme", existingUser.getGroupe().getTheme()
                        ) : null
                )
        );
    }
}
