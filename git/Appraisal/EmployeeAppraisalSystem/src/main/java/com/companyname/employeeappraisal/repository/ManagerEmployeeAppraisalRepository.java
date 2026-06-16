package com.companyname.employeeappraisal.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.companyname.employeeappraisal.model.ManagerEmployeeAppraisal;

public interface ManagerEmployeeAppraisalRepository extends JpaRepository<ManagerEmployeeAppraisal,Integer> {

	Optional<ManagerEmployeeAppraisal> findByEmployeeAppraisal_AppraisalIdAndManagerIdAndQuestion_QuestionId(
			Integer appraisalId, Integer managerId, Integer questionId);

	List<ManagerEmployeeAppraisal> findByEmployeeAppraisal_AppraisalIdAndManagerId(Integer appraisalId, Integer managerId);

//	List<ManagerEmployeeAppraisal> findByEmployeeAppraisal_AppraisalId(Integer appraisalId);

	//List<ManagerEmployeeAppraisal> findByManagerIdAndAppraisalId(Integer managerId, Integer appraisalId);
	List<ManagerEmployeeAppraisal> findByManagerIdAndEmployeeAppraisal_AppraisalId(Integer managerId, Integer appraisalId);

	Integer findStatusByEmployeeAppraisal_AppraisalIdAndManagerId(Integer appraisalId, Integer managerId);

//	List<ManagerEmployeeAppraisal> findByEmployeeAppraisal_AppraisalIdAndStatus(Integer appraisalId, int i);

	List<ManagerEmployeeAppraisal> findByEmployeeAppraisal_AppraisalId(Integer appraisalId);

	List<ManagerEmployeeAppraisal> findByManagerIdAndEmployeeAppraisal_AppraisalIdAndStatus(Integer managerId,
			Integer appraisalId, int status);

}
