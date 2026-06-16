package com.companyname.employeeappraisal.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.companyname.employeeappraisal.model.PerformanceGoal;

public interface PerformanceGoalRepository extends JpaRepository<PerformanceGoal, Integer> {

	List<PerformanceGoal> findByEmployeeIdAndStatusNot(Integer employeeid, int Status);

	PerformanceGoal findByGoalId(int goalId);

	List<PerformanceGoal> findByEmployeeIdOrderByCreatedDateDesc(Integer employeeId);

	List<PerformanceGoal> findByEmployeeIdAndStatusNotInOrderByCreatedDateDesc(Integer employeeId,
			List<Integer> excludedStatuses);

	int countByEmployeeIdInAndStatusInAndFinancialYearFinancialYearIdAndQuarterQuarterId(
			List<Integer> subordinateEmpSeqNos, List<Integer> statuses, int financialYearId, int quarterId);

//	List<PerformanceGoal> findByEmployeeIdAndFinancialYear_YearAndStatusIn(Integer employeeIdStr, Integer year,
//			List<Integer> statuses);

	List<PerformanceGoal> findByEmployeeIdAndFinancialYear_FinancialYearIdAndStatusIn(Integer employeeId,
			Integer financialYearId, List<Integer> statuses);

	@Query(value = "SELECT * FROM tbl_performance_goals WHERE DATE(createddate) BETWEEN :startDate AND :endDate", nativeQuery = true)
	List<PerformanceGoal> findByCreatedDateBetweenDatesOnly(@Param("startDate") LocalDate startDate,
			@Param("endDate") LocalDate endDate);
	List<PerformanceGoal> findByEmployeeIdAndStatusInAndCreatedDateBetween(
		    Integer employeeId,
		    List<Integer> statuses,
		    LocalDateTime fromDate,
		    LocalDateTime toDate
		);

	int countByEmployeeIdInAndStatusIn(List<Integer> subordinateEmpSeqNos, List<Integer> asList);

	List<PerformanceGoal> findByEmployeeIdAndStatusNotAndCreatedDateBetween(Integer employeeId, int status,
			LocalDateTime fromDate, LocalDateTime toDate);

//	@Query(value = "SELECT * FROM tbl_performance_goals p WHERE p.employeeid = :employeeId AND p.status IN (:statuses) AND DATE(p.createddate) BETWEEN :fromDate AND :toDate", nativeQuery = true)
//	List<PerformanceGoal> findGoalsByEmployeeIdStatusAndCreatedDateBetweenDatesOnly(
//	    @Param("employeeId") Integer employeeId,
//	    @Param("statuses") List<Integer> statuses,
//	    @Param("fromDate") LocalDateTime fromDateTime,
//	    @Param("toDate") LocalDateTime toDateTime);
//	@Query(value = "SELECT * FROM tbl_performance_goals p WHERE p.employeeid = :employeeId AND p.status IN (:statuses) AND DATE(p.createddate) BETWEEN :fromDate AND :toDate", nativeQuery = true)
//	List<PerformanceGoal> findGoalsByEmployeeIdStatusAndCreatedDateBetweenDatesOnly(Integer employeeId,
//			List<Integer> statuses, LocalDate fromDate, LocalDate toDate);



}
