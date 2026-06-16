package com.companyname.employeeappraisal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.companyname.employeeappraisal.model.EmployeeAppraisalDetails;

public interface EmployeeAppraisalDetailsRepository extends JpaRepository<EmployeeAppraisalDetails,Integer> {

	EmployeeAppraisalDetails findByAppraisal_AppraisalIdAndQuestion_QuestionId(Integer appraisalId, Integer questionId);


	List<EmployeeAppraisalDetails> findByAppraisal_AppraisalId(Integer appraisalId);

}
