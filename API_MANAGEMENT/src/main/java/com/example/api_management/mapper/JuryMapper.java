package com.example.api_management.mapper;

import com.example.api_management.Entities.User;
import com.example.api_management.DTo.JuryDTO;
import com.example.api_management.request.JuryRequest;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class JuryMapper {

    private static final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

    public JuryDTO toJury(User user) {
        if (user == null) {
            throw new IllegalArgumentException("La requête est nulle");
        }

        return JuryDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .matricule(user.getMatricule())
                .build();
    }

    public List<String> validateRequestAddUser(JuryRequest juryRequest){
        List <String> missingFields = new ArrayList<>();

        if(juryRequest.username() == null || juryRequest.username().isEmpty()){
            missingFields.add("Le champ 'username' est requis");
        }
        if(juryRequest.password() == null || juryRequest.username().isEmpty()){
            missingFields.add("Le champ 'password' est requis");
        }
        if (juryRequest.email() == null || juryRequest.email().isEmpty()) {
            missingFields.add("Le champ 'email' est requis");
        } else {
            if (!isValidEmail(juryRequest.email())) {
                missingFields.add("L'email n'est pas valide");
            }
        }
        if(juryRequest.matricule() == null || juryRequest.username().isEmpty()){
            missingFields.add("Le champ 'matricule' est requis");
        }
        return missingFields;
    }


    private boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
