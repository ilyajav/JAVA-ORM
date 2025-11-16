package com.example.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Сущность, представляющая учебный курс.
 * Использует Lombok для генерации стандартных методов.
 */
@Entity
@Table(name = "courses")
@Getter // Генерирует все геттеры
@Setter // Генерирует все сеттеры
@NoArgsConstructor // Генерирует конструктор без аргументов (требуется JPA)
@AllArgsConstructor // Генерирует конструктор со всеми аргументами (для Builder)
@Builder // Позволяет создавать объекты с помощью паттерна Builder
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    private Integer duration; // Продолжительность курса в часах/днях

    private LocalDate startDate; // Дата начала курса

    // Связь Многие к Одному: Категория курса. LAZY загрузка.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    // Связь Многие к Одному: Преподаватель (обязательное поле). LAZY загрузка.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id", nullable = false)
    private User teacher;

    // Связь Один ко Многим: Модули курса. Каскадное сохранение и удаление модулей при удалении курса.
    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default // Lombok: гарантирует, что эта инициализация используется при использовании Builder
    private List<Module> modules = new ArrayList<>();

    // Связь Один ко Многим: Записи студентов на курс.
    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY)
    @Builder.Default
    private List<Enrollment> enrollments = new ArrayList<>();

    // Связь Один ко Многим: Отзывы о курсе.
    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY)
    @Builder.Default
    private List<CourseReview> reviews = new ArrayList<>();

    // Связь Многие ко Многим: Теги (ключевые слова).
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "course_tag",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    @Builder.Default
    private Set<Tag> tags = new HashSet<>();

    /**
     * Helper method to add a module to the course while maintaining bidirectional relationship.
     * @param module The module to add.
     */
    public void addModule(Module module) {
        modules.add(module);
        module.setCourse(this);
    }
}