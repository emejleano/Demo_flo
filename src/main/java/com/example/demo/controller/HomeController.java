package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/login")  // Pastikan ini sesuai dengan konfigurasi SecurityConfig
    public String login() {
        return "login";  // Mengarahkan ke file login.html di folder templates
    }

    @GetMapping("/menu")  // Endpoint untuk halaman menu
    public String menu() {
        return "menu";  // Mengarahkan ke file menu.html di folder templates
    }
}
