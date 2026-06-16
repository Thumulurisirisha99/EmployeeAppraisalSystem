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
@Table(name = "tbl_master_appraisal_dept_questions", schema = "performance_appraisal_system")
public class AppraisalDeptQuestion {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "question_id")
	private Integer questionId;
	
	@JsonBackReference
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "dept_module_id", nullable = false)
	private AppraisalDeptModule deptModule;
//	@JsonIgnore
//	@ManyToOne(fetch = FetchType.EAGER)
//	@JoinColumn(name = "dept_module_id", nullable = false)
//	private AppraisalDeptModule deptModule;


	@Column(name = "question_text", nullable = false, columnDefinition = "TEXT")
	private String questionText;

	@Column(name = "status")
	private Integer status = 1001;

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
}
