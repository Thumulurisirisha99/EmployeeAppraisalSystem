package com.companyname.employeeappraisal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.companyname.employeeappraisal.model.AppraisalDeptQuestion;
import com.companyname.employeeappraisal.model.AppraisalModule;

public interface AppraisalModuleRepository extends JpaRepository<AppraisalModule,Integer> {
}
