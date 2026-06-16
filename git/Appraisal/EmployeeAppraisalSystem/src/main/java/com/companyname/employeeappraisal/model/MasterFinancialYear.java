package com.companyname.employeeappraisal.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tbl_master_financial_year", schema = "performance_appraisal_system")
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class MasterFinancialYear {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "financial_year_id")
	private Integer financialYearId;

	@Column(name = "financial_year_code", nullable = false, unique = true, length = 20)
	private String financialYearCode;

	@Column(name = "year", nullable = false)
	private Integer year;

	@Column(name = "start_date", nullable = false)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDate startDate;

	@Column(name = "end_date", nullable = false)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDate endDate;

	@Column(name = "status")
	private Integer status = 1001;

	@Column(name = "updated_on", insertable = false, updatable = false)
	private String updatedOn;

	public Integer getFinancialYearId() {
		return financialYearId;
	}

	public void setFinancialYearId(Integer financialYearId) {
		this.financialYearId = financialYearId;
	}

	public String getFinancialYearCode() {
		return financialYearCode;
	}

	public void setFinancialYearCode(String financialYearCode) {
		this.financialYearCode = financialYearCode;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(String updatedOn) {
		this.updatedOn = updatedOn;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}
	
}
