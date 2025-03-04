package com.example.api_management.Service;

import com.example.api_management.Entities.Role;
import com.example.api_management.Entities.User;
import com.example.api_management.Repositories.UserRepository;
import com.example.api_management.mapper.JuryMapper;
import com.example.api_management.request.JuryRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JuryActionService {
    private final JuryMapper juryMapper;
    private   final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public ResponseEntity<?> AddJury(JuryRequest juryRequest) {
        List<String> errors = juryMapper.validateRequestAddUser(juryRequest);
        if (!errors.isEmpty()) {
            return ResponseEntity.badRequest().body(errors);
        }
        User jury = new User();
        jury.setUsername(juryRequest.username());
        jury.setPassword(juryRequest.password());
        jury.setEmail(juryRequest.email());
        jury.setMatricule(juryRequest.matricule());
        jury.setRole(Role.valueOf("Jury"));
        jury.setPassword(passwordEncoder.encode(jury.getPassword()));

        userRepository.save(jury);

        return ResponseEntity.ok("Jury ajouté avec succès !");
    }
}
