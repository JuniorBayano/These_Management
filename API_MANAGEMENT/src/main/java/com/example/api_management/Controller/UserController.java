package com.example.api_management.Controller;

import com.example.api_management.Entities.User;
import com.example.api_management.Repositories.UserRepository;
import com.example.api_management.Service.UserResponseService;
import com.example.api_management.mapper.UserMapper;
import com.example.api_management.request.UserLoginRequest;
import com.example.api_management.request.UserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserResponseService userResponseService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserRequest userRequest, UserMapper userMapper) {
        // Vérifier les champs manquants
        List<String> missingFields = userMapper.validateRequestRegister(userRequest);
        if (!missingFields.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("errors", missingFields));
        }

        if (userRepository.findByEmail(userRequest.email()) != null) {
            return ResponseEntity.badRequest().body(Map.of("error", "User already exists change email"));
        }

        User user = userMapper.toUser(userRequest);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);

        return ResponseEntity.ok(savedUser);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserLoginRequest user, UserMapper userMapper) {
        List<String> missingFields = userMapper.validateRequestLogin(user);

        if (!missingFields.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("errors", missingFields));
        }

        User existingUser = userRepository.findByEmail(user.email());
        if (existingUser == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "User not found"));
        }
        if (!passwordEncoder.matches(user.password(), existingUser.getPassword())) {
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid password"));
        }

        // Sérialiser manuellement les informations du groupe
        Map<String, Object> response = userResponseService.createLoginResponse(existingUser);

        return ResponseEntity.ok(response);
    }



}
