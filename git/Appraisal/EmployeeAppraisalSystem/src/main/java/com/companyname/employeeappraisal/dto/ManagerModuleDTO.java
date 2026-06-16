package com.companyname.employeeappraisal.dto;

import java.util.List;

public class ManagerModuleDTO {
    private String flag;  
    private Integer Status;
    private List<AppModuleDTO> appModule;

    public ManagerModuleDTO() {
    }

    public ManagerModuleDTO(String flag, List<AppModuleDTO> appModule) {
        this.flag = flag;
        this.appModule = appModule;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public List<AppModuleDTO> getAppModule() {
        return appModule;
    }

    public void setAppModule(List<AppModuleDTO> appModule) {
        this.appModule = appModule;
    }

	public Integer getStatus() {
		return Status;
	}

	public void setStatus(Integer status) {
		Status = status;
	}
}
