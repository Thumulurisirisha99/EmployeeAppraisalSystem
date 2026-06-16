package com.companyname.employeeappraisal.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.companyname.employeeappraisal.model.Employee360FeedbackAnswer;

public interface Employee360FeedbackAnswerRepository extends JpaRepository<Employee360FeedbackAnswer,Integer> {

	
	Optional<Employee360FeedbackAnswer> findByParticipant_ParticipantIdAndCategory_CategoryIdAndQuestionText(
			Integer participantId, Integer categoryId, String questionText);

	List<Employee360FeedbackAnswer> findByParticipantParticipantId(Integer participantId);


}
