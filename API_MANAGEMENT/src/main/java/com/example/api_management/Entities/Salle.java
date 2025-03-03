package com.example.api_management.Entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="salles")
public class Salle {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Integer numeroSalle;
    @Column(nullable = false)
    @OneToMany(mappedBy = "salle")
    private List<Soutenance> soutenanceList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumeroSalle() {
        return numeroSalle;
    }

    public void setNumeroSalle(Integer numeroSalle) {
        this.numeroSalle = numeroSalle;
    }

    public List<Soutenance> getSoutenanceList() {
        return soutenanceList;
    }

    public void setSoutenanceList(List<Soutenance> soutenanceList) {
        this.soutenanceList = soutenanceList;
    }
}
