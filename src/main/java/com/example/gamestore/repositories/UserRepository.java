package com.example.gamestore.repositories;

import com.example.gamestore.entities.userEntities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsByEmail(String email);

    User findByEmailAndPassword(String email, String password);
}
