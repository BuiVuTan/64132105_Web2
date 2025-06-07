package com.example.comicweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"com.example.comicweb", "com.example.web2_64132105"})
@EntityScan(basePackages = {"com.example.comicweb.model"})
@EnableJpaRepositories(basePackages = {"com.example.comicweb.repository"})
public class ComicWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(ComicWebApplication.class, args);
    }
} 