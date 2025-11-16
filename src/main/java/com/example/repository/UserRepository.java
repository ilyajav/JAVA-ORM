package com.example.repository;

import com.example.model.User;
import com.example.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Репозиторий для управления сущностями User (Пользователь).
 * Предоставляет стандартные CRUD операции и пользовательские методы поиска,
 * основанные на email и роли пользователя.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Находит пользователя по его уникальному адресу электронной почты.
     * @param email Адрес электронной почты пользователя.
     * @return Optional<User>, содержащий пользователя, если он найден.
     */
    Optional<User> findByEmail(String email);

    /**
     * Находит всех пользователей с указанной ролью (например, STUDENT, TEACHER).
     * @param role Роль пользователя (из enum UserRole).
     * @return Список объектов User.
     */
    List<User> findByRole(UserRole role);

    /**
     * Проверяет, существует ли пользователь с указанным адресом электронной почты.
     * Это полезно при регистрации для избежания дубликатов.
     * @param email Адрес электронной почты для проверки.
     * @return true, если пользователь с таким email существует, иначе false.
     */
    boolean existsByEmail(String email);
}