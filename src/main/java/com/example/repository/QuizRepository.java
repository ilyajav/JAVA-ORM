package com.example.repository;

import com.example.model.Module;
import com.example.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Репозиторий для управления сущностями Quiz (Квиз/Тест).
 * Предоставляет стандартные CRUD операции и пользовательские методы поиска.
 */
@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {

    /**
     * Находит квиз, связанный с указанным учебным модулем.
     * Поскольку связь OneToOne, возвращается Optional.
     * @param module Объект Module, к которому привязан квиз.
     * @return Optional<Quiz>, содержащий квиз, если он найден.
     */
    Optional<Quiz> findByModule(Module module);
}