package com.example.api_management.Entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Builder
@Entity
@Table(name="groupes")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Groupe {
    @Id
    @Column(name = "Numero_Groupe",unique = true)
    private Integer numeroGroupe;
    @Column(nullable = false)
    private String encadreur;
    @Column(nullable = false)
    private String theme;
    @OneToMany(mappedBy = "groupe",cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<User> students;
    @OneToOne
    @JoinColumn(name = "soutenance_id")
    private Soutenance soutenance;

}
