package com.example.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

/**
 * Сущность, представляющая дополнительный профиль пользователя
 * (например, биографию, аватар, телефон).
 * Использует Lombok для генерации стандартных методов.
 */
@Entity
@Table(name = "profiles")
@Getter // Генерирует все геттеры
@Setter // Генерирует все сеттеры
@NoArgsConstructor // Генерирует конструктор без аргументов (требуется JPA)
@AllArgsConstructor // Генерирует конструктор со всеми аргументами (для Builder)
@Builder // Позволяет создавать объекты с помощью паттерна Builder
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Связь Один к Одному с User. Поле user_id будет первичным ключом для профиля.
    // unique = true гарантирует, что у одного пользователя может быть только один профиль.
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(columnDefinition = "TEXT")
    private String bio; // Биография или краткое описание

    private String avatarUrl;  // URL аватара

    private String phone;  // Телефон
}
