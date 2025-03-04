package com.example.api_management.mapper;

import com.example.api_management.Entities.Groupe;
import com.example.api_management.request.UpdateGroupeRequest;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UpdateGroupeMapper {

    public List<String> validateUpdateGroupeRequest(UpdateGroupeRequest updateRequest) {
        List<String> missingFields = new ArrayList<>();

        if (updateRequest.nomEncadreur() == null || updateRequest.nomEncadreur().isEmpty()) {
            missingFields.add("Le champ 'nomEncadreur' est requis");
        }
        if (updateRequest.theme() != null && updateRequest.theme().length() > 100) {
            missingFields.add("Le thème ne doit pas dépasser 100 caractères");
        }

        return missingFields;
    }

    public Groupe toGroupe(UpdateGroupeRequest updateRequest, Integer numeroGroupe) {
        Groupe groupe = new Groupe();
        groupe.setNumeroGroupe(numeroGroupe);
        groupe.setEncadreur(updateRequest.nomEncadreur());
        groupe.setTheme(updateRequest.theme());
        return groupe;
    }
}