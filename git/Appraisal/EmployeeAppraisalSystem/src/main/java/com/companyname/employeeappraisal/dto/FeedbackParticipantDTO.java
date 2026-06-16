package com.companyname.employeeappraisal.dto;

import java.util.List;

public class FeedbackParticipantDTO {
    private Integer categoryId;
	 private String categoryName;
    private List<QuestionAnswerDTO> answers;
	public Integer getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
	public List<QuestionAnswerDTO> getAnswers() {
		return answers;
	}
	public void setAnswers(List<QuestionAnswerDTO> answers) {
		this.answers = answers;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	

}