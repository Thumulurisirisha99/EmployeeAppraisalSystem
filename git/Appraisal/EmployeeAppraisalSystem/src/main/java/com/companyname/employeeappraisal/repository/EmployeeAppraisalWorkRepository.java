package com.companyname.employeeappraisal.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.companyname.employeeappraisal.model.EmployeeAppraisalWork;

public interface EmployeeAppraisalWorkRepository extends JpaRepository<EmployeeAppraisalWork,Integer> {

	List<EmployeeAppraisalWork> findByEmployeeId(Integer employeeId);

	List<EmployeeAppraisalWork> findByEmployeeIdAndFinancialYear_Year(Integer employeeId, Integer year);

	List<EmployeeAppraisalWork> findByEmployeeIdAndFinancialYear_FinancialYearIdAndStatusIn(Integer employeeId,
			Integer financialYearId, List<Integer> statuses);

	EmployeeAppraisalWork findByWorkId(Integer workId);

	List<EmployeeAppraisalWork> findByEmployeeIdAndStatusInAndCreatedDateBetween(Integer employeeId,
			List<Integer> statuses, LocalDateTime fromDate, LocalDateTime toDate);

	List<EmployeeAppraisalWork> findByEmployeeIdOrderByCreatedDateDesc(Integer employeeId);

}
