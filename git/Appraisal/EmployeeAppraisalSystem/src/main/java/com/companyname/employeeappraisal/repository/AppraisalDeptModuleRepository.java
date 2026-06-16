package com.companyname.employeeappraisal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.companyname.employeeappraisal.model.AppraisalDeptModule;

public interface AppraisalDeptModuleRepository extends JpaRepository<AppraisalDeptModule,Integer> {

	List<AppraisalDeptModule> findByDepartmentIdAndStatus(Integer departmentId, int Status);





}
