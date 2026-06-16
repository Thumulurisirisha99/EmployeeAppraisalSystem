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
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "tbl_employee_appraisal_approvers", uniqueConstraints = @UniqueConstraint(name = "uq_appraisal_approver", columnNames = {
		"appraisal_id", "approver_emp_id" }))
public class EmployeeAppraisalApprovers {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer approverId;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "appraisal_id", nullable = false)
	private EmployeeAppraisal appraisal;

	private Integer approverLevel;

	private Integer approverEmpId;
	@Column(name = "approver_role", nullable = false, length = 20)
	private String approverRole;
	private Integer approverStatus;

	private LocalDateTime submittedDate;

	@Column(insertable = false, updatable = false)
	private LocalDateTime createddate;

	private LocalDateTime lupdate;

	public Integer getApproverId() {
		return approverId;
	}

	public void setApproverId(Integer approverId) {
		this.approverId = approverId;
	}

	public EmployeeAppraisal getAppraisal() {
		return appraisal;
	}

	public void setAppraisal(EmployeeAppraisal appraisal) {
		this.appraisal = appraisal;
	}

	public Integer getApproverLevel() {
		return approverLevel;
	}

	public void setApproverLevel(Integer approverLevel) {
		this.approverLevel = approverLevel;
	}

	public Integer getApproverEmpId() {
		return approverEmpId;
	}

	public void setApproverEmpId(Integer approverEmpId) {
		this.approverEmpId = approverEmpId;
	}

	public String getApproverRole() {
		return approverRole;
	}

	public void setApproverRole(String approverRole) {
		this.approverRole = approverRole;
	}

	public Integer getApproverStatus() {
		return approverStatus;
	}

	public void setApproverStatus(Integer approverStatus) {
		this.approverStatus = approverStatus;
	}

	public LocalDateTime getSubmittedDate() {
		return submittedDate;
	}

	public void setSubmittedDate(LocalDateTime submittedDate) {
		this.submittedDate = submittedDate;
	}

	public LocalDateTime getCreateddate() {
		return createddate;
	}

	public void setCreateddate(LocalDateTime createddate) {
		this.createddate = createddate;
	}

	public LocalDateTime getLupdate() {
		return lupdate;
	}

	public void setLupdate(LocalDateTime lupdate) {
		this.lupdate = lupdate;
	}

}
