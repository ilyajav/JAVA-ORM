package com.example.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


/**
 * Сущность, представляющая задание или домашнюю работу в рамках урока (Lesson).
 * Использует Lombok для автоматической генерации геттеров, сеттеров и конструкторов.
 */
@Entity
@Table(name = "assignments")
@Getter // Генерирует все геттеры
@Setter // Генерирует все сеттеры
@NoArgsConstructor // Генерирует конструктор без аргументов (требуется JPA)
@AllArgsConstructor // Генерирует конструктор со всеми аргументами
@Builder // Позволяет создавать объекты с помощью паттерна Builder
public class Assignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Связь многие к одному - урок. LAZY загрузка - стандартная практика.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lesson_id", nullable = false)
    private Lesson lesson;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    private LocalDateTime dueDate; // Срок сдачи задания

    private Integer maxScore; // Максимальный балл за задание

    // Связь один ко многим с Submission (сдачами работ).
    // CascadeType.ALL обеспечивает, что при удалении задания удаляются и все связанные сдачи.
    @OneToMany(mappedBy = "assignment", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Submission> submissions = new ArrayList<>();
}
