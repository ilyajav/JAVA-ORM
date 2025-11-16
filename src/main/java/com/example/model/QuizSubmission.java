package com.example.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
@Entity
@Table(name = "quiz_submissions")
public class QuizSubmission {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id", nullable = false)
    private Quiz quiz;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private User student;
    
    private Integer score;
    
    private LocalDateTime takenAt;  // Дата прохождения

    public QuizSubmission() {
    }
    
    public QuizSubmission(Quiz quiz, User student, Integer score) {
        this.quiz = quiz;
        this.student = student;
        this.score = score;
        this.takenAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Quiz getQuiz() {
        return quiz;
    }
    
    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }
    
    public User getStudent() {
        return student;
    }
    
    public void setStudent(User student) {
        this.student = student;
    }
    
    public Integer getScore() {
        return score;
    }
    
    public void setScore(Integer score) {
        this.score = score;
    }
    
    public LocalDateTime getTakenAt() {
        return takenAt;
    }
    
    public void setTakenAt(LocalDateTime takenAt) {
        this.takenAt = takenAt;
    }
}

