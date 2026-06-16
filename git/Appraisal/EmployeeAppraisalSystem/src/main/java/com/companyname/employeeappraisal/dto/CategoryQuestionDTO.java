package com.companyname.employeeappraisal.dto;

public class CategoryQuestionDTO {

    private Integer categoryId;
    private String categoryName;
    private String questionText;
    private String answerText;
    private Integer participantId;

    public CategoryQuestionDTO(Integer categoryId,
                               String categoryName,
                               String questionText,
                               String answerText,
                               Integer participantId) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.questionText = questionText;
        this.answerText = answerText;
        this.participantId = participantId;
    }

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getQuestionText() {
		return questionText;
	}

	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}

	public String getAnswerText() {
		return answerText;
	}

	public void setAnswerText(String answerText) {
		this.answerText = answerText;
	}

	public Integer getParticipantId() {
		return participantId;
	}

	public void setParticipantId(Integer participantId) {
		this.participantId = participantId;
	}

}
