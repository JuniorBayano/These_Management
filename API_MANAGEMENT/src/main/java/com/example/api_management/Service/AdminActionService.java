package com.example.api_management.Service;

import com.example.api_management.Entities.Groupe;
import com.example.api_management.Entities.Role;
import com.example.api_management.Entities.Salle;
import com.example.api_management.Entities.User;
import com.example.api_management.Repositories.GroupeRepository;
import com.example.api_management.Repositories.SalleRepository;
import com.example.api_management.Repositories.UserRepository;
import com.example.api_management.DTo.JuryDTO;
import com.example.api_management.mapper.GroupeMapper;
import com.example.api_management.mapper.JuryMapper;
import com.example.api_management.mapper.SalleMapper;
import com.example.api_management.mapper.UpdateGroupeMapper;
import com.example.api_management.request.GroupeRequest;
import com.example.api_management.request.JuryRequest;
import com.example.api_management.request.SalleRequest;
import com.example.api_management.request.UpdateGroupeRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminActionService {
    private final JuryMapper juryMapper;
    private   final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final GroupeRepository groupeRepository;
    private final SalleRepository salleRepository;
    private final GroupeMapper groupeMapper;
    private final UpdateGroupeMapper updateGroupeMapper;
    private final SalleMapper salleMapper;

    public ResponseEntity<?> AddJury(JuryRequest juryRequest) {
        List<String> errors = juryMapper.validateRequestAddUser(juryRequest);
        if (!errors.isEmpty()) {
            return ResponseEntity.badRequest().body(errors);
        }
        User jury = new User();
        jury.setUsername(juryRequest.username());
        jury.setPassword(juryRequest.password());
        jury.setEmail(juryRequest.email());
        jury.setMatricule(juryRequest.matricule());
        jury.setRole(Role.valueOf("Jury"));
        jury.setPassword(passwordEncoder.encode(jury.getPassword()));

        userRepository.save(jury);

        return ResponseEntity.ok("Jury ajoute avec succes !");
    }
    public ResponseEntity<?> GetAllJury() {
        List<User> juryList = userRepository.findByRole(Role.Jury);
        List<JuryDTO> juryDTOList = juryList.stream()
                                            .map(juryMapper::toJury)
                .collect(Collectors.toList());
        return ResponseEntity.ok(juryDTOList);
    }
    public ResponseEntity<?> DeletedJury(Long id){
        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isEmpty()) {
            return ResponseEntity.badRequest().body("Utilisateur non trouve");
        }

        User user = optionalUser.get();

        if (user.getRole() != Role.Jury) {
            return ResponseEntity.badRequest().body("L'utilisateur n'est pas un jury");
        }

        userRepository.deleteById(id);
        return ResponseEntity.ok("Jury supprime avec succes");
    }
    public ResponseEntity<?> getNumberOfJury() {
        int numberOfJury = userRepository.countByRole(Role.Jury);
        return ResponseEntity.ok(Map.of("numberOfJury", numberOfJury));
    }
    public ResponseEntity<?> getNumberOfStudent() {
        int numberOfStudent = userRepository.countByRole(Role.Student);
        return ResponseEntity.ok(Map.of("numberOfStudent", numberOfStudent));
    }
    public ResponseEntity<?> getNumberOfGroupes() {
        long numberOfGroupes = groupeRepository.count();
        return ResponseEntity.ok(Map.of("numberOfGroupes", numberOfGroupes));
    }
    public ResponseEntity<?> getNumberOfSalle(){
        long numberOfSalle=salleRepository.count();
        return ResponseEntity.ok(Map.of("numberOfSalle", numberOfSalle));
    }
    public ResponseEntity<?> AddGroupe(GroupeRequest groupeRequest) {
        List<String> errors= groupeMapper.validateAddGroup(groupeRequest);
        if (groupeRepository.existsByNumeroGroupe(groupeRequest.numeroDeGroupe())) {
            return ResponseEntity.badRequest().body("Il y a déjà un groupe avec le numero de groupe '" + groupeRequest.numeroDeGroupe() + "'. Veuillez le changer.");
        }
        if (!errors.isEmpty()) {
            return ResponseEntity.badRequest().body(errors);
        }
        Groupe groupe = groupeMapper.toGroupe(groupeRequest);
        groupeRepository.save(groupe);
        return ResponseEntity.ok("Groupe ajoute avec succes");
    }
    public ResponseEntity<?> deleteGroupe(Integer numeroGroupe) {
        Optional<Groupe> optionalGroupe = groupeRepository.findById(numeroGroupe);

        if (optionalGroupe.isEmpty()) {
            return ResponseEntity.badRequest().body("Groupe non trouve");
        }

        groupeRepository.deleteById(numeroGroupe);
        return ResponseEntity.ok("Groupe supprime avec succes");
    }

    public ResponseEntity<?> updateGroupe(Integer numeroGroupe, UpdateGroupeRequest updateRequest) {
        List<String> errors = updateGroupeMapper.validateUpdateGroupeRequest(updateRequest);
        if (!errors.isEmpty()) {
            return ResponseEntity.badRequest().body(errors);
        }

        Optional<Groupe> optionalGroupe = groupeRepository.findById(numeroGroupe);
        if (optionalGroupe.isEmpty()) {
            return ResponseEntity.badRequest().body("Groupe non trouve");
        }

        Groupe groupe = optionalGroupe.get();
        groupe.setEncadreur(updateRequest.nomEncadreur());
        groupe.setTheme(updateRequest.theme());

        groupeRepository.save(groupe);
        return ResponseEntity.ok("Groupe mis à jour avec succes");
    }
    public ResponseEntity<?> AddSalle(SalleRequest salleRequest) {
        List<String> errors = salleMapper.validateAddSalle(salleRequest);
        if (!errors.isEmpty()) {
            return ResponseEntity.badRequest().body(errors);
        }
        Salle salle = salleMapper.toSalle(salleRequest);
        salleRepository.save(salle);
        return ResponseEntity.ok("Salle ajoute avec succes !");
    }

}
