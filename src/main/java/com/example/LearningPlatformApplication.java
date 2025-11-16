package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Главный класс приложения "Learning Platform" (Учебная платформа).
 * Обеспечивает точку входа для запуска Spring Boot приложения.
 */
@SpringBootApplication
public class LearningPlatformApplication {

    /**
     * Основной метод, который запускает приложение Spring Boot.
     *
     * @param args Аргументы командной строки, передаваемые при запуске.
     */
    public static void main(String[] args) {
        SpringApplication.run(LearningPlatformApplication.class, args);
    }
}