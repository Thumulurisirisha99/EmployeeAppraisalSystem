package com.companyname.employeeappraisal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.companyname.employeeappraisal.model.EmployeeRole;

@Repository
public interface EmployeeRoleRepository extends JpaRepository<EmployeeRole, Integer> {

//    List<EmployeeRole> findByEmployeeId(String employeeId);

	List<EmployeeRole> findByEmployeeIdAndStatus(Integer employeeId, int status);

}
