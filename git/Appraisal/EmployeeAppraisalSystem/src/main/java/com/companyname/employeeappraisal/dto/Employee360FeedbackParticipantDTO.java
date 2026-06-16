package com.companyname.employeeappraisal.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Employee360FeedbackParticipantDTO {

    private Integer participantId;
    private Integer appraisalId;
    private Integer managerId;
    private LocalDate dueDate;

    private Integer requestStatus;        
    private Integer reviewerEmployeeId;
    private Integer participantStatus;    

    private String mailStatus;           
    private String responseStatus;       

    private String accessToken;
    private LocalDateTime tokenExpiry;
    private LocalDateTime mailSentOn;
    private LocalDateTime respondedOn;
    private LocalDateTime createdOn;

    // Getters and Setters

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
}
