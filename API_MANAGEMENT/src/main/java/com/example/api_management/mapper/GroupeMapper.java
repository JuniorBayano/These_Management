package com.example.api_management.mapper;

import com.example.api_management.Entities.Groupe;
import com.example.api_management.request.GroupeRequest;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class GroupeMapper {
    public Groupe toGroupe(GroupeRequest groupeRequest) {
        if(groupeRequest == null){
            throw new  IllegalArgumentException("la requete est null");
        }
        return Groupe.builder()
                .numeroGroupe(groupeRequest.numeroDeGroupe())
                .encadreur(groupeRequest.nomDeEncadreur())
                .theme(groupeRequest.theme())
                .build();
    }
    public List<String> validateAddGroup(GroupeRequest groupeRequest) {
        List<String> missingFields = new ArrayList<>();

        if (groupeRequest.numeroDeGroupe() == null) {
            missingFields.add("Le champ 'numeroDeGroupe' est requis");
        }
        if (groupeRequest.nomDeEncadreur() == null || groupeRequest.nomDeEncadreur().isEmpty()) {
            missingFields.add("Le champ 'nomDeEncadreur' est requis");
        }
        if (groupeRequest.theme() == null || groupeRequest.theme().isEmpty()) {
            missingFields.add("Le champ 'theme' est requis");
        }

        return missingFields;
    }
}
