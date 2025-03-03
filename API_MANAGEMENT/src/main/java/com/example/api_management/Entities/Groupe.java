package com.example.api_management.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;
@Builder
@Entity
@Table(name="groupes")
@NoArgsConstructor
@AllArgsConstructor
public class Groupe {
    @Id
    @Column(name = "Numero_Groupe")
    private Integer numeroGroupe;
    @Column(nullable = false)
    private String encadreur;
    @Column(nullable = false)
    private String theme;
    @OneToMany(mappedBy = "groupe")
    private List<User> students;
    @OneToOne
    @JoinColumn(name = "soutenance_id")
    private Soutenance soutenance;
    public Integer getNumeroGroupe() {
        return numeroGroupe;
    }

    public void setNumeroGroupe(Integer numeroGroupe) {
        this.numeroGroupe = numeroGroupe;
    }

    public String getEncadreur() {
        return encadreur;
    }

    public void setEncadreur(String encadreur) {
        this.encadreur = encadreur;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public List<User> getStudents() {
        return students;
    }

    public void setStudents(List<User> students) {
        this.students = students;
    }

    public Soutenance getSoutenance() {
        return soutenance;
    }

    public void setSoutenance(Soutenance soutenance) {
        this.soutenance = soutenance;
    }
}
