package com.example.api_management.mapper;

import com.example.api_management.Entities.Note;
import com.example.api_management.Entities.Soutenance;
import com.example.api_management.request.NoteRequest;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class NoteRequestMapper {

    public List<String> validateNoteRequest(NoteRequest noteRequest) {
        List<String> missingFields = new ArrayList<>();

        if (noteRequest.soutenanceId() == null) {
            missingFields.add("Le champ 'soutenanceId' est requis");
        }
        if (noteRequest.juryId() == null) {
            missingFields.add("Le champ 'juryId' est requis");
        }
        if (noteRequest.note() == null) {
            missingFields.add("Le champ 'note' est requis");
        } else if (noteRequest.note() < 0 || noteRequest.note() > 20) {
            missingFields.add("La note doit être comprise entre 0 et 20");
        }
        if (noteRequest.commentaire() != null && noteRequest.commentaire().length() > 255) {
            missingFields.add("Le commentaire ne doit pas dépasser 255 caractères");
        }

        return missingFields;
    }

    public Note toNote(NoteRequest noteRequest, Soutenance soutenance) {
        Note note = new Note();
        note.setSoutenance(soutenance);
        note.setNote(noteRequest.note());
        note.setCommentaire(noteRequest.commentaire());
        return note;
    }
}
