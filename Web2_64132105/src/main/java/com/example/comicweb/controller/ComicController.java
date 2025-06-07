package com.example.comicweb.controller;

import com.example.comicweb.model.Comic;
import com.example.comicweb.model.User;
import com.example.comicweb.model.Category;
import com.example.comicweb.service.ComicService;
import com.example.comicweb.service.CategoryService;
import com.example.comicweb.service.UserService;
import com.example.comicweb.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.security.access.prepost.PreAuthorize;
import jakarta.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/comics")
public class ComicController {
    @Autowired
    private ComicService comicService;
    
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;

    @Autowired
    private FileStorageService fileStorageService;

    @GetMapping
    public String listComics(Model model) {
        model.addAttribute("comics", comicService.getAllComics());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "comics/list";
    }

    @GetMapping("/{id}")
    public String viewComic(@PathVariable Long id, Model model) {
        try {
            Comic comic = comicService.getComicById(id);
            model.addAttribute("comic", comic);
            model.addAttribute("categories", categoryService.getAllCategories());
            return "comics/view";
        } catch (RuntimeException e) {
            return "redirect:/comics";
        }
    }

    @GetMapping("/new")
    @PreAuthorize("isAuthenticated()")
    public String newComicForm(Model model) {
        model.addAttribute("comic", new Comic());
        model.addAttribute("allCategories", categoryService.getAllCategories());
        return "comics/form";
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public String createComic(@Valid @ModelAttribute Comic comic,
                            BindingResult bindingResult,
                            @RequestParam(value = "coverFile", required = false) MultipartFile coverFile,
                            @RequestParam(value = "categoryId", required = false) Long categoryId,
                            @AuthenticationPrincipal UserDetails userDetails,
                            Model model,
                            RedirectAttributes redirectAttributes) {
        try {
            // Validate required fields
            if (bindingResult.hasErrors()) {
                model.addAttribute("allCategories", categoryService.getAllCategories());
                return "comics/form";
            }

            // Validate categoryId
            if (categoryId == null) {
                model.addAttribute("allCategories", categoryService.getAllCategories());
                model.addAttribute("errorMessage", "Please select a category");
                return "comics/form";
            }

            // Validate coverFile for new comics
            if (coverFile == null || coverFile.isEmpty()) {
                model.addAttribute("allCategories", categoryService.getAllCategories());
                model.addAttribute("errorMessage", "Please select a cover image");
                return "comics/form";
            }

            User user = userService.getUserByUsername(userDetails.getUsername())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            comic.setUploader(user);
            
            // Handle category
            Category category = categoryService.getCategoryById(categoryId);
            comic.setCategory(category);
            
            // Handle cover image
            String coverPath = fileStorageService.store(coverFile);
            comic.setCoverImage(coverPath);
            
            comicService.createComic(comic);
            redirectAttributes.addFlashAttribute("successMessage", "Comic created successfully!");
            return "redirect:/comics";
        } catch (Exception e) {
            model.addAttribute("allCategories", categoryService.getAllCategories());
            model.addAttribute("errorMessage", "Failed to create comic: " + e.getMessage());
            return "comics/form";
        }
    }

    @GetMapping("/{id}/edit")
    @PreAuthorize("isAuthenticated()")
    public String editComicForm(@PathVariable Long id, Model model, 
                              @AuthenticationPrincipal UserDetails userDetails,
                              RedirectAttributes redirectAttributes) {
        try {
            Comic comic = comicService.getComicById(id);
            User currentUser = userService.getUserByUsername(userDetails.getUsername())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            
            if (!comic.getUploader().getId().equals(currentUser.getId())) {
                redirectAttributes.addFlashAttribute("errorMessage", "You are not authorized to edit this comic");
                return "redirect:/comics/" + id;
            }
            
            model.addAttribute("comic", comic);
            model.addAttribute("allCategories", categoryService.getAllCategories());
            return "comics/form";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/comics";
        }
    }

    @PostMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public String updateComic(@PathVariable Long id,
                            @Valid @ModelAttribute Comic comic,
                            BindingResult bindingResult,
                            @RequestParam(value = "coverFile", required = false) MultipartFile coverFile,
                            @RequestParam(value = "categoryId", required = false) Long categoryId,
                            @AuthenticationPrincipal UserDetails userDetails,
                            Model model,
                            RedirectAttributes redirectAttributes) {
        try {
            // Validate required fields
            if (bindingResult.hasErrors()) {
                model.addAttribute("allCategories", categoryService.getAllCategories());
                return "comics/form";
            }

            // Validate categoryId
            if (categoryId == null) {
                model.addAttribute("allCategories", categoryService.getAllCategories());
                model.addAttribute("errorMessage", "Please select a category");
                return "comics/form";
            }

            Comic existingComic = comicService.getComicById(id);
            User currentUser = userService.getUserByUsername(userDetails.getUsername())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            
            if (!existingComic.getUploader().getId().equals(currentUser.getId())) {
                redirectAttributes.addFlashAttribute("errorMessage", "You are not authorized to update this comic");
                return "redirect:/comics/" + id;
            }

            // Handle cover image
            if (coverFile != null && !coverFile.isEmpty()) {
                if (existingComic.getCoverImage() != null) {
                    fileStorageService.delete(existingComic.getCoverImage());
                }
                String coverPath = fileStorageService.store(coverFile);
                comic.setCoverImage(coverPath);
            } else {
                comic.setCoverImage(existingComic.getCoverImage());
            }
            
            // Handle category
            Category category = categoryService.getCategoryById(categoryId);
            comic.setCategory(category);
            
            comic.setUploader(existingComic.getUploader());
            comicService.updateComic(id, comic);
            redirectAttributes.addFlashAttribute("successMessage", "Comic updated successfully!");
            return "redirect:/comics/" + id;
        } catch (Exception e) {
            model.addAttribute("allCategories", categoryService.getAllCategories());
            model.addAttribute("errorMessage", "Failed to update comic: " + e.getMessage());
            return "comics/form";
        }
    }

    @PostMapping("/{id}/delete")
    @PreAuthorize("isAuthenticated()")
    public String deleteComic(@PathVariable Long id, 
                            @AuthenticationPrincipal UserDetails userDetails,
                            RedirectAttributes redirectAttributes) {
        try {
            Comic comic = comicService.getComicById(id);
            User currentUser = userService.getUserByUsername(userDetails.getUsername())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            
            if (!comic.getUploader().getId().equals(currentUser.getId())) {
                redirectAttributes.addFlashAttribute("errorMessage", "You are not authorized to delete this comic");
                return "redirect:/comics/" + id;
            }

            if (comic.getCoverImage() != null) {
                fileStorageService.delete(comic.getCoverImage());
            }
            comicService.deleteComic(id);
            redirectAttributes.addFlashAttribute("successMessage", "Comic deleted successfully!");
            return "redirect:/comics";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to delete comic: " + e.getMessage());
            return "redirect:/comics";
        }
    }

    @GetMapping("/category/{categoryId}")
    public String listComicsByCategory(@PathVariable Long categoryId, Model model) {
        model.addAttribute("comics", comicService.getComicsByCategory(categoryId));
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("category", categoryService.getCategoryById(categoryId));
        return "comics/list";
    }
} 