package com.companyname.employeeappraisal.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.companyname.employeeappraisal.model.EmployeeAppraisalApprovers;

public interface EmployeeAppraisalApproversRepository extends JpaRepository<EmployeeAppraisalApprovers, Integer> {

	boolean existsByAppraisal_AppraisalId(Integer appraisalId);

	Optional<EmployeeAppraisalApprovers> findByAppraisal_AppraisalIdAndApproverEmpId(Integer appraisalId,
			Integer approverEmpId);

	EmployeeAppraisalApprovers findByAppraisal_AppraisalIdAndApproverLevel(Integer appraisalId, Integer approverLevel);

	List<EmployeeAppraisalApprovers> findByAppraisal_AppraisalIdAndApproverStatusAndApproverRole(Integer appraisalId,
			Integer approverStatus, String approverRole);

//	List<EmployeeAppraisalApprovers> findByAppraisal_AppraisalIdAndApproverRoleAndApproverLevelLessThanEqual(
//			Integer appraisalId, String string, Integer currentLevel);

	List<EmployeeAppraisalApprovers> findByAppraisal_AppraisalIdAndApproverLevelLessThanAndApproverStatus(
			Integer appraisalId, Integer currentLevel, int i);

	List<EmployeeAppraisalApprovers> findByAppraisal_AppraisalIdAndApproverRoleAndApproverStatus(Integer appraisalId,
			String string, int i);

	List<EmployeeAppraisalApprovers> findByAppraisal_AppraisalIdAndApproverRoleAndApproverLevelLessThanAndApproverStatus(
			Integer appraisalId, String string, Integer level, int i);

	List<EmployeeAppraisalApprovers> findByAppraisal_AppraisalIdAndApproverRole(Integer appraisalId, String string);

	@Query(value = """
			    SELECT COUNT(DISTINCT a.appraisal_id)
			    FROM tbl_employee_appraisal_approvers a
			    WHERE a.approver_emp_id = :managerId
			      AND a.approver_role IN ('M','H')
			      AND a.approver_status = 1003
			      AND a.appraisal_id IN (
			          SELECT ea.appraisal_id
			          FROM tbl_employee_appraisal ea
			          WHERE ea.employeeid IN (:empIds)
			      )
			""", nativeQuery = true)
	int countManagerReviewCompleted(@Param("empIds") List<Integer> empIds, @Param("managerId") Integer managerId);

}
