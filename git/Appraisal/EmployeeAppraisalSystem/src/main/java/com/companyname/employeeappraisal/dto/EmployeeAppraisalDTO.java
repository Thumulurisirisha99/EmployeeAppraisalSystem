package com.companyname.employeeappraisal.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public class EmployeeAppraisalDTO {
	private Integer appraisalId;
	private Integer employeeId;
	private String financialYear;
	private Integer financialYearId;
	private Integer quarterId;
	private Integer status;
	private Integer eligibleId;
	private String type;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime createdDate;
	private String role;
	private String acknowledgeFlag;
	private List<EmployeeAppraisalDetailDTO> details;
	private List<ModuleQuestionAnswerCountDTO> moduleSummary;
	private List<AppModuleDTO> appModule;
	private ManagerModuleDTO managerModules;
	private List<ManagerFeedbackDTO> previousManagerFeedback;
	private HodFeedbackDTO hodFeedback;
	private List<FeedbackRequestDTO> feedbackRequestDTO;
	private List<PerformanceGoalDTO> performanceGoalDTO;
	private List<EmployeeAppraisalWorkDTO> employeeAppraisalWorkDTO;

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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public List<EmployeeAppraisalDetailDTO> getDetails() {
		return details;
	}

	public void setDetails(List<EmployeeAppraisalDetailDTO> details) {
		this.details = details;
	}

	public List<ModuleQuestionAnswerCountDTO> getModuleSummary() {
		return moduleSummary;
	}

	public void setModuleSummary(List<ModuleQuestionAnswerCountDTO> moduleSummary) {
		this.moduleSummary = moduleSummary;
	}

	public List<AppModuleDTO> getAppModule() {
		return appModule;
	}

	public void setAppModule(List<AppModuleDTO> appModule) {
		this.appModule = appModule;
	}

	public EmployeeAppraisalDTO(List<AppModuleDTO> appModule) {
		super();
		this.appModule = appModule;
	}

	public EmployeeAppraisalDTO() {
		// TODO Auto-generated constructor stub
	}

	public List<ManagerFeedbackDTO> getPreviousManagerFeedback() {
		return previousManagerFeedback;
	}

	public void setPreviousManagerFeedback(List<ManagerFeedbackDTO> previousManagerFeedback) {
		this.previousManagerFeedback = previousManagerFeedback;
	}

	public HodFeedbackDTO getHodFeedback() {
		return hodFeedback;
	}

	public void setHodFeedback(HodFeedbackDTO hodFeedback) {
		this.hodFeedback = hodFeedback;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public List<FeedbackRequestDTO> getFeedbackRequestDTO() {
		return feedbackRequestDTO;
	}

	public void setFeedbackRequestDTO(List<FeedbackRequestDTO> feedbackRequestDTO) {
		this.feedbackRequestDTO = feedbackRequestDTO;
	}

	public ManagerModuleDTO getManagerModules() {
		return managerModules;
	}

	public void setManagerModules(ManagerModuleDTO managerModules) {
		this.managerModules = managerModules;
	}

	public List<PerformanceGoalDTO> getPerformanceGoalDTO() {
		return performanceGoalDTO;
	}

	public void setPerformanceGoalDTO(List<PerformanceGoalDTO> performanceGoalDTO) {
		this.performanceGoalDTO = performanceGoalDTO;
	}

	public List<EmployeeAppraisalWorkDTO> getEmployeeAppraisalWorkDTO() {
		return employeeAppraisalWorkDTO;
	}

	public void setEmployeeAppraisalWorkDTO(List<EmployeeAppraisalWorkDTO> employeeAppraisalWorkDTO) {
		this.employeeAppraisalWorkDTO = employeeAppraisalWorkDTO;
	}

	public String getAcknowledgeFlag() {
		return acknowledgeFlag;
	}

	public void setAcknowledgeFlag(String acknowledgeFlag) {
		this.acknowledgeFlag = acknowledgeFlag;
	}

	public Integer getEligibleId() {
		return eligibleId;
	}

	public void setEligibleId(Integer eligibleId) {
		this.eligibleId = eligibleId;
	}

}