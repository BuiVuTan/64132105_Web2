package com.example.buivutan_fitcms_blog.thiGK.ntu64132105.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @GetMapping("/")
    public String dashboard(Model model) {
        model.addAttribute("username", "Bui Vu Tan");
        return "dashboard";
    }

    @GetMapping("/dashboard")
    public String dashboardRedirect(Model model) {
        return "redirect:/";
    }
}