package com.companyname.employeeappraisal.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public class PerformanceGoalDTO {
	private Integer employeeId;
	private Integer goalId;
	private String title;
	private String description;
	private Integer goalCategoryId;
	private String goalCategoryName;
	private String timeline;
	private Integer status;
	private String financialYear;
	private Integer financialYearId;
	private Integer quarterId;
	private String quarterName;
	private String statusName;
	private String createdBy;
	private String updateBy;
	private String type;
	private int feedBackRating;
	private String feedBackComment;
	private String implementedGoal;
	private String feedBackDate;
	private String document;
	private byte[] documentBytes;
	private String documentName;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime lupdate;
	private String approvedManagerId;
	private String approvalDate;
	private String managerComment;
	private String goalCreatedYear;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime updatedDate;
	private BigDecimal goalCompletionPercentage;

	public PerformanceGoalDTO() {
	}


	public Integer getGoalId() {
		return goalId;
	}

	public void setGoalId(Integer goalId) {
		this.goalId = goalId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getGoalCategoryId() {
		return goalCategoryId;
	}

	public void setGoalCategoryId(Integer goalCategoryId) {
		this.goalCategoryId = goalCategoryId;
	}

	public String getDocument() {
		return document;
	}

	public void setDocument(String document) {
		this.document = document;
	}

	public String getTimeline() {
		return timeline;
	}

	public void setTimeline(String timeline) {
		this.timeline = timeline;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public Integer getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getGoalCategoryName() {
		return goalCategoryName;
	}

	public void setGoalCategoryName(String goalCategoryName) {
		this.goalCategoryName = goalCategoryName;
	}

	public int getFeedBackRating() {
		return feedBackRating;
	}

	public void setFeedBackRating(int feedBackRating) {
		this.feedBackRating = feedBackRating;
	}

	public String getFeedBackComment() {
		return feedBackComment;
	}

	public void setFeedBackComment(String feedBackComment) {
		this.feedBackComment = feedBackComment;
	}

	public String getImplementedGoal() {
		return implementedGoal;
	}

	public void setImplementedGoal(String implementedGoal) {
		this.implementedGoal = implementedGoal;
	}

	public String getFeedBackDate() {
		return feedBackDate;
	}

	public void setFeedBackDate(String feedBackDate) {
		this.feedBackDate = feedBackDate;
	}

	public LocalDateTime getLupdate() {
		return lupdate;
	}

	public void setLupdate(LocalDateTime lupdate) {
		this.lupdate = lupdate;
	}

	public String getApprovedManagerId() {
		return approvedManagerId;
	}

	public void setApprovedManagerId(String approvedManagerId) {
		this.approvedManagerId = approvedManagerId;
	}

	public String getApprovalDate() {
		return approvalDate;
	}

	public void setApprovalDate(String approvalDate) {
		this.approvalDate = approvalDate;
	}

	public String getManagerComment() {
		return managerComment;
	}

	public void setManagerComment(String managerComment) {
		this.managerComment = managerComment;
	}

	public String getGoalCreatedYear() {
		return goalCreatedYear;
	}

	public void setGoalCreatedYear(String goalCreatedYear) {
		this.goalCreatedYear = goalCreatedYear;
	}

	public LocalDateTime getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(LocalDateTime updatedDate) {
		this.updatedDate = updatedDate;
	}

	public byte[] getDocumentBytes() {
		return documentBytes;
	}

	public void setDocumentBytes(byte[] documentBytes) {
		this.documentBytes = documentBytes;
	}

	public String getDocumentName() {
		return documentName;
	}

	public void setDocumentName(String documentName) {
		this.documentName = documentName;
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


	public BigDecimal getGoalCompletionPercentage() {
		return goalCompletionPercentage;
	}


	public void setGoalCompletionPercentage(BigDecimal goalCompletionPercentage) {
		this.goalCompletionPercentage = goalCompletionPercentage;
	}

}
