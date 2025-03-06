package com.example.api_management.Controller;

import com.example.api_management.Service.PdfService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/groupes")
public class GroupeController {

    private final PdfService pdfService;

    public GroupeController(PdfService pdfService) {
        this.pdfService = pdfService;
    }

    @GetMapping("/export-pdf")
    public ResponseEntity<byte[]> exportGroupesPdf() {
        byte[] pdfBytes = pdfService.generateGroupesNotesPdf();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=groupes_notes.pdf");
        headers.add("Content-Type", "application/pdf");

        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }
}
