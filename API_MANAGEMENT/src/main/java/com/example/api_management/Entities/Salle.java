package com.example.api_management.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name="salles")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Salle {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false,unique = true)
    private String nomSalle;
    @Column(nullable = false)
    @OneToMany(mappedBy = "salle",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Soutenance> soutenanceList;
    @Column(nullable = false)
    private Integer nbrePlaces;
}
