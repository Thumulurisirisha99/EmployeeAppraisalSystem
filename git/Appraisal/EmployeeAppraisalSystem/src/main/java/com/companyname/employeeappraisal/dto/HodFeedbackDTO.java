package com.companyname.employeeappraisal.dto;

import java.util.List;

import com.companyname.employeeappraisal.dto.AppModuleDTO;

public class HodFeedbackDTO {

    private Integer managerId;
    private String managerName;
    private Integer approverLevel;
    private String flag;
    private Integer status;
    private List<AppModuleDTO> modules;
	
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
	public List<AppModuleDTO> getModules() {
		return modules;
	}
	public void setModules(List<AppModuleDTO> modules) {
		this.modules = modules;
	}
	public Integer getApproverLevel() {
		return approverLevel;
	}
	public void setApproverLevel(Integer approverLevel) {
		this.approverLevel = approverLevel;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}


}
