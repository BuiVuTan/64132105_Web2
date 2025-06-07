package com.example.comicweb.repository;

import com.example.comicweb.model.Comic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ComicRepository extends JpaRepository<Comic, Long> {
    List<Comic> findByTitleContainingIgnoreCase(String title);
    List<Comic> findByCategoryId(Long categoryId);
    List<Comic> findByUploaderId(Long userId);
    
    @Query("SELECT c FROM Comic c WHERE " +
           "LOWER(c.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(c.description) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(c.author) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Comic> search(@Param("keyword") String keyword);
} 