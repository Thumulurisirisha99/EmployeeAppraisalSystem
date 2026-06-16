package com.companyname.employeeappraisal.dto;

public class EducationDetails {
	private String qualification;
	private String year;


	public EducationDetails(String qualification, String year) {
		super();
		this.qualification = qualification;
		this.year = year;
	}

	public String getQualification() {
		return qualification;
	}

	public void setQualification(String qualification) {
		this.qualification = qualification;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

}
