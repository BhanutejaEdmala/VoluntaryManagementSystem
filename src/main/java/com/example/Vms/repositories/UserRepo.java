package com.example.Vms.repositories;

import com.example.Vms.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User,Integer> {
    Optional<User> findByName(String username);
    boolean existsByName(String username);
}
