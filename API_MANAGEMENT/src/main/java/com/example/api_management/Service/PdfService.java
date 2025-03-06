package com.example.api_management.Service;

import com.example.api_management.Entities.Soutenance;
import com.example.api_management.Entities.User;
import com.example.api_management.Entities.Groupe;
import com.example.api_management.Entities.Note;
import com.example.api_management.Repositories.SoutenanceRepository;
import com.example.api_management.Repositories.GroupeRepository;
import com.example.api_management.Repositories.NoteRepository;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class PdfService {
    private final SoutenanceRepository soutenanceRepository;
    private final GroupeRepository groupeRepository;
    private final NoteRepository noteRepository;
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public PdfService(SoutenanceRepository soutenanceRepository, GroupeRepository groupeRepository, NoteRepository noteRepository) {
        this.soutenanceRepository = soutenanceRepository;
        this.groupeRepository = groupeRepository;
        this.noteRepository = noteRepository;
    }

    public ResponseEntity<ByteArrayResource> generateSoutenancesPdf() {
        try {
            List<Soutenance> soutenances = soutenanceRepository.findAll();

            Document document = new Document(PageSize.A4.rotate());
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            PdfWriter.getInstance(document, outputStream);
            document.open();

            Font titleFont = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
            Paragraph title = new Paragraph("Liste des Soutenances", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph("\n"));

            float[] columnWidths = {12f, 10f, 10f, 10f, 10f, 18f, 10f, 20f};
            PdfPTable table = new PdfPTable(columnWidths);
            table.setWidthPercentage(100);

            Font tableFont = new Font(Font.FontFamily.HELVETICA, 8);

            String[] headers = {"Date", "Numéro Groupe", "Salle", "Heure Début", "Heure Fin", "Jury", "Encadrant", "Thème"};
            for (String header : headers) {
                PdfPCell headerCell = new PdfPCell(new Phrase(header, new Font(Font.FontFamily.HELVETICA, 9, Font.BOLD)));
                headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                headerCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                table.addCell(headerCell);
            }

            for (Soutenance s : soutenances) {
                table.addCell(new PdfPCell(new Phrase(s.getDate().format(DATE_FORMATTER), tableFont)));
                table.addCell(new PdfPCell(new Phrase(s.getGroupe().getNumeroGroupe().toString(), tableFont)));
                table.addCell(new PdfPCell(new Phrase(s.getSalle().getNomSalle(), tableFont)));
                table.addCell(new PdfPCell(new Phrase(s.getHeureDepart().format(TIME_FORMATTER), tableFont)));
                table.addCell(new PdfPCell(new Phrase(s.getDateFin().format(TIME_FORMATTER), tableFont)));

                String juryNames = s.getJury().stream()
                        .map(User::getUsername)
                        .reduce((name1, name2) -> name1 + ", " + name2)
                        .orElse("N/A");
                PdfPCell juryCell = new PdfPCell(new Phrase(juryNames, tableFont));
                juryCell.setNoWrap(false);
                table.addCell(juryCell);

                String encadrantName = s.getGroupe().getEncadreur() != null ? s.getGroupe().getEncadreur() : "N/A";
                String theme = s.getGroupe().getTheme() != null ? s.getGroupe().getTheme() : "N/A";

                PdfPCell encadrantCell = new PdfPCell(new Phrase(encadrantName, tableFont));
                encadrantCell.setNoWrap(false);
                table.addCell(encadrantCell);

                PdfPCell themeCell = new PdfPCell(new Phrase(theme, tableFont));
                themeCell.setNoWrap(false);
                table.addCell(themeCell);
            }

            document.add(table);
            document.close();

            ByteArrayResource resource = new ByteArrayResource(outputStream.toByteArray());

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=soutenances.pdf")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    public byte[] generateGroupesNotesPdf() {
        Font tableFont = new Font(Font.FontFamily.HELVETICA, 8);
        try {
            List<Groupe> groupes = groupeRepository.findAll();
            Document document = new Document(PageSize.A4.rotate());
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            PdfWriter.getInstance(document, outputStream);
            document.open();

            Font titleFont = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
            Paragraph title = new Paragraph("Liste des Groupes et leurs Notes", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph("\n"));

            float[] columnWidths = {10f, 15f, 15f, 10f, 30f};
            PdfPTable table = new PdfPTable(columnWidths);
            table.setWidthPercentage(100);

            String[] headers = {"Numéro Groupe", "Thème", "Encadreur", "Note", "Membres"};
            for (String header : headers) {
                PdfPCell headerCell = new PdfPCell(new Phrase(header, new Font(Font.FontFamily.HELVETICA, 9, Font.BOLD)));
                headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                headerCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                table.addCell(headerCell);
            }

            for (Groupe groupe : groupes) {
                List<Soutenance> soutenances = soutenanceRepository.findByGroupe(groupe);
                if (soutenances.isEmpty()) {
                    table.addCell(new PdfPCell(new Phrase(String.valueOf(groupe.getNumeroGroupe()), tableFont)));
                    table.addCell(new PdfPCell(new Phrase(groupe.getTheme(), tableFont)));
                    table.addCell(new PdfPCell(new Phrase(groupe.getEncadreur(), tableFont)));
                    table.addCell(new PdfPCell(new Phrase("Pas de notes", tableFont)));
                    table.addCell(new PdfPCell(new Phrase("N/A", tableFont)));
                    continue;
                }

                double moyenneNote = noteRepository.findBySoutenanceIn(soutenances)
                        .stream()
                        .mapToDouble(Note::getNote)
                        .average()
                        .orElse(0.0);

                table.addCell(new PdfPCell(new Phrase(String.valueOf(groupe.getNumeroGroupe()), tableFont)));
                table.addCell(new PdfPCell(new Phrase(groupe.getTheme(), tableFont)));
                table.addCell(new PdfPCell(new Phrase(groupe.getEncadreur(), tableFont)));
                table.addCell(new PdfPCell(new Phrase(String.format("%.2f", moyenneNote), tableFont)));

                String membres = String.join(", ", groupe.getStudents().stream().map(User::getUsername).toList());
                table.addCell(new PdfPCell(new Phrase(membres, tableFont)));
            }

            document.add(table);
            document.close();

            return outputStream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return new byte[0];
        }
    }


}
