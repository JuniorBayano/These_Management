package com.example.api_management.Controller;

import com.example.api_management.Repositories.UserRepository;
import com.example.api_management.Service.JuryActionService;
import com.example.api_management.mapper.JuryMapper;
import com.example.api_management.request.JuryRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private final JuryActionService juryActionService;

    public AdminController(JuryActionService juryActionService, UserRepository userRepository, JuryMapper juryMapper) {
        this.juryActionService = juryActionService;
    }

    @PostMapping("/addJury")
    public ResponseEntity<?> addJury(@RequestBody JuryRequest juryRequest) {
        return juryActionService.AddJury(juryRequest);
    }
}
