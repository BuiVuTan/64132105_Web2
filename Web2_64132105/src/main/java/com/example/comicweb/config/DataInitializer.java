package com.example.comicweb.config;

import com.example.comicweb.model.User;
import com.example.comicweb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        // Create admin if not exists
        if (!userRepository.existsByUsername("admin")) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123")); // Default password: admin123
            admin.setEmail("admin@example.com");
            admin.setRole("ROLE_ADMIN");
            userRepository.save(admin);
        }

        // Create default user if not exists
        if (!userRepository.existsByUsername("user")) {
            User user = new User();
            user.setUsername("user");
            user.setPassword(passwordEncoder.encode("user123")); // Default password: user123
            user.setEmail("user@example.com");
            user.setRole("ROLE_USER");
            userRepository.save(user);
        }
    }
} 