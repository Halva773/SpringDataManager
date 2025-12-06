package com.example.publications.controller;

import com.example.publications.entity.Grade;
import com.example.publications.service.GradeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * REST контроллер для управления оценками.
 * Предоставляет API для CRUD операций.
 */
@RestController
@RequestMapping("/api/grades")
@CrossOrigin(origins = "*") // Разрешаем CORS для JavaFX клиента
public class GradeRestController {

    private final GradeService gradeService;

    @Autowired
    public GradeRestController(GradeService gradeService) {
        this.gradeService = gradeService;
    }

    /**
     * Получить все оценки
     * GET /api/grades
     */
    @GetMapping
    public ResponseEntity<List<Grade>> getAllGrades() {
        List<Grade> grades = gradeService.findAll();
        return ResponseEntity.ok(grades);
    }

    /**
     * Получить оценку по ID
     * GET /api/grades/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<Grade> getGradeById(@PathVariable Long id) {
        Optional<Grade> grade = gradeService.findById(id);
        return grade.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Создать новую оценку
     * POST /api/grades
     */
    @PostMapping
    public ResponseEntity<Grade> createGrade(@Valid @RequestBody Grade grade) {
        // Убеждаемся, что создаём новую запись
        grade.setId(null);
        Grade savedGrade = gradeService.save(grade);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedGrade);
    }

    /**
     * Обновить существующую оценку
     * PUT /api/grades/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<Grade> updateGrade(@PathVariable Long id, 
                                              @Valid @RequestBody Grade grade) {
        if (!gradeService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        grade.setId(id);
        Grade updatedGrade = gradeService.save(grade);
        return ResponseEntity.ok(updatedGrade);
    }

    /**
     * Удалить оценку
     * DELETE /api/grades/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGrade(@PathVariable Long id) {
        if (!gradeService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        gradeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Поиск по имени студента
     * GET /api/grades/search/student?name=...
     */
    @GetMapping("/search/student")
    public ResponseEntity<List<Grade>> searchByStudentName(@RequestParam String name) {
        List<Grade> grades = gradeService.findByStudentName(name);
        return ResponseEntity.ok(grades);
    }

    /**
     * Поиск по предмету
     * GET /api/grades/search/subject?name=...
     */
    @GetMapping("/search/subject")
    public ResponseEntity<List<Grade>> searchBySubject(@RequestParam String name) {
        List<Grade> grades = gradeService.findBySubject(name);
        return ResponseEntity.ok(grades);
    }

    /**
     * Поиск по значению оценки
     * GET /api/grades/search/grade?value=...
     */
    @GetMapping("/search/grade")
    public ResponseEntity<List<Grade>> searchByGradeValue(@RequestParam Integer value) {
        List<Grade> grades = gradeService.findByGrade(value);
        return ResponseEntity.ok(grades);
    }
}

