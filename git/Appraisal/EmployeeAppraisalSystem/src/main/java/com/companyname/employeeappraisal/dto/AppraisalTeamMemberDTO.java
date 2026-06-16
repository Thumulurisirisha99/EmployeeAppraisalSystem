package com.companyname.employeeappraisal.dto;

public class AppraisalTeamMemberDTO {

    private Integer employeeId;
    private String employeeName;
    private String department;
    private String designation;
    private Integer appraisalId;
    private String emailId;
   private String requestedDate;
   private Integer actionStatus;
   private Integer participantId;
   private Integer peerFeedbackRequested;
   private Integer peerFeedbackCompleted;
   private String lastUpdated;
   private String AddedToAppraisal;

	public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public Integer getAppraisalId() {
		return appraisalId;
	}

	public void setAppraisalId(Integer appraisalId) {
		this.appraisalId = appraisalId;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getRequestedDate() {
		return requestedDate;
	}

	public void setRequestedDate(String requestedDate) {
		this.requestedDate = requestedDate;
	}

	public Integer getActionStatus() {
		return actionStatus;
	}

	public void setActionStatus(Integer actionStatus) {
		this.actionStatus = actionStatus;
	}

	
	public Integer getParticipantId() {
		return participantId;
	}

	public void setParticipantId(Integer participantId) {
		this.participantId = participantId;
	}

	public AppraisalTeamMemberDTO() {
		// TODO Auto-generated constructor stub
	}

	

	public Integer getPeerFeedbackRequested() {
		return peerFeedbackRequested;
	}

	public void setPeerFeedbackRequested(Integer peerFeedbackRequested) {
		this.peerFeedbackRequested = peerFeedbackRequested;
	}

	public Integer getPeerFeedbackCompleted() {
		return peerFeedbackCompleted;
	}

	public void setPeerFeedbackCompleted(Integer peerFeedbackCompleted) {
		this.peerFeedbackCompleted = peerFeedbackCompleted;
	}

	public String getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(String lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public String getAddedToAppraisal() {
		return AddedToAppraisal;
	}

	public void setAddedToAppraisal(String addedToAppraisal) {
		this.AddedToAppraisal = addedToAppraisal;
	}
	

//	public AppraisalTeamMemberDTO(Integer employeeId, String employeeName, String department, String designation,
//			Integer appraisalId, String emailId, String requestedDate, Integer actionStatus, Integer participantId) {
//		super();
//		this.employeeId = employeeId;
//		this.employeeName = employeeName;
//		this.department = department;
//		this.designation = designation;
//		this.appraisalId = appraisalId;
//		this.emailId = emailId;
//		this.requestedDate = requestedDate;
//		this.actionStatus = actionStatus;
//		this.participantId = participantId;
//	}


	
}
