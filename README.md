# 📚 Журнал оценок (Grades Journal)

<p align="center">
  <img src="https://img.shields.io/badge/Java-21-orange?style=for-the-badge&logo=openjdk" alt="Java 21">
  <img src="https://img.shields.io/badge/Spring%20Boot-3.3.3-brightgreen?style=for-the-badge&logo=springboot" alt="Spring Boot 3.3.3">
  <img src="https://img.shields.io/badge/Thymeleaf-3.1-green?style=for-the-badge&logo=thymeleaf" alt="Thymeleaf">
  <img src="https://img.shields.io/badge/H2-Database-blue?style=for-the-badge" alt="H2 Database">
  <img src="https://img.shields.io/badge/REST-API-red?style=for-the-badge" alt="REST API">
</p>

> **Вариант 18** — Веб-приложение для управления оценками студентов

## 📋 Описание

Веб-приложение на Java с использованием Spring Boot, реализующее полный CRUD-функционал (создание, чтение, редактирование, удаление) для таблицы оценок студентов. Проект разработан в рамках учебного задания.

Приложение предоставляет:
- 🌐 **Web-интерфейс** — для работы через браузер (Thymeleaf)
- 🔌 **REST API** — для интеграции с внешними клиентами (JavaFX и др.)

### Структура таблицы `grades`

| Столбец | Тип | Описание |
|---------|-----|----------|
| `id` | BIGINT | Уникальный идентификатор (автогенерация) |
| `student_name` | VARCHAR(100) | Имя студента |
| `subject` | VARCHAR(100) | Название предмета |
| `grade` | INTEGER | Оценка (от 1 до 5) |

---

## 🛠 Технологии

| Технология | Версия | Назначение |
|------------|--------|------------|
| Java | 21 | Язык программирования |
| Spring Boot | 3.3.3 | Фреймворк |
| Spring Data JPA | — | Работа с базой данных |
| Spring Security | — | Аутентификация и авторизация |
| Thymeleaf | 3.1 | Шаблонизатор HTML |
| H2 Database | — | Встроенная база данных (in-memory) |
| Maven | 3.9+ | Сборка проекта |
| JUnit 5 | — | Тестирование |

---

## 🚀 Быстрый старт

### Требования

- **JDK 21** или выше
- **Maven 3.6+** (или используйте встроенный Maven Wrapper)

### Установка и запуск

1. **Клонируйте репозиторий:**
   ```bash
   git clone https://github.com/your-username/grades-journal.git
   cd grades-journal
   ```

2. **Запустите приложение:**
   
   С установленным Maven:
   ```bash
   mvn spring-boot:run
   ```
   
   Или с Maven Wrapper (Windows):
   ```cmd
   mvnw.cmd spring-boot:run
   ```
   
   Или с Maven Wrapper (Linux/Mac):
   ```bash
   ./mvnw spring-boot:run
   ```

3. **Откройте в браузере:**
   ```
   http://localhost:8080
   ```

---

## 🔐 Авторизация

При входе в **веб-интерфейс** используйте одну из учётных записей:

| Пользователь | Пароль | Роль |
|-------------|--------|------|
| `admin` | `admin` | ADMIN |
| `user` | `user` | USER |

> ⚠️ **Примечание:** REST API (`/api/**`) доступен **без аутентификации** для удобства интеграции с внешними клиентами.

---

## 📁 Структура проекта

```
src/
├── main/
│   ├── java/com/example/publications/
│   │   ├── PublicationsApplication.java    # Точка входа
│   │   ├── config/
│   │   │   ├── SecurityConfig.java         # Конфигурация Spring Security
│   │   │   └── DataInitializer.java        # Начальные данные
│   │   ├── controller/
│   │   │   ├── GradeController.java        # MVC контроллер (веб-интерфейс)
│   │   │   ├── GradeRestController.java    # REST API контроллер
│   │   │   ├── AuthController.java         # Контроллер авторизации
│   │   │   └── HomeController.java         # Перенаправление
│   │   ├── entity/
│   │   │   ├── Grade.java                  # JPA сущность оценки
│   │   │   └── User.java                   # JPA сущность пользователя
│   │   ├── repository/
│   │   │   ├── GradeRepository.java        # Репозиторий оценок
│   │   │   └── UserRepository.java         # Репозиторий пользователей
│   │   └── service/
│   │       ├── GradeService.java           # Сервис оценок
│   │       └── UserService.java            # Сервис пользователей
│   └── resources/
│       ├── application.properties          # Конфигурация приложения
│       ├── static/css/
│       │   └── style.css                   # Стили CSS
│       └── templates/grades/
│           ├── list.html                   # Список оценок
│           └── form.html                   # Форма редактирования
└── test/
    ├── java/com/example/publications/
    │   └── controller/
    │       └── GradeRestControllerTest.java  # Интеграционные тесты API
    └── resources/
        └── application-test.properties       # Конфигурация для тестов
```

---

## ✨ Функциональность

### Веб-интерфейс (MVC)

| Операция | Описание | URL |
|----------|----------|-----|
| **CREATE** | Добавление новой оценки | `POST /grades/new` |
| **READ** | Просмотр всех оценок | `GET /grades` |
| **UPDATE** | Редактирование оценки | `POST /grades/edit/{id}` |
| **DELETE** | Удаление оценки | `GET /grades/delete/{id}` |

### Дополнительные возможности:
- ✅ Валидация введённых данных
- 🎨 Индивидуальное CSS-оформление (тёмная тема)
- 📱 Адаптивный дизайн для мобильных устройств
- 🔒 Аутентификация через Spring Security
- 📊 Цветовая индикация оценок

---

## 🔌 REST API

REST API доступен по адресу `/api/grades` и позволяет интегрировать приложение с внешними клиентами (JavaFX, мобильные приложения и т.д.).

### Endpoints

| Метод | URL | Описание | Тело запроса |
|-------|-----|----------|--------------|
| `GET` | `/api/grades` | Получить все оценки | — |
| `GET` | `/api/grades/{id}` | Получить оценку по ID | — |
| `POST` | `/api/grades` | Создать новую оценку | JSON |
| `PUT` | `/api/grades/{id}` | Обновить оценку | JSON |
| `DELETE` | `/api/grades/{id}` | Удалить оценку | — |

### Поиск

| Метод | URL | Описание |
|-------|-----|----------|
| `GET` | `/api/grades/search/student?name=...` | Поиск по имени студента |
| `GET` | `/api/grades/search/subject?name=...` | Поиск по предмету |
| `GET` | `/api/grades/search/grade?value=...` | Поиск по значению оценки |

### Примеры запросов

#### Получить все оценки
```bash
curl -X GET http://localhost:8080/api/grades
```

#### Создать оценку
```bash
curl -X POST http://localhost:8080/api/grades \
  -H "Content-Type: application/json" \
  -d '{
    "studentName": "Иванов Иван",
    "subject": "Математика",
    "grade": 5
  }'
```

#### Обновить оценку
```bash
curl -X PUT http://localhost:8080/api/grades/1 \
  -H "Content-Type: application/json" \
  -d '{
    "studentName": "Иванов Иван",
    "subject": "Математика",
    "grade": 4
  }'
```

#### Удалить оценку
```bash
curl -X DELETE http://localhost:8080/api/grades/1
```

### Формат ответов

**Успешный ответ (200 OK):**
```json
{
  "id": 1,
  "studentName": "Иванов Иван",
  "subject": "Математика",
  "grade": 5
}
```

**Ошибка валидации (400 Bad Request):**
```json
{
  "timestamp": "2025-12-06T12:00:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Validation failed"
}
```

**Не найдено (404 Not Found):**
- Пустое тело ответа

---

## 🧪 Тестирование

Проект содержит интеграционные тесты для REST API.

### Запуск тестов

```bash
# Все тесты
mvn test

# Только тесты REST API
mvn test -Dtest=GradeRestControllerTest
```

### Покрытие тестами

- ✅ GET всех оценок (пустой список и с данными)
- ✅ GET оценки по ID (существует / не существует)
- ✅ POST создание оценки (валидные / невалидные данные)
- ✅ PUT обновление оценки (существует / не существует)
- ✅ DELETE удаление оценки (существует / не существует)
- ✅ Поиск по имени студента
- ✅ Поиск по предмету
- ✅ Поиск по значению оценки
- ✅ Полный CRUD цикл
- ✅ Массовое создание записей

---

## 🗄 H2 Console (для отладки)

Для просмотра данных напрямую в базе данных:

1. Перейдите по адресу: `http://localhost:8080/h2-console`
2. Настройки подключения:
   - **JDBC URL:** `jdbc:h2:mem:gradesdb`
   - **User:** `sa`
   - **Password:** *(оставьте пустым)*
3. Нажмите **Connect**

---

## ⚙️ Конфигурация

Основные настройки в `application.properties`:

```properties
# Порт сервера
server.port=8080

# База данных H2
spring.datasource.url=jdbc:h2:mem:gradesdb
spring.jpa.hibernate.ddl-auto=update

# H2 Console
spring.h2.console.enabled=true
```

---

## 🔧 Сборка JAR

```bash
mvn clean package -DskipTests
java -jar target/grades-0.0.1-SNAPSHOT.jar
```

---

## 📄 Лицензия

Этот проект создан в учебных целях.

---

## 👤 Автор

**Студент Финансового университета**  
Вариант: **18 (Оценки / Grades)**

---

<p align="center">
  <sub>Сделано с ❤️ используя Spring Boot</sub>
</p>
