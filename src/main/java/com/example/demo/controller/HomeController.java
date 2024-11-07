package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/login")  // Pastikan ini sesuai dengan konfigurasi SecurityConfig
    public String login() {
        return "login";
    }
}
