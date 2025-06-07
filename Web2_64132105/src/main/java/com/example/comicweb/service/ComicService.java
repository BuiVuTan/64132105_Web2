package com.example.comicweb.service;

import com.example.comicweb.model.Comic;
import com.example.comicweb.repository.ComicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ComicService {
    @Autowired
    private ComicRepository comicRepository;

    public List<Comic> getAllComics() {
        return comicRepository.findAll();
    }

    public Comic getComicById(Long id) {
        return comicRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comic not found with id: " + id));
    }

    public List<Comic> getComicsByCategory(Long categoryId) {
        return comicRepository.findByCategoryId(categoryId);
    }

    public List<Comic> searchComics(String keyword) {
        return comicRepository.search(keyword);
    }

    public List<Comic> getComicsByUploader(Long userId) {
        return comicRepository.findByUploaderId(userId);
    }

    @Transactional
    public Comic createComic(Comic comic) {
        comic.setCreatedAt(LocalDateTime.now());
        return comicRepository.save(comic);
    }

    @Transactional
    public Comic updateComic(Long id, Comic comicDetails) {
        Comic comic = getComicById(id);
        
        comic.setTitle(comicDetails.getTitle());
        comic.setDescription(comicDetails.getDescription());
        comic.setAuthor(comicDetails.getAuthor());
        comic.setStatus(comicDetails.getStatus());
        comic.setCategory(comicDetails.getCategory());
        
        if (comicDetails.getCoverImage() != null) {
            comic.setCoverImage(comicDetails.getCoverImage());
        }
        
        comic.setUpdatedAt(LocalDateTime.now());
        return comicRepository.save(comic);
    }

    @Transactional
    public void deleteComic(Long id) {
        Comic comic = getComicById(id);
        comicRepository.delete(comic);
    }
} 