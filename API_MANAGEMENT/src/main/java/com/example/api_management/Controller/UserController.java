package com.example.api_management.Controller;

import com.example.api_management.Entities.User;
import com.example.api_management.Repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
   final UserRepository userRepository;

   public UserController(UserRepository userRepository) {
       this.userRepository = userRepository;
   }
   @GetMapping("/getallusers")
    public ResponseEntity<List<User>> getAllUser(){
        return new ResponseEntity<>(userRepository.findAll(),HttpStatus.OK);
    }

    @PostMapping("/createUser")
    public ResponseEntity<User> createUser(@RequestBody User user){
       User userCreated=userRepository.save(user);
       return new ResponseEntity<>(userCreated,HttpStatus.CREATED);
    }

}
