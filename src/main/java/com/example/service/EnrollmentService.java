package com.example.service;

import com.example.model.Course;
import com.example.model.Enrollment;
import com.example.model.EnrollmentStatus;
import com.example.model.User;
import com.example.repository.EnrollmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Сервис для управления записями студентов на курсы (Enrollment).
 * Обеспечивает бизнес-логику для регистрации, получения и управления статусом записи.
 */
@Service
@Transactional
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;

    /**
     * Конструктор для внедрения зависимостей.
     * Использует конструкторную инъекцию для EnrollmentRepository.
     *
     * @param enrollmentRepository Репозиторий для доступа к данным записей.
     */
    public EnrollmentService(EnrollmentRepository enrollmentRepository) {
        this.enrollmentRepository = enrollmentRepository;
    }

    /**
     * Записывает студента на указанный курс.
     *
     * @param student Объект User, представляющий студента.
     * @param course Объект Course, на который происходит запись.
     * @return Созданный и сохраненный объект Enrollment.
     * @throws IllegalStateException Если студент уже записан на этот курс.
     */
    public Enrollment enrollStudent(User student, Course course) {
        if (enrollmentRepository.existsByStudentAndCourse(student, course)) {
            // Использование более специфичного исключения, чем общий RuntimeException
            throw new IllegalStateException("Студент уже записан на этот курс");
        }

        Enrollment enrollment = new Enrollment(student, course);
        return enrollmentRepository.save(enrollment);
    }

    /**
     * Получает запись на курс по ее уникальному идентификатору.
     *
     * @param id Идентификатор записи.
     * @return Optional<Enrollment>, содержащий запись, если она найдена.
     */
    @Transactional(readOnly = true)
    public Optional<Enrollment> getEnrollmentById(Long id) {
        return enrollmentRepository.findById(id);
    }

    /**
     * Получает список всех записей, связанных с конкретным студентом.
     *
     * @param student Объект User (студент).
     * @return Список объектов Enrollment.
     */
    @Transactional(readOnly = true)
    public List<Enrollment> getEnrollmentsByStudent(User student) {
        return enrollmentRepository.findByStudent(student);
    }

    /**
     * Получает список всех записей на конкретный курс.
     *
     * @param course Объект Course.
     * @return Список объектов Enrollment.
     */
    @Transactional(readOnly = true)
    public List<Enrollment> getEnrollmentsByCourse(Course course) {
        return enrollmentRepository.findByCourse(course);
    }

    /**
     * Обновляет статус записи (Enrollment) по ее ID.
     *
     * @param enrollmentId Идентификатор записи, которую нужно обновить.
     * @param status Новый статус записи (например, COMPLETED, DROPPED).
     * @return Обновленный объект Enrollment.
     * @throws RuntimeException Если запись с указанным ID не найдена.
     */
    public Enrollment updateEnrollmentStatus(Long enrollmentId, EnrollmentStatus status) {
        Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new RuntimeException("Запись не найдена"));
        enrollment.setStatus(status);
        return enrollmentRepository.save(enrollment);
    }

    /**
     * Изменяет статус записи студента на DROPPED (отчислен/покинул курс).
     *
     * @param enrollmentId Идентификатор записи.
     */
    public void dropStudent(Long enrollmentId) {
        updateEnrollmentStatus(enrollmentId, EnrollmentStatus.DROPPED);
    }

    /**
     * Полностью удаляет запись на курс из системы.
     *
     * @param id Идентификатор записи для удаления.
     */
    public void deleteEnrollment(Long id) {
        enrollmentRepository.deleteById(id);
    }
}