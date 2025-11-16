package com.example.controller;

import com.example.dto.EnrollRequest; // Import DTO
import com.example.model.Enrollment;
import com.example.model.EnrollmentStatus;
import com.example.service.CourseService;
import com.example.service.EnrollmentService;
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
 * REST controller for managing course enrollments.
 * Uses constructor injection and specific exception handling for better control over HTTP status codes.
 */
@RestController
@RequestMapping("/api/enrollments")
@Tag(name = "Enrollments", description = "API для управления записями студентов на курсы")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;
    private final UserService userService;
    private final CourseService courseService;

    /**
     * Constructor Injection (recommended way to inject dependencies).
     */
    public EnrollmentController(EnrollmentService enrollmentService, UserService userService, CourseService courseService) {
        this.enrollmentService = enrollmentService;
        this.userService = userService;
        this.courseService = courseService;
    }

    @Operation(
            summary = "Записать студента на курс",
            description = "Создает новую запись студента на курс. Проверяет, существуют ли студент и курс, а также уникальность записи."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Студент успешно записан на курс"),
            @ApiResponse(responseCode = "404", description = "Студент или курс не найден"),
            @ApiResponse(responseCode = "400", description = "Студент уже записан на этот курс (нарушение бизнес-логики)")
    })
    @PostMapping
    public ResponseEntity<Enrollment> enrollStudent(@RequestBody EnrollRequest request) {
        try {
            // Delegates the enrollment to the service after verifying user/course existence.
            // Assumes the service throws IllegalStateException for duplicate enrollment attempts.
            Enrollment enrollment = enrollmentService.enrollStudent(
                    userService.getUserById(request.getStudentId())
                            .orElseThrow(() -> new NoSuchElementException("Студент не найден")),
                    courseService.getCourseById(request.getCourseId())
                            .orElseThrow(() -> new NoSuchElementException("Курс не найден"))
            );

            return ResponseEntity.status(HttpStatus.CREATED).body(enrollment);

        } catch (NoSuchElementException e) {
            // Handles both "Student not found" and "Course not found"
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (IllegalStateException e) {
            // Handles business logic errors, e.g., "Student is already enrolled"
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @Operation(
            summary = "Получить запись по ID",
            description = "Возвращает информацию о записи на курс по идентификатору"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Запись найдена"),
            @ApiResponse(responseCode = "404", description = "Запись не найдена")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Enrollment> getEnrollmentById(
            @Parameter(description = "ID записи", required = true) @PathVariable Long id) {
        return enrollmentService.getEnrollmentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Получить все записи студента",
            description = "Возвращает список всех курсов, на которые записан указанный студент. Если студент не найден, возвращается 404."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список записей возвращен"),
            @ApiResponse(responseCode = "404", description = "Студент не найден")
    })
    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Enrollment>> getEnrollmentsByStudent(
            @Parameter(description = "ID студента", required = true) @PathVariable Long studentId) {

        // Return 404 NOT_FOUND if the student does not exist.
        return userService.getUserById(studentId)
                .map(enrollmentService::getEnrollmentsByStudent)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Изменить статус записи",
            description = "Обновляет статус записи на курс (ACTIVE, COMPLETED, DROPPED). Использует строковое представление статуса."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Статус успешно обновлен"),
            @ApiResponse(responseCode = "404", description = "Запись с указанным ID не найдена"),
            @ApiResponse(responseCode = "400", description = "Невалидный статус (передано недопустимое строковое значение)")
    })
    @PutMapping("/{id}/status")
    public ResponseEntity<Enrollment> updateStatus(
            @Parameter(description = "ID записи", required = true) @PathVariable Long id,
            @Parameter(description = "Новый статус (ACTIVE, COMPLETED, DROPPED)", required = true)
            @RequestParam String status) {
        try {
            // 1. Convert string to enum (throws IllegalArgumentException if invalid)
            EnrollmentStatus enrollmentStatus = EnrollmentStatus.valueOf(status.toUpperCase());

            // 2. Update status (assuming service throws NoSuchElementException if ID not found)
            Enrollment updated = enrollmentService.updateEnrollmentStatus(id, enrollmentStatus);

            return ResponseEntity.ok(updated);

        } catch (IllegalArgumentException e) {
            // Invalid enum value passed in the query parameter (400)
            return ResponseEntity.badRequest().build();
        } catch (NoSuchElementException e) {
            // Enrollment with the given ID was not found (404)
            return ResponseEntity.notFound().build();
        }
    }
}