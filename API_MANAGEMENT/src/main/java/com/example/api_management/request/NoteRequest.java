package com.example.api_management.request;

public record NoteRequest(Long soutenanceId, Long juryId, Double note, String commentaire) { }
