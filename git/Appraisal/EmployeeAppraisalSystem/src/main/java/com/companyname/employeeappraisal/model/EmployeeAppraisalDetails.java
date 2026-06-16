package com.companyname.employeeappraisal.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "tbl_employee_appraisal_details", uniqueConstraints = @UniqueConstraint(name = "uq_appraisal_question", columnNames = {
		"appraisal_id", "question_id" }))
public class EmployeeAppraisalDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer appraisalDetailId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "appraisal_id", nullable = false)
	private EmployeeAppraisal appraisal;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "question_id", nullable = false)
	private AppraisalDeptQuestion question;

	private Integer ratingId;

	private String ratingComment;

	@Column(name = "createddate", nullable = false)
	private LocalDateTime createdDate;

	@Column(name = "lupdate", insertable = false, updatable = false)
	private LocalDateTime lastUpdate;
	
	@PrePersist
	public void prePersist() {
		if (createdDate == null) {
			createdDate = LocalDateTime.now();
		}
	}

	public Integer getAppraisalDetailId() {
		return appraisalDetailId;
	}

	public void setAppraisalDetailId(Integer appraisalDetailId) {
		this.appraisalDetailId = appraisalDetailId;
	}

	public EmployeeAppraisal getAppraisal() {
		return appraisal;
	}

	public void setAppraisal(EmployeeAppraisal appraisal) {
		this.appraisal = appraisal;
	}

	public AppraisalDeptQuestion getQuestion() {
		return question;
	}

	public void setQuestion(AppraisalDeptQuestion question) {
		this.question = question;
	}

	public Integer getRatingId() {
		return ratingId;
	}

	public void setRatingId(Integer ratingId) {
		this.ratingId = ratingId;
	}

	public String getRatingComment() {
		return ratingComment;
	}

	public void setRatingComment(String ratingComment) {
		this.ratingComment = ratingComment;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public LocalDateTime getLastUpdate() {
		return lastUpdate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public void setLastUpdate(LocalDateTime lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

}
