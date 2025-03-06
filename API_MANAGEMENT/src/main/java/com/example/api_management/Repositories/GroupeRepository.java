package com.example.api_management.Repositories;

import com.example.api_management.Entities.Groupe;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GroupeRepository extends JpaRepository<Groupe, Integer> {
    long count();
    boolean existsByNumeroGroupe(Integer numeroGroupe);

}
