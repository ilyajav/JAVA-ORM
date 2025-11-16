--
-- Скрипт начального заполнения данных для Учебной Платформы.
-- Используется MERGE INTO KEY(id) для обеспечения идемпотентности (вставляет,
-- если записи нет, и игнорирует или обновляет, если запись с таким ID уже есть).
--

-- 1. КАТЕГОРИИ КУРСОВ (categories)
MERGE INTO categories (id, name) KEY(id) VALUES
(1, 'Программирование на Java'),
(2, 'Базы данных'),
(3, 'DevOps');


-- 2. ПОЛЬЗОВАТЕЛИ (users)

-- 2.1. Преподаватели (TEACHER)
MERGE INTO users (id, name, email, role) KEY(id) VALUES
(1, 'Илья Иванов', 'ilya.ivanov@example.com', 'TEACHER'),
(2, 'Семен Чистяков', 'semen.chistakov@example.com', 'TEACHER');

-- 2.2. Студенты (STUDENT)
MERGE INTO users (id, name, email, role) KEY(id) VALUES
(3, 'Марина Петрова', 'marina.petrova@example.com', 'STUDENT'),
(4, 'Иван Бойко', 'ivan.boyko@example.com', 'STUDENT');


-- 3. ТЕГИ (tags)
MERGE INTO tags (id, name) KEY(id) VALUES
(1, 'Java'),
(2, 'Spring'),
(3, 'Hibernate'),
(4, 'QA'),
(5, 'Intern');