package com.companyname.employeeappraisal.dto;

import java.util.List;

public class EmployeeAppraisalWorkResponseDTO {
    private String financialYear;
    private List<EmployeeAppraisalWorkDTO> works;
	public String getFinancialYear() {
		return financialYear;
	}
	public void setFinancialYear(String financialYear) {
		this.financialYear = financialYear;
	}
	public List<EmployeeAppraisalWorkDTO> getWorks() {
		return works;
	}
	public void setWorks(List<EmployeeAppraisalWorkDTO> works) {
		this.works = works;
	}
    
}
