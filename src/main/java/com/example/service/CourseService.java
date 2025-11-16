
package com.example.service;

import com.example.model.Course;
import com.example.model.User;
import com.example.repository.CourseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Сервис для управления сущностями Курсов (Course).
 * Обеспечивает бизнес-логику для создания, получения, обновления и поиска курсов.
 */
@Service
@Transactional
public class CourseService {

    private final CourseRepository courseRepository;

    /**
     * Конструктор для внедрения зависимостей.
     * Использует конструкторную инъекцию для CourseRepository.
     *
     * @param courseRepository Репозиторий для доступа к данным курсов.
     */
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    /**
     * Создает и сохраняет новый курс в системе.
     *
     * @param title Заголовок курса.
     * @param description Описание курса.
     * @param teacher Преподаватель (пользователь), который ведет курс.
     * @return Созданный и сохраненный объект Course.
     */
    public Course createCourse(String title, String description, User teacher) {
        Course course = new Course(title, description, teacher);
        return courseRepository.save(course);
    }

    /**
     * Получает курс по его уникальному идентификатору.
     *
     * @param id Идентификатор курса.
     * @return Optional<Course>, содержащий курс, если он найден.
     */
    @Transactional(readOnly = true)
    public Optional<Course> getCourseById(Long id) {
        return courseRepository.findById(id);
    }

    /**
     * Получает список всех доступных курсов.
     *
     * @return Список всех объектов Course.
     */
    @Transactional(readOnly = true)
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    /**
     * Получает список курсов, которые ведет определенный преподаватель.
     *
     * @param teacher Преподаватель (пользователь).
     * @return Список объектов Course.
     */
    @Transactional(readOnly = true)
    public List<Course> getCoursesByTeacher(User teacher) {
        return courseRepository.findByTeacher(teacher);
    }

    /**
     * Получает список курсов по идентификатору категории.
     *
     * @param categoryId Идентификатор категории.
     * @return Список объектов Course, относящихся к указанной категории.
     */
    @Transactional(readOnly = true)
    public List<Course> getCoursesByCategory(Long categoryId) {
        return courseRepository.findByCategoryId(categoryId);
    }

    /**
     * Осуществляет поиск курсов, заголовок которых содержит указанную строку (без учета регистра).
     *
     * @param title Часть заголовка для поиска.
     * @return Список объектов Course, удовлетворяющих критерию поиска.
     */
    @Transactional(readOnly = true)
    public List<Course> searchCoursesByTitle(String title) {
        return courseRepository.findByTitleContainingIgnoreCase(title);
    }

    /**
     * Обновляет существующий курс, сохраняя переданный объект.
     * Этот метод можно использовать как для создания, так и для обновления (saveOrUpdate).
     *
     * @param course Объект Course с обновленными данными.
     * @return Обновленный и сохраненный объект Course.
     */
    public Course updateCourse(Course course) {
        return courseRepository.save(course);
    }

    /**
     * Удаляет курс по его уникальному идентификатору.
     *
     * @param id Идентификатор курса для удаления.
     */
    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }
}