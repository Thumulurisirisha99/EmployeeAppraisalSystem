package com.companyname.employeeappraisal.model;

import jakarta.persistence.*;

@Entity
@Table(name = "employee_360_feedback_answer",
       indexes = {
           @Index(name = "idx_answer_participant", columnList = "participant_id"),
           @Index(name = "idx_answer_category", columnList = "category_id")
       }
)
public class Employee360FeedbackAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "answer_id")
    private Integer answerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "participant_id", nullable = false,
                foreignKey = @ForeignKey(name = "fk_answer_participant"))
    private Employee360FeedbackParticipant participant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false,
                foreignKey = @ForeignKey(name = "fk_answer_category"))
    private MasterFeedbackCategory category;

    @Column(name = "question_text", columnDefinition = "TEXT", nullable = false)
    private String questionText;

    @Column(name = "answer_text", columnDefinition = "TEXT")
    private String answerText;

    // Getters and Setters

    public Integer getAnswerId() {
        return answerId;
    }

    public void setAnswerId(Integer answerId) {
        this.answerId = answerId;
    }

    public Employee360FeedbackParticipant getParticipant() {
        return participant;
    }

    public void setParticipant(Employee360FeedbackParticipant participant) {
        this.participant = participant;
    }

    public MasterFeedbackCategory getCategory() {
        return category;
    }

    public void setCategory(MasterFeedbackCategory category) {
        this.category = category;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }
}
