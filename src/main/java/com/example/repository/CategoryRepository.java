package com.example.repository;

import com.example.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Репозиторий для управления сущностями Category (Категория курса).
 * Предоставляет стандартные CRUD операции и методы для поиска по имени.
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    /**
     * Находит категорию по ее уникальному названию.
     * @param name Название категории.
     * @return Optional<Category>, содержащий категорию, если она найдена.
     */
    Optional<Category> findByName(String name);
}