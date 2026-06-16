package com.companyname.employeeappraisal.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.companyname.employeeappraisal.model.MasterFinancialYear;

public interface MasterFinancialYearRepository extends JpaRepository<MasterFinancialYear,Integer>{


	 @Query("SELECT m FROM MasterFinancialYear m WHERE m.status = :status") 
	    Optional<MasterFinancialYear> findFinancialYear(@Param("status") int status);


//	Optional<MasterFinancialYear> findByFinancialYearCodeAndStatus(String financialYearCode, int i);
//

	Optional<MasterFinancialYear> findByFinancialYearCode(String financialyear);


//	List<MasterFinancialYear> findByStatus(int status);
}
