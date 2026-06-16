package com.companyname.employeeappraisal.dto;

import java.util.List;

public class AppModuleDTO {
	private Integer moduleId;
	private String moduleName;
	private String enabled;
	private Double averageRating;
	private int roundedAverageRating;

	private List<EmployeeAppraisalDetailDTO> EmployeeAppraisal;
	

	public AppModuleDTO(Integer moduleId, String moduleName, String enabled) {
		super();
		this.moduleId = moduleId;
		this.moduleName = moduleName;
		this.enabled = enabled;
	}

	public Integer getModuleId() {
		return moduleId;
	}

	public void setModuleId(Integer moduleId) {
		this.moduleId = moduleId;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getEnabled() {
		return enabled;
	}

	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}

	public List<EmployeeAppraisalDetailDTO> getEmployeeAppraisal() {
		return EmployeeAppraisal;
	}

	public void setEmployeeAppraisal(List<EmployeeAppraisalDetailDTO> employeeAppraisal) {
		EmployeeAppraisal = employeeAppraisal;
	}

	public AppModuleDTO(Integer moduleId, String moduleName, String enabled,
			List<EmployeeAppraisalDetailDTO> employeeAppraisal) {
		super();
		this.moduleId = moduleId;
		this.moduleName = moduleName;
		this.enabled = enabled;
		EmployeeAppraisal = employeeAppraisal;
	}

	public AppModuleDTO(Integer moduleId, String moduleName) {
		super();
		this.moduleId = moduleId;
		this.moduleName = moduleName;
	}

	public AppModuleDTO() {
		// TODO Auto-generated constructor stub
	}

	public Double getAverageRating() {
		return averageRating;
	}

	public void setAverageRating(Double averageRating) {
		this.averageRating = averageRating;
	}

	public int getRoundedAverageRating() {
		return roundedAverageRating;
	}

	public void setRoundedAverageRating(int roundedAverageRating) {
		this.roundedAverageRating = roundedAverageRating;
	}

}
