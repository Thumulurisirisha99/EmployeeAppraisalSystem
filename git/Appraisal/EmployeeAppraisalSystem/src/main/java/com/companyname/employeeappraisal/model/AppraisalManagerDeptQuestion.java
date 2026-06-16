package com.companyname.employeeappraisal.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tbl_master_manager_appraisal_dept_questions")
public class AppraisalManagerDeptQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    private Integer questionId;
    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "dept_module_id", nullable = false)
    private AppraisalDeptModule deptModule;

    @Column(name = "question_text", columnDefinition = "TEXT", nullable = false)
    private String questionText;

    @Column(name = "status", nullable = false)
    private Integer status = 1001;  
    public AppraisalManagerDeptQuestion() {
    }

    public AppraisalManagerDeptQuestion(AppraisalDeptModule deptModule, String questionText, Integer status) {
        this.deptModule = deptModule;
        this.questionText = questionText;
        this.status = status;
    }
    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public AppraisalDeptModule getDeptModule() {
        return deptModule;
    }

    public void setDeptModule(AppraisalDeptModule deptModule) {
        this.deptModule = deptModule;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

	public AppraisalManagerDeptQuestion(Integer questionId, AppraisalDeptModule deptModule, String questionText,
			Integer status) {
		super();
		this.questionId = questionId;
		this.deptModule = deptModule;
		this.questionText = questionText;
		this.status = status;
	}



	


}
