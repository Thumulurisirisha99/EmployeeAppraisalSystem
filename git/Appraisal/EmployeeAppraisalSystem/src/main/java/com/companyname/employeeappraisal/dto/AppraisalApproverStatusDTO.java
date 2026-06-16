package com.companyname.employeeappraisal.dto;

public class AppraisalApproverStatusDTO {

	private Integer approverStatus;
	private Integer managerId;
	private String managerName;
	private String status;

	public AppraisalApproverStatusDTO() {
	}

	public AppraisalApproverStatusDTO(Integer approverStatus, Integer managerId, String managerName, String status) {
		this.approverStatus = approverStatus;
		this.managerId = managerId;
		this.managerName = managerName;
		this.status = status;
	}

	public Integer getApproverStatus() {
		return approverStatus;
	}

	public void setApproverStatus(Integer approverStatus) {
		this.approverStatus = approverStatus;
	}

	public Integer getManagerId() {
		return managerId;
	}

	public void setManagerId(Integer managerId) {
		this.managerId = managerId;
	}

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
