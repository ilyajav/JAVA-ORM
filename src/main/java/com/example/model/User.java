package com.example.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.ArrayList;
import java.util.List;

/**
 * Сущность, представляющая пользователя системы (студент, преподаватель, администратор).
 * Использует Lombok для генерации стандартных методов и JSR-303 для валидации.
 */
@Entity
@Table(name = "users")
@Getter // Генерирует все геттеры
@Setter // Генерирует все сеттеры
@NoArgsConstructor // Генерирует конструктор без аргументов (требуется JPA)
@AllArgsConstructor // Генерирует конструктор со всеми аргументами (для Builder)
@Builder // Позволяет создавать объекты с помощью паттерна Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Имя пользователя не может быть пустым")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Email не может быть пустым")
    @Email(message = "Email должен быть валидным")
    @Column(nullable = false, unique = true)
    private String email;

    @NotNull(message = "Роль пользователя обязательна")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role; // Роль пользователя (STUDENT, TEACHER, ADMIN)

    // Связь Один к Одному: Профиль пользователя. Каскадное удаление профиля.
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private Profile profile;

    // Курсы, которые ведет преподаватель.
    @OneToMany(mappedBy = "teacher", fetch = FetchType.LAZY)
    @Builder.Default // Lombok: гарантирует инициализацию коллекций
    private List<Course> coursesTaught = new ArrayList<>();

    // Записи студента на курсы.
    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY)
    @Builder.Default
    private List<Enrollment> enrollments = new ArrayList<>();

    // Сданные работы студента.
    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY)
    @Builder.Default
    private List<Submission> submissions = new ArrayList<>();

    // Попытки сдачи квизов студентом.
    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY)
    @Builder.Default
    private List<QuizSubmission> quizSubmissions = new ArrayList<>();

    // Отзывы, оставленные студентом.
    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY)
    @Builder.Default
    private List<CourseReview> reviews = new ArrayList<>();

    /**
     * Вспомогательный конструктор для создания пользователя без ID.
     * Используется для инициализации базовых полей.
     * @param name Имя пользователя.
     * @param email Email пользователя.
     * @param role Роль пользователя.
     */
    public User(String name, String email, UserRole role) {
        this.name = name;
        this.email = email;
        this.role = role;
    }
}