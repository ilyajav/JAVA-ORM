package com.example.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDateTime;

/**
 * Сущность, представляющая отзыв (рейтинг и комментарий) студента о курсе.
 * Использует Lombok для генерации стандартных методов.
 */
@Entity
@Table(name = "course_reviews")
@Getter // Генерирует все геттеры
@Setter // Генерирует все сеттеры
@NoArgsConstructor // Генерирует конструктор без аргументов (требуется JPA)
@AllArgsConstructor // Генерирует конструктор со всеми аргументами (для Builder)
@Builder // Позволяет создавать объекты с помощью паттерна Builder
public class CourseReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Отзыв привязан к конкретному курсу. LAZY загрузка - стандарт.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    // Отзыв привязан к студенту, который его оставил.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private User student;

    @Column(nullable = false)
    private Integer rating;  // Оценка от 1 до 5

    @Column(columnDefinition = "TEXT")
    private String comment;  // Текст отзыва

    // Дата и время создания отзыва
    private LocalDateTime createdAt;

    /**
     * Автоматически устанавливает дату и время создания перед сохранением в базу данных.
     */
    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }
}
