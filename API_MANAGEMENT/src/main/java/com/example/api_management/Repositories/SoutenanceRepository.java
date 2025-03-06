package com.example.api_management.Repositories;

import com.example.api_management.Entities.Groupe;
import com.example.api_management.Entities.Soutenance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SoutenanceRepository extends JpaRepository<Soutenance, Long> {
    List<Soutenance> findByGroupe(Groupe groupe);
}
