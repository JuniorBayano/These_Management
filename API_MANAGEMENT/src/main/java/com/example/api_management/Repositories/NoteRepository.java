package com.example.api_management.Repositories;

import com.example.api_management.Entities.Note;
import com.example.api_management.Entities.Soutenance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
    List<Note> findBySoutenance(Soutenance soutenance);
    List<Note> findBySoutenanceIn(List<Soutenance> soutenances);
}
