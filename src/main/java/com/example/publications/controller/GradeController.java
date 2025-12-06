package com.example.publications.controller;

import com.example.publications.entity.Grade;
import com.example.publications.service.GradeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/grades")
public class GradeController {

    private final GradeService gradeService;

    @Autowired
    public GradeController(GradeService gradeService) {
        this.gradeService = gradeService;
    }

    /**
     * Главная страница - список всех оценок
     */
    @GetMapping
    public String listGrades(Model model) {
        model.addAttribute("grades", gradeService.findAll());
        model.addAttribute("newGrade", new Grade());
        return "grades/list";
    }

    /**
     * Форма добавления новой оценки
     */
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("grade", new Grade());
        return "grades/form";
    }

    /**
     * Обработка создания новой оценки
     */
    @PostMapping("/new")
    public String createGrade(@Valid @ModelAttribute("grade") Grade grade,
                              BindingResult result,
                              RedirectAttributes redirectAttributes,
                              Model model) {
        if (result.hasErrors()) {
            return "grades/form";
        }
        gradeService.save(grade);
        redirectAttributes.addFlashAttribute("successMessage", "Оценка успешно добавлена!");
        return "redirect:/grades";
    }

    /**
     * Форма редактирования оценки
     */
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Grade> gradeOptional = gradeService.findById(id);
        if (gradeOptional.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Оценка не найдена!");
            return "redirect:/grades";
        }
        model.addAttribute("grade", gradeOptional.get());
        return "grades/form";
    }

    /**
     * Обработка обновления оценки
     */
    @PostMapping("/edit/{id}")
    public String updateGrade(@PathVariable("id") Long id,
                              @Valid @ModelAttribute("grade") Grade grade,
                              BindingResult result,
                              RedirectAttributes redirectAttributes) {
        // Устанавливаем id ДО проверки ошибок, чтобы форма редактирования отображалась правильно
        grade.setId(id);
        if (result.hasErrors()) {
            return "grades/form";
        }
        gradeService.save(grade);
        redirectAttributes.addFlashAttribute("successMessage", "Оценка успешно обновлена!");
        return "redirect:/grades";
    }

    /**
     * Удаление оценки
     */
    @GetMapping("/delete/{id}")
    public String deleteGrade(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        if (gradeService.existsById(id)) {
            gradeService.deleteById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Оценка успешно удалена!");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Оценка не найдена!");
        }
        return "redirect:/grades";
    }

    /**
     * Быстрое добавление оценки с главной страницы
     */
    @PostMapping("/quick-add")
    public String quickAddGrade(@Valid @ModelAttribute("newGrade") Grade grade,
                                BindingResult result,
                                RedirectAttributes redirectAttributes,
                                Model model) {
        if (result.hasErrors()) {
            model.addAttribute("grades", gradeService.findAll());
            model.addAttribute("showFormErrors", true);
            return "grades/list";
        }
        gradeService.save(grade);
        redirectAttributes.addFlashAttribute("successMessage", "Оценка успешно добавлена!");
        return "redirect:/grades";
    }
}
