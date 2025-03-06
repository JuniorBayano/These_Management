package com.example.api_management.Service;

import com.example.api_management.Entities.Note;
import com.example.api_management.Entities.Role;
import com.example.api_management.Entities.Soutenance;
import com.example.api_management.Entities.User;
import com.example.api_management.Repositories.NoteRepository;
import com.example.api_management.Repositories.SoutenanceRepository;
import com.example.api_management.Repositories.UserRepository;
import com.example.api_management.mapper.NoteRequestMapper;
import com.example.api_management.request.NoteRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    private final NoteRepository noteRepository;
    private final SoutenanceRepository soutenanceRepository;
    private final UserRepository userRepository;
    private final NoteRequestMapper noteRequestMapper;

    public NoteService(NoteRepository noteRepository, SoutenanceRepository soutenanceRepository,
                       UserRepository userRepository, NoteRequestMapper noteRequestMapper) {
        this.noteRepository = noteRepository;
        this.soutenanceRepository = soutenanceRepository;
        this.userRepository = userRepository;
        this.noteRequestMapper = noteRequestMapper;
    }

    public ResponseEntity<?> attribuerNote(NoteRequest noteRequest) {
        List<String> erreurs = noteRequestMapper.validateNoteRequest(noteRequest);
        if (!erreurs.isEmpty()) {
            return ResponseEntity.badRequest().body(erreurs);
        }

        Soutenance soutenance = soutenanceRepository.findById(noteRequest.soutenanceId())
                .orElseThrow(() -> new RuntimeException("Soutenance non trouvee"));

        User jury = userRepository.findById(noteRequest.juryId())
                .orElseThrow(() -> new RuntimeException("Jury non trouve"));

        if (jury.getRole() != Role.Jury) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("L'utilisateur n'est pas un jury");
        }

        Note note = noteRequestMapper.toNote(noteRequest, soutenance);

        noteRepository.save(note);

        return ResponseEntity.ok("Note attribuee avec succès");
    }
}
