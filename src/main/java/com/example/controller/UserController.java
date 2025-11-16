package com.example.controller;

import com.example.dto.CreateUserRequest; // Используем внешний DTO
import com.example.model.User;
import com.example.model.UserRole;
import com.example.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * REST controller for managing users (students, teachers, administrators).
 * Uses constructor injection and specific exception handling for better control over HTTP status codes.
 */
@RestController
@RequestMapping("/api/users")
@Tag(name = "Users", description = "API для управления пользователями (студенты, преподаватели, администраторы)")
public class UserController {

    private final UserService userService;

    /**
     * Constructor Injection (recommended way to inject dependencies).
     */
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(
            summary = "Создать нового пользователя",
            description = "Создает нового пользователя (студента, преподавателя или администратора). Требуется валидный Email и имя."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Пользователь успешно создан"),
            @ApiResponse(responseCode = "400", description = "Невалидные данные (например, ошибка валидации DTO) или пользователь с таким email уже существует (ошибка бизнес-логики)")
    })
    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody CreateUserRequest request) {
        try {
            // Assumes service throws IllegalStateException if email already exists
            User user = userService.createUser(request.getName(), request.getEmail(), request.getRole());
            return ResponseEntity.status(HttpStatus.CREATED).body(user);
        } catch (IllegalStateException e) {
            // Handles business logic error (e.g., duplicate email)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (RuntimeException e) {
            // Catch-all for unexpected errors
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @Operation(
            summary = "Получить пользователя по ID",
            description = "Возвращает информацию о пользователе по его идентификатору"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь найден"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден")
    })
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(
            @Parameter(description = "ID пользователя", required = true) @PathVariable Long id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Получить всех пользователей",
            description = "Возвращает список всех пользователей системы"
    )
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @Operation(
            summary = "Получить пользователей по роли",
            description = "Возвращает список пользователей с указанной ролью (STUDENT, TEACHER, ADMIN)"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список пользователей возвращен"),
            @ApiResponse(responseCode = "400", description = "Невалидная роль (передано недопустимое строковое значение)")
    })
    @GetMapping("/role/{role}")
    public ResponseEntity<List<User>> getUsersByRole(
            @Parameter(description = "Роль пользователя (STUDENT, TEACHER, ADMIN)", required = true)
            @PathVariable String role) {
        try {
            // Converts string path variable to enum (throws IllegalArgumentException if invalid)
            UserRole userRole = UserRole.valueOf(role.toUpperCase());
            List<User> users = userService.getUsersByRole(userRole);
            return ResponseEntity.ok(users);
        } catch (IllegalArgumentException e) {
            // Invalid enum value passed in the path variable
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @Operation(
            summary = "Обновить существующего пользователя",
            description = "Обновляет данные пользователя по его ID. Все поля должны быть предоставлены в теле запроса."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь успешно обновлен"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден"),
            @ApiResponse(responseCode = "400", description = "Невалидные данные")
    })
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(
            @Parameter(description = "ID пользователя для обновления", required = true) @PathVariable Long id,
            @RequestBody User user) {

        user.setId(id);

        try {
            User updated = userService.updateUser(user);
            return ResponseEntity.ok(updated);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @Operation(
            summary = "Удалить пользователя по ID",
            description = "Удаляет пользователя из системы по его идентификатору"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Пользователь успешно удален (Нет содержимого)"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(
            @Parameter(description = "ID пользователя для удаления", required = true) @PathVariable Long id) {

        try {
            userService.deleteUser(id);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }
}