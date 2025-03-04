package com.example.api_management.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "soutenances")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Soutenance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime date;

    @Column(nullable = false)
    private LocalDateTime heureDepart;

    @Column(nullable = false)
    private Duration duree;

    @Column(nullable = false)
    private LocalDateTime dateFin;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EtatSoutenance etat;

    @OneToMany(mappedBy = "role")
    private List<User> jury;

    @OneToOne
    @JoinColumn(name = "Numero_Groupe")
    private Groupe groupe;

    @ManyToOne
    @JoinColumn(name = "Salle_Numero")
    private Salle salle;

    @OneToOne(mappedBy = "soutenance", cascade = CascadeType.ALL)
    private Note note;

    @PrePersist
    @PreUpdate
    private void calculateDateFin() {
        if (heureDepart != null && duree != null) {
            this.dateFin = heureDepart.plus(duree);
        }
    }
}