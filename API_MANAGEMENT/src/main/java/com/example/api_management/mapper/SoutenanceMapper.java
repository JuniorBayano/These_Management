package com.example.api_management.mapper;

import com.example.api_management.Entities.*;
import com.example.api_management.Repositories.GroupeRepository;
import com.example.api_management.Repositories.SalleRepository;
import com.example.api_management.Repositories.UserRepository;
import com.example.api_management.request.SoutenanceRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SoutenanceMapper {

    private final GroupeRepository groupeRepository;
    private final SalleRepository salleRepository;
    private final UserRepository userRepository;

    public Soutenance toSoutenance(SoutenanceRequest soutenanceRequest) {
        List<String> errors = validateSoutenance(soutenanceRequest);
        if (!errors.isEmpty()) {
            throw new IllegalArgumentException(String.join(", ", errors));
        }

        Groupe groupe = groupeRepository.findById(soutenanceRequest.groupeId())
                .orElseThrow(() -> new IllegalArgumentException("Groupe non trouvé"));

        Salle salle = salleRepository.findById(soutenanceRequest.salleId())
                .orElseThrow(() -> new IllegalArgumentException("Salle non trouvée"));

        List<User> jury = userRepository.findAllById(soutenanceRequest.juryIds());
        if (jury.size() != soutenanceRequest.juryIds().size()) {
            throw new IllegalArgumentException("Certains jurys fournis n'existent pas");
        }

        return Soutenance.builder()
                .date(soutenanceRequest.date())
                .heureDepart(soutenanceRequest.heureDepart())
                .duree(Duration.ofHours(1))
                .etat(EtatSoutenance.PROGRAMMEE)
                .groupe(groupe)
                .salle(salle)
                .jury(jury)
                .build();
    }

    public List<String> validateSoutenance(SoutenanceRequest soutenanceRequest) {
        List<String> errors = new java.util.ArrayList<>();

        if (soutenanceRequest.date() == null) {
            errors.add("Le champ 'date' est requis");
        }
        if (soutenanceRequest.heureDepart() == null) {
            errors.add("Le champ 'heureDepart' est requis");
        }
        if (soutenanceRequest.groupeId() == null) {
            errors.add("Le champ 'groupeId' est requis");
        }
        if (soutenanceRequest.salleId() == null) {
            errors.add("Le champ 'salleId' est requis");
        }
        if (soutenanceRequest.juryIds() == null || soutenanceRequest.juryIds().isEmpty()) {
            errors.add("Au moins un jury doit être fourni");
        } else if (soutenanceRequest.juryIds().size() > 3) {
            errors.add("Un maximum de 3 jurys est autorisé");
        }

        return errors;
    }
}
