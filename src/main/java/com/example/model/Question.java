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
 * Сущность, представляющая вопрос в рамках квиза (Quiz).
 * Вопрос имеет тип и набор вариантов ответа.
 * Использует Lombok для генерации стандартных методов.
 */
@Entity
@Table(name = "questions")
@Getter // Генерирует все геттеры
@Setter // Генерирует все сеттеры
@NoArgsConstructor // Генерирует конструктор без аргументов (требуется JPA)
@AllArgsConstructor // Генерирует конструктор со всеми аргументами (для Builder)
@Builder // Позволяет создавать объекты с помощью паттерна Builder
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Связь Многие к Одному: Вопрос принадлежит квизу. LAZY загрузка.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id", nullable = false)
    private Quiz quiz;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String text; // Текст вопроса

    // Тип вопроса (например, SINGLE_CHOICE, MULTI_CHOICE)
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private QuestionType type;

    // Связь Один ко Многим: Варианты ответа для вопроса.
    // Каскадное удаление вариантов ответа при удалении вопроса.
    @OneToMany(mappedBy = "question", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default // Lombok: гарантирует, что эта инициализация используется при использовании Builder
    private List<AnswerOption> options = new ArrayList<>();

    /**
     * Вспомогательный конструктор, используемый для создания вопроса без ID.
     * @param text Текст вопроса
     * @param type Тип вопроса
     * @param quiz Квиз, к которому относится вопрос
     */
    public Question(String text, QuestionType type, Quiz quiz) {
        this.text = text;
        this.type = type;
        this.quiz = quiz;
    }
}

