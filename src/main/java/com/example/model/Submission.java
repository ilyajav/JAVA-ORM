package com.example.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDateTime;

/**
 * Сущность, представляющая сданную работу/задание студента (Submission).
 * Использует Lombok для генерации стандартных методов.
 */
@Entity
@Table(name = "submissions")
@Getter // Генерирует все геттеры
@Setter // Генерирует все сеттеры
@NoArgsConstructor // Генерирует конструктор без аргументов (требуется JPA)
@AllArgsConstructor // Генерирует конструктор со всеми аргументами (для Builder)
@Builder // Позволяет создавать объекты с помощью паттерна Builder
public class Submission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Связь Многие к Одному: Сдача относится к конкретному заданию.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assignment_id", nullable = false)
    private Assignment assignment;

    // Связь Многие к Одному: Студент, который сдал работу.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private User student;

    @Column(nullable = false)
    private LocalDateTime submittedAt;  // Дата и время отправки

    @Column(columnDefinition = "TEXT")
    private String content; // Содержимое сданной работы (текст, ссылка и т.д.)

    private Integer score; // Оценка за задание

    @Column(columnDefinition = "TEXT")
    private String feedback; // Комментарии преподавателя

    /**
     * Автоматически устанавливает дату отправки перед сохранением в базу данных.
     * Это гарантирует, что поле не будет null и всегда будет содержать точное время сохранения.
     */
    @PrePersist
    protected void onCreate() {
        if (submittedAt == null) {
            submittedAt = LocalDateTime.now();
        }
    }
}