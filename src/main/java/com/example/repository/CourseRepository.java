package com.example.repository;

import com.example.model.Course;
import com.example.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Репозиторий для управления сущностями Course (Учебный курс).
 * Предоставляет стандартные CRUD операции и пользовательские методы поиска.
 */
@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    /**
     * Находит все курсы, которые ведет указанный преподаватель.
     * @param teacher Объект User с ролью TEACHER.
     * @return Список курсов.
     */
    List<Course> findByTeacher(User teacher);

    /**
     * Находит все курсы, принадлежащие указанной категории.
     * @param categoryId ID категории.
     * @return Список курсов.
     */
    List<Course> findByCategoryId(Long categoryId);

    /**
     * Находит все курсы, название которых содержит указанную строку,
     * игнорируя регистр.
     * @param title Часть названия курса для поиска.
     * @return Список курсов, соответствующих критерию поиска.
     */
    List<Course> findByTitleContainingIgnoreCase(String title);
}