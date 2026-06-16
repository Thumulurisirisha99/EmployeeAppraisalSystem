package com.companyname.employeeappraisal.dto;
public class FeedbackSummaryDTO {

    private Long totalEmployees;
    private Long requestedCount;
    private Long submittedCount;
	
	public FeedbackSummaryDTO(Long totalEmployees, Long requestedCount, Long submittedCount) {
		super();
		this.totalEmployees = totalEmployees;
		this.requestedCount = requestedCount;
		this.submittedCount = submittedCount;
	}
	public FeedbackSummaryDTO() {
		// TODO Auto-generated constructor stub
	}
	public Long getTotalEmployees() {
		return totalEmployees;
	}
	public void setTotalEmployees(Long totalEmployees) {
		this.totalEmployees = totalEmployees;
	}
	public Long getRequestedCount() {
		return requestedCount;
	}
	public void setRequestedCount(Long requestedCount) {
		this.requestedCount = requestedCount;
	}
	public Long getSubmittedCount() {
		return submittedCount;
	}
	public void setSubmittedCount(Long submittedCount) {
		this.submittedCount = submittedCount;
	}

}
