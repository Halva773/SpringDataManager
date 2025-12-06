package com.example.publications.controller;

import com.example.publications.entity.Grade;
import com.example.publications.repository.GradeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Интеграционные тесты для REST API оценок.
 * Проверяют полную работоспособность всех CRUD операций.
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class GradeRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private GradeRepository gradeRepository;

    private static final String API_BASE_URL = "/api/grades";

    @BeforeEach
    void setUp() {
        // Очищаем базу перед каждым тестом
        gradeRepository.deleteAll();
    }

    // ===================== ТЕСТЫ GET (Получение) =====================

    @Test
    @Order(1)
    @DisplayName("GET /api/grades - Получить все оценки (пустой список)")
    void getAllGrades_EmptyList_ReturnsEmptyArray() throws Exception {
        mockMvc.perform(get(API_BASE_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    @Order(2)
    @DisplayName("GET /api/grades - Получить все оценки (с данными)")
    void getAllGrades_WithData_ReturnsAllGrades() throws Exception {
        // Подготовка данных
        gradeRepository.save(new Grade("Иванов Иван", "Математика", 5));
        gradeRepository.save(new Grade("Петров Пётр", "Физика", 4));
        gradeRepository.save(new Grade("Сидоров Сидор", "Информатика", 3));

        mockMvc.perform(get(API_BASE_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].studentName", notNullValue()))
                .andExpect(jsonPath("$[0].subject", notNullValue()))
                .andExpect(jsonPath("$[0].grade", notNullValue()));
    }

    @Test
    @Order(3)
    @DisplayName("GET /api/grades/{id} - Получить оценку по ID (существует)")
    void getGradeById_Exists_ReturnsGrade() throws Exception {
        Grade saved = gradeRepository.save(new Grade("Иванов Иван", "Математика", 5));

        mockMvc.perform(get(API_BASE_URL + "/" + saved.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(saved.getId().intValue())))
                .andExpect(jsonPath("$.studentName", is("Иванов Иван")))
                .andExpect(jsonPath("$.subject", is("Математика")))
                .andExpect(jsonPath("$.grade", is(5)));
    }

    @Test
    @Order(4)
    @DisplayName("GET /api/grades/{id} - Получить оценку по ID (не существует)")
    void getGradeById_NotExists_Returns404() throws Exception {
        mockMvc.perform(get(API_BASE_URL + "/999"))
                .andExpect(status().isNotFound());
    }

    // ===================== ТЕСТЫ POST (Создание) =====================

    @Test
    @Order(5)
    @DisplayName("POST /api/grades - Создать новую оценку (валидные данные)")
    void createGrade_ValidData_ReturnsCreated() throws Exception {
        Grade newGrade = new Grade("Новиков Николай", "История", 4);

        mockMvc.perform(post(API_BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newGrade)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.studentName", is("Новиков Николай")))
                .andExpect(jsonPath("$.subject", is("История")))
                .andExpect(jsonPath("$.grade", is(4)));

        // Проверяем, что данные сохранились в БД
        List<Grade> grades = gradeRepository.findAll();
        assertEquals(1, grades.size());
        assertEquals("Новиков Николай", grades.get(0).getStudentName());
    }

    @Test
    @Order(6)
    @DisplayName("POST /api/grades - Создать оценку (пустое имя студента)")
    void createGrade_EmptyStudentName_Returns400() throws Exception {
        Grade invalidGrade = new Grade("", "Математика", 5);

        mockMvc.perform(post(API_BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidGrade)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(7)
    @DisplayName("POST /api/grades - Создать оценку (пустой предмет)")
    void createGrade_EmptySubject_Returns400() throws Exception {
        Grade invalidGrade = new Grade("Иванов Иван", "", 5);

        mockMvc.perform(post(API_BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidGrade)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(8)
    @DisplayName("POST /api/grades - Создать оценку (оценка меньше 1)")
    void createGrade_GradeLessThan1_Returns400() throws Exception {
        Grade invalidGrade = new Grade("Иванов Иван", "Математика", 0);

        mockMvc.perform(post(API_BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidGrade)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(9)
    @DisplayName("POST /api/grades - Создать оценку (оценка больше 5)")
    void createGrade_GradeGreaterThan5_Returns400() throws Exception {
        Grade invalidGrade = new Grade("Иванов Иван", "Математика", 6);

        mockMvc.perform(post(API_BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidGrade)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(10)
    @DisplayName("POST /api/grades - Создать оценку (null оценка)")
    void createGrade_NullGrade_Returns400() throws Exception {
        Grade invalidGrade = new Grade("Иванов Иван", "Математика", null);

        mockMvc.perform(post(API_BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidGrade)))
                .andExpect(status().isBadRequest());
    }

    // ===================== ТЕСТЫ PUT (Обновление) =====================

    @Test
    @Order(11)
    @DisplayName("PUT /api/grades/{id} - Обновить существующую оценку")
    void updateGrade_Exists_ReturnsUpdated() throws Exception {
        Grade saved = gradeRepository.save(new Grade("Иванов Иван", "Математика", 3));

        Grade updatedGrade = new Grade("Иванов Иван Иванович", "Высшая математика", 5);

        mockMvc.perform(put(API_BASE_URL + "/" + saved.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedGrade)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(saved.getId().intValue())))
                .andExpect(jsonPath("$.studentName", is("Иванов Иван Иванович")))
                .andExpect(jsonPath("$.subject", is("Высшая математика")))
                .andExpect(jsonPath("$.grade", is(5)));

        // Проверяем изменения в БД
        Grade fromDb = gradeRepository.findById(saved.getId()).orElseThrow();
        assertEquals("Иванов Иван Иванович", fromDb.getStudentName());
        assertEquals("Высшая математика", fromDb.getSubject());
        assertEquals(5, fromDb.getGrade());
    }

    @Test
    @Order(12)
    @DisplayName("PUT /api/grades/{id} - Обновить несуществующую оценку")
    void updateGrade_NotExists_Returns404() throws Exception {
        Grade updatedGrade = new Grade("Иванов Иван", "Математика", 5);

        mockMvc.perform(put(API_BASE_URL + "/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedGrade)))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(13)
    @DisplayName("PUT /api/grades/{id} - Обновить с невалидными данными")
    void updateGrade_InvalidData_Returns400() throws Exception {
        Grade saved = gradeRepository.save(new Grade("Иванов Иван", "Математика", 3));

        Grade invalidGrade = new Grade("", "Математика", 5);

        mockMvc.perform(put(API_BASE_URL + "/" + saved.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidGrade)))
                .andExpect(status().isBadRequest());
    }

    // ===================== ТЕСТЫ DELETE (Удаление) =====================

    @Test
    @Order(14)
    @DisplayName("DELETE /api/grades/{id} - Удалить существующую оценку")
    void deleteGrade_Exists_ReturnsNoContent() throws Exception {
        Grade saved = gradeRepository.save(new Grade("Иванов Иван", "Математика", 5));

        mockMvc.perform(delete(API_BASE_URL + "/" + saved.getId()))
                .andExpect(status().isNoContent());

        // Проверяем, что запись удалена
        assertFalse(gradeRepository.existsById(saved.getId()));
    }

    @Test
    @Order(15)
    @DisplayName("DELETE /api/grades/{id} - Удалить несуществующую оценку")
    void deleteGrade_NotExists_Returns404() throws Exception {
        mockMvc.perform(delete(API_BASE_URL + "/999"))
                .andExpect(status().isNotFound());
    }

    // ===================== ТЕСТЫ ПОИСКА =====================

    @Test
    @Order(16)
    @DisplayName("GET /api/grades/search/student - Поиск по имени студента")
    void searchByStudentName_ReturnsMatchingGrades() throws Exception {
        gradeRepository.save(new Grade("Иванов Иван", "Математика", 5));
        gradeRepository.save(new Grade("Иванов Пётр", "Физика", 4));
        gradeRepository.save(new Grade("Петров Сергей", "Математика", 3));

        mockMvc.perform(get(API_BASE_URL + "/search/student")
                        .param("name", "Иванов"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[*].studentName", everyItem(containsString("Иванов"))));
    }

    @Test
    @Order(17)
    @DisplayName("GET /api/grades/search/student - Поиск без результатов")
    void searchByStudentName_NoResults_ReturnsEmptyArray() throws Exception {
        gradeRepository.save(new Grade("Иванов Иван", "Математика", 5));

        mockMvc.perform(get(API_BASE_URL + "/search/student")
                        .param("name", "Несуществующий"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    @Order(18)
    @DisplayName("GET /api/grades/search/subject - Поиск по предмету")
    void searchBySubject_ReturnsMatchingGrades() throws Exception {
        gradeRepository.save(new Grade("Иванов Иван", "Математика", 5));
        gradeRepository.save(new Grade("Петров Пётр", "Высшая математика", 4));
        gradeRepository.save(new Grade("Сидоров Сергей", "Физика", 3));

        mockMvc.perform(get(API_BASE_URL + "/search/subject")
                        .param("name", "математика"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    @Order(19)
    @DisplayName("GET /api/grades/search/grade - Поиск по значению оценки")
    void searchByGradeValue_ReturnsMatchingGrades() throws Exception {
        gradeRepository.save(new Grade("Иванов Иван", "Математика", 5));
        gradeRepository.save(new Grade("Петров Пётр", "Физика", 5));
        gradeRepository.save(new Grade("Сидоров Сергей", "Информатика", 3));

        mockMvc.perform(get(API_BASE_URL + "/search/grade")
                        .param("value", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[*].grade", everyItem(is(5))));
    }

    // ===================== ИНТЕГРАЦИОННЫЕ СЦЕНАРИИ =====================

    @Test
    @Order(20)
    @DisplayName("Полный CRUD цикл - создание, чтение, обновление, удаление")
    void fullCrudCycle_WorksCorrectly() throws Exception {
        // 1. Создаём оценку
        Grade newGrade = new Grade("Тестовый Студент", "Тестовый Предмет", 3);

        MvcResult createResult = mockMvc.perform(post(API_BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newGrade)))
                .andExpect(status().isCreated())
                .andReturn();

        Grade createdGrade = objectMapper.readValue(
                createResult.getResponse().getContentAsString(), Grade.class);
        Long gradeId = createdGrade.getId();
        assertNotNull(gradeId);

        // 2. Читаем созданную оценку
        mockMvc.perform(get(API_BASE_URL + "/" + gradeId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.studentName", is("Тестовый Студент")));

        // 3. Обновляем оценку
        Grade updatedGrade = new Grade("Тестовый Студент", "Тестовый Предмет", 5);
        mockMvc.perform(put(API_BASE_URL + "/" + gradeId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedGrade)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.grade", is(5)));

        // 4. Проверяем обновление
        mockMvc.perform(get(API_BASE_URL + "/" + gradeId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.grade", is(5)));

        // 5. Удаляем оценку
        mockMvc.perform(delete(API_BASE_URL + "/" + gradeId))
                .andExpect(status().isNoContent());

        // 6. Проверяем, что оценка удалена
        mockMvc.perform(get(API_BASE_URL + "/" + gradeId))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(21)
    @DisplayName("Массовое создание и получение оценок")
    void bulkCreateAndRetrieve_WorksCorrectly() throws Exception {
        // Создаём 10 оценок
        for (int i = 1; i <= 10; i++) {
            Grade grade = new Grade("Студент " + i, "Предмет " + i, (i % 5) + 1);
            mockMvc.perform(post(API_BASE_URL)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(grade)))
                    .andExpect(status().isCreated());
        }

        // Проверяем, что все 10 созданы
        mockMvc.perform(get(API_BASE_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(10)));

        // Проверяем количество в БД
        assertEquals(10, gradeRepository.count());
    }

    @Test
    @Order(22)
    @DisplayName("Проверка Content-Type заголовков")
    void contentTypeHeaders_AreCorrect() throws Exception {
        Grade grade = gradeRepository.save(new Grade("Иванов Иван", "Математика", 5));

        // GET возвращает application/json
        mockMvc.perform(get(API_BASE_URL))
                .andExpect(header().string("Content-Type", containsString("application/json")));

        mockMvc.perform(get(API_BASE_URL + "/" + grade.getId()))
                .andExpect(header().string("Content-Type", containsString("application/json")));

        // POST возвращает application/json
        Grade newGrade = new Grade("Новый Студент", "Новый Предмет", 4);
        mockMvc.perform(post(API_BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newGrade)))
                .andExpect(header().string("Content-Type", containsString("application/json")));
    }
}

