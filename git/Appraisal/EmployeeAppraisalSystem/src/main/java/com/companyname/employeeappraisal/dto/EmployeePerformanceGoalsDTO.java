package com.companyname.employeeappraisal.dto;

import java.util.List;
import java.util.Map;

public class EmployeePerformanceGoalsDTO {
	private String employeeId;
	private String password;
	private String fullName;
	private String department;
	private String designation;
	private List<PerformanceGoalDTO> goals;
	private List<Map<String, Object>> goalsByYear;

	public EmployeePerformanceGoalsDTO(String employeeId, String password, String fullName, String department,
			String designation, List<PerformanceGoalDTO> goals, List<Map<String, Object>> goalsByYear) {
		super();
		this.employeeId = employeeId;
		this.password = password;
		this.fullName = fullName;
		this.department = department;
		this.designation = designation;
		this.goals = goals;
		this.goalsByYear = goalsByYear;
	}

	public EmployeePerformanceGoalsDTO() {
		// TODO Auto-generated constructor stub
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public List<PerformanceGoalDTO> getGoals() {
		return goals;
	}

	public void setGoals(List<PerformanceGoalDTO> goals) {
		this.goals = goals;
	}

	public List<Map<String, Object>> getGoalsByYear() {
		return goalsByYear;
	}

	public void setGoalsByYear(List<Map<String, Object>> goalsByYear) {
		this.goalsByYear = goalsByYear;
	}

	@Override
	public String toString() {
		return "EmployeePerformanceGoalsDTO [employeeId=" + employeeId + ", password=" + password + ", fullName="
				+ fullName + ", department=" + department + ", designation=" + designation + ", goals=" + goals
				+ ", goalsByYear=" + goalsByYear + "]";
	}

}
