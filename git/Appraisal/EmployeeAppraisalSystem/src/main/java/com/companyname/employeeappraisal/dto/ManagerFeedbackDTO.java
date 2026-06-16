package com.companyname.employeeappraisal.dto;

import java.util.List;

public class ManagerFeedbackDTO {

    private Integer managerId;
    private String managerName;
    private Integer approverLevel;
    private String flag;
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

	public Integer getApproverLevel() {
        return approverLevel;
    }

    public void setApproverLevel(Integer approverLevel) {
        this.approverLevel = approverLevel;
    }

    public List<AppModuleDTO> getModules() {
        return modules;
    }

    public void setModules(List<AppModuleDTO> modules) {
        this.modules = modules;
    }

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	
    
}
