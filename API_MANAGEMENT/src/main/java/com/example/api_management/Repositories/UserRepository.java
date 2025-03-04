package com.example.api_management.Repositories;

import com.example.api_management.Entities.Groupe;
import com.example.api_management.Entities.Role;
import com.example.api_management.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {
      User findByEmail(String email);
      List<User> findByRole(Role role);
      @Query("SELECT COUNT(u) FROM User u WHERE u.groupe = :groupe AND u.role = 'Student'")
      int countStudentsInGroup(@Param("groupe") Groupe groupe);
      @Query("SELECT COUNT(u) FROM User u WHERE u.role = :role")
      int countByRole(@Param("role") Role role);

}
