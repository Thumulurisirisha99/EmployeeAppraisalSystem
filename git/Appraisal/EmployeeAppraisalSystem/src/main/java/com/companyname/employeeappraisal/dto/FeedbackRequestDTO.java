package com.companyname.employeeappraisal.dto;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public class FeedbackRequestDTO {
    private Integer appraisalId;
    private Integer managerId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dueDate;
    private Integer reviewerEmployeeId;
    private Integer participantStatus;
    private String type;
    private String reviewerName;
    private String employeeName;
    private List<FeedbackParticipantDTO> participants;
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
	public List<FeedbackParticipantDTO> getParticipants() {
		return participants;
	}
	public void setParticipants(List<FeedbackParticipantDTO> participants) {
		this.participants = participants;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getReviewerName() {
		return reviewerName;
	}
	public void setReviewerName(String reviewerName) {
		this.reviewerName = reviewerName;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

}

