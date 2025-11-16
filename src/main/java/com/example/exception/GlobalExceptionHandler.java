package com.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Helper method to construct a consistent error response map.
     */
    private Map<String, Object> buildErrorResponse(HttpStatus status, String errorName, String message) {
        Map<String, Object> error = new HashMap<>();
        error.put("timestamp", LocalDateTime.now());
        error.put("status", status.value());
        error.put("error", errorName);
        error.put("message", message);
        return error;
    }

    /**
     * Handles 400 Bad Request exceptions: IllegalArgumentException and IllegalStateException.
     * Эти исключения часто возникают при ошибках валидации или нарушениях бизнес-правил (например, дублирование email).
     */
    @ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class})
    public ResponseEntity<Map<String, Object>> handleBadRequestExceptions(RuntimeException e) {
        Map<String, Object> errorBody = buildErrorResponse(
                HttpStatus.BAD_REQUEST,
                e instanceof IllegalArgumentException ? "Invalid Argument" : "Illegal State (Business Rule Violation)",
                e.getMessage()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorBody);
    }

    /**
     * Handles 404 Not Found exceptions.
     * Используется, когда запрашиваемый ресурс (пользователь, курс, запись) не найден.
     */
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Map<String, Object>> handleNoSuchElementException(NoSuchElementException e) {
        Map<String, Object> errorBody = buildErrorResponse(
                HttpStatus.NOT_FOUND,
                "Resource Not Found",
                e.getMessage()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorBody);
    }

    /**
     * General catch-all for all other uncaught exceptions (500 Internal Server Error).
     * Это обеспечивает возврат стандартизированного тела JSON при непредвиденных серверных ошибках.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleAllUncaughtExceptions(Exception e) {
        // В реальном приложении здесь обязательно должно быть логирование исключения
        System.err.println("Uncaught exception: " + e.getClass().getSimpleName() + " - " + e.getMessage());

        Map<String, Object> errorBody = buildErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Internal Server Error",
                "An unexpected error occurred. Please try again later."
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorBody);
    }
}