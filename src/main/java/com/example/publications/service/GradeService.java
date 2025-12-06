package com.example.publications.service;

import com.example.publications.entity.Grade;
import com.example.publications.repository.GradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class GradeService {

    private final GradeRepository gradeRepository;

    @Autowired
    public GradeService(GradeRepository gradeRepository) {
        this.gradeRepository = gradeRepository;
    }

    /**
     * Получить все оценки
     */
    public List<Grade> findAll() {
        return gradeRepository.findAll();
    }

    /**
     * Найти оценку по ID
     */
    public Optional<Grade> findById(Long id) {
        return gradeRepository.findById(id);
    }

    /**
     * Сохранить или обновить оценку
     */
    public Grade save(Grade grade) {
        return gradeRepository.save(grade);
    }

    /**
     * Удалить оценку по ID
     */
    public void deleteById(Long id) {
        gradeRepository.deleteById(id);
    }

    /**
     * Проверить существование оценки
     */
    public boolean existsById(Long id) {
        return gradeRepository.existsById(id);
    }

    /**
     * Поиск по имени студента
     */
    public List<Grade> findByStudentName(String studentName) {
        return gradeRepository.findByStudentNameContainingIgnoreCase(studentName);
    }

    /**
     * Поиск по предмету
     */
    public List<Grade> findBySubject(String subject) {
        return gradeRepository.findBySubjectContainingIgnoreCase(subject);
    }

    /**
     * Поиск по оценке
     */
    public List<Grade> findByGrade(Integer grade) {
        return gradeRepository.findByGrade(grade);
    }
}

