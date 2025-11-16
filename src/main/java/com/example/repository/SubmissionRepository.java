package com.example.repository;

import com.example.model.Assignment;
import com.example.model.Submission;
import com.example.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Репозиторий для управления сущностями Submission (Сданная работа/задание).
 * Предоставляет стандартные CRUD операции и пользовательские методы поиска.
 */
@Repository
public interface SubmissionRepository extends JpaRepository<Submission, Long> {

    /**
     * Находит все сданные работы, выполненные указанным студентом.
     * @param student Объект User с ролью STUDENT.
     * @return Список объектов Submission.
     */
    List<Submission> findByStudent(User student);

    /**
     * Находит все сданные работы, относящиеся к указанному заданию.
     * @param assignment Объект Assignment.
     * @return Список объектов Submission.
     */
    List<Submission> findByAssignment(Assignment assignment);

    /**
     * Находит уникальную сданную работу, выполненную указанным студентом
     * для конкретного задания.
     * @param student Объект User.
     * @param assignment Объект Assignment.
     * @return Optional<Submission>, содержащий сданную работу, если она найдена.
     */
    Optional<Submission> findByStudentAndAssignment(User student, Assignment assignment);
}