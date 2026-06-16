package com.companyname.employeeappraisal.dto;

public class ModuleQuestionAnswerCountDTO {

    private Integer moduleId;
    private Long questionCount;
    private Long submittedAnswerCount;

    public Integer getModuleId() {
        return moduleId;
    }

    public void setModuleId(Integer moduleId) {
        this.moduleId = moduleId;
    }

    public Long getQuestionCount() {
        return questionCount;
    }

    public void setQuestionCount(Long questionCount) {
        this.questionCount = questionCount;
    }

	public Long getSubmittedAnswerCount() {
		return submittedAnswerCount;
	}

	public void setSubmittedAnswerCount(Long submittedAnswerCount) {
		this.submittedAnswerCount = submittedAnswerCount;
	}

  
}
