package com.example.repository;

import com.example.model.Assignment;
import com.example.model.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Репозиторий для управления сущностями Assignment (Задание).
 * Предоставляет стандартные CRUD операции и пользовательские методы поиска.
 */
@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Long> {

    /**
     * Находит все задания, принадлежащие указанному уроку.
     * @param lesson Объект Lesson, для которого необходимо найти задания.
     * @return Список объектов Assignment.
     */
    List<Assignment> findByLesson(Lesson lesson);
}