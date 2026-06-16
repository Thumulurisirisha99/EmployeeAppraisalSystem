package com.companyname.employeeappraisal.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.companyname.employeeappraisal.model.MasterFinancialYear;
import com.companyname.employeeappraisal.model.MasterWorkType;

public interface MasterWorkTypeRepository extends JpaRepository<MasterWorkType, Integer> {

	Optional<MasterWorkType> findByWorkTypeAndStatus(String workTypeText, int status);

}
