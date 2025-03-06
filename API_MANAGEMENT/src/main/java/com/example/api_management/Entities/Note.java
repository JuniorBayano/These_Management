package com.example.api_management.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="notes")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "soutenance_id")
    private Soutenance soutenance;

    @Column(nullable = false)
    private Double note;

    @Column(nullable = true)
    private String commentaire;
}
