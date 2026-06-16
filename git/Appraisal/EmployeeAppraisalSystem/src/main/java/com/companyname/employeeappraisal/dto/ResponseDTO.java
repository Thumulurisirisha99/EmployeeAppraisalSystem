package com.companyname.employeeappraisal.dto;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ResponseDTO {

	private Integer employeeId;
	private String fullName;
	private String department;
	private Integer departmentId;
	private String designation;
	private String totalGoalCount;
	private String lastUpdated;
	private String status;
	private String statusName;
	private String role;
	private Integer roleId;
	private String token;
	private List<AppModuleDTO> modules;
	private String financialYear;
	private Integer financialYearId;
	private Integer quarterId;
	private String quarterName;
	private Integer year;
	private Integer appraisalId;
	private Integer eligibleId;
	private String ActiveFinancialYear;
	private Integer ActiveFinancialYearId;
	private Integer ActiveQuarterId;
	private String ActiveQuarterName;
	private Integer Activeyear;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private LocalDate activeFromDate;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private LocalDate activeToDate;

	public ResponseDTO() {
	}

	public Integer getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public Integer getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getTotalGoalCount() {
		return totalGoalCount;
	}

	public void setTotalGoalCount(String totalGoalCount) {
		this.totalGoalCount = totalGoalCount;
	}

	public String getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(String lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public List<AppModuleDTO> getModules() {
		return modules;
	}

	public void setModules(List<AppModuleDTO> modules) {
		this.modules = modules;
	}

	public String getFinancialYear() {
		return financialYear;
	}

	public void setFinancialYear(String financialYear) {
		this.financialYear = financialYear;
	}

	public Integer getFinancialYearId() {
		return financialYearId;
	}

	public void setFinancialYearId(Integer financialYearId) {
		this.financialYearId = financialYearId;
	}

	public Integer getQuarterId() {
		return quarterId;
	}

	public void setQuarterId(Integer quarterId) {
		this.quarterId = quarterId;
	}

	public String getQuarterName() {
		return quarterName;
	}

	public void setQuarterName(String quarterName) {
		this.quarterName = quarterName;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Integer getAppraisalId() {
		return appraisalId;
	}

	public void setAppraisalId(Integer appraisalId) {
		this.appraisalId = appraisalId;
	}

	public Integer getEligibleId() {
		return eligibleId;
	}

	public void setEligibleId(Integer eligibleId) {
		this.eligibleId = eligibleId;
	}

	public String getActiveFinancialYear() {
		return ActiveFinancialYear;
	}

	public void setActiveFinancialYear(String activeFinancialYear) {
		ActiveFinancialYear = activeFinancialYear;
	}

	public Integer getActiveFinancialYearId() {
		return ActiveFinancialYearId;
	}

	public void setActiveFinancialYearId(Integer activeFinancialYearId) {
		ActiveFinancialYearId = activeFinancialYearId;
	}

	public Integer getActiveQuarterId() {
		return ActiveQuarterId;
	}

	public void setActiveQuarterId(Integer activeQuarterId) {
		ActiveQuarterId = activeQuarterId;
	}

	public String getActiveQuarterName() {
		return ActiveQuarterName;
	}

	public void setActiveQuarterName(String activeQuarterName) {
		ActiveQuarterName = activeQuarterName;
	}

	public Integer getActiveyear() {
		return Activeyear;
	}

	public void setActiveyear(Integer activeyear) {
		Activeyear = activeyear;
	}

	public LocalDate getActiveFromDate() {
		return activeFromDate;
	}

	public void setActiveFromDate(LocalDate activeFromDate) {
		this.activeFromDate = activeFromDate;
	}

	public LocalDate getActiveToDate() {
		return activeToDate;
	}

	public void setActiveToDate(LocalDate activeToDate) {
		this.activeToDate = activeToDate;
	}



}
