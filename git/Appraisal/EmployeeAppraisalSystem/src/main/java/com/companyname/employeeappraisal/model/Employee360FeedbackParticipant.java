package com.companyname.employeeappraisal.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "employee_360_feedback_participant", uniqueConstraints = @UniqueConstraint(name = "uniq_appraisal_reviewer_request_manager", columnNames = {
		"appraisal_id", "reviewer_employee_id", "participant_status", "manager_id" }))
public class Employee360FeedbackParticipant {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "participant_id")
	private Integer participantId;

	@Column(name = "appraisal_id", nullable = false)
	private Integer appraisalId;

	@Column(name = "manager_id", nullable = false)
	private Integer managerId;

	@Column(name = "due_date")
	private LocalDate dueDate;

	@Column(name = "request_status", nullable = false)
	private Integer requestStatus = 1001; // 1001=Created, 1002=Mail Sent, 1003=Closed

	@Column(name = "reviewer_employee_id", nullable = false)
	private Integer reviewerEmployeeId;

	@Column(name = "participant_status", nullable = false)
	private Integer participantStatus = 1001; // 1001=Created, 1002=Mail Sent, 1003=Responded

	@Column(name = "mail_status", nullable = false, length = 1)
	private String mailStatus = "N"; // N=Not Sent, S=Sent

	@Column(name = "response_status", nullable = false, length = 1)
	private String responseStatus = "P"; // P=Pending, S=Submitted

	@Column(name = "access_token", nullable = false, length = 100)
	private String accessToken;

	@Column(name = "token_expiry")
	private LocalDateTime tokenExpiry;

	@Column(name = "mail_sent_on")
	private LocalDateTime mailSentOn;

	@Column(name = "responded_on")
	private LocalDateTime respondedOn;

	@OneToMany(mappedBy = "participant", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Employee360FeedbackAnswer> answers;

	@Column(name = "created_on", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime createdOn;

	@PrePersist
	protected void onCreate() {
		if (createdOn == null) {
			createdOn = LocalDateTime.now();
		}
	}

	public Integer getParticipantId() {
		return participantId;
	}

	public void setParticipantId(Integer participantId) {
		this.participantId = participantId;
	}

	public Integer getAppraisalId() {
		return appraisalId;
	}

	public void setAppraisalId(Integer appraisalId) {
		this.appraisalId = appraisalId;
	}

	public Integer getManagerId() {
		return managerId;
	}

	public void setManagerId(Integer managerId) {
		this.managerId = managerId;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}

	public Integer getRequestStatus() {
		return requestStatus;
	}

	public void setRequestStatus(Integer requestStatus) {
		this.requestStatus = requestStatus;
	}

	public Integer getReviewerEmployeeId() {
		return reviewerEmployeeId;
	}

	public void setReviewerEmployeeId(Integer reviewerEmployeeId) {
		this.reviewerEmployeeId = reviewerEmployeeId;
	}

	public Integer getParticipantStatus() {
		return participantStatus;
	}

	public void setParticipantStatus(Integer participantStatus) {
		this.participantStatus = participantStatus;
	}

	public String getMailStatus() {
		return mailStatus;
	}

	public void setMailStatus(String mailStatus) {
		this.mailStatus = mailStatus;
	}

	public String getResponseStatus() {
		return responseStatus;
	}

	public void setResponseStatus(String responseStatus) {
		this.responseStatus = responseStatus;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public LocalDateTime getTokenExpiry() {
		return tokenExpiry;
	}

	public void setTokenExpiry(LocalDateTime tokenExpiry) {
		this.tokenExpiry = tokenExpiry;
	}

	public LocalDateTime getMailSentOn() {
		return mailSentOn;
	}

	public void setMailSentOn(LocalDateTime mailSentOn) {
		this.mailSentOn = mailSentOn;
	}

	public LocalDateTime getRespondedOn() {
		return respondedOn;
	}

	public void setRespondedOn(LocalDateTime respondedOn) {
		this.respondedOn = respondedOn;
	}

	public LocalDateTime getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
	}

	public List<Employee360FeedbackAnswer> getAnswers() {
		return answers;
	}

	public void setAnswers(List<Employee360FeedbackAnswer> answers) {
		this.answers = answers;
	}
}
