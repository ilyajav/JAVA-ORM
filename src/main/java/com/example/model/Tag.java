package com.example.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.HashSet;
import java.util.Set;

/**
 * Сущность, представляющая тег (ключевое слово) для курсов.
 * Использует Lombok для генерации стандартных методов.
 */
@Entity
@Table(name = "tags")
@Getter // Генерирует все геттеры
@Setter // Генерирует все сеттеры
@NoArgsConstructor // Генерирует конструктор без аргументов (требуется JPA)
@AllArgsConstructor // Генерирует конструктор со всеми аргументами (для Builder)
@Builder // Позволяет создавать объекты с помощью паттерна Builder
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name; // Название тега (например, "Java", "Frontend")

    // Связь Многие ко Многим: Курсы, связанные с этим тегом. mappedBy указывает
    // на поле 'tags' в сущности Course. LAZY загрузка.
    @ManyToMany(mappedBy = "tags", fetch = FetchType.LAZY)
    @Builder.Default // Lombok: гарантирует, что эта инициализация используется при использовании Builder
    private Set<Course> courses = new HashSet<>();

    /**
     * Вспомогательный конструктор для создания тега по имени.
     * @param name Название тега.
     */
    public Tag(String name) {
        this.name = name;
    }
}

