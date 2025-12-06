package com.example.publications.config;

import com.example.publications.entity.Grade;
import com.example.publications.repository.GradeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initData(GradeRepository gradeRepository) {
        return args -> {
            // Добавляем начальные данные для демонстрации
            if (gradeRepository.count() == 0) {
                gradeRepository.save(new Grade("Иванов Иван", "Математика", 5));
                gradeRepository.save(new Grade("Иванов Иван", "Программирование", 4));
                gradeRepository.save(new Grade("Петрова Мария", "Математика", 4));
                gradeRepository.save(new Grade("Петрова Мария", "История", 5));
                gradeRepository.save(new Grade("Сидоров Алексей", "Программирование", 3));
                gradeRepository.save(new Grade("Сидоров Алексей", "Английский язык", 4));
                gradeRepository.save(new Grade("Козлова Анна", "Физика", 5));
                gradeRepository.save(new Grade("Козлова Анна", "Математика", 5));
                
                System.out.println("✓ Начальные данные успешно загружены!");
            }
        };
    }
}

