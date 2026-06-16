package com.companyname.employeeappraisal.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.sql.Timestamp;

@Entity
@Table(name = "tbl_employee_appraisal_work", schema = "performance_appraisal_system")
public class EmployeeAppraisalWork {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "work_id")
	private Integer workId;

	@Column(name = "employee_id", nullable = false)
	private Integer employeeId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "financial_year_id")
	private MasterFinancialYear financialYear;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "quarter_id")
	private MasterQuarter quarter;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "work_type_id")
	private MasterWorkType workType;

	@Column(name = "work_title", nullable = false, length = 200)
	private String workTitle;

	@Column(name = "work_description", nullable = false, columnDefinition = "TEXT")
	private String workDescription;

	@Column(name = "completion_percentage", nullable = false)
	private Integer completionPercentage = 0;

	@Column(name = "outcome", length = 255)
	private String outcome;

	@Column(name = "file_path", length = 500)
	private String filePath;

	@Column(name = "status", nullable = false)
	private Integer status = 1001;

	@Column(name = "created_date", nullable = false)
	private LocalDateTime createdDate;

	@Column(name = "updated_date", insertable = false, updatable = false)
	private Timestamp updatedDate;

	public Integer getWorkId() {
		return workId;
	}

	public void setWorkId(Integer workId) {
		this.workId = workId;
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

	public String getWorkTitle() {
		return workTitle;
	}

	public void setWorkTitle(String workTitle) {
		this.workTitle = workTitle;
	}

	public String getWorkDescription() {
		return workDescription;
	}

	public void setWorkDescription(String workDescription) {
		this.workDescription = workDescription;
	}

	public MasterWorkType getWorkType() {
		return workType;
	}

	public Integer getCompletionPercentage() {
		return completionPercentage;
	}

	public void setCompletionPercentage(Integer completionPercentage) {
		this.completionPercentage = completionPercentage;
	}

	public String getOutcome() {
		return outcome;
	}

	public void setOutcome(String outcome) {
		this.outcome = outcome;
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

	public Timestamp getUpdatedDate() {
		return updatedDate;
	}

	public void setWorkType(MasterWorkType workType) {
		this.workType = workType;
	}

	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

}
