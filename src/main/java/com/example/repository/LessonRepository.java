package com.example.repository;

import com.example.model.Lesson;
import com.example.model.Module;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Репозиторий для управления сущностями Lesson (Урок).
 * Предоставляет стандартные CRUD операции и пользовательские методы поиска.
 */
@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {

    /**
     * Находит все уроки, принадлежащие указанному учебному модулю.
     * @param module Объект Module.
     * @return Список объектов Lesson.
     */
    List<Lesson> findByModule(Module module);
}