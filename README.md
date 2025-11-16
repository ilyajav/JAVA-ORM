Система управления учебной платформой (LMS)

Полнофункциональное веб-приложение для эффективного управления учебными курсами, студентами и преподавателями. Система построена на базе Spring Boot 3.2.0 и использует Spring Data JPA с Hibernate для работы с интегрированной базой данных H2.

⚙️ Технологии и Стек

Категория

Технологии

Backend

Java 17+, Spring Boot 3.2.0, Spring Data JPA, Hibernate

Сборка

Maven 3.6+

База данных

H2 Database (встроенная, не требует установки)

🚀 Установка и Запуск

Для запуска приложения достаточно иметь установленные Java 17+ и Maven 3.6+.

Клонирование и навигация:

# (Предполагается, что вы уже в папке проекта)



Сборка проекта:
Выполните полную сборку и установку зависимостей:

mvn clean install



Запуск приложения:
Используйте встроенную цель Spring Boot для запуска:

mvn spring-boot:run



Приложение будет доступно по адресу: http://localhost:8080

🔗 Доступ и Инструменты

Для разработки и тестирования доступны следующие ресурсы:

Инструмент

URL

Описание

Swagger UI

http://localhost:8080/swagger-ui.html

Интерактивная документация для тестирования REST API.

H2 Console

http://localhost:8080/h2-console

Веб-консоль для просмотра и управления встроенной БД.

🛠️ Настройки H2 Console

При подключении к H2 Console используйте следующие параметры:

JDBC URL: jdbc:h2:file:./learning_platform

User: skillfactory

Password: (оставьте пустым)

🗄️ Модель Данных (15 Сущностей)

В проекте реализована комплексная модель, включающая 15 JPA-сущностей с использованием связей OneToOne, OneToMany и ManyToMany.

Группа

Основные Сущности

Связанные Сущности

Пользователи

User, Profile



Курсы

Course, Category, Tag

CourseReview (Отзывы)

Содержание

Module, Lesson



Активность

Enrollment (Запись на курс), Assignment (Задание), Submission (Ответ на задание)



Тестирование

Quiz, Question, AnswerOption, QuizSubmission



🌐 Основные API Endpoints

Все API-методы доступны по префиксу /api.

👤 Пользователи (/api/users)

Методы:

GET - /api/users -Получить список всех пользователей

GET - /api/users/{id} - Получить пользователя по ID

POST - /api/users - Создать нового пользователя (JSON-тело)

PUT - /api/users/{id} - Обновить данные пользователя

DELETE - /api/users/{id} - Удалить пользователя

📚 Курсы (/api/courses)

Методы: 

GET - /api/courses - Получить список всех курсов

GET - /api/courses/{id} - Получить курс по ID

POST - /api/courses - Создать новый курс (JSON-тело)

PUT - /api/courses/{id} - Обновить данные курса

DELETE - /api/courses/{id} - Удалить курс

📝 Записи на курсы (/api/enrollments)

Метод: 

GET - /api/enrollments - Получить список всех записей

POST - /api/enrollments - Записать студента на курс (JSON-тело)

GET - /api/enrollments/student/{studentId} - Получить все записи по ID студента

💡 Примеры Использования (cURL)

Для быстрого тестирования API из командной строки.

1. Создание пользователя

curl -X POST http://localhost:8080/api/users \
-H "Content-Type: application/json" \
-d '{"name":"Иван Иванов","email":"ivan@example.com","role":"STUDENT"}'



2. Получение всех пользователей

curl http://localhost:8080/api/users



3. Создание курса

curl -X POST http://localhost:8080/api/courses \
-H "Content-Type: application/json" \
-d '{"title":"Java для начинающих","description":"Основы Java","teacherId":1}'



(Примечание: teacherId должен ссылаться на существующего пользователя с ролью TEACHER).

📂 Структура Проекта

Ключевые пакеты расположены в src/main/java/com/example/:

model/ — JPA-сущности (Entity)

repository/ — Репозитории (Spring Data JPA)

service/ — Бизнес-логика приложения

controller/ — REST-контроллеры

✅ Тестирование

Для запуска интеграционных и модульных тестов используйте команду:

mvn test



⚠️ Возможные Проблемы

Порт 8080 занят:
Если порт уже используется, измените его в файле application.properties:

server.port=8081



Ошибки при запуске:
Убедитесь, что ваша среда соответствует требованиям: установлена Java 17+ и корректно настроен Maven.
