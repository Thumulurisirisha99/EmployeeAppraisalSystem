package com.companyname.employeeappraisal.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.companyname.employeeappraisal.model.MasterQuarter;

public interface MasterQuarterRepository extends JpaRepository<MasterQuarter, Integer> {

	@Query(value = "SELECT quarter_id, quarter_code, months_included, start_date, end_date, created_on "
			+ "FROM performance_appraisal_system.tbl_master_quarter " + "WHERE CURDATE() BETWEEN "
			+ "STR_TO_DATE(CONCAT(start_date, '-', YEAR(CURDATE())), '%d-%b-%Y') "
			+ "AND STR_TO_DATE(CONCAT(end_date, '-', YEAR(CURDATE())), '%d-%b-%Y')", nativeQuery = true)
	MasterQuarter findCurrentQuarter();

	  Optional<MasterQuarter> findByQuarterCode(String quarterCode);

	
}
