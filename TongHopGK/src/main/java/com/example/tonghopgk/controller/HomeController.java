package com.example.tonghopgk.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    List<SinhVien> dsSV = new ArrayList<>();

    public HomeController() {
        dsSV.add(new SinhVien("SV001", "Bùi Vũ Tân", 8.5));
        dsSV.add(new SinhVien("SV002", "Nguyễn Phúc Khang", 7.0));
        dsSV.add(new SinhVien("SV003", "Nguyễn Thanh Nhã", 6.8));
    }
    @GetMapping("/list")
    public String listPage(Model model) {
        model.addAttribute("dsSV", dsSV);
        return "list";
    }
    @GetMapping("/homepage")
    public String home() {
        return "index";
    }
    @GetMapping("/about")
    public String aboutPage() {
        return "about";
    }
    @GetMapping("/address")
    public String addressPage() {
        return "address";
    }
}