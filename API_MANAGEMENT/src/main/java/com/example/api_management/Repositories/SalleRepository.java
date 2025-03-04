package com.example.api_management.Repositories;

import com.example.api_management.Entities.Salle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalleRepository extends JpaRepository<Salle, Integer> {
    long count();
}
