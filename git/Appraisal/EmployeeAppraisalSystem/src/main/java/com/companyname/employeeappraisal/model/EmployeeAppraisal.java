package com.companyname.employeeappraisal.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
@Table(name = "tbl_employee_appraisal", uniqueConstraints = {
		@UniqueConstraint(name = "uq_employee_finyear", columnNames = { "employeeid", "financial_year_id",
				"quarter_id" }) })
public class EmployeeAppraisal {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "appraisal_id")
	private Integer appraisalId;

	@Column(name = "employeeid", nullable = false)
	private Integer employeeId;

//	@Column(name = "financial_year", nullable = false, length = 20)
//	private String financialYear;
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "financial_year_id", nullable = false)
//	private MasterFinancialYear financialYear;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "financial_year_id", nullable = false)
	private MasterFinancialYear financialYear;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "quarter_id")
	private MasterQuarter quarter;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "eligible_id", nullable = false)
	@JsonIgnore
	private AppraisalEligibleEmployee eligibleEmployee;

	@Column(name = "status", columnDefinition = "INT DEFAULT 1001")
	private Integer status;

	@Column(name = "createddate", nullable = false)
	private LocalDateTime createdDate;

	@Column(name = "acknowledgment_flag", nullable = false, columnDefinition = "CHAR(1) DEFAULT 'N'")
	private String acknowledgmentFlag = "N";

	@Column(name = "lupdate", insertable = false, updatable = false)
	private LocalDateTime lastUpdate;

	@PrePersist
	public void prePersist() {
		if (createdDate == null) {
			createdDate = LocalDateTime.now();
		}
		if (acknowledgmentFlag == null) {
			acknowledgmentFlag = "N";
		}
	}

	public Integer getAppraisalId() {
		return appraisalId;
	}

	public void setAppraisalId(Integer appraisalId) {
		this.appraisalId = appraisalId;
	}

	public Integer getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
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

	public LocalDateTime getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(LocalDateTime lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public MasterQuarter getQuarter() {
		return quarter;
	}

	public void setQuarter(MasterQuarter quarter) {
		this.quarter = quarter;
	}

	public MasterFinancialYear getFinancialYear() {
		return financialYear;
	}

	public void setFinancialYear(MasterFinancialYear financialYear) {
		this.financialYear = financialYear;
	}

	public String getAcknowledgmentFlag() {
		return acknowledgmentFlag;
	}

	public void setAcknowledgmentFlag(String acknowledgmentFlag) {
		this.acknowledgmentFlag = acknowledgmentFlag;
	}

	public AppraisalEligibleEmployee getEligibleEmployee() {
		return eligibleEmployee;
	}

	public void setEligibleEmployee(AppraisalEligibleEmployee eligibleEmployee) {
		this.eligibleEmployee = eligibleEmployee;
	}

}
