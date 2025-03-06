package com.example.api_management.Controller;

import com.example.api_management.Repositories.SoutenanceRepository;
import com.example.api_management.Repositories.UserRepository;
import com.example.api_management.Service.AdminActionService;
import com.example.api_management.Service.PdfService;
import com.example.api_management.mapper.JuryMapper;
import com.example.api_management.request.*;
import jakarta.validation.Valid;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private final AdminActionService adminActionService;
    private final PdfService pdfService;
    private final SoutenanceRepository soutenanceRepository;

    public AdminController(AdminActionService adminActionService, PdfService pdfService,SoutenanceRepository soutenanceRepository) {
        this.adminActionService = adminActionService;
        this.pdfService = pdfService;
        this.soutenanceRepository = soutenanceRepository;
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
    @PostMapping("/programSoutenance")
    public ResponseEntity<?> programSoutenance(@Valid @RequestBody SoutenanceRequest soutenanceRequest) {
        return adminActionService.submitSoutenance(soutenanceRequest);
    }
    @GetMapping("/exportSoutenancesPdf")
    public ResponseEntity<ByteArrayResource> exportSoutenancesPdf() {
        return pdfService.generateSoutenancesPdf();
    }
    @GetMapping("/etat")
    public List<?> getSoutenancesEtat() {
        return soutenanceRepository.findAll().stream()
                .map(s -> Map.of(
                        "numeroGroupe", s.getGroupe().getNumeroGroupe(),
                        "etat", s.getEtat()
                ))
                .collect(Collectors.toList());
    }

}
