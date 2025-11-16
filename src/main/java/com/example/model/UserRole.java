package com.example.model;

/**
 * Перечисление ролей пользователя в системе.
 * Включает пользовательские описания для удобства отображения в UI или API ответах.
 */
public enum UserRole {

    STUDENT("Студент"), // Пользователь, проходящий курсы
    TEACHER("Преподаватель"), // Пользователь, создающий и ведущий курсы
    ADMIN("Администратор"); // Пользователь с полными правами управления системой

    private final String description;

    /**
     * Конструктор для инициализации описания роли.
     * @param description Человеко-понятное описание роли.
     */
    UserRole(String description) {
        this.description = description;
    }

    /**
     * Возвращает пользовательское описание роли.
     * @return Описание роли на русском языке.
     */
    public String getDescription() {
        return description;
    }
}