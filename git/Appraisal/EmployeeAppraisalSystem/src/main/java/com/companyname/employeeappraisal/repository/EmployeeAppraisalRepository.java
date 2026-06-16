package com.companyname.employeeappraisal.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.companyname.employeeappraisal.dto.ResponseDTO;
import com.companyname.employeeappraisal.model.EmployeeAppraisal;
import com.companyname.employeeappraisal.model.MasterFinancialYear;
import com.companyname.employeeappraisal.model.PerformanceGoal;

public interface EmployeeAppraisalRepository extends JpaRepository<EmployeeAppraisal,Integer> {

	//boolean existsByEmployeeIdAndFinancialYearAndStatus(Integer employeeId, String financialYear, int status);

//	EmployeeAppraisal findByEmployeeIdAndFinancialYearAndQuarter_QuarterId(Integer employeeId, String financialYear,Integer quarterId);

	EmployeeAppraisal findByEmployeeIdAndFinancialYear_FinancialYearIdAndQuarter_QuarterId(Integer employeeId,
			Integer financialYearId, Integer quarterId);

	int countByEmployeeIdInAndStatusInAndFinancialYear_FinancialYearIdAndQuarter_QuarterId(
			List<Integer> subordinateEmpSeqNos, List<Integer> asList, Integer financialYearId, Integer quarterId);

	Optional<EmployeeAppraisal> findByAppraisalIdAndStatus(Integer appraisalId, int status);

    @Query("SELECT e.status FROM EmployeeAppraisal e WHERE e.appraisalId = :appraisalId")
    Optional<Integer> findStatusByAppraisalId(@Param("appraisalId") Integer appraisalId);
    
    Optional<EmployeeAppraisal> findById(Integer appraisalId);


	EmployeeAppraisal findByAppraisalId(Integer appraisalId);

	List<EmployeeAppraisal> findByEmployeeIdAndStatusOrderByFinancialYear_FinancialYearIdDesc(Integer employeeId, int i,
			PageRequest of);

	List<EmployeeAppraisal> findByEmployeeIdAndStatus(Integer employeeId, int i);

	Optional<EmployeeAppraisal> findByAppraisalIdAndStatusIn(Integer appraisalId, List<Integer> asList);

	Optional<EmployeeAppraisal> findByEmployeeIdAndStatusInAndFinancialYear_FinancialYearIdAndQuarter_QuarterId(
			Integer employeeId, List<Integer> statuses, Integer financialYearId, Integer quarterId);

	Optional<EmployeeAppraisal> findByEligibleEmployee_EligibleId(Integer eligibleId);
	
	@Query("SELECT a FROM EmployeeAppraisal a JOIN FETCH a.financialYear WHERE a.appraisalId = :appraisalId AND a.status IN :statuses")
	Optional<EmployeeAppraisal> findByAppraisalIdAndStatusInWithFinancialYear(@Param("appraisalId") Integer appraisalId, @Param("statuses") List<Integer> statuses);

	int countByEmployeeIdInAndStatusIn(List<Integer> subordinateEmpSeqNos, List<Integer> asList);

	int countByEmployeeIdInAndStatus(List<Integer> subordinateEmpSeqNos, int status);


}
