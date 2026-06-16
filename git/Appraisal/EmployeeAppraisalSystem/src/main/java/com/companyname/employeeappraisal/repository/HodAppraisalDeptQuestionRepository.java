package com.companyname.employeeappraisal.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.companyname.employeeappraisal.model.EmployeeAppraisal;
import com.companyname.employeeappraisal.model.HodAppraisalDeptQuestion;

public interface HodAppraisalDeptQuestionRepository extends JpaRepository<HodAppraisalDeptQuestion,Integer>{

	List<HodAppraisalDeptQuestion> findByStatus(int status);

	List<HodAppraisalDeptQuestion> findByDeptModule_DeptModuleIdAndDeptModule_Status(Integer moduleId, int i);
	 List<HodAppraisalDeptQuestion> findByDeptModule_DeptModuleIdAndStatus(Integer deptModuleId, Integer status);
}
