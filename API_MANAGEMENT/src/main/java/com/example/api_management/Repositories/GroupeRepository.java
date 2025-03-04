package com.example.api_management.Repositories;

import com.example.api_management.Entities.Groupe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupeRepository extends JpaRepository<Groupe, Integer> {
    long count();
    boolean existsByNumeroGroupe(Integer numeroGroupe);
}
