package com.companyname.employeeappraisal.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.companyname.employeeappraisal.model.Employee360FeedbackParticipant;

public interface Employee360FeedbackParticipantRepository extends JpaRepository<Employee360FeedbackParticipant,Integer>{

	Optional<Employee360FeedbackParticipant> findByAppraisalIdAndReviewerEmployeeId(Integer appraisalId, Integer reviewerEmployeeId);

	List<Employee360FeedbackParticipant> findByMailStatus(String string);

	long countByManagerIdAndAppraisalIdAndParticipantStatusIn(Integer managerSeq, Integer appraisalId,
			List<Integer> feedbackStatuses);

	Optional<Employee360FeedbackParticipant> findTopByManagerIdAndAppraisalIdAndParticipantStatusInOrderByRespondedOnDesc(
			Integer managerSeq, Integer appraisalId, List<Integer> feedbackStatuses);

	List<Employee360FeedbackParticipant> findByAppraisalId(Integer appraisalId);

	List<Employee360FeedbackParticipant> findByAppraisalIdAndParticipantStatus(Integer appraisalId, Integer participantStatus);
	@Query(value = "SELECT " +
	        "COUNT(DISTINCT el.employeeid) AS totalEmployees, " +

	        "COALESCE(SUM(CASE WHEN p.participant_status = 1002 THEN 1 ELSE 0 END), 0) AS requested, " +

	        "COALESCE(SUM(CASE WHEN p.participant_status = 1003 THEN 1 ELSE 0 END), 0) AS submitted " +

	        "FROM performance_appraisal_system.tbl_appraisal_eligible_employees el " +

	        "LEFT JOIN performance_appraisal_system.tbl_employee_appraisal ep " +
	        "ON el.employeeid = ep.employeeid " +
	        "AND el.financial_year_id = ep.financial_year_id " +

	        "LEFT JOIN employee_360_feedback_participant p " +
	        "ON p.appraisal_id = ep.appraisal_id " +
	        "AND p.manager_id = :managerId " +

	        "WHERE el.employeeid IN (:subordinates)",
	        nativeQuery = true)
	Map<String, Object> getEligibleFeedbackSummary(
	        @Param("managerId") Integer managerId,
	        @Param("subordinates") List<Integer> subordinates);


}
