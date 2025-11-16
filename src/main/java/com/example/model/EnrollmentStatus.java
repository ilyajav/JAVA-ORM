package com.example.model;

/**
 * Перечисление статусов записи студента на курс.
 * Включает пользовательские описания для удобства отображения в UI или API ответах.
 */
public enum EnrollmentStatus {

    ACTIVE("Активен"), // Студент проходит курс
    COMPLETED("Завершен"), // Студент успешно завершил курс
    DROPPED("Отчислен"); // Студент покинул курс (бросил)

    private final String description;

    /**
     * Конструктор для инициализации описания статуса.
     * @param description Человеко-понятное описание статуса.
     */
    EnrollmentStatus(String description) {
        this.description = description;
    }

    /**
     * Возвращает пользовательское описание статуса.
     * @return Описание статуса на русском языке.
     */
    public String getDescription() {
        return description;
    }
}