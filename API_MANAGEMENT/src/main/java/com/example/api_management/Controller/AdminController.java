package com.example.api_management.Controller;

import com.example.api_management.Repositories.UserRepository;
import com.example.api_management.Service.AdminActionService;
import com.example.api_management.mapper.JuryMapper;
import com.example.api_management.request.GroupeRequest;
import com.example.api_management.request.JuryRequest;
import com.example.api_management.request.SalleRequest;
import com.example.api_management.request.UpdateGroupeRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private final AdminActionService adminActionService;

    public AdminController(AdminActionService adminActionService, UserRepository userRepository, JuryMapper juryMapper) {
        this.adminActionService = adminActionService;
    }
    @PostMapping("/addJury")
    public ResponseEntity<?> addJury(@RequestBody JuryRequest juryRequest) {
        return adminActionService.AddJury(juryRequest);
    }
    @GetMapping("/getAllJury")
    public ResponseEntity<?> getAllJury() {
     return adminActionService.GetAllJury();
    }
    @DeleteMapping("/deleteJury/{id}")
    public ResponseEntity<?> deleteJury(@PathVariable Long id) {
        return adminActionService.DeletedJury(id);
    }
    @GetMapping("/getNumberOfJury")
    public ResponseEntity<?> GetAllJury() {
        return adminActionService.getNumberOfJury();
    }
    @GetMapping("/getNumberOfStudent")
    public ResponseEntity<?> GetAllStudent() {
        return adminActionService.getNumberOfStudent();
    }
    @GetMapping("/getNumberOfGroupe")
    public ResponseEntity<?> GetAllGroupe() {
        return adminActionService.getNumberOfGroupes();
    }
    @GetMapping("/getNumberOfSalle")
    public ResponseEntity<?> GetAllSalle() {
        return adminActionService.getNumberOfSalle();
    }
    @PostMapping("/addGroupe")
    public ResponseEntity<?> addGroupe(@RequestBody GroupeRequest groupeRequest) {
        return adminActionService.AddGroupe(groupeRequest);
    }
    @DeleteMapping("/deleteGroupe/{id}")
    public ResponseEntity<?> deleteGroupe(@PathVariable int id) {
        return adminActionService.deleteGroupe(id);
    }
    @PutMapping("/updateGroupe/{numeroGroupe}")
    public ResponseEntity<?> updateGroupe(@PathVariable int numeroGroupe, @RequestBody UpdateGroupeRequest updateGroupeRequest) {
        return adminActionService.updateGroupe(numeroGroupe,updateGroupeRequest);
    }
    @PostMapping("/addSalle")
    public ResponseEntity<?> addSalle(@RequestBody SalleRequest salleRequest) {
        return adminActionService.AddSalle(salleRequest);
    }
}
