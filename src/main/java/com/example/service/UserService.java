package com.example.service;

import com.example.model.User;
import com.example.model.UserRole;
import com.example.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Сервис для управления пользователями (User) и связанными операциями.
 * Обеспечивает бизнес-логику для создания, поиска и обновления пользователей.
 */
@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    /**
     * Конструктор для внедрения зависимостей.
     *
     * @param userRepository Репозиторий для доступа к данным пользователей.
     */
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Создает и сохраняет нового пользователя в базе данных.
     *
     * @param name Имя пользователя.
     * @param email Email пользователя (должен быть уникальным).
     * @param role Роль пользователя (например, STUDENT, TEACHER).
     * @return Сохраненный объект User.
     * @throws IllegalArgumentException Если пользователь с указанным email уже существует.
     */
    public User createUser(String name, String email, UserRole role) {
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Пользователь с email " + email + " уже существует");
        }

        User user = new User(name, email, role);
        return userRepository.save(user);
    }

    /**
     * Получает пользователя по его уникальному идентификатору.
     *
     * @param id Идентификатор пользователя.
     * @return Optional<User>, содержащий пользователя, если он найден.
     */
    @Transactional(readOnly = true)
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    /**
     * Получает пользователя по его уникальному email.
     *
     * @param email Email пользователя.
     * @return Optional<User>, содержащий пользователя, если он найден.
     */
    @Transactional(readOnly = true)
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Получает список всех зарегистрированных пользователей.
     *
     * @return Список объектов User.
     */
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Получает список пользователей по заданной роли.
     *
     * @param role Роль пользователя.
     * @return Список объектов User, соответствующих роли.
     */
    @Transactional(readOnly = true)
    public List<User> getUsersByRole(UserRole role) {
        return userRepository.findByRole(role);
    }

    /**
     * Обновляет данные существующего пользователя.
     * Примечание: Этот метод используется для сохранения уже извлеченного и измененного объекта.
     *
     * @param user Объект User для сохранения/обновления.
     * @return Обновленный и сохраненный объект User.
     */
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    /**
     * Удаляет пользователя по его уникальному идентификатору.
     *
     * @param id Идентификатор пользователя для удаления.
     */
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}