package com.example.publications.config;

import com.example.publications.entity.Grade;
import com.example.publications.entity.User;
import com.example.publications.repository.GradeRepository;
import com.example.publications.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initData(GradeRepository gradeRepository, 
                                       UserRepository userRepository,
                                       PasswordEncoder passwordEncoder) {
        return args -> {
            // Создаём начальных пользователей
            if (userRepository.count() == 0) {
                // Администратор
                User admin = new User();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("admin"));
                admin.setRole(User.Role.ADMIN);
                userRepository.save(admin);

                // Обычный пользователь
                User user = new User();
                user.setUsername("user");
                user.setPassword(passwordEncoder.encode("user"));
                user.setRole(User.Role.USER);
                userRepository.save(user);

                System.out.println("✓ Пользователи успешно созданы!");
                System.out.println("  - admin/admin (роль: ADMIN)");
                System.out.println("  - user/user (роль: USER)");
            }

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
