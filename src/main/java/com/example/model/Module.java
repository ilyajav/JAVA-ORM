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
 * Сущность, представляющая учебный модуль в составе курса.
 * Модуль может содержать уроки и один связанный квиз.
 * Использует Lombok для генерации стандартных методов.
 */
@Entity
@Table(name = "modules")
@Getter // Генерирует все геттеры
@Setter // Генерирует все сеттеры
@NoArgsConstructor // Генерирует конструктор без аргументов (требуется JPA)
@AllArgsConstructor // Генерирует конструктор со всеми аргументами (для Builder)
@Builder // Позволяет создавать объекты с помощью паттерна Builder
public class Module {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Связь Многие к Одному: Модуль принадлежит курсу. LAZY загрузка.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    private Integer orderIndex; // Порядковый номер модуля в курсе

    // Связь Один ко Многим: Уроки в модуле. Каскадное удаление.
    @OneToMany(mappedBy = "module", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default // Lombok: гарантирует, что эта инициализация используется при использовании Builder
    private List<Lesson> lessons = new ArrayList<>();

    // Связь Один к Одному: Квиз (тест) для модуля. Каскадное удаление.
    @OneToOne(mappedBy = "module", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Quiz quiz;
}