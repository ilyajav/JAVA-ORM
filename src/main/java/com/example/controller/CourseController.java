package com.example.controller;

import com.example.dto.CreateCourseRequest; // Import DTO
import com.example.model.Course;
import com.example.service.CourseService;
import com.example.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * REST controller for managing educational courses.
 * Handles CRUD operations and course searching.
 * Uses constructor injection for dependencies.
 */
@RestController
@RequestMapping("/api/courses")
@Tag(name = "Courses", description = "API для управления учебными курсами")
public class CourseController {

    private final CourseService courseService;
    private final UserService userService;

    /**
     * Constructor Injection (recommended way to inject dependencies).
     */
    public CourseController(CourseService courseService, UserService userService) {
        this.courseService = courseService;
        this.userService = userService;
    }

    @Operation(
            summary = "Создать новый курс",
            description = "Создает новый учебный курс. Ответственность за поиск и валидацию преподавателя делегирована сервису."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Курс успешно создан"),
            @ApiResponse(responseCode = "400", description = "Невалидные данные (например, отсутствуют обязательные поля)"),
            @ApiResponse(responseCode = "404", description = "Преподаватель с указанным ID не найден")
    })
    @PostMapping
    public ResponseEntity<Course> createCourse(@RequestBody CreateCourseRequest request) {
        try {
            // Service method now handles finding the user and creating the course
            Course course = courseService.createCourse(
                    request.getTitle(),
                    request.getDescription(),
                    request.getTeacherId() != null ?
                            userService.getUserById(request.getTeacherId())
                                    .orElseThrow(() -> new NoSuchElementException("Преподаватель не найден")) : null
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(course);
        } catch (NoSuchElementException e) {
            // Specific handling for 'Teacher not found' error (404 Not Found)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (IllegalArgumentException e) {
            // For other bad request cases (e.g., missing title, 400 Bad Request)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @Operation(
            summary = "Получить курс по ID",
            description = "Возвращает информацию о курсе по его идентификатору"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Курс найден"),
            @ApiResponse(responseCode = "404", description = "Курс не найден")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourseById(
            @Parameter(description = "ID курса", required = true) @PathVariable Long id) {
        return courseService.getCourseById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Получить все курсы",
            description = "Возвращает список всех курсов в системе"
    )
    @GetMapping
    public List<Course> getAllCourses() {
        return courseService.getAllCourses();
    }

    @Operation(
            summary = "Поиск курсов по названию",
            description = "Возвращает список курсов, название которых содержит указанный текст (поиск без учета регистра)"
    )
    @GetMapping("/search")
    public List<Course> searchCourses(
            @Parameter(description = "Текст для поиска в названии курса", required = true)
            @RequestParam String title) {
        return courseService.searchCoursesByTitle(title);
    }

    @Operation(
            summary = "Обновить существующий курс",
            description = "Обновляет данные курса по его ID. Все поля курса должны быть предоставлены в теле запроса."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Курс успешно обновлен"),
            @ApiResponse(responseCode = "404", description = "Курс не найден")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Course> updateCourse(
            @Parameter(description = "ID курса для обновления", required = true) @PathVariable Long id,
            @RequestBody Course course) {

        // Ensure the ID from the path is used for the update
        course.setId(id);

        try {
            Course updated = courseService.updateCourse(course);
            return ResponseEntity.ok(updated);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(
            summary = "Удалить курс по ID",
            description = "Удаляет курс из системы по его идентификатору"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Курс успешно удален (Нет содержимого)"),
            @ApiResponse(responseCode = "404", description = "Курс не найден")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(
            @Parameter(description = "ID курса для удаления", required = true) @PathVariable Long id) {

        try {
            courseService.deleteCourse(id);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }
}