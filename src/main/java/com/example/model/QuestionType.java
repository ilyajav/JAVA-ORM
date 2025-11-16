package com.example.model;

/**
 * Перечисление, определяющее тип вопроса в квизе.
 * Включает пользовательские описания для удобства отображения в UI.
 */
public enum QuestionType {

    SINGLE_CHOICE("Одиночный выбор"),    // Вопрос с одним правильным вариантом ответа
    MULTIPLE_CHOICE("Множественный выбор"); // Вопрос с несколькими правильными вариантами ответа

    private final String description;

    /**
     * Конструктор для инициализации описания типа вопроса.
     * @param description Человеко-понятное описание типа вопроса.
     */
    QuestionType(String description) {
        this.description = description;
    }

    /**
     * Возвращает пользовательское описание типа вопроса.
     * @return Описание типа вопроса на русском языке.
     */
    public String getDescription() {
        return description;
    }
}