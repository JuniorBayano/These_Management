package com.example.api_management.mapper;

import com.example.api_management.Entities.Groupe;
import com.example.api_management.Entities.Role;
import com.example.api_management.Entities.User;
import com.example.api_management.request.UserRequest;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public User topUser(UserRequest request){
        if(request == null){
        }
        assert request != null;
        return User.builder()
                .username(request.name())
                .password(request.password())
                .email(request.email())
                .matricule(request.matricule())
                .groupe(Groupe.builder().numeroGroupe(request.numerogroupe()).build())
                .role(Role.valueOf(request.role()))
                .build();
    }
    public User fromUser(User user){
        if (user==null){
            return null;
        }
        return new User(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                user.getMatricule(),
                user.getGroupe(),
                user.getRole()
        );
    }
}
