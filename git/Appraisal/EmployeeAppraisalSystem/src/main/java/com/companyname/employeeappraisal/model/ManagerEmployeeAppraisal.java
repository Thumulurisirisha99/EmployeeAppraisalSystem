package com.companyname.employeeappraisal.model;
import java.sql.Timestamp;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "tbl_manager_employee_appraisal",
       uniqueConstraints = @UniqueConstraint(columnNames = {"appraisal_id", "manager_id", "question_id"}))
public class ManagerEmployeeAppraisal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "manager_appraisal_detail_id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appraisal_id", nullable = false)
    private EmployeeAppraisal employeeAppraisal;

    @Column(name = "manager_id", nullable = false)
    private Integer managerId;

    @Column(name = "status", nullable = false)
    private Integer status = 1001; 

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "question_id", nullable = false)
    
    private AppraisalManagerDeptQuestion question;

    @Column(name = "rating_id", nullable = false)
    private Integer ratingId;

    @Column(name = "comment", columnDefinition = "TEXT")
    private String comment;

    @Column(name = "createddate", nullable = true)
    private Timestamp createdDate;

    @Column(name = "lupdate", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Timestamp lastUpdate;

    // Constructors
    public ManagerEmployeeAppraisal() {}

    // Getters and Setters
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public EmployeeAppraisal getEmployeeAppraisal() {
        return employeeAppraisal;
    }

    public void setEmployeeAppraisal(EmployeeAppraisal employeeAppraisal) {
        this.employeeAppraisal = employeeAppraisal;
    }

    public Integer getManagerId() {
        return managerId;
    }

    public void setManagerId(Integer managerId) {
        this.managerId = managerId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public AppraisalManagerDeptQuestion getQuestion() {
        return question;
    }

    public void setQuestion(AppraisalManagerDeptQuestion question) {
        this.question = question;
    }

    public Integer getRatingId() {
        return ratingId;
    }

    public void setRatingId(Integer ratingId) {
        this.ratingId = ratingId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
