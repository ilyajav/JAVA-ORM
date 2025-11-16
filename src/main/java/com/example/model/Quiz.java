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
 * Сущность, представляющая квиз (тест) для учебного модуля.
 * Использует Lombok для генерации стандартных методов.
 */
@Entity
@Table(name = "quizzes")
@Getter // Генерирует все геттеры
@Setter // Генерирует все сеттеры
@NoArgsConstructor // Генерирует конструктор без аргументов (требуется JPA)
@AllArgsConstructor // Генерирует конструктор со всеми аргументами (для Builder)
@Builder // Позволяет создавать объекты с помощью паттерна Builder
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Связь Один к Одному: Квиз привязан к конкретному модулю.
    // unique = true гарантирует, что у модуля может быть только один квиз.
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "module_id", nullable = false, unique = true)
    private Module module;

    @Column(nullable = false)
    private String title;

    private Integer timeLimit; // Ограничение времени на прохождение квиза (в минутах)

    // Связь Один ко Многим: Вопросы квиза. Каскадное удаление вопросов.
    @OneToMany(mappedBy = "quiz", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default // Lombok: гарантирует, что эта инициализация используется при использовании Builder
    private List<Question> questions = new ArrayList<>();

    // Связь Один ко Многим: Попытки сдачи этого квиза студентами.
    @OneToMany(mappedBy = "quiz", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<QuizSubmission> quizSubmissions = new ArrayList<>();
}
