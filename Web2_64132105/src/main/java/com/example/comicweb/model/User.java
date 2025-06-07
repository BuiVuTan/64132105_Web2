package com.example.comicweb.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "full_name")
    private String fullName;
    
    @Column(nullable = false)
    private String role = "USER"; // USER, ADMIN
    
    private String avatar;
    
    @Column(nullable = false)
    private boolean active = true;

    @OneToMany(mappedBy = "uploader", cascade = CascadeType.ALL)
    private Set<Comic> comics = new HashSet<>();
} 