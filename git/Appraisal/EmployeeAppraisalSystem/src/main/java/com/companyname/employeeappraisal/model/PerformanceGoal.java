package com.companyname.employeeappraisal.model;

import java.math.BigDecimal;
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
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name = "tbl_performance_goals")
public class PerformanceGoal {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "goal_id")
	private Integer goalId;

	@Column(name = "employeeid", nullable = false, length = 50)
	private Integer employeeId;

	@Column(name = "title", nullable = false, length = 255)
	private String title;

	@Column(name = "description", columnDefinition = "TEXT")
	private String description;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "goal_category_id", nullable = false)
	private GoalCategory goalCategory;

	@Column(name = "timeline", length = 50)
	private String timeline;

	@Column(name = "status", nullable = false)
	private Integer status = 1001;

//	@Column(name = "financial_year", nullable = false)
//	private String financialYear;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "financial_year_id")
	private MasterFinancialYear financialYear;
//	@ManyToOne(fetch = FetchType.EAGER)
//	@JoinColumn(name = "financial_year_id")
//	private MasterFinancialYear financialYear;


	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "quarter_id")
	private MasterQuarter quarter;

	@Column(name = "createdby", length = 100)
	private String createdBy;

	@Column(name = "createddate")
	private LocalDateTime createdDate;

	@Column(name = "updateby", length = 100)
	private String updateBy;

	@Column(name = "updateddate")
	private LocalDateTime updatedDate;

	@Column(name = "approved_manager_id")
	private String approvedManagerId;

	@Column(name = "manager_comment")
	private String managerComment;

	@Column(name = "approval_date")
	private LocalDateTime approvalDate;

	@Column(name = "document_name", length = 255)
	private String documentName;

	@Column(name = "document_path", length = 500)
	private String documentPath;

	@Column(name = "feedback_rating")
	private Integer feedbackRating;
	
	@Column(name = "goal_completion_percentage")
	private BigDecimal goalCompletionPercentage;


	@Column(name = "feedback_comment", columnDefinition = "TEXT")
	private String feedbackComment;

	@Column(name = "feedback_date")
	private LocalDateTime feedbackDate;

	@Column(name = "implemented_goal")
	private String implementedGoal;

	@ManyToOne
	@JoinColumn(name = "status", referencedColumnName = "status_code", insertable = false, updatable = false)
	private MasterStatus masterStatus;

	@Column(name = "lupdate", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP", insertable = false, updatable = false)
	private LocalDateTime lupdate;

	@PrePersist
	protected void onCreate() {
		this.createdDate = LocalDateTime.now();
		this.updatedDate = LocalDateTime.now();
	}

	@PreUpdate
	protected void onUpdate() {
		this.updatedDate = LocalDateTime.now();
	}

	public PerformanceGoal() {
		this.status = 1001;
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

	public GoalCategory getGoalCategory() {
		return goalCategory;
	}

	public void setGoalCategory(GoalCategory goalCategory) {
		this.goalCategory = goalCategory;
	}

	public Integer getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
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

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public LocalDateTime getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(LocalDateTime updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getApprovedManagerId() {
		return approvedManagerId;
	}

	public void setApprovedManagerId(String approvedManagerId) {
		this.approvedManagerId = approvedManagerId;
	}

	public LocalDateTime getApprovalDate() {
		return approvalDate;
	}

	public void setApprovalDate(LocalDateTime approvalDate) {
		this.approvalDate = approvalDate;
	}

	public String getDocumentName() {
		return documentName;
	}

	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}

	public String getDocumentPath() {
		return documentPath;
	}

	public void setDocumentPath(String documentPath) {
		this.documentPath = documentPath;
	}

	public Integer getFeedbackRating() {
		return feedbackRating;
	}

	public void setFeedbackRating(Integer feedbackRating) {
		this.feedbackRating = feedbackRating;
	}

	public String getFeedbackComment() {
		return feedbackComment;
	}

	public void setFeedbackComment(String feedbackComment) {
		this.feedbackComment = feedbackComment;
	}

	public LocalDateTime getFeedbackDate() {
		return feedbackDate;
	}

	public void setFeedbackDate(LocalDateTime feedbackDate) {
		this.feedbackDate = feedbackDate;
	}

	public MasterStatus getMasterStatus() {
		return masterStatus;
	}

	public void setMasterStatus(MasterStatus masterStatus) {
		this.masterStatus = masterStatus;
	}

	public String getImplementedGoal() {
		return implementedGoal;
	}

	public void setImplementedGoal(String implementedGoal) {
		this.implementedGoal = implementedGoal;
	}

	public LocalDateTime getLupdate() {
		return lupdate;
	}

	public void setLupdate(LocalDateTime lupdate) {
		this.lupdate = lupdate;
	}

	public String getManagerComment() {
		return managerComment;
	}

	public void setManagerComment(String managerComment) {
		this.managerComment = managerComment;
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

	public BigDecimal getGoalCompletionPercentage() {
		return goalCompletionPercentage;
	}

	public void setGoalCompletionPercentage(BigDecimal goalCompletionPercentage) {
		this.goalCompletionPercentage = goalCompletionPercentage;
	}

}
