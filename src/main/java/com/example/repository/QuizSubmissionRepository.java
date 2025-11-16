package com.example.repository;

import com.example.model.Quiz;
import com.example.model.QuizSubmission;
import com.example.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Репозиторий для управления сущностями QuizSubmission (Попытка сдачи квиза).
 * Предоставляет стандартные CRUD операции и методы для поиска по квизу и студенту.
 */
@Repository
public interface QuizSubmissionRepository extends JpaRepository<QuizSubmission, Long> {

    /**
     * Находит все попытки сдачи, связанные с указанным квизом.
     * @param quiz Объект Quiz.
     * @return Список объектов QuizSubmission.
     */
    List<QuizSubmission> findByQuiz(Quiz quiz);

    /**
     * Находит все попытки сдачи, выполненные указанным студентом.
     * @param student Объект User с ролью STUDENT.
     * @return Список объектов QuizSubmission.
     */
    List<QuizSubmission> findByStudent(User student);

    /**
     * Находит все попытки сдачи конкретного квиза указанным студентом,
     * отсортированные по дате сдачи.
     * @param quiz Объект Quiz.
     * @param student Объект User.
     * @return Список объектов QuizSubmission, отсортированный по дате создания.
     */
    List<QuizSubmission> findByQuizAndStudentOrderBySubmittedAtDesc(Quiz quiz, User student);
}