package com.companyname.employeeappraisal.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.companyname.employeeappraisal.model.HodEmployeeAppraisal;

public interface HodEmployeeAppraisalRepository extends JpaRepository<HodEmployeeAppraisal, Integer> {

//	Optional<HodEmployeeAppraisal> findByEmployeeAppraisal_AppraisalIdAndHodIdAndQuestion_QuestionId(
//		    Integer appraisalId, Integer hodId, Integer questionId);

	List<HodEmployeeAppraisal> findByEmployeeAppraisal_AppraisalIdAndHodId(Integer appraisalId, Integer hodId);

	List<HodEmployeeAppraisal> findByHodIdAndEmployeeAppraisal_AppraisalId(Integer hodId, Integer appraisalId);

	List<HodEmployeeAppraisal> findByEmployeeAppraisal_AppraisalIdAndHodIdAndQuestion_QuestionId(
			Integer appraisalId, Integer hodId, Integer questionId);

	List<HodEmployeeAppraisal> findByEmployeeAppraisal_AppraisalIdAndStatus(Integer appraisalId, int i);

	List<HodEmployeeAppraisal> findByEmployeeAppraisal_AppraisalId(Integer appraisalId);

	List<HodEmployeeAppraisal> findByHodIdAndEmployeeAppraisal_AppraisalIdAndStatus(Integer hodId, Integer appraisalId,
			int status);
}
