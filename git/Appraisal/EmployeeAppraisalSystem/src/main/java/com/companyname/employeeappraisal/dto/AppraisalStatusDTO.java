package com.companyname.employeeappraisal.dto;
public class AppraisalStatusDTO {
    private Integer employeeId;
    private String employeeName;
    private String department;
    private String selfAppraisal;
    private String approverStatus;
  

	public AppraisalStatusDTO(Integer employeeId, String employeeName, String department, String selfAppraisal,
			String approverStatus) {
		super();
		this.employeeId = employeeId;
		this.employeeName = employeeName;
		this.department = department;
		this.selfAppraisal = selfAppraisal;
		this.approverStatus = approverStatus;
	}

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

	public String getSelfAppraisal() {
		return selfAppraisal;
	}

	public void setSelfAppraisal(String selfAppraisal) {
		this.selfAppraisal = selfAppraisal;
	}

	public String getApproverStatus() {
		return approverStatus;
	}

	public void setApproverStatus(String approverStatus) {
		this.approverStatus = approverStatus;
	}


}
