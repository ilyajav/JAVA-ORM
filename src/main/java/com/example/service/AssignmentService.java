package com.example.service;

import com.example.model.Assignment;
import com.example.model.Lesson;
import com.example.model.Submission;
import com.example.model.User;
import com.example.repository.AssignmentRepository;
import com.example.repository.SubmissionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Сервис для управления сущностями Заданий (Assignment) и Решений (Submission).
 * Обеспечивает бизнес-логику для создания, получения, сдачи и оценивания заданий.
 */
@Service
@Transactional
public class AssignmentService {

    private final AssignmentRepository assignmentRepository;
    private final SubmissionRepository submissionRepository;

    /**
     * Конструктор для внедрения зависимостей (AssignmentRepository и SubmissionRepository).
     * Используется конструкторная инъекция, которая является предпочтительной практикой в Spring.
     *
     * @param assignmentRepository Репозиторий для доступа к данным заданий.
     * @param submissionRepository Репозиторий для доступа к данным решений.
     */
    public AssignmentService(AssignmentRepository assignmentRepository, SubmissionRepository submissionRepository) {
        this.assignmentRepository = assignmentRepository;
        this.submissionRepository = submissionRepository;
    }

    /**
     * Создает и сохраняет новое задание в системе.
     *
     * @param title Заголовок задания.
     * @param description Описание задания.
     * @param lesson Урок, к которому привязано задание.
     * @param dueDate Срок сдачи задания.
     * @param maxScore Максимально возможный балл за задание.
     * @return Созданный и сохраненный объект Assignment.
     */
    public Assignment createAssignment(String title, String description, Lesson lesson,
                                       LocalDateTime dueDate, Integer maxScore) {
        Assignment assignment = new Assignment(title, description, lesson);
        assignment.setDueDate(dueDate);
        assignment.setMaxScore(maxScore);
        return assignmentRepository.save(assignment);
    }

    /**
     * Получает задание по его уникальному идентификатору.
     *
     * @param id Идентификатор задания.
     * @return Optional<Assignment>, содержащий задание, если оно найдено.
     */
    @Transactional(readOnly = true)
    public Optional<Assignment> getAssignmentById(Long id) {
        return assignmentRepository.findById(id);
    }

    /**
     * Получает список всех заданий, привязанных к определенному уроку.
     *
     * @param lesson Урок, для которого нужно получить задания.
     * @return Список объектов Assignment.
     */
    @Transactional(readOnly = true)
    public List<Assignment> getAssignmentsByLesson(Lesson lesson) {
        return assignmentRepository.findByLesson(lesson);
    }

    /**
     * Студент сдает выполненное задание.
     *
     * @param assignmentId Идентификатор задания, которое сдается.
     * @param student Объект пользователя, который сдает задание.
     * @param content Содержимое решения (текст, ссылка и т.д.).
     * @return Сохраненный объект Submission (Решение).
     * @throws RuntimeException если задание не найдено или студент уже сдавал это задание.
     */
    public Submission submitAssignment(Long assignmentId, User student, String content) {
        Assignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new RuntimeException("Задание не найдено")); // NOTE: Рекомендуется использовать собственное исключение (e.g., AssignmentNotFoundException).

        Optional<Submission> existingSubmission = submissionRepository
                .findByStudentAndAssignment(student, assignment);

        if (existingSubmission.isPresent()) {
            throw new RuntimeException("Студент уже сдал это задание"); // NOTE: Рекомендуется использовать собственное исключение (e.g., DuplicateSubmissionException).
        }

        Submission submission = new Submission(assignment, student, content);
        return submissionRepository.save(submission);
    }

    /**
     * Оценивает сданное решение, проставляя балл и добавляя обратную связь.
     *
     * @param submissionId Идентификатор решения, которое нужно оценить.
     * @param score Набранный балл.
     * @param feedback Обратная связь/комментарий преподавателя.
     * @return Обновленный и сохраненный объект Submission.
     * @throws RuntimeException если решение не найдено.
     */
    public Submission gradeSubmission(Long submissionId, Integer score, String feedback) {
        Submission submission = submissionRepository.findById(submissionId)
                .orElseThrow(() -> new RuntimeException("Решение не найдено")); // NOTE: Рекомендуется использовать собственное исключение (e.g., SubmissionNotFoundException).

        submission.setScore(score);
        submission.setFeedback(feedback);
        return submissionRepository.save(submission);
    }

    /**
     * Получает список всех решений, сданных для определенного задания.
     *
     * @param assignment Задание, для которого нужно получить решения.
     * @return Список объектов Submission.
     */
    @Transactional(readOnly = true)
    public List<Submission> getSubmissionsByAssignment(Assignment assignment) {
        return submissionRepository.findByAssignment(assignment);
    }

    /**
     * Удаляет задание по его уникальному идентификатору.
     *
     * @param id Идентификатор задания для удаления.
     */
    public void deleteAssignment(Long id) {
        assignmentRepository.deleteById(id);
    }
}