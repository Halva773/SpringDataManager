package com.example.publications.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    /**
     * Перенаправление с корневого пути на страницу оценок
     */
    @GetMapping("/")
    public String home() {
        return "redirect:/grades";
    }
}

