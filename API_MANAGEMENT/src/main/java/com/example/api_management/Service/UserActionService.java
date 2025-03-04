package com.example.api_management.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.api_management.Entities.Groupe;
import com.example.api_management.Entities.Role;
import com.example.api_management.Entities.User;
import com.example.api_management.Repositories.UserRepository;
import com.example.api_management.mapper.UserMapper;
import com.example.api_management.request.UserLoginRequest;
import com.example.api_management.request.UserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserActionService {
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private long jwtExpiration;

    public ResponseEntity<?> registerUser(UserRequest userRequest) {
        List<String> missingFields = userMapper.validateRequestRegister(userRequest);
        if (!missingFields.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("errors", missingFields));
        }

        if (userRepository.findByEmail(userRequest.email()) != null) {
            return ResponseEntity.badRequest().body(Map.of("error", "User already exists, change email"));
        }

        User user = userMapper.toUser(userRequest);

        if (user.getRole() == Role.Student) {
            Groupe groupe = user.getGroupe();

            int studentCount = userRepository.countStudentsInGroup(groupe);

            if (studentCount >= 2) {
                return ResponseEntity.badRequest().body(Map.of("error", "Le groupe est déjà complet (2 étudiants maximum)"));
            }
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User savedUser = userRepository.save(user);

        String token = generateToken(savedUser);

        Map<String, Object> response = new HashMap<>();
        response.put("user", savedUser);
        response.put("token", token);
        response.put("message", "User registered successfully");

        return ResponseEntity.ok(response);
    }

    public ResponseEntity<?> loginUser(UserLoginRequest userRequest) {
        List<String> missingFields = userMapper.validateRequestLogin(userRequest);
        if (!missingFields.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("errors", missingFields));
        }

        User existingUser = userRepository.findByEmail(userRequest.email());
        if (existingUser == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "User not found"));
        }

        if (!passwordEncoder.matches(userRequest.password(), existingUser.getPassword())) {
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid password"));
        }

        String token = generateToken(existingUser);
        Map<String, Object> response = new HashMap<>();
        response.put("user", existingUser);
        response.put("token", token);
        response.put("message", "Login successful");

        return ResponseEntity.ok(response);
    }

    public ResponseEntity<?>  logoutUser() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Logout successful");
        return ResponseEntity.ok(response);
    }



    private String generateToken(User user) {
        Algorithm algorithm = Algorithm.HMAC256(jwtSecret);

        return JWT.create()
                .withSubject(user.getEmail())
                .withClaim("userId", user.getId())
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + jwtExpiration))
                .sign(algorithm);
    }

}