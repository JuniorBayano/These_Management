package com.example.api_management.Entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="soutenances")
public class Soutenance {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime date;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public EtatSoutenance getEtat() {
        return etat;
    }

    public void setEtat(EtatSoutenance etat) {
        this.etat = etat;
    }

    public List<User> getJury() {
        return jury;
    }

    public void setJury(List<User> jury) {
        this.jury = jury;
    }

    public Groupe getGroupe() {
        return groupe;
    }

    public void setGroupe(Groupe groupe) {
        this.groupe = groupe;
    }

    public Salle getSalle() {
        return salle;
    }

    public void setSalle(Salle salle) {
        this.salle = salle;
    }

    public Note getNote() {
        return note;
    }

    public void setNote(Note note) {
        this.note = note;
    }
}
