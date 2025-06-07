package com.example.comicweb.controller;

import com.example.comicweb.dto.RegisterDTO;
import com.example.comicweb.model.User;
import com.example.comicweb.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {
    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String loginForm() {
        return "auth/login";
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("registerDTO", new RegisterDTO());
        return "auth/register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("registerDTO") RegisterDTO registerDTO,
                             BindingResult result,
                             RedirectAttributes redirectAttributes) {
        // Kiểm tra xác nhận mật khẩu
        if (!registerDTO.getPassword().equals(registerDTO.getConfirmPassword())) {
            result.rejectValue("confirmPassword", "error.registerDTO", "Passwords do not match");
        }

        // Kiểm tra username đã tồn tại
        if (userService.isUsernameExists(registerDTO.getUsername())) {
            result.rejectValue("username", "error.registerDTO", "Username already exists");
        }

        // Kiểm tra email đã tồn tại
        if (userService.isEmailExists(registerDTO.getEmail())) {
            result.rejectValue("email", "error.registerDTO", "Email already exists");
        }

        if (result.hasErrors()) {
            return "auth/register";
        }

        // Tạo user mới
        try {
            User user = new User();
            user.setUsername(registerDTO.getUsername());
            user.setEmail(registerDTO.getEmail());
            user.setPassword(registerDTO.getPassword());
            
            userService.registerUser(user);
            redirectAttributes.addFlashAttribute("successMessage", 
                "Registration successful! Please login with your new account.");
            return "redirect:/login";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/register";
        }
    }
} 