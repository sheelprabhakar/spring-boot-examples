package com.c4c.example.basic.core.service;


import com.c4c.example.basic.core.entity.User;
import com.c4c.example.basic.core.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service // Marks this class as a service component for dependency injection
public class UserService implements UserDetailsService {

  @Autowired
  private UserRepo userRepo; // Injects the UserRepo for accessing user data

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    // Retrieves user details by username from the database
    return userRepo.findByUsername(username);
  }

  public String create(String username, String password) {
    // Encodes the password and creates a new User object
    User user = User.builder()
        .username(username)
        .password(new BCryptPasswordEncoder().encode(password)) // Encrypts the password
        .authorities("student") // Assigns default authority
        .build();

    // Saves the new user to the database
    userRepo.save(user);

    return "Create Successfully !"; // Returns a success message
  }
}