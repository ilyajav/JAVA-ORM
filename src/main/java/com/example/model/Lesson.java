package com.example.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.ArrayList;
import java.util.List;

/**
 * Сущность, представляющая отдельный урок в рамках учебного модуля.
 * Использует Lombok для генерации стандартных методов.
 */
@Entity
@Table(name = "lessons")
@Getter // Генерирует все геттеры
@Setter // Генерирует все сеттеры
@NoArgsConstructor // Генерирует конструктор без аргументов (требуется JPA)
@AllArgsConstructor // Генерирует конструктор со всеми аргументами (для Builder)
@Builder // Позволяет создавать объекты с помощью паттерна Builder
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Связь Многие к Одному: Урок принадлежит модулю. LAZY загрузка.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "module_id", nullable = false)
    private Module module;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content; // Основное текстовое содержимое урока

    private String videoUrl; // URL дополнительного видеоматериала

    // Связь Один ко Многим: Задания, связанные с этим уроком.
    // CascadeType.ALL обеспечивает удаление заданий при удалении урока.
    @OneToMany(mappedBy = "lesson", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default // Lombok: гарантирует, что эта инициализация используется при использовании Builder
    private List<Assignment> assignments = new ArrayList<>();
}
