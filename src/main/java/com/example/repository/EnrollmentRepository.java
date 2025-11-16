package com.example.repository;

import com.example.model.Course;
import com.example.model.Enrollment;
import com.example.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Репозиторий для управления сущностями Enrollment (Запись на курс).
 * Предоставляет стандартные CRUD операции и методы для поиска по студенту и курсу.
 */
@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    /**
     * Находит все записи (курсы), на которые записан указанный студент.
     * @param student Объект User с ролью STUDENT.
     * @return Список объектов Enrollment.
     */
    List<Enrollment> findByStudent(User student);

    /**
     * Находит все записи (студентов), связанные с указанным курсом.
     * @param course Объект Course.
     * @return Список объектов Enrollment.
     */
    List<Enrollment> findByCourse(Course course);

    /**
     * Находит уникальную запись по указанному студенту и курсу.
     * Используется для получения существующей записи.
     * @param student Объект User.
     * @param course Объект Course.
     * @return Optional<Enrollment>, содержащий запись, если она существует.
     */
    Optional<Enrollment> findByStudentAndCourse(User student, Course course);

    /**
     * Проверяет наличие записи для указанного студента на конкретный курс.
     * Это более эффективный метод для проверки существования, чем findBy...
     * @param student Объект User.
     * @param course Объект Course.
     * @return true, если запись существует, иначе false.
     */
    boolean existsByStudentAndCourse(User student, Course course);
}