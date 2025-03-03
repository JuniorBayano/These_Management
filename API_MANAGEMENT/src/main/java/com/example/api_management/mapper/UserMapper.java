package com.example.api_management.mapper;

import com.example.api_management.Entities.Groupe;
import com.example.api_management.Entities.Role;
import com.example.api_management.Entities.User;
import com.example.api_management.request.UserLoginRequest;
import com.example.api_management.request.UserRequest;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserMapper {
    public User toUser(UserRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("La requête est nulle");
        }

        return User.builder()
                .username(request.username())
                .password(request.password())
                .email(request.email())
                .matricule(request.matricule())
                .groupe(Groupe.builder().numeroGroupe(request.groupe()).build())
                .role(Role.valueOf(request.role()))
                .build();
    }

    public List<String> validateRequestRegister(UserRequest request) {
        List<String> missingFields = new ArrayList<>();

        if (request.username() == null || request.username().isEmpty()) {
            missingFields.add("Le champ 'name' est requis");
        }
        if (request.password() == null || request.password().isEmpty()) {
            missingFields.add("Le champ 'password' est requis");
        }
        if (request.email() == null || request.email().isEmpty()) {
            missingFields.add("Le champ 'email' est requis");
        }
        if (request.matricule() == null || request.matricule().isEmpty()) {
            missingFields.add("Le champ 'matricule' est requis");
        }
        if (request.groupe() == null) {
            missingFields.add("Le champ 'groupe' est requis");
        }
        if (request.role() == null || request.role().isEmpty()) {
            missingFields.add("Le champ 'role' est requis");
        }

        return missingFields;
    }

    public List<String> validateRequestLogin(UserLoginRequest requestRegisterRequest) {
        List<String> missingFields = new ArrayList<>();
        if (requestRegisterRequest.email() == null || requestRegisterRequest.email().isEmpty()) {
            missingFields.add("Le champ 'email' est requis");
        }
        if (requestRegisterRequest.password() == null || requestRegisterRequest.password().isEmpty()) {
            missingFields.add("Le champ 'password' est requis");
        }
        return missingFields;
    }
}
