package com.example.publications.controller;

import com.example.publications.entity.User;
import com.example.publications.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {

    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Страница входа
     */
    @GetMapping("/login")
    public String loginPage(@RequestParam(value = "error", required = false) Boolean error,
                           @RequestParam(value = "logout", required = false) Boolean logout,
                           Model model) {
        if (Boolean.TRUE.equals(error)) {
            model.addAttribute("errorMessage", "Неверное имя пользователя или пароль");
        }
        if (Boolean.TRUE.equals(logout)) {
            model.addAttribute("successMessage", "Вы успешно вышли из системы");
        }
        return "auth/login";
    }

    /**
     * Страница регистрации
     */
    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("user", new User());
        return "auth/register";
    }

    /**
     * Обработка регистрации
     */
    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("user") User user,
                               BindingResult result,
                               RedirectAttributes redirectAttributes,
                               Model model) {
        
        // Проверка валидации
        if (result.hasErrors()) {
            return "auth/register";
        }

        // Проверка существования пользователя
        if (userService.existsByUsername(user.getUsername())) {
            model.addAttribute("usernameError", "Пользователь с таким именем уже существует");
            return "auth/register";
        }

        try {
            // Регистрация с ролью USER по умолчанию
            userService.registerUser(user.getUsername(), user.getPassword());
            redirectAttributes.addFlashAttribute("successMessage", 
                "Регистрация успешна! Теперь вы можете войти в систему.");
            return "redirect:/login";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Ошибка при регистрации: " + e.getMessage());
            return "auth/register";
        }
    }

    /**
     * Страница "Доступ запрещён"
     */
    @GetMapping("/access-denied")
    public String accessDenied() {
        return "auth/access-denied";
    }
}

