package com.c4c.example.basic.core.repository;

import com.c4c.example.basic.core.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepo extends JpaRepository<User, Integer> {
  UserDetails findByUsername(String username);
}