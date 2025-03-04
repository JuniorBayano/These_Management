package com.example.api_management.Controller;

import com.example.api_management.Entities.User;
import com.example.api_management.Service.UserActionService;
import com.example.api_management.request.UserLoginRequest;
import com.example.api_management.request.UserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {
    private final UserActionService userActionService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserRequest userRequest) {
        return userActionService.registerUser(userRequest);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserLoginRequest userRequest) {
        return userActionService.loginUser(userRequest);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser() {
        return userActionService.logoutUser();
    }
}
