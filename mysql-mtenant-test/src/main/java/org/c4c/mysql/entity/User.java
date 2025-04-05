package org.c4c.mysql.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username; // Unique username for the user

    @Column(nullable = false)
    private String password; // User's password

    @Column(nullable = false)
    private String email; // User's email address

    @Column(nullable = false)
    private String tenantId; // Identifier for the tenant to which the user belongs
}