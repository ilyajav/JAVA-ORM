package com.example.service;

import com.example.model.Quiz;
import com.example.model.QuizSubmission;
import com.example.model.User;
import com.example.repository.QuizRepository;
import com.example.repository.QuizSubmissionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * Сервис для управления тестами (Quiz) и их прохождениями (QuizSubmission).
 * Обеспечивает бизнес-логику для создания, получения и обработки результатов тестов.
 */
@Service
@Transactional
public class QuizService {

    private final QuizRepository quizRepository;
    private final QuizSubmissionRepository quizSubmissionRepository;

    /**
     * Конструктор для внедрения зависимостей. Использует конструкторную инъекцию.
     *
     * @param quizRepository Репозиторий для доступа к данным тестов.
     * @param quizSubmissionRepository Репозиторий для доступа к данным прохождений тестов.
     */
    public QuizService(QuizRepository quizRepository, QuizSubmissionRepository quizSubmissionRepository) {
        this.quizRepository = quizRepository;
        this.quizSubmissionRepository = quizSubmissionRepository;
    }

    /**
     * Создает и сохраняет новый тест в базе данных.
     *
     * @param title Заголовок теста.
     * @param timeLimit Ограничение по времени в минутах.
     * @return Сохраненный объект Quiz.
     */
    public Quiz createQuiz(String title, Integer timeLimit) {
        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setTimeLimit(timeLimit);
        return quizRepository.save(quiz);
    }

    /**
     * Получает тест по его уникальному идентификатору.
     *
     * @param id Идентификатор теста.
     * @return Optional<Quiz>, содержащий тест, если он найден.
     */
    @Transactional(readOnly = true)
    public Optional<Quiz> getQuizById(Long id) {
        return quizRepository.findById(id);
    }

    /**
     * Получает список всех доступных тестов.
     *
     * @return Список объектов Quiz.
     */
    @Transactional(readOnly = true)
    public List<Quiz> getAllQuizzes() {
        return quizRepository.findAll();
    }

    /**
     * Обрабатывает и сохраняет прохождение теста студентом.
     *
     * @param quizId Идентификатор пройденного теста.
     * @param student Объект User, представляющий студента.
     * @param score Полученный студентом балл.
     * @return Сохраненный объект QuizSubmission.
     * @throws NoSuchElementException Если тест с указанным ID не найден.
     */
    public QuizSubmission submitQuiz(Long quizId, User student, Integer score) {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new NoSuchElementException("Тест с ID " + quizId + " не найден"));

        QuizSubmission submission = new QuizSubmission(quiz, student, score);
        return quizSubmissionRepository.save(submission);
    }

    /**
     * Получает список всех прохождений (Submission) для указанного теста.
     *
     * @param quizId Идентификатор теста.
     * @return Список объектов QuizSubmission.
     * @throws NoSuchElementException Если тест с указанным ID не найден.
     */
    @Transactional(readOnly = true)
    public List<QuizSubmission> getQuizSubmissions(Long quizId) {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new NoSuchElementException("Тест с ID " + quizId + " не найден"));
        return quizSubmissionRepository.findByQuiz(quiz);
    }

    /**
     * Удаляет тест по его уникальному идентификатору.
     *
     * @param id Идентификатор теста для удаления.
     */
    public void deleteQuiz(Long id) {
        quizRepository.deleteById(id);
    }
}
