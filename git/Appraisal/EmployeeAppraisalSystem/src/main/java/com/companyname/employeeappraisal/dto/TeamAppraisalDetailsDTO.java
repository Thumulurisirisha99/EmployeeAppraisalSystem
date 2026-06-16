
package com.companyname.employeeappraisal.dto;

import java.util.List;

public class TeamAppraisalDetailsDTO {

	private Integer employeeId;
	private String employeeName;
	private Integer departmentId;

	private String department;
	private String designation;
	private String gender;
	private String goalStatus;
	private String appraisalStatus;
	private String mngReview;
	private String hodReview;
	private String overallRating;

	private String doj;
	private String location;
	private String workingDays;
	private String prevExp;
	private String curExp;
	private String totalExp;
	private Integer appraisalId;
	private Integer eligibleId;
	private String utilizedLeaves;
	private String lossOfPay;
	private String enable;
	private String status;
	private Double selfRating;
	private Double managerRating;
	private Double hodRating;
	private List<EducationDetails> educationDetails;

	private List<AppraisalApproverStatusDTO> managerReview;
	private List<FeedbackRequestDTO> feedbackRequestDTO;

	public TeamAppraisalDetailsDTO() {
	}

	// --- Getters & Setters ---
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

	public Integer getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
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

	public String getGoalStatus() {
		return goalStatus;
	}

	public void setGoalStatus(String goalStatus) {
		this.goalStatus = goalStatus;
	}

	public String getAppraisalStatus() {
		return appraisalStatus;
	}

	public void setAppraisalStatus(String appraisalStatus) {
		this.appraisalStatus = appraisalStatus;
	}

	public String getMngReview() {
		return mngReview;
	}

	public void setMngReview(String mngReview) {
		this.mngReview = mngReview;
	}

	public String getHodReview() {
		return hodReview;
	}

	public void setHodReview(String hodReview) {
		this.hodReview = hodReview;
	}

	public String getOverallRating() {
		return overallRating;
	}

	public void setOverallRating(String overallRating) {
		this.overallRating = overallRating;
	}

	public String getDoj() {
		return doj;
	}

	public void setDoj(String doj) {
		this.doj = doj;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getWorkingDays() {
		return workingDays;
	}

	public void setWorkingDays(String workingDays) {
		this.workingDays = workingDays;
	}

	public String getPrevExp() {
		return prevExp;
	}

	public void setPrevExp(String prevExp) {
		this.prevExp = prevExp;
	}

	public String getCurExp() {
		return curExp;
	}

	public void setCurExp(String curExp) {
		this.curExp = curExp;
	}

	public String getTotalExp() {
		return totalExp;
	}

	public void setTotalExp(String totalExp) {
		this.totalExp = totalExp;
	}

	public List<EducationDetails> getEducationDetails() {
		return educationDetails;
	}

	public void setEducationDetails(List<EducationDetails> educationDetails) {
		this.educationDetails = educationDetails;
	}

	public Integer getAppraisalId() {
		return appraisalId;
	}

	public void setAppraisalId(Integer appraisalId) {
		this.appraisalId = appraisalId;
	}

	public String getUtilizedLeaves() {
		return utilizedLeaves;
	}

	public void setUtilizedLeaves(String utilizedLeaves) {
		this.utilizedLeaves = utilizedLeaves;
	}

	public String getLossOfPay() {
		return lossOfPay;
	}

	public void setLossOfPay(String lossOfPay) {
		this.lossOfPay = lossOfPay;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public List<AppraisalApproverStatusDTO> getManagerReview() {
		return managerReview;
	}

	public void setManagerReview(List<AppraisalApproverStatusDTO> managerReview) {
		this.managerReview = managerReview;
	}

	public String getEnable() {
		return enable;
	}

	public void setEnable(String enable) {
		this.enable = enable;
	}

	public List<FeedbackRequestDTO> getFeedbackRequestDTO() {
		return feedbackRequestDTO;
	}

	public void setFeedbackRequestDTO(List<FeedbackRequestDTO> feedbackRequestDTO) {
		this.feedbackRequestDTO = feedbackRequestDTO;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getEligibleId() {
		return eligibleId;
	}

	public void setEligibleId(Integer eligibleId) {
		this.eligibleId = eligibleId;
	}

	public Double getSelfRating() {
		return selfRating;
	}

	public void setSelfRating(Double selfRating) {
		this.selfRating = selfRating;
	}

	public Double getManagerRating() {
		return managerRating;
	}

	public void setManagerRating(Double managerRating) {
		this.managerRating = managerRating;
	}

	public Double getHodRating() {
		return hodRating;
	}

	public void setHodRating(Double hodRating) {
		this.hodRating = hodRating;
	}

}
