package com.example.api_management.Controller;

import com.example.api_management.Repositories.SoutenanceRepository;
import com.example.api_management.Repositories.UserRepository;
import com.example.api_management.Service.NoteService;
import com.example.api_management.request.NoteRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/jury")
public class JuryController {

    private final NoteService noteService;
    private final SoutenanceRepository soutenanceRepository;
    private final UserRepository userRepository;

    public JuryController(NoteService noteService, SoutenanceRepository soutenanceRepository, UserRepository userRepository) {
        this.noteService = noteService;
        this.soutenanceRepository = soutenanceRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/attribuerNote")
    public ResponseEntity<?> attribuerNote(@RequestBody NoteRequest noteRequest) {
        return noteService.attribuerNote(noteRequest);
    }
}
