# 📚 Журнал оценок (Grades Journal)

<p align="center">
  <img src="https://img.shields.io/badge/Java-21-orange?style=for-the-badge&logo=openjdk" alt="Java 21">
  <img src="https://img.shields.io/badge/Spring%20Boot-3.3.3-brightgreen?style=for-the-badge&logo=springboot" alt="Spring Boot 3.3.3">
  <img src="https://img.shields.io/badge/Thymeleaf-3.1-green?style=for-the-badge&logo=thymeleaf" alt="Thymeleaf">
  <img src="https://img.shields.io/badge/H2-Database-blue?style=for-the-badge" alt="H2 Database">
</p>

> **Вариант 18** — Веб-приложение для управления оценками студентов

## 📋 Описание

Веб-приложение на Java с использованием Spring Boot, реализующее полный CRUD-функционал (создание, чтение, редактирование, удаление) для таблицы оценок студентов. Проект разработан в рамках учебного задания.

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

При входе используйте одну из учётных записей:

| Пользователь | Пароль | Роль |
|-------------|--------|------|
| `admin` | `admin` | ADMIN, USER |
| `user` | `user` | USER |

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
│   │   │   ├── GradeController.java        # CRUD контроллер
│   │   │   └── HomeController.java         # Перенаправление
│   │   ├── entity/
│   │   │   └── Grade.java                  # JPA сущность
│   │   ├── repository/
│   │   │   └── GradeRepository.java        # Spring Data репозиторий
│   │   └── service/
│   │       └── GradeService.java           # Бизнес-логика
│   └── resources/
│       ├── application.properties          # Конфигурация приложения
│       ├── static/css/
│       │   └── style.css                   # Стили CSS
│       └── templates/grades/
│           ├── list.html                   # Список оценок
│           └── form.html                   # Форма редактирования
└── test/                                   # Тесты
```

---

## ✨ Функциональность

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

## 🗄 H2 Console (для отладки)

Для просмотра данных напрямую в базе данных:

1. Перейдите по адресу: `http://localhost:8080/h2-console`
2. Настройки подключения:
   - **JDBC URL:** `jdbc:h2:mem:gradesdb`
   - **User:** `sa`
   - **Password:** *(оставьте пустым)*
3. Нажмите **Connect**

---

## 🎨 Скриншоты

### Главная страница (список оценок)
- Таблица с оценками
- Форма быстрого добавления
- Кнопки редактирования и удаления

### Форма редактирования
- Поля ввода с валидацией
- Легенда оценок (1-5)

---

## 📝 API Endpoints

| Метод | URL | Описание |
|-------|-----|----------|
| GET | `/` | Перенаправление на `/grades` |
| GET | `/grades` | Список всех оценок |
| GET | `/grades/new` | Форма добавления |
| POST | `/grades/new` | Создание оценки |
| GET | `/grades/edit/{id}` | Форма редактирования |
| POST | `/grades/edit/{id}` | Обновление оценки |
| GET | `/grades/delete/{id}` | Удаление оценки |

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
