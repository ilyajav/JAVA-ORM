package com.example.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDateTime;

/**
 * Сущность, представляющая запись студента на курс.
 * Использует Lombok для генерации стандартных методов и Builder для удобного создания.
 */
@Entity
@Table(name = "enrollments")
@Getter // Генерирует все геттеры
@Setter // Генерирует все сеттеры
@NoArgsConstructor // Генерирует конструктор без аргументов (требуется JPA)
@AllArgsConstructor // Генерирует конструктор со всеми аргументами (для Builder)
@Builder // Позволяет создавать объекты с помощью паттерна Builder
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Студент, который записался на курс. LAZY загрузка.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private User student;

    // Курс, на который записался студент. LAZY загрузка.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    private LocalDateTime enrollDate; // Дата записи

    // Хранение ENUM как строки — лучшая практика для читаемости и гибкости.
    @Enumerated(EnumType.STRING)
    private EnrollmentStatus status; // Текущий статус записи (ACTIVE, COMPLETED, DROPPED)

    /**
     * Автоматически устанавливает дату записи и начальный статус (ACTIVE)
     * перед сохранением сущности.
     */
    @PrePersist
    protected void onCreate() {
        if (enrollDate == null) {
            enrollDate = LocalDateTime.now();
        }
        if (status == null) {
            status = EnrollmentStatus.ACTIVE;
        }
    }
}