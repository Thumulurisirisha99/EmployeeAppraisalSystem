package com.companyname.employeeappraisal.dto;

import java.util.List;

public class FeedbackSubmitRequestDTO {

    private Integer participantId;
    private List<FeedbackParticipantDTO> categoryDtos;

    public Integer getParticipantId() {
        return participantId;
    }

    public void setParticipantId(Integer participantId) {
        this.participantId = participantId;
    }

    public List<FeedbackParticipantDTO> getCategoryDtos() {
        return categoryDtos;
    }

    public void setCategoryDtos(List<FeedbackParticipantDTO> categoryDtos) {
        this.categoryDtos = categoryDtos;
    }
}
