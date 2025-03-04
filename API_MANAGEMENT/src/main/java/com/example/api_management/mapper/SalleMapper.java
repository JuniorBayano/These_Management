package com.example.api_management.mapper;

import com.example.api_management.Entities.Salle;
import com.example.api_management.request.SalleRequest;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SalleMapper {
    public Salle toSalle(SalleRequest salleRequest) {
        if (salleRequest == null) {
            throw new IllegalArgumentException("La requete est null");
        }
        return Salle.builder()
                .nomSalle(salleRequest.nomSalle())
                .nbrePlaces(salleRequest.nbrePlaces())
                .build();
    }
    public List<String> validateAddSalle(SalleRequest salleRequest) {
        List<String> errors = new ArrayList<>();
        if(salleRequest.nomSalle() == null || salleRequest.nomSalle().isEmpty()) {
            errors.add("Le champ 'nomSalle' est requis");
        }
        if (salleRequest.nbrePlaces() ==null){
            errors.add("Le champ 'nbrePlaces' est requis");
        }
        return errors;
    }
}
