package com.companyname.employeeappraisal.model;

import java.sql.Timestamp;
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
@Table(name = "tbl_appraisal_eligible_employees", schema = "performance_appraisal_system", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "employeeid", "financial_year_id", "quarter_id", "status" }) })
public class AppraisalEligibleEmployee {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "eligible_id")
	private Integer eligibleId;

	@Column(name = "employeeid", nullable = false)
	private Integer employeeId;

	@Column(name = "division", nullable = false)
	private Integer division;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "financial_year_id")
	private MasterFinancialYear financialYear;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "quarter_id")
	private MasterQuarter quarter;

	@Column(name = "status")
	private Integer status = 1001;

	@Column(name = "createddate", insertable = false, updatable = false)
	private Timestamp createdDate;

	@Column(name = "createdby")
	private Integer createdBy;

	@Column(name = "updatedby")
	private Integer updatedBy;

	@Column(name = "updatedon")
	private LocalDateTime updatedOn;

	public Integer getEligibleId() {
		return eligibleId;
	}

	public void setEligibleId(Integer eligibleId) {
		this.eligibleId = eligibleId;
	}

	public Integer getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}

	public MasterFinancialYear getFinancialYear() {
		return financialYear;
	}

	public void setFinancialYear(MasterFinancialYear financialYear) {
		this.financialYear = financialYear;
	}

	public MasterQuarter getQuarter() {
		return quarter;
	}

	public void setQuarter(MasterQuarter quarter) {
		this.quarter = quarter;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public Integer getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Integer updatedBy) {
		this.updatedBy = updatedBy;
	}

	public LocalDateTime getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(LocalDateTime updatedOn) {
		this.updatedOn = updatedOn;
	}

	public Integer getDivision() {
		return division;
	}

	public void setDivision(Integer division) {
		this.division = division;
	}
	

}
