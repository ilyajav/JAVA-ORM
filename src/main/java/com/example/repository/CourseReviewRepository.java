package com.example.repository;

import com.example.model.Course;
import com.example.model.CourseReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Репозиторий для управления сущностями CourseReview (Отзыв о курсе).
 * Предоставляет стандартные CRUD операции и пользовательские методы поиска.
 */
@Repository
public interface CourseReviewRepository extends JpaRepository<CourseReview, Long> {

    /**
     * Находит все отзывы, относящиеся к указанному курсу.
     * @param course Объект Course, для которого необходимо найти отзывы.
     * @return Список объектов CourseReview.
     */
    List<CourseReview> findByCourse(Course course);
}