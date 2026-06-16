package com.companyname.employeeappraisal.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_hod_employee_appraisal", schema = "performance_appraisal_system", uniqueConstraints = {
		@UniqueConstraint(name = "uq_hod_appraisal_question", columnNames = { "appraisal_id", "hod_id",
				"question_id" }) })
public class HodEmployeeAppraisal {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "hod_appraisal_detail_id")
	private Integer hodAppraisalDetailId;

	// FK → tbl_employee_appraisal.appraisal_id
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "appraisal_id", nullable = false)
	private EmployeeAppraisal employeeAppraisal;

	@Column(name = "hod_id", nullable = false)
	private Integer hodId;

	// FK → tbl_master_hod_appraisal_dept_questions.question_id
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "question_id", nullable = false)
	private HodAppraisalDeptQuestion question;

	@Column(name = "rating_id", nullable = false)
	private Integer ratingId;

	@Column(name = "comment", columnDefinition = "TEXT")
	private String comment;

	@Column(name = "status", nullable = false)
	private Integer status = 1001;

	@Column(name = "createddate")
	private LocalDateTime createdDate;

	@Column(name = "lupdate")
	private LocalDateTime lastUpdated;

	// Constructors
	public HodEmployeeAppraisal() {
	}

	public HodEmployeeAppraisal(EmployeeAppraisal employeeAppraisal, Integer hodId, HodAppraisalDeptQuestion question,
			Integer ratingId) {
		this.employeeAppraisal = employeeAppraisal;
		this.hodId = hodId;
		this.question = question;
		this.ratingId = ratingId;
		this.status = 1001;
	}

	// Getters & Setters
	public Integer getHodAppraisalDetailId() {
		return hodAppraisalDetailId;
	}

	public void setHodAppraisalDetailId(Integer hodAppraisalDetailId) {
		this.hodAppraisalDetailId = hodAppraisalDetailId;
	}

	public EmployeeAppraisal getEmployeeAppraisal() {
		return employeeAppraisal;
	}

	public void setEmployeeAppraisal(EmployeeAppraisal employeeAppraisal) {
		this.employeeAppraisal = employeeAppraisal;
	}

	public Integer getHodId() {
		return hodId;
	}

	public void setHodId(Integer hodId) {
		this.hodId = hodId;
	}

	public HodAppraisalDeptQuestion getQuestion() {
		return question;
	}

	public void setQuestion(HodAppraisalDeptQuestion question) {
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public LocalDateTime getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(LocalDateTime lastUpdated) {
		this.lastUpdated = lastUpdated;
	}
}
