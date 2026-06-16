package com.companyname.employeeappraisal.dto;

import java.time.LocalDateTime;
import java.util.List;

public class ManagerEmployeeAppraisalDTO {

    private Integer appraisalId;
    private Integer managerId;
	private String type;
    private Integer status;
//    private String modeType;
	private LocalDateTime createdDate;
    private List<EmployeeAppraisalDetailDTO> details;

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

//	public String getModeType() {
//		return modeType;
//	}
//
//	public void setModeType(String modeType) {
//		this.modeType = modeType;
//	}



  
}
