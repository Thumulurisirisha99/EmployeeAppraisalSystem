package com.companyname.employeeappraisal.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.companyname.employeeappraisal.model.AppraisalEligibleEmployee;

public interface AppraisalEligibleEmployeeRepository extends JpaRepository<AppraisalEligibleEmployee,Integer>{

	long countByEmployeeIdInAndFinancialYear_FinancialYearIdAndQuarter_QuarterIdAndStatus(
			List<Long> subordinateEmpSeqNosLong, Integer financialYearId, Integer quarterId, int i);


	boolean existsByEmployeeIdAndStatus(Integer employeeId, Integer status);

	Optional<AppraisalEligibleEmployee> findByEmployeeIdAndQuarter_QuarterIdAndFinancialYear_FinancialYearIdAndStatus(
		    Integer employeeId, Integer quarterId, Integer financialYearId, Integer status);


	Optional<AppraisalEligibleEmployee> findTopByEmployeeIdAndStatusOrderByEligibleIdDesc(Integer employeeId, int i);

	Optional<AppraisalEligibleEmployee> findTopByEmployeeIdAndStatusInOrderByEligibleIdDesc(Integer employeeId,
			List<Integer> asList);
	
	 List<AppraisalEligibleEmployee>
	    findByEmployeeIdAndFinancialYear_FinancialYearIdAndQuarter_QuarterId(
	            Integer employeeId,
	            Integer financialYearId,
	            Integer quarterId);


	Optional<AppraisalEligibleEmployee> findByEmployeeIdAndStatusInAndFinancialYear_FinancialYearIdAndQuarter_QuarterId(
			Integer employeeId, List<Integer> statuses, Integer financialYearId, Integer quarterId);


	Optional<AppraisalEligibleEmployee> findByEmployeeIdAndFinancialYear_FinancialYearIdAndQuarter_QuarterIdAndStatus(
			Integer employeeId, Integer financialYearId, Integer quarterId, int status);


//	long countByEmployeeIdInAndStatus(List<Long> subordinateEmpSeqNosLong, int i);



	long countByEmployeeIdIn(List<Long> list);


}
